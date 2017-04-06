<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<title>Tarefa</title>
</head>
<body>

<h3>Alterar tarefa ${tarefa.id}</h3>
<hr>

<form action="alteraTarefa" method="post">

	<input type="hidden" name="id" value="${tarefa.id}"/>
	
	Descrição<br/>
	<textarea rows="5" cols="100" name="descricao">
		${tarefa.descricao}
	</textarea>
	<br/>
	
	Finalizado? <input type="checkbox" name="finalizado" value="true" ${tarefa.finalizado ? 'checked' : ''}>
	<br/>
	
	Data de finalização: <br/>
	<input type="text" name="dataFinalizacao" value="<fmt:formatDate value="${tarefa.dataFinalizacao}" pattern="dd/MM/yyyy"/>"/>
	<br/>

	<input type="submit" value="Alterar"/>

</form>

</body>
</html>