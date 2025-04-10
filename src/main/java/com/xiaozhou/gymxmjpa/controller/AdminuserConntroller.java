package com.xiaozhou.gymxmjpa.controller;



import com.xiaozhou.gymxmjpa.dao.AdminuserDao;
import com.xiaozhou.gymxmjpa.entity.Adminuser;
import com.xiaozhou.gymxmjpa.util.ThreadLocalHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 管理员登录Controller控制层
 * @Author: LiuJian
 * @Date: 2020/4/4
 */
@Slf4j
@Controller
@RequestMapping("/")
public class AdminuserConntroller {
    @Autowired
    private AdminuserDao adminuserDao;

    /**
     * @Description: 输入端口号直接跳转登录界面
     * @Author: LiuJian
     * @Date: 2020/4/29
     */
    @RequestMapping("/")
    public String beforeLogin(){
        return "login";
    }

    /**
     * @Description: 管理员登录验证方法
     * @Author: LiuJian
     * @Date: 2020/4/4
     */
    @RequestMapping("/dl/yz")
    public String login(String type, String username, String password,HttpSession httpSession,Model model){
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken userToken=new UsernamePasswordToken(username,DigestUtils.md5Hex(password));
        String path = "";
        try{
            subject.login(userToken);
            Adminuser a= adminuserDao.findByAdminNameAndAdminPassword(username,DigestUtils.md5Hex(password));
            httpSession.setAttribute("user",a);

            // 登录成功后，将当前用户信息存入ThreadLocal中
            ThreadLocalHolder.setCurrentUser(a);
            if (type.equals("members")) {
                //会员
                if (!Objects.equals("2",a.getPermission())) {
                    model.addAttribute("msg","权限不足");
                    return "login";
                }
                path = "WEB-INF/jsp/members_index";
            } else if (type.equals("coaches")) {
                //教练  教练可以登陆管理员账号
                if (!Objects.equals("3",a.getPermission()) && !Objects.equals("1",a.getPermission())) {
                    model.addAttribute("msg","权限不足");
                    return "login";
                }
                path = "WEB-INF/jsp/coaches_index";
            } else if (type.equals("administrators")) {
                //管理员
                if (!Objects.equals("1",a.getPermission())) {
                    model.addAttribute("msg","权限不足");
                    return "login";
                }
                path = "WEB-INF/jsp/index";
            }
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名或密码错误,请重新输入");
            return "login";
        }

        return path;
        /*Adminuser a= adminuserDao.findByAdminNameAndAdminmima(username,password);
        if(a!=null){
            httpSession.setAttribute("user",a);
            return "WEB-INF/jsp/index" ;
        }
        model.addAttribute("mag","账号或密码错误");
        return "login";*/
    }

    /**
     * @Description: 退出登录后清楚session
     * @Author: LiuJian
     * @Date: 2020/5/1
     */
    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }

    /**
     * @Description: 跳转到修改密码界面
     * @Author: LiuJian
     * @Date: 2020/5/1
     */
    @RequestMapping("/updPassword")
    public String updPassword(){
        return "WEB-INF/jsp/updPassword";
    }

    @RequestMapping("/register")
    public String register(){
        return "WEB-INF/jsp/register";
    }


    /**
     * @Description: 修改密码
     * @Author: LiuJian
     * @Date: 2020/5/1
     */
    @RequestMapping("/upd/updPassword")
    public String updPasswordConfirm(String oldPassword,String newPassword,String newPasswordAgain,HttpSession httpSession,Model model){
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!.%*#?&])[A-Za-z\\d$@$!.%*#?&]{8,}$");
        Matcher m = p.matcher(newPassword);
        if(!m.matches()){
            model.addAttribute("msg","新密码最少为8位并为字母+数字+特殊字符");
            return "WEB-INF/jsp/updPassword";
        }
        if(!newPassword.equals(newPasswordAgain)){
            model.addAttribute("msg","两次输入新密码不一致,请重新输入");
            return "WEB-INF/jsp/updPassword";
        }
        Adminuser adminuser=(Adminuser) httpSession.getAttribute("user");
        if(null != adminuser){
            if(!adminuser.getAdminPassword().equals(DigestUtils.md5Hex(oldPassword))){
                model.addAttribute("msg","原密码不正确,请重新输入");
                return "WEB-INF/jsp/updPassword";
            }
            adminuserDao.updPassword(adminuser.getAdminId(), DigestUtils.md5Hex(newPassword));
        }
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
       return "redirect:/login.jsp";
    }

    @RequestMapping("/user/register")
    public String register(String username,String newPassword,String newPasswordAgain,String type,HttpSession httpSession,Model model){
        log.info("注册用户名:"+username+"密码:"+newPassword+"确认密码:"+newPasswordAgain+"权限:"+type);
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!.%*#?&])[A-Za-z\\d$@$!.%*#?&]{8,}$");
        Matcher m = p.matcher(newPassword);
        if(!m.matches()){
            model.addAttribute("msg","新密码最少为8位并为字母+数字+特殊字符");
            return "WEB-INF/jsp/register";
        }
        if(!newPassword.equals(newPasswordAgain)){
            model.addAttribute("msg","两次输入新密码不一致,请重新输入");
            return "WEB-INF/jsp/register";
        }
        adminuserDao.createUser(username, DigestUtils.md5Hex(newPassword),type);
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        log.info("注册成功");
        return "redirect:/login.jsp";
    }


}
