package com.gitee.hengboy.mybatis.pageable.request;

import com.gitee.hengboy.mybatis.pageable.LogicFunction;
import com.gitee.hengboy.mybatis.pageable.Page;

/**
 * 分页构造接口定义
 *
 * @author：于起宇 <br/>
 * ===============================
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
     * @return
     */
    int getPageIndex();

    /**
     * 每页条数
     *
     * @return
     */
    int getPageSize();

    /**
     * 当前页开始位置
     *
     * @return
     */
    long getOffset();

    /**
     * 当前页码结束位置
     * @return
     */
    long getEndRow();

    /**
     * 下一页
     *
     * @return
     */
    Pageable next();

    /**
     * 上一页
     *
     * @return
     */
    Pageable previous();

    /**
     * 第一页
     *
     * @return
     */
    Pageable first();

    /**
     * 请求分页并且返回分页响应实体实例
     *
     * @param logicFunction 业务方法函数
     * @param <T>           泛型类型
     * @return
     */
    public <T> Page<T> request(LogicFunction logicFunction);
}
