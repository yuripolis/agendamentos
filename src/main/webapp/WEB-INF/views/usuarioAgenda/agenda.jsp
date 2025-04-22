<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:templatefechado fluido="true" title="Agenda do Usuário" cssFiles="" jsFiles="">
	<div class="col">
		<div class="card w-100 shadow-sm">
			<div class="card-header bg-info d-flex justify-content-between align-items-center">
				<h4 class="text-white mb-0">Agenda do Usuário</h4>
				<a class="btn btn-light btn-sm" href="/administracao/agenda/adicionar">
					<i class="fa fa-plus me-1"></i> Nova Sessão
				</a>
			</div>
			<div class="card-body">
				<c:if test="${not empty agenda}">
					<div class="table-responsive">
						<table class="table table-bordered table-hover dt-table">
							<thead class="table-light">
								<tr>
									<th>Título</th>
									<th>Início</th>
									<th>Fim</th>
									<th>Anotações</th>
									<th class="text-center">Ações</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${agenda}">
									<tr>
										<td>${item.titulo}</td>
										<td><fmt:formatDate value="${item.inicio}" pattern="dd/MM/yyyy HH:mm"/></td>
										<td><fmt:formatDate value="${item.fim}" pattern="dd/MM/yyyy HH:mm"/></td>
										<td>${item.anotacoes}</td>
										<td class="text-center text-nowrap">
											<a href="/administracao/agenda/editar/${item.id}" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>
											<a href="/administracao/agenda/remover/${item.id}" class="btn btn-danger btn-sm modal-excluir-link" data-id="${item.id}" data-descricao="${item.titulo}">
												<i class="fas fa-times"></i>
											</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				<c:if test="${empty agendaList}">
					<div class="alert alert-info text-center">Nenhuma sessão encontrada na agenda.</div>
				</c:if>
			</div>
		</div>
	</div>
</t:templatefechado>
