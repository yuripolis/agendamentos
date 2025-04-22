package calendario.agendamentos.Agenda;

import java.util.List;
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

import calendario.agendamentos.Usuario.UsuarioModel;
import calendario.agendamentos.Usuario.UsuarioService;
import calendario.agendamentos.historicodosistema.GerenciadorDeHistorico;
import calendario.agendamentos.mensagensdosistema.Erros;
import calendario.agendamentos.mensagensdosistema.MensagensDoSistemaDAO;
import calendario.agendamentos.mensagensdosistema.Sucesso;

@Controller
@RequestMapping("/administracao/usuarios/{usuarioId}/agenda")
public class UsuarioAgendaController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioAgendaRepository usuarioAgendaRepository;

    @Autowired
    private MensagensDoSistemaDAO mensagensDoSistemaDAO;

    @Autowired
    private GerenciadorDeHistorico historico;

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

    @GetMapping
    public String verAgenda(@PathVariable Long usuarioId, Model model) {
        UsuarioModel usuario = usuarioService.findById(usuarioId).orElseThrow();
        List<UsuarioAgendaModel> agenda = usuarioAgendaRepository.findByUsuarioId(usuarioId);
        model.addAttribute("usuario", usuario);
        model.addAttribute("agenda", agenda);
        return "usuarioAgenda/agenda";
    }

    @GetMapping("/adicionar")
    public String formAdicionar(@PathVariable Long usuarioId, Model model) {
        UsuarioModel usuario = usuarioService.findById(usuarioId).orElseThrow();
        UsuarioAgendaModel agenda = new UsuarioAgendaModel();
        agenda.setUsuario(usuario);
        model.addAttribute("agenda", agenda);
        return "usuarioAgenda/agendaForm";
    }

    @GetMapping("/editar/{agendaId}")
    public String formEditar(@PathVariable Long usuarioId, @PathVariable Long agendaId, Model model) {
        UsuarioAgendaModel agenda = usuarioAgendaRepository.findById(agendaId).orElseThrow();
        model.addAttribute("agenda", agenda);
        return "usuarioAgenda/agendaForm";
    }

    @PostMapping({"/adicionar", "/editar/{agendaId}"})
    public String salvarAgenda(@PathVariable Long usuarioId,
                               @ModelAttribute UsuarioAgendaModel agenda,
                               RedirectAttributes redirectAttributes) {
        try {
            UsuarioModel usuario = usuarioService.findById(usuarioId).orElseThrow();
            agenda.setUsuario(usuario);
            usuarioAgendaRepository.save(agenda);

            sucesso.setMensagem(redirectAttributes,
                mensagensDoSistemaDAO.buscaPorPropriedade("MensagemSalvoComSucesso").getValor());

            return "redirect:/administracao/usuarios/" + usuarioId + "/agenda";
        } catch (Exception e) {
            erros.setRedirectOrModel(redirectAttributes);
            erros.adiciona("Erro ao salvar agenda: " + e.getMessage());
            return "redirect:/administracao/usuarios/" + usuarioId + "/agenda";
        }
    }

    @RequestMapping(value = "/remover/{agendaId}", method = RequestMethod.GET)
    public String excluir(@PathVariable Long usuarioId,
                          @PathVariable Long agendaId,
                          RedirectAttributes redirectAttributes) {
        Optional<UsuarioAgendaModel> encontrado = usuarioAgendaRepository.findById(agendaId);
        try {
            usuarioAgendaRepository.deleteById(agendaId);
        } catch (Exception e) {
            erros.setRedirectOrModel(redirectAttributes);
            erros.adiciona("Não foi possível excluir a sessão. Verifique se ela está sendo usada em outra parte do sistema.");
            return "redirect:/administracao/usuarios/" + usuarioId + "/agenda";
        }
        historico.excluir(encontrado.get(), "AgendaUsuario");
        sucesso.setMensagem(redirectAttributes,
            mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
        return "redirect:/administracao/usuarios/" + usuarioId + "/agenda";
    }
}

