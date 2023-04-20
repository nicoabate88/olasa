
package com.ola.portal.servicio;

import com.ola.portal.entidad.Admin;
import com.ola.portal.entidad.Cliente;
import com.ola.portal.entidad.Solicitud;
import com.ola.portal.excepcion.MiException;
import com.ola.portal.repositorio.AdminRepositorio;
import com.ola.portal.repositorio.ClienteRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ola.portal.repositorio.SolicitudRepositorio;

@Service
public class SolicitudServicio {
    
    @Autowired
    private SolicitudRepositorio solicitudRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private AdminRepositorio adminRepositorio;
    
    @Transactional
    public void crearSolicitud(String tipoServicio, String observacion, Long idCliente, Long idUsuario) throws MiException{
        
        Cliente cliente = new Cliente();
        Optional<Cliente> cte = clienteRepositorio.findById(idCliente);
        if(cte.isPresent()){
            cliente = cte.get();
        }
        Admin usuario = new Admin();
        Optional<Admin> user = adminRepositorio.findById(idUsuario);
        if(user.isPresent()){
            usuario = user.get();
        }
       
        Solicitud solicitud = new Solicitud();
        
        solicitud.setTipoServicio(tipoServicio);
        solicitud.setObservacion(observacion);
        solicitud.setCliente(cliente);
        solicitud.setUsuario(usuario);
        
        solicitud.setFecha(new Date());
        solicitud.setEstado("pendiente");
        
        solicitudRepositorio.save(solicitud);    
    }
    
    public ArrayList<Solicitud> buscarSolicitudes(){
        
        ArrayList<Solicitud> lista = new ArrayList();
        
        lista = (ArrayList<Solicitud>) solicitudRepositorio.findAll();
        
        return lista;
    }
    
    public Solicitud buscarSolicitud(Long id){
        
        return solicitudRepositorio.getById(id);
    }
    
    @Transactional
    public void modificarSolicitud(Long id, String observacion, String estado) throws MiException{
                    
        Optional<Solicitud> respuesta = solicitudRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            
            Solicitud solicitud = respuesta.get();
            
            solicitud.setObservacion(observacion);
            solicitud.setEstado(estado);
            
            solicitudRepositorio.save(solicitud);
        
        }
    }
    
    @Transactional
    public void eliminarSolicitud(Long id) throws MiException{
        
        Optional<Solicitud> respuesta = solicitudRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            
            solicitudRepositorio.deleteById(id);
        }
        
    }
    
    public boolean validarEliminarSolicitud(Long id){
        
         Solicitud solicitudS = solicitudRepositorio.findById(id).get();

         boolean flag = false;
         
         if(solicitudS.getEstado().equals("a ejecutar")){
            flag = true;
        }
         
         return flag;
    }
   
    
    
    
}
