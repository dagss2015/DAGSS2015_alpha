/*
 Proyecto Java EE, DAGSS-2014
 */

package es.uvigo.esei.dagss.dominio.daos;

import es.uvigo.esei.dagss.dominio.entidades.Cita;
import es.uvigo.esei.dagss.dominio.entidades.Receta;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;


@Stateless
@LocalBean
public class CitaDAO  extends GenericoDAO<Cita>{    

    // Completar aqui
     public List<Cita> buscarCitasPorMedico(String colegiado) {
        Query q = em.createQuery("SELECT c FROM Cita AS c "
                + "  WHERE c.medico.numeroColegiado = :numero");
        q.setParameter("numero", colegiado);
       return q.getResultList();
    }
}
