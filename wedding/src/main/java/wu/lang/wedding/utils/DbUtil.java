package wu.lang.wedding.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 一般不关闭连接
 */
@Slf4j
public class DbUtil {

    private static String url;
    private static String userName;
    private static String passWord;

    /**
     * 设置连接参数
     */
    public static void setConnectionParam(String urlStr, String userNameStr, String passwordStr) {
        url = urlStr;
        userName = userNameStr;
        passWord = passwordStr;
    }

    public static Connection connection;

    /**
     * 获取连接
     */
    public static Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, passWord);
            if (connection.isClosed()) {
                log.error("数据库连接失败关闭，url:{}", url);
                return null;
            }
            return connection;
        } catch (Exception e) {
            log.error("数据库创建失败：{}，url:{}", e.getMessage(), url);
            return null;
        }
    }

    public static void release(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行数据库更新
     * delete,update,insert语句
     */
    public static int update(String sql, Object... parameters) {
        try {
            Connection conn = getConnection();
            if (conn == null) {
                log.error("数据库连接失败,sql:{}", sql);
                return 0;
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
            return pstmt.executeUpdate();
        } catch (Exception e) {
            log.error("数据库更新失败【{}】,sql:【{}】" , e.getMessage(), sql);
        }

        return 0;
    }

    /**
     * 执行数据库查找
     * select语句
     */
    public static ResultSet select(String sql, Object... parameters) {
        try {
            Connection conn = getConnection();
            if (conn == null) {
                log.error("数据库连接失败,sql:{}", sql);
                return null;
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
            return pstmt.executeQuery();
        } catch (Exception e) {
            log.error("数据库更新失败,sql:{}", sql);
        }

        return null;
    }
}
