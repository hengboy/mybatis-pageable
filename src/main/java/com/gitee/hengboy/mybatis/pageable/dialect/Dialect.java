package com.gitee.hengboy.mybatis.pageable.dialect;

import com.gitee.hengboy.mybatis.pageable.Page;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 数据库方言接口定义
 * - 该接口定义不同数据库获取分页sql
 * - 定义不同数据库获取查询数据总条数的sql
 * <p>
 *
 * @author：于起宇 Created with IDEA.
 * Date：2018/7/29
 * Time：2:43 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * </p>
 */
public interface Dialect {
    /**
     * 获取分页sql
     *
     * @param boundSql boundSql 对象
     * @param page     分页响应对象实例
     * @return
     */
    String getPageSql(BoundSql boundSql, Page page);

    /**
     * 获取总条数sql
     *
     * @param boundSql boundSql 对象
     * @return
     */
    String getCountSql(BoundSql boundSql);

    /**
     * 将分页参数映射添加到BoundSql内的parameterMappings参数集合内
     * 并将分页参数添加到Parameter集合内
     *
     * @param parameter 分页查询时请求参数集合
     * @param page      分页响应对象实例
     * @return
     */
    Object getPageParameter(Object parameter, Page page);

    /**
     * 添加分页的参数映射
     *
     * @param pageBoundSql 分页的BoundSql实例
     * @param statement    MappedStatement对象实例
     * @param page         分页响应对象实例
     */
    void addPageMapping(BoundSql pageBoundSql, MappedStatement statement, Page page);
}
