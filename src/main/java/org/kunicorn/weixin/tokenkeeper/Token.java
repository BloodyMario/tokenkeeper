package org.kunicorn.weixin.tokenkeeper;

import java.util.Date;

/**
 * 对Token的封装
 */
public class Token {

    private String accessToken;

    // 多少秒过期
    private int expiresIn;

    // 获取时间
    private Date acquireTime;

    // 今天第几次获取，接口单日调用上限2000
    private int indexOfToday;

    /**
     * 是否已过期
     * @return
     */
    public boolean isExpired() {
        Date now = new Date();
        return (now.getTime() - acquireTime.getTime()) >= expiresIn * 1000;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getAcquireTime() {
        return acquireTime;
    }

    public void setAcquireTime(Date acquireTime) {
        this.acquireTime = acquireTime;
    }

    public int getIndexOfToday() {
        return indexOfToday;
    }

    public void setIndexOfToday(int indexOfToday) {
        this.indexOfToday = indexOfToday;
    }
}
