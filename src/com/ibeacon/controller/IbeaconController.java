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
import com.ibeacon.entity.Ibeacon;
import com.ibeacon.entity.Login;
import com.ibeacon.entity.OldMan;
import com.ibeacon.form.IbeaconContainer;
import com.ibeacon.service.IbeaconService;
import com.ibeacon.service.LoginService;
import com.ibeacon.service.OldManService;

@Controller("IbeaconController")
@RequestMapping("/ibeacon")
public class IbeaconController extends BaseController {

	@Resource(name = "loginServiceImpl")
	private LoginService loginService;
	@Resource(name = "ibeaconServiceImpl")
	private IbeaconService ibeaconService;
	@Resource(name = "oldManServiceImpl")
	private OldManService oldManService;

	/**
	 * 跳转到i beacon信息列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexGoto(Ibeacon ibeacon,String oldManName, Pageable pageable, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("page", ibeaconService.findIbeaconList(ibeacon, login.getId(),oldManName, pageable));
		if (ibeacon.getUUID() != null) {
			model.addAttribute("UUID", ibeacon.getUUID());
		}
		if (oldManName != null) {
			model.addAttribute("oldManName", oldManName);
		}
		return "/ibeacon/index";
	}

	/**
	 * 跳转到增加顾客个人信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ibeaconAdd", method = RequestMethod.GET)
	public String ibeaconAdd(ModelMap model) {
		return "/ibeacon/add";
	}

	/**
	 * 跳转到编辑顾客个人信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ibeaconEdit", method = RequestMethod.GET)
	public String ibeaconEdit(Long id, ModelMap model) {
		model.addAttribute("ibeacon", ibeaconService.find(id));
		return "/ibeacon/edit";
	}

	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/ibeaconSave", method = RequestMethod.POST)
	public Boolean ibeaconSave(Ibeacon ibeacon, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Set<Login> logins = new HashSet<Login>();
		logins.add(login);
		ibeacon.setLogin(logins);
		ibeaconService.save(ibeacon);
		return true;
	}

	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/ibeaconDelete", method = RequestMethod.POST)
	public Boolean ibeaconDelete(Long id, ModelMap model) {
		ibeaconService.delete(id);
		return true;
	}

	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/ibeaconUpdate", method = RequestMethod.POST)
	public Boolean ibeaconUpdate(Ibeacon ibeacon, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Set<Login> logins = new HashSet<Login>();
		logins.add(login);
		ibeacon.setLogin(logins);
		ibeaconService.update(ibeacon);
		return true;
	}

	/**
	 * 保存顾客信息表
	 */
	@RequestMapping(value = "/deleteSelection", method = RequestMethod.GET)
	public String deleteSelection(String ids, Ibeacon ibeacon,String oldManName, Pageable pageable, ModelMap model) {
		String[] idList = ids.split(",");
		Long[] id = new Long[idList.length];
		for (int i = 0; i < idList.length; i++) {
			id[i] = Long.valueOf(idList[i]);
		}
		ibeaconService.delete(id);
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("page", ibeaconService.findIbeaconList(ibeacon, login.getId(),oldManName, pageable));
		if (ibeacon.getUUID() != null) {
			model.addAttribute("UUID", ibeacon.getUUID());
		}
		if (oldManName != null) {
			model.addAttribute("oldManName", oldManName);
		}
		return "/ibeacon/index";
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView export(Ibeacon ibeacon, Pageable pageable,String oldManName, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Page<IbeaconContainer> pageIbeacon = ibeaconService.findIbeaconList(ibeacon, login.getId(),oldManName, pageable);
		List<IbeaconContainer> ibeacons = pageIbeacon.getContent();
		try {
			String filename = "iBeacon信息表.xls";
			// 设置标题
			String[] header = { "UUID", "Major", "Minor", "剩余电量", "是否开机状态", "老人ID", "老人姓名", "修改时间", "修改人", "创建时间",
					"创建人", "备注" };
			// 设置单元格取值
			String[] properties = { "UUID", "major", "minor", "electric", "mode", "oldman", "name", "modifyDate",
					"modifyName", "createDate", "createName", "remark" };
			return new ModelAndView(new ExcelView(filename, null, properties, header, null, null, ibeacons, null),
					model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/oldManList", method = RequestMethod.GET)
	public String oldManList(OldMan oldMan, Long ibeaconId, Pageable pageable, ModelMap model) {
		if (oldMan.getId() != null) {
			model.addAttribute("seen", false);
		} else {
			model.addAttribute("seen", true);
		}
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("page", oldManService.findOldManList(oldMan, login.getId(), pageable));
		if (oldMan.getName() != null) {
			model.addAttribute("name", oldMan.getName());
		}
		model.addAttribute("ibeaconId", ibeaconId);
		return "/ibeacon/oldManList";
	}

	@ResponseBody
	@RequestMapping(value = "/config", method = RequestMethod.POST)
	public Boolean config(Long id, Long ibeaconId, ModelMap model) {
		OldMan oldMan = oldManService.find(id);
		Ibeacon ibeacon = ibeaconService.find(ibeaconId);
		Set<OldMan> oldMans = new HashSet<OldMan>();
		oldMans.add(oldMan);
		ibeacon.setOldMan(oldMans);
		ibeaconService.update(ibeacon);
		return true;
	}

	@RequestMapping(value = "/deletecofig", method = RequestMethod.GET)
	public String deletecofig(Long ibeaconId, Pageable pageable,String oldManName, ModelMap model) {
		Ibeacon ibeacon = ibeaconService.find(ibeaconId);
		ibeacon.setOldMan(null);
		ibeaconService.update(ibeacon);
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("page", ibeaconService.findIbeaconList(ibeacon, login.getId(),oldManName, pageable));
		if (ibeacon.getUUID() != null) {
			model.addAttribute("UUID", ibeacon.getUUID());
		}
		if (oldManName != null) {
			model.addAttribute("oldManName", oldManName);
		}
		return "/ibeacon/index";
	}
	
	/**
	 * 检查用户名是否存在  存在返回 false 不存在返回 true
	 */ 
	@ResponseBody
	@RequestMapping(value = "/check_UUID", method = RequestMethod.POST)
	public boolean checkUUID(String UUID) {
		if (ibeaconService.IsExists(UUID)>0) {
			return false;
		} else {
			return true;
		}
	}
}
