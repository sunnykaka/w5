package com.akkafun.platform.common.web.el;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.support.ServletContextResource;

import com.akkafun.platform.Application;
import com.akkafun.platform.Platform;
import com.akkafun.platform.exception.PlatformException;
import com.akkafun.platform.util.Servlets;
import com.akkafun.platform.util.StringUtil;


public class PlatformFunction {
	
	private static Logger log = LoggerFactory.getLogger(PlatformFunction.class);
	
	private static Application app = Platform.getInstance().getApp();
	
	private static String contextPath = null;
	

	/**
	 * 将字符串中的换行符格式化成br标签
	 * @param str
	 * @return
	 */
	public static String replaceNR(String str) {
		if(str == null) return null;
		if(str.trim().equals("")) return "";
		str = str.replaceAll("\\r\\n", "<br/>");
		str = str.replaceAll("\\n", "<br/>");
		str = str.replaceAll("\\r", "<br/>");
		return str;
	}
	
	/**
	 * 将字符串中的转义字符输出为正常的html实体
	 * @param str
	 * @return
	 */
	public static String decodeHtml(String str) {
		if(str == null) return null;
		if(str.trim().equals("")) return "";
		return str.replaceAll("&amp;", "&")
			     .replaceAll("&lt;", "<")
			     .replaceAll("&gt;", ">")
			     .replaceAll("&apos;", "\'")
			     .replaceAll("&quot;", "\"")
			     .replaceAll("&nbsp;", " ")
			     .replaceAll("&copy;", "@")
			     .replaceAll("&reg;", "?");
		
	}
	
	/**
	 * 如果字符串的长度超过length个字符,后面的字符显示成省略号
	 * @param str
	 * @return
	 */
	public static String ellipsis(String str, Integer length) {
		return StringUtil.abbreviate(str, length, "...");
	}
	
	/**
	 * 判断数组中是否有某对象
	 * @param arr
	 * @param o
	 * @return
	 */
	public static boolean contains(Object[] arr, Object o) {
		if(arr == null || arr.length == 0) return false;
		if(o == null) return false;
		for(Object objInArr : arr) {
			if(o.equals(objInArr)) return true;
			if(objInArr instanceof String) {
				if(o.toString().equals(objInArr)) return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断数组中是否有某对象
	 * @param
	 * @param o
	 * @return
	 */
	public static boolean colContains(Collection<?> c, Object o) {
		if(c == null || c.isEmpty()) return false;
		if(o == null) return false;
		return c.contains(o);
	}
	
	/**
	 * 得到bindResult对象在request attribute中的key
	 * @param modelAttribute
	 * @return
	 */
	public static String getBindResultKey(String modelAttribute) {
		
		return  new StringBuilder().append(BindingResult.class.getName()).append(".").append(modelAttribute).toString();
	}
	
	/**
	 * 为表单域中添加sParam_开头的参数隐藏域
	 * @param pageContext
	 * @return
	 */
	public static String params(PageContext pageContext) {
		String pageIndex = pageContext.getRequest().getParameter("pageIndex");
		String pageSize = pageContext.getRequest().getParameter("pageSize");
		StringBuilder html = new StringBuilder();
		if(NumberUtils.isNumber(pageIndex)) {
			html.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\" />", "pageIndex", pageIndex));
		}
		if(NumberUtils.isNumber(pageSize)) {
			html.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\" />", "pageSize", pageSize));
		}
		Map<String, String[]> paramsMap = Servlets.getParametersStartingWith(pageContext.getRequest(), "sParam_");
		for(Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			if(values == null) continue;
			for(int i = 0; i < values.length; i++) {
				html.append(String.format("<input type=\"hidden\" name=\"sParam_%s\" value=\"%s\" />", key, values[i]));
			}
		}
		return html.toString();
	}
	
	/**
	 * 得到配置值
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		return app.getConfigValue(key);
	}
	
}
