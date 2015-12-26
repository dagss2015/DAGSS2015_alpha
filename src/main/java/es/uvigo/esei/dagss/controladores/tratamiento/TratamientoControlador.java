/*
 Proyecto Java EE, DAGSS-2013
 */
package es.uvigo.esei.dagss.controladores.tratamiento;

import es.uvigo.esei.dagss.dominio.daos.RecetaDAO;
import es.uvigo.esei.dagss.dominio.daos.TratamientoDAO;
import es.uvigo.esei.dagss.dominio.entidades.EstadoReceta;
import es.uvigo.esei.dagss.dominio.entidades.Prescripcion;
import es.uvigo.esei.dagss.dominio.entidades.Receta;
import es.uvigo.esei.dagss.dominio.entidades.Tratamiento;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author labkode
 */

@Named(value = "tratamientoControlador")
@SessionScoped
public class TratamientoControlador implements Serializable {

    @EJB
    private TratamientoDAO tratamientoDAO;
    
    @EJB
    private RecetaDAO recetaDAO;
   
    public void procesar(Tratamiento t) {
     
        this.tratamientoDAO.crear(t);
        
        // Calcular numero de receteas en base al envase del medicamento
        // Suponemos que dosis se aplica para cada dia
        // Ej: del dia 1 de enero al 20 te tomas 1 aspirina por la mañana
        // Suponiendo que las aspirinas viene en envases de 10, las recetas 
        // generadas serían:
        // - 1 al 10
        // - 11 al 20
        // Siguiento el consejo de Fran los tratamientos solo tienen una 
        // prescripcion.
        Calendar cal = Calendar.getInstance();
        cal.setTime(t.getFechaFin());
        long fin = cal.getTimeInMillis();
        cal.setTime(t.getFechaInicio());
        long inicio = cal.getTimeInMillis(); 
        
        long diff = fin - inicio;
        long dias = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        
        Prescripcion pres = t.getPrescipciones().get(0);
        long dosisPorDia = pres.getDosis();
        long dosisTotales = dias * dosisPorDia;
        
        long capacidadEnvase = pres.getMedicamento().getNumeroDosis();
        double numeroEnvases = Math.ceil(dosisTotales / capacidadEnvase);
        
        // Ahora que tenemos los envases que necesitamos debemos racionarlos
        // a lo largo del calendario segun la fecha de caducidad del medicament
        /*
        for(int i = 0; i < numeroRecetas; i++) {
            long fechaIni = 
            Receta r = new Receta(t.g prescripcion, Integer cantidad, Date inicioValidez, Date finValidez, EstadoReceta estadoReceta) 

        }
        */
        
        Receta r = new Receta(pres, (int)(numeroEnvases), t.getFechaInicio(), t.getFechaFin(), EstadoReceta.GENERADA);
        this.recetaDAO.crear(r);

        // TODO calcular recetas necesarias y crearlas en DB
    }

}
