package pro.meisen.boot.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pro.meisen.boot.core.constants.AppCode;
import pro.meisen.boot.core.response.Result;

/**
 * 异常处理器
 * @author meisen
 * 2019-05-23
 */
@ControllerAdvice
public class ExceptionProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionProcessor.class);

    @ExceptionHandler
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        Result result = new Result();
        if (e instanceof AppException) {
            AppException appException = (AppException) e;
            result.setCode(appException.getErrorCode());
            result.setMsg(appException.getErrorMsg());
            return result;
        }
        LOGGER.error("未知错误, 错误信息: {}", e.getCause());
        result.setCode(AppCode.APP_ERROR);
        result.setMsg("未知错误, 请稍后重试.");
        return result;
    }
}
