<%@page import="java.util.Map.Entry"%>
<%@page import="com.yunbang.picerali.entity.ace.Pxcxq"%>
<%@page import="org.springframework.data.domain.Pageable"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page  import="java.lang.*,java.net.URLEncoder,java.util.*,org.springframework.data.domain.*"%>
<%@ taglib uri="/tags/core" prefix="c"%>
123
<div class="list_search_condition2"><%--
    <?php
    foreach($multiSelectors as $key=>$selector){
        $urlx='';
        foreach($multiSelectors as $mkey=>$mselector){
           if($mkey!=$key){
                $urlx=$urlx.$mkey.'='.urlencode($mselector['currentVal']).'&';
            }
        }
    ?>
  
    --%><%
    	LinkedHashMap<String,Object> xqSelectors=(LinkedHashMap<String,Object>)request.getAttribute("xqSelectors");
    	for(Entry<String,Object> e:xqSelectors.entrySet()){
    		String urlx="";
    		LinkedHashMap<String,Object> selector=(LinkedHashMap<String,Object>)e.getValue();
    		LinkedHashMap<String,Object> mxqSelectors=(LinkedHashMap<String,Object>)xqSelectors.clone();
    		for(Entry<String,Object> me:mxqSelectors.entrySet()){
    			if(e.getKey().equals(me.getKey())){
    				LinkedHashMap ms=(LinkedHashMap)me.getValue();
    				urlx+=me.getKey()+"="+ms.get("currentVal").toString()+"&";
    			}
    		}
    		
    		%>
    		
    		<dl class="search_list_dl">
	        <dt><i></i><%=selector.get("name").toString() %></dt>
	        <dd>
	            <ul>
    		<%
    			List<String> valsList=(List<String>)selector.get("vals");
    			for (String val:valsList){
    				String urlxs=request.getAttribute("urlSelect").toString()+e.getKey()+"="+val;
    				urlxs="\""+urlxs+"\"";
    				String divPage=(String)request.getAttribute("divPage");
    				String ac="href='javascript:void(0);' onclick='gotoPage(\""+divPage+"\","+urlxs+")'";
    				if(val.equals(selector.get("currentVal"))){
    					
    			%>
    				<li><a class='hover' <%= ac%> ><%=val %></a></li>
    			<%
	
    				}else{
    				%>
    				<li><a <%=ac %> ><%=val %></a></li>
    			<%	
    				}
    			}
    		%>
    		 </ul><div class="clearing"></div>
    		  </dd>
	    </dl>
   <% 		
    	}
    %><%--
    
    
	    <dl class="search_list_dl">
	        <dt><i></i><?php echo $selector['name']; ?><c:out value=""></dt>
	        <dd>
	            <ul>
	                <?php 
	                foreach($selector['vals'] as $val){
                    $urlxs=$urlSelect.$urlx.$key.'='.urlencode($val);
                    $ac='href="javascript:void(0);" onclick="gotoPage(\''.$divPage.'\',\''.$urlxs.'\');"';
                    if($val==$selector['currentVal']){
                        echo "<li><a class='hover' {$ac} >{$val}</a></li>";
                    }
                    else{
                        echo "<li><a {$ac} >{$val}</a></li>";
                    }
                }
                ?>
	
	            </ul><div class="clearing"></div>
	        </dd>
	    </dl>
    
    
    
--%></div>

