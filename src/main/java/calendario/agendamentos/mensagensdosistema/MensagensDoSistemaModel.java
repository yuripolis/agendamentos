package calendario.agendamentos.mensagensdosistema;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MENSAGENSDOSISTEMA")
public class MensagensDoSistemaModel implements Serializable  {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMensagensDoSistemaPropriedade")
	@Id
	@Column(length = 255)
	private String propriedade;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMensagensDoSistemaValor")
	@Column(length = 3990)
	private String valor;

	@NotNull(message = "ValidacaoErroPreenchimentoObrigatorioMensagensDoSistemaTela")
	@Column(length = 255)
	private String tela;





	public String getPropriedade() {
		return propriedade;
	}

	public void setPropriedade(String propriedade) {
		this.propriedade = propriedade;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getTela() {
		return tela;
	}

	public void setTela(String tela) {
		this.tela = tela;
	}


	@Override
	public String toString() {
		return propriedade + " - " + valor;
	}
}