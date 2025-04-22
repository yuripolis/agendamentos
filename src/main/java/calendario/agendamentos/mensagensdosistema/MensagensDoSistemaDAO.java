package calendario.agendamentos.mensagensdosistema;

import javax.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import org.springframework.validation.ObjectError;
import com.google.gson.Gson;


import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class MensagensDoSistemaDAO {

	private final String TABELA = MensagensDoSistemaModel.class.getSimpleName();

	private Map<String, MensagensDoSistemaModel> mapa;

	@PersistenceContext
	private EntityManager manager;

	public MensagensDoSistemaDAO() {
	}


	
	@Transactional
	public void insere(MensagensDoSistemaModel mensagensdosistema) {
		manager.persist(mensagensdosistema);
		manager.flush();
		manager.detach(mensagensdosistema);
	}

	@Transactional
	public void altera(MensagensDoSistemaModel mensagensdosistema) {
		manager.merge(mensagensdosistema);
		manager.flush();
		manager.detach(mensagensdosistema);
	}

	@Transactional
	public void exclui(MensagensDoSistemaModel mensagensdosistema) {
		manager.remove(manager.find(MensagensDoSistemaModel.class, mensagensdosistema.getPropriedade()));
	}

	
	public String buscaPorNome(String id) {

		String jpql = "SELECT l FROM " + TABELA + " l WHERE l.propriedade = :id ";

		TypedQuery<MensagensDoSistemaModel> query = manager.createQuery(jpql, MensagensDoSistemaModel.class);

		query.setParameter("id", id);

		List<MensagensDoSistemaModel> resultado = query.getResultList();
		
		return resultado.get(0).getValor();
		
	}
	
	
	public MensagensDoSistemaModel buscaPorPropriedade(String propriedade) {

		if (mapa == null) {
			this.listaTudoComCache();
		}

		MensagensDoSistemaModel retorno = mapa.get(propriedade);

		if(retorno == null){
			retorno = new MensagensDoSistemaModel();
			retorno.setPropriedade("");
			retorno.setValor("");
			retorno.setTela("");
		}

		return retorno;
	}
	@Cacheable(value = "cacheMensagensDoSistema")
	public Map<String, MensagensDoSistemaModel> listaTudoComCache() {
		String jpql = "SELECT m FROM " + TABELA + " m ORDER BY m.id";
		TypedQuery<MensagensDoSistemaModel> query = manager.createQuery(jpql, MensagensDoSistemaModel.class);

		mapa = new HashMap<String, MensagensDoSistemaModel>();

		for (MensagensDoSistemaModel dados : query.getResultList()) {
			mapa.put(dados.getPropriedade(), dados);
		}
		return mapa;
	}

	@CacheEvict(value = "cacheMensagensDoSistema")
	public void limpaCache() {
		mapa = null;
	}

	public List<MensagensDoSistemaModel> listaTudoSemCache() {
		String jpql = "SELECT m FROM " + TABELA + " m ORDER BY m.id";
		TypedQuery<MensagensDoSistemaModel> query = manager.createQuery(jpql, MensagensDoSistemaModel.class);
		return query.getResultList();
	}
	public MensagensDoSistemaModel buscaPorPropriedadeSemCache(String id) {

		String jpql = "SELECT m FROM " + TABELA + " m WHERE m.id = :id ";

		TypedQuery<MensagensDoSistemaModel> query = manager.createQuery(jpql, MensagensDoSistemaModel.class);

		query.setParameter("id", id);

		List<MensagensDoSistemaModel> resultado = query.getResultList();

		if(resultado.size() == 0){
			return new MensagensDoSistemaModel();
		}else{
			return resultado.get(0);
		}
	}

	public MensagensDoSistemaModel buscaPorPropriedadeClonando(String id) {
		MensagensDoSistemaModel encontrado = buscaPorPropriedadeSemCache(id);
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(encontrado), MensagensDoSistemaModel.class);
	}

	public String buscaPorError(ObjectError objectError) {
		return buscaPorPropriedade(objectError.getDefaultMessage()).getValor();
	}


	@Transactional
	public void insertsParaSeremUtilizadosNoPostConstruct() {
		List<MensagensDoSistemaModel> lista = listaTudoSemCache();
		criaSeNaoExistir(lista, "NomeDoProjeto", "P3solutions", "Nome do Projeto");
		criaSeNaoExistir(lista, "SaudacaoHome", "Bem-vindo: ", "Home");
		criaSeNaoExistir(lista, "ErroLoginAutenticacao", "Usuário ou senha inválida", "Home");
		criaSeNaoExistir(lista, "ServicoFormularioId", "Código", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaId", "Código", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioNome", "Nome", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaNome", "Nome", "Lista<th class=\"text-center\">sersao de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioBrevedescricao", "Breve Descrição", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaBrevedescricao", "Breve Descrição", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioParentescoNivel", "Nível", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaParentescoNivel", "Nível", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioVersao", "Versão", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaVersao", "Versão", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioDataCriacao", "Data Criação", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaDataCriacao", "Data Criação", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioPublicado", "Publicado", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaPublicado", "Publicado", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioAprovado", "Aprovado", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaAprovado", "Aprovado", "Lista de Aprovação");
		criaSeNaoExistir(lista, "ServicoFormularioDescricaocompleta", "Descrição Completa", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioUrlServico", "URL do Serviço", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioUrlServicoDescricao", "Descrição URL do Serviço", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioTagsDeBusca", "Tags de busca", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioTagsDeBuscaExemplo", "Informar palavras chave separadas por , exemplo: 'IPTU Segunda via,IPTU Digital", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaDescricaocompleta", "Descrição Completa", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioTempodeAtendimento", "Tempo Atendimento", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaTempodeAtendimento", "Tempo Atendimento", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioTempodeResposta", "Tempo Resposta", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaTempodeResposta", "Tempo Resposta", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioAnexos", "Anexos", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaAnexos", "Anexos", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioEndereco", "Endereço de atendimento", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaEndereco", "Endereço de atendimento", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioTipoPublicacao", "Tipo Publicação", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaTipoPublicacao", "Tipo Publicação", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioFormaSolicitacao", "Forma Solicitação", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaFormaSolicitacao", "Forma Solicitação", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioProcedimentoDeExecucao", "Procedimento de Execução", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaProcedimentoDeExecucao", "Procedimento de Execução", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioObs", "Observação", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaObs", "Observação", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoMenuPrincipal", "Serviço", "Menu");
		criaSeNaoExistir(lista, "ServicoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "ServicoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "ServicoFormularioBotaoInsere", "Insere", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioBotaoAltera", "Altera", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioTituloDaPagina", "Cadastro de Serviço", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaTituloDaPagina", "Lista de Serviço", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFormularioVersoesTituloDaPagina", "Cadastro de Versão", "Formulário de Serviço");
		criaSeNaoExistir(lista, "ServicoListaVersoesTituloDaPagina", "Lista de Versões", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoListaAcoes", "Ações", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoListaSemRegistro", "Não foram encontrados registros para Serviço", "Lista de Serviço");
		criaSeNaoExistir(lista, "ServicoFilhoListaSemRegistro", "Não foram encontradas outras versões deste serviço", "Lista de Serviço");
		criaSeNaoExistir(lista, "UnidadeFormularioId", "Código", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaId", "Código", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeFormularioReferencia", "Referência", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaReferencia", "Referência", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeFormularioResponsavel", "Resonsável", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaResponsavel", "Resonsável", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeFormularioEmail", "E-mail", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaEmail", "E-mail", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeFormularioTelefone", "Telefone", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaTelefone", "Telefone", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeFormularioFax", "Fax", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaFax", "Fax", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeFormularioOutrasInformacoes", "Outras Informações", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaOutrasInformacoes", "Outras Informações", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeFormularioObservacoes", "Observações", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaObservacoes", "Observações", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeFormularioNome", "nome", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaNome", "nome", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeFormularioDescricao", "Descrição", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaDescricao", "Descrição", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeFormularioUnidadeTipo", "Tipo de Unidade", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaUnidadeTipo", "Tipo de Unidade", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeMenuPrincipal", "Unidade", "Menu");
		criaSeNaoExistir(lista, "UnidadeMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "UnidadeMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "UnidadeFormularioBotaoInsere", "Insere", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeFormularioBotaoAltera", "Altera", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeFormularioTituloDaPagina", "Cadastro de Unidade", "Formulário de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaTituloDaPagina", "Lista de Unidade", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaAcoes", "Ações", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeMensagemUsuario", "Importante: As Unidades possuem permissão por usuário. Caso não esteja visualizando, favor verificar!", "Lista de Unidade");
		criaSeNaoExistir(lista, "UnidadeListaSemRegistro", "Não foram encontrados registros para Unidade", "Lista de Unidade");

		
		
		criaSeNaoExistir(lista, "Servico_UnidadeFormularioId", "Código", "Formulário de Serviço Unidade");
		criaSeNaoExistir(lista, "Servico_UnidadeListaId", "Código", "Lista de Serviço Unidade");
		criaSeNaoExistir(lista, "Servico_UnidadeFormularioServico", "Servico", "Formulário de Serviço Unidade");
		criaSeNaoExistir(lista, "Servico_UnidadeListaServico", "Servico", "Lista de Serviço Unidade");
		criaSeNaoExistir(lista, "Servico_UnidadeFormularioUnidade", "Unidade", "Formulário de Serviço Unidade");
		criaSeNaoExistir(lista, "Servico_UnidadeListaUnidade", "Unidade", "Lista de Serviço Unidade");
		criaSeNaoExistir(lista, "Servico_UnidadeMenuPrincipal", "Serviço Unidade", "Menu");
		criaSeNaoExistir(lista, "Servico_UnidadeMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "Servico_UnidadeMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "Servico_UnidadeFormularioBotaoInsere", "Insere", "Formulário de Serviço Unidade");
		criaSeNaoExistir(lista, "Servico_UnidadeFormularioBotaoAltera", "Altera", "Formulário de Serviço Unidade");
		criaSeNaoExistir(lista, "Servico_UnidadeFormularioTituloDaPagina", "Cadastro de Serviço Unidade", "Formulário de Serviço Unidade");
		criaSeNaoExistir(lista, "Servico_UnidadeListaTituloDaPagina", "Lista de Serviço Unidade", "Lista de Serviço Unidade");
		criaSeNaoExistir(lista, "Servico_UnidadeListaAcoes", "Ações", "Lista de Serviço Unidade");
		criaSeNaoExistir(lista, "Servico_UnidadeListaSemRegistro", "Não foram encontrados registros para Serviço Unidade", "Lista de Serviço Unidade");
		
		criaSeNaoExistir(lista, "UnidadeTipoFormularioId", "Código", "Formulário de Tipo de Unidade");
		criaSeNaoExistir(lista, "UnidadeTipoListaId", "Código", "Lista de Tipo de Unidade");
		criaSeNaoExistir(lista, "UnidadeTipoFormularioDescricao", "Descrição", "Formulário de Tipo de Unidade");
		criaSeNaoExistir(lista, "UnidadeTipoListaDescricao", "Descrição", "Lista de Tipo de Unidade");
		criaSeNaoExistir(lista, "UnidadeTipoMenuPrincipal", "Tipo de Unidade", "Menu");
		criaSeNaoExistir(lista, "UnidadeTipoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "UnidadeTipoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "UnidadeTipoFormularioBotaoInsere", "Insere", "Formulário de Tipo de Unidade");
		criaSeNaoExistir(lista, "UnidadeTipoFormularioBotaoAltera", "Altera", "Formulário de Tipo de Unidade");
		criaSeNaoExistir(lista, "UnidadeTipoFormularioTituloDaPagina", "Cadastro de Tipo de Unidade", "Formulário de Tipo de Unidade");
		criaSeNaoExistir(lista, "UnidadeTipoListaTituloDaPagina", "Lista de Tipo de Unidade", "Lista de Tipo de Unidade");
		criaSeNaoExistir(lista, "UnidadeTipoListaAcoes", "Ações", "Lista de Tipo de Unidade");
		criaSeNaoExistir(lista, "UnidadeTipoListaSemRegistro", "Não foram encontrados registros para Tipo de Unidade", "Lista de Tipo de Unidade");
		
		criaSeNaoExistir(lista, "Unidade_UsuarioFormularioId", "Código", "Formulário de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioListaId", "Código", "Lista de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioFormularioUnidade", "Unidade", "Formulário de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioListaUnidade", "Unidade", "Lista de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioFormularioUsuario", "Usuario", "Formulário de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioListaUsuario", "Usuario", "Lista de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioMenuPrincipal", "Unidade Usuario", "Menu");
		criaSeNaoExistir(lista, "Unidade_UsuarioMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "Unidade_UsuarioMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "Unidade_UsuarioFormularioBotaoInsere", "Insere", "Formulário de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioFormularioBotaoAltera", "Altera", "Formulário de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioFormularioBotaoAlterar", "Alterar", "Formulário de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioFormularioBotaoVoltar", "Voltar", "Formulário de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioFormularioTituloDaPagina", "Cadastro de Unidade ao Usuario", "Formulário de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioListaTituloDaPagina", "Lista de Unidade_Usuario", "Lista de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioListaAcoes", "Ações", "Lista de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioListaSemRegistro", "Não foram encontrados registros para Unidade_Usuario", "Lista de Unidade_Usuario");
		criaSeNaoExistir(lista, "Unidade_UsuarioFormularioPropriedade", "Unidade", "Unidade Usuário");
		criaSeNaoExistir(lista, "Unidade_UsuarioFormularioAcao", "Ação", "Unidade Usuário");

		criaSeNaoExistir(lista, "SubUnidadeFormularioId", "Código", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaId", "Código", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeFormularioReferencia", "Referência", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaReferencia", "Referência", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeFormularioResponsavel", "Resonsável", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaResponsavel", "Resonsável", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeFormularioEmail", "E-mail", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaEmail", "E-mail", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeFormularioTelefone", "Telefone", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaTelefone", "Telefone", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeFormularioFax", "Fax", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaFax", "Fax", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeFormularioOutrasInformacoes", "Outras Informações", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaOutrasInformacoes", "Outras Informações", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeFormularioObservacoes", "Observações", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaObservacoes", "Observações", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeFormularioNome", "Nome", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaNome", "nome", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeFormularioDescricao", "Descrição", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaDescricao", "Descrição", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeFormularioSubUnidadeTipo", "Tipo de Sub Unidade", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaSubUnidadeTipo", "Tipo de Sub Unidade", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeMenuPrincipal", "Sub Unidade", "Menu");
		criaSeNaoExistir(lista, "SubUnidadeMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "SubUnidadeMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "SubUnidadeFormularioBotaoInsere", "Insere", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeFormularioBotaoAltera", "Altera", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeFormularioTituloDaPagina", "Cadastro de SubUnidade", "Formulário de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaTituloDaPagina", "Lista de Sub Unidade", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaAcoes", "Ações", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeMensagemUsuario", "Importante: As Sub Unidades possuem permissão por usuário. Caso não esteja visualizando, favor verificar!", "Lista de SubUnidade");
		criaSeNaoExistir(lista, "SubUnidadeListaSemRegistro", "Não foram encontrados registros para Sub Unidade", "Lista de SubUnidade");

		
		criaSeNaoExistir(lista, "SubUnidadeTipoFormularioId", "Código", "Formulário de Tipo de Sub Unidade");
		criaSeNaoExistir(lista, "SubUnidadeTipoListaId", "Código", "Lista de Tipo de Sub Unidade");
		criaSeNaoExistir(lista, "SubUnidadeTipoFormularioDescricao", "Descrição", "Formulário de Tipo de Sub Unidade");
		criaSeNaoExistir(lista, "SubUnidadeTipoListaDescricao", "Descrição", "Lista de Tipo de Sub Unidade");
		criaSeNaoExistir(lista, "SubUnidadeTipoMenuPrincipal", "Tipo de Sub Unidade", "Menu");
		criaSeNaoExistir(lista, "SubUnidadeTipoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "SubUnidadeTipoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "SubUnidadeTipoFormularioBotaoInsere", "Insere", "Formulário de Tipo de Sub Unidade");
		criaSeNaoExistir(lista, "SubUnidadeTipoFormularioBotaoAltera", "Altera", "Formulário de Tipo de Sub Unidade");
		criaSeNaoExistir(lista, "SubUnidadeTipoFormularioTituloDaPagina", "Cadastro de Tipo de Sub Unidade", "Formulário de Tipo de Sub Unidade");
		criaSeNaoExistir(lista, "SubUnidadeTipoListaTituloDaPagina", "Lista de Tipo de Sub Unidade", "Lista de Tipo de Sub Unidade");
		criaSeNaoExistir(lista, "SubUnidadeTipoListaAcoes", "Ações", "Lista de Tipo de Sub Unidade");
		criaSeNaoExistir(lista, "SubUnidadeTipoListaSemRegistro", "Não foram encontrados registros para Tipo de Sub Unidade", "Lista de Tipo de Sub Unidade");
		
		criaSeNaoExistir(lista, "LegislacaoTipoFormularioId", "Código", "Formulário de Tipo de Legislação");
		criaSeNaoExistir(lista, "LegislacaoTipoListaId", "Código", "Lista de Tipo de Legislação");
		criaSeNaoExistir(lista, "LegislacaoTipoFormularioDescricao", "Descrição", "Formulário de Tipo de Legislação");
		criaSeNaoExistir(lista, "LegislacaoTipoListaDescricao", "Descrição", "Lista de Tipo de Legislação");
		criaSeNaoExistir(lista, "LegislacaoTipoMenuPrincipal", "Tipo de Legislação", "Menu");
		criaSeNaoExistir(lista, "LegislacaoTipoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "LegislacaoTipoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "LegislacaoTipoFormularioBotaoInsere", "Insere", "Formulário de Tipo de Legislação");
		criaSeNaoExistir(lista, "LegislacaoTipoFormularioBotaoAltera", "Altera", "Formulário de Tipo de Legislação");
		criaSeNaoExistir(lista, "LegislacaoTipoFormularioTituloDaPagina", "Cadastro de Tipo de Legislação", "Formulário de Tipo de Legislação");
		criaSeNaoExistir(lista, "LegislacaoTipoListaTituloDaPagina", "Lista de Tipo de Legislação", "Lista de Tipo de Legislação");
		criaSeNaoExistir(lista, "LegislacaoTipoListaAcoes", "Ações", "Lista de Tipo de Legislação");
		criaSeNaoExistir(lista, "LegislacaoTipoListaSemRegistro", "Não foram encontrados registros para Tipo de Legislação", "Lista de Tipo de Legislação");

		criaSeNaoExistir(lista, "AprovacaoFormularioId", "Código", "Formulário de Aprovação");
		criaSeNaoExistir(lista, "AprovacaoListaId", "Código", "Lista de Aprovação");
		criaSeNaoExistir(lista, "AprovacaoFormularioDescricao", "Descrição", "Formulário de Aprovação");
		criaSeNaoExistir(lista, "AprovacaoListaDescricao", "Descrição", "Lista de Aprovação");
		criaSeNaoExistir(lista, "AprovacaoMenuPrincipal", "Aprovação", "Menu");
		criaSeNaoExistir(lista, "AprovacaoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "AprovacaoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "AprovacaoFormularioBotaoInsere", "Insere", "Formulário de Aprovação");
		criaSeNaoExistir(lista, "AprovacaoFormularioBotaoAltera", "Altera", "Formulário de Aprovação");
		criaSeNaoExistir(lista, "AprovacaoFormularioTituloDaPagina", "Cadastro de Aprovação", "Formulário de Aprovação");
		criaSeNaoExistir(lista, "AprovacaoListaTituloDaPagina", "Lista de Aprovação", "Lista de Aprovação");
		criaSeNaoExistir(lista, "AprovacaoListaAcoes", "Ações", "Lista de Aprovação");
		criaSeNaoExistir(lista, "AprovacaoListaSemRegistro", "Não foram encontrados registros para Aprovação", "Lista de Aprovação");
		
		criaSeNaoExistir(lista, "DocumentoFormularioId", "Código", "Formulário de Documento");
		criaSeNaoExistir(lista, "DocumentoListaId", "Código", "Lista de Documento");
		criaSeNaoExistir(lista, "DocumentoFormularioNumero", "numero", "Formulário de Documento");
		criaSeNaoExistir(lista, "DocumentoListaNumero", "numero", "Lista de Documento");
		criaSeNaoExistir(lista, "DocumentoFormularioNome", "Nome", "Formulário de Documento");
		criaSeNaoExistir(lista, "DocumentoListaNome", "Nome", "Lista de Documento");
		criaSeNaoExistir(lista, "DocumentoFormularioDescricao", "Descrição", "Formulário de Documento");
		criaSeNaoExistir(lista, "DocumentoListaDescricao", "Descrição", "Lista de Documento");
		criaSeNaoExistir(lista, "DocumentoFormularioObs", "Observação", "Formulário de Documento");
		criaSeNaoExistir(lista, "DocumentoListaObs", "Observação", "Lista de Documento");
		criaSeNaoExistir(lista, "DocumentoMenuPrincipal", "Documento", "Menu");
		criaSeNaoExistir(lista, "DocumentoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "DocumentoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "DocumentoFormularioBotaoInsere", "Insere", "Formulário de Documento");
		criaSeNaoExistir(lista, "DocumentoFormularioBotaoAltera", "Altera", "Formulário de Documento");
		criaSeNaoExistir(lista, "DocumentoFormularioTituloDaPagina", "Cadastro de Documento", "Formulário de Documento");
		criaSeNaoExistir(lista, "DocumentoListaTituloDaPagina", "Lista de Documento", "Lista de Documento");
		criaSeNaoExistir(lista, "DocumentoListaAcoes", "Ações", "Lista de Documento");
		criaSeNaoExistir(lista, "DocumentoListaSemRegistro", "Não foram encontrados registros para Documento", "Lista de Documento");
		criaSeNaoExistir(lista, "Servico_DocumentoFormularioId", "Código", "Formulário de Servico Documento");
		criaSeNaoExistir(lista, "Servico_DocumentoListaId", "Código", "Lista de Servico Documento");
		criaSeNaoExistir(lista, "Servico_DocumentoFormularioServico", "Servico", "Formulário de Servico Documento");
		criaSeNaoExistir(lista, "Servico_DocumentoListaServico", "Servico", "Lista de Servico Documento");
		criaSeNaoExistir(lista, "Servico_DocumentoFormularioDocumento", "Documento", "Formulário de Servico Documento");
		criaSeNaoExistir(lista, "Servico_DocumentoListaDocumento", "Documento", "Lista de Servico Documento");
		criaSeNaoExistir(lista, "Servico_DocumentoMenuPrincipal", "Servico Documento", "Menu");
		criaSeNaoExistir(lista, "Servico_DocumentoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "Servico_DocumentoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "Servico_DocumentoFormularioBotaoInsere", "Insere", "Formulário de Servico Documento");
		criaSeNaoExistir(lista, "Servico_DocumentoFormularioBotaoAltera", "Altera", "Formulário de Servico Documento");
		criaSeNaoExistir(lista, "Servico_DocumentoFormularioTituloDaPagina", "Cadastro de Servico Documento", "Formulário de Servico Documento");
		criaSeNaoExistir(lista, "Servico_DocumentoListaTituloDaPagina", "Lista de Servico Documento", "Lista de Servico Documento");
		criaSeNaoExistir(lista, "Servico_DocumentoListaAcoes", "Ações", "Lista de Servico Documento");
		criaSeNaoExistir(lista, "Servico_DocumentoListaSemRegistro", "Não foram encontrados registros para Servico Documento", "Lista de Servico Documento");
		criaSeNaoExistir(lista, "EnderecoFormularioId", "Código", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaId", "Código", "Lista de Endereço");
		criaSeNaoExistir(lista, "EnderecoFormularioLogradouro", "Logradouro", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaLogradouro", "Logradouro", "Lista de Endereço");
		criaSeNaoExistir(lista, "EnderecoFormularioNumero", "Número", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaNumero", "Número", "Lista de Endereço");
		criaSeNaoExistir(lista, "EnderecoFormularioComplemento", "Complemento", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaComplemento", "Complemento", "Lista de Endereço");
		criaSeNaoExistir(lista, "EnderecoFormularioBairro", "Bairro", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaBairro", "Bairro", "Lista de Endereço");
		criaSeNaoExistir(lista, "EnderecoFormularioCep", "CEP", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaCep", "CEP", "Lista de Endereço");
		criaSeNaoExistir(lista, "EnderecoFormularioCidade", "Cidade", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaCidade", "Cidade", "Lista de Endereço");
		criaSeNaoExistir(lista, "EnderecoFormularioUf", "UF", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaUf", "UF", "Lista de Endereço");
		criaSeNaoExistir(lista, "EnderecoFormularioPontoreferencia", "Ponto de Referência", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaPontoreferencia", "Ponto de Referência", "Lista de Endereço");
		criaSeNaoExistir(lista, "EnderecoFormularioTipoLogradouro", "Tipo Logradouro", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaTipoLogradouro", "TipoLogradouro", "Lista de Endereço");
		criaSeNaoExistir(lista, "EnderecoFormularioObs", "Observação", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaObs", "Observação", "Lista de Endereço");
		criaSeNaoExistir(lista, "EnderecoMenuPrincipal", "Endereço", "Menu");
		criaSeNaoExistir(lista, "EnderecoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "EnderecoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "EnderecoFormularioBotaoInsere", "Insere", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoFormularioBotaoAltera", "Altera", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoFormularioTituloDaPagina", "Cadastro de Endereço", "Formulário de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaTituloDaPagina", "Lista de Endereço", "Lista de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaAcoes", "Ações", "Lista de Endereço");
		criaSeNaoExistir(lista, "EnderecoListaSemRegistro", "Não foram encontrados registros para Endereço", "Lista de Endereço");
		criaSeNaoExistir(lista, "FormaSolicitacaoFormularioId", "Código", "Formulário de Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoListaId", "Código", "Lista de Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoFormularioDescricao", "Descrição", "Formulário de Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoListaDescricao", "Descrição", "Lista de Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoFormularioNome", "Nome", "Formulário de Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoListaNome", "Nome", "Lista de Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoFormularioFormaSolicitacaoTipo", "Tipo Solicitação", "Formulário de Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoListaFormaSolicitacaoTipo", "Tipo Solicitação", "Lista de Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoMenuPrincipal", "Forma Solicitação", "Menu");
		criaSeNaoExistir(lista, "FormaSolicitacaoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "FormaSolicitacaoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "FormaSolicitacaoFormularioBotaoInsere", "Insere", "Formulário de Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoFormularioBotaoAltera", "Altera", "Formulário de Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoFormularioTituloDaPagina", "Cadastro de Forma Solicitação", "Formulário de Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoListaTituloDaPagina", "Lista de Forma Solicitação", "Lista de Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoListaAcoes", "Ações", "Lista de Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoListaSemRegistro", "Não foram encontrados registros para Forma Solicitação", "Lista de Forma Solicitação");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoFormularioId", "Código", "Formulário de Serviço Forma Solicitação");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoListaId", "Código", "Lista de Serviço Forma Solicitação");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoFormularioServico", "Servico", "Formulário de Serviço Forma Solicitação");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoListaServico", "Servico", "Lista de Serviço Forma Solicitação");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoFormularioFormaSolicitacao", "FormaSolicitacao", "Formulário de Serviço Forma Solicitação");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoListaFormaSolicitacao", "FormaSolicitacao", "Lista de Serviço Forma Solicitação");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoMenuPrincipal", "Serviço Forma Solicitação", "Menu");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoFormularioBotaoInsere", "Insere", "Formulário de Serviço Forma Solicitação");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoFormularioBotaoAltera", "Altera", "Formulário de Serviço Forma Solicitação");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoFormularioTituloDaPagina", "Cadastro de Serviço Forma Solicitação", "Formulário de Serviço Forma Solicitação");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoListaTituloDaPagina", "Lista de Serviço Forma Solicitação", "Lista de Serviço Forma Solicitação");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoListaAcoes", "Ações", "Lista de Serviço Forma Solicitação");
		criaSeNaoExistir(lista, "Servico_FormaSolicitacaoListaSemRegistro", "Não foram encontrados registros para Serviço Forma Solicitação", "Lista de Serviço Forma Solicitação");
		criaSeNaoExistir(lista, "FormaSolicitacaoTipoFormularioId", "Código", "Formulário de Forma Solicitação Tipo");
		criaSeNaoExistir(lista, "FormaSolicitacaoTipoListaId", "Código", "Lista de Forma Solicitação Tipo");
		criaSeNaoExistir(lista, "FormaSolicitacaoTipoFormularioDescricao", "Descrição", "Formulário de Forma Solicitação Tipo");
		criaSeNaoExistir(lista, "FormaSolicitacaoTipoListaDescricao", "Descrição", "Lista de Forma Solicitação Tipo");
		criaSeNaoExistir(lista, "FormaSolicitacaoTipoMenuPrincipal", "Forma Solicitação Tipo", "Menu");
		criaSeNaoExistir(lista, "FormaSolicitacaoTipoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "FormaSolicitacaoTipoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "FormaSolicitacaoTipoFormularioBotaoInsere", "Insere", "Formulário de Forma Solicitação Tipo");
		criaSeNaoExistir(lista, "FormaSolicitacaoTipoFormularioBotaoAltera", "Altera", "Formulário de Forma Solicitação Tipo");
		criaSeNaoExistir(lista, "FormaSolicitacaoTipoFormularioTituloDaPagina", "Cadastro de Forma Solicitação Tipo", "Formulário de Forma Solicitação Tipo");
		criaSeNaoExistir(lista, "FormaSolicitacaoTipoListaTituloDaPagina", "Lista de Forma Solicitação Tipo", "Lista de Forma Solicitação Tipo");
		criaSeNaoExistir(lista, "FormaSolicitacaoTipoListaAcoes", "Ações", "Lista de Forma Solicitação Tipo");
		criaSeNaoExistir(lista, "FormaSolicitacaoTipoListaSemRegistro", "Não foram encontrados registros para Forma Solicitação Tipo", "Lista de Forma Solicitação Tipo");
		criaSeNaoExistir(lista, "FormularioFormularioId", "Código", "Formulário de Formulário");
		criaSeNaoExistir(lista, "FormularioListaId", "Código", "Lista de Formulário");
		criaSeNaoExistir(lista, "FormularioFormularioNome", "nome", "Formulário de Formulário");
		criaSeNaoExistir(lista, "FormularioListaNome", "nome", "Lista de Formulário");
		criaSeNaoExistir(lista, "FormularioFormularioDescricao", "Descrição", "Formulário de Formulário");
		criaSeNaoExistir(lista, "FormularioListaDescricao", "Descrição", "Lista de Formulário");
		criaSeNaoExistir(lista, "FormularioMenuPrincipal", "Formulário", "Menu");
		criaSeNaoExistir(lista, "FormularioMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "FormularioMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "FormularioFormularioBotaoInsere", "Insere", "Formulário de Formulário");
		criaSeNaoExistir(lista, "FormularioFormularioBotaoAltera", "Altera", "Formulário de Formulário");
		criaSeNaoExistir(lista, "FormularioFormularioTituloDaPagina", "Cadastro de Formulário", "Formulário de Formulário");
		criaSeNaoExistir(lista, "FormularioListaTituloDaPagina", "Lista de Formulário", "Lista de Formulário");
		criaSeNaoExistir(lista, "FormularioListaAcoes", "Ações", "Lista de Formulário");
		criaSeNaoExistir(lista, "FormularioListaSemRegistro", "Não foram encontrados registros para Formulário", "Lista de Formulário");
		criaSeNaoExistir(lista, "Servico_FormularioFormularioId", "Código", "Formulário de Serviço Formulário");
		criaSeNaoExistir(lista, "Servico_FormularioListaId", "Código", "Lista de Serviço Formulário");
		criaSeNaoExistir(lista, "Servico_FormularioFormularioServico", "Servico", "Formulário de Serviço Formulário");
		criaSeNaoExistir(lista, "Servico_FormularioListaServico", "Servico", "Lista de Serviço Formulário");
		criaSeNaoExistir(lista, "Servico_FormularioFormularioFormulario", "Formulario", "Formulário de Serviço Formulário");
		criaSeNaoExistir(lista, "Servico_FormularioListaFormulario", "Formulario", "Lista de Serviço Formulário");
		criaSeNaoExistir(lista, "Servico_FormularioMenuPrincipal", "Serviço Formulário", "Menu");
		criaSeNaoExistir(lista, "Servico_FormularioMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "Servico_FormularioMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "Servico_FormularioFormularioBotaoInsere", "Insere", "Formulário de Serviço Formulário");
		criaSeNaoExistir(lista, "Servico_FormularioFormularioBotaoAltera", "Altera", "Formulário de Serviço Formulário");
		criaSeNaoExistir(lista, "Servico_FormularioFormularioTituloDaPagina", "Cadastro de Serviço Formulário", "Formulário de Serviço Formulário");
		criaSeNaoExistir(lista, "Servico_FormularioListaTituloDaPagina", "Lista de Serviço Formulário", "Lista de Serviço Formulário");
		criaSeNaoExistir(lista, "Servico_FormularioListaAcoes", "Ações", "Lista de Serviço Formulário");
		criaSeNaoExistir(lista, "Servico_FormularioListaSemRegistro", "Não foram encontrados registros para Serviço Formulário", "Lista de Serviço Formulário");
		criaSeNaoExistir(lista, "LegislacaoFormularioId", "Código", "Formulário de Legislação");
		criaSeNaoExistir(lista, "LegislacaoListaId", "Código", "Lista de Legislação");
		criaSeNaoExistir(lista, "LegislacaoFormularioTitulo", "Título", "Formulário de Legislação");
		criaSeNaoExistir(lista, "LegislacaoListaDataDaLei", "Data Da Lei", "Lista de Legislação");
		criaSeNaoExistir(lista, "LegislacaoFormularioLei", "Lei", "Formulário de Legislação");
		criaSeNaoExistir(lista, "LegislacaoFormularioDataDaLei", "Data da Lei", "Formulário de Legislação");
		criaSeNaoExistir(lista, "LegislacaoListaLegislacaoTipo", "Tipo", "Lista de Legislação");
		criaSeNaoExistir(lista, "LegislacaoListaLei", "Lei", "Lista de Legislação");
		criaSeNaoExistir(lista, "LegislacaoFormularioDescricao", "Descrição", "Formulário de Legislação");
		criaSeNaoExistir(lista, "LegislacaoListaDescricao", "Descrição", "Lista de Legislação");
		criaSeNaoExistir(lista, "LegislacaoMenuPrincipal", "Legislação", "Menu");
		criaSeNaoExistir(lista, "LegislacaoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "LegislacaoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "LegislacaoFormularioBotaoInsere", "Insere", "Formulário de Legislação");
		criaSeNaoExistir(lista, "LegislacaoFormularioBotaoAltera", "Altera", "Formulário de Legislação");
		criaSeNaoExistir(lista, "LegislacaoFormularioTituloDaPagina", "Cadastro de Legislação", "Formulário de Legislação");
		criaSeNaoExistir(lista, "LegislacaoFormularioLegislacaoTipo", "Tipo de Legislação", "Formulário de Legislação");
		criaSeNaoExistir(lista, "LegislacaoListaTituloDaPagina", "Lista de Legislação", "Lista de Legislação");
		criaSeNaoExistir(lista, "LegislacaoListaAcoes", "Ações", "Lista de Legislação");
		criaSeNaoExistir(lista, "LegislacaoListaSemRegistro", "Não foram encontrados registros para Legislação", "Lista de Legislação");
		criaSeNaoExistir(lista, "Servico_LegislacaoFormularioId", "Código", "Formulário de Serviço Legislação");
		criaSeNaoExistir(lista, "Servico_LegislacaoListaId", "Código", "Lista de Serviço Legislação");
		criaSeNaoExistir(lista, "Servico_LegislacaoFormularioServico", "Servico", "Formulário de Serviço Legislação");
		criaSeNaoExistir(lista, "Servico_LegislacaoListaServico", "Servico", "Lista de Serviço Legislação");
		criaSeNaoExistir(lista, "Servico_LegislacaoFormularioLegislacao", "Legislacao", "Formulário de Serviço Legislação");
		criaSeNaoExistir(lista, "Servico_LegislacaoListaLegislacao", "Legislacao", "Lista de Serviço Legislação");
		criaSeNaoExistir(lista, "Servico_LegislacaoMenuPrincipal", "Serviço Legislação", "Menu");
		criaSeNaoExistir(lista, "Servico_LegislacaoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "Servico_LegislacaoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "Servico_LegislacaoFormularioBotaoInsere", "Insere", "Formulário de Serviço Legislação");
		criaSeNaoExistir(lista, "Servico_LegislacaoFormularioBotaoAltera", "Altera", "Formulário de Serviço Legislação");
		criaSeNaoExistir(lista, "Servico_LegislacaoFormularioTituloDaPagina", "Cadastro de Serviço Legislação", "Formulário de Serviço Legislação");
		criaSeNaoExistir(lista, "Servico_LegislacaoListaTituloDaPagina", "Lista de Serviço Legislação", "Lista de Serviço Legislação");
		criaSeNaoExistir(lista, "Servico_LegislacaoListaAcoes", "Ações", "Lista de Serviço Legislação");
		criaSeNaoExistir(lista, "Servico_LegislacaoListaSemRegistro", "Não foram encontrados registros para Serviço Legislação", "Lista de Serviço Legislação");
		criaSeNaoExistir(lista, "ProcedimentoFormularioId", "Código", "Formulário de Procedimento");
		criaSeNaoExistir(lista, "ProcedimentoListaId", "Código", "Lista de Procedimento");
		criaSeNaoExistir(lista, "ProcedimentoFormularioSigla", "Sigla", "Formulário de Procedimento");
		criaSeNaoExistir(lista, "ProcedimentoListaSigla", "Sigla", "Lista de Procedimento");
		criaSeNaoExistir(lista, "ProcedimentoFormularioNome", "nome", "Formulário de Procedimento");
		criaSeNaoExistir(lista, "ProcedimentoListaNome", "nome", "Lista de Procedimento");
		criaSeNaoExistir(lista, "ProcedimentoFormularioDescricao", "Descrição", "Formulário de Procedimento");
		criaSeNaoExistir(lista, "ProcedimentoListaDescricao", "Descrição", "Lista de Procedimento");
		criaSeNaoExistir(lista, "ProcedimentoMenuPrincipal", "Procedimento", "Menu");
		criaSeNaoExistir(lista, "ProcedimentoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "ProcedimentoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "ProcedimentoFormularioBotaoInsere", "Insere", "Formulário de Procedimento");
		criaSeNaoExistir(lista, "ProcedimentoFormularioBotaoAltera", "Altera", "Formulário de Procedimento");
		criaSeNaoExistir(lista, "ProcedimentoFormularioTituloDaPagina", "Cadastro de Procedimento", "Formulário de Procedimento");
		criaSeNaoExistir(lista, "ProcedimentoListaTituloDaPagina", "Lista de Procedimento", "Lista de Procedimento");
		criaSeNaoExistir(lista, "ProcedimentoListaAcoes", "Ações", "Lista de Procedimento");
		criaSeNaoExistir(lista, "ProcedimentoListaSemRegistro", "Não foram encontrados registros para Procedimento", "Lista de Procedimento");
		criaSeNaoExistir(lista, "Servico_ProcedimentoFormularioId", "Código", "Formulário de Serviço Procedimento");
		criaSeNaoExistir(lista, "Servico_ProcedimentoListaId", "Código", "Lista de Serviço Procedimento");
		criaSeNaoExistir(lista, "Servico_ProcedimentoFormularioServico", "Servico", "Formulário de Serviço Procedimento");
		criaSeNaoExistir(lista, "Servico_ProcedimentoListaServico", "Servico", "Lista de Serviço Procedimento");
		criaSeNaoExistir(lista, "Servico_ProcedimentoFormularioProcedimento", "Procedimento", "Formulário de Serviço Procedimento");
		criaSeNaoExistir(lista, "Servico_ProcedimentoListaProcedimento", "Procedimento", "Lista de Serviço Procedimento");
		criaSeNaoExistir(lista, "Servico_ProcedimentoMenuPrincipal", "Serviço Procedimento", "Menu");
		criaSeNaoExistir(lista, "Servico_ProcedimentoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "Servico_ProcedimentoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "Servico_ProcedimentoFormularioBotaoInsere", "Insere", "Formulário de Serviço Procedimento");
		criaSeNaoExistir(lista, "Servico_ProcedimentoFormularioBotaoAltera", "Altera", "Formulário de Serviço Procedimento");
		criaSeNaoExistir(lista, "Servico_ProcedimentoFormularioTituloDaPagina", "Cadastro de Serviço Procedimento", "Formulário de Serviço Procedimento");
		criaSeNaoExistir(lista, "Servico_ProcedimentoListaTituloDaPagina", "Lista de Serviço Procedimento", "Lista de Serviço Procedimento");
		criaSeNaoExistir(lista, "Servico_ProcedimentoListaAcoes", "Ações", "Lista de Serviço Procedimento");
		criaSeNaoExistir(lista, "Servico_ProcedimentoListaSemRegistro", "Não foram encontrados registros para Serviço Procedimento", "Lista de Serviço Procedimento");
		criaSeNaoExistir(lista, "RequisitoFormularioId", "Código", "Formulário de Requisito");
		criaSeNaoExistir(lista, "RequisitoListaId", "Código", "Lista de Requisito");
		criaSeNaoExistir(lista, "RequisitoFormularioNome", "nome", "Formulário de Requisito");
		criaSeNaoExistir(lista, "RequisitoListaNome", "nome", "Lista de Requisito");
		criaSeNaoExistir(lista, "RequisitoFormularioDescricao", "Descrição", "Formulário de Requisito");
		criaSeNaoExistir(lista, "RequisitoListaDescricao", "Descrição", "Lista de Requisito");
		criaSeNaoExistir(lista, "RequisitoMenuPrincipal", "Requisito", "Menu");
		criaSeNaoExistir(lista, "RequisitoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "RequisitoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "RequisitoFormularioBotaoInsere", "Insere", "Formulário de Requisito");
		criaSeNaoExistir(lista, "RequisitoFormularioBotaoAltera", "Altera", "Formulário de Requisito");
		criaSeNaoExistir(lista, "RequisitoFormularioTituloDaPagina", "Cadastro de Requisito", "Formulário de Requisito");
		criaSeNaoExistir(lista, "RequisitoListaTituloDaPagina", "Lista de Requisito", "Lista de Requisito");
		criaSeNaoExistir(lista, "RequisitoListaAcoes", "Ações", "Lista de Requisito");
		criaSeNaoExistir(lista, "RequisitoListaSemRegistro", "Não foram encontrados registros para Requisito", "Lista de Requisito");
		criaSeNaoExistir(lista, "Servico_RequisitoFormularioId", "Código", "Formulário de Serviço Requisito");
		criaSeNaoExistir(lista, "Servico_RequisitoListaId", "Código", "Lista de Serviço Requisito");
		criaSeNaoExistir(lista, "Servico_RequisitoFormularioServico", "Servico", "Formulário de Serviço Requisito");
		criaSeNaoExistir(lista, "Servico_RequisitoListaServico", "Servico", "Lista de Serviço Requisito");
		criaSeNaoExistir(lista, "Servico_RequisitoFormularioRequisito", "Requisito", "Formulário de Serviço Requisito");
		criaSeNaoExistir(lista, "Servico_RequisitoListaRequisito", "Requisito", "Lista de Serviço Requisito");
		criaSeNaoExistir(lista, "Servico_RequisitoMenuPrincipal", "Serviço Requisito", "Menu");
		criaSeNaoExistir(lista, "Servico_RequisitoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "Servico_RequisitoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "Servico_RequisitoFormularioBotaoInsere", "Insere", "Formulário de Serviço Requisito");
		criaSeNaoExistir(lista, "Servico_RequisitoFormularioBotaoAltera", "Altera", "Formulário de Serviço Requisito");
		criaSeNaoExistir(lista, "Servico_RequisitoFormularioTituloDaPagina", "Cadastro de Serviço Requisito", "Formulário de Serviço Requisito");
		criaSeNaoExistir(lista, "Servico_RequisitoListaTituloDaPagina", "Lista de Serviço Requisito", "Lista de Serviço Requisito");
		criaSeNaoExistir(lista, "Servico_RequisitoListaAcoes", "Ações", "Lista de Serviço Requisito");
		criaSeNaoExistir(lista, "Servico_RequisitoListaSemRegistro", "Não foram encontrados registros para Serviço Requisito", "Lista de Serviço Requisito");
		criaSeNaoExistir(lista, "TributoFormularioId", "Código", "Formulário de Tributo");
		criaSeNaoExistir(lista, "TributoListaId", "Código", "Lista de Tributo");
		criaSeNaoExistir(lista, "TributoFormularioNome", "nome", "Formulário de Tributo");
		criaSeNaoExistir(lista, "TributoListaNome", "nome", "Lista de Tributo");
		criaSeNaoExistir(lista, "TributoFormularioDescricao", "Descrição", "Formulário de Tributo");
		criaSeNaoExistir(lista, "TributoListaDescricao", "Descrição", "Lista de Tributo");
		criaSeNaoExistir(lista, "TributoFormularioPreco", "Preço", "Formulário de Tributo");
		criaSeNaoExistir(lista, "TributoListaPreco", "Preço", "Lista de Tributo");
		criaSeNaoExistir(lista, "TributoFormularioMetrica", "Métrica", "Formulário de Tributo");
		criaSeNaoExistir(lista, "TributoListaMetrica", "Métrica", "Lista de Tributo");
		criaSeNaoExistir(lista, "TributoMenuPrincipal", "Tributo", "Menu");
		criaSeNaoExistir(lista, "TributoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "TributoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "TributoFormularioBotaoInsere", "Insere", "Formulário de Tributo");
		criaSeNaoExistir(lista, "TributoFormularioBotaoAltera", "Altera", "Formulário de Tributo");
		criaSeNaoExistir(lista, "TributoFormularioTituloDaPagina", "Cadastro de Tributo", "Formulário de Tributo");
		criaSeNaoExistir(lista, "TributoListaTituloDaPagina", "Lista de Tributo", "Lista de Tributo");
		criaSeNaoExistir(lista, "TributoListaAcoes", "Ações", "Lista de Tributo");
		criaSeNaoExistir(lista, "TributoListaSemRegistro", "Não foram encontrados registros para Tributo", "Lista de Tributo");
		criaSeNaoExistir(lista, "Servico_TributoFormularioId", "Código", "Formulário de Serviço Tributo");
		criaSeNaoExistir(lista, "Servico_TributoListaId", "Código", "Lista de Serviço Tributo");
		criaSeNaoExistir(lista, "Servico_TributoFormularioServico", "Servico", "Formulário de Serviço Tributo");
		criaSeNaoExistir(lista, "Servico_TributoListaServico", "Servico", "Lista de Serviço Tributo");
		criaSeNaoExistir(lista, "Servico_TributoFormularioTributo", "Tributo", "Formulário de Serviço Tributo");
		criaSeNaoExistir(lista, "Servico_TributoListaTributo", "Tributo", "Lista de Serviço Tributo");
		criaSeNaoExistir(lista, "Servico_TributoMenuPrincipal", "Serviço Tributo", "Menu");
		criaSeNaoExistir(lista, "Servico_TributoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "Servico_TributoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "Servico_TributoFormularioBotaoInsere", "Insere", "Formulário de Serviço Tributo");
		criaSeNaoExistir(lista, "Servico_TributoFormularioBotaoAltera", "Altera", "Formulário de Serviço Tributo");
		criaSeNaoExistir(lista, "Servico_TributoFormularioTituloDaPagina", "Cadastro de Serviço Tributo", "Formulário de Serviço Tributo");
		criaSeNaoExistir(lista, "Servico_TributoListaTituloDaPagina", "Lista de Serviço Tributo", "Lista de Serviço Tributo");
		criaSeNaoExistir(lista, "Servico_TributoListaAcoes", "Ações", "Lista de Serviço Tributo");
		criaSeNaoExistir(lista, "Servico_TributoListaSemRegistro", "Não foram encontrados registros para Serviço Tributo", "Lista de Serviço Tributo");
		criaSeNaoExistir(lista, "TipoLogradouroFormularioId", "Código", "Formulário de Tipo Logradouro");
		criaSeNaoExistir(lista, "TipoLogradouroListaId", "Código", "Lista de Tipo Logradouro");
		criaSeNaoExistir(lista, "TipoLogradouroFormularioDescricao", "Descrição", "Formulário de Tipo Logradouro");
		criaSeNaoExistir(lista, "TipoLogradouroListaDescricao", "Descrição", "Lista de Tipo Logradouro");
		criaSeNaoExistir(lista, "TipoLogradouroMenuPrincipal", "Tipo Logradouro", "Menu");
		criaSeNaoExistir(lista, "TipoLogradouroMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "TipoLogradouroMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "TipoLogradouroFormularioBotaoInsere", "Insere", "Formulário de Tipo Logradouro");
		criaSeNaoExistir(lista, "TipoLogradouroFormularioBotaoAltera", "Altera", "Formulário de Tipo Logradouro");
		criaSeNaoExistir(lista, "TipoLogradouroFormularioTituloDaPagina", "Cadastro de Tipo Logradouro", "Formulário de Tipo Logradouro");
		criaSeNaoExistir(lista, "TipoLogradouroListaTituloDaPagina", "Lista de Tipo Logradouro", "Lista de Tipo Logradouro");
		criaSeNaoExistir(lista, "TipoLogradouroListaAcoes", "Ações", "Lista de Tipo Logradouro");
		criaSeNaoExistir(lista, "TipoLogradouroListaSemRegistro", "Não foram encontrados registros para Tipo Logradouro", "Lista de Tipo Logradouro");
		criaSeNaoExistir(lista, "Unidade_EnderecoFormularioId", "Código", "Formulário de Unidade Endereço");
		criaSeNaoExistir(lista, "Unidade_EnderecoListaId", "Código", "Lista de Unidade Endereço");
		criaSeNaoExistir(lista, "Unidade_EnderecoFormularioUnidade", "Unidade", "Formulário de Unidade Endereço");
		criaSeNaoExistir(lista, "Unidade_EnderecoListaUnidade", "Unidade", "Lista de Unidade Endereço");
		criaSeNaoExistir(lista, "Unidade_EnderecoFormularioIdentificacao", "Nome do Local", "Formulário de Unidade Endereço");
		criaSeNaoExistir(lista, "Unidade_EnderecoListaIdentificacao", "Nome do Local", "Lista de Unidade Endereço");
		criaSeNaoExistir(lista, "Unidade_EnderecoFormularioEndereco", "Endereco", "Formulário de Unidade Endereço");
		criaSeNaoExistir(lista, "Unidade_EnderecoListaEndereco", "Endereco", "Lista de Unidade Endereço");
		criaSeNaoExistir(lista, "Unidade_EnderecoMenuPrincipal", "Unidade Endereço", "Menu");
		criaSeNaoExistir(lista, "Unidade_EnderecoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "Unidade_EnderecoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "Unidade_EnderecoFormularioBotaoInsere", "Insere", "Formulário de Unidade Endereço");
		criaSeNaoExistir(lista, "Unidade_EnderecoFormularioBotaoAltera", "Altera", "Formulário de Unidade Endereço");
		criaSeNaoExistir(lista, "Unidade_EnderecoFormularioTituloDaPagina", "Cadastro de Unidade Endereço", "Formulário de Unidade Endereço");
		criaSeNaoExistir(lista, "Unidade_EnderecoListaTituloDaPagina", "Lista de Unidade Endereço", "Lista de Unidade Endereço");
		criaSeNaoExistir(lista, "Unidade_EnderecoListaAcoes", "Ações", "Lista de Unidade Endereço");
		criaSeNaoExistir(lista, "Unidade_EnderecoListaSemRegistro", "Não foram encontrados registros para Unidade Endereço", "Lista de Unidade Endereço");
		criaSeNaoExistir(lista, "UsuarioFormularioId", "Código", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaId", "Código", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioLogin", "Login", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaLogin", "Login", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioNome", "Nome", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaNome", "Login", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioNomeCompleto", "Nome Completo", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaNomeCompleto", "Nome Completo", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioSenha", "Senha", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaSenha", "Senha", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioEmail", "E-Mail", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaEmail", "E-Mail", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioAprovador", "Aprovador", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaAprovador", "Aprovador", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioCpf", "CPF", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaCpf", "CPF", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioRg", "RG", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaRg", "RG", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioLdap", "LDAP", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaLdap", "LDAP", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioLocal", "Local de Trabalho", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaLocal", "Local de Trabalho", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioGrupo", "Grupo", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaGrupo", "Grupo", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioMenuPrincipal", "Usuário", "Menu");
		criaSeNaoExistir(lista, "UsuarioMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "UsuarioMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "UsuarioFormularioBotaoInsere", "Insere", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioBotaoAltera", "Altera", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioFormularioTituloDaPagina", "Cadastro de Usuário", "Formulário de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaTituloDaPagina", "Lista de Usuário", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaAcoes", "Ações", "Lista de Usuário");
		criaSeNaoExistir(lista, "UsuarioListaSemRegistro", "Não foram encontrados registros para Usuário", "Lista de Usuário");
		criaSeNaoExistir(lista, "GrupoFormularioId", "Código", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoListaId", "Código", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoFormularioNome", "Nome", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoListaNome", "Nome", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoFormularioEntidade", "Entidade", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoListaEntidade", "Entidade", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoFormularioAcao", "Ação", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoListaAcao", "Ação", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoFormularioTipo", "tipo", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoListaTipo", "tipo", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoFormularioDescricao", "Descrição", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoListaDescricao", "Descrição", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoMenuPrincipal", "Grupo", "Menu");
		criaSeNaoExistir(lista, "GrupoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "GrupoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "GrupoFormularioBotaoInsere", "Insere", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoFormularioBotaoAltera", "Altera", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoFormularioTituloDaPagina", "Cadastro de Grupo", "Formulário de Grupo");
		criaSeNaoExistir(lista, "GrupoListaTituloDaPagina", "Lista de Grupo", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoListaAcoes", "Ações", "Lista de Grupo");
		criaSeNaoExistir(lista, "GrupoListaSemRegistro", "Não foram encontrados registros para Grupo", "Lista de Grupo");
		criaSeNaoExistir(lista, "PermissionamentoFormularioId", "Código", "Formulário de Permissionamento");
		criaSeNaoExistir(lista, "PermissionamentoListaId", "Código", "Lista de Permissionamento");
		criaSeNaoExistir(lista, "PermissionamentoFormularioServico", "Servico", "Formulário de Permissionamento");
		criaSeNaoExistir(lista, "PermissionamentoListaServico", "Servico", "Lista de Permissionamento");
		criaSeNaoExistir(lista, "PermissionamentoFormularioGrupo", "Grupo", "Formulário de Permissionamento");
		criaSeNaoExistir(lista, "PermissionamentoListaGrupo", "Grupo", "Lista de Permissionamento");
		criaSeNaoExistir(lista, "PermissionamentoFormularioUsuario", "Usuário", "Formulário de Permissionamento");
		criaSeNaoExistir(lista, "PermissionamentoListaUsuario", "Usuário", "Lista de Permissionamento");
		criaSeNaoExistir(lista, "PermissionamentoMenuPrincipal", "Permissionamento", "Menu");
		criaSeNaoExistir(lista, "PermissionamentoMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "PermissionamentoMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "PermissionamentoFormularioBotaoInsere", "Insere", "Formulário de Permissionamento");
		criaSeNaoExistir(lista, "PermissionamentoFormularioBotaoAltera", "Altera", "Formulário de Permissionamento");
		criaSeNaoExistir(lista, "PermissionamentoFormularioTituloDaPagina", "Cadastro de Permissionamento", "Formulário de Permissionamento");
		criaSeNaoExistir(lista, "PermissionamentoListaTituloDaPagina", "Lista de Permissionamento", "Lista de Permissionamento");
		criaSeNaoExistir(lista, "PermissionamentoListaAcoes", "Ações", "Lista de Permissionamento");
		criaSeNaoExistir(lista, "PermissionamentoListaSemRegistro", "Não foram encontrados registros para Permissionamento", "Lista de Permissionamento");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioId", "Código", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaId", "Código", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioLogin", "Login", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaLogin", "Login", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioNome", "Nome", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaNome", "Nome", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioDatahora", "Data / Hora", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaDatahora", "Data / Hora", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioLocal", "Local", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaLocal", "Local", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioAcao", "Ação", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaAcao", "Ação", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioDados", "Dados", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaDados", "Dados", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaMenuPrincipal", "Histórico do sistema", "Menu");
		criaSeNaoExistir(lista, "HistoricoDoSistemaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "HistoricoDoSistemaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioBotaoInsere", "Insere", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioBotaoAltera", "Altera", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaFormularioTituloDaPagina", "Cadastro de Histórico do sistema", "Formulário de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaTituloDaPagina", "Lista de Histórico do sistema", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaAcoes", "Ações", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "HistoricoDoSistemaListaSemRegistro", "Não foram encontrados registros para Histórico do sistema", "Lista de Histórico do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioPropriedade", "Propriedade", "Formulário de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaPropriedade", "Propriedade", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioValor", "Valor", "Formulário de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaValor", "Valor", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioTela", "Tela", "Formulário de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaTela", "Tela", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaMenuPrincipal", "Mensagens do sistema", "Menu");
		criaSeNaoExistir(lista, "MensagensDoSistemaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "MensagensDoSistemaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioBotaoInsere", "Insere", "Formulário de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioBotaoAltera", "Altera", "Formulário de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaFormularioTituloDaPagina", "Cadastro de Mensagens do sistema", "Formulário de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaTituloDaPagina", "Lista de Mensagens do sistema", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaAcoes", "Ações", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "MensagensDoSistemaListaSemRegistro", "Não foram encontrados registros para Mensagens do sistema", "Lista de Mensagens do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioPropriedade", "Propriedade", "Formulário de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaPropriedade", "Propriedade", "Lista de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioValor", "Valor", "Formulário de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaValor", "Valor", "Lista de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaMenuPrincipal", "Configurações do sistema", "Menu");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaMenuInsere", "Insere", "Menu");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaMenuLista", "Lista", "Menu");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioBotaoInsere", "Insere", "Formulário de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioBotaoAltera", "Altera", "Formulário de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaFormularioTituloDaPagina", "Cadastro de Configurações do sistema", "Formulário de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaTituloDaPagina", "Lista de Configurações do sistema", "Lista de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaAcoes", "Ações", "Lista de Configurações do sistema");
		criaSeNaoExistir(lista, "ConfiguracoesDoSistemaListaSemRegistro", "Não foram encontrados registros para Configurações do sistema", "Lista de Configurações do sistema");
		criaSeNaoExistir(lista, "ModalExclusaoTitulo", "Confirmar exclusão", "Modal de exclusão");
		criaSeNaoExistir(lista, "ModalExclusaoMensagemInicio", "Deseja mesmo excluir o ", "Modal de exclusão");
		criaSeNaoExistir(lista, "ModalExclusaoMensagemFim", "? Essa ação não poderá ser revertida!", "Modal de exclusão");
		criaSeNaoExistir(lista, "ModalExclusaoBotaoSim", "Sim, excluir!", "Modal de exclusão");
		criaSeNaoExistir(lista, "ModalExclusaoBotaoNao", "Não, Fechar!", "Modal de exclusão");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaTitulo", "Confirmar redefinição de senha", "Modal de redefinição de senha");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaMensagemInicio", "Deseja mesmo redefinir a senha de ", "Modal de redefinição de senha");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaMensagemFim", "? A nova senha será 123456!", "Modal de redefinição de senha");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaBotaoSim", "Sim, redefinir!", "Modal de redefinição de senha");
		criaSeNaoExistir(lista, "ModalRedefinirSenhaBotaoNao", "Não, Fechar!", "Modal de redefinição de senha");
		criaSeNaoExistir(lista, "ModalRedefinirUsuario", "Usuário/Login", "Modal de redefinição de senha");
		criaSeNaoExistir(lista, "ModalRedefinirSenha", "Senha", "Modal de redefinição de senha");
		criaSeNaoExistir(lista, "ModalRedefinirConfirmarSenha", "Confirmar Senha", "Modal de redefinição de senha");
		criaSeNaoExistir(lista, "MensagemAdicionadoComSucesso", "Inserido com sucesso!", "Todas as telas de inserção");
		criaSeNaoExistir(lista, "MensagemAlteradoComSucesso", "Alterado com sucesso!", "Todas as telas de alteração");
		criaSeNaoExistir(lista, "MensagemExcluidoComSucesso", "Excluído com sucesso!", "Todas as telas de exclusão");
		criaSeNaoExistir(lista, "MensagemErroIdNulo", "O id não pode ser nulo!", "Rest Controller");
		criaSeNaoExistir(lista, "MensagemErroAlteracaoNaoLocalizadaNoBanco", "Não foi possivel realizar a alteração, não localizado no banco!", "Rest Controller");
		criaSeNaoExistir(lista, "MensagemErroExclusaoNaoLocalizadaNoBanco", "Não foi possivel realizar a exclusão, não localizado no banco!", "Rest Controller");
		criaSeNaoExistir(lista, "SenhaAtualNaoConfere", "Senha atual não confere!", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "ValidacaoErroNovaSenhaObrigatoria", "O campo Nova senha deve ter de 6 a 30 caracteres!", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "ValidacaoErroRepeteSenhaObrigatoria", "O campo Repetir nova senha deve ter de 6 a 30 caracteres!", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "ValidacaoErroSenhasDiferentes", "O campo Nova senha e Repetir nova senha são diferentes!", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "AlterarSenhaTitulo", "Alterar Senha", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "AlterarSenhaSenhaAtual", "Senha atual", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "AlterarSenhaNovaSenha", "Nova senha", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "AlterarSenhaRepetirNovaSenha", "Repetir nova senha", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "AlterarSenhaBotao", "Altera", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "AlterarSenhaUsuariosDaRede", "Usuários da rede não podem alterar a senha!", "Tela de alteração de senha");
		criaSeNaoExistir(lista, "MenuBotaoSair", "Sair", "Menu");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioServicoNome", "O campo Nome deve ser preenchido!", "Formulário de Servico");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioServicoBrevedescricao", "O campo Breve Descrição deve ser preenchido!", "Formulário de Servico");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioServicoDescricaocompleta", "O campo Descrição Completa deve ser preenchido!", "Formulário de Servico");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUnidadeReferencia", "O campo referência deve ser preenchido!", "Formulário de Unidade");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUnidadeNome", "O campo nome deve ser preenchido!", "Formulário de Unidade");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUnidadeDescricao", "O campo Descrição deve ser preenchido!", "Formulário de Unidade");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUnidadeTipoDescricao", "O campo Descrição deve ser preenchido!", "Formulário de UnidadeTipo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioDocumentoNumero", "O campo numero deve ser preenchido!", "Formulário de Documento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioDocumentoNome", "O campo Nome deve ser preenchido!", "Formulário de Documento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioDocumentoDescricao", "O campo Descrição deve ser preenchido!", "Formulário de Documento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioEnderecoLogradouro", "O campo Logradouro deve ser preenchido!", "Formulário de Endereco");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioEnderecoNumero", "O campo Número deve ser preenchido!", "Formulário de Endereco");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioEnderecoBairro", "O campo Bairro deve ser preenchido!", "Formulário de Endereco");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioEnderecoCep", "O campo CEP deve ser preenchido!", "Formulário de Endereco");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioEnderecoCidade", "O campo Cidade deve ser preenchido!", "Formulário de Endereco");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioEnderecoUf", "O campo UF deve ser preenchido!", "Formulário de Endereco");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioFormaSolicitacaoTipoDescricao", "O campo Descrição deve ser preenchido!", "Formulário de FormaSolicitacaoTipo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioFormularioNome", "O campo nome deve ser preenchido!", "Formulário de Formulario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioFormularioDescricao", "O campo Descrição deve ser preenchido!", "Formulário de Formulario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioLegislacaoTitulo", "O campo Título deve ser preenchido!", "Formulário de Legislacao");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioLegislacaoLei", "O campo Lei deve ser preenchido!", "Formulário de Legislacao");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioLegislacaoDescricao", "O campo Descrição deve ser preenchido!", "Formulário de Legislacao");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProcedimentoNome", "O campo nome deve ser preenchido!", "Formulário de Procedimento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioProcedimentoDescricao", "O campo Descrição deve ser preenchido!", "Formulário de Procedimento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioRequisitoNome", "O campo nome deve ser preenchido!", "Formulário de Requisito");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioRequisitoDescricao", "O campo Descrição deve ser preenchido!", "Formulário de Requisito");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioTributoNome", "O campo nome deve ser preenchido!", "Formulário de Tributo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioTributoDescricao", "O campo Descrição deve ser preenchido!", "Formulário de Tributo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioTipoLogradouroDescricao", "O campo Descrição deve ser preenchido!", "Formulário de TipoLogradouro");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioLogin", "O campo Login deve ser preenchido!", "Formulário de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioNome", "O campo Nome deve ser preenchido!", "Formulário de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioSenha", "O campo Senha deve ser preenchido!", "Formulário de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioEmail", "O campo E-Mail deve ser preenchido!", "Formulário de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioCpf", "O campo CPF deve ser preenchido!", "Formulário de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioRg", "O campo RG deve ser preenchido!", "Formulário de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioLdap", "O campo LDAP deve ser preenchido!", "Formulário de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioUsuarioLocal", "O campo Local de Trabalho deve ser preenchido!", "Formulário de Usuario");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioGrupoNome", "O campo Nome deve ser preenchido!", "Formulário de Grupo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioGrupoEntidade", "O campo Entidade deve ser preenchido!", "Formulário de Grupo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioGrupoAcao", "O campo Ação deve ser preenchido!", "Formulário de Grupo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioGrupoTipo", "O campo tipo deve ser preenchido!", "Formulário de Grupo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioGrupoDescricao", "O campo Descrição deve ser preenchido!", "Formulário de Grupo");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioPermissionamentoServico", "O campo Servico deve ser preenchido!", "Formulário de Permissionamento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioPermissionamentoGrupo", "O campo Grupo deve ser preenchido!", "Formulário de Permissionamento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioPermissionamentoUsuario", "O campo Usuário deve ser preenchido!", "Formulário de Permissionamento");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaLogin", "O campo Login deve ser preenchido!", "Formulário de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaNome", "O campo Nome deve ser preenchido!", "Formulário de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroDataObrigatoriaHistoricoDoSistemaDatahora", "O campo Data / Hora deve ser preenchido!", "Formulário de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaLocal", "O campo Local deve ser preenchido!", "Formulário de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaAcao", "O campo Ação deve ser preenchido!", "Formulário de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioHistoricoDoSistemaDados", "O campo Dados deve ser preenchido!", "Formulário de HistoricoDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMensagensDoSistemaPropriedade", "O campo Propriedade deve ser preenchido!", "Formulário de MensagensDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMensagensDoSistemaValor", "O campo Valor deve ser preenchido!", "Formulário de MensagensDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioMensagensDoSistemaTela", "O campo Tela deve ser preenchido!", "Formulário de MensagensDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioConfiguracoesDoSistemaPropriedade", "O campo Propriedade deve ser preenchido!", "Formulário de ConfiguracoesDoSistema");
		criaSeNaoExistir(lista, "ValidacaoErroPreenchimentoObrigatorioConfiguracoesDoSistemaValor", "O campo Valor deve ser preenchido!", "Formulário de ConfiguracoesDoSistema");
		
		
		//mensagens genericas para serem usadas em varios locais
		//CRIAR EM ORDEM ALFABETICA
		criaSeNaoExistir(lista, "genericoAcao", "Ação", "Generico");
		criaSeNaoExistir(lista, "genericoCartaServico", "Carta de Serviços", "Generico");
		criaSeNaoExistir(lista, "genericoDescricao", "Descrição", "Generico");
		criaSeNaoExistir(lista, "genericoDetalhe", "Detalhe do Serviço", "Generico");
		criaSeNaoExistir(lista, "genericoDocumento", "Documento", "Generico");
		criaSeNaoExistir(lista, "genericoDoSevico", " do Serviço", "Generico");
		criaSeNaoExistir(lista, "genericoFormaSolicitacao", "Forma Solicitação", "Generico");
		criaSeNaoExistir(lista, "genericoFormulario", "Formulario", "Generico");
		criaSeNaoExistir(lista, "genericoId", "ID", "Generico");
		criaSeNaoExistir(lista, "genericoLei", "Lei", "Generico");
		criaSeNaoExistir(lista, "genericoListados", "Listado", "Generico");
		criaSeNaoExistir(lista, "genericoListadas", "Listada", "Generico");
		criaSeNaoExistir(lista, "genericoLegislacao", "Legislação", "Generico");
		criaSeNaoExistir(lista, "genericoNome", "Nome", "Generico");
		criaSeNaoExistir(lista, "genericoNomeExtensao", "Extensão", "Generico");
		criaSeNaoExistir(lista, "genericoNumero", "Número", "Generico");
		criaSeNaoExistir(lista, "genericoObs", "Observação", "Generico");
		criaSeNaoExistir(lista, "genericoSigla", "Sigla", "Generico");
		criaSeNaoExistir(lista, "genericoReferencia", "Referência", "Generico");
		criaSeNaoExistir(lista, "genericoPreco", "Preço", "Generico");
		criaSeNaoExistir(lista, "genericoProcedimento", "Procedimento", "Generico");
		criaSeNaoExistir(lista, "genericoRequisito", "Requisito", "Generico");
		criaSeNaoExistir(lista, "genericoTipo", "Tipo", "Generico");
		criaSeNaoExistir(lista, "genericoTitulo", "Título", "Generico");
		criaSeNaoExistir(lista, "genericoTributo", "Tributo", "Generico");
		criaSeNaoExistir(lista, "genericoTempoAtendimento", "Tempo de Atendimento", "Generico");
		criaSeNaoExistir(lista, "genericoTempoResposta", "Tempo de Resposta", "Generico");
		criaSeNaoExistir(lista, "genericoUnidade", "Unidade", "Generico");
		criaSeNaoExistir(lista, "genericoUnidadeAtendimento", "Unidades que possuem o", "Generico");
		criaSeNaoExistir(lista, "genericoVoltar", "Voltar", "Generico");
		criaSeNaoExistir(lista, "tipoUsuarioInsercao", "Inserir um novo tipo usuário", "TipoUsuario");
		criaSeNaoExistir(lista, "tipoUsuarioListaVazia", "Não foram encontrados registros para Tipo de usuários", "TipoUsuario");
		criaSeNaoExistir(lista, "tipoUsuarioListaNome", "nome", "Lista de Tributo");
		criaSeNaoExistir(lista, "tipoUsuarioFormularioTituloDaPagina", "Cadastro de Tipo de Usuário", "Formulário de Tipo usuario");
		criaSeNaoExistir(lista, "tipoUsuarioFormularioNome", "nome", "Formulário de tipo de usuario");
		criaSeNaoExistir(lista, "tipoUsuarioFormularioBotaoInsere", "Insere", "Formulário de tipoUsuario");
		criaSeNaoExistir(lista, "tipoUsuarioFormularioBotaoAltera", "Altera", "Formulário de tipoUsuario");
	}

	private void criaSeNaoExistir(List<MensagensDoSistemaModel> lista, String propriedade, String valor, String tela) {
		boolean existe = false;
		for (MensagensDoSistemaModel prop : lista) {
			if (prop.getPropriedade().equals(propriedade)) {
				existe = true;
				break;
			}
		}
		if (!existe) {
			MensagensDoSistemaModel prop = new MensagensDoSistemaModel();
			prop.setPropriedade(propriedade);
			prop.setValor(valor);
			prop.setTela(tela);
			manager.persist(prop);
		}
	}

}