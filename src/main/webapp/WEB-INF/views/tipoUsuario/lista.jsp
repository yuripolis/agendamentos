<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ page import="calendario.agendamentos.urls.ListaDeURLs" %>

<my:templatefechado
    fluido="true"
    title="Tipo de Usuário"
    cssFiles="datatables/datatables.min.css"
    jsFiles="moment.min.js,datatables/datatables.min.js,datatables/datetime-moment.js,datatables-init.js">

    <div class="col">
        <div class="card w-100 shadow-sm">
            <div class="card-header bg-info d-flex justify-content-between align-items-center">
                <h4 class="text-white mb-0">
                    ${mensagens.get('tipoUsuarioInsercao').valor}
                </h4>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <c:url var="urlInserir" value="<%= ListaDeURLs.TIPO_USUARIO_INSERIR %>" />
                    <a class="btn btn-light btn-sm" href="${urlInserir}" title="Adicionar">
                        <i class="fa fa-plus me-1"></i> Novo
                    </a>
                </sec:authorize>
            </div>

            <div class="card-body">
                <c:if test="${not empty tipoUsuarios}">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover dt-table">
                            <thead class="table-light">
                                <tr>
                                    <th class="text-center">${mensagens.get('tipoUsuarioListaNome').valor}</th>
                                    <th class="text-center">
									${mensagens.get('GrupoListaAcoes').valor}</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="tipoUsuario" items="${tipoUsuarios}">
                                    <tr>
                                        <td class="text-center">${tipoUsuario.tipo}</td>
                                        <td class="text-center text-nowrap"><sec:authorize
											access="hasRole('ROLE_ADMIN')">

											<c:url var="url"
												value="<%=ListaDeURLs.TIPO_USUARIO_ALTERAR%>" />
												<c:url var="urlDelete"
												value="<%=ListaDeURLs.TIPO_USUARIO_REMOVER%>" />
												<a class="btn btn-primary btn-sm" href="${url}/${tipoUsuario.id}"><i
													class="fas fa-pencil-alt"></i></a>
												<a
													class="btn btn-danger text-white btn-sm modal-excluir-link"
													href="${urlDelete}/${tipoUsuario.id}" data-id="${tipoUsuario.id}"
													data-descricao="${tipoUsuario.tipo}"><i class="fas fa-times"></i></a>

										</sec:authorize></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>

                <c:if test="${empty tipoUsuarios}">
                    <div class="alert alert-info text-center">
                        ${mensagens.get('tipoUsuarioListaVazia').valor}
                    </div>
                </c:if>
            </div>
        </div>
    </div>

</my:templatefechado>
