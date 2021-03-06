package ai.liga.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.spring.web.servlet.view.JsonView;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ai.liga.cookie.CookieComponent;
import ai.liga.ligaai.service.LigaAiService;
import ai.liga.user.model.User;
import ai.liga.user.service.UserService;
import ai.liga.util.$;
import ai.liga.util.Constants;

@Controller
public class UserController {

	private final UserService userService;
	
	private final LigaAiService ligaAiService;

	private final CookieComponent cookieComponent;

	@Autowired
	public UserController(UserService userService, LigaAiService ligaAiService, CookieComponent cookieComponent) {
		this.userService = userService;
		this.ligaAiService = ligaAiService;
		this.cookieComponent = cookieComponent;
	}

	@RequestMapping("/u/conta")
	public void conta(final HttpServletRequest request, final HttpServletResponse response) {
		User user = $.getUserFromRequest(request);
		if (user == null) {
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", response.encodeRedirectURL("/"));
		} else {
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.setHeader("Location", response.encodeRedirectURL("/u/conta/" + user.getId()));
		}
	}

	@RequestMapping("/u/conta/{id}")
	public ModelAndView conta(@PathVariable long id, final HttpServletRequest request) {
		User user = userService.load(id);

		User userIn = $.getUserFromRequest(request);
		if (user == null) {
			return new ModelAndView(new RedirectView("/"));
		}

		ModelAndView mav = new ModelAndView("/u/conta").addObject("user", user);
		mav.addObject("ligaais", ligaAiService.getTopFromUser(user));
		return mav.addObject("himself",
				(userIn != null && userIn.getId() == user.getId()));
	}

	@RequestMapping("/u/registrar")
	public String registrar(HttpServletRequest request) {
		$.getUserFromRequest(request);
		User user = (User) request.getAttribute(Constants.USER);
		return "/u/registrar";
	}

	@RequestMapping("/u/entrar")
	public ModelAndView entrar(@Valid User user, BindingResult result, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(new JsonView());
		if (result.hasErrors()) {
			return mav.addObject("errors", result.getFieldErrors());
		}

		user = userService.login(user);
		if (user == null) {
			result.addError(new FieldError("user", "email", "E-mail ou senha inválidos."));
			return mav.addObject("errors", result.getFieldErrors());
		}

		$.setUserOnRequest(request, user);
		return mav.addObject(Constants.USER, user).addObject("ok", "true");
	}

	@RequestMapping("/u/sair")
	public String sair(HttpServletRequest request, HttpServletResponse response) {
		$.setUserOnRequest(request, null);
		response.addCookie(cookieComponent.createExpiredCookie(Constants.USER));

		return "redirect:/";
	}

	@RequestMapping("/u/atualizar")
	public ModelAndView atualizar(User user, BindingResult result, HttpServletRequest request) {
		User userIn = $.getUserFromRequest(request);
		ModelAndView mav = new ModelAndView(new JsonView());
		if (userIn == null || user == null)
			return mav.addObject("ok", false);

		if (userIn.getId() != null && userIn.getId() != user.getId()) {
			result.addError(new FieldError("user", "id", "Não foi possível atualizar a conta de usuário. :("));
			return mav.addObject("errors", result.getFieldErrors());
		}

		userIn = userService.load(user.getId());
		if (userIn.getId() != null && userIn.getId() != user.getId()) {
			result.addError(new FieldError("user", "id", "Não foi possível carregar a conta de usuário. :("));
			return mav.addObject("errors", result.getFieldErrors());
		}

		if (GenericValidator.isBlankOrNull(user.getName())) {
			result.addError(new FieldError("user", "id", "Não foi possível carregar a conta de usuário. :("));
			return mav.addObject("errors", result.getFieldErrors());
		}

		userIn.setName(user.getName());
		return mav.addObject("ok", "true");
	}

	@RequestMapping("/u/atualizar-senha")
	public ModelAndView atualizarSenha(@RequestParam String newPassword, User user, BindingResult result,
			HttpServletRequest request) {

		User userIn = $.getUserFromRequest(request);
		ModelAndView mav = new ModelAndView(new JsonView());
		if (userIn == null || user == null || userIn.getId() == null)
			return mav.addObject("ok", false);

		if (userIn.getId() != user.getId()) {
			result.addError(new FieldError("user", "id", "Não foi possível atualizar a conta de usuário. :("));
			return mav.addObject("errors", result.getFieldErrors());
		}

		userIn = userService.load(user.getId());
		if (userIn.getId() != null && userIn.getId() != user.getId()) {
			result.addError(new FieldError("user", "id", "Não foi possível carregar a conta de usuário. :("));
			return mav.addObject("errors", result.getFieldErrors());
		}

		if (!user.getPassword().equals(userIn.getPassword())) {
			result.addError(new FieldError("user", "password", "Senha atual não confere. ;)"));
			return mav.addObject("errors", result.getFieldErrors());
		}

		if (newPassword == null || newPassword.length() < 6 || newPassword.length() > 20) {
			result.addError(new FieldError("newPassword", null,
					"Nova senha não é válida. Digite algo entre 6 e 20 caracteres. ;)"));
			return mav.addObject("errors", result.getFieldErrors());
		}

		userIn.setPassword(newPassword);
		return mav.addObject("ok", "true");
	}

	@RequestMapping("/u/criar")
	public ModelAndView criar(@Valid User user, BindingResult result, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(new JsonView());
		if (result.hasErrors()) {
			return mav.addObject("errors", result.getFieldErrors());
		}

		if (userService.exists(user)) {
			result.addError(new FieldError("user", "email", "E-mail já cadastrado."));
			return mav.addObject("errors", result.getFieldErrors());
		}
		user = userService.save(user);
		$.setUserOnRequest(request, user);
		return mav.addObject(Constants.USER, user).addObject("ok", "true");
	}

}
