package com.xinyu.check.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;


import freemarker.template.SimpleHash;

public class FreeMarkerViewExt extends FreeMarkerView {
	@Override
	protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		model.put("ctx", request.getContextPath());
		model.put("base", request.getContextPath() + "/themes/default");
	//	model.put("currentUser", SessionPerson.get());
	//	model.put("currentCity", CurrentCity.getDefaultCurrentCity());
	//	model.put("currentCU", CurrentCity.getCurrentCompany());
	//	model.put("personOrgDirective", ContextLoader.getCurrentWebApplicationContext().getBean("personOrgDirective"));
	//	model.put("orgDirective", ContextLoader.getCurrentWebApplicationContext().getBean("orgDirective"));
		//model.put("PERMISSION", BeanFactory.getBean("permissionValidationDirective"));
	//	model.put("personPositionsDirective", ContextLoader.getCurrentWebApplicationContext().getBean("personPositionsDirective"));
		return super.buildTemplateModel(model, request, response);
	}
}
