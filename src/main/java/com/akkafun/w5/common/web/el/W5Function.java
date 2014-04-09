package com.akkafun.w5.common.web.el;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import com.akkafun.w5.W5Application;
import com.akkafun.w5.code.model.Code;
import com.akkafun.w5.code.service.CodeService;
import com.akkafun.w5.common.web.WebHolder;
import com.akkafun.w5.user.model.User;
import com.akkafun.platform.Platform;


public class W5Function {
	
	private static CodeService codeService = Platform.getInstance().getBean(CodeService.class);
	private static W5Application app = (W5Application)Platform.getInstance().getApp();

	/**
	 * 得到某一类型的数据字段缓存map
	 * @param kind
	 * @return
	 */
	public static Map<Long, Code> getCodeMap(String kind) {
		return codeService.getCodeMapByKind(kind);
	}
	
	/**
	 * 根据数据字典的id显示字面值
	 * @param codeId
	 * @return
	 */
	public static String displayCode(Long codeId) {
		Code code = codeService.getCodeById(codeId);
		if(code == null) return null;
		return code.getDisplayName();
	}

}
