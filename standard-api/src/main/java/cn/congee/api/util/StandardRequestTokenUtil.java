package cn.congee.api.util;

import cn.congee.api.module.system.login.domain.RequestTokenBO;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:06
 **/
public class StandardRequestTokenUtil {

    private static final String USER_KEY = "smart_admin_user";

    private static ThreadLocal<RequestTokenBO> RequestUserThreadLocal = new ThreadLocal<RequestTokenBO>();

    public static void setUser(HttpServletRequest request, RequestTokenBO requestToken) {
        request.setAttribute(USER_KEY, requestToken);
        RequestUserThreadLocal.set(requestToken);
    }

    public static RequestTokenBO getThreadLocalUser() {
        return RequestUserThreadLocal.get();
    }

    public static RequestTokenBO getRequestUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (request != null) {
                return (RequestTokenBO) request.getAttribute(USER_KEY);
            }
        }
        return null;
    }

    public static Long getRequestUserId() {
        RequestTokenBO requestUser = getRequestUser();
        if (null == requestUser) {
            return null;
        }
        return requestUser.getRequestUserId();
    }

}
