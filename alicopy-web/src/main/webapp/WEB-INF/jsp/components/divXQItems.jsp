<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>



<jsp:include page="../widget/multiSelector.jsp"/>

<jsp:include page="../widget/pagination.jsp"/>


<c:forEach var="item" items="${page.content}">
	<div class="divXQItem">
    <div class="XQItemButton">
        <a target="_blank" href="/index.php/Main/xq.html?id=${ item.id}'">
           开始制作
    </a>
    <a target="_blank" href="/index.php/pub/Preview/xq.html?id=${ item.id}">
       预览
</a>
</div>
<img class="divXQImg" src="${ item.thumbnailurl}"/>
</div>
</c:forEach>

<div style="clear:both" ></div>