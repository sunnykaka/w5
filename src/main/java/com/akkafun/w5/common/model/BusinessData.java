package com.akkafun.w5.common.model;

/**
 * 
 * @author liubin
 *
 */
public interface BusinessData {

	/**
	 * 取得操作人
	 * @return 操作人
	 */
	public Operator getOperator();
	
	/**
	 * 设置操作人
	 * @param operator 操作人
	 */
	public void setOperator(Operator operator);
}