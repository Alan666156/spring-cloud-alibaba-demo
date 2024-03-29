package com.fuhx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * security config 解决访问http接口，页面弹出login登录
 * springboot2.0版本以后需要手动配置类，1.x版本中配置文件加入security.basic.enabled=false，2.0版本以后配置不生效
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开放post、put请求
        http.csrf().disable();
        //配置不需要登录验证
        http.authorizeRequests()
                .anyRequest().permitAll().and().logout().permitAll();
    }

}
