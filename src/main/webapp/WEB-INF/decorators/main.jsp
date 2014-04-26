<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" buffer="64kb" %>
<%@ include file="/WEB-INF/page/inc/taglibs.jsp" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="utf-8"/>
    <title><decorator:title default="王者战神计费管理系统"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <%@ include file="/WEB-INF/page/inc/common.jsp" %>

    <decorator:head/>
</head>
<body>

<%@ include file="/WEB-INF/page/inc/header.jsp" %>

<div class="main-container" id="main-container">
<script type="text/javascript">
    try{ace.settings.check('main-container' , 'fixed')}catch(e){}
</script>

<div class="main-container-inner">
<a class="menu-toggler" id="menu-toggler" href="#">
    <span class="menu-text"></span>
</a>

<c:import url="/WEB-INF/page/inc/left.jsp"/>

<decorator:body/>

</div><!-- /.main-container-inner -->

<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="icon-double-angle-up icon-only bigger-110"></i>
</a>
</div><!-- /.main-container -->


</body>
</html>