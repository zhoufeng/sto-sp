<%@page import="java.util.Random"%>
<%@page import="com.yunbang.picerali.entity.ace.Pxcxq"%>
<%@page import="org.springframework.data.domain.Page"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>

<link rel="stylesheet" type="text/css" href="${URL_ROOT }/pub/css/pagination.css" />

<%
	String divPage=StringUtils.isEmpty(request.getAttribute("divPage").toString())?"":request.getAttribute("divPage").toString();
	String urlPage=StringUtils.isEmpty(request.getAttribute("urlPage").toString())?"":request.getAttribute("urlPage").toString();
	Page<Pxcxq> pageModel=(Page<Pxcxq>)request.getAttribute("page");
	int currentPage=(int)pageModel.getNumber();
	int prePage=Math.max(currentPage+1-1,1);
	int totalPage=(int)pageModel.getTotalPages();
	int nextPage=Math.min(currentPage+1+1,totalPage);
	Random r=new Random(1000);
	String idInput="page"+Integer.valueOf(r.nextInt()+1).toString();
	
	
 %>

<%!
	public String pageLink(String divPage,String urlPage,int currentPage,Integer i,String pageText){
		if("".equals(pageText)){
			pageText=Integer.toString(i);
		}
		String ac="href=\"javascript:void(0);\" onclick=\"gotoPage('"+divPage+"\','"+urlPage+i+"');\"";
		
		if(i==currentPage){
           return "<a style=\"border:solid thin #FFF;color:#888\" >"+pageText+"</a>";
        }else{
            return "<a "+ac+" >"+pageText+"</a>";
        }
	}
	
 %>
 <%!
 	public String pageLink(String divPage,String urlPage,int currentPage,Integer i){
		String pageText=Integer.toString(i);
		return pageLink(divPage,urlPage,currentPage,i,pageText);
	}
  %>
<!-- 
<?php 
    if(!isset($divPage) )$divPage='';
    if(!isset($urlPage) )$urlPage='';
    $prePage=max($currentPage-1,1);
    $nextPage=min($currentPage+1,$totalPage);
    $idInput='page'.rand(1,10000);
    if (!function_exists('pageLink')) {
      function pageLink($divPage,$urlPage,$currentPage,$i,$pageText=''){
        if($pageText==''){
            $pageText=$i;
        }
        $ac='href="javascript:void(0);" onclick="gotoPage(\''.$divPage.'\',\''.$urlPage.$i.'\');"';
        if($i==$currentPage){
            echo '<a style="border:solid thin #FFF;color:#888" >'.$pageText.'</a>';
        }else{
            echo '<a '.$ac.' >'.$pageText.'</a>';
        }
      }
    }
?>
 -->
 <div class='divPagination' >

  <%=pageLink(divPage,urlPage,currentPage,prePage,"< 前一页") %>
 <%=pageLink(divPage,urlPage,currentPage,1) %>
 <% middenPageContent(pageModel,divPage,urlPage,nextPage);%>
 
 <%!
   	
 	public  String middenPageContent(Page<Pxcxq> page,String divPage,String urlPage,Integer nextPage){
 		String retString="";
 		Integer start=null;
 		Integer end=null;
 		Integer currentPage=page.getNumber()+1;
 		Integer totalPage=page.getTotalPages();
 		Integer pageLength=7;
 		Integer middlePage=(pageLength+1)/2;
 		if(currentPage<=middlePage){
           start=2;
           end=pageLength+1;
        }else{
           start=currentPage-middlePage+1;
           end=currentPage+middlePage-1;
        };
        
        if(totalPage-currentPage<middlePage){
            start=totalPage-pageLength;
            end=totalPage-1;
        };
        if(start<1){
            start=2;
        }
        if(end>totalPage-1){
           end=totalPage-1;
        }
        if(start>2){
            retString+="...";
        }
        for(int i=start;i<=end;i++){
             retString+=pageLink(divPage,urlPage,currentPage,i);
        }
        if(totalPage-end>1){
           retString+="...";
        }
        if(totalPage>1){
            retString+=pageLink(divPage,urlPage,currentPage,totalPage);
        }
        return retString+=pageLink(divPage,urlPage,currentPage,nextPage,"后一页 >");
        
 	}
         
  %>
 <!-- 
<div class='divPagination' >
    <?php
        $pageLength=7;
        $middlePage=($pageLength+1)/2;
        
        pageLink($divPage,$urlPage,$currentPage,$prePage,'< 前一页');
        pageLink($divPage,$urlPage,$currentPage,1);
        
        if($currentPage<=$middlePage){
            $start=2;
            $end=$pageLength+1;
        }else{
            $start=$currentPage-$middlePage+1;
            $end=$currentPage+$middlePage-1;
        };
        if($totalPage-$currentPage<$middlePage){
            $start=$totalPage-$pageLength;
            $end=$totalPage-1;
        };
        if($start<1){
            $start=2;
        }
        if($end>$totalPage-1){
            $end=$totalPage-1;
        }
        if($start>2){
            echo '...';
        }
        for($i=$start;$i<=$end;$i++){
             pageLink($divPage,$urlPage,$currentPage,$i);
        }
        if($totalPage-$end>1){
            echo '...';
        }
        if($totalPage>1){
            pageLink($divPage,$urlPage,$currentPage,$totalPage);
        }
         pageLink($divPage,$urlPage,$currentPage,$nextPage,'后一页 >');
    ?>
     -->
<!--    共<?php echo $totalPage; ?>页-->
    共<%=totalPage %>页&nbsp;到第 
    <input id="<%=idInput %>" type="text" style="width: 3em;text-align: center;" value="<%=currentPage %>"/>页&nbsp;
    <a href="javascript:void(0);" onclick="gotoPage('<%=divPage %>','<%=urlPage %>'+$('#<%=idInput %>').val())">确定</a>
</div>
