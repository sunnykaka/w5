package com.akkafun.w5.common.util;

import java.util.Date;

import com.akkafun.w5.common.model.BusinessData;
import com.akkafun.w5.common.model.Operator;
import com.akkafun.w5.user.model.User;
import com.akkafun.platform.Platform;
import com.akkafun.platform.id.IdGenerator;

public class OperatorUtil {
	
	private static IdGenerator idGenerator = Platform.getInstance().getBean(IdGenerator.class);

	public static Operator createOperator(BusinessData instance, User user) {
		Operator operator = new Operator();
		operator.setId(idGenerator.nextId(Operator.class));
		operator.setAddTime(new Date());
		operator.setUpdateTime(new Date());
		operator.setType(instance.getClass().getName());
		if(user != null) {
			operator.setCreatorId(user.getId());
		}
		instance.setOperator(operator);
		return operator;
	}
	
	public static Operator updateOperator(BusinessData instance, User user) {
		Operator operator = instance.getOperator();
		if(operator == null) {
			createOperator(instance, user);
		} else {
			operator.setUpdateTime(new Date());
			if(user != null) {
				operator.setUpdatorId(user.getId());
			}
		}
		return operator;
	}
	
}
