<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/page/inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <title>查询订单</title>
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
        <li>订单管理</li>
        <li class="active">查询订单</li>
    </ul>
    <!-- .breadcrumb -->
</div>

<div class="page-content">
<div class="page-header">
    <h1>
        订单管理
        <small>
            <i class="icon-double-angle-right"></i>
            查询订单
        </small>
    </h1>
</div>
<!-- /.page-header -->

<div class="row">
<div class="col-xs-12">
<!-- PAGE CONTENT BEGINS -->

    <c:if test="${not empty param.errorMsg}">
        <div class="alert alert-block alert-danger">
            <button type="button" class="close" data-dismiss="alert">
                <i class="icon-remove"></i>
            </button>

            <i class="icon-error red"></i>

            <c:out value="${param.errorMsg}" /><br/>
        </div>

    </c:if>


<div class="row">
<div class="col-xs-12">
<div class="table-responsive">
<table id="sample-table-1" class="table table-striped table-bordered table-hover">
<thead>
<tr>
    <th>会员用户名</th>
    <th>会员昵称</th>
    <th>折扣</th>
    <th>陪练用户名</th>
    <th>陪练单价</th>
    <th>提成比例</th>
    <th><i class="icon-time bigger-110 hidden-480"></i>开始时间</th>
    <th><i class="icon-time bigger-110 hidden-480"></i>结束时间</th>
    <th>时间合计</th>
    <th>订单收益</th>
    <th>陪练收益</th>
    <th>状态</th>
    <th><i class="icon-time bigger-110 hidden-480"></i>下单时间</th>
    <th>备注</th>
    <th></th>
</tr>
</thead>

<tbody>

<c:forEach items="${orders}" var="order">
    <tr>
        <td><c:out value="${order.customer.username}"/> </td>
        <td><c:out value="${order.customer.nickname}"/> </td>
        <td><c:out value="${order.discount}"/>%</td>
        <td><c:out value="${order.coach.username}"/></td>
        <td><c:out value="${order.price}"/> </td>
        <td><c:out value="${order.proportion}"/>%</td>
        <td><fmt:formatDate value="${order.startTime}" pattern="yyyy-MM-dd HH:mm"/> </td>
        <td><fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd HH:mm"/> </td>
        <td><c:out value="${order.coachHour}"/> </td>
        <td><fmt:formatNumber value="${order.payment}" type="currency" /> </td>
        <td><fmt:formatNumber value="${order.coachSalary}" type="currency" /></td>
        <td><c:out value="${order.status.value}"/> </td>
        <td><fmt:formatDate value="${order.operator.updateTime}" pattern="yyyy-MM-dd HH:mm"/> </td>
        <td><c:out value="${order.remark}"/> </td>
        <td>
            <div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
                <c:if test="${order.status eq 'WAIT_CONFIRM'}">
                    <button class="btn btn-xs btn-info" onclick="location.href='${ctxPath}/order/update.action?orderId=${order.id}'">
                        <i class="icon-edit bigger-120"></i>
                    </button>
                </c:if>
                <c:if test="${order.status eq 'WAIT_CONFIRM'}">
                    <button class="btn btn-xs btn-success" onclick="location.href='${ctxPath}/order/confirm.action?orderId=${order.id}'">
                        <i class="icon-ok bigger-120"></i>
                    </button>
                </c:if>
                <c:if test="${order.status eq 'WAIT_CONFIRM'}">
                    <button class="btn btn-xs btn-danger" onclick="location.href='${ctxPath}/order/invalid.action?orderId=${order.id}'">
                        <i class="icon-trash bigger-120"></i>
                    </button>
                </c:if>
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

