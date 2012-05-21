<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="loginUrl" value="/login" />
<c:url var="logoutUrl" value="/j_spring_security_logout" />

<html lang="en">
	<head>
		<title>Aggro's Towne BBS</title>
	</head>
	<body>
		<header>
			<h1>Aggro's Towne BBS</h1>
		</header>
		
		<section>
			<header>
				<h2>Message of the day</h2>
			</header>
			<c:choose>
				<c:when test="${not empty motd}">
					<c:out value="${motd.htmlText}" escapeXml="false" />
				</c:when>
				<c:otherwise>
					<p>[Message unavailable]</p>
				</c:otherwise>
			</c:choose>
		</section>
		<section>
			<header>
				<h2>Important messages</h2>
			</header>
			<c:choose>
				<%-- Empty means no messages; null means no service --%>
				<c:when test="${importantMessages != null}">
					<c:forEach var="message" items="${importantMessages}">
						<div style="margin:20px 0">
							<c:out value="${message.htmlText}" escapeXml="false" />
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<p>[Important messages unavailable]</p>
				</c:otherwise>
			</c:choose>
		</section>
	</body>
</html>
