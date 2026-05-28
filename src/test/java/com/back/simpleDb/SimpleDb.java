package com.back.simpleDb;

import java.sql.*;

public class SimpleDb {
    private Connection connection;

    SimpleDb(String host, String dbUser, String dbPassword, String dbName) {
        try {
            String url = "jdbc:mysql://" + host + ":3306/" + dbName + "?serverTimezone=Asia/Seoul" + "&characterEncoding=UTF-8";
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            this.connection = connection;
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDevMode(boolean devMode) {
        if(devMode) {
            ;
        }
    }

    public void run(String sql)  {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(String sql, Object... params) {
        String result = sql;

        for (Object param : params) {
            String value;

            if (param instanceof String) {
                value = "'" + param + "'";
            } else {
                value = param.toString();
            }

            result = result.replaceFirst("\\?", value);
        }
        this.run(result);
    }

    public Sql genSql() {
        return new Sql(connection);
    }

    public void close() {
        try {
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void startTransaction() {}
    public void commit() {}
    public void rollback() {}

}
