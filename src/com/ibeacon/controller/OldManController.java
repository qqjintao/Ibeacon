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
import com.ibeacon.entity.OldMan;
import com.ibeacon.entity.Login;
import com.ibeacon.service.DataDictionaryService;
import com.ibeacon.service.OldManService;
import com.ibeacon.service.LoginService;

@Controller("OldManController")
@RequestMapping("/oldMan")
public class OldManController extends BaseController {

	@Resource(name = "loginServiceImpl")
	private LoginService loginService;
	@Resource(name = "oldManServiceImpl")
	private OldManService oldManService;
	@Resource(name = "dataDictionaryServiceImpl")
	private DataDictionaryService dataDictionaryService;

	/**
	 * 跳转到顾客信息列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexGoto(OldMan oldMan, Pageable pageable, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("page", oldManService.findOldManList(oldMan, login.getId(), pageable));
		if (oldMan.getName() != null) {
			model.addAttribute("name", oldMan.getName());
		}
		return "/oldMan/index";
	}

	/**
	 * 跳转到增加顾客个人信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/oldManAdd", method = RequestMethod.GET)
	public String oldManAdd(ModelMap model) {
		model.addAttribute("provinces",dataDictionaryService.findDataDictionaryList(-1L));
		return "/oldMan/add";
	}

	/**
	 * 跳转到编辑顾客个人信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/oldManEdit", method = RequestMethod.GET)
	public String oldManEdit(Long id, ModelMap model) {
		model.addAttribute("oldMan", oldManService.find(id));
		model.addAttribute("provinces",dataDictionaryService.findDataDictionaryList(-1L));
		return "/oldMan/edit";
	}

	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/oldManSave", method = RequestMethod.POST)
	public Boolean oldManSave(OldMan oldMan, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Set<Login> logins = new HashSet<Login>();
		logins.add(login);
		oldMan.setLogin(logins);
		oldManService.save(oldMan);
		return true;
	}

	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/oldManDelete", method = RequestMethod.POST)
	public Boolean oldManDelete(Long id, ModelMap model) {
		oldManService.delete(id);
		return true;
	}

	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/oldManUpdate", method = RequestMethod.POST)
	public Boolean oldManUpdate(OldMan oldMan, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Set<Login> logins = new HashSet<Login>();
		logins.add(login);
		oldMan.setLogin(logins);
		oldManService.update(oldMan);
		return true;
	}

	/**
	 * 保存顾客信息表
	 */
	@RequestMapping(value = "/deleteSelection", method = RequestMethod.GET)
	public String deleteSelection(String ids, OldMan oldMan, Pageable pageable, ModelMap model) {
		String[] idList = ids.split(",");
		Long[] id = new Long[idList.length];
		for (int i = 0; i < idList.length; i++) {
			id[i] = Long.valueOf(idList[i]);
		}
		oldManService.delete(id);
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("page", oldManService.findOldManList(oldMan, login.getId(), pageable));
		if (oldMan.getName() != null) {
			model.addAttribute("name", oldMan.getName());
		}
		return "/oldMan/index";
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView export(OldMan oldMan, Pageable pageable, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Page<OldMan> pageOldMan = oldManService.findOldManList(oldMan, login.getId(), pageable);
		List<OldMan> oldMans = pageOldMan.getContent();
		try {
			String filename = "老人信息表.xls";
			// 设置标题
			String[] header = { "姓名", "英文名", "年龄", "性别", "国籍", "民族", "政治面貌", "身份证", "身体状况", "联系电话", "关系", "现居住地省",
					"现居住地市", "现居住地区", "现居住地街道地址", "户口所在省", "户口所在市", "户口所在区", "户口居住街道地址", "医保账户", "负责医生", "医生电话", "负责护士",
					"护士电话", "紧急联系人", "紧急联系电话", "备注", "头像地址" };
			// 设置单元格取值
			String[] properties = { "name", "englishName", "age", "sex", "nationality", "nation", "politicsStatus",
					"identityCard", "physical", "phone", "relation", "province", "city", "district", "streetAddress",
					"domicileProvince", "domicileCity", "domicileDistrict", "domicile", "account", "doctor",
					"doctorPhone", "nurse", "nursePhone", "emergencyName", "emergencyCall", "remark","headPortrait" };
			return new ModelAndView(new ExcelView(filename, null, properties, header, null, null, oldMans, null),
					model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
