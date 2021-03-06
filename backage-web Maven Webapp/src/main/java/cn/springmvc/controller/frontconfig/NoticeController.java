
package cn.springmvc.controller.frontconfig; 

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.model.Admin;
import cn.springmvc.model.PlatformAnnouncementEntity;
import cn.springmvc.service.PlatformAnnouncementService;
import cn.springmvc.util.HttpSessionUtil;
import cn.springmvc.util.LoadUrlUtil;


import product_p2p.kit.HttpIp.AddressUtils;
import product_p2p.kit.optrecord.InsertAdminLogEntity;
import product_p2p.kit.pageselect.PageEntity;

/**
 * 
* @author 杨翰林
* @Description: 网站公告控制层 
* @since 
* @date 2016-4-7 上午10:01:20
 */
@Controller
@RequestMapping("/frontconfig")
public class NoticeController {
	
	@Autowired
	private PlatformAnnouncementService announcementService;
	
	//日志信息
	Log logger = LogFactory.getLog(NoticeController.class);
	
	/**
	 * 
	* list查询网站公告列表 
	* TODO查询网站公告列表
	* @author 杨翰林  
	* * @Title: list 
	* @Description: 查询网站公告列表 
	* @param @param req
	* @param @param pageNum
	* @param @param pageSize
	* @param @param title
	* @param @param statu
	* @param @return 设定文件 
	* @return PageEntity 返回类型 
	* @date 2016-4-7 下午2:45:47
	* @throws
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageEntity list(Map<String, Object> req, HttpServletRequest request) {
		
		//日志信息
		logger.info("查询网站公告列表");
		
		PageEntity pager = new PageEntity();
		
		String title = request.getParameter("title");
		String statu = request.getParameter("statu");
		int start = Integer.valueOf(request.getParameter("start"));
		int length = Integer.valueOf(request.getParameter("length"));
		req.put("title", title);
		req.put("statu", statu);
		pager.setPageNum(start / length + 1);
		pager.setPageSize(length);
		pager.setMap(req);
		
		logger.info("开始查询网站公告列表......req=" + req);
		List<PlatformAnnouncementEntity>  announceList = announcementService.selectPlatformList(pager);
		logger.info("结束查询网站公告......接口返回:result=" + announceList);
		pager.setResults(announceList);
		
		return pager;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public int addNotice(HttpServletRequest request) {
		
		//日志信息
		logger.info("添加网站公告");
		
		PlatformAnnouncementEntity announce = new PlatformAnnouncementEntity();
		HttpSession session = HttpSessionUtil.getSession(request);
		InsertAdminLogEntity entity = new InsertAdminLogEntity();
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		if (title != null && title != "") {
			announce.setTitle(title);
		}
		if (content != null && content != "") {
			announce.setContent(content);
		}
			announce.setOptId(1);
			
		String [] sIpInfo = new String[6];
		Admin userInfo = (Admin)session.getAttribute("LoginPerson");
		if (userInfo != null) {
			entity.setiAdminId(userInfo.getId());
		}
		entity.setlOptId(50301);
		entity.setlModuleId(503);
		entity.setsDetail("");
		entity.setsIp(AddressUtils.GetRemoteIpAddr(request, sIpInfo));
		entity.setsMac(null);
		entity.setsUrl(LoadUrlUtil.getFullURL(request));
		
		logger.info("开始添加网站公告......req：title：" + title);
		int num = announcementService.insertPlatform(announce, entity, sIpInfo);
		logger.info("结束添加网站公告......接口返回result：" + num);
		
		return num;
	}
	
	/**
	 * 
	* update修改网站公告 
	* TODO修改网站公告
	* @author 杨翰林  
	* * @Title: update 
	* @Description: 修改网站公告 
	* @param @param title
	* @param @param content
	* @param @param optId
	* @param @param annoceId
	* @param @return 设定文件 
	* @return int 返回类型 
	* @date 2016-4-8 下午3:14:35
	* @throws
	 */
	@RequestMapping("/update")
	@ResponseBody
	public int update(HttpServletRequest request) {
		
		//日志信息
		logger.info("修改网站公告");
		
		PlatformAnnouncementEntity announce = new PlatformAnnouncementEntity();
		HttpSession session = HttpSessionUtil.getSession(request);
		InsertAdminLogEntity entity = new InsertAdminLogEntity();
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String annoceId = request.getParameter("annoceId");
		if (title != null && title != "") {
			announce.setTitle(title);
		}
		if (content != null && content != "") {
			announce.setContent(content);
		}
		if (annoceId != null && annoceId != "") {
			announce.setId(Long.valueOf(annoceId));
		}
		
		String [] sIpInfo = new String[5];
		Admin userInfo = (Admin)session.getAttribute("LoginPerson");
		if (userInfo != null) {
			entity.setiAdminId(userInfo.getId());
		}
		entity.setlOptId(50302);
		entity.setlModuleId(503);
		entity.setsDetail("");
		entity.setsIp(AddressUtils.GetRemoteIpAddr(request, sIpInfo));
		entity.setsMac(null);
		entity.setsUrl(LoadUrlUtil.getFullURL(request));
		
		logger.info("开始修改网站公告......req：title：" + title);
		int num = announcementService.updatePlatform(announce, entity, sIpInfo);
		logger.info("结束修改网站公告......接口返回：result：" + title);
		
		return num;
	}
	
	/**
	 * 
	* ofOrOpenDept启用停用网站公告 
	* TODO启用停用网站公告
	* @author 杨翰林  
	* * @Title: ofOrOpenDept 
	* @Description: 启用停用网站公告 
	* @param @param annoceId
	* @param @param deptStatu
	* @param @return 设定文件 
	* @return int 返回类型 
	* @date 2016-4-8 下午3:19:11
	* @throws
	 */
	@RequestMapping("ofOrOpenNotice")
	@ResponseBody
	public int ofOrOpenNotice(HttpServletRequest request) {
		
		PlatformAnnouncementEntity announce = new PlatformAnnouncementEntity();
		HttpSession session = HttpSessionUtil.getSession(request);
		InsertAdminLogEntity entity = new InsertAdminLogEntity();
		
		String annoceId = request.getParameter("annoceId");
		String statu = request.getParameter("statu");
		if (annoceId != null && annoceId != "") {
			announce.setId(Long.valueOf(annoceId));
		}
		if (statu != null && statu != "") {
			announce.setStatu(Integer.valueOf(statu));
		}
		
		String [] sIpInfo = new String[5];
		Admin userInfo = (Admin)session.getAttribute("LoginPerson");
		if (userInfo != null) {
			entity.setiAdminId(userInfo.getId());
		}
		entity.setlOptId(50303);
		entity.setlModuleId(503);
		entity.setsDetail("");
		entity.setsIp(AddressUtils.GetRemoteIpAddr(request, sIpInfo));
		entity.setsMac(null);
		entity.setsUrl(LoadUrlUtil.getFullURL(request));
		
		int num = announcementService.updatePlatformStatuByID(announce, entity, sIpInfo);
		return num;
	}
	
	/**
	 * 
	* query4update为修改查询单个实体
	* TODO为修改查询单个实体
	* @author 杨翰林  
	* * @Title: query4update 
	* @Description: 为修改查询单个实体 
	* @param @param noticeId
	* @param @return 设定文件 
	* @return PlatformAnnouncementEntity 返回类型 
	* @date 2016-4-8 下午3:15:05
	* @throws
	 */
	@RequestMapping("/query4update")
	@ResponseBody
	public PlatformAnnouncementEntity query4update (HttpServletRequest request){
		
		String noticeId = request.getParameter("noticeId");
		PlatformAnnouncementEntity annonce = announcementService.
				selectPlatformByID(Integer.valueOf(noticeId));
		return annonce;
	}
}

