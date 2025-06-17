package com.ddantime.ddantime.common.resolver;

import com.ddantime.ddantime.common.annotation.RequestUser;
import com.ddantime.ddantime.common.exception.CustomException;
import com.ddantime.ddantime.common.exception.ErrorCode;
import com.ddantime.ddantime.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RequestUserArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class)
                && parameter.hasParameterAnnotation(RequestUser.class);
    }

    /**
     * 파라미터에 전달할 객체 생성
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Object user = webRequest.getNativeRequest(HttpServletRequest.class).getAttribute("requestUser");

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return user;
    }
}
