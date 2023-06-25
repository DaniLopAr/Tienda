package com.tienda.services;

import com.tienda.domain.Categoria;
import java.util.List;



public interface CategoriaService {
    
    //el siguiente metodo retorna una lista con las categorias
    //que estan en la tabla categoria, todas o solo las activas
  
    public List<Categoria> getCategorias(boolean activos);
        
    
    
    //aca siguen los metodos para hacer un CRUD de la tabla categorias
    
    
    
    
}
