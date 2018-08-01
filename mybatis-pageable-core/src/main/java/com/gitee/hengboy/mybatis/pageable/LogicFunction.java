package com.gitee.hengboy.mybatis.pageable;

/**
 * 业务逻辑执行方法接口定义
 * 提供给PageRequest作为参数使用
 * @author：于起宇
 * ===============================
 * Created with IDEA.
 * Date：2018/7/28
 * Time：11:49 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public interface LogicFunction {
    /**
     * 查询逻辑执行方法
     */
    void invoke();
}
