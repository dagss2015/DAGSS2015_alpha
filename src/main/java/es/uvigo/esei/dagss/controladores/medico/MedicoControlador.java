/*
 Proyecto Java EE, DAGSS-2013
 */
package es.uvigo.esei.dagss.controladores.medico;

import es.uvigo.esei.dagss.controladores.autenticacion.AutenticacionControlador;
import es.uvigo.esei.dagss.controladores.tratamiento.TratamientoControlador;
import es.uvigo.esei.dagss.dominio.daos.CitaDAO;
import es.uvigo.esei.dagss.dominio.daos.FarmaciaDAO;
import es.uvigo.esei.dagss.dominio.daos.MedicamentoDAO;
import es.uvigo.esei.dagss.dominio.daos.MedicoDAO;
import es.uvigo.esei.dagss.dominio.daos.TratamientoDAO;
import es.uvigo.esei.dagss.dominio.entidades.Cita;
import es.uvigo.esei.dagss.dominio.entidades.EstadoCita;
import es.uvigo.esei.dagss.dominio.entidades.Medicamento;
import es.uvigo.esei.dagss.dominio.entidades.Medico;
import es.uvigo.esei.dagss.dominio.entidades.Paciente;
import es.uvigo.esei.dagss.dominio.entidades.Prescripcion;
import es.uvigo.esei.dagss.dominio.entidades.Receta;
import es.uvigo.esei.dagss.dominio.entidades.TipoUsuario;
import es.uvigo.esei.dagss.dominio.entidades.Tratamiento;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author ribadas
 */

@Named(value = "medicoControlador")
@SessionScoped
public class MedicoControlador implements Serializable {


   

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
       this.dosis = dosis;
    }
    
    public List<Tratamiento> getListaDeTratamientos() {
        return listaDeTratamientos;
    }

    public void setListaDeTratamientos(List<Tratamiento> listaDeTratamientos) {
        this.listaDeTratamientos = listaDeTratamientos;
    }
    
    // Para el medico
    private Medico medicoActual;
    private String dni;
    private String numeroColegiado;
    private String password;
    
    // Para el listado de tratamientos
    List<Tratamiento> listaDeTratamientos;
    Prescripcion prescripcionActual;
    Tratamiento tratamientoActual;
    
   
    
    
    Date fechaInicio;
    Date fechaFin;

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    String comentario;
    String dosis;
    
    // Para citas
    List<Cita> citasDelMedico;
    Cita citaActual;
    
    // Para medicamentos
    List<Medicamento> listaDeMedicamentos;
    String medicamentoABuscar;
    Medicamento medicamentoSeleccionado;
    
    public String doCambiarEstadoCitaCompletada() {
        this.citaActual.setEstado(EstadoCita.COMPLETADA);
        this.citaActual = this.citaDAO.actualizar(citaActual);
        return "";
    } 
    public String doCambiarEstadoCitaAusente() {
        this.citaActual.setEstado(EstadoCita.AUSENTE);
        this.citaActual = this.citaDAO.actualizar(citaActual);
        return "";
    } 
    public String doEliminarTratamiento(Tratamiento t) {
        this.tratamientoDAO.eliminar(t);
        this.actualizarTratamientos();
        return "";
    }
    
    public String doGuardarEditado() {
        this.tratamientoDAO.actualizar(this.tratamientoActual);
        this.actualizarTratamientos();
        return "";
    }
    
    public String doCrearTratamiento() {
        this.tratamientoActual = new Tratamiento(
                this.citaActual.getPaciente(),
                this.medicoActual,
                this.comentario,
                this.fechaInicio,
                this.fechaFin);
        System.out.println(this.tratamientoActual);
        
        this.prescripcionActual = new Prescripcion(this.comentario,
                this.tratamientoActual, this.medicamentoSeleccionado, Integer.parseInt(this.dosis));
        
        List<Prescripcion> lst = new ArrayList<>();
        lst.add(this.prescripcionActual);
        this.tratamientoActual.setPrescipciones(lst);
        
        this.prescripcionActual.setTratamiento(this.tratamientoActual);
        
        //this.tratamientoDAO.crear(this.tratamientoActual);
        this.tratamientoControlador.procesar(this.tratamientoActual);
        
        this.actualizarTratamientos();
        
        return "";
    }

    public String doShowFormularioTratamiento(Medicamento m) {
        this.medicamentoSeleccionado = m;
        return "";
    }
    public String doBuscarMedicamento() {
        List<Medicamento> lst;
        lst = medicamentoDAO.buscarPorNombre(this.medicamentoABuscar);
        this.listaDeMedicamentos = lst;
        System.err.println(lst);
        return "";
    }
    public List<Medicamento> getListaDeMedicamentos() {
        return listaDeMedicamentos;
    }

    public void setListaDeMedicamentos(List<Medicamento> listaDeMedicamentos) {
        this.listaDeMedicamentos = listaDeMedicamentos;
    }

    public String getMedicamentoABuscar() {
        return medicamentoABuscar;
    }

    public void setMedicamentoABuscar(String medicamentoABuscar) {
        this.medicamentoABuscar = medicamentoABuscar;
    }

    public Medicamento getMedicamentoSeleccionado() {
        return medicamentoSeleccionado;
    }

    public void setMedicamentoSeleccionado(Medicamento medicamentoSeleccionado) {
        this.medicamentoSeleccionado = medicamentoSeleccionado;
    }

    public Tratamiento getTratamientoActual() {
        return tratamientoActual;
    }

    public void setTratamientoActual(Tratamiento tratamientoActual) {
        this.tratamientoActual = tratamientoActual;
    }
    
   
    
    public Prescripcion getPrescripcionActual() {
        return prescripcionActual;
    }

    public void setPrescripcionActual(Prescripcion prescripcionActual) {
        this.prescripcionActual = prescripcionActual;
    }
   

    public Cita getCitaActual() {
        return citaActual;
    }

    public void setCitaActual(Cita citaActual) {
        this.citaActual = citaActual;
    }
    

    public List<Cita> getCitasDelMedico() {
        return citasDelMedico;
    }

    public void setCitasDelMedico(List<Cita> citasDelMedico) {
        this.citasDelMedico = citasDelMedico;
    }
    
    private void actualizarCitas() {
        citasDelMedico = citaDAO.buscarCitasPorMedico(this.medicoActual.getNumeroColegiado());
    }
    
    @Inject
    private AutenticacionControlador autenticacionControlador;
    

    @EJB
    private MedicoDAO medicoDAO;
    @EJB
    private MedicamentoDAO medicamentoDAO;
    @EJB
    private CitaDAO citaDAO;
    
    @EJB
    TratamientoDAO tratamientoDAO;
    
    @Inject
    private TratamientoControlador tratamientoControlador;

    /**
     * Creates a new instance of AdministradorControlador
     */
    public MedicoControlador() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNumeroColegiado() {
        return numeroColegiado;
    }

    public void setNumeroColegiado(String numeroColegiado) {
        this.numeroColegiado = numeroColegiado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Medico getMedicoActual() {
        return medicoActual;
    }

    public void setMedicoActual(Medico medicoActual) {
        this.medicoActual = medicoActual;
    }

    private boolean parametrosAccesoInvalidos() {
        return (((dni == null) && (numeroColegiado == null)) || (password == null));
    }

    private Medico recuperarDatosMedico() {
        Medico medico = null;
        if (dni != null) {
            medico = medicoDAO.buscarPorDNI(dni);
        }
        if ((medico == null) && (numeroColegiado != null)) {
            medico = medicoDAO.buscarPorNumeroColegiado(numeroColegiado);
        }
        return medico;
    }

    public String doLogin() {
        String destino = null;
        if (parametrosAccesoInvalidos()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha indicado un DNI ó un número de colegiado o una contraseña", ""));
        } else {
            Medico medico = recuperarDatosMedico();
            if (medico == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No existe ningún médico con los datos indicados", ""));
            } else {
                if (autenticacionControlador.autenticarUsuario(medico.getId(), password,
                        TipoUsuario.MEDICO.getEtiqueta().toLowerCase())) {
                    medicoActual = medico;
                    this.actualizarCitas();
                    destino = "privado/index";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Credenciales de acceso incorrectas", ""));
                }
            }
        }
        return destino;
    }

    //Acciones
    public String doShowCita(Cita c) {
        this.citaActual = c;
        this.actualizarTratamientos();
        return "detallesCita";
    }
    
    private void actualizarTratamientos() {
        this.listaDeTratamientos = this.tratamientoDAO.buscarTodos();
        this.medicamentoABuscar = "";
        this.listaDeMedicamentos = null;
        this.medicamentoSeleccionado = null;
        this.tratamientoActual = null;
        this.prescripcionActual = null;
    }
}
