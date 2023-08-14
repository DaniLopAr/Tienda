/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tienda.service;


import java.io.IOException;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Daniel Lopez
 */
public interface ReporteService {
    public ResponseEntity<Resource> generaReporte(String reporte,//nombre del archivo que tiene el reporte.jasper
            Map<String, Object> parametros,// Parametros si el reporte ocupa parametros
            String tipo //tipo de reporte PDF, XLS, CSV...
    ) throws IOException;
    
}
