package com.akkafun.w5.common.util;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
 
/**
 * 可以按properties文件顺序遍历的类,使用keySet方法遍历
 * @author liubin
 *
 */
public class OrderedProperties extends Properties {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();
 
    public Enumeration<Object> keys() {
        return Collections.<Object> enumeration(keys);
    }
 
    public Object put(Object key, Object value) {
        keys.add(key);
        return super.put(key, value);
    }
 
    public Set<Object> keySet() {
        return keys;
    }
 
    public Set<String> stringPropertyNames() {
        Set<String> set = new LinkedHashSet<String>();
 
        for (Object key : this.keys) {
            set.add((String) key);
        }
 
        return set;
    }
}