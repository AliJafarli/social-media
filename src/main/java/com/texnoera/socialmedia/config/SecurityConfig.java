package com.texnoera.socialmedia.config;


import com.texnoera.socialmedia.security.filter.JwtRequestFilter;
import com.texnoera.socialmedia.security.handler.AccessRestrictionHandler;
import com.texnoera.socialmedia.service.abstracts.UserService;
import com.texnoera.socialmedia.service.model.IamServiceUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;
    private final AccessRestrictionHandler accessRestrictionHandler;

    private static final String GET = "GET";
    private static final String POST = "POST";

    private static final AntPathRequestMatcher[] NOT_SECURED_URLS = new AntPathRequestMatcher[]{
            new AntPathRequestMatcher("/auth-login", POST),
            new AntPathRequestMatcher("/auth-register", POST),
            new AntPathRequestMatcher("/auth-refresh-token", GET),

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(NOT_SECURED_URLS).permitAll()

                        .requestMatchers(post("/users-all")).hasAnyAuthority(adminAccessSecurityRoles())
                        .requestMatchers(post("/create-user")).hasAnyAuthority(adminAccessSecurityRoles())

                        .anyRequest().authenticated())
                .exceptionHandling(exceptions ->exceptions
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                                .accessDeniedHandler(accessRestrictionHandler)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserService userService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private String[] adminAccessSecurityRoles() {
        return new String[]{
                IamServiceUserRole.SUPER_ADMIN.name(),
                IamServiceUserRole.ADMIN.name()
        };
    }

    private static AntPathRequestMatcher get(String pattern) {
        return new AntPathRequestMatcher(pattern, GET);
    }

    private static AntPathRequestMatcher post(String pattern) {
        return new AntPathRequestMatcher(pattern, POST);
    }
}
