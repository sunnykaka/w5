<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>


<script type="text/javascript">
	//存放页面的分页参数与查询参数
	var pageVariables = {searchParams:{}};
	<%
	Map params = request.getParameterMap();
	for(Iterator iter = params.entrySet().iterator(); iter.hasNext();) {
		Map.Entry entry = (Map.Entry) iter.next();
		String name = (String) entry.getKey();
		String[] values = (String[]) entry.getValue();
		if(values.length==0 || values[0]==null || "".equals(values[0].trim())) continue;
		name = name.trim();
		if("pageIndex".equals(name)) {
			Integer pageIndex = null;
			try {
				pageIndex = Integer.parseInt(values[0].trim());
			} catch (NumberFormatException ignore) {}
			if(pageIndex != null) {
				out.println("pageVariables.pageIndex = '" + pageIndex + "'");
			}
		} else if("pageSize".equals(name)) {
			Integer pageSize = null;
			try {
				pageSize = Integer.parseInt(values[0].trim());
			} catch (NumberFormatException ignore) {}
			if(pageSize != null) {
				out.println("pageVariables.pageSize = '" + pageSize + "'");
			}
		} else if(name.startsWith("sParam_")) {
			//String paramName = name.substring(7);
			out.println("pageVariables.searchParams['" + name + "'] = [];");
			for(String value : values) {
				out.println("pageVariables.searchParams['" + name + "'].push('" + value + "');");
			}
		}
	}
	%>
</script>