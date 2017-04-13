package cn.edu.cumt.servlet;

import cn.edu.cumt.service.indexService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "index")
public class index extends HttpServlet {
    private String status;
    private static int i=1;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("index Servlet正常工作");
        ServletContext application = request.getServletContext();
        HttpSession session =request.getSession();
        indexService is=new indexService();
        is.setIndexDao();
        session.setAttribute("good",is.getAll());
        application.setAttribute("counter",String.valueOf(i++));
        if(!(session.getAttribute("access").equals(0))){
            status="Log Out";
        }else{
            status="Log IN";
        }
        session.setAttribute("status",status);
        session.setAttribute("count","start");
        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }
}
