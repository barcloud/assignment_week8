package cn.edu.cumt.servlet;

import cn.edu.cumt.entity.User;
import cn.edu.cumt.service.userService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//读取cookies，实现自动登陆
@WebServlet(name = "login")
public class login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!(request.getSession().getAttribute("access").equals(0))){
            request.getSession().setAttribute("access",0);
            response.sendRedirect("/index.html");
        }
        else{
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
                            request.getSession().setAttribute("access", _user.getUser_id());
                            response.sendRedirect("index.html");
                        }

                    }
                }
            }

            if (request.getSession().getAttribute("access").equals(0)){
                response.sendRedirect("login.jsp");
            }

        }

    }
}
