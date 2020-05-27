package com.lsqingfeng.action.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @className: SecurityConfiguration
 * @description:
 * @author: sh.Liu
 * @create: 2020-04-15 22:41
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder(){
        DelegatingPasswordEncoder delegatingPasswordEncoder =
                (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //设置defaultPasswordEncoderForMatches为NoOpPasswordEncoder
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
        return  delegatingPasswordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("action")
                .password("1234").roles("admin")
                .and()
                .withUser("admin")
                .password("admin").roles("admin");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略静态资源
        web.ignoring().antMatchers("/js/**", "/css/**","/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/test").permitAll() // 允许所有人访问test接口，该语句要放到anyRest的上面
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html") // 指定登录页面
                .loginProcessingUrl("/doLogin") // 指定登录时的方法
                .usernameParameter("name")      //前端参数名称：默认username
                .passwordParameter("passwd")    // 前端参数名称: 默认password
                .defaultSuccessUrl("/test")    //登录成功跳转到你访问的地址 如/hello
                .successForwardUrl("/index")    //表示不管你是从哪里来的，登录后一律跳转到
                .permitAll()              //登录相关请求不拦截
                .and()
                .csrf().disable();        //禁用csrf
    }
}
