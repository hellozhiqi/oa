<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<c:set  value="${pageContext.request.contextPath}" var="ctx"/>
<%@ attribute name="url" required="true" type="java.lang.String" %>
<%@ attribute  name="page" required="true" type="org.springframework.data.domain.Page"%>

<c:if test="${not empty page }">
	<nav>
	  <ul class="pagination">
	    <li>
	      <a href="${ctx}${url}?pageNumber=${page.number eq 0 ? 0 : page.number - 1}" aria-label="上一页">
	       		 <span aria-hidden="true">&laquo;</span>
	      </a>
	    </li>
	    <c:set value="${page.number-2}"  var="begin"/>
	    <c:set value="${page.number+2}"  var="end"/>
	    <!-- 当前页，不能小于0 -->
	    <c:if test="${begin lt 0}">
	    	<c:set value="${end+(-begin) }" var="end"></c:set>
	    	 <c:set value="0"  var="begin"></c:set>
	    </c:if>
	    <c:if test="${begin < 0}">
	    	 <c:set value="0"  var="begin"></c:set>
	    </c:if>
	      <c:if test="${end gt (page.totalPages-1)}">
	      	  <!-- 当前页，不能总页数 -->
	    	 <c:set value="${page.totalPages-1}"  var="end"></c:set>
	    	<%--  <c:set var="begin" value="${end - 4 }"></c:set> --%>
	    </c:if>
        <c:forEach begin="${begin }" end="${end }" var="number">
      		 <li class="${page.number eq number ? 'active' : ' '}">
      		 	<a href="${ctx}${url}?pageNumber=${number}">${number+1 }</a>
      		 </li>
        </c:forEach>
	    <li>
	      <a href="${ctx }${url}?pageNumber=${page.number ge(page.totalPages - 1) ? page.totalPages - 1 : page.number + 1}" 
	      		aria-label="下一页">
	        <span aria-hidden="true">&raquo;</span>
	      </a>
	    </li>
	  </ul>
	</nav>
</c:if>