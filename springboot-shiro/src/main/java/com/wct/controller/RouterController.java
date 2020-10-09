package com.wct.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RouterController {

    @RequestMapping({"/","/index"})
    public String toIndex(Model model){

        model.addAttribute("msg","helloWorld");

        return "index";
    }

    @RequestMapping("/user/add")
    public String toAdd(){
        return "user/add";
    }
    @RequestMapping("/user/update")
    public String toUpdate(){
        return "user/update";
    }

   @RequestMapping("/toLogin")
   public String toLogin(){
        return "login";
   }


   @RequestMapping("/login")
   public String login(String username,String password,Model model){
        // 获取当前用户
       Subject subject = SecurityUtils.getSubject();

       // 将前端转递的用户密码封装到令牌中，认证方法中可以用token进行验证用户
       UsernamePasswordToken token = new UsernamePasswordToken(username, password);


       try {
           // 登录 成功就到index
           subject.login(token);
           return "index";
       } catch (UnknownAccountException e) { //用户不正确
           model.addAttribute("msg","用户信息不正确!");
           return "login";
       }catch (IncorrectCredentialsException e) { //密码不正确
           model.addAttribute("msg","密码不正确!");
           return "login";
       }
    }


    //未授权的页面
    @RequestMapping("/unauth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权不得访问!";
    }



}
