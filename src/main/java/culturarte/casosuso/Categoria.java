
package culturarte.casosuso;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @Column(name="ref")
    private String id;

    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="padre_id")
    private Categoria padre;

    @OneToMany(mappedBy = "padre", fetch = FetchType.LAZY)
    private List<Categoria> hijos;

    public Categoria() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Categoria getPadre() { return padre; }
    public void setPadre(Categoria padre) { this.padre = padre; }

    public List<Categoria> getHijos() { return hijos; }
    public void setHijos(List<Categoria> hijos) { this.hijos = hijos; }
}
