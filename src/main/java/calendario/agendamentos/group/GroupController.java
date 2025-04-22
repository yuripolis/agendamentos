package calendario.agendamentos.group;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import calendario.agendamentos.historicodosistema.GerenciadorDeHistorico;
import calendario.agendamentos.mensagensdosistema.Erros;
import calendario.agendamentos.mensagensdosistema.MensagensDoSistemaDAO;
import calendario.agendamentos.mensagensdosistema.Sucesso;

@Controller
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;
    
	
	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;
	
	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;
    
    @GetMapping("/administracao/groups")
    public String listGroups(Model model) {
        model.addAttribute("groups", groupRepository.findAll());
        return "group/listaGroup";
    }

    @GetMapping("/administracao/groups/add")
    public String addGroupForm(Model model) {
        model.addAttribute("group", new Group());
        return "group/formularioGroup";
    }

    @PostMapping("/administracao/groups/add")
    public String addGroup(Group group) {
        groupRepository.save(group);
        return "redirect:/administracao/groups";
    }

    @GetMapping("/administracao/groups/edit/{id}")
    public String editGroupForm(@PathVariable("id") Long id, Model model) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid group Id:" + id));
        model.addAttribute("group", group);
        return "group/formularioGroup"; // Replace with the appropriate template name
    }
    
    @RequestMapping(value = "/administracao/groups/remove/{id}", method = RequestMethod.GET)
    public String exclui(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Group> encontrado = groupRepository.findById(id);
        
        try {
            groupRepository.deleteById(id);
        } catch (Exception e) {
            erros.setRedirectOrModel(redirectAttributes);
            erros.adiciona("Não foi possível excluir o grupo. Verifique se ele está sendo utilizado em outras partes do sistema.");
            return "redirect:/administracao/groups";
        }

        historico.excluir(encontrado.get(), "Group");
        sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());

        return "redirect:/administracao/groups";
    }

    

    @PostMapping("/administracao/groups/edit/{id}")
    public String editGroup(@PathVariable("id") Long id, @ModelAttribute("group") Group group) {
        group.setId(id);
        groupRepository.save(group);
        return "redirect:/administracao/groups";
    }
}
