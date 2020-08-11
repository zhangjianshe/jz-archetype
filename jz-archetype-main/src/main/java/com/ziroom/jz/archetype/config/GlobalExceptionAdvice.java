package com.ziroom.jz.archetype.config;

import com.ziroom.jz.archetype.config.model.Result;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 请求参数格式异常.
     *
     * @param request   the request
     * @param exception the exception
     * @return the model and view
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result httpMessageNotReadableExceptionHandler(HttpServletRequest request,
                                                         HttpMessageNotReadableException exception) {
        return Result.fail(421, exception.getMessage());
    }

    /**
     * 调用方法不支持时 返回的视图.
     *
     * @param request   the request
     * @param exception the exception
     * @return the model and view
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class})
    public Result handlerMethodNotSupportException(HttpServletRequest request,
                                                   Exception exception) {
        return Result.fail(421, exception.getMessage());
    }

    /**
     * 处理参数绑定验证错误.
     *
     * @param request   the request
     * @param exception the exception
     * @return the model and view
     */

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result handleBindError(HttpServletRequest request, MethodArgumentNotValidException exception) {
        BindingResult br = exception.getBindingResult();

        StringBuilder sb = new StringBuilder();
        for (ObjectError ex : br.getAllErrors()) {
            sb.append(ex.getDefaultMessage());
            sb.append(";");
        }
        return Result.fail(421, exception.getMessage());
    }


    /**
     * Default error handler.
     *
     * @param request   the request
     * @param exception the exception
     * @return the model and view
     */
    @ExceptionHandler(value = {Exception.class})
    public Result defaultErrorHandler(HttpServletRequest request, Exception exception) {
        String msg = exception.toString();
        StackTraceElement[] stackTrace = exception.getStackTrace();
        if (stackTrace.length > 0) {
            StackTraceElement ste = stackTrace[0];
            msg += "\r\n错误代码在" + ste.getClassName() + "类:" + ste.getMethodName() + "方法的" + ste.getLineNumber() + "行";
        }
        return Result.fail(501, msg);
    }
}
