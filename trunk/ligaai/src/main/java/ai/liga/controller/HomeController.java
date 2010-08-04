package ai.liga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ai.liga.ligaai.model.LigaAi;
import ai.liga.ligaai.service.LigaAiService;

@Controller
public class HomeController {

	private final LigaAiService ligaAiService;

	@Autowired
	public HomeController(LigaAiService ligaAiService) {
		this.ligaAiService = ligaAiService;
	}

	@RequestMapping("/")
	public ModelAndView home() {
		List<LigaAi> ligaais = ligaAiService.getTop();
		return new ModelAndView("home").addObject("ligaais", ligaais);
		
		
	}
}