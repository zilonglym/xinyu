package com.xinyu.controller.task;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xinyu.common.BaseController;

/**
 * 财务帐单定时生成器
 * @author yangmin
 * 2018年3月6日
 *
 */
@Controller
@Lazy(value=false)
@RequestMapping(value="expressPrice")
public class ExpressPriceTask extends BaseController {

}
