package com.gitee.hengboy.mybatis.pageable;

import java.util.List;

/**
 * 分页结果对象
 *
 * @author：于起宇 <br/>
 * ===============================
 * Created with IDEA.
 * Date：2018/7/28
 * Time：10:05 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public interface Page<T> {
    /**
     * 分页查询到的数据列表
     * 参与分页后的分页数据
     *
     * @return
     */
    List<T> getData();

    /**
     * 获取总页数
     *
     * @return
     */
    long getTotalPages();

    /**
     * 获取总条数
     *
     * @return
     */
    long getTotalElements();

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
     * 是否存在下一页
     *
     * @return
     */
    boolean hasNext();

    /**
     * 是否存在上一页
     *
     * @return
     */
    boolean hasPrevious();

    /**
     * 是否为首页
     *
     * @return
     */
    boolean isFirst();

    /**
     * 是否为末页
     *
     * @return
     */
    boolean isLast();
}
