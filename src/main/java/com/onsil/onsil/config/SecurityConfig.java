package com.onsil.onsil.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    //스프링 컨테이너에 등록되고 di할 수 있다..
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                        (auth) ->auth.requestMatchers(
//                                            "/",
//                                            "/index/index",
//                                            "/member/login",
//                                            "/member/signup",
//                                            "/api/**",
//                                            "/css/**",
//                                            "/images/**",
//                                            "/js/**"),
//                                            "/html/**"
                                "/**") //작업 편하게 하기 위해 임시로 모든 경로 보안 허용
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/member/login")
                                .usernameParameter("userID")
                                .passwordParameter("userPW")
                                .loginProcessingUrl("/member/login")
                                .defaultSuccessUrl("/index/index",true)
                                .failureUrl("/member/login?error") //redirect로 넘어간다.
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                    .logoutUrl("/member/logout")
                                    .logoutSuccessUrl("/index/index")
                                    .invalidateHttpSession(true)
                                    .deleteCookies("JSESSIONID")
                                    .permitAll()
                )
                .csrf((csrf)->csrf.disable()); //끝나고 찾아보기
        return httpSecurity.build();
    }
}

