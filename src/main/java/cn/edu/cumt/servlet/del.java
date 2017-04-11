package cn.edu.cumt.servlet;

import cn.edu.cumt.entity.Cart;
import cn.edu.cumt.service.cartService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by fc751 on 2017/4/1.
 */
@WebServlet(name = "del")
public class del extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession();
        cartService se=new cartService();
        se.setCartDao();
        Cart delCart=new Cart();
        delCart.setUser_id((Integer) session.getAttribute("access"));
        delCart.setSuk(Integer.parseInt(request.getParameter("suk")));
        session.setAttribute("cart",se.getAll(delCart));//读取数据库中购物车数量
        se.deleteCart(delCart);
        session.setAttribute("cart",se.getAll(delCart));//读取数据库中购物车数量
        RequestDispatcher rd = request.getRequestDispatcher("cart.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
