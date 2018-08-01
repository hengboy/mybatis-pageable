package com.gitee.hengboy.mybatis.pageable.request;

import com.gitee.hengboy.mybatis.pageable.LogicFunction;
import com.gitee.hengboy.mybatis.pageable.Page;
import com.gitee.hengboy.mybatis.pageable.common.PageableRequestHelper;

/**
 * 分页请求对象
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/28
 * Time：7:03 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class PageableRequest extends AbstractPageRequest {
    /**
     * 构造函数初始化分页请求对象
     *
     * @param pageIndex 当前页码
     * @param pageSize  每页条数
     */
    private PageableRequest(int pageIndex, int pageSize) {
        super(pageIndex, pageSize);
    }

    /**
     * 对外提供的初始化分页请求方法
     *
     * @param pageIndex 当前页码
     * @param pageSize  每页条数
     * @return 获取分页请求对象实例
     */
    public static Pageable of(int pageIndex, int pageSize) {
        return new PageableRequest(pageIndex, pageSize);
    }

    /**
     * 执行请求分页方法
     *
     * @param logicFunction 业务逻辑查询方法
     * @param <T> 泛型参数
     * @return 执行分页请求
     */
    public <T> Page<T> request(LogicFunction logicFunction) {
        // 业务方法执行
        logicFunction.invoke();

        /*
         * 获取threadLocal分页请求对象
         * 注意：该行代码需要再执行完成业务方法后执行，原因是业务方法在执行时拦截器需要对threadLocal内的page对象作出修改
         */
        Page<T> page = (Page<T>) PageableRequestHelper.getPageLocal();

        // 删除threadLocal分页请求对象
        PageableRequestHelper.removePageLocal();
        return page;
    }

    /**
     * 获取下一页分页对象
     *
     * @return 获取下一页分页请求实例
     */
    public Pageable next() {
        return of(pageIndex + 1, pageSize);
    }

    /**
     * 获取上一页分页对象
     *
     * @return 获取上一页分页请求实例
     */
    public Pageable previous() {
        return pageIndex == 1 ? this : of(pageIndex - 1, pageSize);
    }

    /**
     * 获取首页分页对象
     *
     * @return 获取首页分页请求实例
     */
    public Pageable first() {
        return of(1, pageSize);
    }
}
