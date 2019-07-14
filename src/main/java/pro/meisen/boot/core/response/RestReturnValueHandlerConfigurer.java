package pro.meisen.boot.core.response;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回值处理器
 * @author meisen
 * 2019-05-23
 */
@Configuration
public class RestReturnValueHandlerConfigurer implements InitializingBean {
    private final RequestMappingHandlerAdapter handlerAdapter;

    @Autowired
    public RestReturnValueHandlerConfigurer(RequestMappingHandlerAdapter handlerAdapter) {
        this.handlerAdapter = handlerAdapter;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers = handlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> newList = new ArrayList<>();
        if (handlerMethodReturnValueHandlers != null) {
            for (HandlerMethodReturnValueHandler valueHandler: handlerMethodReturnValueHandlers) {
                if (valueHandler instanceof RequestResponseBodyMethodProcessor) {
                    HandlerMethodReturnValueHandlerProxy proxy = new HandlerMethodReturnValueHandlerProxy(valueHandler);
                    newList.add(proxy);
                } else {
                    newList.add(valueHandler);
                }
            }
        }
        handlerAdapter.setReturnValueHandlers(newList);
    }
}
