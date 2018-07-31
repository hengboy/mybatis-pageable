package com.gitee.hengboy.mybatis.pageable.common;

import com.gitee.hengboy.mybatis.pageable.executor.ExecutorQueryRequest;
import org.apache.ibatis.executor.Executor;

import java.sql.SQLException;
import java.util.List;

/**
 * mybatis执行器工具类
 * @author：于起宇 <br/>
 * ===============================
 * Created with IDEA.
 * Date：2018/7/30
 * Time：5:16 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public final class ExecutorHelper {
    /**
     *
     * @param executor
     * @param request
     * @param <E>
     * @return
     * @throws SQLException
     */
    public static <E> List<E> query(Executor executor, ExecutorQueryRequest request) throws SQLException {
        return executor.query(request.getStatement(),
                request.getParameter(),
                request.getRowBounds(),
                request.getResultHandler(),
                request.getCacheKey(),
                request.getBoundSql());
    }
}
