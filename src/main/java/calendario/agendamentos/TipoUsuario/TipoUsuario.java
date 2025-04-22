package calendario.agendamentos.TipoUsuario;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import calendario.agendamentos.historicodosistema.GerenciadorDeHistorico;
import calendario.agendamentos.mensagensdosistema.Erros;
import calendario.agendamentos.mensagensdosistema.MensagensDoSistemaDAO;
import calendario.agendamentos.mensagensdosistema.Sucesso;
import calendario.agendamentos.urls.ListaDeURLs;


@Controller
public class TipoUsuario {
	
	
	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;
	
	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;
	
	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;
	
	@GetMapping("/administracao/lista/tipoUsuario")
	public String listaTipoUsuario(Model model) {
		model.addAttribute("tipoUsuarios", this.tipoUsuarioRepository.findAll());
		return "tipoUsuario/lista";
	}
	
	@GetMapping("/administracao/inserir/tipoUsuario")
	public String inserirTipoUsuarioFormulario(Model model) {
		return "tipoUsuario/formulario";
	}
	
	@PostMapping("/administracao/inserir/tipoUsuario")
	public String inserirTipoUsuario(@Valid TipoUsuarioModel  tipousuarioModel, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()){
			model.addAttribute("tipoUsuarioModel", tipousuarioModel);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "tipoUsuario/formulario";
		}

		tipoUsuarioRepository.save(tipousuarioModel);
		Optional<TipoUsuarioModel> encontrado = tipoUsuarioRepository.findById(tipousuarioModel.getId());
		historico.inserir(encontrado.get(), "TipoUsuario");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.TIPO_USUARIO_LISTA;
	}
	
	@RequestMapping(value = ListaDeURLs.TIPO_USUARIO_ALTERAR + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable Long id, Model model) {
		Optional<TipoUsuarioModel> tipoUsuarioModel = tipoUsuarioRepository.findById(id);
		model.addAttribute("tipoUsuario", tipoUsuarioModel.get());
		return "tipoUsuario/formulario";
	}
	
	@RequestMapping(value = ListaDeURLs.TIPO_USUARIO_ALTERAR, method = RequestMethod.POST)
	public String altera(@Valid TipoUsuarioModel tipoUsuarioModel, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("tipoUsuario", tipoUsuarioModel);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "/tipoUsuario/formulario";
		}

		Optional<TipoUsuarioModel> anterior = tipoUsuarioRepository.findById(tipoUsuarioModel.getId());
		tipoUsuarioRepository.save(tipoUsuarioModel);
		Optional<TipoUsuarioModel> atual = tipoUsuarioRepository.findById(tipoUsuarioModel.getId());
		anterior.get().setTipo(tipoUsuarioModel.getTipo());
		historico.alterar(anterior.get(), atual.get(), "TipoUsuario");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.TIPO_USUARIO_LISTA;
	}
	
	@RequestMapping(value = ListaDeURLs.TIPO_USUARIO_REMOVER+"/{id}", method = RequestMethod.GET)
	public String exclui(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
		Optional<TipoUsuarioModel> encontrado = tipoUsuarioRepository.findById(id);
		try {
			tipoUsuarioRepository.deleteById(id);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona("Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:"+ListaDeURLs.TIPO_USUARIO_LISTA;
		}
		historico.excluir(encontrado.get(), "TipoUsuario");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.TIPO_USUARIO_LISTA;
	}
	
}
