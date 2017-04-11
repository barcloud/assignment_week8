package cn.edu.cumt.servlet;

import cn.edu.cumt.entity.Cart;
import cn.edu.cumt.entity.Good;
import cn.edu.cumt.service.cartService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "cart")
@SuppressWarnings("unchecked")
public class cart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession();
        if (session.getAttribute("access").toString().equals("0")){
            response.sendRedirect("login");
        }
        else {
            cartService se=new cartService();
            se.setCartDao();
            Cart getCart=new Cart();
            getCart.setUser_id((Integer) session.getAttribute("access"));
            session.setAttribute("cart",se.getAll(getCart));//读取数据库中购物车数量
            //更新购物车
            Cart newCart=new Cart();
            Cart oldCart=new Cart();
            List<Cart> cart_list;
            cart_list= (List<Cart>) session.getAttribute("cart");
            List<Good> good_list;
            good_list=(List<Good>) session.getAttribute("good");
            for(Cart newList:cart_list){
                oldCart.setSuk(newList.getSuk());
                oldCart.setUser_id((Integer) session.getAttribute("access"));
                if (!(request.getParameter(String.valueOf(oldCart.getSuk())).equals(""))) {
                    newCart.setShopping_num(se.getBySuk(oldCart).getShopping_num() + Integer.parseInt(request.getParameter(String.valueOf(oldCart.getSuk()))));
                    se.updateCart(oldCart, newCart);
                }
            }
            //添加购物车里没有的数据到数据库

            for(Good goodList:good_list) {
                boolean i=true;
                Cart addCart = new Cart();
                addCart.setSuk(goodList.getSuk());
                    for (Cart cartList : cart_list) {
                        if (cartList.getSuk() == goodList.getSuk() || request.getParameter(String.valueOf(addCart.getSuk())).equals("0") ) {
                            i = false;
                        }
                    }

                if (i && !(request.getParameter(String.valueOf(addCart.getSuk())).equals(""))){
                    addCart.setUser_id((Integer) session.getAttribute("access"));
                    addCart.setShopping_num(Integer.parseInt(request.getParameter(String.valueOf(addCart.getSuk()))));
                    se.addCart(addCart);
                }

            }

            session.setAttribute("cart",se.getAll(getCart));//二次读取数据库中购物车数量
            RequestDispatcher rd = request.getRequestDispatcher("cart.jsp");
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
