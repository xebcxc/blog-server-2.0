package fun.baozi.boot.core.exception;

import fun.baozi.boot.core.constants.AppCode;
import fun.baozi.boot.core.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理器
 * @author baozi
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
            LOGGER.error("AppException.errorCode={}|errorMsg={}", appException.getErrorCode(), appException.getErrorMsg());
            return result;
        }
        LOGGER.error("系统错误. msg={}", e.getMessage());
        result.setCode(AppCode.APP_ERROR);
        result.setMsg("未知错误, 请稍后重试.");
        return result;
    }
}
