package com.hanay.crowd.filter;

import com.hsnay.crowd.entity.vo.MemberLoginVO;
import com.hsnay.crowd.util.AccessPassResources;
import com.hsnay.crowd.util.CrowdConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CrowdAccessFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String servletPath = request.getServletPath();
        boolean isPassContains = AccessPassResources.PASS_RES_SET.contains(servletPath);
        if (isPassContains) {
            return false;
        }
        boolean isStaticResource = AccessPassResources.judgeIsStaticResource(servletPath);
        // 是静态资源则工具方法返回true，因为应该放行，所以取反，返回false
        return !isStaticResource;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpSession session = request.getSession();
        Object loginMember = session.getAttribute("member");
        if(loginMember == null){
            HttpServletResponse response = currentContext.getResponse();
            session.setAttribute("message", CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
            try {
                System.out.println("重定向");
                response.sendRedirect("/auth/member/to/login/page.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
