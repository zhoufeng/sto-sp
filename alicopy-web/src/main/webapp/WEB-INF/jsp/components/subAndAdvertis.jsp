<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<div id="divBoard">
    <div id="divSubInfo">
        <div id="divSubInfoHead">
            <p>订购信息</p>
        </div>
        <div id="divSubInfoDetail">
            <p>当前版本：${subname}</p>
            <p>剩余天数：${subDeadline}(天)</p>
            <p>
            <c:choose>
            	<c:when test="${subname=='未订购' }">
            	<a href="http://fuwu.taobao.com/ser/detail.htm?service_id=25539" target="_blank" class="subBtn">订购</a>
            	</c:when>
            	<c:otherwise>
            		<a href="http://fuwu.taobao.com/ser/detail.htm?service_id=25539" target="_blank" class="subBtn">续订</a>
            	</c:otherwise>
            </c:choose>
            <c:if test="${ subname!='专业版'}">
            	<a href="http://fuwu.taobao.com/ser/detail.htm?service_id=25539" target="_blank"  class="subBtn">升级</a>
            </c:if>
               
            </p>
        </div>

    </div>
    <a href="${URL_ROOT }/index.php/pub/Customize.html" target="_blank"><img src="${URL_ROOT }/pub/img/131017/dz.png"></img></a>
</div>