package com.akkafun.w5.common.util;

import java.util.List;

public class ImportResult {

	private List<String[]> datas;
	
	private String[] wrongInfos;
	
	private int successNum;
	
	private int failureNum;
	
	public List<String[]> getDatas() {
		return datas;
	}

	public void setDatas(List<String[]> datas) {
		this.datas = datas;
	}

	public String[] getWrongInfos() {
		return wrongInfos;
	}

	public void setWrongInfos(String[] wrongInfos) {
		this.wrongInfos = wrongInfos;
	}

	public int getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}

	public int getFailureNum() {
		return failureNum;
	}

	public void setFailureNum(int failureNum) {
		this.failureNum = failureNum;
	}

	public boolean isSuccess() {
		return failureNum == 0;
	}
	
}
