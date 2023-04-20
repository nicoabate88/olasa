
package com.ola.portal.controlador;

import com.ola.portal.excepcion.MiException;
import com.ola.portal.servicio.ClienteServicio;
import com.ola.portal.servicio.ServicioEjecutadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ejecutado")
@PreAuthorize("hasAnyRole('ROLE_administrativo', 'ROLE_directivo')")
public class ServicioEjecutadoControlador {
    
    @Autowired
    private ServicioEjecutadoServicio ejecutadoServicio;
    @Autowired
    private ClienteServicio clienteServicio;
    
    @PostMapping("/registro")
    public String registro(@RequestParam String tipoServicio, @RequestParam String observacion, @RequestParam Long idSolicitud,
            @RequestParam Long idCliente, @RequestParam Long idUsuario){
        
        try {
            
            ejecutadoServicio.crearServicioEjecutado(tipoServicio, observacion, idSolicitud, idCliente, idUsuario);
            
            return "redirect:../ejecutado/listar";
            
        } catch (MiException ex) {
            
            return "index.html";
        }
        
    }
    
    @GetMapping("/listar")
    public String listar(ModelMap modelo){
        
        modelo.addAttribute("ejecutados", ejecutadoServicio.buscarEjecutados());
        modelo.addAttribute("clientes", clienteServicio.buscarClientes());
        return "ejecutadoLista.html";
    }
    
    @PostMapping("/listarTipo")
    public String listarFiltro(@RequestParam String tipo, ModelMap modelo ){
        
        modelo.addAttribute("ejecutados", ejecutadoServicio.buscarEjecutados());
        modelo.put("tipo", tipo);
        
        return "ejecutadoListaTipo.html";
    }
    
    @PostMapping("/listarNombre")
    public String listarNombre(@RequestParam String nombre, ModelMap modelo ){
        
        modelo.addAttribute("ejecutados", ejecutadoServicio.buscarEjecutados());
        modelo.put("nombre", nombre);
        
        return "ejecutadoListaNombre.html";
}
    
}
