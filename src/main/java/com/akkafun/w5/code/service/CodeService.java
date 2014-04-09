package com.akkafun.w5.code.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akkafun.w5.code.dao.CodeDao;
import com.akkafun.w5.code.model.Code;
import com.akkafun.platform.Platform;
import com.akkafun.platform.exception.PlatformException;
import com.akkafun.platform.genericdao.search.Search;
import com.akkafun.platform.mapper.JsonMapper;

@Service
public class CodeService {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	// 数据字典缓存Map<id, 数据字典对象>
	private Map<Long, Code> idBuffer = null;

	// 数据字典缓存Map<类型， Map<id, 数据字典对象>
	private Map<String, Map<Long, Code>> kindBuffer = null;

	public static final String KIND_CODE_KEY = "com.akkafun.w5.code.Code";

	//直报年度有关
	public static final String DECLARATION_YEAR = "declaration_year";
	public static final String DECLARATION_YEAR_IS_OPENING = "declaration_year_is_opening";
	public static final String CURRENT_DECLARATION_YEAR_VALUE = "current";

	//是否启用重点调查
	public static final String MAJOR_INQUIRY = "major_inquiry";

	public static final String CATEGORY = "category";
	
	//公司业务类型
	public static final String COMPANY_BUSINESS_TYPE= "company_business_type";
	//登记注册类型
	public static final String COMPANY_REGISTER= "company_register";
	//执行会计制度
	public static final String COMPANY_ACCOUNTING= "accounting";
	//机构类型
	public static final String COMPANY_ORGANIZATION= "organization";

	//技术支持联系方式
	public static final String SUPPORT_CONTACT= "support_contact";
	//页脚联系方式和版权
	public static final String FOOTER_INFO= "footer_info";
	
	@Autowired
	private CodeDao codeDao;

	@Transactional(readOnly = true)
	public synchronized void load() {
		List<Code> codes = codeDao.search(new Search().addSortDesc("kind").addSortAsc("id"));
		Map<Long, Code> kind = null;
		Map<Long, Code> tempIdBuffer = new TreeMap<>();
		Map<String, Map<Long, Code>> tempKindBuffer = new LinkedHashMap<>();
		for (Code code : codes) {
			Code cacheCode = (Code)code.clone();
			tempIdBuffer.put(code.getId(), cacheCode);
			if((kind = tempKindBuffer.get(code.getKind().toLowerCase().trim())) == null) {
				kind = new TreeMap<Long, Code>();
				tempKindBuffer.put(code.getKind().toLowerCase(), kind);
			}
			synchronized(kind) {
				kind.put(code.getId(), cacheCode);
			}
		}
		//初始化code的树状关系
		for(Map.Entry<Long, Code> entry : tempIdBuffer.entrySet()) {
			Code code = entry.getValue();
			String path = code.getPath();
			if(StringUtils.isBlank(path)) continue;
			String[] parents = path.split("/");
			//path路径的最后一个节点就是父节点
			Long parentId = Long.parseLong(parents[parents.length - 1]);
			Code parent = tempIdBuffer.get(parentId);
			if(parent == null) {
				throw new PlatformException(String.format("初始化code的时候,根据path属性中的id找不到对应的code,当前code[%s], 不存在的parentId[%s]", code.getId().toString(), parentId.toString()));
			}
			code.setParent(parent);
			List<Code> children = parent.getChildren();
			if(children == null) {
				children = new ArrayList<>();
				parent.setChildren(children);
			}
			children.add(code);
		}
		idBuffer = tempIdBuffer;
		kindBuffer = tempKindBuffer;
	}


	public Code getCodeById(Long codeId) {
		Code code = idBuffer.get(codeId);
		if(code == null) {
			log.warn("id为{}的code为null", codeId);
		}
		return code;
	}

	public Map<Long, Code> getCodeMapByKind(String kind) {
		Map<Long, Code> codeMap = kindBuffer.get(kind);
		if(codeMap == null || codeMap.isEmpty()) {
			log.warn("kind为{}的codeMap为null", kind);
		}
		return codeMap;
	}
	
	public Code getCodeByKindAndDisplayName(String kind, String displayName) {
		return getCodeByConditions(kind, displayName, null);
	}
	
	public Code getCodeByConditions(String kind, String displayName, Integer depth) {
		Map<Long, Code> codeMap = getCodeMapByKind(kind);
		if(codeMap != null) {
			for(Map.Entry<Long, Code> entry : codeMap.entrySet()) {
				Code code = entry.getValue();
				if(code.getDisplayName() != null && code.getDisplayName().equals(displayName)) {
					if(depth == null) {
						return code;
					} else {
						if(code.getDepth() >= depth) {
							//当大于或等于要求的深度时,反正该字典
							return code;
						}
					}
				}
			}
		}
		return null;
	}

}
