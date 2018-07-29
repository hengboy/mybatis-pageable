package com.gitee.hengboy.mybatis.pageable.dialect;

import org.apache.ibatis.mapping.BoundSql;

/**
 * 数据库方言接口定义
 * - 该接口定义不同数据库获取分页sql
 * - 定义不同数据库获取查询数据总条数的sql
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/29
 * Time：2:43 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public interface Dialect {
    /**
     * 获取分页sql
     *
     * @param boundSql boundSql 对象
     * @return
     */
    String getPageSql(BoundSql boundSql);

    /**
     * 获取总条数sql
     *
     * @param boundSql boundSql 对象
     * @return
     */
    String getCountSql(BoundSql boundSql);
}
