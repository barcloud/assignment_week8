package cn.edu.cumt.controller;

import cn.edu.cumt.entity.Cart;
import cn.edu.cumt.entity.Good;
import cn.edu.cumt.entity.User;
import cn.edu.cumt.service.cartService;
import cn.edu.cumt.service.indexService;
import cn.edu.cumt.service.userService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller

public class mainController {
    @RequestMapping(value = "/{pages}")
    public ModelAndView pages(@PathVariable String pages,ModelAndView mv) {
        // 动态跳转页面
        mv.setViewName(pages);
        return mv;
    }
    private static int i=1;
    @RequestMapping(value="/changeStatus")
    public ModelAndView changeStatus(HttpServletRequest request, ModelAndView mv,RedirectAttributes redirectAttributes){
        User user = (User) request.getSession().getAttribute("user");
        // 2.判断用户是否已经登录
        if(user == null){
            mv.setViewName("redirect:cookieCheck");
        }else{
            request.getSession().setAttribute("user",null);
            user=null;
            redirectAttributes.addFlashAttribute("status","Login");
            mv.setViewName("redirect:index");
        }
        return mv;
    }
    @RequestMapping(value= "/cookieCheck")
    public ModelAndView check( ModelAndView mv, HttpServletRequest request, HttpSession session,RedirectAttributes redirectAttributes){
            //读取Cookie实现自动登陆
            userService us=new userService();
            us.setUserDao();
            User _user=new User();
            Cookie[] cookies =request.getCookies();
            redirectAttributes.addFlashAttribute("status","Login");
            mv.setViewName("login");
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
                            redirectAttributes.addFlashAttribute("status","Logout");
                            mv.setViewName("redirect:index");
                        }
                    }
                }
            }

            return mv;

    }
    @RequestMapping(value= "/loginCheck")
    public ModelAndView login(String usrName, String Password, ModelAndView mv,  HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes){


        userService us=new userService();
        us.setUserDao();
        User _user=new User();
        _user.setUser_name(usrName);
        if (!(us.getByUser_name(_user)==null | _user.getPwd()==null) && _user.getPwd().equals(Password)) {
            Cookie c =new Cookie("name",usrName);
            c.setMaxAge(10*60);
            response.addCookie(c);
            Cookie d =new Cookie("psd",Password);
            d.setMaxAge(10*60);
            response.addCookie(d);
            //Cookie相关操作
            User user = new User();
            user.setPwd(_user.getPwd());
            user.setUser_name(_user.getUser_name());
            user.setUser_id(_user.getUser_id());
            session.setAttribute("user",_user);
            redirectAttributes.addFlashAttribute("status","Logout");
            mv.setViewName("redirect:index");
        } else {
            mv.setViewName("error");

        }
        return mv;
    }
    @RequestMapping(value= "/indexRD")
    public ModelAndView indexRD(RedirectAttributes redirectAttributes){
        //redirectAttributes.addFlashAttribute("status","Logout");
        return new ModelAndView("redirect:index");
    }
    @RequestMapping(value= "/index")
    public ModelAndView index(ModelAndView mv, HttpSession session){
        ServletContext application=session.getServletContext();
        System.out.println("index 初始化");
        indexService is=new indexService();
        is.setIndexDao();
        session.setAttribute("good",is.getAll());
        application.setAttribute("counter",String.valueOf(i++));
        mv.setViewName("index");
        return mv;
    }
    @RequestMapping(value= "/cart")
    public ModelAndView cart(ModelAndView mv, HttpSession session,HttpServletRequest request){
        cartService se=new cartService();
        se.setCartDao();
        Cart getCart=new Cart();
        User user = (User) session.getAttribute("user");
        getCart.setUser_id(user.getUser_id());
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
            oldCart.setUser_id(user.getUser_id());
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
                addCart.setUser_id(user.getUser_id());
                addCart.setShopping_num(Integer.parseInt(request.getParameter(String.valueOf(addCart.getSuk()))));
                se.addCart(addCart);
            }

        }

        session.setAttribute("cart",se.getAll(getCart));//二次读取数据库中购物车数量
        mv.setViewName("cart");
        return mv;
    }
    @RequestMapping(value = "/del")
    public ModelAndView del(HttpSession session,HttpServletRequest request){
        cartService se=new cartService();
        se.setCartDao();
        Cart delCart=new Cart();
        User user = (User) session.getAttribute("user");
        delCart.setUser_id(user.getUser_id());
        delCart.setSuk(Integer.parseInt(request.getParameter("suk")));
        session.setAttribute("cart",se.getAll(delCart));//读取数据库中购物车数量
        se.deleteCart(delCart);
        session.setAttribute("cart",se.getAll(delCart));//读取数据库中购物车数量
        return new ModelAndView("cart");
    }
}