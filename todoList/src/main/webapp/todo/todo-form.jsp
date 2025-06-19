<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>User Management Application</title>


<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">

</head>

</head>
<body>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: #C0C9EE">
			<div>
				<a href="#" class="navbar-brand"> Todo
					App</a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Todos</a></li>
			</ul>

			<ul class="navbar-nav navbar-collapse justify-content-end">
				<li><a href="<%=request.getContextPath()%>/logout"
					class="nav-link">Logout</a></li>
			</ul>
		</nav>
	</header>
	<div class="container col-md-5">
        <div class="card">
            <div class="card-body">

                <c:choose>
                    <c:when test="${todo != null}">
                        <form action="update" method="post">
                    </c:when>
                    <c:otherwise>
                        <form action="insert" method="post">
                    </c:otherwise>
                </c:choose>

                    <caption>
                        <h2>
                            <c:if test="${todo != null}">Edit Todo</c:if>
                            <c:if test="${todo == null}">Add New Todo</c:if>
                        </h2>
                    </caption>

                    <c:if test="${todo != null}">
                        <input type="hidden" name="id" value="<c:out value='${todo.id}' />" />
                    </c:if>

                    <fieldset class="form-group">
                        <label>Todo Title</label>
                        <input type="text" value="<c:out value='${todo.title}' />"
                               class="form-control" name="title" required="required" minlength="5">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Todo Description</label>
                        <input type="text" value="<c:out value='${todo.description}' />"
                               class="form-control" name="description" minlength="5">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Todo Status</label>
                        <select class="form-control" name="status">
                            <option value="false" ${todo != null && !todo.status ? 'selected' : ''}>In Progress</option>
                            <option value="true" ${todo != null && todo.status ? 'selected' : ''}>Complete</option>
                        </select>
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Todo Target Date</label>
                        <input type="date" value="<c:out value='${todo.targetDate}' />"
                               class="form-control" name="targetDate" required="required">
                    </fieldset>

                    <button type="submit" class="btn btn-success">Save</button>
                </form>
            </div>
        </div>
    </div>

	<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>