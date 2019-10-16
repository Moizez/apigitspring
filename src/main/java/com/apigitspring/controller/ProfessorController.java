package com.apigitspring.controller;


import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.apigitspring.model.Professor;


@Controller
@RequestMapping("/professor")
public class ProfessorController {
	
	private static final String Url_Api_Professor = "https://projetoifhelp.herokuapp.com/api/professor/";
	
	@GetMapping("/add")
	public ModelAndView add(Professor professor) {
		
		ModelAndView mv = new ModelAndView("/professor/form");
		mv.addObject("professor", professor);
		return mv;
	}
	
	@PostMapping("/save")
	public ModelAndView salvar(@Valid Professor professor, BindingResult result	) {
		RestTemplate rest = new RestTemplate();
		if(result.hasErrors()) {
			return add(professor); 
		}

		rest.postForObject(Url_Api_Professor, professor, Professor.class);
		
		return findAll();
	}
	
	
	@GetMapping("/listar")
	private ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("professor/listar");
		RestTemplate rest = new RestTemplate();
		
		Professor[] professor = rest.getForObject(Url_Api_Professor, Professor[].class);
		
		mv.addObject("professores", professor);
		
		return mv;
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		RestTemplate restTemplate = new RestTemplate();
		Professor professor = restTemplate.getForObject(Url_Api_Professor +id, Professor.class);
		
		return add(professor);
	}
	
	@GetMapping("/delete/{id}")
	public  ModelAndView delete(@PathVariable("id") Long id) {
	    	
	    RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(Url_Api_Professor + "{id}/", id);

		return findAll();
	}

}
