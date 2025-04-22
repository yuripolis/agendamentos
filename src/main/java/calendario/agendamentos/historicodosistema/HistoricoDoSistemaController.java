package calendario.agendamentos.historicodosistema;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import calendario.agendamentos.urls.ListaDeURLs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;

@Controller
public class HistoricoDoSistemaController {


	@Autowired
	private HistoricoDoSistemaDAO historicoDoSistemaDAO;

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = ListaDeURLs.LISTA_DE_HISTORICODOSISTEMA, method = RequestMethod.GET)
	public String lista(Model model) {
		model.addAttribute("listaDeHistoricoDoSistema", historicoDoSistemaDAO.listaTudo());
		return "/historicodosistema/lista";
	}

}