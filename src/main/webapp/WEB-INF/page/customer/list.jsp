<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/page/inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <title>查询会员</title>
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
        <li>会员管理</li>
        <li class="active">查询会员</li>
    </ul>
    <!-- .breadcrumb -->
</div>

<div class="page-content">
<div class="page-header">
    <h1>
        会员管理
        <small>
            <i class="icon-double-angle-right"></i>
            查询会员
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
    <th>账户余额</th>
    <th>享受折扣</th>
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

<c:forEach items="${customers}" var="customer">
    <tr>
        <td><c:out value="${customer.username}"/> </td>
        <td><c:out value="${customer.gender}"/> </td>
        <td><c:out value="${customer.nickname}"/> </td>
        <td><fmt:formatNumber value="${customer.balance}" type="currency" /> </td>
        <td><fmt:formatNumber value="${customer.discount}" type="currency" /> </td>
        <td><c:out value="${customer.qq}"/> </td>
        <td><c:out value="${customer.yy}"/> </td>
        <td><fmt:formatDate value="${customer.operator.updateTime}" pattern="yyyy-MM-dd HH:mm"/> </td>
        <td>
            <div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
                <button class="btn btn-xs btn-info" onclick="location.href='${ctxPath}/customer/update.action?userId=${customer.id}'">
                    <i class="icon-edit bigger-120"></i>
                </button>
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

