package org.kunicorn.weixin.tokenkeeper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 向业务方提供Token获取手段
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenRepository repository;

    @Autowired
    private TokenRefresher refresher;

    /**
     * 获取当前的Token
     *
     * @return
     */
    @RequestMapping("/get")
    public Token getAccessToken() {
        return repository.findCurrent();
    }

    /**
     * 立即从微信获取最新Token并返回
     *
     * @return
     */
    @RequestMapping("/refresh")
    public Token refreshToken() {
        refresher.run();
        return repository.findCurrent();
    }

}
