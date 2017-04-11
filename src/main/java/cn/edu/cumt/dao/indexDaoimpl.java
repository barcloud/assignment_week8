package cn.edu.cumt.dao;

import cn.edu.cumt.dbutil.DbUtil;
import cn.edu.cumt.entity.Good;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class indexDaoimpl implements indexDao {
    @Override
    public List<Good> getAll() {
        List<Good> newsList=new ArrayList<>();
        try{
            Connection conn=DbUtil.getConnection();
            ResultSet rs= DbUtil.executeQuery("select * from good", new Object[]{},conn);
            while(rs.next()){
                Good good=new Good();
                good.setGood_name(rs.getString(2));
                good.setSuk(rs.getInt(1));
                newsList.add(good);
            }
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return newsList;
    }
}
