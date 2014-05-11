<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/page/inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <title>修改密码</title>
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
        <li class="active">修改密码</li>
    </ul>
    <!-- .breadcrumb -->
</div>

<div class="page-content">
<div class="page-header">
    <h1>
        修改密码
    </h1>
</div>
<!-- /.page-header -->

<div class="row">
<div class="col-xs-12">
<!-- PAGE CONTENT BEGINS -->

    <c:if test="${not empty errorMsg}">
        <div class="alert alert-block alert-danger">
            <button type="button" class="close" data-dismiss="alert">
                <i class="icon-remove"></i>
            </button>

            <i class="icon-error red"></i>

            <c:out value="${errorMsg}" />
        </div>
    </c:if>


    <form class="form-horizontal" role="form" id="form1" action="${ctxPath}/np/my/change_password.action" method="post">

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="oldPassword">旧密码</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="password" name="oldPassword" id="oldPassword" class="col-xs-12 col-sm-6" />
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="newPassword">新密码</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="password" name="newPassword" id="newPassword" class="col-xs-12 col-sm-6" />
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="repeatPassword">再次输入</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="password" name="repeatPassword" id="repeatPassword" class="col-xs-12 col-sm-6" />
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

<script src="${ctxPath}/assets/js/fuelux/fuelux.wizard.min.js"></script>
<script src="${ctxPath}/assets/js/jquery.validate.min.js"></script>
<script src="${ctxPath}/assets/js/additional-methods.min.js"></script>
<script src="${ctxPath}/assets/js/bootbox.min.js"></script>
<script src="${ctxPath}/assets/js/jquery.maskedinput.min.js"></script>
<script src="${ctxPath}/assets/js/select2.min.js"></script>


<!-- inline scripts related to this page -->

<script type="text/javascript">
    jQuery(function ($) {

        $("#submit_btn").click(function() {
           $("#form1").submit();
        });

        $('#form1').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            rules: {
                oldPassword: {
                    required: true
                },
                newPassword: {
                    required: true
                },
                repeatPassword: {
                    required: true
                }
            },

            messages: {
                oldPassword: {
                    required: "请输入原始密码"
                },
                newPassword: {
                    required: "请输入新密码"
                },
                repeatPassword: {
                    required: "请再次输入新密码"
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
                if($("#newPassword").val() !== $("#repeatPassword").val()) {
                    bootbox.alert("两次输入的密码不一致");
                    return;
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

