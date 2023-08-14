/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.services.impl;

import com.tienda.service.ReporteService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterContext;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel Lopez
 */
@Service
public class ReporteServiceImpl implements ReporteService {

    //Para hacer la conexion a la base de datos...
    @Autowired
    DataSource dataSource;

    @Override
    public ResponseEntity<Resource> generaReporte(
            String reporte,
            Map<String, Object> parametros,
            String tipo) throws IOException {

        try {

            //Estilo es para saber donde lo queremos vver o descargar
            String estilo;
            if (tipo.equals("vPDF")) {
                estilo = "inline; ";
            } else {
                estilo = "attachment; ";
            }

            //se define la ruta donde estan los reportes
            String reportePath = "reportes";

            //se define el obnjeto donde se genera en memeoria el reporte...
            ByteArrayOutputStream salida = new ByteArrayOutputStream();

            //se define el lugar y acceso al archivo del reporte .jasper
            var fuente = new ClassPathResource(
                    reportePath
                    + File.separator
                    + reporte
                    + ".jasper");

            // definir un objeto para leer el archivo del reporte coipilado
            InputStream elReporte = fuente.getInputStream();

            // se crea el reporte como tal... se genera en memoria 
            var reporteJasper = JasperFillManager.fillReport(elReporte, parametros, dataSource.getConnection());

            //Se comienza con la definicion de la respuesta al usuario
            //El medio.
            MediaType mediaType = null;

            // se define el archivo de salida
            String archivoSalida = "";

            // se define un areglo de bytes para pasar el reporte a bytes
            byte[] data;

            //dependiendo de lo seleccionado asi se genera la salida
            switch (tipo) {
                case "Pdf", "vPdf" -> {
                    JasperExportManager.exportReportToPdfStream(reporteJasper, salida);

                    mediaType = MediaType.APPLICATION_PDF;
                    archivoSalida = reporte + ".pdf";
                }

                case "Xls" -> {
                    JRXlsExporter exportador = new JRXlsExporter();
                    exportador
                            .setExporterInput(new SimpleExporterInput(reporteJasper));
                    exportador
                            .setExporterOutput(new SimpleOutputStreamExporterOutput(salida));

                    SimpleXlsxReportConfiguration configuracion = new SimpleXlsxReportConfiguration();

                    configuracion
                            .setDetectCellType(Boolean.TRUE);
                    configuracion
                            .setCollapseRowSpan(Boolean.TRUE);

                    exportador
                            .setConfiguration(configuracion);

                    exportador.exportReport();
                    mediaType = MediaType.APPLICATION_OCTET_STREAM;
                    archivoSalida = reporte + ".xlsx";

                }
                
                

            }

            //se toma el documento pdf y se trabnsforma en bytes
            data = salida.toByteArray();

            //ya se define la salida del reportre
            HttpHeaders header = new HttpHeaders();
            header.set("Content-Disposition", estilo + "filename=\"" + archivoSalida + "\"");

            return ResponseEntity
                    .ok()
                    .headers(header)
                    .contentType(mediaType)
                    .body(new InputStreamResource(
                            new ByteArrayInputStream(
                                    data
                            )
                    ));

        } catch (SQLException | JRException e) {
            e.printStackTrace();
        }

        return null;
    }

}
