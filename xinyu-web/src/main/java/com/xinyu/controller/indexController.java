package com.xinyu.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;
import com.xinyu.model.base.Centro;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.AccountMenu;
import com.xinyu.model.system.AccountRelation;
import com.xinyu.model.system.AccountRoleRelation;
import com.xinyu.model.system.AccountRoles;
import com.xinyu.model.system.Notice;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.service.report.ReportService;
import com.xinyu.service.system.AccountMenuService;
import com.xinyu.service.system.AccountRoleRelationService;
import com.xinyu.service.system.AccountRolesService;
import com.xinyu.service.system.AccountService;
import com.xinyu.service.system.CentroService;
import com.xinyu.service.system.SystemItemService;
import com.xinyu.service.system.UserService;

/**
 * 首页控制类
 * 
 * @author lenovo
 *
 */
@Controller
public class indexController extends BaseController {

	public final static String Session_User_key = "Session_User_key";
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountRoleRelationService relationService;
	@Autowired
	private AccountRolesService roleService;
	@Autowired
	private AccountMenuService menuService;
	@Autowired
	private CentroService centroService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private SystemItemService sysService;

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index(ModelMap model) {
		/**
		 * 查询此用户所有的菜单项,暂时不对角色做区分，一次性查询出此用户所有的菜单项
		 */
		Account account = this.getCurrentAccount();
		if (account == null) {
			return this.login(model);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountId", account.getId());
		List<AccountMenu> menuList = this.menuService.getAccountMenuByAccount(params);
		model.put("menuList", menuList);

		params.clear();
		params.put("status", "WMS_ACCEPT");
		List<Map<String, Object>> ordersMap = this.reportService.findShipCount(params);
		model.put("orders", ordersMap);

		Notice notice = this.sysService.findNoticeById(null);
		model.put("notice", notice);

		List<Map<String, Object>> itemMap = this.reportService.findZeroItemCount(params);
		model.put("items", itemMap);

		AccountRoleRelation relation = this.relationService.findAccountRoleRelationByAccountId(account.getId());
		AccountRoles roles = this.roleService.getAccountRolesById(relation.getRole().getId());
		model.put("roles", roles);

		model.put("account", account);

		return "common/index";
	}

	/**
	 * 登录转向
	 * 
	 * @return
	 */
	@RequestMapping(value = "login")
	public String login(ModelMap model) {
		List<Centro> centroList = this.centroService.getCentroByList(null);
		model.put("centroList", centroList);
		return "login";
	}

	@RequestMapping(value = "logout")
	public String logout(ModelMap model) {
		this.getRequest().getSession().removeAttribute(indexController.Session_User_key);
		SessionUser.clear();
		return this.login(model);
	}

	/**
	 * 提交登录
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping(value = "/login/submitLogin")
	@ResponseBody
	public Map<String, Object> submitLogin() throws Exception {
		String userName = this.getString("userName");
		String password = this.getString("password");
		String centroId = this.getString("centro");
		Centro centro = this.centroService.getCentroById(centroId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		List<Account> accountList = this.accountService.findAccountsPage(params, 1, 10);
		logger.debug("登录参数:" + params + "||" + accountList.size());
		if (accountList != null && accountList.size() > 0) {
			Account account = accountList.get(0);
			account.setCu(centro.getId());

			String md5Password = this.EncoderByMd5(password);
			if (md5Password.equals(account.getPassword())) {
				resultMap.put("ret", 1);
				SessionUser.set(account);// 登录用户对象写入session
				resultMap.put("id", account.getId());
				resultMap.put("userName", account.getUserName());
				this.getRequest().getSession().setAttribute(indexController.Session_User_key, account);
			} else {
				resultMap.put("ret", 0);
			}
		} else {
			resultMap.put("ret", 0);
		}

		return resultMap;
	}

}
