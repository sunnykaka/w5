package com.akkafun.w5.permission.model;

// Generated 2013-3-12 9:17:45 by Hibernate Tools 3.6.0

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.akkafun.w5.common.model.BusinessData;
import com.akkafun.w5.common.model.Operator;

/**
 * Role generated by hbm2java
 */
public class Role implements java.io.Serializable,
		com.akkafun.platform.common.dao.Entity<Long>, BusinessData, Comparable<Role>, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String url;
	private String description;
	private Boolean admin = false;
	private Boolean updatable = true;
	
	private Long parentId;
	private Role parent;
	private Operator operator;
	private Set<Permission> permissions = new HashSet<Permission>(0);


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getAdmin() {
		return this.admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Role getParent() {
		return parent;
	}

	public void setParent(Role parent) {
		this.parent = parent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}


	@Override
	public int compareTo(Role myClass) {
		return new CompareToBuilder().append(this.parentId, myClass.parentId)
				.append(this.name, myClass.name).append(this.parent, myClass.parent)
				.append(this.id, myClass.id).toComparison();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Role)) {
			return false;
		}
		Role rhs = (Role) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(this.parentId, rhs.parentId)
				.append(this.permissions,rhs.permissions).append(this.name, rhs.name)
				.append(this.parent, rhs.parent).append(this.id, rhs.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(-1903814475, 1107969487).appendSuper(super.hashCode())
				.append(this.parentId).append(this.permissions).append(this.name).append(this.parent)
				.append(this.id).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name", this.name).append("parent",this.parent)
				.append("id", this.id).append("parentId", this.parentId)
				.append("permissions",this.permissions).toString();
	}

	public Boolean getUpdatable() {
		return updatable;
	}

	public void setUpdatable(Boolean updatable) {
		this.updatable = updatable;
	}

}