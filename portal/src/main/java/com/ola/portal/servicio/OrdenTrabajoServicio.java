
package com.ola.portal.servicio;

import com.ola.portal.entidad.Admin;
import com.ola.portal.entidad.Cliente;
import com.ola.portal.entidad.OrdenTrabajo;
import com.ola.portal.entidad.Solicitud;
import com.ola.portal.excepcion.MiException;
import com.ola.portal.repositorio.AdminRepositorio;
import com.ola.portal.repositorio.ClienteRepositorio;
import com.ola.portal.repositorio.OrdenTrabajoRepositorio;
import com.ola.portal.repositorio.SolicitudRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenTrabajoServicio {

    @Autowired
    private OrdenTrabajoRepositorio ordenRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private AdminRepositorio adminRepositorio;

    @Autowired
    private SolicitudRepositorio solicitudRepositorio;

    @Transactional
    public void crearOrdenTrabajo(String tipoServicio, String observacion, String fecha, Long idSolicitud, Long idCliente, Long idUsuario) throws MiException, ParseException {

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

        Date fechaOrden = convertirFecha(fecha);

        OrdenTrabajo ot = new OrdenTrabajo();

        ot.setTipoServicio(tipoServicio);
        ot.setObservacion(observacion);
        ot.setSolicitud(solicitud);
        ot.setCliente(cliente);
        ot.setUsuario(usuario);
        ot.setFecha(fechaOrden);

        ordenRepositorio.save(ot);

    }
    
    public OrdenTrabajo buscarOrden(Long id){
        
        return ordenRepositorio.getById(id);
        
    }

    public ArrayList<OrdenTrabajo> buscarOrdenesTrabajo() {

        ArrayList<OrdenTrabajo> listaOrdenes = new ArrayList();

        listaOrdenes = (ArrayList<OrdenTrabajo>) ordenRepositorio.findAll();

        return listaOrdenes;
    }

    public Date convertirFecha(String fecha) throws ParseException { //convierte fecha String a fecha Date
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.parse(fecha);
    }

}
