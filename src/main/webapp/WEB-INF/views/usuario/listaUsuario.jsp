<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:templatefechado
    fluido="true"
    title="Usuários"
    cssFiles="datatables/datatables.min.css"
    jsFiles="moment.min.js,datatables/datatables.min.js,datatables/datetime-moment.js,datatables-init.js">

    <div class="col">
        <div class="card w-100 shadow-sm">
            <div class="card-header bg-info d-flex justify-content-between align-items-center">
                <h4 class="text-white mb-0">Lista de Usuários</h4>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="btn btn-light btn-sm" href="/administracao/usuarios/add" title="Adicionar Novo Usuário">
                        <i class="fa fa-plus me-1"></i> Novo
                    </a>
                </sec:authorize>
            </div>

            <div class="card-body">
                <c:if test="${not empty usuarios}">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover dt-table">
                            <thead class="table-light text-center">
                                <tr>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Grupos</th>
                                    <th>Ações</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="usuario" items="${usuarios}">
                                    <tr>
                                        <td class="text-center">${usuario.id}</td>
                                        <td class="text-center">${usuario.username}</td>
                                        <td class="text-center">
                                            <c:forEach var="group" items="${usuario.authorities}">
                                                <span class="badge bg-secondary">${group.name}</span>
                                            </c:forEach>
                                        </td>
                                        <td class="text-center text-nowrap">
                                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                                <a href="/administracao/usuarios/resetSenha/${usuario.id}" 
                                                   class="btn btn-warning text-white btn-sm modal-redefinir-senha-link" 
                                                   title="Redefinir Senha">
                                                    <i class="fas fa-key"></i>
                                                </a>
                                                <a href="/administracao/usuarios/edit/${usuario.id}" 
                                                   class="btn btn-primary btn-sm" 
                                                   title="Editar">
                                                    <i class="fas fa-pencil-alt"></i>
                                                </a>
                                                <a href="/administracao/usuarios/delete/${usuario.id}" 
                                                   class="btn btn-danger btn-sm modal-excluir-link" 
                                                   title="Excluir" 
                                                   data-id="${usuario.id}" 
                                                   data-descricao="${usuario.username}">
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

                <c:if test="${empty usuarios}">
                    <div class="alert alert-info text-center">
                        Nenhum usuário encontrado.
                    </div>
                </c:if>
            </div>
        </div>
    </div>

</t:templatefechado>
