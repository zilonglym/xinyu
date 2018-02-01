package com.graby.store.admin.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.graby.store.entity.Item;
import com.graby.store.remote.ItemRemote;


@Controller
@RequestMapping(value="f7index")
public class F7getDataController {
	
	@Autowired
	private ItemRemote itemRemote;

	@RequestMapping(value="item")
	public String index(HttpServletRequest request,ModelMap model,
			@RequestParam(value = "pageSize", defaultValue = "14") int pageSize,
			@RequestParam(value = "page", defaultValue = "1") int page){
		String searchText=request.getParameter("searchText");
		Page<Item> itemList=this.itemRemote.findPageUserItems(Long.valueOf(searchText),page,pageSize);
		model.put("list", itemList);
		model.put("searchText", searchText);
		//分页
		return "f7index/itemF7index";
	}
	
	
}
