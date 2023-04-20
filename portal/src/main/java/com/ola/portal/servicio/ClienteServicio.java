
package com.ola.portal.servicio;

import com.ola.portal.entidad.Cliente;
import com.ola.portal.entidad.Solicitud;
import com.ola.portal.excepcion.MiException;
import com.ola.portal.repositorio.ClienteRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ola.portal.repositorio.SolicitudRepositorio;

@Service
public class ClienteServicio {
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private SolicitudRepositorio solicitudRepositorio;
    
    @Transactional
    public void crearCliente(String nombre, String dni, String telefono, String email, String observacion) throws MiException{
        
        validar(dni);
         
        Cliente cliente = new Cliente();
        
        cliente.setNombre(nombre);
        cliente.setDni(dni);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);
        cliente.setObservacion(observacion);
        cliente.setFechaAlta(new Date());
        
        clienteRepositorio.save(cliente);
        
    }
    
    public ArrayList<Cliente> buscarClientes(){
        
        ArrayList<Cliente> listaClientes = new ArrayList();
        
        listaClientes = (ArrayList<Cliente>) clienteRepositorio.findAll();
        
        return listaClientes;
        
    }
    
    public Cliente buscarCliente(Long id){
        
        return clienteRepositorio.getById(id);
    }
    
    @Transactional
    public void modificarCliente(Long id, String nombre, String dni, String telefono, String email, String observacion) throws MiException{
        
        Optional<Cliente> cte = clienteRepositorio.findById(id);
        
        if(cte.isPresent()){
            Cliente cliente = cte.get();
            
            cliente.setNombre(nombre);
            cliente.setDni(dni);
            cliente.setTelefono(telefono);
            cliente.setEmail(email);
            cliente.setObservacion(observacion);
            
            clienteRepositorio.save(cliente);
        } 
    }
    
    @Transactional
    public void eliminarCliente(Long id) throws MiException{
        
        ArrayList<Solicitud> solicitud = solicitudRepositorio.buscarSolicitudIdCte(id);
        
        if(solicitud == null || solicitud.isEmpty()){
            
            clienteRepositorio.deleteById(id);
            
        } else{
            
            throw new MiException("El Cliente no puede ser eliminado porque tiene contratado Solicitud/es de Servicios");
        }
        
    }
    
    private void validar(String dni) throws MiException{
        
       ArrayList<Cliente> clientes = buscarClientes();
       
        for (Cliente cliente : clientes) {
            if(cliente.getDni().equals(dni)){
               throw new MiException("El DNI del Cliente ya se encuentra registrado");
            }
        }
    }
    
    
    
}
