package org.kunicorn.weixin.tokenkeeper;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * 从微信获取Token的实现类，目前是每小时跑一次
 */
@EnableScheduling
@Component
@Slf4j
public class TokenRefresher {

    @Autowired
    private WeixinConfig config;

    @Autowired
    private TokenRepository repository;

    @Autowired
    private TokenFactory factory;

    private String refreshUrl;

    @PostConstruct
    public void initURL() {
        String refreshUrlTemplate =
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=GRANTTYPE&appid=APPID&secret=APPSECRET";

        // 根据配置初始化token刷新地址
        refreshUrl = refreshUrlTemplate.replace("GRANTTYPE", config.grantType)
                .replace("APPID", config.appId).replace("APPSECRET", config.appSecret);

        log.info("获取AccessToken的URL：" + refreshUrl);
    }

    @Scheduled(fixedRate = 3600000)
    public void run() {

        // 向微信服务器获取AccessToken
        RestTemplate restTemplate = new RestTemplate();
        String strRespBody = restTemplate.getForObject(refreshUrl, String.class);
        log.info("微信返回：" + strRespBody);

        // 正常返回
        if (strRespBody.indexOf("access_token") != -1) {
            WeixinRspToken rspToken = JSON.parseObject(strRespBody, WeixinRspToken.class);

            // 构造新的token
            Token tokenNew = factory.buildToken(rspToken);

            // 存下来
            repository.save(tokenNew);

            log.info("刷新Token成功" + JSON.toJSONString(tokenNew));
        }
        // 异常返回
        else if (strRespBody.indexOf("errcode") != -1) {
            WeixinRspErr rspErr = JSON.parseObject(strRespBody, WeixinRspErr.class);

            // 记日志
            log.warn("刷新Token失败" + JSON.toJSONString(rspErr));
        }
    }
}
