package com.sky.handler;

import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static com.sky.constant.MessageConstant.*;
import java.sql.SQLIntegrityConstraintViolationException;




/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error("异常信息：{}", ex.getMessage());
        String message=ex.getMessage();
        if (message.contains("Duplicate entry")){
            String [] str =message.split(" ");
            String username=str[2];
            String msg=username+ALREADY_EXIST;
            return Result.error(msg);
        }else{
            String msg=UNKNOWN_ERROR;
            return Result.error(msg);
        }
    }

}
