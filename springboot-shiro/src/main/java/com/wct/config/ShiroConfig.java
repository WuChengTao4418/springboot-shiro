package com.wct.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@SuppressWarnings("all")
public class ShiroConfig {

    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean factoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 设置安全管理器
        factoryBean.setSecurityManager(securityManager);


        /*
          设置shiro 的内置过滤器

          anno  无需认证可以访问
          authc  必须认证
          user  必须拥有记住我功能
          role 拥有某个角色权限
          perms　拥有对某个资源权限

         */

        Map<String,String> filterMap = new LinkedHashMap<>();



        // add和update需要认证才能访问 会跳到登录界面
        filterMap.put("/user/add","authc");
        filterMap.put("/user/update","authc");


        //授权  type=Unauthorized, status=401 回调到一个没有授权的页面
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");

        factoryBean.setFilterChainDefinitionMap(filterMap);

        //跳到登录界面（没有认证的请求）
        factoryBean.setLoginUrl("/toLogin");

        //未授权的跳转到 /unauth
        factoryBean.setUnauthorizedUrl("/unauth");

        return factoryBean;

    }



    //DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联realm
        securityManager.setRealm(userRealm);

        return securityManager;
    }

    //realm
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }

    // 整合shiro-thymeleaf
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
