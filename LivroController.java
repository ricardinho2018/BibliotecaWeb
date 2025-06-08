package com.exemplo.biblioteca.controller;

import com.exemplo.biblioteca.model.Livro;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class LivroController {

    private List<Livro> livros = new ArrayList<>();

    public LivroController() {
        livros.add(new Livro("Dom Quixote", "Cervantes"));
        livros.add(new Livro("Os Maias", "Eça de Queirós"));
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/livros")
    public String listarLivros(Model model) {
        model.addAttribute("livros", livros);
        return "listar";
    }

    @GetMapping("/livros/novo")
    public String formNovoLivro(Model model) {
        model.addAttribute("livro", new Livro());
        return "adicionar";
    }

    @PostMapping("/livros")
    public String adicionarLivro(@ModelAttribute Livro livro) {
        livros.add(livro);
        return "redirect:/livros";
    }

    @PostMapping("/livros/emprestar")
    public String emprestarLivro(@RequestParam String titulo) {
        livros.stream()
              .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
              .findFirst()
              .ifPresent(l -> l.setEmprestado(true));
        return "redirect:/livros";
    }

    @PostMapping("/livros/devolver")
    public String devolverLivro(@RequestParam String titulo) {
        livros.stream()
              .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
              .findFirst()
              .ifPresent(l -> l.setEmprestado(false));
        return "redirect:/livros";
    }
}
