package calendario.agendamentos.postConstruct;

import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import calendario.agendamentos.Usuario.UsuarioModel;
import calendario.agendamentos.Usuario.UsuarioRepository;
import calendario.agendamentos.Usuario.UsuarioService;
import calendario.agendamentos.group.GroupRepository;
import calendario.agendamentos.group.GroupService;
import calendario.agendamentos.mensagensdosistema.MensagensDoSistemaDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class ExecutePostConstruct {
	
	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UsuarioRepository userRepository;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@PostConstruct
	public void atualizaBanco() {
		mensagensDoSistemaDAO.insertsParaSeremUtilizadosNoPostConstruct();
		insereGrupoSeNaoExistir();
		//insereUsuarioGrupoSeNaoExistir();
	}

	private void insereGrupoSeNaoExistir() {
		GroupService grupo = new GroupService(groupRepository);
		grupo.insertsParaSeremUtilizadosNoPostConstruct();
	}

}