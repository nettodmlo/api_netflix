package com.aula.aplicacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aula.aplicacao.model.Filmes;
import com.aula.aplicacao.service.FilmeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/filmes")
public class FilmeController {
    
    @Autowired
    private FilmeService filmeService;
    
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Filmes> filmes = filmeService.findAll();
        model.addAttribute("filmes", filmes);
        return "listaFilmes";
    }
    
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        filmeService.deletar(id);
        return "redirect:/filmes/listar";
    }
    
	@GetMapping
    public String exibirPagina(Model model) {
        model.addAttribute("filme", new Filmes());
		return "filme";
    }
    
    @PostMapping
    public String formFilme(@ModelAttribute @Valid Filmes filme, BindingResult bindingResult) { // Alterando "produto" para "filme"
        if(bindingResult.hasErrors()) {
            return "filme"; 
        }
        System.out.println(filme);
        filmeService.save(filme);
        return "filme";
    }
    @GetMapping("/atualizar/{id}")
public String exibirFormularioAtualizacao(@PathVariable Long id, Model model) {
    Filmes filme = filmeService.findById(id);
    model.addAttribute("filme", filme);
    return "updateFilme"; 
}

@PostMapping("/atualizar")
public String atualizarFilme(@ModelAttribute @Valid Filmes filme, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return "updateFilme"; 
    }
    filmeService.save(filme); 
    return "redirect:/filmes/listar"; 
}
}