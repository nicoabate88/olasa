
package com.ola.portal.servicio;

import com.ola.portal.entidad.Admin;
import com.ola.portal.excepcion.MiException;
import com.ola.portal.repositorio.AdminRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class AdminServicio implements UserDetailsService {
    
    @Autowired
    private AdminRepositorio adminRepositorio;
    
    @Transactional
    public void crearUsuario(String nombre, String dni, String telefono, String email, String usuario, String password, String password2, String rol) throws MiException{
        
        validar(dni, usuario, password, password2);
        
        Admin admin = new Admin();
        
        admin.setNombre(nombre);
        admin.setDni(dni);
        admin.setTelefono(telefono);
        admin.setEmail(email);
        admin.setUsuario(usuario);
        admin.setPassword(new BCryptPasswordEncoder().encode(password));
        admin.setRol(rol);
        admin.setFechaAlta(new Date());
        
        adminRepositorio.save(admin);
    }
    
    public ArrayList<Admin> buscarUsuarios(){
        
        ArrayList<Admin> listaUsuarios = new ArrayList();
        
        listaUsuarios = (ArrayList<Admin>) adminRepositorio.findAll();
        
        return listaUsuarios;
    }
    
    private void validar(String dni, String usuario, String password, String password2) throws MiException{
        
        if(!password.equals(password2)){
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
        
        ArrayList<Admin> listaUsuarios = new ArrayList();
        
        listaUsuarios = buscarUsuarios();
        
        for (Admin lista : listaUsuarios) {
        
        if(lista.getDni().equals(dni)){
            throw new MiException("El DNI del Usuario ya se encuentra registrado");
        }
        if(lista.getUsuario().equals(usuario)){
            throw new MiException("El nombre de Usuario ingresado no está disponible, por favor ingrese otro");
        }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        
       Admin admin = adminRepositorio.buscarPorUsuario(usuario);
       
       if(admin != null){
           
           List<GrantedAuthority> permisos = new ArrayList();
           
           GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+admin.getRol());
           
           permisos.add(p);
           
           ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
           
           HttpSession session = attr.getRequest().getSession(true);
           
           session.setAttribute("usuariosession", admin);
           
           return new User(admin.getUsuario(), admin.getPassword(), permisos);
           
       } else{
           
        return null;
    }
    }
    
}
