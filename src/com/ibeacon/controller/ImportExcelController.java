package com.ibeacon.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.ibeacon.entity.Acceptor;
import com.ibeacon.entity.DataDictionary;
import com.ibeacon.entity.Document;
import com.ibeacon.entity.Ibeacon;
import com.ibeacon.entity.Info;
import com.ibeacon.entity.Login;
import com.ibeacon.entity.OldMan;
import com.ibeacon.service.AcceptorService;
import com.ibeacon.service.DataDictionaryService;
import com.ibeacon.service.DocumentService;
import com.ibeacon.service.IbeaconService;
import com.ibeacon.service.InfoService;
import com.ibeacon.service.LoginService;
import com.ibeacon.service.OldManService;
import com.ibeacon.util.ExcelParser;
import com.ibeacon.util.ExcelType;

@Controller("ImportExcelController")
@RequestMapping("/import")
public class ImportExcelController extends BaseController {

	@Resource(name = "loginServiceImpl")
	private LoginService loginService;
	@Resource(name = "infoServiceImpl")
	private InfoService infoService;
	@Resource(name = "oldManServiceImpl")
	private OldManService oldManService;
	@Resource(name = "ibeaconServiceImpl")
	private IbeaconService ibeaconService;
	@Resource(name = "acceptorServiceImpl")
	private AcceptorService acceptorService;
	@Resource(name = "documentServiceImpl")
	private DocumentService documentService;
	@Resource(name = "dataDictionaryServiceImpl")
	private DataDictionaryService dataDictionaryService;
	
	@RequestMapping(value = "/uploadExecl", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public void photoSave(MultipartFile file, String downtype, HttpServletResponse response, ModelMap model) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		Login login = (Login) request.getSession().getAttribute("login");
		Set<Login> logins = new HashSet<Login>();
		logins.add(login);
		@SuppressWarnings("unused")
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 设置单元格的校验
		ExcelType[] types = setExcel(downtype);
		// long beginTime = System.currentTimeMillis();
		ExcelParser parser = new ExcelParser(types);
		try {
			parser.run(file.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		List<Object> errorresults = parser.getErrorresults();
		StringBuffer error = new StringBuffer();
		for (int i = 0; i < errorresults.size(); i++) {
			error.append("导入数据有误请检查：错误数据位置为：" + (String) errorresults.get(i) + "\r\n");
		}
		if (error != null && StringUtils.isNotBlank(error)) {
			System.out.println(error);
		}
		// 解析出来的数据
		List<List<Object>> results = parser.getResults();
		try {
			// 保存解析出来的数据
			if ("info".equals(downtype)) {
				if(results!=null&&results.size()>0){
					for (int i = 0; i < results.size(); i++) {
						@SuppressWarnings({ "unchecked", "rawtypes" })
						List<Object> list = (List) results.get(i);
						Info info=new Info();
						info.setIdCardName(list.get(0)!=null?list.get(0).toString():"");
						info.setEnglishName(list.get(1)!=null?list.get(1).toString():"");
						info.setAge(list.get(2)!=null?list.get(2).toString():"");
						info.setSex(list.get(3)!=null?list.get(3).toString():"");
						info.setBirthplace(list.get(4)!=null?list.get(4).toString():"");
						info.setDateOfBirth(list.get(5)!=null?list.get(5).toString():"");
						info.setIdCard(list.get(6)!=null?list.get(6).toString():"");
						info.setNation(list.get(7)!=null?list.get(7).toString():"");
						info.setPoliticsStatus(list.get(8)!=null?list.get(8).toString():"");
						info.setMaritalStatus(list.get(9)!=null?list.get(9).toString():"");
						info.setProvince(list.get(10)!=null?list.get(10).toString():"");
						info.setCity(list.get(11)!=null?list.get(11).toString():"");
						info.setDistrict(list.get(12)!=null?list.get(12).toString():"");
						info.setStreetAddress(list.get(13)!=null?list.get(13).toString():"");
						info.setDomicileProvince(list.get(14)!=null?list.get(14).toString():"");
						info.setDomicileCity(list.get(15)!=null?list.get(15).toString():"");
						info.setDomicileDistrict(list.get(16)!=null?list.get(16).toString():"");
						info.setDomicile(list.get(17)!=null?list.get(17).toString():"");
						info.setMailbox(list.get(18)!=null?list.get(18).toString():"");
						info.setPhone(list.get(19)!=null?list.get(19).toString():"");
						info.setHomePhone(list.get(20)!=null?list.get(20).toString():"");
						info.setOfficePhone(list.get(21)!=null?list.get(21).toString():"");
						info.setRemark(list.get(22)!=null?list.get(22).toString():"");
						info.setHeadPortrait(list.get(23)!=null?list.get(23).toString():"");
						info.setLogin(logins);
						infoService.save(info);
					}
				}else{
					throw new RuntimeException("导入的数据为空，请重新导入！");
				}
			} else if ("oldMan".equals(downtype)) {
				if(results!=null&&results.size()>0){
					for (int i = 0; i < results.size(); i++) {
						@SuppressWarnings({ "unchecked", "rawtypes" })
						List<Object> list = (List) results.get(i);
						OldMan oldMan=new OldMan();
						oldMan.setName(list.get(0)!=null?list.get(0).toString():"");
						oldMan.setEnglishName(list.get(1)!=null?list.get(1).toString():"");
						oldMan.setAge(list.get(2)!=null?list.get(2).toString():"");
						oldMan.setSex(list.get(3)!=null?list.get(3).toString():"");
						oldMan.setNationality(list.get(4)!=null?list.get(4).toString():"");
						oldMan.setNation(list.get(5)!=null?list.get(5).toString():"");
						oldMan.setPoliticsStatus(list.get(6)!=null?list.get(6).toString():"");
						oldMan.setIdentityCard(list.get(7)!=null?list.get(7).toString():"");
						oldMan.setPhysical(list.get(8)!=null?list.get(8).toString():"");
						oldMan.setPhone(list.get(9)!=null?list.get(9).toString():"");
						oldMan.setRelation(list.get(10)!=null?list.get(10).toString():"");
						oldMan.setProvince(list.get(11)!=null?list.get(11).toString():"");
						oldMan.setCity(list.get(12)!=null?list.get(12).toString():"");
						oldMan.setDistrict(list.get(13)!=null?list.get(13).toString():"");
						oldMan.setStreetAddress(list.get(14)!=null?list.get(14).toString():"");
						oldMan.setDomicileProvince(list.get(15)!=null?list.get(15).toString():"");
						oldMan.setDomicileCity(list.get(16)!=null?list.get(16).toString():"");
						oldMan.setDomicileDistrict(list.get(17)!=null?list.get(17).toString():"");
						oldMan.setDomicile(list.get(18)!=null?list.get(18).toString():"");
						oldMan.setAccount(list.get(19)!=null?list.get(19).toString():"");
						oldMan.setDoctor(list.get(20)!=null?list.get(20).toString():"");
						oldMan.setDoctorPhone(list.get(21)!=null?list.get(21).toString():"");
						oldMan.setNurse(list.get(22)!=null?list.get(22).toString():"");
						oldMan.setNursePhone(list.get(23)!=null?list.get(23).toString():"");
						oldMan.setEmergencyName(list.get(24)!=null?list.get(24).toString():"");
						oldMan.setEmergencyCall(list.get(25)!=null?list.get(25).toString():"");
						oldMan.setRemark(list.get(26)!=null?list.get(26).toString():"");
						oldMan.setHeadPortrait(list.get(27)!=null?list.get(27).toString():"");
						oldMan.setLogin(logins);
						oldManService.save(oldMan);
					}
				}else{
					throw new RuntimeException("导入的数据为空，请重新导入！");
				}
			} else if ("ibeacon".equals(downtype)) {
				if(results!=null&&results.size()>0){
					for (int i = 0; i < results.size(); i++) {
						@SuppressWarnings({ "unchecked", "rawtypes" })
						List<Object> list = (List) results.get(i);
						Ibeacon ibeacon=new Ibeacon();
						ibeacon.setLogin(logins);
						ibeacon.setUUID(list.get(0)!=null?list.get(0).toString():"");
						ibeacon.setMajor(list.get(1)!=null?list.get(1).toString():"");
						ibeacon.setMinor(list.get(2)!=null?list.get(2).toString():"");
						ibeacon.setElectric(list.get(3)!=null?list.get(3).toString():"");
						ibeacon.setMode(list.get(4)!=null?list.get(4).toString():"");
						if(list.get(5)!=null){
							OldMan oldMan=oldManService.find(Long.parseLong(list.get(5).toString()));
							Set<OldMan> oldMans = new HashSet<OldMan>();
							oldMans.add(oldMan);
							ibeacon.setOldMan(oldMans);
						}
						ibeacon.setRemark(list.get(11)!=null?list.get(11).toString():"");
						ibeaconService.save(ibeacon);
					}
				}else{
					throw new RuntimeException("导入的数据为空，请重新导入！");
				}
			} else if ("acceptor".equals(downtype)) {
				if(results!=null&&results.size()>0){
					for (int i = 0; i < results.size(); i++) {
						@SuppressWarnings({ "unchecked", "rawtypes" })
						List<Object> list = (List) results.get(i);
						Acceptor acceptor=new Acceptor();
						acceptor.setName(list.get(0)!=null?list.get(0).toString():"");
						acceptor.setAddress(list.get(1)!=null?list.get(1).toString():"");
						acceptor.setAlarmTime(list.get(2)!=null?list.get(2).toString():"");
						acceptor.setAlarmVoice(list.get(3)!=null?list.get(3).toString():"");
						acceptor.setIsSend(list.get(4)!=null?list.get(4).toString():"");
						acceptor.setPhone(list.get(5)!=null?list.get(5).toString():"");
						acceptor.setRemark(list.get(10)!=null?list.get(10).toString():"");
						acceptor.setLogin(logins);
						acceptorService.save(acceptor);
					}
				}else{
					throw new RuntimeException("导入的数据为空，请重新导入！");
				}
			}else if ("document".equals(downtype)) {
				if(results!=null&&results.size()>0){
					for (int i = 0; i < results.size(); i++) {
						@SuppressWarnings({ "unchecked", "rawtypes" })
						List<Object> list = (List) results.get(i);
						Document document=new Document();
						document.setName(list.get(0)!=null?list.get(0).toString():"");
						document.setLoadAddress(list.get(1)!=null?list.get(1).toString():"");
						document.setRemark(list.get(6)!=null?list.get(6).toString():"");
						document.setLogin(logins);
						documentService.save(document);
					}
				}else{
					throw new RuntimeException("导入的数据为空，请重新导入！");
				}
			}else if ("dataDictionary".equals(downtype)) {
				if(results!=null&&results.size()>0){
					for (int i = 0; i < results.size(); i++) {
						@SuppressWarnings({ "unchecked", "rawtypes" })
						List<Object> list = (List) results.get(i);
						DataDictionary dataDictionary=new DataDictionary();
						if(list.get(0)!=null){
							dataDictionary.setId(Long.parseLong(list.get(0).toString()));
						}
						dataDictionary.setCreateDate(new Date());
						dataDictionary.setCreateName(login.getNickName());
						dataDictionary.setModifyDate(new Date());
						dataDictionary.setModifyName(login.getNickName());
						dataDictionary.setCode(list.get(1)!=null?list.get(1).toString():"");
						dataDictionary.setName(list.get(2)!=null?list.get(2).toString():"");
						dataDictionary.setDetails(list.get(3)!=null?list.get(3).toString():"");
						if(list.get(4)!=null){
							dataDictionary.setParentId(Long.parseLong(list.get(4).toString()));
						}
						dataDictionaryService.update(dataDictionary);
					}
				}else{
					throw new RuntimeException("导入的数据为空，请重新导入！");
				}
			}
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}

	}

	private ExcelType[] setExcel(String type) {
		ExcelType[] types = null;
		if ("info".equals(type)) {
			types = new ExcelType[] { // 验证excel数据是否正确
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 1
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 2
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 3
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 4
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 5
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 6
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 7
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 8
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 9
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 10
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 1
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 2
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 3
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 4
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 5
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 6
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 7
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 8
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 9
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 10
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 1
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 2
					new ExcelType(ExcelType.CELL_TYPE_STRING, false),// 3
					new ExcelType(ExcelType.CELL_TYPE_STRING, false),// 4
			};
		} else if ("oldMan".equals(type)) {
			types = new ExcelType[] { // 验证excel数据是否正确
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 1
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 2
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 3
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 4
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 5
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 6
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 7
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 8
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 9
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 10
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 1
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 2
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 3
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 4
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 5
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 6
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 7
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 8
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 9
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 10
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 1
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 2
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 3
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 4
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 5
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 6
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 7
					new ExcelType(ExcelType.CELL_TYPE_STRING, false),// 8
			};
		}else if("ibeacon".equals(type)){
			types = new ExcelType[] { // 验证excel数据是否正确
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 1
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 2
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 3
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 4
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 5
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 6
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 7
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 8
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 9
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 10
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 1
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 2
			};
		}else if ("acceptor".equals(type)) {
			types = new ExcelType[] { // 验证excel数据是否正确
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 1
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 2
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 3
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 4
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 5
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 6
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 7
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 8
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 9
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 10
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 1
			};
		} else if ("document".equals(type)) {
			types = new ExcelType[] { // 验证excel数据是否正确
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 1
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 2
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 3
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 4
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 5
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 6
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 7
			};
		}else if ("dataDictionary".equals(type)) {
			types = new ExcelType[] { // 验证excel数据是否正确
					new ExcelType(ExcelType.CELL_TYPE_STRING, true), // 1
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 2
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 3
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 4
					new ExcelType(ExcelType.CELL_TYPE_STRING, false), // 5
			};
		}
		return types;
	}
}
