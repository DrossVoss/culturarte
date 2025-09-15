
package culturarte.casosuso;

import javax.persistence.*;

@Entity
@Table(name = "biografias")
public class Biografia {
    @Id
    @Column(name="usuario_id", length=10)
    private String usuarioId;

    @Column(name="texto", columnDefinition="TEXT")
    private String texto;

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
}
