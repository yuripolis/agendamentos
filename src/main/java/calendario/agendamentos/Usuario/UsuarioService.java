package calendario.agendamentos.Usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import calendario.agendamentos.group.Group;
import calendario.agendamentos.group.GroupRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GroupRepository groupRepository;

    public List<UsuarioModel> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioModel> findById(long id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioModel save(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(long id) {
        usuarioRepository.deleteById(id);
    }

    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }
    
    public void setPassword(UsuarioModel usuario, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        usuario.setSenha(encodedPassword);
        usuarioRepository.save(usuario);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioModel usuario = null;
		try {
			usuario = usuarioRepository.findByusername(username);
		}catch(Exception e) {
			throw new UsernameNotFoundException("aplicação não encontrado!");
		}

		return usuario;
	}
}
