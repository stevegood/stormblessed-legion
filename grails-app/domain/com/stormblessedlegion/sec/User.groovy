package com.stormblessedlegion.sec

class User {

	transient springSecurityService

	String username
	String password
    String email
	boolean enabled = true
	boolean accountExpired = false
	boolean accountLocked = false
	boolean passwordExpired = false

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password blank: false
        email blank: false, unique: true, email: true
	}

	static mapping = {
		password column: '`password`'
        table 'invicta_users'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
