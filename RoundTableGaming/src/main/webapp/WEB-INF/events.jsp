<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/events.css" type="text/css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Events</title>
<link rel="icon" 
      type="image/png" 
      href="img/favicon.ico">
</head>
<body>

	<div class="navbar">
		<jsp:include page="navbar.jsp" />
		<jsp:include page="login-popout.jsp"></jsp:include>
		<br>
	</div>



	<div class="game-list">
		<table class="table table-dark table-hover">
			<thead>
				<tr>
					
						<th scope="col">Upcoming Events</th>
				
					
						<th scope="col">Date</th>
					

					<th scope="col">Event Games</th>
					<th scope="col">Location <th>


				</tr>
			</thead>
			<tbody>
				<c:forEach var="event" items="${futureEvents}">
					<tr>

						<td><a href="singleEvent.do?id=${event.id}">${event.title}</a><p>${event.address.city}, ${event.address.state} </p></td>
						<td>${event.eventDate }</td>
						<td>
								<c:forEach var="eventGames" items="${event.eventGames}">
							${eventGames.game.title }<br>
								</c:forEach>
							</td>
							<td><img width="150px" src="https://maps.googleapis.com/maps/api/staticmap?center=${event.address.zipCode}&zoom=5&size=150x150&markers=${event.address.zipCode}&key=AIzaSyBGkwnAWsK1Xff-R9G3nurccb9Wxt3d8R8">
							
							
							
							</td>
				</c:forEach>
				
				<tr>
				<tr>

				</tr>
			</tbody>
		</table>

	
	
 
	<div class="game-list">
		<table class="table table-dark table-hover" style="margin-top: 0px;">
			<thead>
				<tr>
					
						<th scope="col">Past Events</th>
				
					
						<th scope="col">Date</th>
					

					<th scope="col">Event Games</th>
					<th scope="col">Location <th>


				</tr>
			</thead>
			<tbody>
				<c:forEach var="event" items="${pastEvents}">
					<tr>

						<td><a href="singleEvent.do?id=${event.id}">${event.title}</a><p>${event.address.city}, ${event.address.state} </p></td>
						<td>${event.eventDate }</td>
						<td>
								<c:forEach var="eventGames" items="${event.eventGames}">
							${eventGames.game.title }<br>
								</c:forEach>
							</td>
							<td><img width="150px" src="https://maps.googleapis.com/maps/api/staticmap?center=${event.address.zipCode}&zoom=5&size=150x150&markers=${event.address.zipCode}&key=AIzaSyBGkwnAWsK1Xff-R9G3nurccb9Wxt3d8R8">
							
							
							
							</td>
				</c:forEach>
				
				<tr>
				<tr>

				</tr>
			</tbody>
		</table>

<img src="https://maps.googleapis.com/maps/api/staticmap?center=us&zoom=3&size=800x800&markers=${zipCodes}&key=AIzaSyBGkwnAWsK1Xff-R9G3nurccb9Wxt3d8R8">
	
	
 

	</div>
</body>
</html>






















