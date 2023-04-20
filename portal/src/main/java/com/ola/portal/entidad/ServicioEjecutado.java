
package com.ola.portal.entidad;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class ServicioEjecutado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoServicio;
    private String observacion;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @OneToOne
    private Solicitud solicitud;
    @OneToOne
    private Cliente cliente;
    @OneToOne
    private Admin usuario;

    public ServicioEjecutado() {
    }

    public ServicioEjecutado(Long id, String tipoServicio, String observacion, Date fecha, Solicitud solicitud, Cliente cliente, Admin usuario) {
        this.id = id;
        this.tipoServicio = tipoServicio;
        this.observacion = observacion;
        this.fecha = fecha;
        this.solicitud = solicitud;
        this.cliente = cliente;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Admin getUsuario() {
        return usuario;
    }

    public void setUsuario(Admin usuario) {
        this.usuario = usuario;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    
    
    
    
    
    
    
}
