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
                                <input type="text" name="username" class="form-control" placeholder="用户名" />
                                <i class="icon-user"></i>
                            </span>
                        </label>

                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="password" name="password" class="form-control" placeholder="密码" />
                                <i class="icon-lock"></i>
                            </span>
                        </label>

                        <div class="space"></div>

                        <div class="clearfix">

                            <button type="button" id="login_btn" class="width-35 pull-right btn btn-sm btn-primary">
                                <i class="icon-key"></i>
                                登录
                            </button>
                        </div>

                        <div class="space-4"></div>
                    </fieldset>
                </form>

            </div><!-- /widget-main -->

            <div class="toolbar clearfix">

                <div>
                    <a href="#" onclick="show_box('customer-box'); return false;" class="forgot-password-link">
                        <i class="icon-arrow-left"></i>
                        会员注册
                    </a>
                </div>

                <div>
                    <a href="#" onclick="show_box('coach-box'); return false;" class="user-signup-link">
                        陪练注册
                        <i class="icon-arrow-right"></i>
                    </a>
                </div>
            </div>

        </div><!-- /widget-body -->
    </div><!-- /login-box -->

    <div id="customer-box" class="signup-box widget-box no-border">
        <div class="widget-body">
            <div class="widget-main">
                <h4 class="header green lighter bigger">
                    <i class="icon-group blue"></i>
                    会员注册
                </h4>

                <div class="space-6"></div>
                <p>请输入您的注册信息: </p>

                <form id="customerForm" action="${ctxPath}/register.action" method="post">
                    <input type="hidden" name="type" value="CUSTOMER" />

                    <fieldset>

                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="text" class="form-control" name="username" placeholder="用户名" />
                                <i class="icon-user"></i>
                            </span>
                        </label>

                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="password" name="password" class="form-control" placeholder="密码" />
                                <i class="icon-lock"></i>
                            </span>
                        </label>

                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="text" class="form-control" name="mobile" placeholder="手机" />
                                <i class="icon-user"></i>
                            </span>
                        </label>

                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="text" class="form-control" name="yy" placeholder="YY" />
                                <i class="icon-user"></i>
                            </span>
                        </label>

                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="text" class="form-control" name="qq" placeholder="QQ" />
                                <i class="icon-user"></i>
                            </span>
                        </label>

                        <div class="space-24"></div>

                        <div class="clearfix">
                            <button type="button" id="customer_btn" class="width-65 pull-right btn btn-sm btn-success">
                                注册
                                <i class="icon-arrow-right icon-on-right"></i>
                            </button>
                        </div>
                    </fieldset>
                </form>
            </div>

            <div class="toolbar center">
                <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
                    <i class="icon-arrow-left"></i>
                    回到登录
                </a>
            </div>
        </div><!-- /widget-body -->
    </div><!-- /signup-box -->

    <div id="coach-box" class="signup-box widget-box no-border">
        <div class="widget-body">
            <div class="widget-main">
                <h4 class="header green lighter bigger">
                    <i class="icon-group blue"></i>
                    陪练注册
                </h4>

                <div class="space-6"></div>
                <p>请输入您的注册信息: </p>

                <form id="coachForm" action="${ctxPath}/register.action" method="post">
                    <input type="hidden" name="type" value="COACH" />

                    <fieldset>

                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="text" class="form-control" name="username" placeholder="用户名" />
                                <i class="icon-user"></i>
                            </span>
                        </label>

                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="password" name="password" class="form-control" placeholder="密码" />
                                <i class="icon-lock"></i>
                            </span>
                        </label>

                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="text" class="form-control" name="price" placeholder="陪练单价" />
                                <i class="icon-user"></i>
                            </span>
                        </label>

                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="text" class="form-control" name="mobile" placeholder="手机" />
                                <i class="icon-user"></i>
                            </span>
                        </label>

                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="text" class="form-control" name="yy" placeholder="YY" />
                                <i class="icon-user"></i>
                            </span>
                        </label>

                        <label class="block clearfix">
                            <span class="block input-icon input-icon-right">
                                <input type="text" class="form-control" name="qq" placeholder="QQ" />
                                <i class="icon-user"></i>
                            </span>
                        </label>

                        <div class="space-24"></div>

                        <div class="clearfix">
                            <button type="button" id="coach_btn" class="width-65 pull-right btn btn-sm btn-success">
                                注册
                                <i class="icon-arrow-right icon-on-right"></i>
                            </button>
                        </div>
                    </fieldset>
                </form>
            </div>

            <div class="toolbar center">
                <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
                    <i class="icon-arrow-left"></i>
                    回到登录
                </a>
            </div>
        </div><!-- /widget-body -->
    </div>


</div><!-- /position-relative -->
</div>
</div><!-- /.col -->
</div><!-- /.row -->
</div>
</div><!-- /.main-container -->

<%@ include file="/WEB-INF/page/inc/common_js.jsp" %>

<!-- inline scripts related to this page -->

<script type="text/javascript">

    function show_box(id) {
        jQuery('.widget-box.visible').removeClass('visible');
        jQuery('#'+id).addClass('visible');
    }

    jQuery(function ($) {

        $("body").bind('keyup',function(event) {
            if(event.keyCode==13 && $("#login-box").hasClass("visible")){
                $("#form1").submit();
            }
        });

        $("#login_btn").click(function() {
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
                password: {
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

        $("#customer_btn").click(function() {
            $("#customerForm").submit();
        });

        $('#customerForm').validate({
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
                password: {
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

        $("#coach_btn").click(function() {
            $("#coachForm").submit();
        });

        $('#coachForm').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                price: {
                    required: true,
                    number: true
                }
            },

            messages: {
                username: {
                    required: "请输入用户名"
                },
                password: {
                    required: "请输入密码"
                },
                price: {
                    required: "请输入单价",
                    number: "陪练单价必须为数字"
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

