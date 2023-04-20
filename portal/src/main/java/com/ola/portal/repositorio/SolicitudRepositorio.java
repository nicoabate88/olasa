
package com.ola.portal.repositorio;

import com.ola.portal.entidad.Solicitud;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepositorio extends JpaRepository<Solicitud, Long> {
    
    @Query("SELECT s FROM Solicitud s WHERE cliente_id = :id")
    public ArrayList<Solicitud> buscarSolicitudIdCte(@Param("id") Long id);
    
}
