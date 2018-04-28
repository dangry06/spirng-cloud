package com.aspire.imp.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.imp.common.contanst.ApiResult;
import com.aspire.imp.common.contanst.RetCode;
import com.aspire.imp.common.exception.BusinessException;
import com.aspire.imp.common.util.fileupload.StorageException;
import com.aspire.imp.common.util.fileupload.StorageFileNotFoundException;

@ControllerAdvice
public class ExceptionHandlerAdvice {
	private final static Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
	
    @ExceptionHandler
    @ResponseBody
    public ApiResult businessExceptionHandler(BusinessException e) { //还可以声明接收其他任意参数
    	log.error(e.getLocalizedMessage(), e);
    	return new ApiResult(e.getCode(), e.getLocalizedMessage());
    }

    @ExceptionHandler
    @ResponseBody
    public ApiResult storageFileNotFoundExceptionler(StorageFileNotFoundException e, Model model){
    	log.error(e.getLocalizedMessage(), e);
//    	model.addAttribute("message", e.getMessage());
//    	return "exception";
    	return new ApiResult(RetCode.FILE_NOTEXIST, e.getLocalizedMessage());
    }
    
    @ExceptionHandler
    @ResponseBody
    public ApiResult storageExceptionHandler(StorageException e) {
    	log.error(e.getLocalizedMessage(), e);
    	return new ApiResult(RetCode.FILE_OPERATE_ERROR, e.getLocalizedMessage());
    }
    
    @ExceptionHandler
    @ResponseBody
    public ApiResult missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
    	log.error(e.getLocalizedMessage(), e);
    	return new ApiResult(RetCode.MISSING_PARAMETER, e.getLocalizedMessage());
    }
    
    @ExceptionHandler
    @ResponseBody
    public ApiResult allExceptionHandler(Exception e) {
    	log.error(e.getLocalizedMessage(), e);
    	return new ApiResult(RetCode.SYSTEM_ERROR, e.getLocalizedMessage());
    }
}
 