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
import com.ibeacon.entity.Document;
import com.ibeacon.entity.Login;
import com.ibeacon.service.DocumentService;
import com.ibeacon.service.LoginService;

@Controller("DocumentController")
@RequestMapping("/document")
public class DocumentController extends BaseController{
	@Resource(name = "loginServiceImpl")
	private LoginService loginService;
	@Resource(name = "documentServiceImpl")
	private DocumentService documentService;
	/**
	 * 跳转到顾客信息列表页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexGoto(Document document,Pageable pageable,ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("page", documentService.findDocumentList(document,login.getId(),pageable));
		if (document.getName() != null) {
			model.addAttribute("name", document.getName());
		}
		return "/document/index";
	}	
	
	/**
	 * 跳转到增加顾客个人信息页面
	 * @return
	 */
	@RequestMapping(value = "/documentAdd", method = RequestMethod.GET)
	public String documentAdd(ModelMap model) {
		return "/document/add";
	}
	
	/**
	 * 跳转到编辑顾客个人信息页面
	 * @return
	 */
	@RequestMapping(value = "/documentEdit", method = RequestMethod.GET)
	public String documentEdit(Long id,ModelMap model) {
		model.addAttribute("document", documentService.find(id));
		return "/document/edit";
	}
	
	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/documentSave", method = RequestMethod.POST)
	public Boolean documentSave(Document document,ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Set<Login> logins = new HashSet<Login>();
		logins.add(login);
		document.setLogin(logins);
		documentService.save(document);
		return true;
	}
	
	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/documentDelete", method = RequestMethod.POST)
	public Boolean documentDelete(Long id,ModelMap model) {
		documentService.delete(id);
		return true;
	}
	
	/**
	 * 保存顾客信息表
	 */
	@ResponseBody
	@RequestMapping(value = "/documentUpdate", method = RequestMethod.POST)
	public Boolean documentUpdate(Document document,ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Set<Login> logins = new HashSet<Login>();
		logins.add(login);
		document.setLogin(logins);
		documentService.update(document);
		return true;
	}
	/**
	 * 保存顾客信息表
	 */
	@RequestMapping(value = "/deleteSelection", method = RequestMethod.GET)
	public String deleteSelection(String ids, Document document, Pageable pageable, ModelMap model) {
		String[] idList = ids.split(",");
		Long[] id = new Long[idList.length];
		for (int i = 0; i < idList.length; i++) {
			id[i] = Long.valueOf(idList[i]);
		}
		documentService.delete(id);
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		model.addAttribute("page", documentService.findDocumentList(document, login.getId(), pageable));
		if (document.getName() != null) {
			model.addAttribute("name", document.getName());
		}
		return "/document/index";
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView export(Document document, Pageable pageable, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Page<Document> pageDocument = documentService.findDocumentList(document, login.getId(), pageable);
		List<Document> documents = pageDocument.getContent();
		try {
			String filename = "操作文档表.xls";
			// 设置标题
			String[] header = { "文档名称", "下载的地址",  "修改时间", "修改人", "创建时间", "创建人", "备注"};
			// 设置单元格取值
			String[] properties = { "name", "loadAddress","modifyDate", "modifyName",
					"createDate", "createName", "remark"};
			return new ModelAndView(new ExcelView(filename, null, properties, header, null, null, documents, null),
					model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
