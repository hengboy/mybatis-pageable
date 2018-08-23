package com.gitee.hengboy.mybatis.pageable.dialect;

import com.gitee.hengboy.mybatis.pageable.common.enums.DialectEnum;
import com.gitee.hengboy.mybatis.pageable.common.exception.ErrorMsgEnum;
import com.gitee.hengboy.mybatis.pageable.common.exception.PageableException;
import org.apache.ibatis.mapping.MappedStatement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 方言动态工厂
 * <p>
 * ================================
 *
 * @author：于起宇 Created with IDEA.
 * Date：2018/7/29
 * Time：3:28 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 * </p>
 */
public class DialectDynamicFactory {

    /**
     * 数据库连接字符串与数据库方言映射集合
     * key=>数据库连接字符串开头的内容，如：jdbc:mysql:xx，内容则为：jdbc:mysql
     * value=>DialectEnum数据库方言枚举
     */
    private static final Map<String, DialectEnum> URL_DIALECT_MAPPING = new HashMap();

    /**
     * 构造函数私有化
     * 仅通过newInstance方法获取方言实例
     */
    private DialectDynamicFactory() {
    }

    /**
     * 静态代码块初始化链接字符串与数据库方言枚举关系
     */
    static {

        // Use MySQL Dialect
        URL_DIALECT_MAPPING.put("jdbc:mysql", DialectEnum.MYSQL);
        URL_DIALECT_MAPPING.put("jdbc:sqlite", DialectEnum.MYSQL);
        URL_DIALECT_MAPPING.put("jdbc:mariadb", DialectEnum.MYSQL);

        // Use Oracle Dialect
        URL_DIALECT_MAPPING.put("jdbc:oracle", DialectEnum.ORACLE);
        URL_DIALECT_MAPPING.put("jdbc:dm", DialectEnum.ORACLE);

        URL_DIALECT_MAPPING.put("jdbc:db2", DialectEnum.DB2);
        URL_DIALECT_MAPPING.put("jdbc:postgresql", DialectEnum.POSTGRES);

        // Use SqlServer Dialect
        URL_DIALECT_MAPPING.put("jdbc:sqlserver", DialectEnum.SQLSERVER);
        URL_DIALECT_MAPPING.put("jdbc:derby", DialectEnum.SQLSERVER);

        // Use HSQL Dialect
        URL_DIALECT_MAPPING.put("jdbc:hsqldb:hsql", DialectEnum.HSQL);
        URL_DIALECT_MAPPING.put("jdbc:h2", DialectEnum.HSQL);

        // Use Informix Dialect
        URL_DIALECT_MAPPING.put("jdbc:informix-sqli", DialectEnum.INfORMIX);
        URL_DIALECT_MAPPING.put("jdbc:informix", DialectEnum.INfORMIX);
    }

    /**
     * 动态获取方言实例
     *
     * @param dialect   方言实现类
     * @param statement mappedStatement对象
     * @return 获取数据库方言
     */
    public static Dialect newInstance(MappedStatement statement, String dialect) {
        try {
            // 如果没有传递枚举参数
            // 则自动根据数据库
            if (dialect == null) {
                return newAutoInstance(statement);
            }
            // 反射获取方言实现实例
            return (Dialect) Class.forName(dialect).newInstance();
        }
        // 遇到异常后抛出方言暂未支持异常
        catch (Exception e) {
            throw new PageableException(ErrorMsgEnum.DIALECT_NOT_FOUND);
        }
    }

    /**
     * 根据驱动类型自动获取数据库类型
     *
     * @param statement MappedStatement对象实例
     * @return 数据库方言实例
     */
    public static Dialect newAutoInstance(MappedStatement statement) {
        try {
            // 获取数据源
            DataSource dataSource = statement.getConfiguration().getEnvironment().getDataSource();
            // 获取数据源
            Connection connection = dataSource.getConnection();
            // 获取数据源数据连接的字符串
            String jdbcUrl = connection.getMetaData().getURL();
            // 关闭数据源
            connection.close();
            // 获取对应的数据库枚举
            DialectEnum dialectEnum = loopGetDialect(jdbcUrl);
            return dialectEnum.getValue().newInstance();
        } catch (Exception e) {
            throw new PageableException(ErrorMsgEnum.DIALECT_NOT_FOUND);
        }
    }

    /**
     * 从连接字符串前缀集合内进行匹配
     *
     * @param jdbcUrl 数据库连接字符串
     * @return 获取数据库方言枚举
     */
    static DialectEnum loopGetDialect(String jdbcUrl) {
        Iterator<String> iterator = URL_DIALECT_MAPPING.keySet().iterator();
        while (iterator.hasNext()) {
            // 配置的指定数据库前缀字符串
            String urlPrefix = iterator.next();
            if (jdbcUrl.indexOf(urlPrefix) != -1) {
                return URL_DIALECT_MAPPING.get(urlPrefix);
            }
        }
        return null;
    }
}
