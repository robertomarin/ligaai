package ai.liga.ligaai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.spring.web.servlet.view.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ai.liga.ligaai.model.LigaAi;
import ai.liga.ligaai.service.LigaAiService;
import ai.liga.ligaai.util.LigaAiUtils;
import ai.liga.user.model.User;
import ai.liga.user.service.UserService;
import ai.liga.util.$;

@Controller
public class LigaAiController {

	private final LigaAiService ligaAiService;

	private final LigaAiUtils ligaAiUtils;

	private final UserService userService;

	@Autowired
	public LigaAiController(LigaAiService ligaAiService, LigaAiUtils ligaAiUtils, UserService userService) {
		this.ligaAiService = ligaAiService;
		this.ligaAiUtils = ligaAiUtils;
		this.userService = userService;
	}

	@RequestMapping("/l/")
	public String view() {
		return "ligaai";
	}

	@RequestMapping("/l/novo")
	public ModelAndView criar(@Valid LigaAi ligaAi, HttpServletRequest request) {
		User user = $.getUserFromRequest(request);

		if (user == null) {
			return new ModelAndView(new RedirectView("/"));
		}

		ModelAndView mav = new ModelAndView("l/ligaai");
		ligaAi.setUser(user);
		ligaAi.setRemoteAddress(request.getRemoteAddr());
		ligaAiUtils.fillTags(ligaAi);
		ligaAi = ligaAiService.merge(ligaAi);
		return mav.addObject("ligaai", ligaAi);
	}

	@RequestMapping("/l/topo/{id}")
	public ModelAndView topo(@PathVariable Long id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(new JsonView());
		User user = $.getUserFromRequest(request);
		return mav.addObject("ok", user != null && ligaAiService.topo(id));
	}

	@RequestMapping("/l/mais/{inicio}")
	public ModelAndView mais(@PathVariable int inicio, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/l/mais");
		return mav.addObject("ligaais", ligaAiService.getTop(inicio));
	}

	@RequestMapping("/l/apagar/{id}")
	public ModelAndView apagar(@PathVariable Long id, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(new JsonView());

		User u = $.getUserFromRequest(request);
		if (u != null && (u = userService.load(u.getId())) != null && u.isAdmin()) {
			ligaAiService.delete(id);
			mav.addObject("ok", true);
		} else {
			mav.addObject("ok", false);
		}

		return mav;
	}

}
