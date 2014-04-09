<#if hasPara>
	<#assign url="${url}&pageIndex=">
<#else>
	<#assign url="${url}?pageIndex=">
</#if>
				
<#if (page.pageIndex > 0)>
	<#assign currentPage="${(page.pageIndex - 1)?c}">
<#else>
	<#assign currentPage="${page.pageIndex?c}">
</#if>
<script type="text/javascript">
	$(function(){
		$("div.ui-page-wrap").pagination(${page.totalRecordCount?c}, {
			num_edge_entries: 2,
			current_page: ${currentPage},
			num_display_entries: 4,
			items_per_page: ${page.pageSize?c},
			prev_text: '<前一页',
			next_text: '后一页>',
		    callback: callbackFun
		});
	});
	
	function goto(){
		var href = '${url}';
		var pageIndex = $("#pageIndex").val();
		if(parseInt(pageIndex) != pageIndex){
			alert("请输入数字!");
			return;
		}
		var total = ${page.pageCount?c};
		if(pageIndex > total){
			pageIndex = total;
		}
		window.location = '${url}' + pageIndex;
	}
		
	var callbackFun = function (pageIndex, pageDiv){ 
	    window.location = '${url}' + (pageIndex + 1);
	}
		
</script>
<div class="ui-page">
    <div class="ui-page-wrap">
        <b class="ui-page-skip">
            <span>${page.totalRecordCount?c}条 共${page.pageCount?c}页</span> 到第<input class="ui-page-skipTo" type="text" value="${page.pageIndex?c}" size="3" id="pageIndex">页
            <button type="button" class="ui-btn-s" onclick="javascript:goto();">确定</button>
        </b>
    </div>
</div>