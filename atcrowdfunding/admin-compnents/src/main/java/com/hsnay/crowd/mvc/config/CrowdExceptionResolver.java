package com.hsnay.crowd.mvc.config;

import com.google.gson.Gson;
import com.hsnay.crowd.exception.AccessForbiddenException;
import com.hsnay.crowd.exception.LoginFailedException;
import com.hsnay.crowd.util.CrowdConstant;
import com.hsnay.crowd.util.CrowdUtil;
import com.hsnay.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//表示基于注解的异常处理器类
@ControllerAdvice
public class CrowdExceptionResolver {
    //类型与方法绑定
    @ExceptionHandler(value = {LoginFailedException.class,AccessForbiddenException.class})
    public ModelAndView resolveNullPointerException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);

    }




    private ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //判断请求类型 返回json还是页面
        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        //ajax请求
        if (judgeResult) {
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            Gson gson = new Gson();
            String s = gson.toJson(resultEntity);
            response.getWriter().write(s);
            return null;
        }
        //不是json
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
