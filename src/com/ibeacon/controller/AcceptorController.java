package com.ibeacon.controller;

import java.util.HashSet;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;

import com.ibeacon.ExcelView;
import com.ibeacon.Page;
import com.ibeacon.Pageable;
import com.ibeacon.entity.Acceptor;
import com.ibeacon.entity.Login;
import com.ibeacon.service.AcceptorService;
import com.ibeacon.service.LoginService;

@Controller("AcceptorController")
@RequestMapping("/acceptor")
public class AcceptorController extends BaseController{

	@Resource(name = "loginServiceImpl")
	private LoginService loginService;
	@Resource(name = "acceptorServiceImpl")
	private AcceptorService acceptorService;
	
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
		if (acceptor.getName() != null) {
			model.addAttribute("name", acceptor.getName());
		}
		return "/acceptor/index";
	}
	
	/**
	 * 跳转到顾客信息列表页面
	 * @return
	 */
	@RequestMapping(value = "/alarm", method = RequestMethod.GET)
	public String alarmGoto(Acceptor acceptor,Pageable pageable,ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("page", acceptorService.findAcceptorList(acceptor,login.getId(),pageable));
		return "/alarm/index";
	}
	
	/**
	 * 跳转到增加顾客个人信息页面
	 * @return
	 */
	@RequestMapping(value = "/acceptorAdd", method = RequestMethod.GET)
	public String acceptorAdd(ModelMap model) {
		return "/acceptor/add";
	}
	
	/**
	 * 跳转到编辑顾客个人信息页面
	 * @return
	 */
	@RequestMapping(value = "/acceptorEdit", method = RequestMethod.GET)
	public String acceptorEdit(Long id,ModelMap model) {
		model.addAttribute("acceptor", acceptorService.find(id));
		return "/acceptor/edit";
	}
	
	/**
	 * 跳转到编辑顾客个人信息页面
	 * @return
	 */
	@RequestMapping(value = "/alarmEdit", method = RequestMethod.GET)
	public String alarmEdit(Long id,ModelMap model) {
		model.addAttribute("acceptor", acceptorService.find(id));
		return "/alarm/edit";
	}
	
	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/acceptorSave", method = RequestMethod.POST)
	public Boolean acceptorSave(Acceptor acceptor,ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Set<Login> logins = new HashSet<Login>();
		logins.add(login);
		acceptor.setLogin(logins);
		acceptorService.save(acceptor);
		return true;
	}
	
	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/acceptorDelete", method = RequestMethod.POST)
	public Boolean acceptorDelete(Long id,ModelMap model) {
		acceptorService.delete(id);
		return true;
	}
	
	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/acceptorUpdate", method = RequestMethod.POST)
	public Boolean acceptorUpdate(Acceptor acceptor,ModelMap model) {
		Acceptor oldAcceptor=acceptorService.find(acceptor.getId());
		if(acceptor.getName()!=null&&acceptor.getName()!=""){
			oldAcceptor.setName(acceptor.getName());
		}
		if(acceptor.getAddress()!=null&&acceptor.getAddress()!=""){
			oldAcceptor.setAddress(acceptor.getAddress());
		}
		if(acceptor.getAlarmTime()!=null&&acceptor.getAlarmTime()!=""){
			oldAcceptor.setAlarmTime(acceptor.getAlarmTime());
		}
		if(acceptor.getAlarmVoice()!=null&&acceptor.getAlarmVoice()!=""){
			oldAcceptor.setAlarmVoice(acceptor.getAlarmVoice());
		}
		if(acceptor.getIsSend()!=null&&acceptor.getIsSend()!=""){
			oldAcceptor.setIsSend(acceptor.getIsSend());
		}
		if(acceptor.getPhone()!=null&&acceptor.getPhone()!=""){
			oldAcceptor.setPhone(acceptor.getPhone());
		}
		if(acceptor.getRemark()!=null&&acceptor.getRemark()!=""){
			oldAcceptor.setRemark(acceptor.getRemark());
		}
		acceptorService.update(oldAcceptor);
		return true;
	}
	
	/**
	 * 保存顾客信息表
	 */
	@RequestMapping(value = "/deleteSelection", method = RequestMethod.GET)
	public String deleteSelection(String ids, Acceptor acceptor, Pageable pageable, ModelMap model) {
		String[] idList = ids.split(",");
		Long[] id = new Long[idList.length];
		for (int i = 0; i < idList.length; i++) {
			id[i] = Long.valueOf(idList[i]);
		}
		acceptorService.delete(id);
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("page", acceptorService.findAcceptorList(acceptor, login.getId(), pageable));
		if (acceptor.getName() != null) {
			model.addAttribute("name", acceptor.getName());
		}
		return "/acceptor/index";
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView export(Acceptor acceptor, Pageable pageable, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Page<Acceptor> pageAcceptor = acceptorService.findAcceptorList(acceptor, login.getId(), pageable);
		List<Acceptor> acceptors = pageAcceptor.getContent();
		try {
			String filename = "接收器信息表.xls";
			// 设置标题
			String[] header = { "设备名称", "设备位置", "相隔警报时间", "警报声音", "是否发送短信","发送的手机号码", "修改时间", "修改人", "创建时间", "创建人", "备注"};
			// 设置单元格取值
			String[] properties = { "name", "address", "alarmTime", "alarmVoice", "isSend","phone", "modifyDate", "modifyName",
					"createDate", "createName", "remark"};
			return new ModelAndView(new ExcelView(filename, null, properties, header, null, null, acceptors, null),
					model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
