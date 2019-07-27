package com.ibeacon.controller;

import java.util.HashSet;
import java.util.Set;

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
import com.ibeacon.entity.Acceptor;
import com.ibeacon.entity.Fence;
import com.ibeacon.entity.Login;
import com.ibeacon.service.AcceptorService;
import com.ibeacon.service.FenceService;
import com.ibeacon.service.LoginService;

@Controller("FenceController")
@RequestMapping("/fence")
public class FenceController extends BaseController{
	@Resource(name = "loginServiceImpl")
	private LoginService loginService;
	@Resource(name = "acceptorServiceImpl")
	private AcceptorService acceptorService;
	@Resource(name = "fenceServiceImpl")
	private FenceService fenceService;
	/**
	 * 跳转到顾客信息列表页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexGoto(Acceptor acceptor,Pageable pageable,ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("page", acceptorService.findAcceptorList(acceptor,login.getId(),pageable));
		return "/fence/index";
	}
	
	/**
	 * 跳转到编辑顾客个人信息页面
	 * @return
	 */
	@RequestMapping(value = "/updata", method = RequestMethod.GET)
	public String updata(ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("fence",fenceService.findByLoginId(login.getId()));
		return "/fence/updata";
	}
	
	/**
	 * 跳转到编辑顾客个人信息页面
	 * @return
	 */
	@RequestMapping(value = "/fenceEdit", method = RequestMethod.GET)
	public String fenceEdit(Long id,Acceptor acceptor,Pageable pageable,ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Acceptor oldAcceptor=acceptorService.find(id);
		Fence fence=fenceService.findByLoginId(login.getId());
		if(fence!=null){
			oldAcceptor.setAlarmTime(fence.getAlarmTime());
			oldAcceptor.setAlarmVoice(fence.getAlarmVoice());
			oldAcceptor.setIsSend(fence.getIsSend());
			oldAcceptor.setPhone(fence.getPhone());
			acceptorService.update(oldAcceptor);
		}else{
			return "/fence/updata";
		}
		model.addAttribute("page", acceptorService.findAcceptorList(acceptor,login.getId(),pageable));
		return "/fence/index";
	}
	
	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/fenceUpdate", method = RequestMethod.POST)
	public Boolean fenceUpdate(Fence fence,ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Set<Login> logins = new HashSet<Login>();
		logins.add(login);
		fence.setLogin(logins);
		fenceService.update(fence);
		return true;
	}
}
