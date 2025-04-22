<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:templatefechado
    fluido="true"
    title="${agenda.id == 0 ? 'Adicionar Sessão' : 'Editar Sessão'}">

    <div class="col-md-8 mx-auto">
        <div class="card shadow-sm">
            <div class="card-header bg-info text-white">
                <h5 class="mb-0">${agenda.id == 0 ? 'Adicionar Nova Sessão' : 'Editar Sessão'}</h5>
            </div>

            <div class="card-body">${agenda.id}
                <form action="${'/administracao/usuarios/1/agenda/adicionar'}" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                    <div class="mb-3">
                        <label for="titulo" class="form-label">Título</label>
                        <input type="text" class="form-control" id="titulo" name="titulo" value="${agenda.titulo}" required>
                    </div>

                    <div class="mb-3">
                        <label for="inicio" class="form-label">Início</label>
                        <input type="datetime-local" class="form-control" id="inicio" name="inicio"
                               value="<fmt:formatDate value='${agenda.inicio}' pattern='yyyy-MM-dd\'T\'HH:mm'/>" required>
                    </div>

                    <div class="mb-3">
                        <label for="fim" class="form-label">Fim</label>
                        <input type="datetime-local" class="form-control" id="fim" name="fim"
                               value="<fmt:formatDate value='${agenda.fim}' pattern='yyyy-MM-dd\'T\'HH:mm'/>" required>
                    </div>

                    <div class="mb-3">
                        <label for="anotacoes" class="form-label">Anotações</label>
                        <textarea class="form-control" id="anotacoes" name="anotacoes" rows="4">${agenda.anotacoes}</textarea>
                    </div>

                    <button type="submit" class="btn btn-success w-100">${agenda.id == 0 ? 'Adicionar Sessão' : 'Salvar Alterações'}</button>
                    <a href="/administracao/agenda" class="btn btn-secondary w-100 mt-2">Voltar para Agenda</a>
                </form>
            </div>
        </div>
    </div>

</t:templatefechado>
