package br.com.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.demo.repository.TarefaRepository;

@Controller
public class HomeController {
	
	@Autowired
	private TarefaRepository tarefaRepository;

	@GetMapping("/")
	public ModelAndView home(HttpServletRequest request) {
		String email = request.getUserPrincipal().getName();
		ModelAndView mv = new ModelAndView();
		
		int totalConcluidas = tarefaRepository.quantidadeTarefasConcluida(email);
		int totalNaoConcluidas = tarefaRepository.quantidadeTarefasNaoConcluida(email);
		
		mv.addObject("totalConcluidas", totalConcluidas);
		mv.addObject("totalNaoConcluidas", totalNaoConcluidas);
		mv.addObject("usuario", email);
		
		mv.setViewName("home/home");
		return mv;
	}
}
