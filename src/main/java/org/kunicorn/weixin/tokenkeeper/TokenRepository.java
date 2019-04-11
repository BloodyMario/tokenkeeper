package org.kunicorn.weixin.tokenkeeper;

/**
 * 存储Token
 */
public interface TokenRepository {

    /**
     * 保存
     * @param token
     */
    void save(Token token);

    /**
     * 获取当前保存的
     * @return
     */
    Token findCurrent();

    /**
     * 获取上一个保存的
     * @return
     */
    Token findLast();
}
