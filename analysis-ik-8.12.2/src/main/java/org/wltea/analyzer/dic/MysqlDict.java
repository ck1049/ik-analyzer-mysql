package org.wltea.analyzer.dic;

import java.sql.*;
import java.util.Properties;

/**
 * 词库来源MySQL
 * @author loafer
 * @since 2024-05-04 16:49:34
 **/
public class MysqlDict {

    private Properties prop;

    public MysqlDict(Properties prop) throws ClassNotFoundException {
        this.prop = prop;
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    /**
     * 获取数据库连接
     * @return
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                prop.getProperty("jdbc_url"),
                prop.getProperty("jdbc_username"),
                prop.getProperty("jdbc_password"));
    }

    /**
     * 加载主词库
     * @param dict
     */
    public void loadRemoteDict(DictSegment dict) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(prop.getProperty("jdbc_main_sql"));
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            dict.fillSegment(resultSet.getString(1).toCharArray());
        }
        closeResource(resultSet, stmt, conn);
    }

    /**
     * 加载停用词库
     */
    public void loadRemoteStopDict(DictSegment dict) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(prop.getProperty("jdbc_stop_sql"));
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            dict.fillSegment(resultSet.getString(1).toCharArray());
        }
        closeResource(resultSet, stmt, conn);
    }

    /**
     * 关闭资源
     * @param resultSet
     * @param stmt
     * @param conn
     * @throws SQLException
     */
    private void closeResource(ResultSet resultSet, PreparedStatement stmt, Connection conn) throws SQLException {
        resultSet.close();
        stmt.close();
        conn.close();
    }

}
