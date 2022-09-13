package gaozhi.online.peoplety.im.service;

import gaozhi.online.base.interceptor.HeaderChecker;
import gaozhi.online.base.result.Result;
import gaozhi.online.peoplety.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * @author http://gaozhi.online
 * @version 1.0
 * @description: TODO
 * @date 2022/9/13 16:03
 */
@Service
public class UserService {
    private static final String CHECK_AUTH_URL = "http://APP-PEOPLETY-USER/general/user/post/check_auth";
    private static final String POST_MSG_URL = "http://APP-PEOPLETY-USER/general/user/post/message";
    @Autowired
    private RestTemplate restTemplate;

    public Result checkAuth(String token, String url, String ip) {
        //设置请求头参数
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HeaderChecker.accessToken, token);
        httpHeaders.add(HeaderChecker.rpcURLKey, url);
        httpHeaders.add(HeaderChecker.rpcClientIp, ip);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.postForObject(CHECK_AUTH_URL, httpEntity, Result.class);
    }

    public Result postMessage(String token, String url, String ip, Message body) {
        //设置请求头参数
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HeaderChecker.accessToken, token);
        httpHeaders.add(HeaderChecker.rpcURLKey, url);
        httpHeaders.add(HeaderChecker.rpcClientIp, ip);
        HttpEntity<Message> httpEntity = new HttpEntity<>(body, httpHeaders);
        return restTemplate.postForObject(POST_MSG_URL, httpEntity, Result.class);
    }
}
