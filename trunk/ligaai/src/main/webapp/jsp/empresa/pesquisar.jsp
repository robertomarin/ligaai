
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<pre>

	Empresa cadastrada com sucesso!
	
	Detalhes da Empresa
	Empresa: ${empresa.id}
	Nome: ${empresa.nome}
	
	Data Criação : <fmt:formatDate value="${empresa.dataCriacao.time}" pattern="dd/MM/yyyy HH:mm:ss" />
	
	<c:forEach items="${empresa.funcionarios}" var="funcionario">
		Funcionario: ${funcionario.nome}
	</c:forEach>
	
</pre>
