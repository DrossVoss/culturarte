
package culturarte.casosuso;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="seguidor_id")
    private Usuario seguidor;

    @ManyToOne
    @JoinColumn(name="seguido_id")
    private Usuario seguido;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Usuario getSeguidor() { return seguidor; }
    public void setSeguidor(Usuario seguidor) { this.seguidor = seguidor; }
    public Usuario getSeguido() { return seguido; }
    public void setSeguido(Usuario seguido) { this.seguido = seguido; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
