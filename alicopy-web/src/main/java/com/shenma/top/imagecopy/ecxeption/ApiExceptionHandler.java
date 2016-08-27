package com.shenma.top.imagecopy.ecxeption;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
public class ApiExceptionHandler {
	protected static Logger logger = Logger.getLogger("ApiExceptionHandler");
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String handleInvalidRequestError(NullPointerException ex) {
    	logger.error(ex);
    	System.out.println(ex);
        return ex.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleUnexpectedServerError(RuntimeException ex) {
    	  Map<String, Object> model = new HashMap<String, Object>();
          model.put("ex", ex);
          logger.error(ex);
          return new ModelAndView("exception/error", model);  
    }
}