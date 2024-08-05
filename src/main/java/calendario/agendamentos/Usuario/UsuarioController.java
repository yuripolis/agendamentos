package calendario.agendamentos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/administracao/usuarios")
//@PreAuthorize("hasAnyRole('ROLE_GERENCIAL', 'ROLE_ADMIN')")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listUsuarios(Model model) {
        List<UsuarioModel> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuario/listaUsuario";
    }

    @GetMapping("/add")
    public String showAddUsuarioForm(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        model.addAttribute("groups", usuarioService.findAllGroups());
        return "usuario/formularioUsuario";
    }

    @PostMapping("/add")
    public String addUsuario(@ModelAttribute UsuarioModel usuario) {
    	usuarioService.setPassword(usuario, "123456");
    	usuarioService.save(usuario);
    	
        return "redirect:/administracao/usuarios";
    }

    @GetMapping("/edit/{id}")
    public String showEditUsuarioForm(@PathVariable("id") long id, Model model) {
        UsuarioModel usuario = usuarioService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("usuario", usuario);
        model.addAttribute("groups", usuarioService.findAllGroups());
        return "usuario/formularioUsuario";
    }
    
    
    @GetMapping("/editSenha/{id}")
    public String showResetSenha(@PathVariable("id") long id, Model model) {
        UsuarioModel usuario = usuarioService.findById(id).orElseThrow(() -> new IllegalArgumentException("Id de usuario invalido:" + id));
        model.addAttribute("usuario", usuario);
        model.addAttribute("groups", usuarioService.findAllGroups());
        return "usuario/formularioResetSenha";
    }
    
    
    @GetMapping("/resetSenha/{id}")
    public String resetSenha(@PathVariable("id") long id) {
        UsuarioModel usuario = usuarioService.findById(id).orElseThrow(() -> new IllegalArgumentException("Id de usuario invalido:" + id));
        usuarioService.setPassword(usuario, "123456");
    	usuarioService.save(usuario);
    	
        return "redirect:/administracao/usuarios";
    }
    
    
    @PostMapping("/editSenha/{id}")
    public String editUsuarioSenha(@PathVariable("id") long id, String senha) {
    	UsuarioModel usuario = usuarioService.findById(id).orElseThrow(() -> new IllegalArgumentException("Id de usuario invalido:" + id));
    	usuarioService.setPassword(usuario, senha);
        return "redirect:/administracao/usuarios";
    }

    @PostMapping("/edit/{id}")
    public String editUsuario(@PathVariable("id") long id, @ModelAttribute UsuarioModel usuario) {
    	Optional<UsuarioModel> existingUsuario = usuarioService.findById(id);
        if (existingUsuario != null) {
            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                usuario.setSenha(existingUsuario.get().getPassword());
            } else {
            	usuarioService.setPassword(usuario,usuario.getPassword());
            }
        }
        usuarioService.save(usuario);
        return "redirect:/administracao/usuarios";
    }

    @GetMapping("/delete/{id}")
    public String deleteUsuario(@PathVariable("id") long id) {
        usuarioService.deleteById(id);
        return "redirect:/administracao/usuarios";
    }
    
}
