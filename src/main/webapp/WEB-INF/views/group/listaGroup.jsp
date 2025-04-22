<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ page import="calendario.agendamentos.urls.ListaDeURLs" %>

<t:templatefechado
    fluido="true"
    title="Grupos"
    cssFiles="datatables/datatables.min.css"
    jsFiles="moment.min.js,datatables/datatables.min.js,datatables/datetime-moment.js,datatables-init.js">

    <div class="col">
        <div class="card w-100 shadow-sm">
            <div class="card-header bg-info d-flex justify-content-between align-items-center">
                <h4 class="text-white mb-0">${mensagens.get('grupoTitulo').valor}</h4>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <c:url var="urlAdicionar" value="/administracao/groups/add" />
                    <a class="btn btn-light btn-sm" href="${urlAdicionar}" title="Adicionar">
                        <i class="fa fa-plus me-1"></i> Novo
                    </a>
                </sec:authorize>
            </div>

            <div class="card-body">
                <c:if test="${not empty groups}">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover dt-table">
                            <thead class="table-light">
                                <tr>
                                    <th class="text-center">ID</th>
                                    <th class="text-center">${mensagens.get('grupoNome').valor}</th>
                                    <th class="text-center">${mensagens.get('GrupoListaAcoes').valor}</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="group" items="${groups}">
                                    <tr>
                                        <td class="text-center">${group.id}</td>
                                        <td class="text-center">${group.name}</td>
                                        <td class="text-center text-nowrap">
                                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                                <a class="btn btn-primary btn-sm" href="/administracao/groups/edit/${group.id}">
                                                    <i class="fas fa-pencil-alt"></i>
                                                </a>
                                                <a class="btn btn-danger text-white btn-sm modal-excluir-link"
                                                   href="/administracao/groups/remove/${group.id}"
                                                   data-id="${group.id}"
                                                   data-descricao="${group.name}">
                                                    <i class="fas fa-times"></i>
                                                </a>
                                            </sec:authorize>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>

                <c:if test="${empty groups}">
                    <div class="alert alert-info text-center">
                        ${mensagens.get('grupoListaVazia').valor}
                    </div>
                </c:if>
            </div>
        </div>
    </div>

</t:templatefechado>
