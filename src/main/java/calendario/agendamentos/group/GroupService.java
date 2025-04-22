package calendario.agendamentos.group;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;


@Service
public class GroupService {

	private final String TABELA = Group.class.getSimpleName();

	private GroupRepository groupRepository;

	public GroupService(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	@Transactional
	public void insertsParaSeremUtilizadosNoPostConstruct() {
		criaSeNaoExistir("ROLE_VISITANTE");
		criaSeNaoExistir("ROLE_SERVICO");
		criaSeNaoExistir("ROLE_OPERACIONAL");
		criaSeNaoExistir("ROLE_GERENCIAL");
		criaSeNaoExistir("ROLE_ADMIN");
		criaSeNaoExistir("ROLE_INCLUIR");
		criaSeNaoExistir("ROLE_EDITAR");
		criaSeNaoExistir("ROLE_EXCLUIR");
	}

	@Transactional
	public List<Group> getAllGroups() {
		return groupRepository.findAll(); // Recupera todos os grupos do banco de dados
	}

	private void criaSeNaoExistir(String nome) {
		boolean existe = false;
		List<Group> lista = getAllGroups();
		for (Group prop : lista) {
			if (prop.getName().equals(nome)) {
				existe = true;
				break;
			}
		}
		if (!existe) {
			Group grupo = new Group();
			grupo.setName(nome);
			groupRepository.save(grupo);
		}
	}

	@Transactional
	public void insertsGrupoNoUsuarioPostConstruct(Long usuarioId, Long grupoId, EntityManager manager) {
		String sql = "insert into usuario_model_lista_de_grupo values(%d, %d)";
		// String sql = "INSERT INTO usuario_model_lista_de_grupo (usuario_model_id,
		// lista_de_grupo_id) VALUES (%d, %d)";
		sql = String.format(sql, usuarioId, grupoId);
		manager.createNativeQuery(sql).executeUpdate();
	}

}
