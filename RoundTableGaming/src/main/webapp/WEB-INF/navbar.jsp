<!-- CSS only -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">

<link rel="stylesheet" href="css/stylesheet.css" type="text/css">

<!-- JS, Popper.js, and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
	integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
	integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
	crossorigin="anonymous"></script>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<c:choose>
	<c:when test="${not empty loggedInUser}">
		<nav
			class="navbar navbar-expand-sm navbar-light fixed-top navbar-custom">
			<button class="navbar-toggler nav-btn" type="button"
				data-toggle="collapse" data-target="#navbar5">
				<span class="navbar-toggler-icon"></span>
			</button>
			<a href="/" class="navbar-brand nav-btn"><img class="logo" src="images/newRoundTableLogo.png"></a>
			<div class="navbar-collapse collapse justify-content-stretch"
				id="navbar5">
				<ul class="navbar-nav">
					<li class="nav-item active"><a class="nav-link " href="#">About
							<span class="sr-only">Home</span>
					</a></li>
					<li class="nav-item"><a href=""
						class="btn btn-default btn-rounded" data-toggle="modal"
						data-target="#darkModalForm">${loggedInUser.firstName}</a></li>
					<li class="nav-item"><a class="nav-link "
						href="${pageContext.request.contextPath}/logOut.do">Log Out</a></li>
					<li class="nav-item"><a class="nav-link "
						href="${pageContext.request.contextPath}/account.do">Account</a></li>
				</ul>
				<div class="search-box">
				<form class="mx-2 my-auto d-inline w-50">
					<div class="input-group">
						<input type="text" class="form-control" placeholder="Search Games">
						<span class="input-group-btn">
							<button class="btn btn-outline-secondary" type="button">GO</button>
						</span>
					</div>
				</form>
				</div>
			</div>

		</nav>
	</c:when>

	<c:otherwise>
		<nav
			class="navbar navbar-expand-sm navbar-light fixed-top navbar-custom">
			<button class="navbar-toggler nav-btn" type="button"
				data-toggle="collapse" data-target="#navbar5">
				<span class="navbar-toggler-icon"></span>
			</button>
			<a href="/" class="navbar-brand nav-btn"><img class="logo" src="images/newRoundTableLogo.png"></a>
			<div class="navbar-collapse collapse justify-content-stretch"
				id="navbar5">
				<ul class="navbar-nav">
					<li class="nav-item active"><a class="nav-link " href="#">About
							<span class="sr-only">Home</span>
					</a></li>


					<li class="nav-item"><a href=""
						class="btn btn-default btn-rounded" data-toggle="modal"
						data-target="#darkModalForm">Login</a></li>

					<li class="nav-item"><a class="nav-link "
						href="${pageContext.request.contextPath}/register.do">Register</a></li>


				</ul>
				<div class="search-box">
				<form class="mx-2 my-auto d-inline w-50 align-right">
					<div class="input-group">
						<input type="text" class="form-control" placeholder="Search Games">
						<span class="input-group-btn">
							<button class="btn btn-outline-secondary" type="button">GO</button>
						</span>
					</div>
				</form>
				</div>
			</div>

		</nav>
	</c:otherwise>
</c:choose>