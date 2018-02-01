package com.graby.store.admin.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.graby.store.cache.Cache;
import com.taobao.api.ApiException;

@Controller
@Component
@RequestMapping(value = "/")
public class TopAuthController {

	@Value("${top.appkey}")
	private final String clientId = "23012748";

	@Value("${top.appSecret}")
	private final String clientSecret = "1ef1ff93070d9be52f3bdd05adc10a02";

	@Value("${top.oauth.token}")
	private final String tokenUrl = "https://oauth.taobao.com/token";

	@Autowired
	private Cache<String, String> userCache;

	// @Autowired
	// private TopApi topApi;
	//
	// @Autowired
	// private AuthService userService;

	/**
	 * OAuth2方式
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ApiException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "top_oauth")
	public String oauth(HttpServletRequest request, HttpServletResponse response, Model model) throws ApiException,
			IOException {
		// String error = request.getParameter("error");
		// if (StringUtils.isNotBlank(error)) {
		// String error_description = request.getParameter("error_description");
		// System.out.println(error_description);
		// }
		// String code = request.getParameter("code");
		// model.addAttribute("code", code);
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("code", code);
		// params.put("client_id", clientId);
		// params.put("client_secret", clientSecret);
		// params.put("grant_type", "authorization_code");
		// params.put("redirect_uri", "http://admin.wlpost.com/top_oauth");
		// // params.put("redirect_uri", "http://121.196.129.75/top_oauth_get");
		// String json = WebUtils.doPost(tokenUrl, params, 1000, 1000);
		// ObjectMapper mapper = new ObjectMapper();
		// Map<String, String> value = mapper.readValue(json, Map.class);
		// String sessionKey = value.get("access_token");
		// String nick = value.get("taobao_user_nick");
		// model.addAttribute("username", nick);
		// model.addAttribute("password", EncryptUtil.md5(nick));
		// ShiroContextUtils.logout();
		return "auth/post";
	} 

}
