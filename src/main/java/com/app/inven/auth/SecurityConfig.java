    package com.app.inven.auth;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        private final JwtAuthEntryPoint authEntryPoint;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(JwtAuthEntryPoint authEntryPoint,
                              JwtAuthenticationFilter jwtAuthenticationFilter) {
            this.authEntryPoint = authEntryPoint;
            this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth
                            // Public endpoints
                            .requestMatchers(
                                    "/api/auth/**",
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**"
                            ).permitAll()

                            // Product endpoints
                            .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/products").hasAnyRole("ADMIN", "INVENTORY_MANAGER")
                            .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAnyRole("ADMIN", "INVENTORY_MANAGER")
                            .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/api/products/trigger-reorder").hasAnyRole("ADMIN", "INVENTORY_MANAGER")
                            .requestMatchers(HttpMethod.PUT, "/api/products/reorder-config").hasAnyRole("ADMIN", "INVENTORY_MANAGER")

                            // Purchase Order endpoints
                            .requestMatchers(HttpMethod.GET, "/api/purchase-orders").hasAnyRole("ADMIN", "INVENTORY_MANAGER", "PURCHASE_OFFICER")
                            .requestMatchers(HttpMethod.GET, "/api/purchase-orders/**").hasAnyRole("ADMIN", "INVENTORY_MANAGER", "PURCHASE_OFFICER")
                            .requestMatchers(HttpMethod.POST, "/api/purchase-orders/**").hasAnyRole("ADMIN", "PURCHASE_OFFICER")
                            .requestMatchers(HttpMethod.PUT, "/api/purchase-orders/**").hasAnyRole("ADMIN", "PURCHASE_OFFICER")
                            .requestMatchers(HttpMethod.DELETE, "/api/purchase-orders/**").hasRole("ADMIN")

                            // All other requests
                            .anyRequest().authenticated()
                    )
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }