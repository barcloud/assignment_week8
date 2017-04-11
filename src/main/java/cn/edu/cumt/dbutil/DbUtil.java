package cn.edu.cumt.dbutil;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {


    private static DataSource dataSource=null;

	static {
		try{
            ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring/beans.xml");
            //获取dataSource对象
            dataSource=applicationContext.getBean("dataSource",DataSource.class);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static Connection getConnection(){
		Connection connection=null;
		try {
			connection=dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	public static boolean executeUpdate(String sql,Object[] args){
        Connection conn=getConnection();
		PreparedStatement pst;
		int rowsCount=0;
		try{
			pst=conn.prepareStatement(sql);
			if(args!=null&args.length>0){
				for(int i=0;i<args.length;i++){
					pst.setObject(i+1, args[i]);
				}
			}
			rowsCount=pst.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsCount>0;
	}
	public static ResultSet executeQuery(String sql,Object[] args,Connection conn){
		PreparedStatement pst=null;
		ResultSet rs=null;
		try{
			pst=conn.prepareStatement(sql);
			if(args!=null&args.length>0){
				for(int i=0;i<args.length;i++){
					pst.setObject(i+1, args[i]);
				}
			}
			rs=pst.executeQuery();
		}catch(SQLException e){
			e.printStackTrace();
		}

		return rs;






	}
}
