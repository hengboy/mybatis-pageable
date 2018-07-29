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
    public static final ThreadLocal<Page<?>> PAGEABLE_THREAD_LOCAL = new ThreadLocal<>();
}
