package com.wct.config;

import com.wct.pojo.User;
import com.wct.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;


// 自定义Realm 授权+认证 doGetAuthenticationInfo  doGetAuthorizationInfo
// AuthenticatingRealm  doGetAuthenticationInfo
@SuppressWarnings("all")
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserServiceImpl userService;

    // 授权 在每次进入页面时会执行这个方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("doGetAuthorizationInfo==>授权");
        //在启动拦截了filterMap.put("/user/add","perms[user:add]");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //给指定的授权
//        info.addStringPermission("user:add");
        //从认证的返回值中获取登录的用户
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();

        info.addStringPermission(currentUser.getPerms());
        return info;
    }


    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo==>认证");
        //只要点击登录就会进入这个方法
        // 用来匹配登录的用户和密码(在登录时又将用户和密码封装到令牌中了)
        //UsernamePasswordToken implements HostAuthenticationToken
        //HostAuthenticationToken extends AuthenticationToken



        UsernamePasswordToken usertoken= (UsernamePasswordToken)token;

        // 数据库的用户名匹配
        User user = userService.getUserByName(usertoken.getUsername());

        //设置一个session，给它一个值loginUsername
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("loginUser",user);

        if (user==null){ // 用户不正确就报错
            return null;
        }

        // 密码认证 shir
        // 密码加密 : 默认 getCredentialsMatche(SimpleCredentialsMatcher)
        // 默认的实现类md5  md5盐值加密（在md5的加密上加一些参数）
        return new SimpleAuthenticationInfo(user,user.getPwd(),"");
    }


}
