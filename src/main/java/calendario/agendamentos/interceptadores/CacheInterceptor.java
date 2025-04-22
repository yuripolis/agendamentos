package calendario.agendamentos.interceptadores;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import calendario.agendamentos.mensagensdosistema.MensagensDoSistemaDAO;
import calendario.agendamentos.mensagensdosistema.MensagensDoSistemaModel;


@Component
public class CacheInterceptor extends HandlerInterceptorAdapter {


	@Autowired
	private MensagensDoSistemaDAO mensagemDAO;

	//@Autowired
	//private ConfiguracoesDoSistemaDAO configuracoesDAO;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			
			Map<String, MensagensDoSistemaModel> mensagens  = mensagemDAO.listaTudoComCache();
			modelAndView.addObject("mensagens", mensagemDAO.listaTudoComCache());
			//modelAndView.addObject("configuracoes", configuracoesDAO.listaTudoComCache());
		}
		super.postHandle(request, response, handler, modelAndView);
	}
}
