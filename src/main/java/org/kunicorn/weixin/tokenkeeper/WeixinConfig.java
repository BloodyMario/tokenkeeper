package org.kunicorn.weixin.tokenkeeper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信相关配置，默认配在application.properties
 */
@Component
public class WeixinConfig {

    @Value("${weixin.grant_type}")
    public String grantType;

    @Value("${weixin.appid}")
    public String appId;

    @Value("${weixin.appsecret}")
    public String appSecret;
}
