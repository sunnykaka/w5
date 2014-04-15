<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/page/inc/taglibs.jsp" %>

<div class="sidebar" id="sidebar">
    <script type="text/javascript">
        try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
    </script>

    <ul class="nav nav-list">
        <li data-first-level="true">
            <a href="#" class="dropdown-toggle">
                <i class="icon-list"></i>
                <span class="menu-text">会员管理</span>

                <b class="arrow icon-angle-down"></b>
            </a>

            <ul class="submenu">
                <w5tag:permission url="/customer/add.action">
                    <li data-second-level="true">
                        <a href="${ctxPath}/customer/add.action">
                            <i class="icon-double-angle-right"></i>
                            新增会员
                        </a>
                    </li>
                </w5tag:permission>

                <w5tag:permission url="/customer/list.action">
                    <li data-second-level="true">
                        <a href="${ctxPath}/customer/list.action">
                            <i class="icon-double-angle-right"></i>
                            查询会员
                        </a>
                    </li>
                </w5tag:permission>
            </ul>
        </li>

        <li data-first-level="true">
            <a href="#" class="dropdown-toggle">
                <i class="icon-list"></i>
                <span class="menu-text">陪练管理</span>

                <b class="arrow icon-angle-down"></b>
            </a>

            <ul class="submenu">
                <w5tag:permission url="/coach/add.action">
                    <li data-second-level="true">
                        <a href="${ctxPath}/coach/add.action">
                            <i class="icon-double-angle-right"></i>
                            新增陪练
                        </a>
                    </li>
                </w5tag:permission>

                <w5tag:permission url="/coach/list.action">
                    <li data-second-level="true">
                        <a href="${ctxPath}/coach/list.action">
                            <i class="icon-double-angle-right"></i>
                            查询陪练
                        </a>
                    </li>
                </w5tag:permission>
            </ul>
        </li>
    </ul><!-- /.nav-list -->

    <div class="sidebar-collapse" id="sidebar-collapse">
        <i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
    </div>

    <script type="text/javascript">
        try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}

        //加载页面的时候根据url选中左边的菜单
        (function() {
            var pathnameArr = window.location.pathname.split("/");
            //e.g. ["/", "w5", "customer", "list"]
            var startIndex = GV.ctxPath ? 2 : 1;
            if(pathnameArr.length < startIndex + 1) {
                return;
            }
            var firstLevelPath = pathnameArr[startIndex];
            var secondLevelPath = pathnameArr[startIndex + 1];
            $("li[data-first-level='true']").each(function() {
                var firstLevelMenu = $(this);
                var firstLevelMenuHasActive = false;
                firstLevelMenu.find("li[data-second-level='true']").each(function() {
                    var secondLevelMenu = $(this);
                    var uri = secondLevelMenu.find("a").attr("href");
                    if(uri && uri.indexOf(firstLevelPath) != -1 && firstLevelMenuHasActive === false) {
                        firstLevelMenu.addClass("active open");
                        firstLevelMenuHasActive = true;
                    }
                    if(uri && uri.indexOf(secondLevelPath) != -1) {
                        secondLevelMenu.addClass("active");
                        return false;
                    }
                });

            });
        })();

        //二级菜单数量为0的父菜单不显示
        (function() {
            $("li[data-first-level='true']").each(function() {
                var firstLevelMenu = $(this);
                if(firstLevelMenu.find("li[data-second-level='true']").length == 0) {
                    firstLevelMenu.hide();
                }
            });
        })();


    </script>
</div>