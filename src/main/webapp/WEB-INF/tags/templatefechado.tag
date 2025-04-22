<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="title" required="true"%>
<%@ attribute name="fluido" required="true" type="java.lang.Boolean"%>
<%@ attribute name="cssFiles" required="false"%>
<%@ attribute name="jsFiles" required="false"%>
<%@ attribute name="refresh" required="false"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>${title}</title>

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- FontAwesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<!-- Custom Template CSS -->
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/templateFechadoCSS.css'/>">

<c:forEach items="${cssFiles}" var="file">
	<c:url var="url" value="/resources/css/${file}" />
	<link rel="stylesheet" href="${url}">
</c:forEach>
</head>

<body>
	<jsp:include page="/WEB-INF/views/menu.jsp" />
	<div class="container">
		<div class="row mb-2" id="mensagens">
			<my:erros/>
			<my:sucesso/>
			<my:alerta/>
		</div>
		<div class="row justify-content-center">
			<jsp:doBody />
		</div>
		<br/>
	</div>

	<!-- Base URL variable for JS -->
	<script type="text/javascript">
		var urlPadrao = '<c:url value="/"/>';
	</script>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

	<!-- Extra page-specific JS -->
	<c:forEach items="${jsFiles}" var="file">
		<c:url var="url" value="/resources/js/${file}" />
		<script src="${url}"></script>
	</c:forEach>
</body>
</html>
