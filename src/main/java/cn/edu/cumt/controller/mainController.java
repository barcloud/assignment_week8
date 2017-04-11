package cn.edu.cumt.controller;

import cn.edu.cumt.entity.User;
import cn.edu.cumt.service.userService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import sun.security.util.Password;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class mainController {
    @RequestMapping(value = "/{pages}")
    public String pages(@PathVariable String pages) {
        // 动态跳转页面
        return pages;
    }

    @RequestMapping(value="/check")
    public ModelAndView check(String usrName, String Password, ModelAndView mv, HttpServletRequest request, HttpServletResponse response, HttpSession session){
            //读取Cookie实现自动登陆
            userService us=new userService();
            us.setUserDao();
            User _user=new User();
            Cookie[] cookies =request.getCookies();

            for(Cookie c : cookies){
                if (c.getName().equals("name")){
                    _user.setUser_name(c.getValue());
                    for(Cookie d : cookies) {
                        if (d.getName().equals("psd") && !(us.getByUser_name(_user)==null | _user.getPwd()==null ) && _user.getPwd().equals(d.getValue())){
                            User user = new User();
                            user.setUser_name(_user.getUser_name());
                            user.setPwd(_user.getPwd());
                            user.setUser_id(_user.getUser_id());
                            session.setAttribute("user",_user);
                            mv.setViewName("redirect:index");
                        }
                    }
                }
            }
            if (request.getSession().getAttribute("user")==null){
            mv.setViewName("login");
            }
            return mv;

    }
    @RequestMapping(value="/login")
    public ModelAndView login(String usrName, String Password, ModelAndView mv, HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributesModelMap addmap){
        Cookie c =new Cookie("name",usrName);
        c.setMaxAge(10*60);
        response.addCookie(c);
        Cookie d =new Cookie("psd",Password);
        d.setMaxAge(10*60);
        response.addCookie(d);
        //Cookie相关操作

        userService us=new userService();
        us.setUserDao();
        User _user=new User();
        _user.setUser_name(usrName);
        if (!(us.getByUser_name(_user)==null | _user.getPwd()==null) && _user.getPwd().equals(Password)) {
            User user = new User();
            user.setPwd(_user.getPwd());
            user.setUser_name(_user.getUser_name());
            user.setUser_id(_user.getUser_id());
            session.setAttribute("user",_user);
            mv.setViewName("redirect:index");
        } else {
            mv.addObject("message", "登录名或密码错误，请重新输入!");
            mv.setViewName("login");

        }
        return mv;
    }
//        if(usrName != null && usrName.equals("fkit")
//                && Password!= null && Password.equals("123456")){
//            // 模拟创建用户
//            User user = new User();
//            user.setUser_name(usrName);
//            user.setPwd(Password);
//            user.setUser_id("");
//            // 登录成功，将user对象设置到HttpSession作用范围域
//            session.setAttribute("user", user);
//            // 转发到main请求
//            mv.setViewName("redirect:main");
//        }else{
//            // 登录失败，设置失败提示信息，并跳转到登录页面
//            mv.addObject("message", "登录名或密码错误，请重新输入!");
//            mv.setViewName("loginForm");
//        }
//        return mv;
//    }
}