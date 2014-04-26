<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/page/inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <title>查询陪练</title>
</head>

<body>
<div class="main-content">
<div class="breadcrumbs" id="breadcrumbs">
    <script type="text/javascript">
        try {
            ace.settings.check('breadcrumbs', 'fixed')
        } catch (e) {
        }
    </script>

    <ul class="breadcrumb">
        <li>
            <i class="icon-home home-icon"></i>
            <a href="${ctxPath}/">主页</a>
        </li>
        <li>陪练管理</li>
        <li class="active">查询陪练</li>
    </ul>
    <!-- .breadcrumb -->
</div>

<div class="page-content">
<div class="page-header">
    <h1>
        陪练管理
        <small>
            <i class="icon-double-angle-right"></i>
            查询陪练
        </small>
    </h1>
</div>
<!-- /.page-header -->

<div class="row">
<div class="col-xs-12">
<!-- PAGE CONTENT BEGINS -->

<div class="row">
<div class="col-xs-12">
<div class="table-responsive">
<table id="sample-table-1" class="table table-striped table-bordered table-hover">
<thead>
<tr>
    <th>用户名</th>
    <th>性别</th>
    <th class="hidden-480">昵称</th>
    <th>陪练单价</th>
    <th>提成比例</th>
    <th>账户余额</th>
    <th>QQ</th>
    <th>YY</th>
    <th>
        <i class="icon-time bigger-110 hidden-480"></i>
        更新时间
    </th>
    <th></th>
</tr>
</thead>

<tbody>

<c:forEach items="${coaches}" var="coach">
    <tr>
        <td><c:out value="${coach.username}"/> </td>
        <td><c:out value="${coach.gender}"/> </td>
        <td><c:out value="${coach.nickname}"/> </td>
        <td><fmt:formatNumber value="${coach.price}" type="currency" /> </td>
        <td>${coach.proportion}%</td>
        <td><fmt:formatNumber value="${coach.balance}" type="currency" /> </td>
        <td><c:out value="${coach.qq}"/> </td>
        <td><c:out value="${coach.yy}"/> </td>
        <td><fmt:formatDate value="${coach.operator.updateTime}" pattern="yyyy-MM-dd HH:mm"/> </td>
        <td>
            <div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
                <button class="btn btn-xs btn-info" onclick="location.href='${ctxPath}/coach/update.action?userId=${coach.id}'">
                    <i class="icon-edit bigger-120"></i>
                </button>
                <!--
                <button class="btn btn-xs btn-success" onclick="location.href='${ctxPath}/coach/salary/settle.action?userId=${coach.id}'">
                    <i class="icon-ok bigger-120"></i>
                </button>
                <button class="btn btn-xs btn-warning" onclick="location.href='${ctxPath}/coach/salary/list.action?userId=${coach.id}'">
                    <i class="icon-flag bigger-120"></i>
                </button>
                -->
            </div>
        </td>
    </tr>
</c:forEach>

</tbody>
</table>
</div><!-- /.table-responsive -->
</div><!-- /span -->
</div>

<!-- PAGE CONTENT ENDS -->
</div>
<!-- /.col -->
</div>
<!-- /.row -->
</div>


</div>
<!-- /.main-content -->


<%@ include file="/WEB-INF/page/inc/common_js.jsp" %>

<!-- inline scripts related to this page -->

<script type="text/javascript">
    jQuery(function ($) {

    })
</script>

</body>

</html>

