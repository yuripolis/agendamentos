package calendario.agendamentos.mensagensdosistema;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import calendario.agendamentos.historicodosistema.GerenciadorDeHistorico;
import calendario.agendamentos.urls.ListaDeURLs;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.security.access.annotation.Secured;
import javax.validation.Valid;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class MensagensDoSistemaController {

	@Autowired
	private Erros erros;

	@Autowired
	private Sucesso sucesso;

	@Autowired
	private MensagensDoSistemaDAO mensagensDoSistemaDAO;

	@Autowired
	private GerenciadorDeHistorico historico;

	@RequestMapping(value = ListaDeURLs.LISTA_DE_MENSAGENSDOSISTEMA, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeMensagensDoSistema", mensagensDoSistemaDAO.listaTudoSemCache());
		return "/mensagensdosistema/lista";
	}

	@Secured({ "ROLE_INSERIR" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_INSERCAO_DE_MENSAGENSDOSISTEMA, method = RequestMethod.GET)
	public String carregaFormularioParaInsercao(Model model) {
		return "/mensagensdosistema/formulario";
	}

	@Secured({ "ROLE_EDITAR" })
	@RequestMapping(value = ListaDeURLs.FORMULARIO_EDICAO_DE_MENSAGENSDOSISTEMA + "/{id}", method = RequestMethod.GET)
	public String carregaFormularioParaEdicao(@PathVariable String id, Model model) {
		MensagensDoSistemaModel mensagensDoSistema = mensagensDoSistemaDAO.buscaPorPropriedadeSemCache(id);
		model.addAttribute("mensagensDoSistema", mensagensDoSistema);
		return "/mensagensdosistema/formulario";
	}

	@Secured({ "ROLE_INSERIR" })
	@RequestMapping(value = ListaDeURLs.INSERCAO_DE_MENSAGENSDOSISTEMA, method = RequestMethod.POST)
	public String insere(@Valid MensagensDoSistemaModel mensagensDoSistema, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("mensagensDoSistema", mensagensDoSistema);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "/mensagensDoSistema/formulario";
		}

		mensagensDoSistemaDAO.insere(mensagensDoSistema);
		MensagensDoSistemaModel encontrado = mensagensDoSistemaDAO.buscaPorPropriedade(mensagensDoSistema.getPropriedade());
		historico.inserir(encontrado, "Mensagens do sistema");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAdicionadoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_MENSAGENSDOSISTEMA;
	}

	@Secured({ "ROLE_EDITAR" })
	@RequestMapping(value = ListaDeURLs.EDICAO_DE_MENSAGENSDOSISTEMA, method = RequestMethod.POST)
	public String altera(@Valid MensagensDoSistemaModel mensagensDoSistema, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if(bindingResult.hasErrors()){
			model.addAttribute("mensagensDoSistema", mensagensDoSistema);
			erros.setRedirectOrModel(model);
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				erros.adiciona(mensagensDoSistemaDAO.buscaPorError(objectError));
			}
			return "/mensagensdosistema/formulario";
		}

		MensagensDoSistemaModel anterior = mensagensDoSistemaDAO.buscaPorPropriedadeClonando(mensagensDoSistema.getPropriedade());
		mensagensDoSistemaDAO.altera(mensagensDoSistema);
		MensagensDoSistemaModel atual = mensagensDoSistemaDAO.buscaPorPropriedadeClonando(mensagensDoSistema.getPropriedade());
		anterior.setValor(mensagensDoSistema.getValor());
		anterior.setTela(mensagensDoSistema.getTela());
		historico.alterar(anterior, atual, "Mensagens do sistema");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemAlteradoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_MENSAGENSDOSISTEMA;
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.EXCLUSAO_DE_MENSAGENSDOSISTEMA, method = RequestMethod.POST)
	public String exclui(@Valid MensagensDoSistemaModel mensagensDoSistema, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		MensagensDoSistemaModel encontrado = mensagensDoSistemaDAO.buscaPorPropriedadeClonando(mensagensDoSistema.getPropriedade());
		try {
			mensagensDoSistemaDAO.exclui(encontrado);
		} catch (Exception e) {
			erros.setRedirectOrModel(redirectAttributes);
			erros.adiciona("Não foi possível excluir o registro. Verificar se o registro está sendo utilizado em outras partes do sistema.");
			return "redirect:"+ListaDeURLs.LISTA_DE_MENSAGENSDOSISTEMA;
		}
		historico.excluir(encontrado, "Mensagens do sistema");
		sucesso.setMensagem(redirectAttributes, mensagensDoSistemaDAO.buscaPorPropriedade("MensagemExcluidoComSucesso").getValor());
		return "redirect:"+ListaDeURLs.LISTA_DE_MENSAGENSDOSISTEMA;
	}

}