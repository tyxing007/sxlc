
package cn.springmvc.service.impl; 

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;




import product_p2p.kit.datatrans.IntegerAndString;
import product_p2p.kit.dbkey.DbKeyUtil;


import cn.springmvc.dao.InvestIncomeDao;
import cn.springmvc.dao.InvestIncomeListDao;
import cn.springmvc.dao.impl.HandleThreePartyDaoImpl;
import cn.springmvc.dao.impl.IdGeneratorUtil;
import cn.springmvc.dao.impl.SelectThreePartyDaoImpl;
import cn.sxlc.account.manager.model.AccountInterfaceEntity;
import cn.sxlc.account.manager.model.AccountInterfaceReturnEntity;
import cn.sxlc.account.manager.model.AuditEntity;
import cn.sxlc.account.manager.model.AuditReturnEntity;
import cn.sxlc.account.manager.model.AuthorizeInterfaceEntity;
import cn.sxlc.account.manager.model.AuthorizeInterfaceReturnEntity;
import cn.sxlc.account.manager.model.AwardEntity;
import cn.sxlc.account.manager.model.InvestRecordEntity;
import cn.sxlc.account.manager.model.LoanRepayEntity;
import cn.sxlc.account.manager.model.LoanReturnInfoBean;
import cn.sxlc.account.manager.model.LoanTransactionEntity;
import cn.sxlc.account.manager.model.LoanTransferEntity;
import cn.sxlc.account.manager.model.LoanTransferReturnEntity;
import cn.sxlc.account.manager.model.RechargeEntity;
import cn.sxlc.account.manager.model.RechargeReturnEntity;
import cn.sxlc.account.manager.model.RepalyUtitls;
import cn.sxlc.account.manager.model.RepayDetailEntity;
import cn.sxlc.account.manager.model.RepayInterfaceEntity;
import cn.sxlc.account.manager.model.SurpriseRedEntity;
import cn.sxlc.account.manager.model.WithdrawalsFeeEntity;
import cn.sxlc.account.manager.model.WithdrawsEntity;
import cn.sxlc.account.manager.model.WithdrawsInterdaceEntity;
import cn.sxlc.account.manager.model.WithdrawsInterdaceReturnEntity;
import cn.springmvc.model.InvestIncomeEntity;
import cn.springmvc.service.ManagedInterfaceServerTestI;
import cn.sxlc.account.manager.model.Common;
import cn.sxlc.account.manager.model.HTTPClientUtilsbak;
import cn.sxlc.account.manager.model.RsaHelper;


/** 
 * 第三方托管接口实现   测试
* @author ZZY 
* @Description: TODO 
* @since 
* @date 2016-3-7 下午2:40:03  */
@Service("managedInterfaceTestIImpl")
public class ManagedInterfaceTestIImpl implements ManagedInterfaceServerTestI{
	@Resource(name="selectThreePartyDaoImpl")
	private SelectThreePartyDaoImpl  selectThreePartyDaoImpl;
	@Resource(name="handleThreePartyDaoImpl")
	private HandleThreePartyDaoImpl  handleThreePartyDaoImpl;
	IdGeneratorUtil generatorUtil = IdGeneratorUtil.GetIdGeneratorInstance();
	@Resource(name="investIncomeDaoImpl")
	private  InvestIncomeDao  investIncomeDao;
	@Resource(name="investIncomeListDaoImpl")
	private  InvestIncomeListDao  investIncomeListDao;
	/* *  *  * @param memberEntity
	 * 双乾开户接口信息处理
	 */
	/* *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#testAccountInterfaceQDD(cn.sxlc.account.manager.model.AccountInterfaceEntity) */
	@Override
	public AccountInterfaceEntity testAccountInterfaceQDD(AccountInterfaceEntity memberEntity) {
		//获取平台乾多多标识
		String pmark="";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountTypeID", 1);//平台账号类型
		pmark=selectThreePartyDaoImpl.findThirdPartyMark(map);
		memberEntity.setPlatformMoneymoremore(pmark);
		//根据会员id获取会员基本信息数据
		if (memberEntity.getMemberType()==0) {//个人
			map.put("memberId", memberEntity.getId());
			memberEntity=selectThreePartyDaoImpl.selectpAccountById(map);
			memberEntity.setAccountType("");
		}else if (memberEntity.getMemberType()==1) {//企业
			map.put("memberId", memberEntity.getId());
			memberEntity=selectThreePartyDaoImpl.selectcAccountById(map);
			memberEntity.setAccountType("1");
		}
		int type=0;//0：会员 1：担保机构 2：平台
		if(memberEntity.getMemberType()==2){
			type=1;
		}
		if(memberEntity.getMemberType()==3){
			type=2;
		}
		//开户 界面返回通知地址
		memberEntity.setReturnURL("http://182.150.179.116:14000/");
		//开户 服务器返回通知地址
		memberEntity.setNotifyURL("http://182.150.179.116:14000/");
		//开户信息提交三方地址
		memberEntity.setSubmitURL("http://218.4.234.150:88/main/SubmitURLPrefixByRegister");
		//查询此次操作订单号
		String ordernumber =handleThreePartyDaoImpl.generateorderNo("KH");
		//相关字段拼接
		memberEntity.setRemark1(memberEntity.getId()+"");
		memberEntity.setRemark2(ordernumber+"n"+type+"n"+memberEntity.getIsApp());
		
		String dataStr = memberEntity.getRegisterType() + memberEntity.getAccountType()
		+ memberEntity.getPhone() + memberEntity.getEmail() + memberEntity.getName()
		+ memberEntity.getIdcard() +  memberEntity.getLogName()
		+ memberEntity.getPlatformMoneymoremore() + memberEntity.getRandomTimeStamp()
		+ memberEntity.getRemark1() + memberEntity.getRemark2()
		+ memberEntity.getRemark3() + memberEntity.getReturnURL()
		+ memberEntity.getNotifyURL();
		String privatekey = Common.privateKeyPKCS8;
		RsaHelper rsa = RsaHelper.getInstance();
		String SignInfo = rsa.signData(dataStr, privatekey);
		memberEntity.setSignInfo(SignInfo);
		//添加开户第三方交互记录
		long iId=generatorUtil.GetId();
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", iId);//第三方交互记录id
		maps.put("merbillNo", ordernumber);//当前操作订单号
		maps.put("type", 1);//操作类型
		maps.put("interfaceType", 1);//第三方接口提供商
		maps.put("detail", dataStr);//加密前数据
		maps.put("detailEncrypt", SignInfo);//加密后数据
		maps.put("remark", "");//备注
		handleThreePartyDaoImpl.insertThirdInterfaceRecord(maps);
//		 if(result == 1){
		    	generatorUtil.SetIdUsed(iId);
////			}else{
//				generatorUtil.SetIdUsedFail(iId);
//			}
		return memberEntity;
	}

	
	/* *  * 
	 * 双乾开户接口页面返回
	 *  * @return SUCCESS 开户成功； FAIL 开户失败
	 *   * @see cn.springmvc.service.ManagedInterfaceServerTestI#testLoanRegisterBindReturn() */
	@Override
	public String testLoanRegisterBindReturn() {
		AccountInterfaceReturnEntity 
			accountInterfaceReturnEntity2=new AccountInterfaceReturnEntity();
		String retur="SUCCESS";
		try {
			//获取第三方返回开户信息
			accountInterfaceReturnEntity2=accountInterfaceReturnEntity2.getAllAccount();
			if(accountInterfaceReturnEntity2.getStatu()==1){//开户成功
				
			}else{//开户失败
				retur="FAIL";
			}
		} catch (UnsupportedEncodingException e) {
			
			// TODO Auto-generated catch block e.printStackTrace();
			
		}
		
		// TODO Auto-generated method stub return null;
		return retur;
	}


	
	/* *  
	 *  双乾开户接口服务器返回
	 * *  * @param accountInterfaceEntity * @see cn.springmvc.service.ManagedInterfaceServerTestI#testLoanRegisterBindNotify(cn.sxlc.account.manager.model.AccountInterfaceReturnEntity) */
	@Override
	public void testLoanRegisterBindNotify() {
		AccountInterfaceReturnEntity 
		accountInterfaceReturnEntity2=new AccountInterfaceReturnEntity();
	try {
		//获取第三方返回开户信息
		accountInterfaceReturnEntity2=accountInterfaceReturnEntity2.getAllAccount();
		//将获取的第三方返回的开户信息进行业务逻辑处理
		if(accountInterfaceReturnEntity2.getStatu()==1){//开户成功
			String remark=accountInterfaceReturnEntity2.getRemark1();//得到拼接的会员id
			long id=0;//会员id
			if (remark != null && remark != "" && !remark.equals("自定义备注1")) {
				id = Long.parseLong(remark);
			}
			String sMerBillNo = accountInterfaceReturnEntity2.getRemark2();
			String[] typStrings = sMerBillNo.split("n");
			sMerBillNo = typStrings[0];//订单编号
			String typeS = typStrings[1];// 开户类型
			Map<String, Object> map = new HashMap<String, Object>();
			long iId=generatorUtil.GetId();
			map.put("tid", iId);//第三方账户信息id
			map.put("merbillno", sMerBillNo);
			map.put("backDetailEncrypt", accountInterfaceReturnEntity2.getSignInfo());
			map.put("backDetail", accountInterfaceReturnEntity2.toString());
			map.put("type", typeS);
			map.put("memberid", id);
			map.put("account", accountInterfaceReturnEntity2.getAccountNumber());
			map.put("thirdPartymark", accountInterfaceReturnEntity2.getMoneymoremoreId());
			handleThreePartyDaoImpl.openAccountBack(map);
			generatorUtil.SetIdUsed(iId);
		}
		//接收第三方返回信息处理完成，通知第三方已成功接收处理
		HttpServletResponse response 
			= ((ServletWebRequest)RequestContextHolder
					.getRequestAttributes()).getResponse();
		response.setContentType("text/plain;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write("SUCCESS");
		response.getWriter().flush();
		response.getWriter().close();
	} catch (Exception e) {
		// TODO Auto-generated catch block e.printStackTrace();
	}
		// TODO Auto-generated method stub 
		
	}


	
	/* *  
	 * 充值提交第三方接口处理
	 * *  * @param recharge 充值需要提交参数第一步处理
	 */
	/* *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#testLoanRecharge(cn.sxlc.account.manager.model.RechargeEntity) */
	@Override
	public RechargeEntity testLoanRecharge(RechargeEntity recharge) {
		//生成充值订单号
		recharge.setOrderNo(handleThreePartyDaoImpl.generateorderNo("SQ"));
		//获取平台乾多多标识
		String pmark="";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountTypeID", 1);//平台账号类型
		pmark=selectThreePartyDaoImpl.findThirdPartyMark(map);
		recharge.setPlatformMoneymoremore(pmark);
		//根据会员id获取会员乾多多标识
		String smark="";
		int type=0;
		if(recharge.getMemberType()==0||recharge.getMemberType()==1){
			type=0;//会员
		}else if(recharge.getMemberType()==2){
			type=1;//担保机构
		}else {
			type=2;//平台
		}
		map.put("memberType", type);//会员类型
		map.put("memberID", recharge.getMemberId());//会员id
		smark=selectThreePartyDaoImpl.findMemberThirdPartyMark(map);
		if(smark==null || !smark.equals("")){//还未开户
			
		}
		recharge.setSubmitURL("http://218.4.234.150:88/main/loan/toloanrecharge.action");
		recharge.setReturnURL("http://182.150.179.116:14000/loanrechargereturn.html");
		recharge.setNotifyURL("http://182.150.179.116:14000/testLoanRechargeNotify.action");
		//成功所需数据拼接
		recharge.setRemark1(recharge.getMemberId()+"A"+type);
		recharge.setRemark2(recharge.getIsApp()+"");//充值端
		//判断充值类型 如果是快捷支付，查询出支付手续费的收取方式
		String feeType="";
		if(recharge.getRechargeType().equals("2")){//快捷支付
			feeType=selectThreePartyDaoImpl.payRechargeSet(map);
			if(feeType==null){
				recharge.setFeeType("1");
			}else {
				recharge.setFeeType(feeType);
			}
		}
		if(recharge.getRechargeType().equals("3") || recharge.getRechargeType().equals("4")){
			recharge.setFeeType("1");
		}
		
		recharge.setRechargeMoneymoremore(smark);
		String privatekey = Common.privateKeyPKCS8;
		String dataStr = recharge.getRechargeMoneymoremore()
				+ recharge.getPlatformMoneymoremore()
				+ recharge.getOrderNo()
				+ recharge.getAmount()
				+ recharge.getRechargeType() + recharge.getFeeType()
				+ recharge.getRandomTimeStamp()
				+ recharge.getRemark1() + recharge.getRemark2()
				+ recharge.getRemark3()
				+ recharge.getReturnURL()
				+ recharge.getNotifyURL();
		System.out.println(dataStr);
		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		String SignInfo = rsa.signData(dataStr, privatekey);
		recharge.setSignInfo(SignInfo);
		//添加开户第三方交互记录
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", generatorUtil.GetId());//第三方交互记录id
		maps.put("merbillNo", recharge.getOrderNo());//当前操作订单号
		maps.put("type", "01");//操作类型
		maps.put("interfaceType", 1);//第三方接口提供商
		maps.put("detail", dataStr);//加密前数据
		maps.put("detailEncrypt", SignInfo);//加密后数据
		maps.put("remark", "");//备注
		handleThreePartyDaoImpl.insertThirdInterfaceRecord(maps);		
		// TODO Auto-generated method stub return null;
		return recharge;
	}


	
	/* * 
	 * 双乾第三方充值页面返回处理
	 *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#testLoanRechargeReturn() */
	@Override
	public String testLoanRechargeReturn() {
		RechargeReturnEntity recharge=new RechargeReturnEntity();
		String retur="SUCCESS";
		try {
			//获取充值第三方返回信息
			recharge=recharge.rechargeReturnData();
			if(recharge.getStatu()==0){//充值成功
				retur="SUCCESS";
			}else if (recharge.getStatu()==1) {//充值失败
				retur="FAIL";
			}else if (recharge.getStatu()==2){//汇款充值信息提交成功，等待第三方处理
				retur="SUCCESSWAIT";
			}
		} catch (Exception e) {
			retur="ERRO";
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub return null;
		return retur;
	}


	
	/* *  * 
	 * 双乾第三方充值服务器返回处理
	 *  *  * @see cn.springmvc.service.ManagedInterfaceServerTestI#testLoanRechargeNotify() */
	@Override
	public void testLoanRechargeNotify() {
		RechargeReturnEntity recharge=new RechargeReturnEntity();
		// TODO Auto-generated method stub 
		try {
			//获取充值第三方返回信息
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			RechargeReturnEntity rechargeReturnEntity=new RechargeReturnEntity();
			rechargeReturnEntity.setResultCode(request.getParameter("ResultCode"));
			rechargeReturnEntity.setMessage(request.getParameter("Message"));//充值返回信息
			String ResultCode=request.getParameter("ResultCode");
			if(ResultCode!=null ){
				if(ResultCode.equals("88") || ResultCode.equals("90")){
					if(ResultCode.equals("88") && request.getParameter("RechargeType").equals(3)){//汇款充值信息提交成功等待处理
						rechargeReturnEntity.setStatu(2);
					}else{
						rechargeReturnEntity.setStatu(0);
					}
					rechargeReturnEntity.setAmount(request.getParameter("Amount"));
					rechargeReturnEntity.setCardNoList(request.getParameter("CardNoList"));
					rechargeReturnEntity.setFee(request.getParameter("Fee"));
					rechargeReturnEntity.setFeeType(request.getParameter("FeeType"));
					rechargeReturnEntity.setFeePlatform(request.getParameter("FeePlatform"));
					rechargeReturnEntity.setLoanNo(request.getParameter("LoanNo"));
					rechargeReturnEntity.setNotifyURL(request.getParameter("NotifyURL"));
					rechargeReturnEntity.setOrderNo(request.getParameter("OrderNo"));
					rechargeReturnEntity.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					rechargeReturnEntity.setRechargeMoneymoremore(request.getParameter("RechargeMoneymoremore"));
					rechargeReturnEntity.setRechargeType(request.getParameter("RechargeType"));
					rechargeReturnEntity.setRemark1(request.getParameter("Remark1"));
					rechargeReturnEntity.setRemark2(request.getParameter("Remark2"));
					rechargeReturnEntity.setRemark3(request.getParameter("Remark3"));
					rechargeReturnEntity.setSignInfo(request.getParameter("SignInfo"));
					rechargeReturnEntity.setReturnURL(request.getParameter("ReturnURL"));
				}else{
					rechargeReturnEntity.setStatu(1);
				}
			}else{
				rechargeReturnEntity.setStatu(1);
			}
			
			
			if(recharge.getStatu()==0){//充值成功
				//充值成功处理平台数据
				//recharge.setRemark1(recharge.getMemberId()+"A"+recharge.getMemberType());
				//recharge.setRemark2(recharge.getIsApp()+"");//充值端
				long memberId=0;//会员id
				String smemberId=recharge.getRemark1().split("A")[0];
				memberId=Long.parseLong(smemberId);
				int memberType=0;//会员类型
				memberType=Integer.parseInt(recharge.getRemark1().split("A")[1]);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("merbillno", recharge.getOrderNo());//平台交易订单号
				//充值记录id
				map.put("rechargeID", 1);
				//会员类型
				map.put("memberType", memberType);
				//会员id
				map.put("memberID", memberId);
				//第三方交易流水号
				map.put("thirdMerBillno", recharge.getLoanNo());
				//充值金额 此处要转换为 精确到分的 long 类型 ；还未转换
				map.put("withdrawalMoney", IntegerAndString.StringToLong(recharge.getAmount(),0));
				//平台代付手续费 此处要转换为 精确到分的 long 类型 ；还未转换
				map.put("mngFeeRepay", IntegerAndString.StringToLong(recharge.getFeePlatform(),0));
				//用户承担手续费 此处要转换为 精确到分的 long 类型 ；还未转换
				map.put("userFee", IntegerAndString.StringToLong(recharge.getFee(),0));
				//用户充值时填写的备注
				map.put("remark", recharge.getRemark3());
				//生成充值交易记录时所需的id
				long did = generatorUtil.GetId();
				map.put("tradeID", did);
				//生成充值手续费扣除交易记录时所需的id
				long tid = generatorUtil.GetId();
				map.put("tradeIDtow", tid);
				//接口返回数据加密
				map.put("backDetailEncrypt", recharge.getSignInfo());
				//接口返回数据解密后
				map.put("backDetail", recharge.toString());
				//积分记录id
				long iid = generatorUtil.GetId();
				map.put("integralID", iid);
				//红包记录id
				long rid = generatorUtil.GetId();
				map.put("memberRPID", rid);
				//数据加密字符串
				map.put("skey", DbKeyUtil.GetDbCodeKey());
				handleThreePartyDaoImpl.rechargeBack(map);
			}else if (recharge.getStatu()==1) {//充值失败
				//充值失败处理平台数据
				
			}else if (recharge.getStatu()==2){//汇款充值信息提交成功，等待第三方处理
				
				
			}
			HttpServletResponse response 
			= ((ServletWebRequest)RequestContextHolder
					.getRequestAttributes()).getResponse();
			response.setContentType("text/plain;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("SUCCESS");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		
		//接收第三方返回信息处理完成，通知第三方已成功接收处理
				
	}
	
	/* *  *  * @param withdrawsEntity
	 * 双乾第三方接口提交信息处理
	 */
	/* *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#testLoanWithdraws(cn.sxlc.account.manager.model.WithdrawsInterdaceEntity) */
	@Override
	public WithdrawsInterdaceEntity testLoanWithdraws(WithdrawsInterdaceEntity withdrawsEntity) {
		String privatekey = Common.privateKeyPKCS8;
		String publickey = Common.publicKey;
		//生成提现订单号
		String orderNoString=handleThreePartyDaoImpl.generateorderNo("SQ");
		withdrawsEntity.setOrderNo(orderNoString);
		//平台标识
		String pmark="";
		Map<String, Object> mapp = new HashMap<String, Object>();
		mapp.put("accountTypeID", 1);//平台账号类型
		pmark=selectThreePartyDaoImpl.findThirdPartyMark(mapp);
		withdrawsEntity.setPlatformMoneymoremore(pmark);
		//查询出手续费费率
		WithdrawalsFeeEntity withdrawalsFeeEntity=new WithdrawalsFeeEntity();
		Map<String, Object> map=new HashMap<String, Object>();
		int paymentMemberType=0;
		if(withdrawsEntity.getMemberType()==1||withdrawsEntity.getMemberType()==0){
			//前台会员
			paymentMemberType=0;
		}else if (withdrawsEntity.getMemberType()==2) {
			//保荐机构
			paymentMemberType=1;
		}else{
			//平台
			paymentMemberType=2;
		}
		map.put("paymentMemberType", paymentMemberType);
		withdrawalsFeeEntity=selectThreePartyDaoImpl.isWithdrawalsCheak(map);
		
		//管理费费率 
		//withdrawsEntity.setsFeeRate("");
		withdrawalsFeeEntity.getWithdrawal_Fee_Third();
		//平台垫付百分比
		withdrawsEntity.setsPtRate(withdrawalsFeeEntity.getWithdrawal_Fee_Pingtai()+"");
		//当前提现用户乾多多标识
		String smark="";
		map.put("memberType", paymentMemberType);//会员类型
		map.put("memberID", withdrawsEntity.getMemberId());//会员id
		smark=selectThreePartyDaoImpl.findMemberThirdPartyMark(map);
		withdrawsEntity.setWithdrawMoneymoremore(smark);
		//提现银行卡信息
		map.put("bankCardId", withdrawsEntity.getCardId());//银行卡信息id
		WithdrawsEntity w=selectThreePartyDaoImpl.finBankCode(map);
		withdrawsEntity.setCardType("0");//借记卡
		withdrawsEntity.setCardNo(w.getsCardNo());
		withdrawsEntity.setBankCode(w.getsBankCode());
		withdrawsEntity.setBranchBankName(w.getsBankName());
		withdrawsEntity.setProvince(w.getsProvince());
		withdrawsEntity.setCity(w.getsCity());
		//
		withdrawsEntity.setRemark1(withdrawsEntity.getMemberId()+"n"+withdrawsEntity.getCardId());
		withdrawsEntity.setRemark2(paymentMemberType+"");
		withdrawsEntity.setRemark3(orderNoString);
		withdrawsEntity.setSubmitURL("http://218.4.234.150:88/main/loan/toloanwithdraws.action");
		withdrawsEntity.setReturnURL("http://182.150.179.116:14000/loanrechargereturn.html");
		withdrawsEntity.setNotifyURL("http://182.150.179.116:14000/testLoanRechargeNotify.action");
		//提交数据签名动作
		String dataStr = withdrawsEntity.getWithdrawMoneymoremore()
				+ withdrawsEntity.getPlatformMoneymoremore()
				+ withdrawsEntity.getOrderNo()
				+ withdrawsEntity.getAmount()
				+ withdrawsEntity.getsPtRate()
				+ withdrawsEntity.getsUserFee()
				+ withdrawsEntity.getsFeeRate()
				+ withdrawsEntity.getCardNo()
				+ withdrawsEntity.getCardType()
				+ withdrawsEntity.getBankCode()
				+ withdrawsEntity.getBranchBankName()
				+ withdrawsEntity.getProvince()
				+ withdrawsEntity.getCity() + withdrawsEntity.getRandomTimeStamp()+withdrawsEntity.getRemark1()
				+ withdrawsEntity.getRemark2() + withdrawsEntity.getRemark3() + withdrawsEntity.getReturnURL() + withdrawsEntity.getNotifyURL();
		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		String SignInfo = rsa.signData(dataStr, privatekey);
		withdrawsEntity.setSignInfo(SignInfo);
		withdrawsEntity.setCardNo(rsa.encryptData(
				withdrawsEntity.getCardNo(), publickey));
		//添加开户第三方交互记录
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", generatorUtil.GetId());//第三方交互记录id
		maps.put("merbillNo", withdrawsEntity.getOrderNo());//当前操作订单号
		maps.put("type", "01");//操作类型
		maps.put("interfaceType", 1);//第三方接口提供商
		maps.put("detail", dataStr);//加密前数据
		maps.put("detailEncrypt", SignInfo);//加密后数据
		maps.put("remark", "");//备注
		handleThreePartyDaoImpl.insertThirdInterfaceRecord(maps);	
		
		return withdrawsEntity;
		// TODO Auto-generated method stub return null;
	}
	
	@Override
	public String testLoanWithdrawsReturn() {
		
		String retur="SUCCESS";
		try {
			WithdrawsInterdaceReturnEntity 
				withdrawsInterdaceReturnEntity=new WithdrawsInterdaceReturnEntity();
			withdrawsInterdaceReturnEntity = withdrawsInterdaceReturnEntity.mentionReturnInformation();
			//0：提现申请成功，等待平台审核  1：提现成功 2：提现失败3:提现银行退回
			if(withdrawsInterdaceReturnEntity.getStatu()==0){
				
			}else if (withdrawsInterdaceReturnEntity.getStatu()==1) {
				
			}else if (withdrawsInterdaceReturnEntity.getStatu()==2) {
				retur="FAIL";
			}else {
				
			}
		} catch (Exception e) {
			retur="ERRO";
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub return null;
		return retur;
	}
	
	
	/* *  *
	 * 双乾提现服务器返回处理
	 *   *  * @see cn.springmvc.service.ManagedInterfaceServerTestI#testLoanWithdrawsNotify() */
	@Override
	public void testLoanWithdrawsNotify() {
		try {
			WithdrawsInterdaceReturnEntity 
				withdrawsInterdaceReturnEntity=new WithdrawsInterdaceReturnEntity();
			withdrawsInterdaceReturnEntity = withdrawsInterdaceReturnEntity.mentionReturnInformation();
			
			//0：提现申请成功，等待平台审核  1：提现成功 2：提现失败3:提现银行退回
			if(withdrawsInterdaceReturnEntity.getStatu()==0){
				Map<String, Object> map=new HashMap<String, Object>();
				handleThreePartyDaoImpl.MemberWithdrawalBack_Qianduoduo(map);
			}else if (withdrawsInterdaceReturnEntity.getStatu()==1) {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("merbillno", withdrawsInterdaceReturnEntity.getOrderNo());//交易记录id
				map.put("rechargeID", 1);//交易记录id
				
				map.put("memberType", 1);
				map.put("memberID", 1);
				
				map.put("thirdMerBillno", withdrawsInterdaceReturnEntity.getLoanNo());//交易记录id
				map.put("withdrawalMoney", withdrawsInterdaceReturnEntity.getAmount());//提现金额(需转换*100的long)
				map.put("mngFeeRepay", 1);//平台代付手续费
				map.put("userFee", 1);//用户支付手续费
				map.put("remark", 1);
				map.put("tradeID", 1);//交易记录id
				map.put("tradeIDtow", 1);//交易记录id
				map.put("backDetailEncrypt", withdrawsInterdaceReturnEntity.getSignInfo());//返回加密参数
				map.put("backDetail", "");//
				map.put("integralID", 1);//随id
				map.put("memberRPID", 1);//交易记录id
				map.put("skey", DbKeyUtil.GetDbCodeKey());//交易记录id
				handleThreePartyDaoImpl.MemberWithdrawalBack_Qianduoduo(map);
			}else if (withdrawsInterdaceReturnEntity.getStatu()==2) {
				
			}else {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("tradeID", 1);//交易记录id
				map.put("merbillno", withdrawsInterdaceReturnEntity.getOrderNo());//平台订单号
				map.put("thirdBillNo", withdrawsInterdaceReturnEntity.getLoanNo());//第三方交易流水号
				map.put("retType", 1);//提现银行退回
				map.put("backDetailEncrypt", withdrawsInterdaceReturnEntity.getSignInfo());//返回加密数据
				map.put("backDetail", 1);//
				map.put("skey", DbKeyUtil.GetDbCodeKey());//秘钥
				handleThreePartyDaoImpl.WithdraBack(map);
			}
			HttpServletResponse response 
			= ((ServletWebRequest)RequestContextHolder
					.getRequestAttributes()).getResponse();
			response.setContentType("text/plain;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("SUCCESS");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub 
		
	}
	
	
	
	/* * 
	 * 双乾第三方自动投标、自动还款、二次分配授权提交信息处理
	 *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#testLoanAuthorize() */
	@Override
	public AuthorizeInterfaceEntity testLoanAuthorize(AuthorizeInterfaceEntity authorizeInterfaceEntity) {
		//生成交易订单号
		String OrderNo=handleThreePartyDaoImpl.generateorderNo("SQ");
		//获取平台乾多多标识
		String pmark="";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountTypeID", 1);//平台账号类型
		pmark=selectThreePartyDaoImpl.findThirdPartyMark(map);
		authorizeInterfaceEntity.setPlatformMoneymoremore(pmark);
		//根据会员id获取会员乾多多标识
		String smark="";
		int type=0;
		if(authorizeInterfaceEntity.getMemberType()==0||authorizeInterfaceEntity.getMemberType()==1){
			type=0;//会员
		}else if(authorizeInterfaceEntity.getMemberType()==2){
			type=1;//担保机构
		}else {
			type=2;//平台
		}
		map.put("memberType", type);//会员类型
		map.put("memberID", authorizeInterfaceEntity.getMemberId());//会员id
		smark=selectThreePartyDaoImpl.findMemberThirdPartyMark(map);
		authorizeInterfaceEntity.setMoneymoremoreId(smark);
		authorizeInterfaceEntity.setRemark1(type+"");
		authorizeInterfaceEntity.setRemark2(authorizeInterfaceEntity.getMemberId()+"");
		authorizeInterfaceEntity.setRemark3(OrderNo);
		authorizeInterfaceEntity.setSubmitURL("http://218.4.234.150:88/main/loan/toloanauthorize.action");
		authorizeInterfaceEntity.setReturnURL("http://182.150.179.116:14000/");
		authorizeInterfaceEntity.setNotifyURL("http://182.150.179.116:14000/");
		String privatekey = Common.privateKeyPKCS8;
		String dataStr = authorizeInterfaceEntity.getMoneymoremoreId() + authorizeInterfaceEntity.getPlatformMoneymoremore()
				+ authorizeInterfaceEntity.getAuthorizeTypeOpen()
				+ authorizeInterfaceEntity.getAuthorizeTypeClose()
				+ authorizeInterfaceEntity.getRandomTimeStamp() + authorizeInterfaceEntity.getRemark1()
				+ authorizeInterfaceEntity.getRemark2() + authorizeInterfaceEntity.getRemark3()
				+ authorizeInterfaceEntity.getReturnURL() + authorizeInterfaceEntity.getNotifyURL();
		RsaHelper rsa = RsaHelper.getInstance();
		String SignInfo = rsa.signData(dataStr, privatekey);
		authorizeInterfaceEntity.setSignInfo(SignInfo);
		
		//添加开户第三方交互记录
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", generatorUtil.GetId());//第三方交互记录id
		maps.put("merbillNo", OrderNo);//当前操作订单号
		maps.put("type", "04");//操作类型
		maps.put("interfaceType", 1);//第三方接口提供商
		maps.put("detail", dataStr);//加密前数据
		maps.put("detailEncrypt", SignInfo);//加密后数据
		maps.put("remark", "");//备注
		handleThreePartyDaoImpl.insertThirdInterfaceRecord(maps);	
		
		// TODO Auto-generated method stub return null;
		return authorizeInterfaceEntity;
	}
	
	
	/* *  
	 * 双乾授权接口 服务器返回处理
	 * *  *  * @see cn.springmvc.service.ManagedInterfaceServerTestI#testLoanAuthorizeNotify() */
	@Override
	public void testLoanAuthorizeNotify() {
		try {
			AuthorizeInterfaceReturnEntity 
				authorizeInterfaceReturnEntity=new AuthorizeInterfaceReturnEntity();
			authorizeInterfaceReturnEntity = authorizeInterfaceReturnEntity.authorizationInformation();
			if(authorizeInterfaceReturnEntity.getStatu()==0){//授权操作成功
				//关闭还是开启 1：开启  2：关闭
				int statu=authorizeInterfaceReturnEntity.getAuthorizeStatu();
				//授权种类
				int handleStatu=0;
				handleStatu=Integer.parseInt(authorizeInterfaceReturnEntity.getAuthorizeType());
				//处理备注拼接内容
				//得到会员类型
				String smenberType= authorizeInterfaceReturnEntity.getRemark1();
				int menberType=0;//会员类型
				menberType=Integer.parseInt(smenberType);
				//得到会员id
				String smenberId=authorizeInterfaceReturnEntity.getRemark2();
				long memberId=0;//会员id
				memberId=Long.parseLong(smenberId);
				//得到平台操作订单号
				String orderNumber=authorizeInterfaceReturnEntity.getRemark3();
				//数据处理
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("merbillNo", orderNumber);//当前操作订单号
				map.put("memberType", menberType);//
				map.put("memberID", memberId);//
				map.put("statu", statu);//
				map.put("handleStatu", handleStatu);//
				map.put("backDetailEncrypt", authorizeInterfaceReturnEntity.getSignInfo());//
				map.put("backDetail", "");//
				handleThreePartyDaoImpl.authorizeBack(map);
				
			}else{//授权操作失败
			}
			HttpServletResponse response 
			= ((ServletWebRequest)RequestContextHolder
					.getRequestAttributes()).getResponse();
			response.setContentType("text/plain;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("SUCCESS");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub 
		
	}
	
	/* *  
	 * 双乾 授权接口页面返回处理
	 * *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#testLoanAuthorizeReturn() */
	@Override
	public String testLoanAuthorizeReturn() {
		String retur="SUCCESS";
		try {
			AuthorizeInterfaceReturnEntity 
				authorizeInterfaceReturnEntity=new AuthorizeInterfaceReturnEntity();
			authorizeInterfaceReturnEntity = authorizeInterfaceReturnEntity.authorizationInformation();
			if(authorizeInterfaceReturnEntity.getStatu()==0){//授权操作成功
				//关闭还是开启
				//授权
				
			}else{//授权操作失败
				retur="FAIL";
			}
		} catch (Exception e) {
			retur="ERRO";
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub return null;
		return retur;
	}
	
	/* * 
	 * 双乾第三方审核 放款 提现 接口 提交信息
	 *  *  * @param auditEntity
	 */
	/* *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#testLoanTransferAudit(cn.sxlc.account.manager.model.AuditEntity) */
	@Override
	public AuditEntity testLoanTransferAudit(AuditEntity auditEntity) {
		//获取平台乾多多标识
		String pmark="";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountTypeID", 1);//平台账号类型
		pmark=selectThreePartyDaoImpl.findThirdPartyMark(map);
		String loanNoStr="";
		if (auditEntity.getStype()==1) {//项目
			//根据项目id查询需要操作流水号
			map.put("applyId", auditEntity.getPid());
			List<LoanTransactionEntity> list=new ArrayList<LoanTransactionEntity>();
			list=selectThreePartyDaoImpl.GetInvestListByProId(map);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					if (i == list.size() - 1) {
						loanNoStr = loanNoStr + list.get(i).getBillNo();
						if (list.get(i).getGiftBillNo()!=null && !list.get(i).getGiftBillNo().equals("")) {
							loanNoStr=loanNoStr+","+list.get(i).getGiftBillNo();
						}
					} else {
						loanNoStr = loanNoStr + list.get(i).getBillNo() + ",";
						if (list.get(i).getGiftBillNo()!=null && !list.get(i).getGiftBillNo().equals("")) {
							loanNoStr=loanNoStr+list.get(i).getGiftBillNo()+",";
						}
					}
				}
			}
		}
		if (auditEntity.getStype()==2) {//债权
			//根据债权转让申请id
			map.put("ctaId", auditEntity.getPid());
			List<LoanTransactionEntity> list=new ArrayList<LoanTransactionEntity>();
			list=selectThreePartyDaoImpl.GetCreditorTransId(map);
			int cou=0;
			if (list != null && list.size() > 0) {
				int length=list.size()-1;
				for(int i=0;i<length;i++){
					LoanTransactionEntity entity=list.get(i);
					loanNoStr+=entity.getBillNo();
					if(++cou==200) break;
					String giftBillNo=entity.getGiftBillNo();
					if(giftBillNo!=null&&!giftBillNo.equals("")){
						loanNoStr+=","+entity.getGiftBillNo();
						if(++cou==200) break;
					}
					
					loanNoStr+=(i==length?"":",");
				}
//				for (int i = 0; i < list.size(); i++) {
//					if (i == list.size() - 1) {
//						loanNoStr = loanNoStr + list.get(i).getBillNo();
//						cou++;
//						if (cou>=200) {
//							break;
//						}
//						if (list.get(i).getGiftBillNo()!=null && !list.get(i).getGiftBillNo().equals("")) {
//							loanNoStr=loanNoStr+","+list.get(i).getGiftBillNo();
//							cou++;
//							if (cou>=200) {
//								break;
//							}
//						}
//					} else {
//						loanNoStr = loanNoStr + list.get(i).getBillNo() + ",";
//						cou++;
//						if (cou>=200) {
//							loanNoStr=loanNoStr.substring(0, loanNoStr.length()-1);
//							break;
//						}
//						if (list.get(i).getGiftBillNo()!=null && !list.get(i).getGiftBillNo().equals("")) {
//							loanNoStr=loanNoStr+list.get(i).getGiftBillNo()+",";
//							cou++;
//							if (cou>=200) {
//								loanNoStr=loanNoStr.substring(0, loanNoStr.length()-1);
//								break;
//							}
//						}
//					}
//				}
			}
		}
		if (auditEntity.getStype()==3) {//提现
			map.put("withdrawalID", auditEntity.getPid());
			loanNoStr=selectThreePartyDaoImpl.findWithdrawalRecharge(map);
		}
		auditEntity.setLoanNoList(loanNoStr);
		//获取操作订单号
		String OrderNumber=handleThreePartyDaoImpl.generateorderNo("SQ");
		auditEntity.setSubmitURL("http://218.4.234.150:88/main/loan/toloantransferaudit.action");
		auditEntity.setReturnURL("http://182.150.179.116:14000/");
		auditEntity.setNotifyURL("http://182.150.179.116:14000/");
		auditEntity.setRemark1(auditEntity.getPid()+"n"+auditEntity.getStype());
		auditEntity.setRemark2(OrderNumber);
		
		String dataStr = auditEntity.getLoanNoList() + auditEntity.getPlatformMoneymoremore()
				+ auditEntity.getAuditType() +auditEntity.getRemark1()
				+ auditEntity.getRemark2() + auditEntity.getRemark3()
				+ auditEntity.getReturnURL() + auditEntity.getNotifyURL();
		// 签名
		String privatekey = Common.privateKeyPKCS8;
		RsaHelper rsa = RsaHelper.getInstance();
		String SignInfo = rsa.signData(dataStr, privatekey);
		auditEntity.setSignInfo(SignInfo);
		//添加开户第三方交互记录
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", generatorUtil.GetId());//第三方交互记录id
		maps.put("merbillNo", OrderNumber);//当前操作订单号
		maps.put("type", "05");//操作类型
		maps.put("interfaceType", 1);//第三方接口提供商
		maps.put("detail", dataStr);//加密前数据
		maps.put("detailEncrypt", SignInfo);//加密后数据
		maps.put("remark", "");//备注
		handleThreePartyDaoImpl.insertThirdInterfaceRecord(maps);
		
		// TODO Auto-generated method stub return null;
		return auditEntity;
	}
	
	
	/* * 
	 * 双乾第三方放款、提现审核接口 页面返回
	 *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#testLoanTransferAuditReturn() */
	@Override
	public String testLoanTransferAuditReturn() {
		String retur="SUCCESS";
		try {
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			AuditReturnEntity auditReturnEntity=new AuditReturnEntity();
			auditReturnEntity.setResultCode(request.getParameter("ResultCode"));
			auditReturnEntity.setMessage(request.getParameter("Message"));//返回信息
			//String ReturnTimes = request.getParameter("ReturnTimes");//返回次数
			if(auditReturnEntity.getResultCode()!=null){
				if(auditReturnEntity.getResultCode().equals("88")){
					auditReturnEntity.setStatu(0);
					auditReturnEntity.setLoanNoList(request.getParameter("LoanNoList"));
					auditReturnEntity.setLoanNoListFail(request.getParameter("LoanNoListFail"));
					auditReturnEntity.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					auditReturnEntity.setAuditType(request.getParameter("AuditType"));
					auditReturnEntity.setRemark1(request.getParameter("Remark1"));
					auditReturnEntity.setRemark2(request.getParameter("Remark2"));
					auditReturnEntity.setRemark3(request.getParameter("Remark3"));
					auditReturnEntity.setSignInfo(request.getParameter("SignInfo"));
				}else {
					auditReturnEntity.setStatu(1);
				}
			}else {
				auditReturnEntity.setStatu(1);
			}
			if(auditReturnEntity.getStatu()==0){//审核操作成功
				
				
			}else{//审核操作失败
				retur="FAIL";
			}
		} catch (Exception e) {
			retur="ERRO";
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub return null;
		return retur;
	}
	
	
	/* *  * 
	 * 双乾第三方放款、提现审核接口 服务器返回
	 *  *  * @see cn.springmvc.service.ManagedInterfaceServerTestI#testLoanTransferAuditNotify() */
	@Override
	public void testLoanTransferAuditNotify() {
		try {
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			AuditReturnEntity auditReturnEntity=new AuditReturnEntity();
			auditReturnEntity.setResultCode(request.getParameter("ResultCode"));
			auditReturnEntity.setMessage(request.getParameter("Message"));//返回信息
			//String ReturnTimes = request.getParameter("ReturnTimes");//返回次数
			if(auditReturnEntity.getResultCode()!=null){
				if(auditReturnEntity.getResultCode().equals("88")){
					auditReturnEntity.setStatu(0);
					auditReturnEntity.setLoanNoList(request.getParameter("LoanNoList"));
					auditReturnEntity.setLoanNoListFail(request.getParameter("LoanNoListFail"));
					auditReturnEntity.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					auditReturnEntity.setAuditType(request.getParameter("AuditType"));
					auditReturnEntity.setRemark1(request.getParameter("Remark1"));
					auditReturnEntity.setRemark2(request.getParameter("Remark2"));
					auditReturnEntity.setRemark3(request.getParameter("Remark3"));
					auditReturnEntity.setSignInfo(request.getParameter("SignInfo"));
				}else {
					auditReturnEntity.setStatu(1);
				}
			}else {
				auditReturnEntity.setStatu(1);
			}
			
			
			if(auditReturnEntity.getStatu()==0){//审核操作成功
				//审核类型 1.通过 2.退回  5.提现通过6.提现退回
				String [] strings=auditReturnEntity.getRemark1().split("n");
				if(auditReturnEntity.getAuditType().equals("1")){//放款
					if (strings[1].equals("2")) {//债权转让放款成功
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("lId", generatorUtil.GetId());//订单号
						map.put("applyId", IntegerAndString.StringToInt(strings[0],0));
						map.put("sMerBillNo", auditReturnEntity.getRemark2());
						map.put("sOrderNos", auditReturnEntity.getLoanNoList());
						map.put("iStatu", 0);
						map.put("backInfoenc", auditReturnEntity.getSignInfo());
						map.put("backinfo", auditReturnEntity.toString());
						map.put("skey", DbKeyUtil.GetDbCodeKey());
						
						int ruelt=handleThreePartyDaoImpl.CreditorTransfer(map);
						if (ruelt==1) {//放款已全部处理成功
							//查询出所有的债权转让投资记录
							map.put("ctaId", IntegerAndString.StringToInt(strings[0],0));
							List<InvestRecordEntity> list=selectThreePartyDaoImpl.GetTransInvestList(map);
							//查询出债权转让利息生成方式
							Integer typInteger=selectThreePartyDaoImpl.InterestType();
							//查询出原有项目的还款方式
							Map<String, Object> mapa=new HashMap<String, Object>();
							Map<String, Integer> mapaaMap=new HashMap<String, Integer>();
							mapaaMap=selectThreePartyDaoImpl.getRepaymentByCTAId(mapa);
							Integer repayment=mapaaMap.get("Deadline_Type");
							int applyID=mapaaMap.get("Apply_Id");
							//查询出剩余期数
							Map<String, Object> mapsMap=selectThreePartyDaoImpl.getDateInstallments(map);
							int numb=IntegerAndString
									.StringToInt(mapsMap.get("Surplus_Time").toString(), 0);//剩余期数
							int numbType=IntegerAndString
									.StringToInt(mapsMap.get("Surplus_Time_Type").toString(), 0);//期限类型
							//查询出最近一次回款时间（作为生成收益计划的投标结束时间） 如果一次都未还则为原项目放款时间
							map=selectThreePartyDaoImpl.GetProdateTrans(map);
							String yearRate=IntegerAndString.LongToString(Long.parseLong(map.get("yearRate").toString())/100);//年化利率
							String prodate=map.get("tim").toString();//时间
							//利息计算天数
							int timNum=IntegerAndString.StringToInt(map.get("timNum").toString(), 0);
							InvestRecordEntity iEntity = new InvestRecordEntity();
							int result=0;
							if (typInteger==0) {//整期生成
								for (int i = 0; i < list.size(); i++) {
									iEntity = list.get(i);
									String amount=IntegerAndString.LongToString(iEntity.getInvestAmountValid());		
									List<LoanRepayEntity> planList=RepalyUtitls.getIncomePlan2(numbType, amount, yearRate, Short.valueOf(numb+""), Short.valueOf(repayment+""), prodate);
									String planStr = "";
									LoanRepayEntity planEntity=new LoanRepayEntity();
									
									for(int m = 0;m<planList.size();m++){
										planEntity = planList.get(m); 
										if(planStr.equals("")){
											planStr = planEntity.getCorpus()+","+planEntity.getInterest()+","+planEntity.getRetrieveDateTime();
											
										}else{
											planStr = planStr+";"+planEntity.getCorpus()+","+planEntity.getInterest()+","+planEntity.getRetrieveDateTime();
										}
									}
									result =GenerateInvestIncome(iEntity.getInvestId(), applyID, iEntity.getMemberID(), planStr);
								}
								
							}else {//按照转让时间点生成
								long interest=0;
								String nowTime="";
								for (int i = 0; i < list.size(); i++) {
									iEntity = list.get(i);
									String amount=IntegerAndString.LongToString(iEntity.getInvestAmountValid());		
									List<LoanRepayEntity> planList=RepalyUtitls.getIncomePlan2(numbType, amount, yearRate, Short.valueOf(numb+""), Short.valueOf(repayment+""), prodate);
									String planStr = "";
									LoanRepayEntity planEntity=new LoanRepayEntity();
									for(int m = 0;m<planList.size();m++){
										planEntity = planList.get(m); 
										if(planList.size()-1<=m && repayment==2){//到期还息本
											if (numbType==1) {
												long intmoney=IntegerAndString.StringToLong(planEntity.getInterest(), 0);
												if (intmoney>0&&timNum>0) {//月标 到期还本付息
													long nowmoney=intmoney/30*numb*timNum;
													planEntity.setInterest(IntegerAndString.LongToString(nowmoney));
													interest=interest+(intmoney-nowmoney);
													nowTime=planEntity.getRetrieveDateTime();
												}
											}else if(numbType==2) {
												long intmoney=IntegerAndString.StringToLong(planEntity.getInterest(), 0);
												if (intmoney>0&&timNum>0) {//年标 到期还本付息
													long nowmoney=intmoney/365*timNum;
													planEntity.setInterest(IntegerAndString.LongToString(nowmoney));
													interest=interest+(intmoney-nowmoney);
													nowTime=planEntity.getRetrieveDateTime();
												}
											}else{
												long intmoney=IntegerAndString.StringToLong(planEntity.getInterest(), 0);
												if (intmoney>0&&timNum>0) {//天标 到期还本付息
													long nowmoney=intmoney/numb*timNum;
													planEntity.setInterest(IntegerAndString.LongToString(nowmoney));
													interest=interest+(intmoney-nowmoney);
													nowTime=planEntity.getRetrieveDateTime();
												}
											}
										}
										if(planStr.equals("")){
											if (numbType==1 && repayment!=2) {
												long intmoney=IntegerAndString.StringToLong(planEntity.getInterest(), 0);
												if (intmoney>0&&timNum>0) {//月标 除到期还本付息
													long nowmoney=intmoney/30*timNum;
													planEntity.setInterest(IntegerAndString.LongToString(nowmoney));
													interest=interest+(intmoney-nowmoney);
													nowTime=planEntity.getRetrieveDateTime();
												}
											}
											planStr = planEntity.getCorpus()+","+planEntity.getInterest()+","+planEntity.getRetrieveDateTime();
										}else{
											planStr = planStr+";"+planEntity.getCorpus()+","+planEntity.getInterest()+","+planEntity.getRetrieveDateTime();
										}
									}
									result =GenerateInvestIncome(iEntity.getInvestId(), applyID, iEntity.getMemberID(), planStr);
								}
								if(interest>0){
									//转让人生成新的收益计划
									String planStrsString="0.00,"+IntegerAndString.LongToString(interest)+","+nowTime;
									GenerateInvestIncome(Long.parseLong(map.get("InvId").toString()), applyID, Long.parseLong(map.get("memberId").toString()), planStrsString);
								}
							}
							//收益平账
							if(result == 1) {
								 result = RepayIncomePing(applyID);
							}
							//修改债权收益状态
							if(result == 1){
								Map<String, Object> map2=new HashMap<String, Object>();
								map2.put("ctaId", IntegerAndString.StringToInt(strings[0],0));
								result = handleThreePartyDaoImpl.updateProjrctTransStatu(map2);
							}
						}
					}
				}else if (auditReturnEntity.getAuditType().equals("2")) {//流标
					if (strings[1].equals("2")) {//债权转让流标
						Map<String, Object> map = new HashMap<String, Object>();
						long apid=0;
						apid=Long.parseLong(strings[0]);//项目id
						map.put("applyId", apid);
						map.put("tid", generatorUtil.GetId());
						map.put("traId", IntegerAndString.StringToInt(strings[0],0));
						map.put("merbillno", auditReturnEntity.getRemark2());//订单号
						map.put("backDetailEncrypt", auditReturnEntity.getSignInfo());
						map.put("backDetail", auditReturnEntity.toString());
						map.put("sInvest", auditReturnEntity.getLoanNoList());
						map.put("skey",DbKeyUtil.GetDbCodeKey());//秘钥
						handleThreePartyDaoImpl.TransFail(map);
					}else {
						Map<String, Object> map = new HashMap<String, Object>();
						long apid=0;
						apid=Long.parseLong(strings[0]);//项目id
						map.put("applyId", apid);
						List<LoanTransactionEntity> list=selectThreePartyDaoImpl.GetInvestListByProId(map);
						String idsString="";
						long id=0;//交易记录id
						if (list.size()>0) {
							for (int i = 0; i < list.size(); i++) {
								id=generatorUtil.GetId();//获取生成交易记录id
								if (!idsString.equals("")) {
									idsString+=";"+list.get(i).getInversId()+","+id;
								}else {
									idsString+=list.get(i).getInversId()+","+id;
								}
							}//
						}
						map.put("merbillno", auditReturnEntity.getRemark2());//订单号
						map.put("backDetailEncrypt", auditReturnEntity.getSignInfo());
						map.put("backDetail", auditReturnEntity.toString());
						map.put("sInvest", idsString);
						map.put("skey",DbKeyUtil.GetDbCodeKey());//秘钥
						handleThreePartyDaoImpl.ProjectFail(map);
					}
					
				}else if (auditReturnEntity.getAuditType().equals("5")) {//提现通过
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("tradeID", generatorUtil.GetId());//交易记录id
					map.put("merbillno", auditReturnEntity.getRemark2());//平台订单号
					map.put("thirdBillNo", auditReturnEntity.getLoanNoList());//第三方交易流水号
					map.put("retType", 3);//提现银行退回
					map.put("backDetailEncrypt", auditReturnEntity.getSignInfo());//返回加密数据
					map.put("backDetail", 1);//
					map.put("skey", DbKeyUtil.GetDbCodeKey());//秘钥
					handleThreePartyDaoImpl.WithdraBack(map);
				}else if (auditReturnEntity.getAuditType().equals("6")) {//提现退回
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("tradeID", generatorUtil.GetId());//交易记录id
					map.put("merbillno", auditReturnEntity.getRemark2());//平台订单号
					map.put("thirdBillNo", auditReturnEntity.getLoanNoList());//第三方交易流水号
					map.put("retType", 2);//提现银行退回
					map.put("backDetailEncrypt", auditReturnEntity.getSignInfo());//返回加密数据
					map.put("backDetail", "");//
					map.put("skey", DbKeyUtil.GetDbCodeKey());//秘钥
					handleThreePartyDaoImpl.WithdraBack(map);
				}
			}else{//审核操作失败
				
			}
			HttpServletResponse response 
			= ((ServletWebRequest)RequestContextHolder
					.getRequestAttributes()).getResponse();
			response.setContentType("text/plain;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("SUCCESS");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub 
		
	}
	@Override
	public LoanTransferEntity testLoanTransfer(
			LoanTransferEntity loanTransferEntity) {
		
		String privatekey = Common.privateKeyPKCS8;
		String dataStr = loanTransferEntity.getLoanJsonList()
				+ loanTransferEntity.getPlatformMoneymoremore()
				+ loanTransferEntity.getTransferAction() + loanTransferEntity.getAction()
				+ loanTransferEntity.getTransferType() + loanTransferEntity.getNeedAudit()
				+ loanTransferEntity.getRandomTimeStamp() + loanTransferEntity.getRemark1()
				+ loanTransferEntity.getRemark2() + loanTransferEntity.getRemark3()
				+ loanTransferEntity.getReturnURL() + loanTransferEntity.getNotifyURL();
		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		String SignInfo = rsa.signData(dataStr, privatekey);
		loanTransferEntity.setSignInfo(SignInfo);
		String LoanJsonList = Common.UrlEncoder(loanTransferEntity.getLoanJsonList(),
				"utf-8");
		loanTransferEntity.setLoanJsonList(LoanJsonList);
		
		if(loanTransferEntity.getAction().equals("2")){//自动投标转账
			HTTPClientUtilsbak httpClientUtilsbak = new HTTPClientUtilsbak();
			List<NameValuePair> nvps = new ArrayList<NameValuePair>(1);
			nvps.add(new BasicNameValuePair("LoanJsonList", loanTransferEntity.getLoanJsonList()));
			nvps.add(new BasicNameValuePair("PlatformMoneymoremore",
					loanTransferEntity.getPlatformMoneymoremore()));
			nvps.add(new BasicNameValuePair("TransferAction", loanTransferEntity
					.getTransferAction()));
			nvps.add(new BasicNameValuePair("Action", loanTransferEntity.getAction()));
			nvps.add(new BasicNameValuePair("TransferType", loanTransferEntity
					.getTransferType()));
			nvps.add(new BasicNameValuePair("NeedAudit", loanTransferEntity
					.getNeedAudit()));
			nvps.add(new BasicNameValuePair("RandomTimeStamp", loanTransferEntity
					.getRandomTimeStamp()));
			nvps.add(new BasicNameValuePair("Remark1", loanTransferEntity.getRemark1()));
			nvps.add(new BasicNameValuePair("Remark2", loanTransferEntity.getRemark2()));
			nvps.add(new BasicNameValuePair("Remark3", loanTransferEntity.getRemark3()));
			nvps.add(new BasicNameValuePair("ReturnURL", loanTransferEntity
					.getReturnURL()));
			nvps.add(new BasicNameValuePair("NotifyURL", loanTransferEntity
					.getNotifyURL()));
			nvps.add(new BasicNameValuePair("SignInfo", SignInfo));
			try {
				String ruelt = httpClientUtilsbak.httpPost(nvps,
						loanTransferEntity.getSubmitURL());
				loanTransferEntity.setStatu(0);//提交信息处理成功
			} catch (Exception e) {
				loanTransferEntity.setStatu(1);//提交信息处理失败
				// TODO Auto-generated catch block e.printStackTrace();
			}
		}else{
			loanTransferEntity.setStatu(0);//提交信息处理成功
		}
		// TODO Auto-generated method stub return null;
		return loanTransferEntity;
	}
	
	
	/* * 
	 * 前台投资 双乾第三方页面返回处理 
	 *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#testInvestmentReturn() */
	@Override
	public String testInvestmentReturn() {
		String retur="SUCCESS";
		try {
			LoanTransferReturnEntity 
					loanTransferReturnEntity=new LoanTransferReturnEntity();
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			loanTransferReturnEntity.
				setResultCode(request.getParameter("ResultCode"));
			loanTransferReturnEntity
				.setMessage(request.getParameter("Message"));//返回信息
			if(loanTransferReturnEntity.getResultCode()!=null){
				if (loanTransferReturnEntity.getResultCode().equals("88")) {
					loanTransferReturnEntity.setStatu(0);
					loanTransferReturnEntity
					.setLoanJsonList(request.getParameter("LoanJsonList"));
					String fah = request.getParameter("LoanJsonList");
					fah = Common.UrlDecoder(fah, "utf-8");
					List<Object> loaninfolist = Common.JSONDecodeList(fah,
							LoanReturnInfoBean.class);
					loanTransferReturnEntity.setLoaninfolists(loaninfolist);//需要处理的转账信息
					loanTransferReturnEntity
					.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					loanTransferReturnEntity
					.setRemark1(request.getParameter("Remark1"));
					loanTransferReturnEntity
					.setRemark2(request.getParameter("Remark2"));
					loanTransferReturnEntity
					.setRemark3(request.getParameter("Remark3"));
				}else {
					loanTransferReturnEntity.setStatu(1);
				}
			}else {
				loanTransferReturnEntity.setStatu(1);
			}
			if(loanTransferReturnEntity.getStatu()==0){//投资成功
				
			}else {//投资失败
				loanTransferReturnEntity.getMessage();//失败原因
				retur="FAIL";
			}
			
		} catch (Exception e) {
			retur="ERRO";
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub return null;
		return retur;
	}
	
	/* * 
	 * 前台投资 双乾第三方服务器返回处理
	 *  *  *  * @see cn.springmvc.service.ManagedInterfaceServerTestI#testInvestmentNotify() */
	@Override
	public void testInvestmentNotify() {
		try {
			LoanTransferReturnEntity 
					loanTransferReturnEntity=new LoanTransferReturnEntity();
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			loanTransferReturnEntity.
				setResultCode(request.getParameter("ResultCode"));
			loanTransferReturnEntity
				.setMessage(request.getParameter("Message"));//返回信息
			if(loanTransferReturnEntity.getResultCode()!=null){
				if (loanTransferReturnEntity.getResultCode().equals("88")) {
					loanTransferReturnEntity.setStatu(0);
					loanTransferReturnEntity
					.setLoanJsonList(request.getParameter("LoanJsonList"));
					String fah = request.getParameter("LoanJsonList");
					fah = Common.UrlDecoder(fah, "utf-8");
					List<Object> loaninfolist = Common.JSONDecodeList(fah,
							LoanReturnInfoBean.class);
					loanTransferReturnEntity.setLoaninfolists(loaninfolist);//需要处理的转账信息
					loanTransferReturnEntity
					.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					loanTransferReturnEntity
					.setRemark1(request.getParameter("Remark1"));
					loanTransferReturnEntity
					.setRemark2(request.getParameter("Remark2"));
					loanTransferReturnEntity
					.setRemark3(request.getParameter("Remark3"));
				}else {
					loanTransferReturnEntity.setStatu(1);
				}
			}else {
				loanTransferReturnEntity.setStatu(1);
			}
			if(loanTransferReturnEntity.getStatu()==0){//投资成功
				if(loanTransferReturnEntity.getLoaninfolist().size()>0){//判断是否存在转账列表
					//判断是否使用红包  提交投资信息到第三方时 Remark 字段内拼接获取（ 是否使用红包+红包id+使用金额）
					
					//判断是否使用代金券  提交投资信息到第三方时 Remark 字段内拼接获取（是否使用代金券+代金券id+使用金额）
					
					//获取会员投资列表信息
					LoanReturnInfoBean lrib = (LoanReturnInfoBean) loanTransferReturnEntity.getLoaninfolist()
							.get(0);
					//处理获取的数据
					
				}
				
			}else {//投资失败
				loanTransferReturnEntity.getMessage();//失败原因
			}
			
			HttpServletResponse response 
			= ((ServletWebRequest)RequestContextHolder
					.getRequestAttributes()).getResponse();
			response.setContentType("text/plain;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("SUCCESS");
			response.getWriter().flush();
			response.getWriter().close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub 
	}
	
	
	
	
	/* * 
	 * 双乾第三方还款 页面返回处理
	 *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#testRepaymentReturn() */
	@Override
	public String testRepaymentReturn() {
		
		String retur="SUCCESS";
		try {
			LoanTransferReturnEntity 
					loanTransferReturnEntity=new LoanTransferReturnEntity();
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			loanTransferReturnEntity.
				setResultCode(request.getParameter("ResultCode"));
			loanTransferReturnEntity
				.setMessage(request.getParameter("Message"));//返回信息
			if(loanTransferReturnEntity.getResultCode()!=null){
				if (loanTransferReturnEntity.getResultCode().equals("88")) {
					loanTransferReturnEntity.setStatu(0);
					loanTransferReturnEntity
					.setLoanJsonList(request.getParameter("LoanJsonList"));
					String fah = request.getParameter("LoanJsonList");
					fah = Common.UrlDecoder(fah, "utf-8");
					List<Object> loaninfolist = Common.JSONDecodeList(fah,
							LoanReturnInfoBean.class);
					loanTransferReturnEntity.setLoaninfolists(loaninfolist);//需要处理的转账信息
					loanTransferReturnEntity
					.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					loanTransferReturnEntity
					.setRemark1(request.getParameter("Remark1"));
					loanTransferReturnEntity
					.setRemark2(request.getParameter("Remark2"));
					loanTransferReturnEntity
					.setRemark3(request.getParameter("Remark3"));
				}else {
					loanTransferReturnEntity.setStatu(1);
				}
			}else {
				loanTransferReturnEntity.setStatu(1);
			}
			if(loanTransferReturnEntity.getStatu()==0){//还款成功
				if(loanTransferReturnEntity.getLoaninfolist().size()>0){
				}
			}else {//还款失败
				loanTransferReturnEntity.getMessage();//失败原因
				retur="FAIL";
			}
			
		} catch (Exception e) {
			retur="ERRO";
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub return null;
		return retur;
		
	}
	
	/* *  
	 * 双乾第三方还款 服务器返回处理
	 * *  *  * @see cn.springmvc.service.ManagedInterfaceServerTestI#testRepaymentNotify() */
	@Override
	public void testRepaymentNotify() {
		try {
			LoanTransferReturnEntity 
					loanTransferReturnEntity=new LoanTransferReturnEntity();
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			loanTransferReturnEntity.
				setResultCode(request.getParameter("ResultCode"));
			loanTransferReturnEntity
				.setMessage(request.getParameter("Message"));//返回信息
			if(loanTransferReturnEntity.getResultCode()!=null){
				if (loanTransferReturnEntity.getResultCode().equals("88")) {
					loanTransferReturnEntity.setStatu(0);
					loanTransferReturnEntity
					.setLoanJsonList(request.getParameter("LoanJsonList"));
					String fah = request.getParameter("LoanJsonList");
					fah = Common.UrlDecoder(fah, "utf-8");
					List<Object> loaninfolist = Common.JSONDecodeList(fah,
							LoanReturnInfoBean.class);
					loanTransferReturnEntity.setLoaninfolists(loaninfolist);//需要处理的转账信息
					loanTransferReturnEntity
					.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					loanTransferReturnEntity
					.setRemark1(request.getParameter("Remark1"));
					loanTransferReturnEntity
					.setRemark2(request.getParameter("Remark2"));
					loanTransferReturnEntity
					.setRemark3(request.getParameter("Remark3"));
				}else {
					loanTransferReturnEntity.setStatu(1);
				}
			}else {
				loanTransferReturnEntity.setStatu(1);
			}
			if(loanTransferReturnEntity.getStatu()==0){//还款成功
				String detil="";
				if(loanTransferReturnEntity.getLoaninfolist().size()>0){
					for (int i = 0; i < loanTransferReturnEntity.getLoaninfolist().size(); i++) {
						LoanReturnInfoBean lrib = (LoanReturnInfoBean) loanTransferReturnEntity.getLoaninfolist()
								.get(i);//批量还款信息
						if (!detil.equals("")) {
							detil+="B"+lrib.getRemark();
						}else {
							detil+=lrib.getRemark()+"B";
						}
					}
					
					
					
				}
				
				
				
			}else {//还款失败
				loanTransferReturnEntity.getMessage();//失败原因
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub 
		
	}


	
	/* *  
	 * 提前还款操作数据
	 * *  * @param lonmemberId 还款人会员id
	 */
	/* *  *  * @param lonmembertype 还款人会员类型
	/* *  *  * @param appplyId 项目申请id
	/* *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#EarlyRepaymentProcessing(long, int, long) */
	@Override
	public RepayInterfaceEntity EarlyRepaymentProcessing(long lonmemberId,int lonmembertype,long appplyId,int[] results) {
		RepayInterfaceEntity repayInterfaceEntity=new RepayInterfaceEntity();
		// TODO Auto-generated method stub return null;
		Map<String, Object> mapsMap=new HashMap<String, Object>();
		//判断是否满足提前还款
		mapsMap.put("applyId", appplyId);
		mapsMap=selectThreePartyDaoImpl.EarlyRepayment(mapsMap);
		int result=Integer.parseInt(mapsMap.get("result").toString());
		results[0]=result;
		if(result==0){//满足条件
			//查询提前还款总共应还本金
			mapsMap.put("skey", DbKeyUtil.GetDbCodeKey());
			mapsMap.put("applyId", appplyId);
			//long lamoney=selectThreePartyDaoImpl.selectAllmoneyForEarlyRepayment(mapsMap);
			//查询出提前还款违约金收取费率
			Map<String, Object> map=new HashMap<String, Object>();
			map=selectThreePartyDaoImpl.findPenaltyRate(mapsMap);
			long investRate=Long.parseLong(map.get("Penalty_Invest_Rate").toString());//支付投资人
			long pingTaiRate=Long.parseLong(map.get("Penalty_PingTai_Rate").toString());//支付平台
			//查询出所有的投资人以及投资记录
			List<RepayDetailEntity> liDetailEntities=selectThreePartyDaoImpl
																.selectAllInvestForEarlyRepayment(mapsMap);
			//总还款金额
			long lallmoney=0;
			//总还款本金
			long lPrincipal=0;
			//总还款利息
			long lInterest=0;
			//总还款违约金
			long lPenalty=0;
			//总投资管理费
			long lMngFee=0;
			//
			String sInfo="";
			if (liDetailEntities.size()>0) {
				//查询出投资会员应收总本金
				for (int i = 0; i < liDetailEntities.size(); i++) {
					Map<String, Object> smap=new HashMap<String, Object>();
					smap.put("skey", DbKeyUtil.GetDbCodeKey());
					smap.put("investId", liDetailEntities.get(i).getiInvestId());
					liDetailEntities.get(i).setlPrincipal(selectThreePartyDaoImpl.selectRevenuePlan(smap));
					lPrincipal+=liDetailEntities.get(i).getlPrincipal();
					//查询投资人乾多多标识
					map.put("memberType", 0);//会员类型
					map.put("memberID", liDetailEntities.get(i).getiMemberId());//会员id
					liDetailEntities.get(i).setsMark(selectThreePartyDaoImpl.findMemberThirdPartyMark(map));
					//计算出当期开始截止提前还款时应收收益
					int protype=selectThreePartyDaoImpl.projectDurationType(mapsMap);//期限类型
					long linver=selectThreePartyDaoImpl.findInterestByMember(mapsMap);//当期还款总需收取利息
					long times=selectThreePartyDaoImpl.findTimesByMember(smap);//剩余几天到还款日
					if (protype==0) {//月标
						linver=linver-(linver/30*times);
					}
					lInterest+=linver;
					liDetailEntities.get(i).setlInterest(linver);//应收当期截止收益
					//计算出利息管理费
					long lmng=0;//管理费费率
					long as=linver*lmng/100;
					lMngFee+=as;
					liDetailEntities.get(i).setlMngFee(as);
					liDetailEntities.get(i).setsMngFee(IntegerAndString.LongToString(as));
					//计算出平台收取提前还款违约金
					if (pingTaiRate>0) {
						long pTaiRate=liDetailEntities.get(i).getlPrincipal()*pingTaiRate;
						linver+=pTaiRate;
						liDetailEntities.get(i).setlPenalty(pTaiRate);
						liDetailEntities.get(i).setsPenalty(IntegerAndString.LongToString(pTaiRate));
						lPenalty+=pTaiRate;
					}
					//计算出投资人收取提前还款违约金
					if (investRate>0) {
						long ir=liDetailEntities.get(i).getlPrincipal()*investRate;
						linver+=ir;
						liDetailEntities.get(i).setlPenaltyInvest(ir);
						liDetailEntities.get(i).setsPenaltyInvest(IntegerAndString.LongToString(investRate));
						lPenalty+=ir;
					}
					linver+=liDetailEntities.get(i).getlPrincipal();//收益总金额
					liDetailEntities.get(i).setlAmount(linver);
					liDetailEntities.get(i).setsAmount(IntegerAndString.LongToString(linver));
					
					
					
					
					liDetailEntities.get(i).setsOrderNo(handleThreePartyDaoImpl.generateorderNo("TQHK"));//随机订单号生成
					sInfo = liDetailEntities.get(i).getsDetail();
					// 投资记录id+本金+利息+总逾期费+平台收取违约金+投资人收取违约金+投资管理费+0+投资人收取逾期费+平台收取逾期费+投资会员id+投资会员类型+该笔平台订单号
					sInfo = sInfo + liDetailEntities.get(i).getiInvestId() + "A" 
					+ lPrincipal + "A" + lInterest + "A" + "0" 
							+ "A" + liDetailEntities.get(i).getlPenalty() +"A"+liDetailEntities.get(i).getsPenaltyInvest()+ "A" 
					+ as + "A" + "0" + "A"+ "0" + "A"+ "0" + "A" + liDetailEntities.get(i).getiMemberId() + "A" + liDetailEntities.get(i).getiMemberType()+"A"+liDetailEntities.get(i).getsOrderNo();
					liDetailEntities.get(i).setsDetail(sInfo);
					
					//					else if(protype==1) {//天标
//						linver=linver;
//					}
					//IntegerAndString.StringToString(str)
				}
				repayInterfaceEntity.setDetailList(liDetailEntities);
				lallmoney=lPrincipal+lInterest+lPenalty;
				repayInterfaceEntity.setlAmount(lallmoney);
				repayInterfaceEntity.setsAmount(IntegerAndString.LongToString(lallmoney));
				repayInterfaceEntity.setlInterest(lInterest);
				repayInterfaceEntity.setsInterest(IntegerAndString.LongToString(lInterest));
				repayInterfaceEntity.setlPrincipal(lPrincipal);
				repayInterfaceEntity.setsPrincipal(IntegerAndString.LongToString(lPrincipal));
				repayInterfaceEntity.setlPenalty(lPenalty);
				repayInterfaceEntity.setsPenalty(IntegerAndString.LongToString(lPenalty));
				repayInterfaceEntity.setlMngFee(lMngFee);
				repayInterfaceEntity.setsMngFee(IntegerAndString.LongToString(lMngFee));
				//还款会员id
				repayInterfaceEntity.setiMemberId(lonmemberId);
				repayInterfaceEntity.setiMemberType(lonmembertype);
				//查询还款会员乾多多标识
				Map<String, Object> lomap=new HashMap<String, Object>();
				lomap.put("memberType", lonmembertype);//会员类型
				lomap.put("memberID", lonmemberId);//会员id
				repayInterfaceEntity.setsMark(selectThreePartyDaoImpl.findMemberThirdPartyMark(lomap));
				
			}
			//
			//
			return repayInterfaceEntity;
		}else {
			return null;
		}
	}


	
	/* *  *  * @param lonmemberId
	/* *  *  * @param lonmembertype
	/* *  *  * @param appplyId
	/* *  *  * @param results
	/* *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#RepaymentProcessing(long, int, long, int[]) */
	@Override
	public RepayInterfaceEntity RepaymentProcessing(long lonmemberId,
			int lonmembertype, long appplyId,long repalyId, int[] results) {
		
		// TODO Auto-generated method stub return null;
		RepayInterfaceEntity repayInterfaceEntity=new RepayInterfaceEntity();
		// TODO Auto-generated method stub return null;
		Map<String, Object> mapsMap=new HashMap<String, Object>();
		//判断是否满足提前还款
		mapsMap.put("applyId", appplyId);
		long result=selectThreePartyDaoImpl.RepayStatu(mapsMap);
		//int result=Integer.parseInt(mapsMap.get("result").toString());
		results[0]=(int)result;
		if(result==0){//满足条件
			//查询还款总共应还本金
			mapsMap.put("skey", DbKeyUtil.GetDbCodeKey());
			mapsMap.put("applyId", appplyId);
			//long lamoney=selectThreePartyDaoImpl.selectAllmoneyForEarlyRepayment(mapsMap);
			//查询出还款违约金收取费率
			
			long investRate=0;//支付投资人
			long pingTaiRate=0;//支付平台
			//查询出所有的投资人以及投资记录
			List<RepayDetailEntity> liDetailEntities=selectThreePartyDaoImpl
																.selectAllInvestForEarlyRepayment(mapsMap);
			//总还款金额
			long lallmoney=0;
			//总还款本金
			long lPrincipal=0;
			//总还款利息
			long lInterest=0;
			//总还款违约金
			long lPenalty=0;
			//总投资管理费
			long lMngFee=0;
			//总逾期费
			long lOverdue=0;
			//总的逾期利息
			long lOverdueInterest=0;
			String sInfo="";
			if (liDetailEntities.size()>0) {
				//查询出投资会员应收总本金
				for (int i = 0; i < liDetailEntities.size(); i++) {
					Map<String, Object> smap=new HashMap<String, Object>();
					//根据投资记录id、还款计划id 查询会员当期应收本金
					smap.put("skey", DbKeyUtil.GetDbCodeKey());
					smap.put("investId", liDetailEntities.get(i).getiInvestId());
					smap.put("repayId", repalyId);
					liDetailEntities.get(i).setlPrincipal(selectThreePartyDaoImpl.selectPrincipalByID(smap));
					lPrincipal+=liDetailEntities.get(i).getlPrincipal();
					//计算出当期应收收益
					long linver=selectThreePartyDaoImpl.findInterestByOne(smap);//当期还款总需收取利息
					lInterest+=linver;
					liDetailEntities.get(i).setlInterest(linver);//应收当期收益
					//查询投资人乾多多标识
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("memberType", 0);//会员类型
					map.put("memberID", liDetailEntities.get(i).getiMemberId());//会员id
					liDetailEntities.get(i).setsMark(selectThreePartyDaoImpl.findMemberThirdPartyMark(map));
					
					//计算出利息管理费
					long lmng=0;//管理费费率
					long as=linver*lmng/100;
					lMngFee+=as;
					liDetailEntities.get(i).setlMngFee(as);
					liDetailEntities.get(i).setsMngFee(IntegerAndString.LongToString(as));
					//计算出平台收取提前还款违约金
					if (pingTaiRate>0) {
						long pTaiRate=0;
						linver+=pTaiRate;
						liDetailEntities.get(i).setlPenalty(pTaiRate);
						liDetailEntities.get(i).setsPenalty(IntegerAndString.LongToString(pTaiRate));
						lPenalty+=pTaiRate;
					}
					//计算出投资人收取提前还款违约金
					if (investRate>0) {
						long ir=0;
						linver+=ir;
						liDetailEntities.get(i).setlPenaltyInvest(ir);
						liDetailEntities.get(i).setsPenaltyInvest(IntegerAndString.LongToString(investRate));
						lPenalty+=ir;
					}
					linver+=liDetailEntities.get(i).getlPrincipal();//收益总金额
					liDetailEntities.get(i).setlAmount(linver);
					liDetailEntities.get(i).setsAmount(IntegerAndString.LongToString(linver));
					//计算逾期费
					//long allOverdue=0;//总应收逾期费
					//查询出逾期天数
					Map<String, Object> mapday=new HashMap<String, Object>();
					mapday.put("repayId", repalyId);
					mapday.put("investId", liDetailEntities.get(i).getiInvestId());
					long idays=selectThreePartyDaoImpl.findDayByOverdue(mapday);
					if (idays>0) {//逾期
						//收益计划id
						long incomeId=selectThreePartyDaoImpl.findIncomeId(mapday);
						//查询出逾期利率
						map.put("iDays", idays);//逾期天数
						selectThreePartyDaoImpl.findOverdueFees(map);
						//根据收益计划id查询投资人预计还款逾期罚息和罚金
						mapday.put("skey", DbKeyUtil.GetDbCodeKey());
						mapday.put("incomeId", incomeId);
						Map<String, Object> mapOvder = selectThreePartyDaoImpl
									.InvestIncomeOverdueFee(mapday);
						long repayOverdueInterest=Long.parseLong(mapOvder.get("Repay_Overdue_Interest").toString());
						long repayOverdue=Long.parseLong(mapOvder.get("Repay_Overdue").toString());
						//查询出之前投资人已收的所有罚金和罚息
						Map<String, Object> mapallOvder = selectThreePartyDaoImpl
								.allInvestRealIncome(mapday);
						long secrepayOverdueInterest=Long.parseLong(mapOvder.get("Repay_Overdue_Interest").toString());
						long secrepayOverdue=Long.parseLong(mapOvder.get("Repay_Overdue").toString());
						//计算出部分还款过后未还完的罚息和罚金
						liDetailEntities.get(i).setlOverdue(secrepayOverdue-repayOverdue);
						liDetailEntities.get(i).setlOverdueInterest(secrepayOverdueInterest-repayOverdueInterest);
						if (liDetailEntities.get(i).getlOverdue()<0) {
							liDetailEntities.get(i).setlOverdue(0);
						}
						if (liDetailEntities.get(i).getlOverdueInterest()<0) {
							liDetailEntities.get(i).setlOverdueInterest(0);
						}
						
						Map<String, Object> ma=new HashMap<String, Object>();
						ma.put("repayId", repalyId);
						Timestamp ttLastPay = null;		//上次计算逾期费时间
						Timestamp ttNow = RepayDetailEntity.GetCurrentDateTime();
						//根据还款计划id查询当期预计还款时间
						//selectThreePartyDaoImpl.overdueRepayMaxTime(mapallOvder);
						
						//根据还款计划id查询最近一次逾期还款时间
						ttLastPay=selectThreePartyDaoImpl.overdueRecvTime(mapallOvder);
						if(ttLastPay!=null){
							idays=RepayDetailEntity.GetDateSub(ttLastPay, ttNow);
						}
						//查询出未收取逾期费用
						// 查找逾期费
						long[] lOverdue1 = {0,0};
						if(idays>0){
							Map<String, Object> mad=new HashMap<String, Object>();
							mad.put("iDays", idays);
							long lRate=selectThreePartyDaoImpl.findOverdueFees(mapallOvder);
							long lYearRate=selectThreePartyDaoImpl.ProjectBaseInfoYearRate(mapsMap);
							RepayDetailEntity.GetYuqiAmount(idays, 
									liDetailEntities.get(i).getlPrincipal(), lYearRate, lOverdue1, lRate);
							liDetailEntities.get(i).setlThisOdInterest(lOverdue1[0]);
							liDetailEntities.get(i).setlThisOverdue(lOverdue1[1]);
							liDetailEntities.get(i).setlOverdue(liDetailEntities.get(i).getlOverdue() + lOverdue1[1]);
							liDetailEntities.get(i).setlOverdueInterest(liDetailEntities.get(i).getlOverdueInterest() + lOverdue1[0]);
							liDetailEntities.get(i).setsOverdue(IntegerAndString.LongToString(liDetailEntities.get(i).getlOverdue()));
							liDetailEntities.get(i).setsOverdueInterest(IntegerAndString.LongToString(liDetailEntities.get(i).getlOverdueInterest()));
							lOverdue += liDetailEntities.get(i).getlOverdue();
							lOverdueInterest += liDetailEntities.get(i).getlOverdueInterest();
						}
						
						
						
					}
					long iAmount = (liDetailEntities.get(i).getlPrincipal() + liDetailEntities.get(i).getlInterest() + liDetailEntities.get(i).getlOverdue() + liDetailEntities.get(i).getlOverdueInterest() 
							+ liDetailEntities.get(i).getlPenalty() );
					liDetailEntities.get(i).setlAmount(iAmount);
					liDetailEntities.get(i).setsAmount(IntegerAndString.LongToString(iAmount));
					liDetailEntities.get(i).setsOrderNo(handleThreePartyDaoImpl.generateorderNo("ZCHK"));//随机订单号生成
					
					liDetailEntities.get(i).setId(selectThreePartyDaoImpl.findIncomeId(mapday));
					
					sInfo = liDetailEntities.get(i).getsDetail();
					// 投资记录id+本金+利息+总逾期费+平台收取违约金+投资人收取违约金+投资管理费+0+投资人收取逾期费+预期利息+投资会员id+投资会员类型+该笔平台订单号
					sInfo = sInfo + liDetailEntities.get(i).getId() + "A" 
								+ liDetailEntities.get(i).getlPrincipal() + "A" 
								+ liDetailEntities.get(i).getlInterest() + "A" 
								+ liDetailEntities.get(i).getlOverdue() + "A" 
								+ liDetailEntities.get(i).getlPenalty() +"A"
								+liDetailEntities.get(i).getsPenaltyInvest()+ "A" 
								+ as + "A" + "1" + "A"+ liDetailEntities.get(i).getlOverdue()
								+ "A"+ liDetailEntities.get(i).getlOverdueInterest() + "A" + liDetailEntities.get(i).getiMemberId() + "A" + liDetailEntities.get(i).getiMemberType()+"A"+liDetailEntities.get(i).getsOrderNo();
					liDetailEntities.get(i).setsDetail(sInfo);
					
					//					else if(protype==1) {//天标
//						linver=linver;
//					}
					//IntegerAndString.StringToString(str)
				}
				repayInterfaceEntity.setDetailList(liDetailEntities);
				lallmoney=lPrincipal+lInterest+lPenalty+lOverdue;
				repayInterfaceEntity.setlAmount(lallmoney);
				repayInterfaceEntity.setsAmount(IntegerAndString.LongToString(lallmoney));
				repayInterfaceEntity.setlInterest(lInterest);
				repayInterfaceEntity.setsInterest(IntegerAndString.LongToString(lInterest));
				repayInterfaceEntity.setlPrincipal(lPrincipal);
				repayInterfaceEntity.setsPrincipal(IntegerAndString.LongToString(lPrincipal));
				repayInterfaceEntity.setlPenalty(lPenalty);
				repayInterfaceEntity.setsPenalty(IntegerAndString.LongToString(lPenalty));
				repayInterfaceEntity.setlMngFee(lMngFee);
				repayInterfaceEntity.setsMngFee(IntegerAndString.LongToString(lMngFee));
				repayInterfaceEntity.setlOverdueInterest(lOverdueInterest);
				//还款会员id
				repayInterfaceEntity.setiMemberId(lonmemberId);
				repayInterfaceEntity.setiMemberType(lonmembertype);
				//查询还款会员乾多多标识
				Map<String, Object> lomap=new HashMap<String, Object>();
				lomap.put("memberType", lonmembertype);//会员类型
				lomap.put("memberID", lonmemberId);//会员id
				repayInterfaceEntity.setsMark(selectThreePartyDaoImpl.findMemberThirdPartyMark(lomap));
				
			}
			//
			//
			return repayInterfaceEntity;
		}else {
			return null;
		}
	}
	@Override
	public RepayInterfaceEntity GetRepayInfoById(long lId, long applayid,long lmoney,
			int isUpdate, int[] iStatu,long lonmemberId, int lonmembertype) {
		RepayInterfaceEntity entity = new RepayInterfaceEntity();
		// TODO Auto-generated method stub return null;
		if(lId==-1){//提前还款
			//entity=EarlyRepaymentProcessing(lonmemberId, lonmembertype, appplyId, results);
		}else {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("repayId", lId);
			long sat=selectThreePartyDaoImpl.RepayStatu(map);
			if(sat==0){//未还
				//
				entity=RepaymentProcessing(lonmemberId, lonmembertype, applayid,
						lId, iStatu);
				//计算本次还款总需金额是否满足账户可用余额
				if(lmoney>=entity.getlAmount()){//满足条件 当期还款可全部还款完成
					return entity;
				}
				List<RepayDetailEntity> repList=entity.getDetailList();
				RepayDetailEntity reEntity=new RepayDetailEntity();
				//判断是否可以满足还本金
				if (lmoney>=entity.getlPrincipal()) {//可先把本金还完
					lmoney=lmoney-entity.getlPrincipal();
				}else {//只能偿还部分本金
					long bmon=0;
					long cou=0;
					if (repList.size()>0) {
						for (int i = 0; i < repList.size(); i++) {
							reEntity=repList.get(i);
							bmon=reEntity.getlPrincipal()*lmoney/entity.getlPrincipal();
							if(bmon>0){
								cou+=bmon;
								if (i+1==repList.size()) {
									reEntity.setlAmount(reEntity.getlAmount()+bmon+(lmoney-cou)-reEntity.getlPrincipal());
									reEntity.setlPrincipal(bmon+(lmoney-cou));
									cou+=(lmoney-cou);
								}else {
									reEntity.setlAmount(reEntity.getlAmount()+bmon-reEntity.getlPrincipal());
									reEntity.setlPrincipal(bmon);
								}
							}else {
								cou+=0;
							}
							String detail=reEntity.getsDetail();
							String [] det=detail.split("A");
							det[1]=reEntity.getlPrincipal()+"";
							String sdet="";
							for (int j = 0; j < det.length; j++) {
								if (sdet!="") {
									sdet+="A"+det[i];
								}else {
									sdet+=det[i]+"A";
								}
							}
							reEntity.setsDetail(sdet);
						}
					}
					entity.setlAmount(entity.getlAmount()+lmoney-entity.getlPrincipal());
					entity.setlPrincipal(lmoney);
					lmoney=lmoney-cou;
				}
				//判断是否满足还利息
				if (lmoney>=entity.getlInterest()) {//可全部还完利息
					lmoney=lmoney-entity.getlInterest();
				}else {//只能还部分利息
					long bmon=0;
					long cou=0;
					if (repList.size()>0) {
						for (int i = 0; i < repList.size(); i++) {
							reEntity=repList.get(i);
							bmon=reEntity.getlInterest()*lmoney/entity.getlInterest();
							if(bmon>0){
								cou+=bmon;
								if (i+1==repList.size()) {
									reEntity.setlAmount(reEntity.getlAmount()+bmon+(lmoney-cou)-reEntity.getlInterest());
									reEntity.setlInterest(bmon+(lmoney-cou));
									cou+=(lmoney-cou);
								}else {
									reEntity.setlAmount(reEntity.getlAmount()+bmon-reEntity.getlInterest());
									reEntity.setlInterest(bmon);
								}
							}else {
								cou+=0;
							}
							String detail=reEntity.getsDetail();
							String [] det=detail.split("A");
							det[2]=reEntity.getlInterest()+"";
							String sdet="";
							for (int j = 0; j < det.length; j++) {
								if (sdet!="") {
									sdet+="A"+det[i];
								}else {
									sdet+=det[i]+"A";
								}
							}
							reEntity.setsDetail(sdet);
						}
					}
					entity.setlAmount(entity.getlAmount()+lmoney-entity.getlInterest());
					entity.setlInterest(lmoney);
					lmoney=lmoney-cou;
				}
				//判断是否满足还逾期罚金
				if (lmoney>=entity.getlOverdue()) {//可全部还完逾期罚金
					lmoney=lmoney-entity.getlOverdue();
				}else {//只能还部分逾期罚金
					long bmon=0;
					long cou=0;
					if (repList.size()>0) {
						for (int i = 0; i < repList.size(); i++) {
							reEntity=repList.get(i);
							bmon=reEntity.getlOverdueInterest()*lmoney/entity.getlOverdueInterest();
							if(bmon>0){
								cou+=bmon;
								if (i+1==repList.size()) {
									reEntity.setlAmount(reEntity.getlAmount()+bmon+(lmoney-cou)-reEntity.getlOverdueInterest());
									reEntity.setlOverdueInterest(bmon+(lmoney-cou));
									cou+=(lmoney-cou);
								}else {
									reEntity.setlAmount(reEntity.getlAmount()+bmon-reEntity.getlOverdueInterest());
									reEntity.setlOverdueInterest(bmon);
								}
							}else {
								cou+=0;
							}
							String detail=reEntity.getsDetail();
							String [] det=detail.split("A");
							det[8]=reEntity.getlOverdueInterest()+"";
							String sdet="";
							for (int j = 0; j < det.length; j++) {
								if (sdet!="") {
									sdet+="A"+det[i];
								}else {
									sdet+=det[i]+"A";
								}
							}
							reEntity.setsDetail(sdet);
						}
					}
					entity.setlAmount(entity.getlAmount()+lmoney-entity.getlOverdueInterest());
					entity.setlOverdue(lmoney);
					lmoney=lmoney-cou;
				}
			}
		}
		
		return entity;
	}
	
	
	

	/* *  *  * @param lonmemberId
	/* *  *  * @param lonmembertype
	/* *  *  * @param appplyId
	/* *  *  * @param results
	/* *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#RepaymentProcessing(long, int, long, int[]) */
	@Override
	public RepayInterfaceEntity Compensatory(long lonmemberId,
			int lonmembertype, long appplyId,long repalyId, int[] results,long daicangId,int daicangtype) {
		
		// TODO Auto-generated method stub return null;
		RepayInterfaceEntity repayInterfaceEntity=new RepayInterfaceEntity();
		// TODO Auto-generated method stub return null;
		Map<String, Object> mapsMap=new HashMap<String, Object>();
		//判断是否满足代偿
		mapsMap.put("applyId", appplyId);
		int result=selectThreePartyDaoImpl.CompensatoryJudgment(mapsMap);
		results[0]=result;
		if(result==0){//满足条件
			//查询提前还款总共应还本金
			mapsMap.put("skey", DbKeyUtil.GetDbCodeKey());
			mapsMap.put("applyId", appplyId);
			//long lamoney=selectThreePartyDaoImpl.selectAllmoneyForEarlyRepayment(mapsMap);
			//查询违约金收取费率
			
			long investRate=0;//支付投资人
			long pingTaiRate=0;//支付平台
			//查询出所有的投资人以及投资记录
			List<RepayDetailEntity> liDetailEntities=selectThreePartyDaoImpl
																.selectAllInvestForEarlyRepayment(mapsMap);
			//总还款金额
			long lallmoney=0;
			//总还款本金
			long lPrincipal=0;
			//总还款利息
			long lInterest=0;
			//总还款违约金
			long lPenalty=0;
			//总投资管理费
			long lMngFee=0;
			//总逾期费
			long lOverdue=0;
			//总的逾期利息
			long lOverdueInterest=0;
			String sInfo="";
			if (liDetailEntities.size()>0) {
				//查询出投资会员应收总本金
				for (int i = 0; i < liDetailEntities.size(); i++) {
					Map<String, Object> smap=new HashMap<String, Object>();
					//根据投资记录id、还款计划id 查询会员当期应收本金
					smap.put("skey", DbKeyUtil.GetDbCodeKey());
					smap.put("investId", liDetailEntities.get(i).getiInvestId());
					smap.put("repayId", repalyId);
					liDetailEntities.get(i).setlPrincipal(selectThreePartyDaoImpl.selectPrincipalByID(smap));
					lPrincipal+=liDetailEntities.get(i).getlPrincipal();
					//计算出当期应收收益
					long linver=selectThreePartyDaoImpl.findInterestByOne(smap);//当期还款总需收取利息
					lInterest+=linver;
					liDetailEntities.get(i).setlInterest(linver);//应收当期收益
					//查询投资人乾多多标识
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("memberType", 0);//会员类型
					map.put("memberID", liDetailEntities.get(i).getiMemberId());//会员id
					liDetailEntities.get(i).setsMark(selectThreePartyDaoImpl.findMemberThirdPartyMark(map));
					
					//计算出利息管理费
					long lmng=0;//管理费费率
					long as=linver*lmng/100;
					lMngFee+=as;
					liDetailEntities.get(i).setlMngFee(as);
					liDetailEntities.get(i).setsMngFee(IntegerAndString.LongToString(as));
					//计算出平台收取提前还款违约金
					if (pingTaiRate>0) {
						long pTaiRate=0;
						linver+=pTaiRate;
						liDetailEntities.get(i).setlPenalty(pTaiRate);
						liDetailEntities.get(i).setsPenalty(IntegerAndString.LongToString(pTaiRate));
						lPenalty+=pTaiRate;
					}
					//计算出投资人收取提前还款违约金
					if (investRate>0) {
						long ir=0;
						linver+=ir;
						liDetailEntities.get(i).setlPenaltyInvest(ir);
						liDetailEntities.get(i).setsPenaltyInvest(IntegerAndString.LongToString(investRate));
						lPenalty+=ir;
					}
					linver+=liDetailEntities.get(i).getlPrincipal();//收益总金额
					liDetailEntities.get(i).setlAmount(linver);
					liDetailEntities.get(i).setsAmount(IntegerAndString.LongToString(linver));
					//计算逾期费
					//long allOverdue=0;//总应收逾期费
					//查询出逾期天数
					Map<String, Object> mapday=new HashMap<String, Object>();
					mapday.put("repayId", repalyId);
					mapday.put("investId", liDetailEntities.get(i).getiInvestId());
					long idays=selectThreePartyDaoImpl.findDayByOverdue(mapday);
					if (idays>0) {//逾期
						//收益计划id
						long incomeId=selectThreePartyDaoImpl.findIncomeId(mapday);
						//查询出逾期利率
						map.put("iDays", idays);//逾期天数
						selectThreePartyDaoImpl.findOverdueFees(map);
						//根据收益计划id查询投资人预计还款逾期罚息和罚金
						mapday.put("skey", DbKeyUtil.GetDbCodeKey());
						mapday.put("incomeId", incomeId);
						Map<String, Object> mapOvder = selectThreePartyDaoImpl
									.InvestIncomeOverdueFee(mapday);
						long repayOverdueInterest=Long.parseLong(mapOvder.get("Repay_Overdue_Interest").toString());
						long repayOverdue=Long.parseLong(mapOvder.get("Repay_Overdue").toString());
						//查询出之前投资人已收的所有罚金和罚息
						Map<String, Object> mapallOvder = selectThreePartyDaoImpl
								.allInvestRealIncome(mapday);
						long secrepayOverdueInterest=Long.parseLong(mapOvder.get("Repay_Overdue_Interest").toString());
						long secrepayOverdue=Long.parseLong(mapOvder.get("Repay_Overdue").toString());
						//计算出部分还款过后未还完的罚息和罚金
						liDetailEntities.get(i).setlOverdue(secrepayOverdue-repayOverdue);
						liDetailEntities.get(i).setlOverdueInterest(secrepayOverdueInterest-repayOverdueInterest);
						if (liDetailEntities.get(i).getlOverdue()<0) {
							liDetailEntities.get(i).setlOverdue(0);
						}
						if (liDetailEntities.get(i).getlOverdueInterest()<0) {
							liDetailEntities.get(i).setlOverdueInterest(0);
						}
						
						Map<String, Object> ma=new HashMap<String, Object>();
						ma.put("repayId", repalyId);
						Timestamp ttLastPay = null;		//上次计算逾期费时间
						Timestamp ttNow = RepayDetailEntity.GetCurrentDateTime();
						//根据还款计划id查询当期预计还款时间
						//selectThreePartyDaoImpl.overdueRepayMaxTime(mapallOvder);
						
						//根据还款计划id查询最近一次逾期还款时间
						ttLastPay=selectThreePartyDaoImpl.overdueRecvTime(mapallOvder);
						if(ttLastPay!=null){
							idays=RepayDetailEntity.GetDateSub(ttLastPay, ttNow);
						}
						//查询出未收取逾期费用
						// 查找逾期费
						long[] lOverdue1 = {0,0};
						if(idays>0){
							Map<String, Object> mad=new HashMap<String, Object>();
							mad.put("iDays", idays);
							long lRate=selectThreePartyDaoImpl.findOverdueFees(mapallOvder);
							long lYearRate=selectThreePartyDaoImpl.ProjectBaseInfoYearRate(mapsMap);
							RepayDetailEntity.GetYuqiAmount(idays, 
									liDetailEntities.get(i).getlPrincipal(), lYearRate, lOverdue1, lRate);
							liDetailEntities.get(i).setlThisOdInterest(lOverdue1[0]);
							liDetailEntities.get(i).setlThisOverdue(lOverdue1[1]);
							liDetailEntities.get(i).setlOverdue(liDetailEntities.get(i).getlOverdue() + lOverdue1[1]);
							liDetailEntities.get(i).setlOverdueInterest(liDetailEntities.get(i).getlOverdueInterest() + lOverdue1[0]);
							liDetailEntities.get(i).setsOverdue(IntegerAndString.LongToString(liDetailEntities.get(i).getlOverdue()));
							liDetailEntities.get(i).setsOverdueInterest(IntegerAndString.LongToString(liDetailEntities.get(i).getlOverdueInterest()));
							lOverdue += liDetailEntities.get(i).getlOverdue();
							lOverdueInterest += liDetailEntities.get(i).getlOverdueInterest();
						}
					}
					long iAmount = (liDetailEntities.get(i).getlPrincipal() + liDetailEntities.get(i).getlInterest() + liDetailEntities.get(i).getlOverdue() + liDetailEntities.get(i).getlOverdueInterest() 
							+ liDetailEntities.get(i).getlPenalty() );
					liDetailEntities.get(i).setlAmount(iAmount);
					liDetailEntities.get(i).setsAmount(IntegerAndString.LongToString(iAmount));
					liDetailEntities.get(i).setsOrderNo(handleThreePartyDaoImpl.generateorderNo("ZCHK"));//随机订单号生成
					
					liDetailEntities.get(i).setId(selectThreePartyDaoImpl.findIncomeId(mapday));
					
					sInfo = liDetailEntities.get(i).getsDetail();
					// 投资记录id+本金+利息+总逾期费+平台收取违约金+投资人收取违约金+投资管理费+0+投资人收取逾期费+预期利息+投资会员id+投资会员类型+该笔平台订单号
					sInfo = sInfo + liDetailEntities.get(i).getId() + "A" 
								+ liDetailEntities.get(i).getlPrincipal() + "A" 
								+ liDetailEntities.get(i).getlInterest() + "A" 
								+ liDetailEntities.get(i).getlOverdue() + "A" 
								+ liDetailEntities.get(i).getlPenalty() +"A"
								+liDetailEntities.get(i).getsPenaltyInvest()+ "A" 
								+ as + "A" + "1" + "A"+ liDetailEntities.get(i).getlOverdue()
								+ "A"+ liDetailEntities.get(i).getlOverdueInterest() + "A" + liDetailEntities.get(i).getiMemberId() + "A" + liDetailEntities.get(i).getiMemberType()+"A"+liDetailEntities.get(i).getsOrderNo();
					liDetailEntities.get(i).setsDetail(sInfo);
					
					// 逾期天数idays，代偿方id daicangId，代偿方类型 daicangtype 
					
				}
				
				
				repayInterfaceEntity.setDetailList(liDetailEntities);
				lallmoney=lPrincipal+lInterest+lPenalty+lOverdue;
				repayInterfaceEntity.setlAmount(lallmoney);
				repayInterfaceEntity.setsAmount(IntegerAndString.LongToString(lallmoney));
				repayInterfaceEntity.setlInterest(lInterest);
				repayInterfaceEntity.setsInterest(IntegerAndString.LongToString(lInterest));
				repayInterfaceEntity.setlPrincipal(lPrincipal);
				repayInterfaceEntity.setsPrincipal(IntegerAndString.LongToString(lPrincipal));
				repayInterfaceEntity.setlPenalty(lPenalty);
				repayInterfaceEntity.setsPenalty(IntegerAndString.LongToString(lPenalty));
				repayInterfaceEntity.setlMngFee(lMngFee);
				repayInterfaceEntity.setsMngFee(IntegerAndString.LongToString(lMngFee));
				repayInterfaceEntity.setlOverdueInterest(lOverdueInterest);
				//还款会员id
				repayInterfaceEntity.setiMemberId(daicangId);
				repayInterfaceEntity.setiMemberType(daicangtype);
				//查询还款会员乾多多标识
				Map<String, Object> lomap=new HashMap<String, Object>();
				lomap.put("memberType", daicangtype);//会员类型
				lomap.put("memberID", daicangId);//代偿机构id
				repayInterfaceEntity.setsMark(selectThreePartyDaoImpl.findMemberThirdPartyMark(lomap));
				
			}
			//
			//
			return repayInterfaceEntity;
		}else {
			return null;
		}
	}
	
	/* *  *  * @param appplyId
	/* *  *  * @param repalyId
	/* *  *  * @param results
	/* *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#CompensatoryPayment(long, long, int[]) */
	@Override
	public RepayInterfaceEntity CompensatoryPayment(long lonmemberId,int lonmembertype,long appplyId,
			long repalyId, int[] results) {
		RepayInterfaceEntity repayInterfaceEntity=new RepayInterfaceEntity();
		//判断是否符合代偿回款
		Map<String, Object> lomap=new HashMap<String, Object>();
		lomap.put("replayId", repalyId);
		int relu=selectThreePartyDaoImpl.CompensatoryPaymentJudgment(lomap);
		if(relu==0){
			Map<String, Object> mapsMap=new HashMap<String, Object>();
			mapsMap.put("skey", DbKeyUtil.GetDbCodeKey());
			mapsMap.put("applyId", appplyId);
			List<RepayDetailEntity> liDetailEntities=new ArrayList<RepayDetailEntity>();
			//总还款金额
			long lallmoney=0;
			//总还款本金
			long lPrincipal=0;
			//总还款利息
			long lInterest=0;
			//总还款违约金
			long lPenalty=0;
			//总投资管理费
			long lMngFee=0;
			//总逾期罚息
			long lOverdue=0;
			//总的逾期利息
			long lOverdueInterest=0;
			
			//查询出还款人当期未还的本金、利息、逾期罚金、逾期利息
			Map<String, Object> mapm=new HashMap<String, Object>();
			
			mapm=selectThreePartyDaoImpl.sumJudgmentMoney(mapm);
			lPrincipal=Long.parseLong(mapm.get("Corpus").toString());
			lInterest=Long.parseLong(mapm.get("Interes").toString());
			lOverdue=Long.parseLong(mapm.get("OverdueInterest").toString());
			lOverdueInterest=Long.parseLong(mapm.get("OverdueFee").toString());
			int memberType=Integer.parseInt(mapm.get("Repay_Type").toString());//代偿方类型
			if(memberType==0){
				memberType=1;//担保机构
			}else {
				memberType=2;//平台
			}
			long memberId=Long.parseLong(mapm.get("Guarantee_ID").toString());//代偿方id
			mapm.get("outDay");
			RepayDetailEntity repayDetailEntity= new RepayDetailEntity();
			repayDetailEntity.setiMemberId(memberId);
			repayDetailEntity.setiMemberType(memberType);
			repayDetailEntity.setlPrincipal(lPrincipal);
			repayDetailEntity.setlInterest(lInterest);
			repayDetailEntity.setlOverdue(lOverdue);
			repayDetailEntity.setlOverdueInterest(lOverdueInterest);
			liDetailEntities.add(repayDetailEntity);
			String sInfo="";
			if (liDetailEntities.size()>0) {
				//查询逾期罚金和罚息
				for (int i = 0; i < liDetailEntities.size(); i++) {
					//Map<String, Object> smap=new HashMap<String, Object>();
					//查询代偿方乾多多标识
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("memberType", liDetailEntities.get(i).getiMemberType());//会员类型
					map.put("memberID", liDetailEntities.get(i).getiMemberId());//会员id
					liDetailEntities.get(i).setsMark(selectThreePartyDaoImpl.findMemberThirdPartyMark(map));
					
					//计算逾期费
					//long allOverdue=0;//总应收逾期费
					//查询出逾期天数
					Map<String, Object> mapday=new HashMap<String, Object>();
					mapday.put("repayId", repalyId);
					long idays=selectThreePartyDaoImpl.findDayByOverdueNow(mapday);
					if (idays>0) {//逾期
						//收益计划id
						//long incomeId=selectThreePartyDaoImpl.findIncomeId(mapday);
						//查询出逾期利率
						map.put("iDays", idays);//逾期天数
						//long price=selectThreePartyDaoImpl.findOverdueFees(map);
						//查询出已还的所有逾期
						
						
						
						Map<String, Object> ma=new HashMap<String, Object>();
						ma.put("repayId", repalyId);
						Timestamp ttLastPay = null;		//上次计算逾期费时间
						Timestamp ttNow = RepayDetailEntity.GetCurrentDateTime();
						//根据还款计划id查询当期预计还款时间
						//selectThreePartyDaoImpl.overdueRepayMaxTime(mapallOvder);
						
						//根据还款计划id查询最近一次逾期还款时间
						ttLastPay=(Timestamp) mapm.get("Record_Date");
						if(ttLastPay!=null){
							idays=RepayDetailEntity.GetDateSub(ttLastPay, ttNow);
						}
						//查询出未收取逾期费用
						// 查找逾期费
						long[] lOverdue1 = {0,0};
						if(idays>0){
							Map<String, Object> mad=new HashMap<String, Object>();
							mad.put("iDays", idays);
							long lRate=selectThreePartyDaoImpl.findOverdueFees(mad);
							long lYearRate=selectThreePartyDaoImpl.ProjectBaseInfoYearRate(mapsMap);
							RepayDetailEntity.GetYuqiAmount(idays, 
									liDetailEntities.get(i).getlPrincipal(), lYearRate, lOverdue1, lRate);
							liDetailEntities.get(i).setlThisOdInterest(lOverdue1[0]);
							liDetailEntities.get(i).setlThisOverdue(lOverdue1[1]);
							liDetailEntities.get(i).setlOverdue(liDetailEntities.get(i).getlOverdue() + lOverdue1[1]);
							liDetailEntities.get(i).setlOverdueInterest(liDetailEntities.get(i).getlOverdueInterest() + lOverdue1[0]);
							liDetailEntities.get(i).setsOverdue(IntegerAndString.LongToString(liDetailEntities.get(i).getlOverdue()));
							liDetailEntities.get(i).setsOverdueInterest(IntegerAndString.LongToString(liDetailEntities.get(i).getlOverdueInterest()));
							lOverdue += liDetailEntities.get(i).getlOverdue();
							lOverdueInterest += liDetailEntities.get(i).getlOverdueInterest();
						}
					}
					long iAmount = (liDetailEntities.get(i).getlPrincipal() + liDetailEntities.get(i).getlInterest() + liDetailEntities.get(i).getlOverdue() + liDetailEntities.get(i).getlOverdueInterest() 
							+ liDetailEntities.get(i).getlPenalty() );
					liDetailEntities.get(i).setlAmount(iAmount);
					liDetailEntities.get(i).setsAmount(IntegerAndString.LongToString(iAmount));
					liDetailEntities.get(i).setsOrderNo(handleThreePartyDaoImpl.generateorderNo("DCHK"));//随机订单号生成
					
					liDetailEntities.get(i).setId(repalyId);
					
					sInfo = liDetailEntities.get(i).getsDetail();
					// 还款计划id+本金+利息+总逾期罚金+平台收取违约金+投资人收取违约金+投资管理费+0+投资人收取逾期费+预期利息+代偿方id+代偿方会员类型+该笔平台订单号
					sInfo = sInfo + liDetailEntities.get(i).getId() + "A" 
								+ liDetailEntities.get(i).getlPrincipal() + "A" 
								+ liDetailEntities.get(i).getlInterest() + "A" 
								+ liDetailEntities.get(i).getlOverdue() + "A" 
								+ liDetailEntities.get(i).getlPenalty() +"A"
								+liDetailEntities.get(i).getsPenaltyInvest()+ "A" 
								+ "0" + "A" + "1" + "A"+ liDetailEntities.get(i).getlOverdue()
								+ "A"+ liDetailEntities.get(i).getlOverdueInterest() + "A" + liDetailEntities.get(i).getiMemberId() + "A" + liDetailEntities.get(i).getiMemberType()+"A"+liDetailEntities.get(i).getsOrderNo();
					liDetailEntities.get(i).setsDetail(sInfo);
					
					//					else if(protype==1) {//天标
//						linver=linver;
//					}
					//IntegerAndString.StringToString(str)
				}
				repayInterfaceEntity.setDetailList(liDetailEntities);
				lallmoney=lPrincipal+lInterest+lPenalty+lOverdue;
				repayInterfaceEntity.setlAmount(lallmoney);
				repayInterfaceEntity.setsAmount(IntegerAndString.LongToString(lallmoney));
				repayInterfaceEntity.setlInterest(lInterest);
				repayInterfaceEntity.setsInterest(IntegerAndString.LongToString(lInterest));
				repayInterfaceEntity.setlPrincipal(lPrincipal);
				repayInterfaceEntity.setsPrincipal(IntegerAndString.LongToString(lPrincipal));
				repayInterfaceEntity.setlPenalty(lPenalty);
				repayInterfaceEntity.setsPenalty(IntegerAndString.LongToString(lPenalty));
				repayInterfaceEntity.setlMngFee(lMngFee);
				repayInterfaceEntity.setsMngFee(IntegerAndString.LongToString(lMngFee));
				repayInterfaceEntity.setlOverdueInterest(lOverdueInterest);
				//还款会员id
				repayInterfaceEntity.setiMemberId(lonmemberId);
				repayInterfaceEntity.setiMemberType(lonmembertype);
				//查询还款会员乾多多标识
				Map<String, Object> lomaps=new HashMap<String, Object>();
				lomaps.put("memberType", lonmembertype);//会员类型
				lomaps.put("memberID", lonmemberId);//会员id
				repayInterfaceEntity.setsMark(selectThreePartyDaoImpl.findMemberThirdPartyMark(lomaps));
			}
			//
			//
			return repayInterfaceEntity;
		}else {
			results[0]=relu;
		}
		// TODO Auto-generated method stub return null;
		return null;
	}
	
	
	
	
	/** 
	* GenerateInvestIncome债权转让 生成收益计划  
	* TODO(描述)
	* @author 朱祖轶  
	* * @Title: GenerateInvestIncome 
	* @Description: TODO 
	* @param @param investID
	* @param @param applyID
	* @param @param memberID
	* @param @param planStr
	* @param @return 设定文件 
	* @return int 返回类型 
	* @date 2016-4-14 下午4:04:01
	* @throws 
	*/
	public int GenerateInvestIncome(long investID,long applyID,long memberID, String planStr) {
		int result = -1;
		String[] sRows = planStr.split(";");
		String[] sCols = null;
		//获取还款计划id
		Map<String, Object> maps =new HashMap<String, Object>();
		maps.put("applyId", applyID);
		List<Integer> RepayIDList= selectThreePartyDaoImpl.selectLoanId(maps);
		int iSize = RepayIDList.size();
		int iLen = sRows.length;
		//还款计划条数与收益计划条数不相等
		if(iSize != iLen){
			return -1;
		}
		int iPrincipal = 0;
		int iInterest = 0;
		Timestamp ttDate = null;
		for(int i=0;i<iSize;i++){
			sCols = sRows[i].split(",");
			if(sCols.length!=3){
				continue;
			}
			iPrincipal = RepalyUtitls.StringToInt(sCols[0]);
			iInterest = RepalyUtitls.StringToInt(sCols[1]);
			ttDate = RepalyUtitls.StringToTimestamp(sCols[2]); 
			IdGeneratorUtil generatorUtil = IdGeneratorUtil.GetIdGeneratorInstance(); 
			long iId    = generatorUtil.GetId() ; 
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("iId",             iId);
			map.put("repayId",   RepayIDList.get(i));
			map.put("investId",            investID);
			map.put("memberId",            memberID);
			map.put("sPrincipal",        iPrincipal);
			map.put("sInterest",          iInterest);
			map.put("ttDate",                ttDate); 
			map.put("sKey",DbKeyUtil.GetDbCodeKey());
			//新增收益计划
			map=handleThreePartyDaoImpl.addInvestIncome(map);
			result =IntegerAndString.StringToInt(map.get("result ").toString(),0);
		    if(result == 1){
		    	generatorUtil.SetIdUsed(iId);
			}else{
				generatorUtil.SetIdUsedFail(iId);
			}
		}
		return result; 
	} 
	
	/**
	 * 还款收益平账
	 * @author 刘利   
	 * @Description: TODO 
	 * @param applyID 项目申请ID
	 * @param @return 设定文件 
	 * @return int 返回类型 
	 * @date 2016-4-12 下午3:56:22
	 */
	public int RepayIncomePing(int applyID) {
		int result = -1;
		List<cn.springmvc.model.LoanRepayEntity> RepayIDList= investIncomeListDao.selectLoanRepayIDByapplyID(applyID);
		for(int i = 0;i < RepayIDList.size();i++){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("replayID",   RepayIDList.get(i).getRepayID());
			map.put("sKey", DbKeyUtil.GetDbCodeKey());
			InvestIncomeEntity investIncomeEntity= investIncomeListDao.selectSumAmount(map);
			String SDRecvMaxTime = investIncomeListDao.selectSDRecvMaxTime(RepayIDList.get(i).getRepayID()); 
			Map<String,Object> mapPlan = new HashMap<String,Object>();
			mapPlan.put("iId",                       RepayIDList.get(i).getRepayID());
			mapPlan.put("iPrincipal",   investIncomeEntity.getTotalSDRecvPrincipal());
			mapPlan.put("iInterest",     investIncomeEntity.getTotalSDRecvInterest());
			mapPlan.put("ttDate",                                      SDRecvMaxTime); 
			mapPlan.put("sKey",                             DbKeyUtil.GetDbCodeKey());
			result = investIncomeDao.updateReplay(mapPlan);
		}
		return result;
	}
	
	/* * 保证金退回 提交数据处理*  * @param applyId
	/* *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#RefundDeposit(long) */
	@Override
	public LoanTransferEntity RefundDeposit(long applyId) {
		LoanTransferEntity loanTransferEntity=new LoanTransferEntity();
		//判断该项目是否符合保证金退回规则 JudgmentRefundDeposit
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("applyId", applyId);
		map.put("skey", DbKeyUtil.GetDbCodeKey());
		map=selectThreePartyDaoImpl.JudgmentRefundDeposit(map);
		int rul=IntegerAndString.StringToInt(map.get("result").toString(), 0);
		if (rul==1) {//符合退回规则
			//获得平台乾多多标识
			String pmark="";
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("accountTypeID", 1);//平台账号类型
			pmark=selectThreePartyDaoImpl.findThirdPartyMark(maps);
			
			//得到退还金额
			long amount=IntegerAndString.StringToLong(map.get("amount").toString(), 0);
			//得到收取方会员id
			long memberId=IntegerAndString.StringToLong(map.get("memberId").toString(), 0);
			map.put("memberType", 0);//会员类型
			map.put("memberID", memberId);//会员id
			String smark=selectThreePartyDaoImpl.findMemberThirdPartyMark(map);
			List<LoanReturnInfoBean> loanReturnInfoBeans=new ArrayList<LoanReturnInfoBean>();
			LoanReturnInfoBean lBean=new LoanReturnInfoBean();
			//生成平台操作订单号
			String ordernumber =handleThreePartyDaoImpl.generateorderNo("THBZJ");
			lBean.setAmount(IntegerAndString.LongToString(amount));
			lBean.setLoanInMoneymoremore(smark);
			lBean.setLoanOutMoneymoremore(pmark);
			lBean.setOrderNo(ordernumber);
			lBean.setBatchNo(ordernumber+"1");
			lBean.setRemark(memberId+"n"+amount+"n"+applyId);//会员id+退还金额（long）+项目申请id
			lBean.setFullAmount(IntegerAndString.LongToString(amount));
			lBean.setSecondaryJsonList("");
			lBean.setTransferName("平台退还借款人保证金");
			loanReturnInfoBeans.add(lBean);
			String JsonList=Common.JSONEncode(loanReturnInfoBeans);
			
			
			loanTransferEntity.setLoanJsonList(JsonList);//转账列表
			loanTransferEntity.setPlatformMoneymoremore(pmark);//平台标识
			loanTransferEntity.setTransferAction("3");//转账类型 1.投标2.还款3.其他
			loanTransferEntity.setAction("1");//操作类型1.手动转账2.自动转账
			loanTransferEntity.setTransferType("2");//
			loanTransferEntity.setNeedAudit("");
			loanTransferEntity.setReturnURL("");//页面返回网址
			loanTransferEntity.setNotifyURL("");//后台通知网址
			loanTransferEntity.setSubmitURL("");//提交第三方地址
			String privatekey = Common.privateKeyPKCS8;
			String dataStr = loanTransferEntity.getLoanJsonList()
					+ loanTransferEntity.getPlatformMoneymoremore()
					+ loanTransferEntity.getTransferAction() + loanTransferEntity.getAction()
					+ loanTransferEntity.getTransferType() + loanTransferEntity.getNeedAudit()
					+ loanTransferEntity.getRandomTimeStamp() + loanTransferEntity.getRemark1()
					+ loanTransferEntity.getRemark2() + loanTransferEntity.getRemark3()
					+ loanTransferEntity.getReturnURL() + loanTransferEntity.getNotifyURL();
			// 签名
			RsaHelper rsa = RsaHelper.getInstance();
			String SignInfo = rsa.signData(dataStr, privatekey);
			loanTransferEntity.setSignInfo(SignInfo);
			String LoanJsonList = Common.UrlEncoder(loanTransferEntity.getLoanJsonList(),
					"utf-8");
			loanTransferEntity.setLoanJsonList(LoanJsonList);
			//添加开户第三方交互记录
			Map<String, Object> smaps = new HashMap<String, Object>();
			smaps.put("id", generatorUtil.GetId());//第三方交互记录id
			smaps.put("merbillNo", ordernumber);//当前操作订单号
			smaps.put("type", "03");//操作类型
			smaps.put("interfaceType", 1);//第三方接口提供商
			smaps.put("detail", dataStr);//加密前数据
			smaps.put("detailEncrypt", SignInfo);//加密后数据
			smaps.put("remark", "");//备注
			handleThreePartyDaoImpl.insertThirdInterfaceRecord(maps);
			
			return loanTransferEntity;
		}else {
			
		}
		// TODO Auto-generated method stub return null;ReturnOfDeposit
		return null;
	}
	
	@Override
	public int ReturnOfDepositNotify() {
		int rulet=0;
		try {
			LoanTransferReturnEntity 
					loanTransferReturnEntity=new LoanTransferReturnEntity();
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			loanTransferReturnEntity.
				setResultCode(request.getParameter("ResultCode"));
			loanTransferReturnEntity
				.setMessage(request.getParameter("Message"));//返回信息
			if(loanTransferReturnEntity.getResultCode()!=null){
				if (loanTransferReturnEntity.getResultCode().equals("88")) {
					loanTransferReturnEntity.setStatu(0);
					loanTransferReturnEntity
					.setLoanJsonList(request.getParameter("LoanJsonList"));
					String fah = request.getParameter("LoanJsonList");
					fah = Common.UrlDecoder(fah, "utf-8");
					List<Object> loaninfolist = Common.JSONDecodeList(fah,
							LoanReturnInfoBean.class);
					loanTransferReturnEntity.setLoaninfolists(loaninfolist);//需要处理的转账信息
					loanTransferReturnEntity
					.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					loanTransferReturnEntity
					.setRemark1(request.getParameter("Remark1"));
					loanTransferReturnEntity
					.setRemark2(request.getParameter("Remark2"));
					loanTransferReturnEntity
					.setRemark3(request.getParameter("Remark3"));
				}else {
					loanTransferReturnEntity.setStatu(1);
				}
			}else {
				loanTransferReturnEntity.setStatu(1);
			}
			
			if(loanTransferReturnEntity.getStatu()==0){//退还成功
				if(loanTransferReturnEntity.getLoaninfolist().size()>0){//判断是否存在转账列表
					//获取会员投资列表信息
					LoanReturnInfoBean lrib = (LoanReturnInfoBean) loanTransferReturnEntity.getLoaninfolist()
							.get(0);
					//处理获取的数据 memberId+"n"+amount+"n"+applyId
					String [] rString=lrib.getRemark().split("n");
					long memberId=IntegerAndString.StringToLong(rString[0], 0);
					long amount=IntegerAndString.StringToLong(rString[1], 0);
					long applyId=IntegerAndString.StringToLong(rString[2], 0);
					long iId    = generatorUtil.GetId() ; 
					Map<String, Object> mapo=new HashMap<String, Object>();
					mapo.put("merbillno", lrib.getOrderNo());
					mapo.put("thredId", iId);
					mapo.put("applyID", applyId);
					mapo.put("memberID", memberId);
					mapo.put("thirdMerBillno", lrib.getLoanNo());
					mapo.put("amount", amount);
					mapo.put("backDetailEncrypt", loanTransferReturnEntity.getSignInfo());
					mapo.put("backDetail", loanTransferReturnEntity.toString());
					mapo.put("skey", DbKeyUtil.GetDbCodeKey());
					handleThreePartyDaoImpl.ReturnOfDeposit(mapo);
					rulet=IntegerAndString.StringToInt(mapo.get("result").toString(),0);
				}
				
			}else {//退还失败
				loanTransferReturnEntity.getMessage();//失败原因
			}
			
			HttpServletResponse response 
			= ((ServletWebRequest)RequestContextHolder
					.getRequestAttributes()).getResponse();
			response.setContentType("text/plain;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("SUCCESS");
			response.getWriter().flush();
			response.getWriter().close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub 
		return rulet;
	}
	
	@Override
	public LoanTransferEntity SurpriseRedSubmit(long applyId) {
		LoanTransferEntity loanTransferEntity=new LoanTransferEntity();
		//查询出该项目的所有惊喜保证金记录
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("applyId", applyId);
		map.put("skey", DbKeyUtil.GetDbCodeKey());
		List<SurpriseRedEntity> lRedEntities=new ArrayList<SurpriseRedEntity>();
		lRedEntities=selectThreePartyDaoImpl.findListSurpriseRed(map);
		String dealString="";
		int rul=1;
		if (rul==1) {//
			//获得平台乾多多标识
			String pmark="";
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("accountTypeID", 1);//平台账号类型
			pmark=selectThreePartyDaoImpl.findThirdPartyMark(maps);
			List<LoanReturnInfoBean> loanReturnInfoBeans=new ArrayList<LoanReturnInfoBean>();
			//生成平台操作订单号
			String order =handleThreePartyDaoImpl.generateorderNo("JXHBFF");
			SurpriseRedEntity surpriseRedEntity=new SurpriseRedEntity();
			if (lRedEntities.size()>0) {
				for (int i = 0; i < lRedEntities.size(); i++) {
					surpriseRedEntity=lRedEntities.get(i);
					//得到发放惊喜红包金额
					long amount=surpriseRedEntity.getAmount();
					//得到收取方会员id
					long memberId=surpriseRedEntity.getMemberId();
					map.put("memberType", 0);//会员类型
					map.put("memberID", memberId);//会员id
					String smark=selectThreePartyDaoImpl.findMemberThirdPartyMark(map);
					LoanReturnInfoBean lBean=new LoanReturnInfoBean();
					//生成平台操作订单号
					String ordernumber =handleThreePartyDaoImpl.generateorderNo("JXHB");
					lBean.setAmount(IntegerAndString.LongToString(amount));
					lBean.setLoanInMoneymoremore(smark);
					lBean.setLoanOutMoneymoremore(pmark);
					lBean.setOrderNo(ordernumber);
					lBean.setBatchNo(ordernumber+"1");
					lBean.setRemark(memberId+"n"+amount+"n"+surpriseRedEntity.getInvestId());//会员id+惊喜红包金额（long）+项目申请id
					lBean.setFullAmount(IntegerAndString.LongToString(amount));
					lBean.setSecondaryJsonList("");
					lBean.setTransferName("平台发放惊喜红包");
					loanReturnInfoBeans.add(lBean);
					//拼接要处理的临时投资记录
					if (!dealString.equals("")) {
						dealString+=","+surpriseRedEntity.getIrtid();
					}else {
						dealString+=surpriseRedEntity.getIrtid()+",";
					}
				}
			}
			
			
			String JsonList=Common.JSONEncode(loanReturnInfoBeans);
			
			loanTransferEntity.setRemark1(order);
			loanTransferEntity.setRemark2(applyId+"");
			loanTransferEntity.setLoanJsonList(JsonList);//转账列表
			loanTransferEntity.setPlatformMoneymoremore(pmark);//平台标识
			loanTransferEntity.setTransferAction("3");//转账类型 1.投标2.还款3.其他
			loanTransferEntity.setAction("1");//操作类型1.手动转账2.自动转账
			loanTransferEntity.setTransferType("2");//
			loanTransferEntity.setNeedAudit("");
			loanTransferEntity.setReturnURL("");//页面返回网址
			loanTransferEntity.setNotifyURL("");//后台通知网址
			loanTransferEntity.setSubmitURL("");//提交第三方地址
			String privatekey = Common.privateKeyPKCS8;
			String dataStr = loanTransferEntity.getLoanJsonList()
					+ loanTransferEntity.getPlatformMoneymoremore()
					+ loanTransferEntity.getTransferAction() + loanTransferEntity.getAction()
					+ loanTransferEntity.getTransferType() + loanTransferEntity.getNeedAudit()
					+ loanTransferEntity.getRandomTimeStamp() + loanTransferEntity.getRemark1()
					+ loanTransferEntity.getRemark2() + loanTransferEntity.getRemark3()
					+ loanTransferEntity.getReturnURL() + loanTransferEntity.getNotifyURL();
			// 签名
			RsaHelper rsa = RsaHelper.getInstance();
			String SignInfo = rsa.signData(dataStr, privatekey);
			loanTransferEntity.setSignInfo(SignInfo);
			String LoanJsonList = Common.UrlEncoder(loanTransferEntity.getLoanJsonList(),
					"utf-8");
			loanTransferEntity.setLoanJsonList(LoanJsonList);
			//添加开户第三方交互记录
			Map<String, Object> smaps = new HashMap<String, Object>();
			smaps.put("id", generatorUtil.GetId());//第三方交互记录id
			smaps.put("merbillNo", order);//当前操作订单号
			smaps.put("type", "03");//操作类型
			smaps.put("interfaceType", 1);//第三方接口提供商
			smaps.put("detail", dataStr);//加密前数据
			smaps.put("detailEncrypt", SignInfo);//加密后数据
			smaps.put("remark", "");//备注
			handleThreePartyDaoImpl.insertThirdInterfaceRecord(maps);
			//标记当前批次需要发惊喜红包的记录
			Map<String, Object> str = new HashMap<String, Object>();
			str.put("subTem", dealString);
			handleThreePartyDaoImpl.DataTagBySurpriseRed(str);
			
			return loanTransferEntity;
		}else {
			
		}
		// TODO Auto-generated method stub return null;ReturnOfDeposit
		return null;
		
	}
	
	
	@Override
	public int SurpriseRedBack() {
		int rulet=0;
		try {
			LoanTransferReturnEntity 
					loanTransferReturnEntity=new LoanTransferReturnEntity();
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			loanTransferReturnEntity.
				setResultCode(request.getParameter("ResultCode"));
			loanTransferReturnEntity
				.setMessage(request.getParameter("Message"));//返回信息
			if(loanTransferReturnEntity.getResultCode()!=null){
				if (loanTransferReturnEntity.getResultCode().equals("88")) {
					loanTransferReturnEntity.setStatu(0);
					loanTransferReturnEntity
					.setLoanJsonList(request.getParameter("LoanJsonList"));
					String fah = request.getParameter("LoanJsonList");
					fah = Common.UrlDecoder(fah, "utf-8");
					List<Object> loaninfolist = Common.JSONDecodeList(fah,
							LoanReturnInfoBean.class);
					loanTransferReturnEntity.setLoaninfolists(loaninfolist);//需要处理的转账信息
					loanTransferReturnEntity
					.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					loanTransferReturnEntity
					.setRemark1(request.getParameter("Remark1"));
					loanTransferReturnEntity
					.setRemark2(request.getParameter("Remark2"));
					loanTransferReturnEntity
					.setRemark3(request.getParameter("Remark3"));
				}else {
					loanTransferReturnEntity.setStatu(1);
				}
			}else {
				loanTransferReturnEntity.setStatu(1);
			}
			String order = loanTransferReturnEntity.getRemark1();
			long applyId = Long.parseLong(loanTransferReturnEntity.getRemark2());
			if(loanTransferReturnEntity.getStatu()==0){//发放成功
				if(loanTransferReturnEntity.getLoaninfolist().size()>0){//判断是否存在转账列表
					LoanReturnInfoBean lrib =new LoanReturnInfoBean();
					
					String dealtString="";
					for (int i = 0; i < loanTransferReturnEntity.getLoaninfolist().size(); i++) {
						//获取会员投资列表信息
						lrib = (LoanReturnInfoBean) loanTransferReturnEntity.getLoaninfolist()
								.get(0);
						//处理获取的数据 memberId+"n"+amount+"n"+applyId
						String [] rString=lrib.getRemark().split("n");
						long memberId=IntegerAndString.StringToLong(rString[0], 0);
						long amount=IntegerAndString.StringToLong(rString[1], 0);
						long investId=IntegerAndString.StringToLong(rString[2], 0);
						if (!dealtString.equals("")) {
							dealtString+=";"+memberId+","+investId+","+amount+","+lrib.getLoanNo()+","+lrib.getOrderNo();
						}else {
							dealtString+=memberId+","+investId+","+amount+","+lrib.getLoanNo()+","+lrib.getOrderNo()+";";
						}
					}
					long iId    = generatorUtil.GetId() ; 
					Map<String, Object> mapo=new HashMap<String, Object>();
					mapo.put("istatu", 1);
					mapo.put("merbillno", order);
					mapo.put("applyID", applyId);
					mapo.put("thredId", iId);
					mapo.put("backDetailEncrypt", loanTransferReturnEntity.getSignInfo());
					mapo.put("backDetail", loanTransferReturnEntity.toString());
					mapo.put("subStrin", dealtString);
					mapo.put("skey", DbKeyUtil.GetDbCodeKey());
					handleThreePartyDaoImpl.SurpriseRedBack(mapo);
					//skey varchar(30),OUT result int
					//rulet=IntegerAndString.StringToInt(mapo.get("result").toString(),0);
				}
				
			}else {//发放失败
				loanTransferReturnEntity.getMessage();//失败原因
				Map<String, Object> mapo=new HashMap<String, Object>();
				mapo.put("istatu", 0);
				mapo.put("merbillno", order);
				mapo.put("applyID", applyId);
				mapo.put("thredId", 1);
				mapo.put("backDetailEncrypt", loanTransferReturnEntity.getSignInfo());
				mapo.put("backDetail", loanTransferReturnEntity.toString());
				mapo.put("subStrin", "");
				mapo.put("skey", DbKeyUtil.GetDbCodeKey());
				handleThreePartyDaoImpl.SurpriseRedBack(mapo);
			}
			
			HttpServletResponse response 
			= ((ServletWebRequest)RequestContextHolder
					.getRequestAttributes()).getResponse();
			response.setContentType("text/plain;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("SUCCESS");
			response.getWriter().flush();
			response.getWriter().close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub 
		return rulet;
	}
	
	/* *  *  * @param inMemberId
	/* *  *  * @param outMemberId
	/* *  *  * @param amount
	/* *  *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#FriendTransfer(long, long, long) */
	@Override
	public LoanTransferEntity FriendTransfer(long inMemberId,long outMemberId,long amount) {
		LoanTransferEntity loanTransferEntity=new LoanTransferEntity();
		//查询转账人账户余额是否满足当次转账 
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("inMemberId", outMemberId);
		map.put("amount", amount);
		map.put("skey", DbKeyUtil.GetDbCodeKey());
		int rul=selectThreePartyDaoImpl.AccountBalance(map);
		if (rul!=-1) {//余额足够转账
			//获得平台乾多多标识
			String pmark="";
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("accountTypeID", 1);//平台账号类型
			pmark=selectThreePartyDaoImpl.findThirdPartyMark(maps);
			//得到转账方会员标识
			//long memberId=IntegerAndString.StringToLong(map.get("memberId").toString(), 0);
			map.put("memberType", 0);//会员类型
			map.put("memberID", outMemberId);//会员id
			String smarkOut=selectThreePartyDaoImpl.findMemberThirdPartyMark(map);
			
			//得到收取方会员标识
			Map<String, Object> mapin = new HashMap<String, Object>();
			mapin.put("memberType", 0);//会员类型
			mapin.put("memberID", inMemberId);//会员id
			String smarkIn=selectThreePartyDaoImpl.findMemberThirdPartyMark(mapin);
			
			List<LoanReturnInfoBean> loanReturnInfoBeans=new ArrayList<LoanReturnInfoBean>();
			LoanReturnInfoBean lBean=new LoanReturnInfoBean();
			//生成平台操作订单号
			String ordernumber =handleThreePartyDaoImpl.generateorderNo("HYZZ");
			lBean.setAmount(IntegerAndString.LongToString(amount));
			lBean.setLoanInMoneymoremore(smarkIn);
			lBean.setLoanOutMoneymoremore(smarkOut);
			lBean.setOrderNo(ordernumber);
			lBean.setBatchNo(ordernumber+"1");
			lBean.setRemark(inMemberId+"n"+amount+"n"+outMemberId);//会员id+退还金额（long）+项目申请id
			lBean.setFullAmount(IntegerAndString.LongToString(amount));
			lBean.setSecondaryJsonList("");
			lBean.setTransferName("好友转账");
			loanReturnInfoBeans.add(lBean);
			String JsonList=Common.JSONEncode(loanReturnInfoBeans);
			
			loanTransferEntity.setRemark1(ordernumber);
			loanTransferEntity.setLoanJsonList(JsonList);//转账列表
			loanTransferEntity.setPlatformMoneymoremore(pmark);//平台标识
			loanTransferEntity.setTransferAction("3");//转账类型 1.投标2.还款3.其他
			loanTransferEntity.setAction("1");//操作类型1.手动转账2.自动转账
			loanTransferEntity.setTransferType("2");//
			loanTransferEntity.setNeedAudit("");
			loanTransferEntity.setReturnURL("");//页面返回网址
			loanTransferEntity.setNotifyURL("");//后台通知网址
			loanTransferEntity.setSubmitURL("");//提交第三方地址
			String privatekey = Common.privateKeyPKCS8;
			String dataStr = loanTransferEntity.getLoanJsonList()
					+ loanTransferEntity.getPlatformMoneymoremore()
					+ loanTransferEntity.getTransferAction() + loanTransferEntity.getAction()
					+ loanTransferEntity.getTransferType() + loanTransferEntity.getNeedAudit()
					+ loanTransferEntity.getRandomTimeStamp() + loanTransferEntity.getRemark1()
					+ loanTransferEntity.getRemark2() + loanTransferEntity.getRemark3()
					+ loanTransferEntity.getReturnURL() + loanTransferEntity.getNotifyURL();
			// 签名
			RsaHelper rsa = RsaHelper.getInstance();
			String SignInfo = rsa.signData(dataStr, privatekey);
			loanTransferEntity.setSignInfo(SignInfo);
			String LoanJsonList = Common.UrlEncoder(loanTransferEntity.getLoanJsonList(),
					"utf-8");
			loanTransferEntity.setLoanJsonList(LoanJsonList);
			//添加开户第三方交互记录
			long gid=generatorUtil.GetId();
			Map<String, Object> smaps = new HashMap<String, Object>();
			smaps.put("id", gid);//第三方交互记录id
			smaps.put("merbillNo", ordernumber);//当前操作订单号
			smaps.put("type", "03");//操作类型
			smaps.put("interfaceType", 1);//第三方接口提供商
			smaps.put("detail", dataStr);//加密前数据
			smaps.put("detailEncrypt", SignInfo);//加密后数据
			smaps.put("remark", "");//备注
			handleThreePartyDaoImpl.insertThirdInterfaceRecord(maps);
			//添加转账记录 
			Map<String, Object> smapre = new HashMap<String, Object>();
			smapre.put("outMember", outMemberId);
			smapre.put("inMember", inMemberId);
			smapre.put("amount", amount);
			smapre.put("recordNo", ordernumber);
			smapre.put("thirdTradeId", gid);
			smapre.put("skey", DbKeyUtil.GetDbCodeKey());
			handleThreePartyDaoImpl.insertMemberTransferRecord(smapre);
			return loanTransferEntity;
		}else {
			
		}
		// TODO Auto-generated method stub return null;ReturnOfDeposit
		return null;
		
	}
	
	
	/* * 会员转账服务器返回处理 *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#MemberTransferBack() */
	@Override
	public int MemberTransferBack() {
		int rulet=0;
		try {
			LoanTransferReturnEntity 
					loanTransferReturnEntity=new LoanTransferReturnEntity();
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			loanTransferReturnEntity.
				setResultCode(request.getParameter("ResultCode"));
			loanTransferReturnEntity
				.setMessage(request.getParameter("Message"));//返回信息
			if(loanTransferReturnEntity.getResultCode()!=null){
				if (loanTransferReturnEntity.getResultCode().equals("88")) {
					loanTransferReturnEntity.setStatu(0);
					loanTransferReturnEntity
					.setLoanJsonList(request.getParameter("LoanJsonList"));
					String fah = request.getParameter("LoanJsonList");
					fah = Common.UrlDecoder(fah, "utf-8");
					List<Object> loaninfolist = Common.JSONDecodeList(fah,
							LoanReturnInfoBean.class);
					loanTransferReturnEntity.setLoaninfolists(loaninfolist);//需要处理的转账信息
					loanTransferReturnEntity
					.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					loanTransferReturnEntity
					.setRemark1(request.getParameter("Remark1"));
					loanTransferReturnEntity
					.setRemark2(request.getParameter("Remark2"));
					loanTransferReturnEntity
					.setRemark3(request.getParameter("Remark3"));
				}else {
					loanTransferReturnEntity.setStatu(1);
				}
			}else {
				loanTransferReturnEntity.setStatu(1);
			}
			String order = loanTransferReturnEntity.getRemark1();
			if(loanTransferReturnEntity.getStatu()==0){//转账成功
				if(loanTransferReturnEntity.getLoaninfolist().size()>0){//判断是否存在转账列表
					//获取会员投资列表信息
					LoanReturnInfoBean lrib = (LoanReturnInfoBean) loanTransferReturnEntity.getLoaninfolist()
							.get(0);
					//处理获取的数据 memberId+"n"+amount+"n"+applyId
					String [] rString=lrib.getRemark().split("n");
					long inmemberId=IntegerAndString.StringToLong(rString[0], 0);
					long amount=IntegerAndString.StringToLong(rString[1], 0);
					long outmemberId=IntegerAndString.StringToLong(rString[2], 0);
					long iId    = generatorUtil.GetId() ; 
					long iIdtow    = generatorUtil.GetId() ; 
					Map<String, Object> mapo=new HashMap<String, Object>();
					
					mapo.put("istatu", 1);
					mapo.put("merbillno", lrib.getOrderNo());
					mapo.put("loanNo", lrib.getLoanNo());
					mapo.put("amount", amount);
					mapo.put("inMemberId", inmemberId);
					mapo.put("outMemberId", outmemberId);
					mapo.put("thredIdone", iId);
					mapo.put("thredIdtow", iIdtow);
					mapo.put("backDetailEncrypt", loanTransferReturnEntity.getSignInfo());
					mapo.put("backDetail", loanTransferReturnEntity.toString());
					mapo.put("skey", DbKeyUtil.GetDbCodeKey());
					handleThreePartyDaoImpl.MemberTransferBack(mapo);
					
				}
				
			}else {//转账失败
				loanTransferReturnEntity.getMessage();//失败原因
				Map<String, Object> mapo=new HashMap<String, Object>();
				mapo.put("istatu", 0);
				mapo.put("merbillno", order);
				mapo.put("loanNo", "");
				mapo.put("amount", "");
				mapo.put("inMemberId", "");
				mapo.put("outMemberId", "");
				mapo.put("thredIdone", "");
				mapo.put("thredIdtow", "");
				mapo.put("backDetailEncrypt", loanTransferReturnEntity.getSignInfo());
				mapo.put("backDetail", loanTransferReturnEntity.toString());
				mapo.put("skey", DbKeyUtil.GetDbCodeKey());
				handleThreePartyDaoImpl.MemberTransferBack(mapo);
			}
			
			HttpServletResponse response 
			= ((ServletWebRequest)RequestContextHolder
					.getRequestAttributes()).getResponse();
			response.setContentType("text/plain;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("SUCCESS");
			response.getWriter().flush();
			response.getWriter().close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub 
		return rulet;
	}
	@Override
	public LoanTransferEntity PlatformAwardMoney(long MemberId,String startTime,String endTime,long newAmount,int type) {
		LoanTransferEntity loanTransferEntity=new LoanTransferEntity();
		//查询提奖会员当次应提奖金额明细 
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("memberId", MemberId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("skey", DbKeyUtil.GetDbCodeKey());
		List<AwardEntity> lAwardEntities = new ArrayList<AwardEntity>();
		if (type==1) {/** 理财顾问*/
			lAwardEntities=selectThreePartyDaoImpl.FinancialAdvisorAward(map);
		}else {/** 推荐达人*/
			lAwardEntities=selectThreePartyDaoImpl.RecommendedTalentAward(map);
		}
		
		long allamount=0;//总提奖金额
		/** 理财顾问*/
		long lcamount1=0;//借款提奖
		long lcamount2=0;//投资提奖
		long lcamount3=0;//还本提奖
		long lcamount4=0;//VIP购买提奖
		long lcsjamount1=0;//事件借款总金额
		long lcsjamount2=0;//事件投资总金额
		long lcsjamount3=0;//事件还本总金额
		long lcsjamount4=0;//事件VIP购买总金额
		AwardEntity awardEntity = new AwardEntity();
		int typ=0;
		if (lAwardEntities.size()>0) {
			for (int i = 0; i < lAwardEntities.size(); i++) {
				awardEntity = lAwardEntities.get(i);
				typ=awardEntity.getAwardType();
				if (typ==0) {//借款提奖
					lcamount1+=awardEntity.getTjmoney();
					lcsjamount1+=awardEntity.getSjmoney();
				}else if (typ==1) {//投资提奖
					lcamount2+=awardEntity.getTjmoney();
					lcsjamount2+=awardEntity.getSjmoney();
				}else if (typ==2) {//还本提奖
					lcamount3+=awardEntity.getTjmoney();
					lcsjamount3+=awardEntity.getSjmoney();
				}else {//VIP购买提奖
					lcamount4+=awardEntity.getTjmoney();
					lcsjamount4+=awardEntity.getSjmoney();
				}
				allamount+=awardEntity.getTjmoney();
			}
		}
		if (allamount>0) {//判断没有提奖金额
			//获得平台乾多多标识
			String pmark="";
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("accountTypeID", 1);//平台账号类型
			pmark=selectThreePartyDaoImpl.findThirdPartyMark(maps);
			
			//得到收取方会员标识 
			Map<String, Object> mapin = new HashMap<String, Object>();
			mapin.put("memberType", 0);//会员类型
			mapin.put("memberID", MemberId);//会员id
			String smarkIn=selectThreePartyDaoImpl.findMemberThirdPartyMark(mapin);
			List<LoanReturnInfoBean> loanReturnInfoBeans=new ArrayList<LoanReturnInfoBean>();
			LoanReturnInfoBean lBean=new LoanReturnInfoBean();
			String tex="";
			if (type==1) {
				lBean.setTransferName("理财顾问提奖");
				tex="LCGW";
			}else {
				lBean.setTransferName("推荐达人提奖");
				tex="TJDR";
			}
			//生成平台操作订单号 
			String ordernumber =handleThreePartyDaoImpl.generateorderNo(tex);
			lBean.setAmount(IntegerAndString.LongToString(newAmount));//输入的为平台给的实际提奖金额
			lBean.setLoanInMoneymoremore(smarkIn);
			lBean.setLoanOutMoneymoremore(pmark);
			lBean.setOrderNo(ordernumber);
			lBean.setBatchNo(ordernumber+"1");
			lBean.setRemark(MemberId+"z"+lcamount1+"n"+lcamount2+"n"+lcamount3+"n"+lcamount4+
					"n"+lcsjamount1+"n"+lcsjamount2+"n"+lcsjamount3+"n"+lcsjamount4
					);//会员id+借款提奖 投资提奖,还本提奖,VIP购买提奖+
			lBean.setFullAmount(IntegerAndString.LongToString(newAmount));
			lBean.setSecondaryJsonList("");
			loanReturnInfoBeans.add(lBean);
			String JsonList=Common.JSONEncode(loanReturnInfoBeans);
			loanTransferEntity.setRemark1(ordernumber+"z"+type);
			loanTransferEntity.setRemark2(startTime);
			loanTransferEntity.setRemark3(endTime);
			loanTransferEntity.setLoanJsonList(JsonList);//转账列表
			loanTransferEntity.setPlatformMoneymoremore(pmark);//平台标识
			loanTransferEntity.setTransferAction("3");//转账类型 1.投标2.还款3.其他
			loanTransferEntity.setAction("1");//操作类型1.手动转账2.自动转账
			loanTransferEntity.setTransferType("2");//
			loanTransferEntity.setNeedAudit("");
			loanTransferEntity.setReturnURL("");//页面返回网址
			loanTransferEntity.setNotifyURL("");//后台通知网址
			loanTransferEntity.setSubmitURL("");//提交第三方地址
			String privatekey = Common.privateKeyPKCS8;
			String dataStr = loanTransferEntity.getLoanJsonList()
					+ loanTransferEntity.getPlatformMoneymoremore()
					+ loanTransferEntity.getTransferAction() + loanTransferEntity.getAction()
					+ loanTransferEntity.getTransferType() + loanTransferEntity.getNeedAudit()
					+ loanTransferEntity.getRandomTimeStamp() + loanTransferEntity.getRemark1()
					+ loanTransferEntity.getRemark2() + loanTransferEntity.getRemark3()
					+ loanTransferEntity.getReturnURL() + loanTransferEntity.getNotifyURL();
			// 签名
			RsaHelper rsa = RsaHelper.getInstance();
			String SignInfo = rsa.signData(dataStr, privatekey);
			loanTransferEntity.setSignInfo(SignInfo);
			String LoanJsonList = Common.UrlEncoder(loanTransferEntity.getLoanJsonList(),
					"utf-8");
			loanTransferEntity.setLoanJsonList(LoanJsonList);
			//添加开户第三方交互记录
			long gid=generatorUtil.GetId();
			Map<String, Object> smaps = new HashMap<String, Object>();
			smaps.put("id", gid);//第三方交互记录id
			smaps.put("merbillNo", ordernumber);//当前操作订单号
			smaps.put("type", "03");//操作类型
			smaps.put("interfaceType", 1);//第三方接口提供商
			smaps.put("detail", dataStr);//加密前数据
			smaps.put("detailEncrypt", SignInfo);//加密后数据
			smaps.put("remark", "");//备注
			handleThreePartyDaoImpl.insertThirdInterfaceRecord(maps);
			return loanTransferEntity;
		}else {
			
		}
		// TODO Auto-generated method stub return null;ReturnOfDeposit
		return null;
	}
	
	
	/* * 平台提奖操作第三方返回处理 *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#PlatformAwardMoneyBack() */
	@Override
	public int PlatformAwardMoneyBack() {
		int rulet=0;
		try {
			LoanTransferReturnEntity 
					loanTransferReturnEntity=new LoanTransferReturnEntity();
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			loanTransferReturnEntity.
				setResultCode(request.getParameter("ResultCode"));
			loanTransferReturnEntity
				.setMessage(request.getParameter("Message"));//返回信息
			if(loanTransferReturnEntity.getResultCode()!=null){
				if (loanTransferReturnEntity.getResultCode().equals("88")) {
					loanTransferReturnEntity.setStatu(0);
					loanTransferReturnEntity
					.setLoanJsonList(request.getParameter("LoanJsonList"));
					String fah = request.getParameter("LoanJsonList");
					fah = Common.UrlDecoder(fah, "utf-8");
					List<Object> loaninfolist = Common.JSONDecodeList(fah,
							LoanReturnInfoBean.class);
					loanTransferReturnEntity.setLoaninfolists(loaninfolist);//需要处理的转账信息
					loanTransferReturnEntity
					.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					loanTransferReturnEntity
					.setRemark1(request.getParameter("Remark1"));
					loanTransferReturnEntity
					.setRemark2(request.getParameter("Remark2"));
					loanTransferReturnEntity
					.setRemark3(request.getParameter("Remark3"));
				}else {
					loanTransferReturnEntity.setStatu(1);
				}
			String [] remark1 = loanTransferReturnEntity.getRemark1().split("z");
			String order = remark1[0];
			String startTime = loanTransferReturnEntity.getRemark2();
			String endTime = loanTransferReturnEntity.getRemark3();
			int itype = 0;
			itype = IntegerAndString.StringToInt(remark1[1], 0);
			if(loanTransferReturnEntity.getStatu()==0){//转账成功
				if(loanTransferReturnEntity.getLoaninfolist().size()>0){//判断是否存在转账列表
					//获取提奖会员列表信息
					LoanReturnInfoBean lrib = (LoanReturnInfoBean) loanTransferReturnEntity.getLoaninfolist()
							.get(0);
					//处理获取的数据 memberId+"n"+amount+"n"+applyId
					String [] rString=lrib.getRemark().split("z");
					long amount = IntegerAndString.StringToLong(lrib.getAmount());
					long inmemberId=IntegerAndString.StringToLong(rString[0], 0);
					String subStr=rString[1];
					long iId    = generatorUtil.GetId() ; 
					long iIdtow    = generatorUtil.GetId() ; 
					long iIdtre    = generatorUtil.GetId() ; 
					Map<String, Object> mapo=new HashMap<String, Object>();
					mapo.put("istatu", 1);
					mapo.put("itype", itype);
					mapo.put("payID", iId);
					mapo.put("merbillno", order);
					mapo.put("loanNo", lrib.getLoanNo());
					mapo.put("amount", amount);
					mapo.put("memberId", inmemberId);
					mapo.put("startTime", startTime);
					mapo.put("endTime", endTime);
					mapo.put("thredId", iIdtow);
					mapo.put("thredIdtow", iIdtre);
					mapo.put("backDetailEncrypt", loanTransferReturnEntity.getSignInfo());
					mapo.put("backDetail", loanTransferReturnEntity.toString());
					mapo.put("subStrin", subStr);
					mapo.put("skey", DbKeyUtil.GetDbCodeKey());
					handleThreePartyDaoImpl.PlatformAwardMoneyBack(mapo);
					
				}
				
			}else {//提奖失败
				
				loanTransferReturnEntity.getMessage();//失败原因
				Map<String, Object> mapo=new HashMap<String, Object>();
				mapo.put("istatu", 0);
				mapo.put("itype", itype);
				mapo.put("payID", 0);
				mapo.put("merbillno", order);
				mapo.put("loanNo", "");
				mapo.put("amount", 0);
				mapo.put("memberId", 0);
				mapo.put("startTime", startTime);
				mapo.put("endTime", endTime);
				mapo.put("thredId", 0);
				mapo.put("thredIdtow", 0);
				mapo.put("backDetailEncrypt", loanTransferReturnEntity.getSignInfo());
				mapo.put("backDetail", loanTransferReturnEntity.toString());
				mapo.put("subStrin", "");
				mapo.put("skey", DbKeyUtil.GetDbCodeKey());
				handleThreePartyDaoImpl.PlatformAwardMoneyBack(mapo);
			}
			HttpServletResponse response 
			= ((ServletWebRequest)RequestContextHolder
					.getRequestAttributes()).getResponse();
			response.setContentType("text/plain;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("SUCCESS");
			response.getWriter().flush();
			response.getWriter().close();
		}else {
			loanTransferReturnEntity.setStatu(1);
			rulet =-1;
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub 
		return rulet;
		
	}
	
	/* * 平台提奖操作第三方返回处理  页面通知 *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#PlatformAwardMoneyReturn() */
	@Override
	public String PlatformAwardMoneyReturn() {
		
		String retur="SUCCESS";
		try {
			LoanTransferReturnEntity 
					loanTransferReturnEntity=new LoanTransferReturnEntity();
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			loanTransferReturnEntity.
				setResultCode(request.getParameter("ResultCode"));
			loanTransferReturnEntity
				.setMessage(request.getParameter("Message"));//返回信息
			if(loanTransferReturnEntity.getResultCode()!=null){
				if (loanTransferReturnEntity.getResultCode().equals("88")) {
					loanTransferReturnEntity.setStatu(0);
					loanTransferReturnEntity
					.setLoanJsonList(request.getParameter("LoanJsonList"));
					String fah = request.getParameter("LoanJsonList");
					fah = Common.UrlDecoder(fah, "utf-8");
					List<Object> loaninfolist = Common.JSONDecodeList(fah,
							LoanReturnInfoBean.class);
					loanTransferReturnEntity.setLoaninfolists(loaninfolist);//需要处理的转账信息
					loanTransferReturnEntity
					.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					loanTransferReturnEntity
					.setRemark1(request.getParameter("Remark1"));
					loanTransferReturnEntity
					.setRemark2(request.getParameter("Remark2"));
					loanTransferReturnEntity
					.setRemark3(request.getParameter("Remark3"));
				}else {
					loanTransferReturnEntity.setStatu(1);
				}
			}else {
				loanTransferReturnEntity.setStatu(1);
			}
			if(loanTransferReturnEntity.getStatu()==0){//成功
				if(loanTransferReturnEntity.getLoaninfolist().size()>0){
				}
			}else {//失败
				loanTransferReturnEntity.getMessage();//失败原因
				retur="FAIL";
			}
			
		} catch (Exception e) {
			retur="ERRO";
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub return null;
		return retur;
		
	}
	
	
	/* * 好友转账返回处理  页面返回 *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#MemberTransferReturn() */
	@Override
	public String MemberTransferReturn() {
		String retur="SUCCESS";
		try {
			LoanTransferReturnEntity 
					loanTransferReturnEntity=new LoanTransferReturnEntity();
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			loanTransferReturnEntity.
				setResultCode(request.getParameter("ResultCode"));
			loanTransferReturnEntity
				.setMessage(request.getParameter("Message"));//返回信息
			if(loanTransferReturnEntity.getResultCode()!=null){
				if (loanTransferReturnEntity.getResultCode().equals("88")) {
					loanTransferReturnEntity.setStatu(0);
					loanTransferReturnEntity
					.setLoanJsonList(request.getParameter("LoanJsonList"));
					String fah = request.getParameter("LoanJsonList");
					fah = Common.UrlDecoder(fah, "utf-8");
					List<Object> loaninfolist = Common.JSONDecodeList(fah,
							LoanReturnInfoBean.class);
					loanTransferReturnEntity.setLoaninfolists(loaninfolist);//需要处理的转账信息
					loanTransferReturnEntity
					.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					loanTransferReturnEntity
					.setRemark1(request.getParameter("Remark1"));
					loanTransferReturnEntity
					.setRemark2(request.getParameter("Remark2"));
					loanTransferReturnEntity
					.setRemark3(request.getParameter("Remark3"));
				}else {
					loanTransferReturnEntity.setStatu(1);
				}
			}else {
				loanTransferReturnEntity.setStatu(1);
			}
			if(loanTransferReturnEntity.getStatu()==0){//成功
				if(loanTransferReturnEntity.getLoaninfolist().size()>0){
				}
			}else {//失败
				loanTransferReturnEntity.getMessage();//失败原因
				retur="FAIL";
			}
			
		} catch (Exception e) {
			retur="ERRO";
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub return null;
		return retur;
		
		
	}
	
	/* * 惊喜红包发放页面返回处理 *  * @return * @see cn.springmvc.service.ManagedInterfaceServerTestI#SurpriseRedReturn() */
	@Override
	public String SurpriseRedReturn() {
		String retur="SUCCESS";
		try {
			LoanTransferReturnEntity 
					loanTransferReturnEntity=new LoanTransferReturnEntity();
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			loanTransferReturnEntity.
				setResultCode(request.getParameter("ResultCode"));
			loanTransferReturnEntity
				.setMessage(request.getParameter("Message"));//返回信息
			if(loanTransferReturnEntity.getResultCode()!=null){
				if (loanTransferReturnEntity.getResultCode().equals("88")) {
					loanTransferReturnEntity.setStatu(0);
					loanTransferReturnEntity
					.setLoanJsonList(request.getParameter("LoanJsonList"));
					String fah = request.getParameter("LoanJsonList");
					fah = Common.UrlDecoder(fah, "utf-8");
					List<Object> loaninfolist = Common.JSONDecodeList(fah,
							LoanReturnInfoBean.class);
					loanTransferReturnEntity.setLoaninfolists(loaninfolist);//需要处理的转账信息
					loanTransferReturnEntity
					.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					loanTransferReturnEntity
					.setRemark1(request.getParameter("Remark1"));
					loanTransferReturnEntity
					.setRemark2(request.getParameter("Remark2"));
					loanTransferReturnEntity
					.setRemark3(request.getParameter("Remark3"));
				}else {
					loanTransferReturnEntity.setStatu(1);
				}
			}else {
				loanTransferReturnEntity.setStatu(1);
			}
			if(loanTransferReturnEntity.getStatu()==0){//成功
				if(loanTransferReturnEntity.getLoaninfolist().size()>0){
				}
			}else {//失败
				loanTransferReturnEntity.getMessage();//失败原因
				retur="FAIL";
			}
			
		} catch (Exception e) {
			retur="ERRO";
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub return null;
		return retur;
	}
	
	@Override
	public LoanTransferEntity PurchaseVIP(long memberId, int years, long amount) {
		LoanTransferEntity loanTransferEntity=new LoanTransferEntity();
		//查询转账人账户余额是否满足当次转账 
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("inMemberId", memberId);
		map.put("amount", amount);
		map.put("skey", DbKeyUtil.GetDbCodeKey());
		int rul=selectThreePartyDaoImpl.AccountBalance(map);
		if (rul!=-1) {//余额足够转账
			//获得平台乾多多标识
			String pmark="";
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("accountTypeID", 1);//平台账号类型
			pmark=selectThreePartyDaoImpl.findThirdPartyMark(maps);
			//得到购买会员标识
			map.put("memberType", 0);//会员类型
			map.put("memberID", memberId);//会员id
			String smarkOut=selectThreePartyDaoImpl.findMemberThirdPartyMark(map);
			
			List<LoanReturnInfoBean> loanReturnInfoBeans=new ArrayList<LoanReturnInfoBean>();
			LoanReturnInfoBean lBean=new LoanReturnInfoBean();
			//生成平台操作订单号
			String ordernumber =handleThreePartyDaoImpl.generateorderNo("GMVIP");
			lBean.setAmount(IntegerAndString.LongToString(amount));
			lBean.setLoanInMoneymoremore(smarkOut);
			lBean.setLoanOutMoneymoremore(pmark);
			lBean.setOrderNo(ordernumber);
			lBean.setBatchNo(ordernumber+"1");
			lBean.setRemark(memberId+"n"+amount+"n"+years);//会员id+购买金额（long）+购买年限
			lBean.setFullAmount(IntegerAndString.LongToString(amount));
			lBean.setSecondaryJsonList("");
			lBean.setTransferName("VIP购买");
			loanReturnInfoBeans.add(lBean);
			String JsonList=Common.JSONEncode(loanReturnInfoBeans);
			
			loanTransferEntity.setRemark1(ordernumber);
			loanTransferEntity.setLoanJsonList(JsonList);//转账列表
			loanTransferEntity.setPlatformMoneymoremore(pmark);//平台标识
			loanTransferEntity.setTransferAction("3");//转账类型 1.投标2.还款3.其他
			loanTransferEntity.setAction("1");//操作类型1.手动转账2.自动转账
			loanTransferEntity.setTransferType("2");//
			loanTransferEntity.setNeedAudit("");
			loanTransferEntity.setReturnURL("");//页面返回网址
			loanTransferEntity.setNotifyURL("");//后台通知网址
			loanTransferEntity.setSubmitURL("");//提交第三方地址
			String privatekey = Common.privateKeyPKCS8;
			String dataStr = loanTransferEntity.getLoanJsonList()
					+ loanTransferEntity.getPlatformMoneymoremore()
					+ loanTransferEntity.getTransferAction() + loanTransferEntity.getAction()
					+ loanTransferEntity.getTransferType() + loanTransferEntity.getNeedAudit()
					+ loanTransferEntity.getRandomTimeStamp() + loanTransferEntity.getRemark1()
					+ loanTransferEntity.getRemark2() + loanTransferEntity.getRemark3()
					+ loanTransferEntity.getReturnURL() + loanTransferEntity.getNotifyURL();
			// 签名
			RsaHelper rsa = RsaHelper.getInstance();
			String SignInfo = rsa.signData(dataStr, privatekey);
			loanTransferEntity.setSignInfo(SignInfo);
			String LoanJsonList = Common.UrlEncoder(loanTransferEntity.getLoanJsonList(),
					"utf-8");
			loanTransferEntity.setLoanJsonList(LoanJsonList);
			//添加第三方交互记录
			long gid=generatorUtil.GetId();
			Map<String, Object> smaps = new HashMap<String, Object>();
			smaps.put("id", gid);//第三方交互记录id
			smaps.put("merbillNo", ordernumber);//当前操作订单号
			smaps.put("type", "03");//操作类型
			smaps.put("interfaceType", 1);//第三方接口提供商
			smaps.put("detail", dataStr);//加密前数据
			smaps.put("detailEncrypt", SignInfo);//加密后数据
			smaps.put("remark", "");//备注
			handleThreePartyDaoImpl.insertThirdInterfaceRecord(maps);
			
			//添加vip购买提交记录  
			long gvipid=generatorUtil.GetId();
			Map<String, Object> vapmap = new HashMap<String, Object>();
			vapmap.put("mvPayId", gvipid);//第三方交互记录id
			vapmap.put("recordNo", ordernumber);//当前操作订单号
			vapmap.put("amount", amount);//购买金额
			vapmap.put("memberId", memberId);//购买会员id
			vapmap.put("thirdId", gid);//交互记录id 
			vapmap.put("skey", DbKeyUtil.GetDbCodeKey());
			handleThreePartyDaoImpl.AddVipPurchaseRecord(vapmap);
			
			return loanTransferEntity;
		}else {
			
		}
		// TODO Auto-generated method stub return null;ReturnOfDeposit
		return null;
	}
	
	@Override
	public int PurchaseVipBack() {
		int rulet=0;
		try {
			LoanTransferReturnEntity 
					loanTransferReturnEntity=new LoanTransferReturnEntity();
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			loanTransferReturnEntity.
				setResultCode(request.getParameter("ResultCode"));
			loanTransferReturnEntity
				.setMessage(request.getParameter("Message"));//返回信息
			if(loanTransferReturnEntity.getResultCode()!=null){
				if (loanTransferReturnEntity.getResultCode().equals("88")) {
					loanTransferReturnEntity.setStatu(0);
					loanTransferReturnEntity
					.setLoanJsonList(request.getParameter("LoanJsonList"));
					String fah = request.getParameter("LoanJsonList");
					fah = Common.UrlDecoder(fah, "utf-8");
					List<Object> loaninfolist = Common.JSONDecodeList(fah,
							LoanReturnInfoBean.class);
					loanTransferReturnEntity.setLoaninfolists(loaninfolist);//需要处理的转账信息
					loanTransferReturnEntity
					.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					loanTransferReturnEntity
					.setRemark1(request.getParameter("Remark1"));
					loanTransferReturnEntity
					.setRemark2(request.getParameter("Remark2"));
					loanTransferReturnEntity
					.setRemark3(request.getParameter("Remark3"));
				}else {
					loanTransferReturnEntity.setStatu(1);
				}
			String order = loanTransferReturnEntity.getRemark1();
			if(loanTransferReturnEntity.getStatu()==0){//转账成功
				if(loanTransferReturnEntity.getLoaninfolist().size()>0){//判断是否存在转账列表
					//获取提奖会员列表信息
					LoanReturnInfoBean lrib = (LoanReturnInfoBean) loanTransferReturnEntity.getLoaninfolist()
							.get(0);
					//处理获取的数据 //会员id+购买金额（long）+购买年限
					String [] rString=lrib.getRemark().split("n");
					long amount = IntegerAndString.StringToLong(rString[1], 0);
					long inmemberId=IntegerAndString.StringToLong(rString[0], 0);
					long year=IntegerAndString.StringToLong(rString[2], 0);
					long iIdtow    = generatorUtil.GetId() ; 
					long iIdtre    = generatorUtil.GetId() ; 
					Map<String, Object> mapo=new HashMap<String, Object>();
					mapo.put("istatu", 1);
					mapo.put("years", year);
					mapo.put("merbillno", order);
					mapo.put("loanNo", lrib.getLoanNo());
					mapo.put("amount", amount);
					mapo.put("memberId", inmemberId);
					mapo.put("thredId", iIdtow);
					mapo.put("thredIdtow", iIdtre);
					mapo.put("backDetailEncrypt", loanTransferReturnEntity.getSignInfo());
					mapo.put("backDetail", loanTransferReturnEntity.toString());
					mapo.put("subStrin",loanTransferReturnEntity.getMessage());//
					mapo.put("skey", DbKeyUtil.GetDbCodeKey());
					handleThreePartyDaoImpl.PurchaseVipBack(mapo);
				}
			}else {//购买失败
				Map<String, Object> mapo=new HashMap<String, Object>();
				mapo.put("istatu", 0);
				mapo.put("years", 0);
				mapo.put("merbillno", order);
				mapo.put("loanNo", "0");
				mapo.put("amount", 0);
				mapo.put("memberId", 0);
				mapo.put("thredId", 0);
				mapo.put("thredIdtow", 0);
				mapo.put("backDetailEncrypt", loanTransferReturnEntity.getSignInfo());
				mapo.put("backDetail", loanTransferReturnEntity.toString());
				mapo.put("subStrin",loanTransferReturnEntity.getMessage());//失败原因
				mapo.put("skey", DbKeyUtil.GetDbCodeKey());
				handleThreePartyDaoImpl.PurchaseVipBack(mapo);
			}
			HttpServletResponse response 
			= ((ServletWebRequest)RequestContextHolder
					.getRequestAttributes()).getResponse();
			response.setContentType("text/plain;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("SUCCESS");
			response.getWriter().flush();
			response.getWriter().close();
		}else {
			loanTransferReturnEntity.setStatu(1);
			rulet =-1;
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub 
		return rulet;
		
		
	}
	@Override
	public String PurchaseVipReturn() {
		String retur="SUCCESS";
		try {
			LoanTransferReturnEntity 
					loanTransferReturnEntity=new LoanTransferReturnEntity();
			ServletRequestAttributes attributes= (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			request.setCharacterEncoding("UTF-8");
			loanTransferReturnEntity.
				setResultCode(request.getParameter("ResultCode"));
			loanTransferReturnEntity
				.setMessage(request.getParameter("Message"));//返回信息
			if(loanTransferReturnEntity.getResultCode()!=null){
				if (loanTransferReturnEntity.getResultCode().equals("88")) {
					loanTransferReturnEntity.setStatu(0);
					loanTransferReturnEntity
					.setLoanJsonList(request.getParameter("LoanJsonList"));
					String fah = request.getParameter("LoanJsonList");
					fah = Common.UrlDecoder(fah, "utf-8");
					List<Object> loaninfolist = Common.JSONDecodeList(fah,
							LoanReturnInfoBean.class);
					loanTransferReturnEntity.setLoaninfolists(loaninfolist);//需要处理的转账信息
					loanTransferReturnEntity
					.setPlatformMoneymoremore(request.getParameter("PlatformMoneymoremore"));
					loanTransferReturnEntity
					.setRemark1(request.getParameter("Remark1"));
					loanTransferReturnEntity
					.setRemark2(request.getParameter("Remark2"));
					loanTransferReturnEntity
					.setRemark3(request.getParameter("Remark3"));
				}else {
					loanTransferReturnEntity.setStatu(1);
				}
			}else {
				loanTransferReturnEntity.setStatu(1);
			}
			if(loanTransferReturnEntity.getStatu()==0){//成功
				if(loanTransferReturnEntity.getLoaninfolist().size()>0){
				}
			}else {//失败
				loanTransferReturnEntity.getMessage();//失败原因
				retur="FAIL";
			}
			
		} catch (Exception e) {
			retur="ERRO";
			// TODO Auto-generated catch block e.printStackTrace();
			e.printStackTrace();
		}
		// TODO Auto-generated method stub return null;
		return retur;
		
		
	}
}

