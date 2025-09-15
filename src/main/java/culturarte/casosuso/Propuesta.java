
package culturarte.casosuso;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "propuestas")
public class Propuesta {
    @Id
    @Column(name="ref")
    private String id;

    private String titulo;
    @Column(name="tipo_retorno")
    private String tipoRetorno;

    @Column(columnDefinition="TEXT")
    private String descripcion;

    @Column(name="imagen_url")
    private String imagenPath;

    @ManyToOne
    @JoinColumn(name="categoria_ref", referencedColumnName="ref")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name="proponente", referencedColumnName="id")
    private Usuario proponente;

    private String lugar;

    @Column(name="fecha")
    private LocalDate fechaRealizacion;

    @Column(name="precio_entrada")
    private BigDecimal precioEntrada;

    @Column(name="monto")
    private BigDecimal montoNecesario;

    @Transient
    private LocalDateTime fechaPublicacion;

    @Column(name="estado_actual")
    private String estadoActual;

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getImagenPath() { return imagenPath; }
    public void setImagenPath(String imagenPath) { this.imagenPath = imagenPath; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Usuario getProponente() { return proponente; }
    public void setProponente(Usuario proponente) { this.proponente = proponente; }
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public LocalDate getFechaRealizacion() { return fechaRealizacion; }
    public void setFechaRealizacion(LocalDate fechaRealizacion) { this.fechaRealizacion = fechaRealizacion; }
    public java.math.BigDecimal getPrecioEntrada() { return precioEntrada; }
    public void setPrecioEntrada(java.math.BigDecimal precioEntrada) { this.precioEntrada = precioEntrada; }
    public java.math.BigDecimal getMontoNecesario() { return montoNecesario; }
    public void setMontoNecesario(java.math.BigDecimal montoNecesario) { this.montoNecesario = montoNecesario; }
    public java.time.LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(java.time.LocalDateTime fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public String getEstadoActual() { return estadoActual; }
    public void setEstadoActual(String estadoActual) { this.estadoActual = estadoActual; }

    public String getTipoRetorno() { return tipoRetorno; }
    public void setTipoRetorno(String tipoRetorno) { this.tipoRetorno = tipoRetorno; }
}
