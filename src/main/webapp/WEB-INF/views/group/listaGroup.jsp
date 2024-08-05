<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:templatefechado fluido="true" title="Grupos">
    <h1>Grupos</h1>
    <table class="table">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Ações</th>
        </tr>
        <c:forEach var="group" items="${groups}">
            <tr>
                <td>${group.id}</td>
                <td>${group.name}</td>
                <td>
                    <a href="/administracao/groups/edit/${group.id}" class="btn btn-success btn-sm">Editar</a>
                    <a href="/administracao/groups/remove/${group.id}" class="btn btn-danger btn-sm">Remover</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="/administracao/groups/add" class="btn btn-primary">Adicionar Novo Grupo</a>
</t:templatefechado>
