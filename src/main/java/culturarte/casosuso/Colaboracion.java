package culturarte.casosuso;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "colaboraciones")
public class Colaboracion {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "propuesta_ref", referencedColumnName = "ref")
    private Propuesta propuesta;

    @ManyToOne
    @JoinColumn(name = "colaborador", referencedColumnName = "id")
    private Usuario colaborador;

    private BigDecimal monto;

    // Table has separate DATE and TIME columns
    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private LocalTime hora;

    // Column name in DB is 'retorno_elegido'
    @Column(name = "retorno_elegido")
    private String tipoRetorno;

    // getters/setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Propuesta getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(Propuesta propuesta) {
        this.propuesta = propuesta;
    }

    public Usuario getColaborador() {
        return colaborador;
    }

    public void setColaborador(Usuario colaborador) {
        this.colaborador = colaborador;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }
}
