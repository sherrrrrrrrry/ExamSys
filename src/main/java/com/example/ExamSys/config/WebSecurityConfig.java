package com.example.ExamSys.config;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.ExamSys.security.MyUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//
	@Autowired
    MyUserDetailsService userDetailsService;
	
	@Autowired
	private ObjectMapper objectMapper;
//
//    private final boolean DEBUG = true;
//
//    public WebSecurityConfig(UserDetailsService userDetailsService) {
//
//        this.userDetailsService = userDetailsService;
//    }
//	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth.inMemoryAuthentication()
//		.withUser("root").password("123").authorities("ROLE_ADMIN");
//	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/js/**","/css/**","/img/**","/fonts/**","/**/*.png","/**/*.jpg", "/**/*.html", "/*.html", "/*.jpg").permitAll()
			.antMatchers("/**").permitAll()//
			.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
			.antMatchers("/api/**").permitAll()
			.antMatchers("/account/**").permitAll()
			.antMatchers("/student/**").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/login.go")
			.loginProcessingUrl("/login")
			.defaultSuccessUrl("/")
//			.failureUrl("/login?error")
			.successHandler(getSuccessHandler())
			.failureHandler(getFailureHandler())
			.usernameParameter("username")
			.passwordParameter("password")
			.permitAll()
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/")
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
			.permitAll()
			.and()
			.csrf()
			.disable();
	}
	
	@Bean
	public AuthenticationSuccessHandler getSuccessHandler() {
		return new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException{
//				String targetUrl = req.getParameter("targetUrl");
//				if(targetUrl == null || "".equals(targetUrl)) {
//					targetUrl = "/";
//				}
//				
//				Object principal = auth.getPrincipal();
//				resp.setContentType("application/json;charset=utf-8");
//				PrintWriter out = resp.getWriter();
//				resp.setStatus(200);
//				Map<String, Object> map = new HashMap<>();
//				map.put("status", 200);
//				map.put("msg", principal);
//				ObjectMapper om = new ObjectMapper();
//				out.write(om.writeValueAsString(map));
//				out.flush();
//				out.close();
//				new DefaultRedirectStrategy().sendRedirect(req, resp, "/index.html");
//				Map<String, Object> map = new HashMap<>();
//				map.put("auth", "12312");
//				resp.setContentType("text/html;charset=UTF-8");
//				ServletOutputStream sos = resp.getOutputStream();
//				sos.write("你好！123".getBytes());
				resp.setContentType("application/json;charset=UTF-8");
				HashMap<String, String> auths = new HashMap<String, String>();
				auths.put("auth", auth.getAuthorities().toString());
				resp.getWriter().write(objectMapper.writeValueAsString(auths));
				return ;
			}
		};
	}
	
	@Bean
	public AuthenticationFailureHandler getFailureHandler() {
		return new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp,
					org.springframework.security.core.AuthenticationException e)
					throws IOException, ServletException {
				// TODO Auto-generated method stub
				
//				resp.setContentType("application/json;charset=utf-8");
//				PrintWriter out = resp.getWriter();
//				resp.setStatus(401);
//				Map<String, Object> map = new HashMap<>();
//				map.put("status", 401);
//				if(e instanceof LockedException) {
//					map.put("msg", "账户被锁定，登陆失败！");
//				} else if (e instanceof BadCredentialsException) {
//					map.put("msg", "账户名或密码输入错误，登陆失败！");
//				} else if (e instanceof DisabledException) {
//					map.put("msg", "账户被禁用，登陆失败！");
//				} else if(e instanceof AccountExpiredException) {
//					map.put("msg", "账户已过期，登陆失败！");
//				} else if(e instanceof CredentialsExpiredException) {
//					map.put("msg", "密码已过期，登陆失败！");
//				} else {
//					map.put("msg", "登陆失败！");
//				}
//				ObjectMapper om = new ObjectMapper();
//				out.write(om.writeValueAsString(map));
//				out.flush();
//				out.close();
			}
		};
	}
}