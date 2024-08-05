package calendario.agendamentos.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import calendario.agendamentos.Usuario.UsuarioAdaptador;
import calendario.agendamentos.Usuario.UsuarioModel;

@Controller
public class LoginController {
	
	@Autowired
	private UsuarioAdaptador usuarioAdaptador;
	
	
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	
	
	@GetMapping("/administracao/home")
	public String home(Model model){
		
		UsuarioModel usuario = usuarioAdaptador.obterUsuarioLogado();
		model.addAttribute("usuario", usuario);
		if( passwordEncoder().matches("123456", usuario.getPassword())) {
			model.addAttribute("resetaSenha", true);
		}
		
		return "home";
	}
	
	 public static PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

}
