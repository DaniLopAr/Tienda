
package com.tienda.controller;

import com.tienda.services.CategoriaService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {
    
    @Autowired
    public CategoriaService categoriaService;
    
    
    @GetMapping("/listado")
    public String listado(Model model){
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalcategorias", categorias.size());
        return "/categoria/listado";
    }
    
    
}
