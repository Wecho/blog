package com.wecho.core.jdbc.template;

import com.wecho.core.reflcet.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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

    public <T> T selectByPrimaryKey(Class<T> clazz, String tableNameStr) throws SQLException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String selectStr = "select * from %s";
        selectStr = String.format(selectStr, tableNameStr);

        preparedStatement = connection.prepareStatement(selectStr);

        resultSet = preparedStatement.executeQuery();
        T con = clazz.newInstance();
        while (resultSet.next()) {
            con = ReflectUtil.generator(clazz, mappingMap(resultSet, con));
        }
        return con;
    }

    private Map<String, Object> mappingMap(ResultSet resultSet, Object obj) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            map.put(field.getName(), resultSet.getObject(formatFieldToSqlStyle(field.getName())));
        }
        return map;
    }

    private String formatFieldToSqlStyle(String field) {
        int length = field.length();
        for (int i = 0; i < length; i++) {
            Character c = field.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                field = field.replaceFirst(c.toString(), "_" + c);
                length ++;
                i += 1;
            }
        }
        return  field;
    }
}
