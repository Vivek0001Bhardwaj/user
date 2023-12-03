package com.techolution.user.config;

import com.techolution.user.security.MyBasicAuthenticationEntryPoint;
import com.techolution.user.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final CustomUserDetailsServiceImpl customUserDetailsService;
	private final MyBasicAuthenticationEntryPoint unauthorizedHandler;
    private final MyAuthenticationFilter myAuthenticationFilter;

	@Autowired
	public SecurityConfig(CustomUserDetailsServiceImpl customUserDetailsService,
                          MyBasicAuthenticationEntryPoint unauthorizedHandler,
                          MyAuthenticationFilter myAuthenticationFilter) {
		this.customUserDetailsService = customUserDetailsService;
		this.unauthorizedHandler = unauthorizedHandler;
		this.myAuthenticationFilter = myAuthenticationFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().headers().frameOptions().disable()
				.and()
				.csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/users/checkUsernameAvailability", "/api/users/checkEmailAvailability").permitAll()
				.requestMatchers(PathRequest.toH2Console()).permitAll()
				.anyRequest().authenticated();

		http.addFilterBefore(myAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}

	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
