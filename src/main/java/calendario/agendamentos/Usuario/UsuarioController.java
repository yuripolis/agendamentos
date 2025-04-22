package calendario.agendamentos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import calendario.agendamentos.TipoUsuario.TipoUsuarioRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/administracao/usuarios")
//@PreAuthorize("hasAnyRole('ROLE_GERENCIAL', 'ROLE_ADMIN')")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    
    private final Path uploadDirectory = Paths.get("D:/myapp/cartaDeServico/documentos");
    
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;
    
    @Autowired
    private UsuarioAdaptador userAdaptar;

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
        model.addAttribute("tipoUsuario", tipoUsuarioRepository.findAll());
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
        model.addAttribute("tipoUsuario", tipoUsuarioRepository.findAll());
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
    
    @GetMapping("/perfil")
    public String editProfile(Model model) {
        model.addAttribute("usuario", this.userAdaptar.obterUsuarioLogado());
        return "usuario/perfil";
    }
    
    @PostMapping("/perfil/{id}")
    public String editUserProfile(@PathVariable long id,
                                  @ModelAttribute UsuarioModel usuario,
                                  @RequestParam("profilePhotoFile") MultipartFile profilePhotoFile) {
    	
        String uploadDirectory = "D:/myapp/cartaDeServico/documentos"; 

        if (!profilePhotoFile.isEmpty()) {
            try {
                String fileName = profilePhotoFile.getOriginalFilename();
                File destinationFile = new File(uploadDirectory + File.separator + fileName);

                profilePhotoFile.transferTo(destinationFile);

                usuario.setProfilePhoto(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.usuarioService.save(usuario);

        return "redirect:/administracao/home";  
    }
    
    @GetMapping("/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getProfilePhoto(@PathVariable String filename) {
        try {
            Path filePath = uploadDirectory.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}
