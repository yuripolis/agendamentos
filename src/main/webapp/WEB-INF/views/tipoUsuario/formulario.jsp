<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ page import="calendario.agendamentos.urls.ListaDeURLs"%>
<c:set var="cssFiles"
	value="bootstrap/bootstrap-toggle.min.css,bootstrap/select2.min.css,bootstrap/select2-bootstrap4.min.css" />
<c:set var="jsFiles"
	value="jquery/bootstrap-toggle.min.js,jquery/select2.min.js,jquery/select2-pt-BR.js,ObjetoIdValor.js,TributoFormulario.js" />
<my:templatefechado fluido="false"
	title="Novo tipo de usuário"
	cssFiles="${cssFiles}" jsFiles="${jsFiles}">
	<c:if test="${empty tributo.id}">
		<c:url var="url" value="<%=ListaDeURLs.TIPO_USUARIO_INSERIR%>" />
	</c:if>
	<c:if test="${not empty tributo.id}">
		<c:url var="url" value="<%=ListaDeURLs.TIPO_USUARIO_ALTERAR%>" />
	</c:if>
	<div class="col">
		<div class="card w-100">
			<div class="card-header bg-info">
				<h4 class="text-white mb-0">${mensagens.get('tipoUsuarioFormularioTituloDaPagina').valor}</h4>
			</div>
			<div class="card-body">
				<form class="msbc-validator-form" id="formulario-form"
					name="formulario" action="${url}" method="POST">
					<input type="hidden" id="${_csrf.parameterName}"
						name="${_csrf.parameterName}" value="${_csrf.token}" />
					<c:if test="${not empty tipoUsuario.id}">
						<input type="hidden" name="id" id="id" value="${tipoUsuario.id}" />
					</c:if>
					<div class="mb-3">
						<label class="col-form-label" for="tipo">${mensagens.get('tipoUsuarioFormularioNome').valor}:</label>
						<input class="form-control" name="tipo" id="tipo"
							value="${tipoUsuario.tipo}" maxlength="100" type="text" />
					</div>


					<div class="mb-3">
						<div class="col-md-4 offset-md-4">
							<c:if test="${empty tipoUsuario.id}">
								<input class="btn btn-success w-100" type="submit"
									value="${mensagens.get('tipoUsuarioFormularioBotaoInsere').valor}" />
							</c:if>
							<c:if test="${not empty tipoUsuario.id}">
								<input class="btn btn-success w-100" type="submit"
									value="${mensagens.get('tipoUsuarioFormularioBotaoAltera').valor}" />
							</c:if>
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>
</my:templatefechado>