
package com.tienda.domain;

import lombok.Data;
import jakarta.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="categoria")
public class Categoria implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_categoria")
    private long id_Categoria;
    private String descripcion;
    private String rutaImagen;
    private boolean activo;
    
    pupublic Categoria(String descripcion, boolean activo){
        this.activo=activo;
        this.descripcion=descripcion;
    }
    
    
    
}
