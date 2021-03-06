<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<%-- Affichage d'une rubrique --%>
<t:template title="${ rubrique.libelle }">

    <jsp:body>
        <div class="col-md-12">
            <jsp:include page="../edition/list.jsp">
                <jsp:param name="editions" value="${editions}"/>
            </jsp:include>
        </div>
    </jsp:body>
    
</t:template>
