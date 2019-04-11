package org.kunicorn.weixin.tokenkeeper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class TokenFactory {

    @Autowired
    private TokenRepository repository;

    /**
     * 根据微信返回结果构造Token对象
     * @param weixinToken
     * @return
     */
    public Token buildToken(WeixinRspToken weixinToken) {
        Token token = new Token();

        token.setAccessToken(weixinToken.getAccess_token());
        token.setExpiresIn(weixinToken.getExpires_in());

        Date nowDate = new Date();
        token.setAcquireTime(nowDate);

        Token current = repository.findCurrent();

        if (current == null) {
            token.setIndexOfToday(0);
        } else {
            Calendar calendarCurrentt = Calendar.getInstance();
            calendarCurrentt.setTime(current.getAcquireTime());

            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(token.getAcquireTime());

            // 没有跨天，index+1
            if (calendarCurrentt.get(Calendar.DAY_OF_YEAR) == calendarNow.get(Calendar.DAY_OF_YEAR)) {
                int indexLast = current.getIndexOfToday();
                token.setIndexOfToday(++indexLast);
            }
            // 新的一天
            else {
                token.setIndexOfToday(0);
            }
        }

        return token;

    }
}
