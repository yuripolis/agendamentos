<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:templatefechado fluido="true" title="Formulário de Usuário">
    <div class="col-md-8 offset-md-2">
        <div class="card shadow-sm">
            <div class="card-header bg-info text-white">
                <h4 class="mb-0">
                    ${usuario.id == 0 ? 'Adicionar' : 'Editar'} Usuário
                </h4>
            </div>
            <div class="card-body">
                <form method="post"
                    action="<c:choose>
                                <c:when test='${usuario.id == 0}'>/administracao/usuarios/add</c:when>
                                <c:otherwise>/administracao/usuarios/edit/${usuario.id}</c:otherwise>
                            </c:choose>">

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                    <div class="mb-3">
                        <label for="username" class="form-label">Username:</label>
                        <input type="text" class="form-control" id="username" name="username"
                            value="${usuario.username}" required />
                    </div>

                    <!-- Hidden password field for compatibility with validation -->
                    <input type="password" class="form-control d-none" id="password" name="password" value="" />

					<div class="form-group mt-3">
						<label for="tipoUsuario">Tipo de Usuário:</label> <select
							id="tipoUsuario" name="tipoUsuario.id" class="form-control"
							required>
							<option value="">Selecione...</option>
							<c:forEach var="tipo" items="${tipoUsuario}">
								<option value="${tipo.id}"
									<c:if test="${usuario.tipoUsuario != null && usuario.tipoUsuario.id == tipo.id}">selected</c:if>>
									${tipo.tipo}</option>
							</c:forEach>
						</select>
					</div>


					<div class="mb-3">
    <label class="form-label">Grupos:</label>
    <div class="row">
        <div class="col-md-5">
            <label class="form-label">Disponíveis</label>
            <select multiple class="form-select" id="available-groups" size="10">
                <c:forEach var="group" items="${groups}">
                    <c:if test="${!usuario.authorities.contains(group)}">
                        <option value="${group.id}">${group.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

         <div class="col-2 d-flex flex-column justify-content-center align-items-center gap-2">
            <button type="button" class="btn btn-outline-primary w-100" id="add-group" title="Adicionar">
                <i class="fas fa-chevron-right me-1"></i> Adicionar
            </button>
            <button type="button" class="btn btn-outline-danger w-100" id="remove-group" title="Remover">
                <i class="fas fa-chevron-left me-1"></i> Remover
            </button>
        </div>

        <div class="col-md-5">
            <label class="form-label">Selecionados</label>
            <select multiple class="form-select" id="selected-groups" name="authorities" size="10">
                <c:forEach var="group" items="${usuario.authorities}">
                    <option value="${group.id}" selected>${group.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
</div>


                    <div class="d-grid gap-2 mt-4">
                        <button type="submit" class="btn btn-success">
                            ${usuario.id == 0 ? 'Adicionar' : 'Salvar'}
                        </button>
                        <a href="/administracao/usuarios" class="btn btn-secondary">Voltar para Lista</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
    document.addEventListener("DOMContentLoaded", function () {
        const available = document.getElementById("available-groups");
        const selected = document.getElementById("selected-groups");

        function moveOptions(from, to) {
            const selectedOptions = Array.from(from.selectedOptions);
            selectedOptions.forEach(opt => {
                const clone = opt.cloneNode(true);
                to.appendChild(clone);
                from.removeChild(opt);
            });
        }

        document.getElementById("add-group").addEventListener("click", () => {
            moveOptions(available, selected);
        });

        document.getElementById("remove-group").addEventListener("click", () => {
            moveOptions(selected, available);
        });

        document.querySelector("form").addEventListener("submit", () => {
            Array.from(selected.options).forEach(opt => opt.selected = true);
        });
    });
</script>

</t:templatefechado>
