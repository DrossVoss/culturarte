
package culturarte.casosuso;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    private String id;

    @Column(name="apodo", nullable=false, unique=true, length=50)
    private String nickname;

    @Column(nullable=false, length=100)
    private String nombre;

    @Column(nullable=false, length=100)
    private String apellido;

    @Column(nullable=false, unique=true, length=150)
    private String email;

    @Column(name="fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(nullable=false)
    private String tipo;

    @Column(name="creado_en")
    private java.time.LocalDateTime creadoEn;
 // PROPONENTE or COLABORADOR

    @Transient
    private String direccion;

    @Transient
    private String biografia;

    @Transient
    private String web;

    @Transient
    private String imgPath;

    public Usuario() {}

    // getters and setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }

    public String getWeb() { return web; }
    public void setWeb(String web) { this.web = web; }

    public String getImgPath() { return imgPath; }
    public void setImgPath(String imgPath) { this.imgPath = imgPath; }
    public java.time.LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(java.time.LocalDateTime creadoEn) { this.creadoEn = creadoEn; }

}
