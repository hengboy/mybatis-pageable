package com.gitee.hengboy.mybatis.pageable.common.exception;

/**
 * 分页异常定义
 *
 * @author：于起宇 <br/>
 * ===============================
 * Created with IDEA.
 * Date：2018/7/28
 * Time：11:02 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public class PageableException extends RuntimeException {

    /**
     * 格式化错误信息
     *
     * @param errorMsgEnum 错误消息枚举
     * @param params       参数列表
     */
    public PageableException(ErrorMsgEnum errorMsgEnum, String... params) {
        super(String.format(errorMsgEnum.getMessage(), params));
    }
}
