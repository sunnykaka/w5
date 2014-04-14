<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
    <head>
        <title>Dashboard - Ace Admin</title>
    </head>

    <body>
    <div class="main-content">
    <div class="breadcrumbs" id="breadcrumbs">
        <script type="text/javascript">
            try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
        </script>

        <ul class="breadcrumb">
            <li>
                <i class="icon-home home-icon"></i>
                <a href="${ctxPath}/">主页</a>
            </li>
            <li class="active">欢迎页面</li>
        </ul><!-- .breadcrumb -->
    </div>

    <div class="page-content">
    <div class="page-header">
        <h1>
            欢迎
        </h1>
    </div><!-- /.page-header -->

    <div class="row">
    <div class="col-xs-12">
    <!-- PAGE CONTENT BEGINS -->

    <div class="alert alert-block alert-success">
        <button type="button" class="close" data-dismiss="alert">
            <i class="icon-remove"></i>
        </button>

        <i class="icon-ok green"></i>

        欢迎来到管理系统
    </div>

    <!-- PAGE CONTENT ENDS -->
    </div><!-- /.col -->
    </div><!-- /.row -->
    </div><!-- /.page-content -->
    </div><!-- /.main-content -->


    <%@ include file="/WEB-INF/page/inc/common_js.jsp" %>

    <!-- inline scripts related to this page -->

    <script type="text/javascript">
        jQuery(function($) {

        })
    </script>

    </body>

</html>

