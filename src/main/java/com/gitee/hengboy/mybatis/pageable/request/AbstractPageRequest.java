package com.gitee.hengboy.mybatis.pageable.request;

import com.gitee.hengboy.mybatis.pageable.DefaultPage;
import com.gitee.hengboy.mybatis.pageable.Page;
import com.gitee.hengboy.mybatis.pageable.common.PageableRequestHelper;
import com.gitee.hengboy.mybatis.pageable.common.exception.ErrorMsgEnum;
import com.gitee.hengboy.mybatis.pageable.common.exception.PageableException;

/**
 * 分页请求对象抽象类
 *
 * @author：于起宇 <br/>
 * ===============================
 * Created with IDEA.
 * Date：2018/7/28
 * Time：10:28 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public abstract class AbstractPageRequest implements Pageable {
    /**
     * 抽象分页请求对象构造函数
     *
     * @param pageIndex 当前分页页码
     * @param pageSize  当前分页每页条数
     */
    public AbstractPageRequest(int pageIndex, int pageSize) {
        if (pageIndex < 1) {
            throw new PageableException(ErrorMsgEnum.PAGE_INDEX_FAILED);
        } else if (pageSize < 1) {
            throw new PageableException(ErrorMsgEnum.PAGE_SIZE_FAILED);
        }
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;

        // 设置分页响应threadLocal
        setPageThreadLocal();
    }

    /**
     * 设置分页响应对应到threadLocal
     */
    private void setPageThreadLocal() {
        // 构造分页响应对象
        Page page = DefaultPage.builder().pageable(this).build();
        // 写入到threadLocal
        PageableRequestHelper.PAGEABLE_THREAD_LOCAL.set(page);
    }

    /**
     * 分页当前页码
     */
    protected int pageIndex;
    /**
     * 分页每页条数
     */
    protected int pageSize;

    /**
     * 获取当前分页页码
     *
     * @return
     */
    @Override
    public int getPageIndex() {
        return this.pageIndex;
    }

    /**
     * 获取当前分页每页条数
     *
     * @return
     */
    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * 获取分页开始位置
     *
     * @return
     */
    @Override
    public long getOffset() {
        return (long) (this.pageIndex - 1) * (long) this.pageSize;
    }

}
