
package com.ola.portal.controlador;

import com.ola.portal.excepcion.MiException;
import com.ola.portal.servicio.ClienteServicio;
import com.ola.portal.servicio.OrdenTrabajoServicio;
import com.ola.portal.servicio.SolicitudServicio;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/orden")
@PreAuthorize("hasAnyRole('ROLE_administrativo', 'ROLE_directivo')")
public class OrdenTrabajoControlador {
    
    @Autowired 
    private OrdenTrabajoServicio ordenServicio;
    
    @PostMapping("/registro")
    public String registro(@RequestParam String tipoServicio, @RequestParam String observacion, @RequestParam String fecha,
            @RequestParam Long idSolicitud, @RequestParam Long idCliente, @RequestParam Long idUsuario, ModelMap modelo) throws ParseException{
        
        try {
            
            ordenServicio.crearOrdenTrabajo(tipoServicio, observacion, fecha, idSolicitud, idCliente, idUsuario);
            
            return "redirect:../orden/listar";
            
        } catch (MiException ex) {
           modelo.put("error", ex.getMessage());
           return "ordenRegistrar.html";
        } 
      
    }
    
    @GetMapping("/listar")
    public String listar(ModelMap modelo){
        
        modelo.addAttribute("ordenes", ordenServicio.buscarOrdenesTrabajo());
        
        return "ordenLista.html";
    }
    
    @GetMapping("/mostrar/{id}")
    public String mostrar(@PathVariable Long id, ModelMap modelo){
        
        modelo.put("orden", ordenServicio.buscarOrden(id));
        
        return "ordenMostrar.html";
    }
    
    @PostMapping("/listarFiltro")
    public String listarFiltro(@RequestParam String tipo, ModelMap modelo){
       
        modelo.addAttribute("ordenes", ordenServicio.buscarOrdenesTrabajo());
        modelo.put("tipo", tipo);
     
        return "ordenListaTipo.html";
    }
}
