<%@page import="ai.liga.ligaai.model.Contact"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fai" uri="http://liga.ai/jstl/functions"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<html lang="pt-BR">
	<body class="morningBackground">
		<div class="wrapper">
		    <my:header/>
			<div class="containerShadow">
				<!--Shorten url-->
				<article id="shortenUrl">
					<div class="container">
						<div class="message hide">Insere sua url ai :)</div>
						<header>
							<h1>O liga.ai encurta urls para você...</h1>
						</header>
						<section>
							<form id="microurl">
								<input type="text" name="url" id="url" class="shortenField" />
								<input type="submit" value="encurta.ai" class="shortenSubmit" />
							</form>
							<div id="loader" class="hide"><img src="/img/loader.gif" /></div>
							<div id="doneShortenUrl" class="hide">
								<span class="baseColor">Sua url encurtada</span>
								<input type="text" id="microurlmicro" readonly="readonly" />
								<div id="clickToCopy">Clique para copiar</div>
								<ul>
									<li><a href="#" title="Postar essa url no Twitter" id="twitter">Twitte essa url</a></li>
									<li>|</li>
									<li>Você economizou <strong class="baseColor" id="savedChars"></strong> caracteres</li>
								</ul>
							</div>
						</section>
					</div>
				</article>
				<!--Shorten url end-->
				<!-- Liga.ai -->
				<article id="liga">
					<div class="container">
						<header>
							<h1 class="ligaStyle">Me Liga<span class="baseColor">.</span>ai</h1>
						</header>
						<section>
							<form id="ligaai" action="/l/novo">
								<input type="hidden" id="position" value="0" />
								<input type="hidden" id="needsubscribe" />
								<div id="cloneable" class="hide">
									<select class="contactType" name="">
										<option value="PHONE" selected>Telefone</option>
										<option value="SKYPE">Skype</option>
										<option value="GTALK">Google Talk</option>
										<option value="MSN">MSN</option>
										<option value="TWITTER">Twitter</option>
										<option value="FACEBOOK">Facebook</option>
										<option value="ORKUT">Orkut</option>
										<option value="EMAIL">E-mail</option>
									</select>
									<input type="text" name="" class="phone contactInfo" />
								</div>
								<div class="contactContainer">
									<select class="contactType" name="contacts[0].type">
										<option value="PHONE" selected>Telefone</option>
										<option value="SKYPE" class="skype">Skype</option>
										<option value="GTALK">Google Talk</option>
										<option value="MSN">MSN</option>
										<option value="TWITTER">Twitter</option>
										<option value="FACEBOOK">Facebook</option>
										<option value="ORKUT">Orkut</option>
										<option value="EMAIL">E-mail</option>
									</select>
									<input type="text" id="firstContact" name="contacts[0].content" class="phone contactInfo" />
									<a href="#" id="addMoreContact" title="Adicione mais contatos">+</a>
								</div>
								
								<textarea name="message" id="message" maxlength="200">Me liga.ai ;-)</textarea>
								<div class="unit textLimit">
									<span class="counter">150</span> restantes
								</div>
								<p><input type="checkbox" checked="checked" id="agree" /> <label for="agree">Li e concordo com os <a href="#" title="Veja os termos de uso do liga.ai">termos de uso</a></label></p>						
								<input type="submit" value="envia.ai" class="sendButton" />
							</form>
						</section>
					</div>
				</article>
				<!-- Liga.ai end-->
				<div id="userList">
					<section id="content">
						<!--user list-->
						<c:forEach items="${ligaais}" var="ligaai" varStatus="i">
							<my:ligaai ligaai="${ligaai}"/>
							<!-- Middle AD -->
							<c:if test="${i.count eq 2}">
								<my:middle-ad/>
							</c:if>
							<!-- Middle AD end -->
						</c:forEach>
						<input type="hidden" value="0" id="moreData" />
						<!--end user list-->
						<div id="postLoader"></div>
						<input type="hidden" id="nLigaai" value="1" />
					</section>
					<my:side-ad />
				</div>
			</div>
		</div>
		<my:footer/>
		<!-- LIGHTBOX -->
		<my:lightbox/>
		<!--JAVASCRIPT-->
		<script language="javascript" type="text/javascript" src="/js/ZeroClipboard.js"></script>
	</body>
</html>