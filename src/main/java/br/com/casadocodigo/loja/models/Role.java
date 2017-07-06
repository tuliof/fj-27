package br.com.casadocodigo.loja.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

@Entity
public class Role implements GrantedAuthority {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	@Id
    private String role;

	/**
	 * Default constructor for hibernate
	 */
	public Role() {}
	
    public Role(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    @Override
	public String getAuthority() {
        return role;
    }

    @Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SimpleGrantedAuthority) {
            return role.equals(((Role) obj).role);
        }

        return false;
    }

    @Override
	public int hashCode() {
        return this.role.hashCode();
    }

    @Override
	public String toString() {
        return this.role;
    }
}
