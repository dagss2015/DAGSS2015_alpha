/*
 Proyecto Java EE, DAGSS-2013
 */
package es.uvigo.esei.dagss.controladores.farmacia;

import es.uvigo.esei.dagss.controladores.autenticacion.AutenticacionControlador;
import es.uvigo.esei.dagss.dominio.daos.FarmaciaDAO;
import es.uvigo.esei.dagss.dominio.daos.MedicamentoDAO;
import es.uvigo.esei.dagss.dominio.daos.MedicoDAO;
import es.uvigo.esei.dagss.dominio.daos.PacienteDAO;
import es.uvigo.esei.dagss.dominio.daos.PrescripcionDAO;
import es.uvigo.esei.dagss.dominio.daos.RecetaDAO;
import es.uvigo.esei.dagss.dominio.daos.TratamientoDAO;
import es.uvigo.esei.dagss.dominio.entidades.EstadoReceta;
import es.uvigo.esei.dagss.dominio.entidades.Farmacia;
import es.uvigo.esei.dagss.dominio.entidades.Paciente;
import es.uvigo.esei.dagss.dominio.entidades.Prescripcion;
import es.uvigo.esei.dagss.dominio.entidades.Receta;
import es.uvigo.esei.dagss.dominio.entidades.TipoUsuario;
import es.uvigo.esei.dagss.dominio.entidades.Tratamiento;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author ribadas
 */
@Named(value = "farmaciaControlador")
@SessionScoped
public class FarmaciaControlador implements Serializable {

    private Farmacia farmaciaActual;
    private String nif;
    private String password;
    
    private Paciente pacienteActual;
    private String numeroPaciente;
    List<Receta> recetasDePaciente;

    public List<Receta> getRecetasDePaciente() {
        return recetasDePaciente;
    }

    public void setRecetasDePaciente(List<Receta> recetasDePaciente) {
        this.recetasDePaciente = recetasDePaciente;
    }
    private Receta recetaActual;            

    public Receta getRecetaActual() {
        return recetaActual;
    }

    public void setRecetaActual(Receta recetaActual) {
        this.recetaActual = recetaActual;
    }


    public String getNumeroPaciente() {
        return numeroPaciente;
    }

    public void setNumeroPaciente(String numeroPaciente) {
        this.numeroPaciente = numeroPaciente;
    }

    public Paciente getPacienteActual() {
        return pacienteActual;
    }

    public void setPacienteActual(Paciente pacienteActual) {
        this.pacienteActual = pacienteActual;
    }
    public void doPagarReceta(Receta r) {
        r.setEstado(EstadoReceta.SERVIDA);
        recetaDAO.actualizar(r);
        this.recetasDePaciente = this.recetaDAO.buscarPorPaciente(this.pacienteActual.getNumeroTarjetaSanitaria());
    }

    @Inject
    private AutenticacionControlador autenticacionControlador;

    @EJB
    private FarmaciaDAO farmaciaDAO;
    
    @EJB 
    private PacienteDAO pacienteDAO;
    
    @EJB
    private TratamientoDAO tratamientoDAO;

    
    @EJB
    private PrescripcionDAO prescripcionDAO;
    
    @EJB
    private MedicoDAO medicoDAO;
    
    @EJB
    private MedicamentoDAO medicamentoDAO;
     
    @EJB
    private RecetaDAO recetaDAO;
    
    
    
     @PostConstruct
    public void inicializar() {
        pacienteActual = new Paciente();
    }
    /**
     * Creates a new instance of AdministradorControlador
     */
    public FarmaciaControlador() {
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Farmacia getFarmaciaActual() {
        return farmaciaActual;
    }

    public void setFarmaciaActual(Farmacia farmaciaActual) {
        this.farmaciaActual = farmaciaActual;
    }

    private boolean parametrosAccesoInvalidos() {
        return ((nif == null) || (password == null));
    }

    public String doLogin() {
        String destino = null;
        if (parametrosAccesoInvalidos()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha indicado un nif o una contraseña", ""));
        } else {
            Farmacia farmacia = farmaciaDAO.buscarPorNIF(nif);
            if (farmacia == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No existe una farmacia con el NIF " + nif, ""));
            } else {
                if (autenticacionControlador.autenticarUsuario(farmacia.getId(), password,
                        TipoUsuario.FARMACIA.getEtiqueta().toLowerCase())) {
                    farmaciaActual = farmacia;
                    destino = "privado/index";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Credenciales de acceso incorrectas", ""));
                }

            }
        }
        return destino;
    }
    
    public String doBuscarPaciente() {
       Paciente p = pacienteDAO.buscarPorTarjetaSanitaria(this.numeroPaciente);
       
       
       
       
       if (p == null) {
           return "";
       }
       
       Calendar dateIni = new GregorianCalendar();
       dateIni.set(2015, 10, 5);
       
       Calendar dateFin = new GregorianCalendar();
       dateFin.set(2016, 11, 15);
       
       this.setPacienteActual(p);
    
       //ToDo THIS IS SHIT REMOVE 
       this.tratamientoDAO.crear(new Tratamiento(pacienteActual,
               medicoDAO.buscarPorDNI("11111111A"), "esto es a machete", 
               dateIni.getTime(), dateFin.getTime()));
       
       Prescripcion pre = this.prescripcionDAO.crear(
               new Prescripcion("consumir moderadamente",
                       tratamientoDAO.buscarPorId(7L), 
                       medicamentoDAO.buscarPorId(1L),2));
       this.recetaDAO.crear( new Receta(pre, 10, dateIni.getTime(), dateFin.getTime(), EstadoReceta.GENERADA));
       // END SHIT
       List<Receta> recetas = this.recetaDAO.buscarPorPaciente(p.getNumeroTarjetaSanitaria());
       this.recetasDePaciente = recetas;
       
       return "ver_paciente";
    }
    
    public String doCancelarBuscarPaciente() {
        return "";
    }
}
