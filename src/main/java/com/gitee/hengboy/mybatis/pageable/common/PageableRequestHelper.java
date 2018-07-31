package com.gitee.hengboy.mybatis.pageable.common;

import com.gitee.hengboy.mybatis.pageable.Page;

/**
 * 分页请求工具类
 *
 * @author：于起宇 <br/>
 * ===============================
 * Created with IDEA.
 * Date：2018/7/28
 * Time：11:27 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class PageableRequestHelper {
    /**
     * 分页请求对象多线程threadLocal
     */
    private static final ThreadLocal<Page<?>> PAGEABLE_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取threadLocal内存放的page分页响应对象
     *
     * @return page 分页响应对应
     */
    public static Page<?> getPageLocal() {
        return PAGEABLE_THREAD_LOCAL.get();
    }

    /**
     * 删除threadLocal内存放的page分页响应对象
     */
    public static void removePageLocal() {
        PAGEABLE_THREAD_LOCAL.remove();
    }

    /**
     * 设置threadLocal内存放的page分页响应对象
     *
     * @param page
     */
    public static void setPageLocal(Page<?> page) {
        PAGEABLE_THREAD_LOCAL.set(page);
    }

}
