package org.booking.core.config.security;

import lombok.RequiredArgsConstructor;
import org.booking.core.domain.entity.role.RoleClassification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
public class HttpSecurityConfiguration {

	private static final String[] PUBLIC_LIST_URL = {"/api/v1/auth/**"};
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authenticationProvider;
	private final CustomLogoutHandler customLogoutHandler;

	@Bean
	@Order(1)
	public SecurityFilterChain publicFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(req ->
						req.requestMatchers(PUBLIC_LIST_URL)
								.permitAll()
								.anyRequest()
								.authenticated()
				)
				.sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.logout(logout ->
						logout.logoutUrl("/api/v1/auth/logout")
								.addLogoutHandler(customLogoutHandler)
								.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
				)
				.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(request ->
						request.requestMatchers("/api/**")
								.hasRole(RoleClassification.ADMIN.name())
								.anyRequest()
								.authenticated()
				).sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	@Order(3)
	public SecurityFilterChain managementFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(request ->
						request.requestMatchers("/api/v1/managements/**")
								.hasRole(RoleClassification.MANAGER.name())
								.anyRequest()
								.authenticated()
				).sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	@Order(4)
	public SecurityFilterChain customerFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(request ->
						request.requestMatchers("/api/v1/customers/**")
								.hasRole(RoleClassification.CUSTOMER.name())
								.anyRequest()
								.authenticated()
				).sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

}
