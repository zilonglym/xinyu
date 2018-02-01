package com.graby.store.admin.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.util.BaseResource;
import com.graby.store.entity.Centro;
import com.graby.store.remote.CentroRemote;
import com.taobao.api.ApiException;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 */
@Controller
@RequestMapping(value="login")
public class LoginController {

	 @Autowired
	 private CentroRemote centroRemote;

	@RequestMapping(method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response,Model model) throws ApiException, IOException {
		
		
		List<Centro> centros = centroRemote.findCentros();
		for(int i   =  0 , size =centros.size();i <size;i++ ){
			Centro centro = centros.get(i);
		}
		request.setAttribute("centros", centros);
		
		return "/account/login"; 
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		
		List<Centro> centros = centroRemote.findCentros();
		for(int i   =  0 , size =centros.size();i <size;i++ ){
			Centro centro = centros.get(i);
		}
		model.addAttribute("centros", centros);
		return "account/login";
	}
	
	@RequestMapping(value="/loginf7Submit")
	@ResponseBody
	public Map<String,Object> loginSubmit(HttpServletRequest request,HttpServletResponse response){
		String centro=request.getParameter("centro");
		SecurityUtils.getSubject().getSession().setAttribute(BaseResource.ADMIN_CENTRO_KEY,centro);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("ret", 1);
		return resultMap;
	}

}
