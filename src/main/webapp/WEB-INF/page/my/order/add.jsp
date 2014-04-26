<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/page/inc/taglibs.jsp" %>
<c:set var="loginUser" value="${sessionScope['com.akkafun.w5.user.model.User'] }" />

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <title>新增订单</title>

    <link rel="stylesheet" href="${ctxPath}/assets/css/custom/bootstrap-datetimepicker.min.css" />
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
        <li>我的订单</li>
        <li class="active">新增订单</li>
    </ul>
    <!-- .breadcrumb -->
</div>

<div class="page-content">
<div class="page-header">
    <h1>
        我的订单
        <small>
            <i class="icon-double-angle-right"></i>
            新增订单
        </small>
    </h1>
</div>
<!-- /.page-header -->

<div class="row">
<div class="col-xs-12">
<!-- PAGE CONTENT BEGINS -->

    <spring:hasBindErrors name="order">
        <div class="alert alert-block alert-danger">
            <button type="button" class="close" data-dismiss="alert">
                <i class="icon-remove"></i>
            </button>

            <i class="icon-error red"></i>

            <c:forEach items="${errors.allErrors}" var="error">
                <c:out value="${error.defaultMessage}" /><br/>
            </c:forEach>
        </div>
    </spring:hasBindErrors>


    <form class="form-horizontal" role="form" id="form1" action="${ctxPath}/my/order/save.action" method="post">

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="customerUsername">会员用户名*</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="customerUsername" id="customerUsername" class="col-xs-12 col-sm-6" value="${order.customerUsername}" />
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="customerDiscount">会员折扣</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="customerDiscount" id="customerDiscount" class="col-xs-12 col-sm-6" readonly />%
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="customerBalance">会员余额</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="customerBalance" id="customerBalance" class="col-xs-12 col-sm-6" readonly />
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="coachUsername">陪练用户名*</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="coachUsername" id="coachUsername" class="col-xs-12 col-sm-6" readonly value="${loginUser.username}" />
            </div>
        </div>
    </div>


    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="price">陪练单价</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="price" id="price" class="col-xs-12 col-sm-6" readonly value="${loginUser.price}" />
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="startTime">开始时间*</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" id="startTime" name="startTime" class="col-xs-12 col-sm-6" value='<fmt:formatDate value="${order.startTime}" pattern="yyyy-MM-dd HH:mm" />'/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="endTime">结束时间*</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" id="endTime" name="endTime" class="col-xs-12 col-sm-6" value='<fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd HH:mm" />' />
            </div>
        </div>
    </div>


    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="coachHour">时间合计</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="coachHour" id="coachHour" class="col-xs-12 col-sm-6" readonly value="${order.coachHour}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="payment">订单收益</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="payment" id="payment" class="col-xs-12 col-sm-6" readonly value="${order.payment}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="proportion">提成比例</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="proportion" id="proportion" class="col-xs-12 col-sm-6" readonly value="${loginUser.proportion}"/>%
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="coachSalary">实际收益</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="coachSalary" id="coachSalary" class="col-xs-12 col-sm-6" readonly value="${order.coachSalary}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="remark">备注</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <textarea class="form-control" id="remark" name="remark" placeholder="备注">${order.remark}</textarea>
            </div>
        </div>
    </div>

    <div class="space-4"></div>

    <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
            <button class="btn btn-info" type="button" id="submit_btn">
                <i class="icon-ok bigger-110"></i>
                提交
            </button>

            &nbsp; &nbsp; &nbsp;
            <button class="btn" type="reset">
                <i class="icon-undo bigger-110"></i>
                重置
            </button>
        </div>
    </div>

    <div class="hr hr-24"></div>

</form>

<!-- PAGE CONTENT ENDS -->
</div>
<!-- /.col -->
</div>
<!-- /.row -->
</div>


</div>
<!-- /.main-content -->

<%@ include file="/WEB-INF/page/inc/common_js.jsp" %>

<script src="${ctxPath}/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="${ctxPath}/assets/js/data-time/moment.min.js"></script>
<script src="${ctxPath}/assets/js/custom/bootstrap-datetimepicker.min.js"></script>


<!-- inline scripts related to this page -->

<script type="text/javascript">

    var customerInputSuccess = false;

    jQuery(function ($) {

        var checkTime = function(startMoment, endMoment) {
            if(!startMoment) {
                var startMoment = moment($("#startTime").val(), "YYYY-MM-DD HH:mm");
            }
            if(!endMoment) {
                var endMoment = moment($("#endTime").val(), "YYYY-MM-DD HH:mm");
            }
            if(startMoment && endMoment) {
                if(startMoment.isBefore(endMoment)) {
                    return true;
                }
            }
            return false;
        }

        var refreshBalance = function() {
            var startMoment = moment($("#startTime").val(), "YYYY-MM-DD HH:mm");
            var endMoment = moment($("#endTime").val(), "YYYY-MM-DD HH:mm");
            if(!checkTime(startMoment, endMoment)) {
                return false;
            }
            var customerDiscount = $("#customerDiscount").val();
            if(!customerDiscount) {
                bootbox.alert("请先输入正确的会员用户名");
                return false;
            }

            var diffHours = endMoment.diff(startMoment, "hours", true).toFixed(2);
            var balance = (parseFloat($("#price").val()) * diffHours).toFixed(2);
            var payment = (balance * (customerDiscount / 100)).toFixed(2);
            var coachSalary = (parseFloat($("#proportion").val()) / 100 * payment).toFixed(2);
            $("#coachHour").val(diffHours);
            $("#payment").val(payment);
            $("#coachSalary").val(coachSalary);
            return true;
        }

        $("#startTime, #endTime")
                .datetimepicker({format: 'yyyy-mm-dd hh:ii', language: 'zh-CN'})
                .on('changeDate', refreshBalance);

        var changeCustomerUsername = function() {
            var customerUsername = $("#customerUsername").val();
            if(!customerUsername) return;
            $.getJSON("${ctxPath}/ajax/user/get_user.action", {"username" : customerUsername, "userType" : 'CUSTOMER'}, function(data) {
                $("#customerDiscount").val("");
                $("#customerBalance").val("");
                customerInputSuccess = false;
                if(!data || !data.id) {
                    bootbox.alert("输入的会员用户名在系统不存在");
                    return;
                }
                customerInputSuccess = true;
                $("#customerDiscount").val(data.discount);
                $("#customerBalance").val(data.balance);
                refreshBalance();
            });
        }

        $("#customerUsername").change(changeCustomerUsername);

        changeCustomerUsername();

        var checkCustomerBalance = function() {
            if(!checkTime()) {
                return false;
            }
            var payment = $("#payment").val();
            if(!payment) {
                return false;
            }
            var customerBalance = $("#customerBalance").val();
            if(!customerBalance) {
                return false;
            }
            if(parseFloat(customerBalance) < parseFloat(payment)) {
                return false
            }
            return true;
        }

        $("#submit_btn").click(function() {
           $("#form1").submit();
        });

        $('#form1').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            rules: {
                customerId: {
                    required: true
                },
                startTime: {
                    required: true
                },
                endTime: {
                    required: true
                }
            },

            messages: {
                customerId: {
                    required: "请输入会员用户名"
                },
                startTime: {
                    required: "请输入开始时间"
                },
                endTime: {
                    required: "请输入结束时间"
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                $('.alert-danger', $('.login-form')).show();
            },

            highlight: function (e) {
                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            },

            success: function (e) {
                $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
                $(e).remove();
            },

            errorPlacement: function (error, element) {
                if(element.is(':checkbox') || element.is(':radio')) {
                    var controls = element.closest('div[class*="col-"]');
                    if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
                    else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                }
                else if(element.is('.select2')) {
                    error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                }
                else if(element.is('.chosen-select')) {
                    error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                }
                else error.insertAfter(element.parent());
            },

            submitHandler: function (form) {
                if(!checkTime()) {
                    bootbox.alert("开始时间需要在结束时间之前");
                    return;
                }
                if(!checkCustomerBalance()) {
                    bootbox.alert("未输入会员用户名或会员余额不足以支付陪练费用");
                    return false;
                }
                if(!customerInputSuccess) {
                    bootbox.alert("请输入正确的会员用户名");
                    return false;
                }
                form.submit();
            },
            invalidHandler: function (form) {
            }
        });
    })
</script>

</body>

</html>

