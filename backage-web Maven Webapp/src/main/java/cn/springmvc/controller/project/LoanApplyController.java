package cn.springmvc.controller.project; 

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import product_p2p.kit.HttpIp.AddressUtils;
import product_p2p.kit.Upload.FtpClientUtil;
import product_p2p.kit.datatrans.IntegerAndString;
import product_p2p.kit.dbkey.DbKeyUtil;
import product_p2p.kit.optrecord.InsertAdminLogEntity;
import product_p2p.kit.pageselect.PageEntity;
import product_p2p.kit.pageselect.PageUtil;
import cn.membermng.model.ComplanyInfoEntity;
import cn.membermng.model.MemberAll;
import cn.membermng.model.MemberDetaileInfo;
import cn.springmvc.model.Admin;
import cn.springmvc.model.ProjectAppAttachmentEntity;
import cn.springmvc.model.ProjectAppRecordEntity;
import cn.springmvc.model.ProjectBaseInfoEntity;
import cn.springmvc.model.ProjectInvestRedPackageEntity;
import cn.springmvc.model.ProjectPurposeEntity;
import cn.springmvc.model.SystemSetEntity;
import cn.springmvc.service.CertificationAuditService;
import cn.springmvc.service.IMemberManangerService;
import cn.springmvc.service.MemberInfoService;
import cn.springmvc.service.ProjectAppRecordService;
import cn.springmvc.service.ProjectAuitService;
import cn.springmvc.service.ProjectBaseInfoService;
import cn.springmvc.service.ProjectPurposeService;
import cn.springmvc.service.SystemSetService;
import cn.springmvc.util.LoadUrlUtil;

import com.alibaba.fastjson.JSON;

/** 
* @author 唐国峰
* @Description: 借款申请控制器 
* @date 2016-5-4 上午10:18:41 
*/
@Controller
@RequestMapping("/project")
public class LoanApplyController {
	 
	@Resource(name="projectAppRecordServiceImpl")
	private ProjectAppRecordService projectAppRecordService;
	
	@Resource(name="projectPurposeServiceImpl")
	private ProjectPurposeService projectPurposeService;
	
	@Resource(name="projectBaseInfoServiceImpl")
	private ProjectBaseInfoService projectBaseInfoService;
	
	@Resource(name="projectAuditServiceImpl")
	private ProjectAuitService projectAuitService;
	
	@Resource(name="certificationAuditServiceImpl")
	private CertificationAuditService certificationAuditService;
	
	@Autowired
	private IMemberManangerService iMemberManangerService;
	
	@Resource(name="memberInfoServicrImpl")
	private MemberInfoService memberInfoService;
	
	@Resource(name="systemSetServiceImpl")
	private SystemSetService systemSetService;
	/** 
	 * @author 唐国峰 
	 * @Description: 跳转到借款意向列表查询页面
	 * @param req
	 * @return String  
	 * @date 2016-5-4 上午10:19:46
	 * @throws 
	 */
	@RequestMapping("/toLoanApplyList")
	public String toLoanApplyList(HttpServletRequest req){
		return "project/loan_intention_1";
	}
	
	/** 
	 * @author 唐国峰 
	 * @Description: 跳转到直接意向借款页面
	 * @param req
	 * @return String  
	 * @date 2016-5-4 下午3:48:04
	 * @throws 
	 */
	@RequestMapping("/toLoanApplyPg")
	public String toLoanApplyPg(HttpServletRequest req){
		List<ProjectBaseInfoEntity> proTypes = projectBaseInfoService.selectProjectBaseInfoCombox();
		req.setAttribute("proTypes", proTypes);
		return "project/loan_intention_2";
	}
	
	/** 
	 * @author 唐国峰 
	 * @Description: 获取借款意向分页数据
	 * @param req
	 * @return PageEntity  
	 * @date 2016-5-4 下午2:41:26
	 * @throws 
	 */
	@RequestMapping("/getLoanPurposeData")
	@ResponseBody
	public PageEntity getLoanPurposeData(HttpServletRequest req){
		HttpSession session = req.getSession();
		Admin admin = (Admin)session.getAttribute("LoginPerson");
		//获取解密后参数
		int start = Integer.parseInt(req.getParameter("start"));
		int length = Integer.parseInt(req.getParameter("length"));
		String memberNo = req.getParameter("memberNo");
		String logname = req.getParameter("logname");
		String personalPhone = req.getParameter("personalPhone");
		String repayWay = req.getParameter("repayWay");
		String sType = req.getParameter("sType");
		//设置查询参数
		PageEntity pager = new PageEntity();
		Map<String,Object> param=new HashMap<String,Object>();
		String skey = DbKeyUtil.GetDbCodeKey();
		param.put("skey", skey);
		param.put("sType", sType);
		param.put("memberNo", memberNo);
		param.put("logname", logname);
		param.put("personalPhone", personalPhone);
		param.put("repayWay", repayWay);
		param.put("adminid", admin.getId());//当前登录者id
		//设置排序参数
   		param.put("recordDate", 1);
   		pager.setOrderKey("1");
		pager.setMap(param);
		pager.setPageNum(start/length+1);
		pager.setPageSize(length);
		projectAppRecordService.selectAllProjectPurpose(pager);
		return pager;
	}
	
	/** 
	 * @author 唐国峰 
	 * @Description: 获取全部会员分页数据
	 * @param req
	 * @return PageEntity  
	 * @date 2016-5-5 上午10:02:34
	 * @throws 
	 */
	@RequestMapping("/getAllMembers")
	@ResponseBody
	public PageEntity getAllMembers(HttpServletRequest req){
		//查询条件
		String memberNo = req.getParameter("memberNo");
		String memberName = req.getParameter("memberName");
		String personalPhone = req.getParameter("personalPhone");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("memberNo", memberNo);
		map.put("memberName", memberName);
		map.put("personalPhone", personalPhone);
		
		int start = Integer.parseInt(req.getParameter("start"));
		int length = Integer.parseInt(req.getParameter("length"));
		PageEntity pager = new PageEntity();
		pager.setPageNum(start/length+1);
		pager.setPageSize(length);
		pager.setMap(map);
		List<MemberAll> list = memberInfoService.getMemberList(pager);
		PageUtil.ObjectToPage(pager, list);
		return pager;
	}
	
	
	/** 
	 * @author 唐国峰 
	 * @Description: 添加意向借款
	 * @param req
	 * @return int  
	 * @date 2016-5-4 下午4:47:25
	 * @throws 
	 */
	@RequestMapping("/addLoanPurpose")
	@ResponseBody
	public int addLoanPurpose(HttpServletRequest req){
		//操作日志参数
		HttpSession session = req.getSession();
		Admin admin = (Admin)session.getAttribute("LoginPerson");
		//moduleID=302(借款申请管理)
		//optID=30201(添加）
		InsertAdminLogEntity logEntity = new InsertAdminLogEntity();
		String [] sIpInfo = new String[8];
		logEntity.setiAdminId(admin.getId());
		logEntity.setsDetail("");
		logEntity.setlModuleId(302);
		logEntity.setlOptId(30201);
		logEntity.setsIp(AddressUtils.GetRemoteIpAddr(req, sIpInfo));
		logEntity.setsMac(null);
		logEntity.setsUrl(LoadUrlUtil.getFullURL(req));
		//获取解密参数
		ProjectPurposeEntity entity = new ProjectPurposeEntity();
		Long projectID = Long.parseLong(req.getParameter("projectID"));
		entity.setProjectID(projectID);
		Long memberID = Long.parseLong(req.getParameter("memberID"));
		entity.setMemberID(memberID);
		Integer memberType = Integer.parseInt(req.getParameter("memberType"));
		entity.setMemberType(memberType);
		Long amount = StringToLong(req.getParameter("amount"),0);
		entity.setAmount(amount);
		String uses = req.getParameter("uses");
		entity.setUses(uses);
		String repaySource = req.getParameter("repaySource");
		entity.setRepaySource(repaySource);
		String projectDescript = req.getParameter("projectDescript");
		entity.setProjectDescript(projectDescript);
		Integer deadline = Integer.parseInt(req.getParameter("deadline"));
		entity.setDeadline(deadline);
		Integer deadlineType = Integer.parseInt(req.getParameter("deadlineType"));
		entity.setDeadlineType(deadlineType);
		Integer repayWay = Integer.parseInt(req.getParameter("repayWay"));
		entity.setRepayWay(repayWay);
		Integer yearRate = (int)StringToLong(req.getParameter("yearRate"),0);
		entity.setYearRate(yearRate);
		entity.setAdminId(admin.getId());
		
//		param.put("maxRate",StringToLong(maxRate));
		int result=0;
		result = projectPurposeService.insertProjectPurpose(entity, logEntity, sIpInfo);
		return result;
	}
	
	
	/** 
	 * @author 唐国峰 
	 * @Description: 跳转到项目分配理财顾问页面
	 * @param req
	 * @return String  
	 * @date 2016-5-4 下午6:17:07
	 * @throws 
	 */
	@RequestMapping("/toDistributionPg")
	public String toDistributionPg(HttpServletRequest req){
		Long memberID = Long.parseLong(req.getParameter("content"));
		MemberDetaileInfo member = iMemberManangerService.memberInfoById(memberID); 
		ComplanyInfoEntity company = iMemberManangerService.companyInfo(memberID); 
		if(member != null){
			req.setAttribute("memberId", member.getMemberId());
			req.setAttribute("logname", member.getMemberName());
			req.setAttribute("personalName", member.getUserName());
		}
		if(company != null){
			req.setAttribute("memberId", company.getMemberId());
			req.setAttribute("logname", company.getMemberName());
			req.setAttribute("personalName", company.getComplanyName());
		}
		return "project/pro-add/loan_intention_allocation";
	}
	
	/** 
	 * @author 唐国峰 
	 * @Description: 获取理财顾问分页数据
	 * @param req
	 * @return String  
	 * @date 2016-5-5 上午9:17:07
	 * @throws 
	 */
	@RequestMapping("/getAdviserData")
	@ResponseBody
	public PageEntity getAdviserData(HttpServletRequest req){
		//获取解密后参数
		int start = Integer.parseInt(req.getParameter("start"));
		int length = Integer.parseInt(req.getParameter("length"));
		String realName = req.getParameter("realName");
		String serviceNo = req.getParameter("serviceNo");
		String servicePhone = req.getParameter("servicePhone");
		//设置查询参数
		PageEntity pager = new PageEntity();
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("realName", realName);
		param.put("serviceNo", serviceNo);
		param.put("servicePhone", servicePhone);
		pager.setMap(param);
		pager.setPageNum(start/length+1);
		pager.setPageSize(length);
		certificationAuditService.findFinancialAdvisor(pager);
		return pager;
	}
	
	
	/** 
	 * @author 唐国峰 
	 * @Description: 分配理财顾问
	 * @param req
	 * @return int  
	 * @date 2016-5-5 上午10:38:46
	 * @throws 
	 */
	@RequestMapping("/distributeAdviser")
	@ResponseBody
	public int distributeAdviser(HttpServletRequest req){
		//操作日志参数
		HttpSession session = req.getSession();
		Admin admin = (Admin)session.getAttribute("LoginPerson");
		//moduleID=20110(个人会员分配理财顾问)
		//optID=2011001(分配理财顾问）
		InsertAdminLogEntity logEntity = new InsertAdminLogEntity();
		String [] sIpInfo = new String[8];
		logEntity.setiAdminId(admin.getId());
		logEntity.setsDetail("");
		logEntity.setlModuleId(20110);
		logEntity.setlOptId(2011001);
		logEntity.setsIp(AddressUtils.GetRemoteIpAddr(req, sIpInfo));
		logEntity.setsMac(null);
		logEntity.setsUrl(LoadUrlUtil.getFullURL(req));
		//获取解密参数
		Long advisorId = Long.parseLong(req.getParameter("advisorId"));
		Long memberID = Long.parseLong(req.getParameter("memberID"));
		
		int result=0;
		result = certificationAuditService.DistributionFinancialAdvisor(memberID,advisorId,0,1,logEntity, sIpInfo);
		return result;
	}
	
	
	/** 
	 * @author 唐国峰 
	 * @Description: 打开项目审批记录页面
	 * @param req
	 * @return String  
	 * @date 2016-5-5 下午12:43:23
	 * @throws 
	 */
	@RequestMapping("/toCheckRecordPg")
	public String toCheckRecordPg(HttpServletRequest req){
		String applyId= req.getParameter("content");
		req.setAttribute("applyId", applyId);
		return "project/pro-add/loan_exam_record";
	}
	
	
	/** 
	 * @author 唐国峰 
	 * @Description: 拒绝借款
	 * @param req
	 * @return int  
	 * @date 2016-5-5 下午1:36:29
	 * @throws 
	 */
	@RequestMapping("/refuseBorrow ")
	@ResponseBody
	public int refuseBorrow(HttpServletRequest req){
		//操作日志参数
		HttpSession session = req.getSession();
		Admin admin = (Admin)session.getAttribute("LoginPerson");
		//moduleID=20110(个人会员分配理财顾问)
		//optID=2011001(分配理财顾问）
		InsertAdminLogEntity logEntity = new InsertAdminLogEntity();
		String [] sIpInfo = new String[8];
		logEntity.setiAdminId(admin.getId());
		logEntity.setsDetail("");
		logEntity.setlModuleId(20110);
		logEntity.setlOptId(2011001);
		logEntity.setsIp(AddressUtils.GetRemoteIpAddr(req, sIpInfo));
		logEntity.setsMac(null);
		logEntity.setsDetail("");
		logEntity.setsUrl(LoadUrlUtil.getFullURL(req));
		//获取解密参数
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("dealStatu", -1);//意向处理状态 -1:打回
		String id = req.getParameter("id");
		param.put("id", id);
		
		int result=0;
		result = projectAppRecordService.updateProjectPurposeById(param, logEntity, sIpInfo);
		return result;
	}
	
	
	/** 
	 * @author 唐国峰 
	 * @Description: 打开项目借款详情页面 
	 * @param req
	 * @return String  
	 * @date 2016-5-5 下午2:06:19
	 * @throws 
	 */
//	@RequestMapping("/toBorrowDetail")
//	public String toBorrowDetail(HttpServletRequest req){
//		//查询借款信息
//		Map<String,Object> param=new HashMap<String,Object>();
//		String id = req.getParameter("id");
//		param.put("id", id);
//		ProjectPurposeEntity proPurpose = projectAppRecordService.selectProjectPurposeById(param);
//		req.setAttribute("proPurpose", proPurpose);
//		//查询项目详情
//		Long applyid = Long.parseLong(req.getParameter("applyid"));
//		ProjectAppRecordEntity proRecord = projectAuitService.selectProjectDetailByID(applyid);
//		req.setAttribute("proRecord", proRecord);
//		return "project/pro-add/loan_pro_detail";
//	}
	
	
	/** 
	 * @author 唐国峰 
	 * @Description: 拉黑借款会员
	 * @param request
	 * @return int  
	 * @date 2016-5-6 上午10:47:14
	 * @throws 
	 */
	@RequestMapping("/blockMember")
	@ResponseBody
	public int blockMember(HttpServletRequest req){
		//操作日志参数
		HttpSession session = req.getSession();
		Admin admin = (Admin)session.getAttribute("LoginPerson");
		//moduleID=302(借款申请管理)
		//optID=30201(添加）
		InsertAdminLogEntity logEntity = new InsertAdminLogEntity();
		String [] sIpInfo = new String[8];
		logEntity.setiAdminId(admin.getId());
		logEntity.setsDetail("");
		logEntity.setlModuleId(302);
		logEntity.setlOptId(30201);
		logEntity.setsIp(AddressUtils.GetRemoteIpAddr(req, sIpInfo));
		logEntity.setsMac(null);
		logEntity.setsUrl(LoadUrlUtil.getFullURL(req));
		
		Map<String,Object> param = new HashMap<String, Object>();
		String memberId = req.getParameter("memberId");
		String remark = req.getParameter("remark");
		param.put("memberId", memberId);
		param.put("remark", remark);//拉黑原因
		param.put("aid", admin.getId());//操作者id
		int iResult = iMemberManangerService.pullBlack(param,logEntity,sIpInfo);
		return iResult;
	}
	
	
	/** 
	 * @author 唐国峰 
	 * @Description: 跳转到补充资料页面
	 * @param req
	 * @return String  
	 * @date 2016-5-5 上午10:48:48
	 * @throws 
	 */
	@RequestMapping("/toAddInformationPg")
	public String toAddInformationPg(HttpServletRequest req){
		//查询借款信息
		Map<String,Object> param=new HashMap<String,Object>();
		String id = req.getParameter("content");
		String skey = DbKeyUtil.GetDbCodeKey();
		param.put("id", id); 
		param.put("skey", skey); 
		ProjectPurposeEntity proPurpose = projectAppRecordService.selectProjectPurposeById(param);
		//查询项目详情
		Long applyid = Long.parseLong(req.getParameter("start"));
		ProjectAppRecordEntity proRecord = projectAuitService.selectProjectDetailByID(applyid);
		//判断提交前还是提交后
		//还款类型与期限
		if(proRecord !=null ){
			int deadlineAfter= proRecord.getProjectBaseInfoentity().getDeadline();//提交后的
			int deadlinePre= proPurpose.getDeadline();//提交前
			if(deadlineAfter!= 0 && deadlinePre != deadlineAfter){//已经第一步保存数据了
				proPurpose.setDeadline(deadlineAfter);
				proPurpose.setDeadlineType(proRecord.getProjectBaseInfoentity().getDeadlineType());
			}
			//还款方式
			int repayWayAfter= proRecord.getProjectBaseInfoentity().getRepayWay();//提交后的
			int repayWayPre= proPurpose.getRepayWay();//提交前
			if(repayWayAfter!= 0 && repayWayPre != repayWayAfter){//已经第一步保存数据了
				proPurpose.setRepayWay(repayWayAfter);
			}
		}
		//FTP服务器地址
		String hostPath = FtpClientUtil.getFtpFilePath();
		hostPath = hostPath.substring(0, hostPath.length()-1);
		//系统配置
		SystemSetEntity sysInfo =  systemSetService.findSystemSet();
		req.setAttribute("sysInfo", sysInfo);
		req.setAttribute("proPurpose", proPurpose);
		req.setAttribute("proRecord", proRecord);
		req.setAttribute("hostPath", hostPath);
		return "project/pro-add/add_information";
	}
	
	/** 
	 * @author 唐国峰 
	 * @Description: 补充资料
	 * @param req
	 * @return int  
	 * @date 2016-5-6 上午11:18:24
	 * @throws 
	 */
	@RequestMapping("/handleProjectAppRecord")
	@ResponseBody
	public int handleProjectAppRecord(HttpServletRequest req, HttpServletResponse response){
		//操作日志参数
		HttpSession session = req.getSession();
		Admin admin = (Admin)session.getAttribute("LoginPerson");
		if(admin == null){
			return -100;
		}
		//moduleID=302(借款申请管理)
		//optID=30201(添加）
		InsertAdminLogEntity logEntity = new InsertAdminLogEntity();
		String [] sIpInfo = new String[8];
		logEntity.setiAdminId(admin.getId());
		logEntity.setsDetail("");
		logEntity.setlModuleId(302);
		logEntity.setlOptId(30201);
		logEntity.setsIp(AddressUtils.GetRemoteIpAddr(req, sIpInfo));
		logEntity.setsMac(null);
		logEntity.setsUrl(LoadUrlUtil.getFullURL(req));
		//获取解密参数
		Map<String,Object> param=new HashMap<String,Object>();
		//第一步参数
		String projectTitle = req.getParameter("projectTitle");
		param.put("projectTitle", projectTitle);
		String amount = req.getParameter("amount");
		param.put("amount", StringToLong(amount,0));
		String deadline = req.getParameter("deadline");
		param.put("deadline", deadline);
		String deadlineType = req.getParameter("deadlineType");
		param.put("deadlineType", deadlineType);
		String investMax = req.getParameter("investMax");
		param.put("investMax",  StringToLong(investMax,0));
		String yearRate = req.getParameter("yearRate");
		param.put("yearRate", StringToLong(yearRate,0));
		String minStart = req.getParameter("minStart");
		param.put("minStart", StringToLong(minStart,0));
		String increaseRange = req.getParameter("increaseRange");
		param.put("increaseRange", StringToLong(increaseRange,0));
		String repayWay = req.getParameter("repayWay");
		param.put("repayWay", repayWay);
		String repayGuarantee = req.getParameter("repayGuarantee");
		param.put("repayGuarantee", repayGuarantee);
		String repaySource = req.getParameter("repaySource");
		param.put("repaySource", repaySource);
		String uses = req.getParameter("uses");
		param.put("uses", uses);
		String projectDescript = req.getParameter("projectDescript");
		param.put("projectDescript", projectDescript);
		String guarantyDescribe = req.getParameter("content");
		param.put("guarantyDescribe", guarantyDescribe);
		String projectId = req.getParameter("projectId");
		param.put("projectType", projectId);//项目类型id
		//第二步参数
		String autoStart = req.getParameter("autoStart");
		param.put("autoStart", autoStart);
		String auotInvestMax = req.getParameter("auotInvestMax");
		param.put("auotInvestMax", StringToLong(auotInvestMax,0));
		String rateAddRate = req.getParameter("rateAddRate");
		param.put("rateAddRate", StringToLong(rateAddRate,0));
		String isDirect = req.getParameter("isDirect");
		param.put("isDirect", isDirect);
		String directPwd = req.getParameter("directPwd");
		param.put("directPwd", directPwd);
		String rewardRate = req.getParameter("rewardRate");
		param.put("rewardRate",  StringToLong(rewardRate,0));
		String guaranteeID = req.getParameter("guaranteeID");
		param.put("guaranteeID", guaranteeID);
		String assetManagerID = req.getParameter("assetManagerID");
		param.put("assetManagerID", assetManagerID);
		String riskMarginType = req.getParameter("riskMarginType");
		param.put("riskMarginType", riskMarginType);
		String riskMarginRate = req.getParameter("riskMarginRate");
		param.put("riskMarginRate", StringToLong(riskMarginRate,0));
		String riskMarginFee = req.getParameter("riskMarginFee");
		param.put("riskMarginFee", StringToLong(riskMarginFee,0));
		String mngFeeRate = req.getParameter("mngFeeRate");
		param.put("mngFeeRate",  StringToLong(mngFeeRate,0));//管理费比例
		String investCountMax = req.getParameter("investCountMax");
		param.put("investCountMax", IntegerAndString.StringToInt(investCountMax,0));
		String guaranteeRate = req.getParameter("guaranteeRate");//担保费率
		param.put("guaranteeRate", StringToLong(guaranteeRate,0));
		String guaranteeFee = req.getParameter("guaranteeFee");//担保费
		param.put("guaranteeFee", StringToLong(guaranteeFee,0));
		String guaranteeType = req.getParameter("guaranteeType");//担保费类型 0：百分比 1：固定金额
		param.put("guaranteeType", guaranteeType);
		//红包惊喜标
		String redPackJson = req.getParameter("start"); 
		List<ProjectInvestRedPackageEntity> redPackageList = JSON.parseArray(redPackJson, ProjectInvestRedPackageEntity.class);
		if(redPackageList != null){
			try{
				projectAppRecordService.insertProjectInvestRedPackage(redPackageList, logEntity, sIpInfo);
			}catch(Exception e){
				writeJson("-10",response);
			}
		}
		
		//未处理
		String mngFeeRateIncreace = req.getParameter("mngFeeRateIncreace");//管理费增量 
		param.put("mngFeeRateIncreace", StringToLong(mngFeeRateIncreace,0));
		String mngFeeAmount = req.getParameter("mngFeeAmount");//管理费金额
		param.put("mngFeeAmount", StringToLong(mngFeeAmount,0));
		String rewardIcon = req.getParameter("rewardIcon");//奖励标图标 空表示不单独配置，使用默认
		param.put("rewardIcon", rewardIcon);
		
		//公用参数
		String cStatu = req.getParameter("cStatu");
		param.put("cStatu", cStatu);//cStatu:1:提交申请，2：保存草稿 
		String ppid = req.getParameter("ppid");
		param.put("ppid", ppid);//意向借款id	 
		String pbiid = req.getParameter("pbiid");
		param.put("pbiid", pbiid);//项目基础信息id
		param.put("checkStatu", 0);//审核状态 -1	审核打回  0 未审核  1 审核通过
		param.put("adminId", admin.getId());
		String styp = req.getParameter("styp");
		param.put("styp", styp);//styp:1：第一步只修改第一步的参数 2：第二步 只修改第二步的参数	
		int data=0;
		data = projectAppRecordService.handleProjectAppRecord(param, logEntity, sIpInfo);
		return data;
	}
	
	
	/** 
	 * @author 唐国峰 
	 * @Description: 打开选择担保机构页面
	 * @param req
	 * @return String  
	 * @date 2016-5-6 下午3:00:19
	 * @throws 
	 */
	@RequestMapping("/toSelectMechanismPg")
	public String toSelectMechanismPg(HttpServletRequest req){
		return "project/pro-add/select_mechanism";
	}
	
	/** 
	 * @author 唐国峰 
	 * @Description: 打开选择资产管理方页面
	 * @param req
	 * @return String  
	 * @date 2016-5-6 下午3:01:53
	 * @throws 
	 */
	@RequestMapping("/toSelectAssetManagePg")
	public String toSelectAssetManagePg(HttpServletRequest req){
		return "project/pro-add/select_assetManagement";
	}
	
	
	
	/** 
	 * @author 唐国峰 
	 * @Description: 保存借款附件 
	 * @param req
	 * @return int  
	 * @date 2016-5-6 下午5:46:07
	 * @throws 
	 */
	@RequestMapping("/addAttachment")
	@ResponseBody
	public int addAttachment(HttpServletRequest req,HttpServletResponse response){
		//操作日志参数
		HttpSession session = req.getSession();
		Admin admin = (Admin)session.getAttribute("LoginPerson");
		if(admin == null){
			return -100;
		}
		//moduleID=302(借款申请管理)
		//optID=30201(添加）
		InsertAdminLogEntity logEntity = new InsertAdminLogEntity();
		String [] sIpInfo = new String[8];
		logEntity.setiAdminId(admin.getId());
		logEntity.setsDetail("");
		logEntity.setlModuleId(302);
		logEntity.setlOptId(30201);
		logEntity.setsIp(AddressUtils.GetRemoteIpAddr(req, sIpInfo));
		logEntity.setsMac(null);
		logEntity.setsUrl(LoadUrlUtil.getFullURL(req));
		
		String attachJson = req.getParameter("start"); 
		List<ProjectAppAttachmentEntity> attachList = JSON.parseArray(attachJson, ProjectAppAttachmentEntity.class);
		int result =0;
		if(attachList != null){
			try{
			result = projectAppRecordService.insertProjectAppAttachment(attachList, logEntity, sIpInfo);
			}catch(Exception e){
				writeJson("-10",response);
			}
		}
		return result;
	}
	
	/**
	 * @author 唐国峰 
	 * @Description: string转化为long,乘以10000且有默认值。
	 * @param value
	 * @param iDefault
	 * @return long  
	 * @date 2016-5-19 上午11:58:18
	 * @throws
	 */
	public long StringToLong(String value,int iDefault){
		long iResult = iDefault;
    	if(value == null || value.equals("")){
    		return iResult;
    	}
    	try {
			iResult = IntegerAndString.StringToLong(value.trim());
		} catch (Exception e) {
		}
    	return iResult;
	}
	
	
	/** 
	 * @author 唐国峰 
	 * @Description: 返回json至页面
	 * @param jsonContent
	 * @param response  
	 * @return void  
	 * @date 2016-5-19 下午6:55:14
	 * @throws 
	 */
	public void writeJson(String jsonContent, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.setContentType("application/json");
			response.getWriter().write(jsonContent);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
