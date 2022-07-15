package com.ycicic.fivehearts.framework.advice;

import com.ycicic.fivehearts.framework.web.domain.AjaxResult;
import com.ycicic.fivehearts.framework.web.domain.R;
import com.ycicic.fivehearts.framework.web.page.TableDataInfo;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * api返回值Advice
 *
 * @author ycc
 */
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, @NonNull MethodParameter methodParameter, @NonNull MediaType mediaType, @NonNull Class aClass, @NonNull ServerHttpRequest serverHttpRequest, @NonNull ServerHttpResponse serverHttpResponse) {
        if (Objects.isNull(o)) {
            return R.ok();
        }

        if (o instanceof R) {
            return o;
        } else if (o instanceof AjaxResult) {
            return o;
        } else if (o instanceof TableDataInfo) {
            return o;
        }

        return R.ok(o);
    }
}
