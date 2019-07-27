package com.ibeacon.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ibeacon.Pageable;
import com.ibeacon.entity.IBeaconInput;
import com.ibeacon.entity.Login;
import com.ibeacon.service.AcceptorService;
import com.ibeacon.service.IBeaconInputService;
import com.ibeacon.service.LoginService;

@Controller("IBeaconInputController")
@RequestMapping("/iBeaconInput")
public class IBeaconInputController extends BaseController{
	
	@Resource(name = "loginServiceImpl")
	private LoginService loginService;
	@Resource(name = "acceptorServiceImpl")
	private AcceptorService acceptorService;
	@Resource(name = "iBeaconInputServiceImpl")
	private IBeaconInputService iBeaconInputService;
	/**
	 * 跳转到顾客信息列表页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexGoto(IBeaconInput iBeaconInput,String oldMan,String acceptorName,String address,Pageable pageable,ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		if (iBeaconInput != null) {
			model.addAttribute("iBeaconInput", iBeaconInput);
		}
		if (oldMan != null) {
			model.addAttribute("oldManName", oldMan);
		}
		if (acceptorName != null) {
			model.addAttribute("acceptorName", acceptorName);
		}
		if (address != null) {
			model.addAttribute("address", address);
		}
		model.addAttribute("page",iBeaconInputService.findIBeaconInput(iBeaconInput, oldMan, acceptorName, address, login.getId(), pageable));
		return "/track/list";
	}
	
	@RequestMapping(value = "/iBeaconInputAdd", method = RequestMethod.GET)
	public String iBeaconInputAdd(ModelMap model) {
		return "/track/add";
	}


	@ResponseBody
	@RequestMapping(value = "/iBeaconInputSave", method = RequestMethod.POST)
	public Boolean iBeaconInputSave(IBeaconInput iBeaconInput, ModelMap model) {
		iBeaconInputService.save(iBeaconInput);
		return true;
	}
}
