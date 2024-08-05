<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:templatefechado fluido="true" title="FormularioDeUsuario">
    <title>${usuario.id == 0 ? 'Adicionar' : 'Editar'} Usuário</title>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="form-container">
                <h1 class="text-center">${usuario.id == 0 ? 'Adicionar' : 'Editar'} Usuário</h1>
                <form action="<c:choose>
                  <c:when test="${usuario.id == 0}">
                    /administracao/usuarios/add
                  </c:when>
                  <c:otherwise>
                    /administracao/usuarios/edit/${usuario.id}
                  </c:otherwise>
              </c:choose>" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" class="form-control" id="username" name="username" value="${usuario.username}" required>
    </div>
        <input type="password" style="display:none;" class="form-control" id="password" name="password" value="" placeholder="Leave blank if unchanged">
    <div class="form-group">
        <label for="groups">Grupos:</label>
        <div class="row">
            <div class="col">
                <select multiple class="form-control" id="available-groups" size="10">
                    <c:forEach var="group" items="${groups}">
                        <option value="${group.id}">${group.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2 d-flex flex-column justify-content-center">
                <button type="button" class="btn btn-primary mb-2" id="add-group">Adicionar &gt;&gt;</button>
                <button type="button" class="btn btn-danger" id="remove-group">&lt;&lt; Remover</button>
            </div>
            <div class="col">
                <select multiple class="form-control" id="selected-groups" name="authorities" size="10">
                    <c:forEach var="group" items="${usuario.authorities}">
                        <option value="${group.id}" selected>${group.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <button type="submit" class="btn btn-submit btn-block">${usuario.id == 0 ? 'Adicionar' : 'Salvar'}</button>
</form>
                <a href="/administracao/usuarios" class="btn btn-secondary btn-block mt-3">Ver Usuários</a>
            </div>
        </div>
    </div>

    <!-- Include necessary JavaScript libraries -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#add-group').click(function() {
                $('#available-groups option:selected').each(function() {
                    $('#selected-groups').append($(this).clone());
                    $(this).remove();
                });
            });

            $('#remove-group').click(function() {
                $('#selected-groups option:selected').each(function() {
                    $('#available-groups').append($(this).clone());
                    $(this).remove();
                });
            });

            $('form').submit(function() {
                $('#selected-groups option').prop('selected', true);
            });
        });
    </script>
</t:templatefechado>
