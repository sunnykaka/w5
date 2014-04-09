package com.akkafun.w5.common.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.akkafun.w5.permission.util.PermissionUtil;
import com.akkafun.w5.user.model.User;

public class PermissionCheckupTag extends SimpleTagSupport {

	// 权限对应的URL
	private String url;
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		User user = null;
		try {
			user = (User)getJspContext().getAttribute(User.SESSION_KEY, PageContext.SESSION_SCOPE);
		} catch (IllegalStateException ignore) {}
		boolean isPermitted = PermissionUtil.isPermitted(user, url);
		if(isPermitted) {
			//输出标签体
            getJspBody().invoke(null);
		}
		
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}