<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/page/inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <title>欢迎登录</title>
    <%@ include file="/WEB-INF/page/inc/common.jsp" %>
</head>

<body class="login-layout">
<div class="main-container">
<div class="main-content">
<div class="row">
<div class="col-sm-10 col-sm-offset-1">
<div class="login-container">
<div class="center">
    <h1>
        <i class="icon-leaf green"></i>
        <span class="red">王者战神</span>
        <span class="white">计费管理系统</span>
    </h1>
    <h4 class="blue">&copy; Ares</h4>
</div>

<div class="space-6"></div>

<div class="position-relative">
    <div id="login-box" class="login-box visible widget-box no-border">
        <div class="widget-body">
            <div class="widget-main">
                <h4 class="header blue lighter bigger">
                    <i class="icon-coffee green"></i>
                    请输入您的登录信息
                </h4>
                <spring:hasBindErrors name="user">
                    <h4 class="header red lighter bigger">
                        <i class="red"></i>
                        <c:forEach items="${errors.allErrors}" var="error">
                            <c:out value="${error.defaultMessage}" /><br/>
                        </c:forEach>
                    </h4>
                </spring:hasBindErrors>

                <div class="space-6"></div>

                <form class="form-horizontal" id="form1" action="${ctxPath}/login.action" method="post">
                    <fieldset>
                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="text" name="username" id="username" class="form-control" placeholder="用户名" />
                                <i class="icon-user"></i>
                            </span>
                        </label>

                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="password" name="password" id="password" class="form-control" placeholder="密码" />
                                <i class="icon-lock"></i>
                            </span>
                        </label>

                        <div class="space"></div>

                        <div class="clearfix">

                            <button type="button" id="submit_btn" class="width-35 pull-right btn btn-sm btn-primary">
                                <i class="icon-key"></i>
                                登录
                            </button>
                        </div>

                        <div class="space-4"></div>
                    </fieldset>
                </form>

            </div><!-- /widget-main -->

        </div><!-- /widget-body -->
    </div><!-- /login-box -->

</div><!-- /position-relative -->
</div>
</div><!-- /.col -->
</div><!-- /.row -->
</div>
</div><!-- /.main-container -->

<%@ include file="/WEB-INF/page/inc/common_js.jsp" %>

<!-- inline scripts related to this page -->

<script type="text/javascript">
    jQuery(function ($) {

        $("body").bind('keyup',function(event) {
            if(event.keyCode==13){
                $("#form1").submit();
            }
        });

        $("#submit_btn").click(function() {
            $("#form1").submit();
        });

        $('#form1').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                }
            },

            messages: {
                username: {
                    required: "请输入用户名"
                },
                newPassword: {
                    required: "请输入密码"
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
                form.submit();
            },
            invalidHandler: function (form) {
            }
        });
    })
</script>
</body>

</html>

