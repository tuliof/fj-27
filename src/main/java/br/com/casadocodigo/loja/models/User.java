package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

@Entity
public class User implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    //~ Instance fields ================================================================================================
    @Id
    private String email;
    private String username;
    private String password;
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Role> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    //~ Constructors ===================================================================================================

    /**
	 * Default constructor for hibernate
	 */
    public User() {}
    
    /**
     * Calls the more complex constructor with all boolean arguments set to {@code true}.
     */
    public User(String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, email, true, true, true, true, authorities);
    }

    /**
     * Construct the <code>User</code> with the details required by
     * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
     *
     * @param username the username presented to the
     *        <code>DaoAuthenticationProvider</code>
     * @param password the password that should be presented to the
     *        <code>DaoAuthenticationProvider</code>
     * @param enabled set to <code>true</code> if the user is enabled
     * @param accountNonExpired set to <code>true</code> if the account has not
     *        expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials
     *        have not expired
     * @param accountNonLocked set to <code>true</code> if the account is not
     *        locked
     * @param authorities the authorities that should be granted to the caller
     *        if they presented the correct username and password and the user
     *        is enabled. Not null.
     *
     * @throws IllegalArgumentException if a <code>null</code> value was passed
     *         either as a parameter or as an element in the
     *         <code>GrantedAuthority</code> collection
     */
    public User(String username, String password, String email, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {

        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }

        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }

    //~ Methods ========================================================================================================

    @Override
	public Collection<Role> getAuthorities() {
        return authorities;
    }

    @Override
	public String getPassword() {
        return password;
    }

    @Override
	public String getUsername() {
        return username;
    }
    
    public String getEmail() {
    	return email;
    }

    @Override
	public boolean isEnabled() {
        return enabled;
    }

    @Override
	public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
	public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
	public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
	public void eraseCredentials() {
        password = null;
    }

    private static SortedSet<Role> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<Role> sortedAuthorities =
            new TreeSet<Role>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add((Role)grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        @Override
		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

    /**
     * Returns {@code true} if the supplied object is a {@code User} instance with the
     * same {@code username} value.
     * <p>
     * In other words, the objects are equal if they have the same username, representing the
     * same principal.
     */
    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof User) {
            return username.equals(((User) rhs).username);
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("\nUsername: ").append(this.username).append("; ");
        sb.append("\nPassword: [PROTECTED]; ");
        sb.append("\nEmail: ").append(this.email).append("; ");
        sb.append("\nEnabled: ").append(this.enabled).append("; ");
        sb.append("\nAccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("\ncredentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        sb.append("\nAccountNonLocked: ").append(this.accountNonLocked).append("; ");

        if (!authorities.isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }
}
