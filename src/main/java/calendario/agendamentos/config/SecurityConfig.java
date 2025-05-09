package calendario.agendamentos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import calendario.agendamentos.Usuario.UsuarioService;
import calendario.agendamentos.seguranca.CustomAuthenticationProvider;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)  // Custom UserDetailsService
                .passwordEncoder(passwordEncoder())       // Custom PasswordEncoder
                .and()
                .build();
    }
	

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider customAuthenticationProvider) throws Exception {
    	http.cors() // Enable CORS for Spring Security
        .and()
        .csrf().disable().authenticationProvider(customAuthenticationProvider)
        .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .antMatchers("/login").permitAll()
                    .antMatchers("/api/auth/login").permitAll()
                    .antMatchers("/administracao/**").authenticated()
                    .anyRequest().permitAll()
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login")
                    .defaultSuccessUrl("/administracao/home", true)
                    .loginProcessingUrl("/efetuarLogin")
                    .permitAll()
            )
            .logout(logout ->
            logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login?logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
    )
            .authenticationProvider(customAuthenticationProvider);
        return http.build();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

}
