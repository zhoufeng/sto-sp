<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .wrap1{
        margin: 10px;
    }
    .divCenter{
        text-align: center;
    }

    .divContent{
        border: 1px solid #CCC;
        display: inline-block;
        padding: 10px;
        text-align: left;
        vertical-align: middle;
    }
    .divContent>img{
        display: block;
        border: 0;
        margin: 0;
        padding: 0;
    }
</style>
<div class="wrap1">

    <div class="divCenter">
        <div class="divContent">
            <!--{ foreach($mp as $m){ }-->

            <img src="${pxcxq.fullUrl}" alt="${pxcxq.name}"/>

            <!--{ } }-->
        </div>
    </div>
</div>
