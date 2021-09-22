/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author Dirceu Junior
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AppUserDetailsService userDetailsService() {
        return new AppUserDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JsfLoginUrlAuthenticationEntryPoint jsfLoginUrlAuthenticationEntryPoint = new JsfLoginUrlAuthenticationEntryPoint();
        jsfLoginUrlAuthenticationEntryPoint.setLoginFormUrl("/login.xhtml");
        jsfLoginUrlAuthenticationEntryPoint.setRedirectStrategy(new JsfRedirectStrategy());

        JsfAccessDeniedHandler jsfAccessDeniedHandler = new JsfAccessDeniedHandler();
        jsfAccessDeniedHandler.setLoginPath("/access.xhtml");
        jsfAccessDeniedHandler.setContextRelative(true);

        http
                .csrf().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers("/login.xhtml", "/error.xhtml", "/landing.xhtml", "/javax.faces.resource/**").permitAll()
                .antMatchers("/dashboard.xhtml", "/access.xhtml").authenticated()
                .antMatchers("/contas/**").hasAnyRole("MASTER", "ADMINISTRADOR")
                .antMatchers("/baixas/**").hasAnyRole("MASTER", "ADMINISTRADOR")
                .antMatchers("/produtos/**").hasAnyRole("MASTER", "ADMINISTRADOR")
                .antMatchers("/sms/**").hasAnyRole("MASTER", "ADMINISTRADOR")
                .antMatchers("/cooperados/**").hasAnyRole("MASTER", "ADMINISTRADOR")
                .antMatchers("/usuarios/**").hasAnyRole("MASTER", "ADMINISTRADOR")
                .antMatchers("/configs/**").hasAnyRole("MASTER", "ADMINISTRADOR")
                .antMatchers("/**").denyAll()
                .and()
                .formLogin()
                .loginPage("/login.xhtml")
                .failureUrl("/login.xhtml?invalid=true")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access.xhtml")
                .authenticationEntryPoint(jsfLoginUrlAuthenticationEntryPoint)
                .accessDeniedHandler(jsfAccessDeniedHandler);

    }

}
