package com.akkafun.w5.permission.model;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

// Generated 2013-3-12 9:17:45 by Hibernate Tools 3.6.0


/**
 * Operation generated by hbm2java
 */
public class Operation implements java.io.Serializable,
		com.akkafun.platform.common.dao.Entity<Long>, Comparable<Operation>, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String url;
	private String name;
	private Boolean configable = false;
	private String required;
	private Integer type;
	private Long resourceId;

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getConfigable() {
		return this.configable;
	}

	public void setConfigable(Boolean configable) {
		this.configable = configable;
	}

	public String getRequired() {
		return this.required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public int compareTo(Operation myClass) {
		return new CompareToBuilder().append(this.id, myClass.id).toComparison();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Operation)) {
			return false;
		}
		Operation rhs = (Operation) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(this.id, rhs.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(1596355739, -978009971).appendSuper(
				super.hashCode()).append(this.id).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).toString();
	}
	
	@Override
	public Object clone() {
		Operation operation = new Operation();
		operation.setId(id);
		operation.setName(name);
		operation.setResourceId(resourceId);
		operation.setUrl(url);
		return operation;
	}

}
