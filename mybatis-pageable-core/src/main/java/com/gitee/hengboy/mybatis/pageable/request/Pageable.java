package com.gitee.hengboy.mybatis.pageable.request;

import com.gitee.hengboy.mybatis.pageable.LogicFunction;
import com.gitee.hengboy.mybatis.pageable.Page;

/**
 * 分页构造接口定义
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/7/28
 * Time：6:59 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public interface Pageable {
    /**
     * 当前页码
     *
     * @return 当前页码
     */
    int getPageIndex();

    /**
     * 每页条数
     *
     * @return 每页条数
     */
    int getPageSize();

    /**
     * 当前页开始位置
     *
     * @return 分页开始位置
     */
    long getOffset();

    /**
     * 当前页码结束位置
     *
     * @return 分页结束位置
     */
    long getEndRow();

    /**
     * 下一页
     *
     * @return 下一页分页请求对象实例
     */
    Pageable next();

    /**
     * 上一页
     *
     * @return 上一页分页请求对象实例
     */
    Pageable previous();

    /**
     * 第一页
     *
     * @return 首页分页请求对象实例
     */
    Pageable first();

    /**
     * 请求分页并且返回分页响应实体实例
     *
     * @param logicFunction 业务方法函数
     * @param <T>           泛型类型
     * @return 分页响应对象
     */
    public <T> Page<T> request(LogicFunction logicFunction);
}
