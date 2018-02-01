package com.graby.store.portal.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.graby.store.entity.User;
import com.graby.store.service.base.UserService;
import com.graby.store.web.auth.ShiroContextUtils;
import com.taobao.api.internal.util.json.JSONReader;
import com.taobao.api.internal.util.json.JSONWriter;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String toEdit(Model model) throws Exception {
		User user = userService.getUser(ShiroContextUtils.getUserid());
		String desc = user.getDescription();
		if (desc != null) {
			Map<String, String> profile = getProfile(desc);
			if (profile != null) {
				model.addAttribute("profile", profile);
			}
		}
		model.addAttribute("action", "update");
		return "profile/profileForm";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String save(@ModelAttribute("preloadItem") Map<String, String> profile, RedirectAttributes redirectAttributes)
			throws Exception {
		userService.updateDesc(ShiroContextUtils.getUserid(), toJsonString(profile));
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/profile/edit";
	}

	@ModelAttribute("preloadItem")
	public Map<String, String> getProfileValue(@RequestParam(value = "phone", required = false) String phone) {
		Map<String, String> item = new HashMap<String, String>();
		item.put("phone", phone);
		return item;
	}

	private static JSONWriter write = new JSONWriter(false, true);
	private static JSONReader reader = new JSONReader() {
	};

	private static String toJsonString(Object object) {
		return write.write(object);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getProfile(String json) {
		return (Map<String, String>) reader.read(json);
	}

}
