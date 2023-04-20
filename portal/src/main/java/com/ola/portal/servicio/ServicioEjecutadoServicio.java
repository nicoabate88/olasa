
package com.ola.portal.servicio;

import com.ola.portal.entidad.Admin;
import com.ola.portal.entidad.Cliente;
import com.ola.portal.entidad.ServicioEjecutado;
import com.ola.portal.entidad.Solicitud;
import com.ola.portal.excepcion.MiException;
import com.ola.portal.repositorio.AdminRepositorio;
import com.ola.portal.repositorio.ClienteRepositorio;
import com.ola.portal.repositorio.ServicioEjecutadoRepositorio;
import com.ola.portal.repositorio.SolicitudRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioEjecutadoServicio {

    @Autowired
    private ServicioEjecutadoRepositorio servicioEjecutadoRepositorio;

    @Autowired
    private SolicitudRepositorio solicitudRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private AdminRepositorio adminRepositorio;

    @Transactional
    public void crearServicioEjecutado(String tipoServicio, String observacion, Long idSolicitud, Long idCliente, Long idUsuario) throws MiException {

        Solicitud solicitud = new Solicitud();
        Optional<Solicitud> sol = solicitudRepositorio.findById(idSolicitud);
        if (sol.isPresent()) {
            solicitud = sol.get();
        }

        Cliente cliente = new Cliente();
        Optional<Cliente> cte = clienteRepositorio.findById(idCliente);
        if (cte.isPresent()) {
            cliente = cte.get();
        }

        Admin usuario = new Admin();
        Optional<Admin> user = adminRepositorio.findById(idUsuario);
        if (user.isPresent()) {
            usuario = user.get();
        }

        ServicioEjecutado servicio = new ServicioEjecutado();

        servicio.setTipoServicio(tipoServicio);
        servicio.setObservacion(observacion);
        servicio.setSolicitud(solicitud);
        servicio.setCliente(cliente);
        servicio.setUsuario(usuario);
        servicio.setFecha(new Date());

        servicioEjecutadoRepositorio.save(servicio);

    }

    public ArrayList<ServicioEjecutado> buscarEjecutados() {

        ArrayList<ServicioEjecutado> listaEjecutados = new ArrayList();

        listaEjecutados = (ArrayList<ServicioEjecutado>) servicioEjecutadoRepositorio.findAll();

        return listaEjecutados;
    }

}
