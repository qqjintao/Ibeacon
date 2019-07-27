package com.ibeacon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibeacon.ExcelView;
import com.ibeacon.Pageable;
import com.ibeacon.entity.DataDictionary;
import com.ibeacon.entity.Ibeacon;
import com.ibeacon.service.DataDictionaryService;

import net.sf.json.JSONArray;

@Controller("DataDictionaryController")
@RequestMapping("/dataDictionary")
public class DataDictionaryController extends BaseController {

	@Resource(name = "dataDictionaryServiceImpl")
	private DataDictionaryService dataDictionaryService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexGoto() {
		return "/document/data";
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView export(Ibeacon ibeacon, Pageable pageable, String oldManName, ModelMap model) {
		List<DataDictionary> date = dataDictionaryService.findList();
		try {
			String filename = "数据字典信息表.xls";
			// 设置标题
			String[] header = {"id", "编号", "名称", "描述", "父节点id" };
			// 设置单元格取值
			String[] properties = {"id","code", "name", "details", "parentId" };
			return new ModelAndView(new ExcelView(filename, null, properties, header, null, null, date, null), model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/onChange", method = RequestMethod.POST)
	public JSONArray onChange(String province,String city, ModelMap model) {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("provinces",dataDictionaryService.findDataDictionaryList(-1L));
		if(province!=null){
			Long cityParentId=dataDictionaryService.findIdByName(province);
			data.put("citys", dataDictionaryService.findDataDictionaryList(cityParentId));
			model.addAttribute("citys",dataDictionaryService.findDataDictionaryList(cityParentId));
		}
		if(city!=null){
			Long districtParentId=dataDictionaryService.findIdByName(city);
			data.put("districts", dataDictionaryService.findDataDictionaryList(districtParentId));
		}	
		JSONArray jsonArray2 = JSONArray.fromObject( data );
		return jsonArray2;
	}
}
