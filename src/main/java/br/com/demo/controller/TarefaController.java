package br.com.demo.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.demo.model.Tarefa;
import br.com.demo.model.Usuario;
import br.com.demo.repository.TarefaRepository;
import br.com.demo.service.UsuarioService;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/listar")
	public ModelAndView listar(HttpServletRequest request, @PageableDefault(size = 3, sort = {"id"}) Pageable pageable) {
		String emailUsuario = request.getUserPrincipal().getName();
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", emailUsuario);
		mv.setViewName("tarefas/listar");
		mv.addObject("tarefas", tarefaRepository.carregarTarefasPorUsuario(emailUsuario, pageable));
		return mv;
	}
	
	@GetMapping("/paginacao")
	public ModelAndView carregaTarefaPorPaginacao(HttpServletRequest request, @PageableDefault(size = 3) Pageable pageable, ModelAndView model) {
		String emailUsuario = request.getUserPrincipal().getName();
		Page<Tarefa> pageTarefa = tarefaRepository.carregarTarefasPorUsuario(emailUsuario, pageable);
		model.addObject("usuario", emailUsuario);
		model.addObject("tarefas", pageTarefa);
		model.setViewName("tarefas/listar");
		return model;
	}
	
	@GetMapping("/inserir")
	public ModelAndView inserir() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tarefas/inserir");
		mv.addObject("tarefa", new Tarefa());
		return mv;
	}
	
	@PostMapping("/inserir")
	public ModelAndView inserir(@Valid Tarefa tarefa, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		if(tarefa.getDataExpiracao() == null) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiração é obrigatória.");
		}else if(tarefa.getDataExpiracao().before(new Date())) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiração não pode ser anterior à data atual e maior que a data atual.");
		}
		if(result.hasErrors()) {
			mv.setViewName("tarefas/inserir");
			mv.addObject(tarefa);
		}else {
			String emailUsuario = request.getUserPrincipal().getName();
			Usuario usuarioLogado = usuarioService.encontrarPorEmail(emailUsuario);
			tarefa.setUsuario(usuarioLogado);
			tarefaRepository.save(tarefa);
			mv.setViewName("redirect:/tarefas/listar");
		}
		return mv;
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		Tarefa tarefa = tarefaRepository.getOne(id);
		mv.addObject("tarefa", tarefa);
		mv.setViewName("tarefas/alterar");
		return mv;
	}
	
	@PostMapping("/alterar")
	public ModelAndView alterar(@Valid Tarefa tarefa, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		if(tarefa.getDataExpiracao() == null) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiração é obrigatória.");
		}else if(tarefa.getDataExpiracao().before(new Date())) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiração não pode ser anterior à data atual.");
		}
		if(result.hasErrors()) {
			mv.setViewName("tarefas/alterar");
			mv.addObject(tarefa);
		}else {
			String emailUsuario = request.getUserPrincipal().getName();
			Usuario usuarioLogado = usuarioService.encontrarPorEmail(emailUsuario);
			tarefa.setUsuario(usuarioLogado);
			tarefaRepository.save(tarefa);
			mv.addObject("usuario", emailUsuario);
			mv.setViewName("redirect:/tarefas/listar");
		}
		return mv;
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id) {
		tarefaRepository.deleteById(id);
		return "redirect:/tarefas/listar";
	}
	
	@GetMapping("/concluir/{id}")
	public String concluir(@PathVariable("id") Long id) {
		Tarefa tarefa = tarefaRepository.getOne(id);
		tarefa.setConcluida(true);
		tarefaRepository.save(tarefa);
		return "redirect:/tarefas/listar";
	}
}
