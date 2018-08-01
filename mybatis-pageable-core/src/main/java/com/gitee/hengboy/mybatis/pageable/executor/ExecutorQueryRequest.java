package com.gitee.hengboy.mybatis.pageable.executor;

import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * mybatis内置执行器query方法所需的参数封装对象
 *
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/30
 * Time：5:10 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Getter
@Builder
public class ExecutorQueryRequest {
    private MappedStatement statement;
    private Object parameter;
    private RowBounds rowBounds;
    private ResultHandler resultHandler;
    private CacheKey cacheKey;
    private BoundSql boundSql;
}
