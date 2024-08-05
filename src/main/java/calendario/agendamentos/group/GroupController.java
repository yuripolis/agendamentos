package calendario.agendamentos.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

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
    
    @GetMapping("/administracao/groups/remove/{id}")
    public String deleteGroupForm(@PathVariable("id") Long id, Model model) {
    	try {
    		groupRepository.deleteById(id);
    	}catch(Exception e) {
    		
    	}
    	return "redirect:/administracao/groups";
    }
    

    @PostMapping("/administracao/groups/edit/{id}")
    public String editGroup(@PathVariable("id") Long id, @ModelAttribute("group") Group group) {
        group.setId(id);
        groupRepository.save(group);
        return "redirect:/administracao/groups";
    }
}
