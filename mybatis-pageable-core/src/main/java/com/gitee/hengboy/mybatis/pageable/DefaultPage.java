package com.gitee.hengboy.mybatis.pageable;

import com.gitee.hengboy.mybatis.pageable.request.Pageable;
import lombok.Builder;

import java.util.List;

/**
 * 默认的分页数据对象
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/28
 * Time：10:52 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Builder
public class DefaultPage<T> implements Page<T> {
    /**
     * 分页后的数据列表
     */
    private List<T> data;
    /**
     * 分页请求对象
     */
    private Pageable pageable;
    /**
     * 总条数
     */
    private long totalElements;

    /**
     * 设置分页数据
     *
     * @param data 分页数据内容
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * 设置总条数
     *
     * @param totalElements 数据总数
     */
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * 获取分页后的数据列表
     *
     * @return 分页数据列表
     */
    public List<T> getData() {
        return this.data;
    }

    /**
     * 获取总页数
     *
     * @return 查询总页数
     */
    public long getTotalPages() {
        // 总页数
        long totalPage = getTotalElements() / getPageSize();
        // 如果取余不为0时，+1页
        return getTotalElements() % getPageSize() == 0 ? totalPage : totalPage + 1;
    }

    /**
     * 获取总条数
     *
     * @return 查询总条数
     */
    public long getTotalElements() {
        return this.totalElements;
    }

    /**
     * 获取当前分页页码
     *
     * @return 当前页码
     */
    public int getPageIndex() {
        return this.pageable.getPageIndex();
    }

    /**
     * 获取每页条数
     *
     * @return 每页条数
     */
    public int getPageSize() {
        return this.pageable.getPageSize();
    }

    /**
     * 获取开始位置
     *
     * @return 分页开始位置
     */
    public long getOffset() {
        return this.pageable.getOffset();
    }

    /**
     * 获取当前页结束位置
     * @return 分页结束位置
     */
    public long getEndRow() {
        return this.pageable.getEndRow();
    }

    /**
     * 是否存在上一页
     *
     * @return true：存在上一页，false：不存在
     */
    public boolean hasPrevious() {
        return this.pageable.getPageIndex() > 1;
    }

    /**
     * 是否为首页
     *
     * @return true：首页，false：非首页
     */
    public boolean isFirst() {
        return this.pageable.getPageIndex() == 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true：存在，false：不存在
     */
    public boolean hasNext() {
        return getTotalPages() > getPageIndex();
    }

    /**
     * 是否为末页
     *
     * @return true：为末页，false：非末页
     */
    public boolean isLast() {
        return getTotalPages() == getPageIndex();
    }
}
