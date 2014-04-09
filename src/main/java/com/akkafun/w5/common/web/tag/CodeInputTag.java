package com.akkafun.w5.common.web.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.akkafun.w5.code.model.Code;
import com.akkafun.w5.code.service.CodeService;
import com.akkafun.platform.Platform;

public class CodeInputTag extends SimpleTagSupport {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private String kind;
	
	private String name;
	
	//select(default),radio,checkbox
	private String type;
	
	private String value;
	
	private boolean required;
	
	private CodeService codeService = Platform.getInstance().getBean(CodeService.class);
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		Map<Long, Code> codeMap = codeService.getCodeMapByKind(kind);
		if(codeMap == null || codeMap.isEmpty()) {
			return;
		}
		//获取页面输出流
		Writer out = getJspContext().getOut();
		StringBuilder sb = new StringBuilder();
		//初始化根节点列表
		List<Code> roots = new ArrayList<>();
		for(Map.Entry<Long, Code> entry : codeMap.entrySet()) {
        	Code code = entry.getValue();
        	if(code.getParent() == null) {
        		//根节点
        		roots.add(code);
        	}
        }
        
		if("radio".equalsIgnoreCase(type)) {
			int i=0;
			for(Code code : roots) {
				handleRadioCode(code, sb, ++i);
			}
		} else if("checkbox".equalsIgnoreCase(type)){
			//多选框
			int i=0;
			for(Code code : roots) {
				handleCheckboxCode(code, sb, ++i);
			}
		} else {
			//下拉框
			sb.append(String.format("<select name='%s'>", name));
			for(Code code : roots) {
				handleSelectCode(code, sb);
			}
			sb.append("</select>");
		}
        out.write(sb.toString());
	}
	
	private void handleSelectCode(Code code, StringBuilder sb) {
		if(code.getChildren() == null || code.getChildren().isEmpty()) {
			String checked = "";
			if(StringUtils.isNumeric(value) && code.getId().equals(Long.parseLong(value))) {
				checked = "selected='selected'";
			}
			sb.append(String.format("<option value='%d' %s>%s</option>", code.getId(), checked,
					showDisplayName(code.getDisplayName(), "&nbsp;&nbsp;", code.getDepth())));
    	} else {
    		sb.append(String.format("<optgroup label='%s'>", showDisplayName(code.getDisplayName(), "&nbsp;&nbsp;", code.getDepth())));
    		for(Code child : code.getChildren()) {
    			//处理子节点
    			handleSelectCode(child, sb);
    		}
    		sb.append("</optgroup>");
    	}
	}
	
	private void handleRadioCode(Code code, StringBuilder sb, int i) {
		String checked = "";
		String requiredString = "";
		if(StringUtils.isNumeric(value)) {
			if(code.getId().equals(Long.parseLong(value))) {
				checked = "checked='checked'";
			}
		}
		if(required) {
			requiredString = "data-validation-engine='validate[required]'";
		}
		sb.append(String.format("<label><input type='radio' value='%d' %s %s name='%s'>%s</label>", code.getId(), checked,
				requiredString, name, String.valueOf(i) + "、" + code.getDisplayName()));
	}

	private void handleCheckboxCode(Code code, StringBuilder sb, int i) {
		String checked = "";
		String requiredString = "";
		if(!StringUtils.isBlank(value)) {
			Set<String> checkedIds = Sets.newHashSet(value.split(":"));
			if(checkedIds.contains(String.valueOf(code.getId()))) {
				checked = "checked='checked'";
			}
		}
		if(required) {
			requiredString = "data-validation-engine='validate[required]'";
		}
		sb.append(String.format("<label><input type='checkbox' value='%d' %s %s name='%s'>%s</label>", code.getId(), checked,
				requiredString, name, String.valueOf(i) + "、" + code.getDisplayName()));
	}
	
	private String showDisplayName(String displayName, String blank, int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < depth; i++) {
			sb.append(blank);
		}
		sb.append(displayName);
		return sb.toString();
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

}