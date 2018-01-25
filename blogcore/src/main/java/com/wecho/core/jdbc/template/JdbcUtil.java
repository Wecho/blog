package com.wecho.core.jdbc.template;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class JdbcUtil {
    private static Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private static String DB_URL = "jdbc:mysql://106.14.138.164:3306/blog";
    private static String USER = "root";
    private static String PASSWORD = "root";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public <T> T selectByPrimaryKey(Class<T> object, String tableNameStr) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String selectStr = "select * from %s";
        selectStr = String.format(selectStr, tableNameStr);

        preparedStatement = connection.prepareStatement(selectStr);

        resultSet = preparedStatement.executeQuery();

        Constructor con = object.getConstructor();
        Object objectInstance = con.newInstance();
        while(resultSet.next()){
            int i = 1;
            Field fields[] = object.getFields();
            for (int fieldIndex = 0; fieldIndex < fields.length; fieldIndex++) {
                Field field = fields[fieldIndex];
                field.set(objectInstance,resultSet.getObject(i++));
            }
        }

        return (T) objectInstance;
    }
}
