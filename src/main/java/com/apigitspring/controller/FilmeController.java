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

import com.apigitspring.model.Filme;

@Controller
@RequestMapping("/filme")
public class FilmeController {
	
	private static final String Url_Api = "http://localhost:9000/api/filme/";
	
	@GetMapping("/add")
	public ModelAndView add(Filme filme) {
		
		ModelAndView mv = new ModelAndView("/filme/form");
		mv.addObject("filme", filme);
		return mv;
	}
	
	@PostMapping("/save")
	public ModelAndView salvar(@Valid Filme filme, BindingResult result	) {
		RestTemplate rest = new RestTemplate();
		if(result.hasErrors()) {
			return add(filme); 
		}

		rest.postForObject(Url_Api, filme, Filme.class);
		
		return findAll();
	}
	
	//Método de listar
	@GetMapping("/listar")
	private ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("filme/listar");
		RestTemplate rest = new RestTemplate();
		
		Filme[] filme = rest.getForObject(Url_Api, Filme[].class);
		
		mv.addObject("filmees", filme);
		
		return mv;
	}
	
	//Método de editar
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		RestTemplate restTemplate = new RestTemplate();
		Filme filme = restTemplate.getForObject(Url_Api +id, Filme.class);
		
		return add(filme);
	}
	
	//Método de deletar
	@GetMapping("/delete/{id}")
	public  ModelAndView delete(@PathVariable("id") Long id) {
	    	
	    RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(Url_Api + "{id}/", id);

		return findAll();
	}

}
