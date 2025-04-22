<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:templatefechado fluido="true" title="Perfil">
	<title>${usuario.id == 0 ? 'Adicionar' : 'Editar'}Usuário</title>
	<div class="row justify-content-center">
		<div class="col-md-6">
			<div class="form-container">
				<h1 class="text-center">Perfil</h1>

				<!-- Display the profile picture if it exists -->
				<c:if test="${not empty usuario.profilePhoto}">
    <div class="text-center mb-3">
        <img src="/administracao/usuarios/${usuario.profilePhoto}" alt="Profile Photo" class="img-thumbnail" style="width: 150px; height: 150px;">
    </div>
</c:if>

				<c:if test="${not empty usuario.profilePhoto}">
					<div class="text-center mb-3">
						<img src="/api/profile-photo/${usuario.profilePhoto}"
							alt="Profile Photo" class="img-thumbnail"
							style="width: 150px; height: 150px;">
					</div>
				</c:if>
				<form action="/administracao/usuarios/perfil/${usuario.id}"
					method="post" enctype="multipart/form-data">

					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />

					<div class="form-group">
						<label for="username">Username:</label> <input type="text"
							class="form-control" id="username" name="username"
							value="${usuario.username}" required>
					</div>

					<div class="form-group">
						<label for="email">Email:</label> <input type="text"
							class="form-control" id="email" name="email"
							value="${usuario.email}" required>
					</div>

					<div class="form-group">
						<label for="phone">Telefone:</label> <input type="number"
							class="form-control" id="phone" name="phone"
							value="${usuario.phone}" required>
					</div>

					<div class="form-group">
						<label for="profilePhotoFile">Foto de Perfil:</label> <input
							type="file" class="form-control" id="profilePhotoFile"
							name="profilePhotoFile" accept="image/*">
					</div>

					<button type="submit" class="btn btn-submit btn-block btn-success">Salvar</button>
				</form>
			</div>
		</div>
	</div>
</t:templatefechado>


