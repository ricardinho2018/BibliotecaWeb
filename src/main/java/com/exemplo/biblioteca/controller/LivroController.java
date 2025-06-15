package com.exemplo.biblioteca.controller;

import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;


    @GetMapping("/livros")
    public String listarLivros(Model model) {
        List<Livro> livros = livroRepository.findAll();
        model.addAttribute("livros", livros);
        return "livros";
    }

    @GetMapping("/livros/novo")
    public String mostrarFormularioNovoLivro(Model model) {
        model.addAttribute("livro", new Livro());
        return "novo";
    }

    @PostMapping("/livros")
    public String salvarLivro(@ModelAttribute Livro livro) {
        livroRepository.save(livro);
        return "redirect:/livros";
    }

    @GetMapping("/livros/apagar/{id}")
    public String apagarLivro(@PathVariable Long id) {
        livroRepository.deleteById(id);
        return "redirect:/livros";
    }

    // Mostrar o formulário para editar um livro existente
    @GetMapping("/livros/editar/{id}")
    public String mostrarFormularioEditarLivro(@PathVariable("id") Long id, Model model) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro inválido Id:" + id));
        model.addAttribute("livro", livro);
        return "editar";  // nome da view Thymeleaf para editar livro
    }

    // Receber os dados atualizados e salvar
    @PostMapping("/livros/{id}")
    public String atualizarLivro(@PathVariable("id") Long id, @ModelAttribute Livro livroAtualizado) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro inválido Id:" + id));

        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setAutor(livroAtualizado.getAutor());
        livro.setAno(livroAtualizado.getAno());

        livroRepository.save(livro);
        return "redirect:/livros";
    }

}
