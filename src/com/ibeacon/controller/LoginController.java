package com.ibeacon.controller;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
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
import com.ibeacon.SendCode;
import com.ibeacon.entity.Login;
import com.ibeacon.service.CaptchaService;
import com.ibeacon.service.LoginService;
import com.ibeacon.service.RSAService;

@Controller("LoginController")
@RequestMapping("/login")
public class LoginController extends BaseController{
	@Resource(name = "loginServiceImpl")
	private LoginService loginService;
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	/**
	 * 跳转到登录页面
	 * @param login
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGoto(ModelMap model) {
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/login/login";
	}
	
	/**
	 * 跳转到后台主页
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap model) {
		return "/login/index";
	}
	
	/**
	 * 跳转到后台主页
	 */
	@RequestMapping(value = "/out", method = RequestMethod.GET)
	public String out(ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		request.getSession().removeAttribute("login");
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/login/login";
	}
	/**
	 * 登录验证 1、验证码传输有误。2、登录成功。3、账号密码错误。4、验证码输入有误。5、跳转到404页面
	 * @param captchaId
	 * @param captcha
	 * @param userName
	 * @param password
	 * @param type
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Integer loginUsername(String captchaId, String captcha,String userName,String password,HttpServletRequest request,String type,ModelMap model) {
		password = rsaService.decryptParameter("password", request);
		rsaService.removePrivateKey(request);
		if(captchaId==null||captcha==null||captchaId.equals("")||captcha.equals("")){
			model.addAttribute("captchaId", captchaId);
			return 1;
		}
		if (captchaService.isValid(captchaId, captcha)) {
			Login login=loginService.findByUsername(userName,DigestUtils.md5Hex(password).toString(),type);
			if(login!=null){
				RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
				if (requestAttributes != null) {
					request = ((ServletRequestAttributes) requestAttributes).getRequest();
					request.getSession().setAttribute("login", login);
					return 2;
				}else{
					return 5;
				}
			}else{
				model.addAttribute("captchaId", captchaId);
				return 3;
			}
		} else {
			model.addAttribute("captchaId", captchaId);
			return 4;
		}
	}
	
	/**
	 * 跳转到注册页面
	 * @param register
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerGoto(ModelMap model) {
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/login/register";
	}
	
	/**
	 * 保存注册表 1、验证码传输有误。2、注册成功。3、手机验证码错误。4、验证码输入有误。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/registerSave", method = RequestMethod.POST)
	public Integer registerSave(String phoneCode,String captchaId, String captcha,Login login,HttpServletRequest request,ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		request = ((ServletRequestAttributes) requestAttributes).getRequest();
		String mobileCode = (String) request.getSession().getAttribute("mobileCode");
		if(!phoneCode.equalsIgnoreCase(mobileCode)){
			return 3;
		}
		String password = rsaService.decryptParameter("passWord", request);
		rsaService.removePrivateKey(request);
		login.setPassWord(DigestUtils.md5Hex(password).toString());
		if(login.getType().equals("1")){
			login.setIsLocked("N");
		}else{
			login.setIsLocked("Y");			
		}
		if(captchaId==null||captcha==null||captchaId.equals("")||captcha.equals("")){
			model.addAttribute("captchaId", captchaId);
			return 1;
		}
		if (captchaService.isValid(captchaId, captcha)) {
			loginService.save(login);
			return 2;
		} else {
			model.addAttribute("captchaId", captchaId);
			return 4;
		}
	}
	
	/**
	 * 检查用户名是否存在  存在返回 false 不存在返回 true
	 */ 
	@ResponseBody
	@RequestMapping(value = "/check_username", method = RequestMethod.POST)
	public boolean checkUsername(String username,String type) {
		if (loginService.usernameExists(username,type)>0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 跳转到忘记密码页面
	 * @param register
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public String forgotPasswordGoto(ModelMap model) {
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/login/forgotPassword";
	}
	
	/**
	 * 修改密码  1、验证码传输有误。2、修改成功。3、修改失败。4、验证码输入有误。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public Integer updatePassword(String phoneCode,String captchaId, String captcha,String mobilePhone,String passWord,String type,HttpServletRequest request,ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		request = ((ServletRequestAttributes) requestAttributes).getRequest();
		String mobileCode = (String) request.getSession().getAttribute("mobileCode");
		if(!phoneCode.equalsIgnoreCase(mobileCode)){
			return 5;
		}
		String password = rsaService.decryptParameter("passWord", request);
		rsaService.removePrivateKey(request);
		if(captchaId==null||captcha==null||captchaId.equals("")||captcha.equals("")){
			model.addAttribute("captchaId", captchaId);
			return 1;
		}
		if (captchaService.isValid(captchaId, captcha)) {
			if(loginService.updatePassword(mobilePhone, DigestUtils.md5Hex(password).toString(), type)){
				return 2;
			}else{
				model.addAttribute("captchaId", captchaId);
				return 3;
			}
		} else {
			model.addAttribute("captchaId", captchaId);
			return 4;
		}
	}
	
	/**
	 * 跳转到登录信息表页面
	 * @param login
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loginTable", method = RequestMethod.GET)
	public String loginTableGoto(ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Login login = (Login) request.getSession().getAttribute("login");
			if(login!=null){
				login.setPassWord("******");
				model.addAttribute("login", login);
				return "/login/loginTable";
			}else{
				return "";
			}	
		}else{
			return "";//跳转到404页面
		}	
	}
	
	/**
	 * 更新用户登录信息表 1、未登录 2、旧密码错误 0、修改成功
	 */ 
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/updateLogin", method = RequestMethod.POST)
	public Integer updateLogin(HttpServletRequest request,Login login,ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login oldLogin = (Login) request.getSession().getAttribute("login");
		String oldPasswordLogin=loginService.findPasswordById(oldLogin.getId());
		if(oldLogin==null){
			return 1;
		}
		String oldPassword = rsaService.decryptParameter("oldPassword", request);
		if(!oldPasswordLogin.equals(DigestUtils.md5Hex(oldPassword).toString())){
			return 2;
		}
		String newPassword = rsaService.decryptParameter("newPassword", request);
		rsaService.removePrivateKey(request);		
		oldLogin.setMailBox(login.getMailBox());
		oldLogin.setMobilePhone(login.getMobilePhone());
		oldLogin.setNickName(login.getNickName());
		oldLogin.setPassWord(DigestUtils.md5Hex(newPassword).toString());
		loginService.update(oldLogin);
		request.getSession().setAttribute("login", oldLogin);
		oldLogin.setPassWord("******");
		model.addAttribute("login", oldLogin);
		return 0;
	}
	
	@RequestMapping(value = "/deleteSelection", method = RequestMethod.GET)
	public String deleteSelection(String ids, Login login, Pageable pageable, ModelMap model) {
		String[] idList = ids.split(",");
		Long[] id = new Long[idList.length];
		for (int i = 0; i < idList.length; i++) {
			id[i] = Long.valueOf(idList[i]);
		}
		loginService.delete(id);
		model.addAttribute("page", loginService.findLoginList(login, pageable));
		if (login != null) {
			model.addAttribute("logins", login);
		}
		return "/login/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "/loginDelete", method = RequestMethod.POST)
	public Boolean loginDelete(Long id, ModelMap model) {
		loginService.delete(id);
		return true;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listGoto(Login login, Pageable pageable, ModelMap model) {
		model.addAttribute("page", loginService.findLoginList(login, pageable));
		if (login != null) {
			model.addAttribute("logins", login);
		}
		return "/login/list";
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView export(Login login, Pageable pageable, ModelMap model) {
		Page<Login> loginList = loginService.findLoginList(login, pageable);
		List<Login> data = loginList.getContent();
		try {
			String filename = "登录信息表.xls";
			// 设置标题
			String[] header = { "手机号码", "邮箱", "昵称", "是否锁定", "类型" };
			// 设置单元格取值
			String[] properties = { "mobilePhone", "mailBox", "nickName", "isLocked", "type" };
			return new ModelAndView(new ExcelView(filename, null, properties, header, null, null, data, null),
					model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/listUpdata", method = RequestMethod.GET)
	public String listUpdata(Login login,Pageable pageable, ModelMap model) {
		Login old=loginService.find(login.getId());
		old.setMobilePhone(login.getMobilePhone()!=null?login.getMobilePhone():null);
		old.setMailBox(login.getMailBox()!=null?login.getMailBox():null);
		old.setNickName(login.getNickName()!=null?login.getNickName():null);
		old.setIsLocked(login.getIsLocked()!=null?login.getIsLocked():null);
		loginService.update(old);
		model.addAttribute("page", loginService.findLoginList(null, pageable));
		return "/login/list";
	}
	
	@RequestMapping(value = "/loginEdit", method = RequestMethod.GET)
	public String loginEdit(Long id, ModelMap model) {
		model.addAttribute("logins", loginService.find(id));
		return "/login/edit";
	}
	
	@ResponseBody
	@RequestMapping(value = "/mobileCode", method = RequestMethod.POST)
	public Boolean mobileCode(String phone, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		SendCode sendCode=new SendCode();
		try {
			String mobileCode=sendCode.getCode(phone);
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			request.getSession().setAttribute("mobileCode", mobileCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
