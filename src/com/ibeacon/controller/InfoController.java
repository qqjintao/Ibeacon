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
import com.ibeacon.entity.Info;
import com.ibeacon.entity.Login;
import com.ibeacon.service.DataDictionaryService;
import com.ibeacon.service.InfoService;
import com.ibeacon.service.LoginService;

@Controller("InfoController")
@RequestMapping("/info")
public class InfoController extends BaseController {

	@Resource(name = "loginServiceImpl")
	private LoginService loginService;
	@Resource(name = "infoServiceImpl")
	private InfoService infoService;
	@Resource(name = "dataDictionaryServiceImpl")
	private DataDictionaryService dataDictionaryService;

	/**
	 * 跳转到顾客信息列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexGoto(Info info, Pageable pageable, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("page", infoService.findInfoList(info, login.getId(), pageable));
		if (info.getIdCardName() != null) {
			model.addAttribute("idCardName", info.getIdCardName());
		}
		return "/info/index";
	}

	/**
	 * 跳转到增加顾客个人信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/infoAdd", method = RequestMethod.GET)
	public String infoAdd(ModelMap model) {
		model.addAttribute("provinces",dataDictionaryService.findDataDictionaryList(-1L));
		return "/info/add";
	}

	/**
	 * 跳转到编辑顾客个人信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/infoEdit", method = RequestMethod.GET)
	public String infoEdit(Long id, ModelMap model) {
		model.addAttribute("info", infoService.find(id));
		model.addAttribute("provinces",dataDictionaryService.findDataDictionaryList(-1L));
		return "/info/edit";
	}

	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/infoSave", method = RequestMethod.POST)
	public Boolean infoSave(Info info, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Set<Login> logins = new HashSet<Login>();
		logins.add(login);
		info.setLogin(logins);
		infoService.save(info);
		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/infoDelete", method = RequestMethod.POST)
	public Boolean infoDelete(Long id, ModelMap model) {
		infoService.delete(id);
		return true;
	}

	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/infoUpdate", method = RequestMethod.POST)
	public Boolean infoUpdate(Info info, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Set<Login> logins = new HashSet<Login>();
		logins.add(login);
		info.setLogin(logins);
		infoService.update(info);
		return true;
	}

	/**
	 * 保存顾客信息表
	 */
	@RequestMapping(value = "/deleteSelection", method = RequestMethod.GET)
	public String deleteSelection(String ids, Info info, Pageable pageable, ModelMap model) {
		String[] idList = ids.split(",");
		Long[] id = new Long[idList.length];
		for (int i = 0; i < idList.length; i++) {
			id[i] = Long.valueOf(idList[i]);
		}
		infoService.delete(id);
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("page", infoService.findInfoList(info, login.getId(), pageable));
		if (info.getIdCardName() != null) {
			model.addAttribute("idCardName", info.getIdCardName());
		}
		return "/info/index";
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView export(Info info, Pageable pageable, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Page<Info> pageInfo = infoService.findInfoList(info, login.getId(), pageable);
		List<Info> infos = pageInfo.getContent();
		try {
			String filename = "顾客信息表.xls";
			// 设置标题
			String[] header = { "姓名", "英文名", "年龄", "性别", "出生地", "出生日期", "身份证", "民族", "政治面貌", "婚姻状况", "现居住地省", "现居住地市",
					"现居住地区", "现居住地街道地址", "户口所在省", "户口所在市", "户口所在区", "户口居住街道地址", "邮箱", "手机", "住宅电话", "办公电话", "备注","头像地址" };
			// 设置单元格取值
			String[] properties = { "idCardName", "englishName", "age", "sex", "birthplace", "dateOfBirth", "idCard",
					"nation", "politicsStatus", "maritalStatus", "province", "city", "district", "streetAddress",
					"domicileProvince", "domicileCity", "domicileDistrict", "domicile", "mailbox", "phone", "homePhone",
					"officePhone", "remark","headPortrait"};
			// 类型转换
			// Converter[] converters = {Double.class,Date.class};
			// 附加内容
			// String[] contents = new String[3];
			// contents[0] = "顾客信息表";
			// contents[1] = "操作员" + ": " + login.getNickName();
			// contents[2] = "生成日期" + ": " + new
			// SimpleDateFormat("yyyy-MM-dd").format(new Date());
			// 调用ExcelView工具类生成Excel并导出
			return new ModelAndView(new ExcelView(filename, null, properties, header, null, null, infos, null), model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
