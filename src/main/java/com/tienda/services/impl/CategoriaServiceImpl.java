/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.services.impl;

import com.tienda.service.CategoriaService;
import com.tienda.domain.Categoria;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoriaServiceImpl implements CategoriaService {

    //la anotaciuon autowired crea un unico objeti sin hacer new y se mantiene
    @Autowired
    private CategoriaDao categoriaDao;

    @Override
    public List<Categoria> getCategorias(boolean activos) {
        var lista = categoriaDao.findAll();
        if (activos) {// si quiere solo las categorias activcas
            lista.removeIf(e -> !e.isActivo());
        }
        return lista;

    }

}
