package com.example.interview.test;

import java.sql.*;
/**
 * Created by Ay on 2017/9/23.
 */
public class StatementAndPreStatement {

    public static void main(String[] args) throws Exception {
        //1 加载数据库驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2 获取数据库连接
        String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8";
        String user = "root";
        String password = "root";
        Connection conn = DriverManager.getConnection(url, user, password);
        //3 创建一个Statement
        String sql = "select id,loginName,email from user where id=";
        String tempSql;
        int count = 1000;
        long time = System.currentTimeMillis();
        for(int i=0 ;i<count ;i++){
            Statement statement = conn.createStatement();
            tempSql=sql+(int) (Math.random() * 100);
            ResultSet rSet = statement.executeQuery(tempSql);
            statement.close();
        }
        System.out.println("statement cost:" + (System.currentTimeMillis() - time));

        String psql = "select id,loginName,email from user where id=?";
        time = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            int id=(int) (Math.random() * 100);
            PreparedStatement preStatement = conn.prepareStatement(psql);
            preStatement.setLong(1, new Long(id));
            ResultSet pSet = preStatement.executeQuery();
            preStatement.close();
        }
        System.out.println("preStatement cost:" + (System.currentTimeMillis() - time));
        conn.close();

        //git 回退版本的面试题

    }
}
