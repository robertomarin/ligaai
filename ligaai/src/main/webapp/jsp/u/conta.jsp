<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fai" uri="http://liga.ai/jstl/functions"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<html>
	<body class="morningBackground">
		<div class="wrapper">
		    <my:header/>
			<div class="containerShadow">
				<div id="userList">
					<section id="content account">
						<input type="hidden" id="userId" value="${user.id}" />
						<c:if test="${!empty param.monkey or !cutAvatar}">
							<div class="unit avatar">
								<img src="/ligaai/avatar/${user.pathAvatar}_300.jpg" onerror="javascript: showUnavailableImage(this, '300x300')" />
							</div>
						</c:if>
						<c:if test="${empty param.monkey and msg == null}">
							<div class="unit userInfo account">
								<div class="unit userNameContainer">
									<h1 id="user" class="userName"><c:out value="${user.name}"></c:out></h1>
									<c:if test="${himself}"><a href="#" id="editName">Editar</a></c:if>
								</div>
								<c:if test="${himself}">
									<form id="changeNameForm" class="hide">
										<input type="text" id="newName" /><input type="submit" id="changeNameButton" value="Mudar Nome" />
									</form>
								</c:if>
								<ul class="unit">
									<c:if test="${himself}">
										<li><c:out value="${user.email}"></c:out></li>
										<li>
											<a href="#" id="changeAvatar">Trocar avatar</a>
											<div id="avatarUpload" class="hide unit">
												<form method="post" action="/u/avatar/novo" enctype="multipart/form-data">
													<input type="file" name="file"/> 
													<input type="submit" name="Upload" class="uploadAvatarbutton"/>
												</form>
											</div>
										</li>
										<li>
											<a href="#" id="changePassword">Mudar senha</a>
											<form id="changePasswordForm" class="hide">
												<input type="password" id="password" /><br />
												<input type="password" id="newPassword" /> <input type="submit" value="Mudar Senha" />
											</form>
										</li>
									</c:if>
								</ul>
							</div>
							<c:if test="${!empty param.monkey or !cutAvatar}">
								<div class="containerLiga">
									<h4>Meus últimos posts</h4>
									<c:forEach items="${ligaais}" var="ligaai" varStatus="i">
										<my:ligaai ligaai="${ligaai}" avatarNomeDisabled="true"/>
									</c:forEach>
								</div>
							</c:if> 
						</c:if>
						<c:if test="${msg != null}">
							<p class="unit"><c:out value="${msg}" escapeXml="false"/></p>
						</c:if>
						<c:if test="${cutAvatar or !empty param.monkey}">
							<div align="center"> 
				                <img src="/ligaai/avatar/original/${user.id}.jpg" id="cropbox" />
				                <div id="previewContainer" class="hide">
			                        <img src="/ligaai/avatar/original/${user.id}.jpg" id="preview" />
				                </div>
							</div>
							<form action="/u/avatar/cortar" id="formCrop" onsubmit="return checkCoords();">
								<input type="hidden" name="x" id="x" />
								<input type="hidden" name="y" id="y" />
								<input type="hidden" name="w" id="w" />
								<input type="hidden" name="h" id="h" />
								<input type="submit" value="Enviar avatar" />
							</form>
						</c:if>
					</section>
					<!-- AD -->
					<my:side-ad />
					<!-- AD end-->
				</div>
			</div>
		</div>
		<my:footer/>
		<!-- LIGHTBOX -->
		<my:lightbox/>
		<!--JAVASCRIPT-->
		<script language="javascript" type="text/javascript" src="/js/jquery.Jcrop.min.js"></script>
	</body>
</html>
