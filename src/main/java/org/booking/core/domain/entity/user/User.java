package org.booking.core.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity(name = User.ENTITY_NAME)
@Table(name = User.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity implements UserDetails {

	public static final String ENTITY_NAME = "User";
	public static final String TABLE_NAME = "users";

	private String name;
	private String email;
	private String password;
	private String salt;
	private String timezone;
	@CollectionTable
	private Set<String> roles = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private Contact contact;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
				.toList();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return !deleted;
	}

}
