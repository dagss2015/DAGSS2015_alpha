/*
 Proyecto Java EE, DAGSS-2014
 */

package es.uvigo.esei.dagss.dominio.daos;

import es.uvigo.esei.dagss.dominio.entidades.Receta;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@LocalBean
public class RecetaDAO extends GenericoDAO<Receta>{
 
    // Completar aqui
    
    public List<Receta> buscarPorPaciente(String tarjetaSanitaria) {
        Query q = em.createQuery("SELECT r FROM Receta AS r "
                + "  WHERE r.prescripcion.tratamiento.paciente.numeroTarjetaSanitaria = :tarjetaSanitaria");
        q.setParameter("tarjetaSanitaria", tarjetaSanitaria);
       return q.getResultList();
    }
}
