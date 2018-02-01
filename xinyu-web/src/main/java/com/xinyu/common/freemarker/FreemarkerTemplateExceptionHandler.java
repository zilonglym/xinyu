package com.xinyu.common.freemarker;

import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Logger;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;


public class FreemarkerTemplateExceptionHandler implements TemplateExceptionHandler {
	private static final Logger logger = Logger.getLogger(FreemarkerTemplateExceptionHandler.class);

	public void handleTemplateException(TemplateException te, Environment env,Writer out) throws TemplateException {
		try {
			out.write("<p name ='_FK_ERROR_NAME' class=\"color_red\" title=\""+te.getMessage()+"\">页面异常 </p>");
			logger.error("[Freemarker Error: " + te.getMessage() + "]");
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			throw new TemplateException("Failed to print error message. Cause: " + e, env);
		}
	}
}
