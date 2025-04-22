package calendario.agendamentos.TipoUsuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TipoUsuarioModel {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long tu_id;
	
	private String tipo;
	
	public TipoUsuarioModel() {}

	public long getId() {
		return tu_id;
	}

	public void setId(long id) {
		this.tu_id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}
