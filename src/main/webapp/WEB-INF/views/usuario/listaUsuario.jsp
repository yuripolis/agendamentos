<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:templatefechado fluido="true" title="listaDeUsuario">
    <title>Lista de Usuários</title>
    <h1>Lista de Usuários</h1>
    <table class="table">
        <thead>
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
                    <td>${usuario.id}</td>
                    <td>${usuario.username}</td>
                    <td>
                        <c:forEach var="group" items="${usuario.authorities}">
                            ${group.name}
                        </c:forEach>
                    </td>
                    <td>
						<a href="/administracao/usuarios/resetSenha/${usuario.id}" class="btn btn-warning text-white btn-sm modal-redefinir-senha-link"><i class="fas fa-key"></i></a>
                        <a href="/administracao/usuarios/edit/${usuario.id}" class="btn btn-success btn-sm">Editar</a>
                        <a href="/administracao/usuarios/delete/${usuario.id}" class="btn btn-danger btn-sm">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="/administracao/usuarios/add" class="btn btn-primary">Adicionar Novo Usuário</a>
</t:templatefechado>
