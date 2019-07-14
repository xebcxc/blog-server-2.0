package pro.meisen.boot.core.response;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import pro.meisen.boot.core.constants.AppCode;

/**
 *
 * 通过修改RequestMappingHandlerAdapter中的returnValueHandlers中的值，
 * 将RequestResponseBodyMethodProcessor替换成自定义对象。
 * @author meisen
 * 2019-05-23
 */
public class HandlerMethodReturnValueHandlerProxy implements HandlerMethodReturnValueHandler {
    private HandlerMethodReturnValueHandler proxyObject;

    public HandlerMethodReturnValueHandlerProxy(HandlerMethodReturnValueHandler proxyObject) {
        this.proxyObject = proxyObject;
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return proxyObject.supportsReturnType(methodParameter);
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        Result<Object> result = new Result<>();
        result.setCode(AppCode.SUCCESS_CODE);
        result.setData(o);
        proxyObject.handleReturnValue(result, methodParameter, modelAndViewContainer, nativeWebRequest);
    }
}
