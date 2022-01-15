package com.example.pollsapi.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "USER")
public class User {

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", allocationSize = 1)
	private Long id;

	@Column(name = "USERNAME", unique = true, nullable = false)
	private String username;

	@Column(name = "PASSWORD")
	@NotNull (message = "Password cannot be null")
	private String password;

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean active;
	
	@ManyToMany(fetch = FetchType.EAGER) 
	@JoinTable(name = "USER_AUTHORITY",
			joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") },
			inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") })
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(Long id, 
					String username, 
					String password, 
					Boolean active, 
					Set<Role> roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.active = active;
		this.roles = roles;
	}

	public User(User user) {
		this.id = user.id;
		this.username = user.username;
		this.password = user.password;
		this.active = user.active;
		this.roles = user.roles;
	}

	public void addRole(Role role) {
		roles.add(role);
		role.getUsers().add(this);
   }

	public void addRoles(Set<Role> roles) {
		roles.forEach(this::addRole);
  	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isActive() {
		return this.active;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	
}
