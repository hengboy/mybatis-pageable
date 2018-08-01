package com.gitee.hengboy.mybatis.pageable;

import java.util.List;

/**
 * 分页结果对象
 *
 * @author：于起宇
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
     * @return 分页数据
     */
    List<T> getData();

    /**
     * 获取总页数
     *
     * @return 总页数
     */
    long getTotalPages();

    /**
     * 获取总条数
     *
     * @return 总条数
     */
    long getTotalElements();

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
     * @return 开始位置
     */
    long getOffset();

    /**
     * 当前页的结束位置
     * @return 结束位置
     */
    long getEndRow();

    /**
     * 是否存在下一页
     *
     * @return 是否有下一页，true：存在，false：不存在
     */
    boolean hasNext();

    /**
     * 是否存在上一页
     *
     * @return 是否有上一页，true：存在，false：不存在
     */
    boolean hasPrevious();

    /**
     * 是否为首页
     *
     * @return 是否为首页，true：首页，false：非首页
     */
    boolean isFirst();

    /**
     * 是否为末页
     *
     * @return 是否为末页，true：末页，false：非末页
     */
    boolean isLast();
}
