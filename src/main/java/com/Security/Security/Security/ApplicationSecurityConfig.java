package com.Security.Security.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import static com.Security.Security.Security.ApplicationUserPermission.*;
import static com.Security.Security.Security.ApplicationUserRole.*;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(antMatcher("/*")).permitAll()
                        .requestMatchers(antMatcher("index")).permitAll()
                        .requestMatchers(antMatcher("/users/**")).hasRole(STUDENT.name())
                        .requestMatchers(antMatcher(HttpMethod.DELETE ,"/managment/users/**")).hasAuthority(COURSE_WRITE.getPermission())
                        .requestMatchers(antMatcher(HttpMethod.PUT ,"/managment/users/**")).hasAuthority(COURSE_WRITE.getPermission())
                        .requestMatchers(antMatcher(HttpMethod.POST ,"/managment/users/**")).hasAuthority(COURSE_WRITE.getPermission())
                        .requestMatchers(antMatcher(HttpMethod.GET ,"/managment/users/**")).hasAnyRole(ADMIN.name() , ADMINTRAINER.name())
                        .anyRequest().authenticated()
                )

                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails saifUser = User.builder()
                .username("saif")
                .password(passwordEncoder.encode("password"))
//                .roles(STUDENT.name()) // ROLE_STUDENT
                .authorities(STUDENT.getGrantedAuthorities())
                .build();
        UserDetails amigoUser = User.builder()
                .username("amigo")
                .password(passwordEncoder.encode("admin"))
//                .roles(ADMIN.name()) //ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails HamaUser = User.builder()
                .username("Hama")
                .password(passwordEncoder.encode("admin2"))
//                .roles(ADMINTRAINER.name()) //ROLE_ADMINTRAINER
                .authorities(ADMINTRAINER.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                  saifUser
                , amigoUser
                , HamaUser);
    }
}