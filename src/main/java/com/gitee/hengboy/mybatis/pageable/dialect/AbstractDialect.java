package com.gitee.hengboy.mybatis.pageable.dialect;

import org.apache.ibatis.mapping.BoundSql;

/**
 * 数据库方言抽象实现类
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/29
 * Time：3:17 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public abstract class AbstractDialect implements Dialect {
    /**
     * 查询总数sql前缀
     */
    private static final String COUNT_SQL_PREFIX = "SELECT COUNT(0) FROM ( ";
    /**
     * 查询总数sql后缀
     */
    private static final String COUNT_SQL_SUFFIX = " ) TEMP_COUNT";

    /**
     * 获取查询总条数sql
     *
     * @param boundSql boundSql 对象
     * @return 查询总条数count sql
     */
    @Override
    public String getCountSql(BoundSql boundSql) {
        StringBuffer sql = new StringBuffer();
        sql.append(COUNT_SQL_PREFIX);
        sql.append(boundSql.getSql());
        sql.append(COUNT_SQL_SUFFIX);
        return sql.toString();
    }
}
