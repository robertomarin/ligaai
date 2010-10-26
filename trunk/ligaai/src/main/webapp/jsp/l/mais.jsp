<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fai" uri="http://liga.ai/jstl/functions"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:forEach items="${ligaais}" var="ligaai" varStatus="i">
	<my:ligaai ligaai="${ligaai}"/>
</c:forEach>