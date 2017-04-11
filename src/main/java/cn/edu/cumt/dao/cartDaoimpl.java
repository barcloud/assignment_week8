package cn.edu.cumt.dao;

import cn.edu.cumt.dbutil.DbUtil;
import cn.edu.cumt.entity.Cart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class cartDaoimpl implements CartDao {


    @Override
    public List<Cart> getAll(Cart cart) {
        List<Cart> newsList=new ArrayList<>();
        try{
            Connection conn=DbUtil.getConnection();
            ResultSet rs= DbUtil.executeQuery("select good_name,shopping_num,cart.suk,cart.user_id from cart,user,good where user.user_id=? AND cart.suk=good.suk AND cart.user_id=user.user_id AND cart.user_id=?", new Object[]{cart.getUser_id(),cart.getUser_id()},conn);
            while(rs.next()){
                Cart list_cart=new Cart();
                list_cart.setGood_name(rs.getString(1));
                list_cart.setShopping_num(rs.getInt(2));
                list_cart.setSuk(rs.getInt(3));
                list_cart.setUser_id(rs.getInt(4));
                newsList.add(list_cart);
            }
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return newsList;

    }


    @Override
    public boolean addCart(Cart cart) {
        return DbUtil.executeUpdate("insert into cart values(?,?,?)", new Object[]{cart.getSuk(),cart.getUser_id(),cart.getShopping_num()});
    }

    @Override
    public boolean updateCart(Cart oldCart,Cart newCart) {
        return DbUtil.executeUpdate("update cart set shopping_num=? where user_id=? AND suk=?",new Object[]{newCart.getShopping_num(),oldCart.getUser_id(),oldCart.getSuk()});
    }

    @Override
    public Cart getBySuk(Cart cart) {
        try{
            Connection conn=DbUtil.getConnection();
            ResultSet rs= DbUtil.executeQuery("select * from cart where suk=? AND  user_id=?", new Object[]{cart.getSuk(),cart.getUser_id()},conn);
            while(rs.next()){
                cart.setSuk(rs.getInt(1));
                //cart.setUser_id (rs.getInt(2));
                cart.setShopping_num(rs.getInt(3));
            }
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return cart;
    }

    @Override
    public boolean deleteCart(Cart cart) {
        return DbUtil.executeUpdate("delete from cart where suk=? And user_id=?", new Object[]{cart.getSuk(),cart.getUser_id()});
    }
}
