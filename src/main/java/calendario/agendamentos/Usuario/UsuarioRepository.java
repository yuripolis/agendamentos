package calendario.agendamentos.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
	
	public UsuarioModel findByusername(String username);
	
}