<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:templatefechado fluido="true" title="FormularioDeUsuario">
    <title>${usuario.id == 0 ? 'Adicionar' : 'Editar'} Usuário</title>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="form-container">
                <h1 class="text-center">${usuario.id == 0 ? 'Adicionar' : 'Editar'} Usuário</h1>
                <form action=" /administracao/usuarios/editSenha/${usuario.id}" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <div class="form-group">
                        <label for="username">Username:</label>
                        <input type="text" style="display:hidden;" class="form-control" id="username" name="username" value="${usuario.username}" >
                        <label for="username">Nova Senha:</label>
                        <input type="text" style="display:hidden;" class="form-control" id="senha" name="senha" value="" required>
                        <label for="username">Confirma Senha:</label>
                        <input type="text" style="display:hidden;" class="form-control" id="ConfirmaSenhasenha" name="confirmaSenha" value="" required>
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
