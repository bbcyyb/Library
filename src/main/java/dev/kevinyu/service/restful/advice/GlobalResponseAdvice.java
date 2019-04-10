package dev.kevinyu.service.restful.advice;

import dev.kevinyu.service.restful.exception.*;
import dev.kevinyu.service.restful.model.ResultEntity;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalResponseAdvice extends ResponseEntityExceptionHandler implements ResponseBodyAdvice<Object> {

    @ExceptionHandler(Exception.class)
    public ResultEntity handleCommonExceptions(HttpServletRequest req, HttpServletResponse res, Exception ex) {
        ResultEntity envelope = ResultEntity.fail(ex.getMessage());
        return envelope;
    }

    @ExceptionHandler(BaseResponseException.class)
    public ResultEntity handleBaseResponseExceptions(HttpServletRequest req, HttpServletResponse res, BaseResponseException ex) {
        ResultEntity envelope = ResultEntity.fail(ex.getStatusCode(),ex.getMessage());
        return envelope;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResultEntity handleNotFoundExceptions(HttpServletRequest req, HttpServletResponse res, NotFoundException ex) {
        ResultEntity envelope = ResultEntity.fail(ex.getStatusCode(),ex.getMessage());
        return envelope;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return o instanceof ResultEntity ? o : ResultEntity.success(o);
    }
}
