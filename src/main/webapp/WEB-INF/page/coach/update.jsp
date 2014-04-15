<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/page/inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <title>新增陪练</title>
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
        <li class="active">新增陪练</li>
    </ul>
    <!-- .breadcrumb -->
</div>

<div class="page-content">
<div class="page-header">
    <h1>
        陪练管理
        <small>
            <i class="icon-double-angle-right"></i>
            更新陪练
        </small>
    </h1>
</div>
<!-- /.page-header -->

<div class="row">
<div class="col-xs-12">
<!-- PAGE CONTENT BEGINS -->

<form class="form-horizontal" role="form" id="form1" action="${ctxPath}/coach/save.action" method="post">

    <input type="hidden" name="userId" value="${coach.id}" />

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="username">用户名*</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="username" id="username" class="col-xs-12 col-sm-6" value="${coach.username}" />
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="newPassword">密码</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="newPassword" id="newPassword" class="col-xs-12 col-sm-6" />
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right">性别</label>

        <div class="col-xs-12 col-sm-9">
            <div class="radio">
                <label>
                    <input name="gender" type="radio" class="ace" value="男" <c:if test="${coach.gender eq '男'}">checked</c:if>>
                    <span class="lbl">男</span>
                </label>
                <label>
                    <input name="gender" type="radio" class="ace" value="女" <c:if test="${coach.gender eq '女'}">checked</c:if>>
                    <span class="lbl">女</span>
                </label>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="price">陪练单价*</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="price" id="price" class="col-xs-12 col-sm-6" value="${coach.price}" />
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="proportion">提成比例*</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="proportion" id="proportion" class="col-xs-12 col-sm-6" value="${coach.proportion == null ? 100 : coach.proportion}"/>%(填入0到100的数字)
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="nickname">昵称</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="nickname" id="nickname" class="col-xs-12 col-sm-6" value="${coach.nickname}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="name">真实姓名</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="name" id="name" class="col-xs-12 col-sm-6" value="${coach.name}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="yy">YY</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="yy" id="yy" class="col-xs-12 col-sm-6" value="${coach.yy}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="qq">QQ</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="qq" id="qq" class="col-xs-12 col-sm-6" value="${coach.qq}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="mobile">手机</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="mobile" id="mobile" class="col-xs-12 col-sm-6" value="${coach.mobile}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="idcard">身份证</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="idcard" id="idcard" class="col-xs-12 col-sm-6" value="${coach.idcard}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="email">邮箱</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="email" id="email" class="col-xs-12 col-sm-6" value="${coach.email}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="alipay">支付宝帐号</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="alipay" id="alipay" class="col-xs-12 col-sm-6" value="${coach.alipay}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="username">备注</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <textarea class="form-control" id="remark" name="remark" placeholder="备注">${coach.remark}</textarea>
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
                username: {
                    required: true
                },
                price: {
                    required: true,
                    number: true
                },
                proportion: {
                    required: true,
                    number: true,
                    range:[0,100]
                }
            },

            messages: {
                username: {
                    required: "请输入用户名"
                },
                price: {
                    required: "请输入陪练单价",
                    number: "陪练单价必须为数字"
                },
                proportion: {
                    required: "请输入提成比例",
                    number: "提成比例必须为数字",
                    range: "折扣范围必须为0-100"
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

