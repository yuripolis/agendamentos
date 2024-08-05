<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card mt-5">
                    <div class="card-header text-center">
                        <h2><i class="fa fa-circle-right"></i> Cadastro de agenda</h2>
                    </div>
                    <div class="card-body">
                        <form action="<c:url value='/efetuarLogin' />" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <div class="form-group">
                                <label for="username"><i class="fa fa-user"></i> E-Mail</label>
                                <input id="username" type="text" name="username" placeholder="E-Mail" required class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="password"><i class="fa fa-lock"></i> Senha</label>
                                <input id="password" type="password" name="password" placeholder="Senha" required class="form-control">
                            </div>
                            <button type="submit" class="btn btn-primary btn-block">
                                <div class="d-flex justify-content-center align-items-center">
                                    <span class="mr-2">ENVIAR</span>
                                    <i class="fa fa-arrow-right"></i>
                                </div>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
