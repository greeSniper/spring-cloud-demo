package com.tangzhe.cloud.demo.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义过滤器
 * Created by 唐哲
 * 2017-12-13 21:31
 */
@Component
public class TokenFilter extends ZuulFilter {

    /**
     * 有4种类型的过滤器
     * return "pre";
     * return "routing";
     * return "error";
     * return "post"
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 数字越小，过滤器优先级越高
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 返回true，该过滤器有效，执行run方法;
     * 返回false，该过滤器无效
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getParameter("token");
        //若token不存在则拦截
        if(token == null) {
            //拦截
            context.setSendZuulResponse(false);
            //401请求非法
            context.setResponseStatusCode(401);
            //返回给页面的信息
            context.setResponseBody("unauthrized");

            //过滤器中抛出异常，不能直接try catch，要这样
//            context.set("error.status_code");
//            context.set("error.exception", e);
//            context.set("error.message", "sasasa");
        }
        return null;
    }

}
