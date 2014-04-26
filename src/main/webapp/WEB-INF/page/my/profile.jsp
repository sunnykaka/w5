<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/page/inc/taglibs.jsp" %>
<c:set var="loginUser" value="${sessionScope['com.akkafun.w5.user.model.User'] }" />

<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <title>我的信息</title>
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
        <li class="active">我的信息</li>
    </ul>
    <!-- .breadcrumb -->
</div>

<div class="page-content">
<div class="page-header">
    <h1>
        我的信息
    </h1>
</div>
<!-- /.page-header -->

<div class="row">
<div class="col-xs-12">
<!-- PAGE CONTENT BEGINS -->

<form class="form-horizontal" role="form" id="form1" action="${ctxPath}/my/profile.action" method="post">

    <input type="hidden" name="userId" value="${loginUser.id}" />

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="username">用户名</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="username" id="username" class="col-xs-12 col-sm-6" readonly value="${loginUser.username}" />
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right">性别</label>

        <div class="col-xs-12 col-sm-9">
            <div class="radio">
                <label>
                    <input name="gender" type="radio" class="ace" value="男" <c:if test="${loginUser.gender eq '男'}">checked</c:if>>
                    <span class="lbl">男</span>
                </label>
                <label>
                    <input name="gender" type="radio" class="ace" value="女" <c:if test="${loginUser.gender eq '女'}">checked</c:if>>
                    <span class="lbl">女</span>
                </label>
            </div>
        </div>
    </div>

    <c:choose>
        <c:when test="${loginUser.type eq 'COACH'}">
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="balance">账户金额</label>

                <div class="col-xs-12 col-sm-9">
                    <div class="clearfix">
                        <input type="text" name="balance" id="balance" class="col-xs-12 col-sm-6" readonly value="${loginUser.balance}" />
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
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="proportion">提成比例</label>

                <div class="col-xs-12 col-sm-9">
                    <div class="clearfix">
                        <input type="text" name="proportion" id="proportion" class="col-xs-12 col-sm-6" readonly value="${loginUser.proportion == null ? 100 : loginUser.proportion}"/>%
                    </div>
                </div>
            </div>
        </c:when>
        <c:when test="${loginUser.type eq 'CUSTOMER'}">
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="balance">账户金额</label>

                <div class="col-xs-12 col-sm-9">
                    <div class="clearfix">
                        <input type="text" name="balance" id="balance" class="col-xs-12 col-sm-6" readonly value="${loginUser.balance}" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="discount">享受折扣</label>

                <div class="col-xs-12 col-sm-9">
                    <div class="clearfix">
                        <input type="text" name="discount" id="discount" class="col-xs-12 col-sm-6" readonly value="${loginUser.discount == null ? 100 : loginUser.discount}"/>%
                    </div>
                </div>
            </div>
        </c:when>
    </c:choose>


    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="nickname">昵称</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="nickname" id="nickname" class="col-xs-12 col-sm-6" value="${loginUser.nickname}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="name">真实姓名</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="name" id="name" class="col-xs-12 col-sm-6" value="${loginUser.name}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="yy">YY</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="yy" id="yy" class="col-xs-12 col-sm-6" value="${loginUser.yy}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="qq">QQ</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="qq" id="qq" class="col-xs-12 col-sm-6" value="${loginUser.qq}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="mobile">手机</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="mobile" id="mobile" class="col-xs-12 col-sm-6" value="${loginUser.mobile}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="idcard">身份证</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="idcard" id="idcard" class="col-xs-12 col-sm-6" value="${loginUser.idcard}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="email">邮箱</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="email" id="email" class="col-xs-12 col-sm-6" value="${loginUser.email}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="alipay">支付宝帐号</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <input type="text" name="alipay" id="alipay" class="col-xs-12 col-sm-6" value="${loginUser.alipay}"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="username">备注</label>

        <div class="col-xs-12 col-sm-9">
            <div class="clearfix">
                <textarea class="form-control" id="remark" name="remark" placeholder="备注">${loginUser.remark}</textarea>
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

    })
</script>

</body>

</html>

