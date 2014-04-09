package com.akkafun.w5.permission.model;

// Generated 2013-3-12 9:17:45 by Hibernate Tools 3.6.0

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Permission generated by hbm2java
 */
public class Permission implements java.io.Serializable,
		com.akkafun.platform.common.dao.Entity<Long>, Comparable<Permission>, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long resourceId;
	private Resource resource;
	private Long operationId;
	private Operation operation;

	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Operation getOperation() {
		return this.operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getOperationId() {
		return operationId;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}
	
	@Override
	public int compareTo(Permission myClass) {
//		Permission myClass = (Permission) object;
		return new CompareToBuilder().append(this.operationId,
				myClass.operationId).append(this.resource, myClass.resource)
				.append(this.operation, myClass.operation).append(this.id,
						myClass.id).append(this.resourceId, myClass.resourceId)
				.toComparison();
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Permission)) {
			return false;
		}
		Permission rhs = (Permission) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(
				this.operationId, rhs.operationId).append(this.resource,
				rhs.resource).append(this.operation, rhs.operation).append(
				this.id, rhs.id).append(this.resourceId, rhs.resourceId)
				.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-301303767, 1978533493).appendSuper(
				super.hashCode()).append(this.operationId)
				.append(this.resource).append(this.operation).append(this.id)
				.append(this.resourceId).toHashCode();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("operation", this.operation)
				.append("id", this.id).append("resource", this.resource)
				.append("operationId", this.operationId).append("resourceId",
						this.resourceId).toString();
	}
	
	@Override
	public Object clone() {
		Permission p = new Permission();
		p.setId(this.id);
		p.setOperation((Operation) this.getOperation().clone());
		p.setResource((Resource) this.getResource().clone());
		p.setOperationId(this.operationId);
		p.setResourceId(this.resourceId);
		return p;
	}

}
