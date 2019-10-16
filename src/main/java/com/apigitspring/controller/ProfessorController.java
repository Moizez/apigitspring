package com.apigitspring.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.apigitspring.model.Professor;
import com.apigitspring.repository.ProfessorRepository;


@Controller
@RequestMapping("/professor")
public class ProfessorController {
	
	private ProfessorRepository repository;
	
	
	private static final String Url_Api_Professor = "https://projetoifhelp.herokuapp.com/api/professor/";
	
	@GetMapping("/add")
	public ModelAndView cadastrar(Professor professor) {
		
		ModelAndView mv = new ModelAndView("/professor/form");
		mv.addObject("professor", professor);
		return mv;
	}
	
	@PostMapping("/save")
	public ModelAndView salvar(@Valid Professor professor, BindingResult result	) {
		RestTemplate rest = new RestTemplate();
		if(result.hasErrors()) {
			return cadastrar(professor); 
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
	
	@PutMapping("/professor/{id}")
	public ResponseEntity<Object> updateProfessor(@RequestBody Professor professor, @PathVariable long id) {

		Optional<Professor> prof = repository.findById(id);

		if (!prof.isPresent())
			return ResponseEntity.notFound().build();

		professor.setId(id);
		
		repository.save(professor);

		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/professor/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}

}
