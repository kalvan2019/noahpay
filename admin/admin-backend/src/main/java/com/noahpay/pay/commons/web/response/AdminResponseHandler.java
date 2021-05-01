package com.noahpay.pay.commons.web.response;

import com.kalvan.client.context.RequestContext;
import com.kalvan.client.context.RequestContextHolder;
import com.kalvan.client.model.Response;
import com.kalvan.client.model.WebResponse;
import com.kalvan.web.servlet.response.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author chenliang
 */
@ControllerAdvice
@Slf4j
public class AdminResponseHandler extends ResponseHandler {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        RequestContext context = RequestContextHolder.getContext();
        if(o instanceof WebResponse){
            context.setState(((Response<?>) o).getState());
            context.setSuccess(((Response<?>) o).success());
            context.setResponseCode(((Response<?>) o).getCode());
            context.setResponseMessage(((Response<?>) o).getMessage());
            return o;
        }
        if (o instanceof Response) {
            //埋点数据
            context.setState(((Response<?>) o).getState());
            context.setSuccess(((Response<?>) o).success());
            context.setResponseCode(((Response<?>) o).getCode());
            context.setResponseMessage(((Response<?>) o).getMessage());
            return WebResponse.buildResult((Response) o);
        }
        return o;
    }
}