package gaozhi.online.peoplety.im.checker;

import com.google.gson.Gson;
import gaozhi.online.base.interceptor.HeaderChecker;
import gaozhi.online.base.interceptor.HeaderPropertyChecker;
import gaozhi.online.base.result.Result;
import gaozhi.online.peoplety.entity.Token;
import gaozhi.online.peoplety.exception.UserException;
import gaozhi.online.peoplety.exception.enums.UserExceptionEnum;
import gaozhi.online.peoplety.im.service.UserService;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogDelegateFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 检查token
 */
@Component(HeaderChecker.accessToken)
public class TokenChecker implements HeaderPropertyChecker<Token> {
    private final Gson gson = new Gson();
    private final Log log = LogDelegateFactory.getHiddenLog(TokenChecker.class);

    @Autowired
    private UserService userService;

    @Override
    public Token check(String value, String url, String ip, HttpServletRequest request, HttpServletResponse response) {
        Result result = userService.checkToken(value, url, ip);
        if (result.getCode() != Result.SUCCESSResultEnum.SUCCESS.code()) {
            throw new UserException(UserExceptionEnum.USER_AUTH_ERROR);
        }
        request.setAttribute(HeaderChecker.rpcURLKey, url);
        request.setAttribute(HeaderChecker.rpcClientIp, ip);
        return gson.fromJson(value, Token.class);
    }

}
