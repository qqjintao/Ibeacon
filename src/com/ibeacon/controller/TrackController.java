package com.ibeacon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibeacon.Pageable;

@Controller("TractController")
@RequestMapping("/track")
public class TrackController extends BaseController{
	/**
	 * 跳转到顾客信息列表页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexGoto(Pageable pageable,ModelMap model) {
		return "/track/index";
	}
}
