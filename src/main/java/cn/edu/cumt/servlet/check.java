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

@WebServlet(name = "check")
public class check extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usrname =request.getParameter("usrName");
        Cookie c =new Cookie("name",usrname);
        c.setMaxAge(10*60);
        response.addCookie(c);
        String psd =request.getParameter("Password");
        Cookie d =new Cookie("psd",psd);
        d.setMaxAge(10*60);
        response.addCookie(d);
        //Cookie相关操作

        request.getSession().setAttribute("access",0);
        userService us=new userService();
        us.setUserDao();
        User _user=new User();
        _user.setUser_name(request.getParameter("usrName"));
        if (!(us.getByUser_name(_user)==null | _user.getPwd()==null ) && _user.getPwd().equals(request.getParameter("Password"))) {
            request.getSession().setAttribute("access",_user.getUser_id());
            response.sendRedirect("index.html");
        } else {
            response.sendRedirect("error.jsp");

        }
        //密码验证相关操作
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
