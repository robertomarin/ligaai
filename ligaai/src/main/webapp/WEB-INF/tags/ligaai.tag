<%@ tag body-content="empty" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fai" uri="http://liga.ai/jstl/functions"%>
<%@ attribute name="ligaai" required="true" type="ai.liga.ligaai.model.LigaAi" %>
<%@ attribute name="avatarNomeDisabled" required="false" %>

<article class="ligaai">
	<c:if test="${!avatarNomeDisabled}">
		<div class="userPic">
			<a href="/u/conta/${ligaai.user.id}"><img src="/ligaai/avatar/${ligaai.user.pathAvatar}_80.jpg" class="userPic lazyImage" alt="Me liga ai!" onerror="javascript:showUnavailableImage(this, '80x80')"/></a>
		</div>
	</c:if>
	<div class="userInfo">
		<header>
			<hgroup>
			<c:if test="${!avatarNomeDisabled}">
				<h1><a name="${ligaai.id}" href="#${ligaai.id}"><c:out value="${ligaai.user.name}" escapeXml="false"/></a></h1>
			</c:if>
			<h3><c:out value="${fai:buildLink(ligaai.message)}" escapeXml="false"/></h3>
			<ul>
				<c:forEach items="${ligaai.contacts}" var="contact" varStatus="j">
				<li class="${fn:toLowerCase(contact.type)}">
					<c:if test="${fai:isLinkable(contact.type)}">
						<a href="${fai:getUrlContact(contact)}">${contact.content}</a>
					</c:if>
					<c:if test="${not fai:isLinkable(contact.type) }">
						${contact.content}
					</c:if>
				</li>
				</c:forEach>
			</ul>
			</hgroup>
		</header>
	</div>
	<c:if test="${not empty u && u.admin}">
	<div class="remove">
		<span class="delete" id="${ligaai.id}"></span>
	</div>
	</c:if>
	<div class="rating">
		<span class="like" id="${ligaai.id}"></span>
	</div>
</article>
