package cn.springmvc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import product_p2p.kit.dbkey.DbKeyUtil;
import product_p2p.kit.pageselect.PageEntity;
import product_p2p.kit.pageselect.PageUtil;
import cn.dictionaries.model.CityInfoEntity;
import cn.dictionaries.model.CountyInfoEntity;
import cn.dictionaries.model.NationInfoEntity;
import cn.dictionaries.model.ProvinceInfoEntity;
import cn.membermng.model.Agreement;
import cn.membermng.model.CompanyInfo;
import cn.membermng.model.ExchangeRecords;
import cn.membermng.model.Friends;
import cn.membermng.model.IntegralGETRecord;
import cn.membermng.model.MemberInfo;
import cn.membermng.model.MemberThirdAuthInfo;
import cn.membermng.model.MemberVouchers;
import cn.membermng.model.MyPoint;
import cn.membermng.model.MyRedPackage;
import cn.membermng.model.PersonalBaseInfo;
import cn.membermng.model.RadPackage;
import cn.membermng.model.SecurityInfo;
import cn.membermng.model.UntreatedMessageEntity;
import cn.springmvc.dao.IMemberReadDao;
import cn.springmvc.dao.IMemberWriteDao;
import cn.springmvc.dao.impl.DictionariesDaoImpl;
import cn.springmvc.dao.impl.IdGeneratorUtil;
import cn.springmvc.service.IMemberService;

@Service
public class MemberInfoServiceImpl implements IMemberService{
	

	@Resource(name="memberInfoReadDaoImpl")
	private IMemberReadDao memberDao;
	
	@Resource(name="memberInfoWriteDaoImpl")
	private IMemberWriteDao memberWriteDao;
	
	@Resource
	private DictionariesDaoImpl dictionariesDaoImpl;
	
	@Override
	public int individualMember(MemberInfo member,PersonalBaseInfo baseInfo) {
		Map<String,Object> param = new HashMap<String, Object>();
		IdGeneratorUtil generatorUtil = IdGeneratorUtil.GetIdGeneratorInstance();
		long id	 = generatorUtil.GetId();
		param.put("skey", 		DbKeyUtil.GetDbCodeKey());
		param.put("mid", 		id);
		param.put("memberNo", member.getMemberNo());
		param.put("lognName", 	member.getLogname());
		param.put("personalPhone", baseInfo.getPersonalPhone());
		param.put("personalPwd", member.getMemberPwd());
		param.put("beinvitateCode", member.getBeinvitateCode());
		param.put("invitateCode", member.getInvitateCode());
		param.put("memberType", member.getMemberType());
		int result = memberWriteDao.individualMember(param);
		if(result == 0){
			generatorUtil.SetIdUsed(id);
		}else {
			generatorUtil.SetIdUsedFail(id);
		}
		return result;
	}

	
	@Override
	public int corporateMember(MemberInfo member,CompanyInfo companyInfo) {
		
		
		
		return 0;
	}

	
	@Override
	public int login(String userName, String password, int userType,String ipAddress,String[] ipInfo,String sourceUrl,String sSessionId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("skey", 				DbKeyUtil.GetDbCodeKey());
		param.put("userName", 			userName);
		param.put("userPwd", 			password);
		param.put("userType", 			userType);
		param.put("ipaddress", 			ipAddress);
		param.put("scountry", 			ipInfo[0]);
		param.put("sregion", 			ipInfo[1]);
		param.put("sprovince",			ipInfo[2]);
		param.put("scity",				ipInfo[3]);
		param.put("sarea", 				ipInfo[4]);
		param.put("ISP", 				ipInfo[5]);
		param.put("sourceUrl", 			sourceUrl);
		param.put("sessionId", 			sSessionId);
		param.put("result", 			1);
		return memberWriteDao.login(param);
	}

	
	@Override
	public int chechPhone(String phone) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("skey", DbKeyUtil.GetDbCodeKey());
		param.put("phone", phone);
		return memberDao.countPhone(param);
	}

	
	@Override
	public int countName(String userName) {

		return memberDao.countName(userName);
	}

	
	@Override
	public int countInvitateCode(String invitateCode) {

		return memberDao.countInvitateCode(invitateCode);
	}
	
	
	@Override
	public MemberInfo memberPersonalInfo(long memberId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("skey", DbKeyUtil.GetDbCodeKey());
		return memberDao.memberPersonalInfo(param);
	}

	
	@Override
	public int editMemberPersonalInfo(long memberId,int sexid, String qqNumber,String homeTown, String currAddress, int provinceId, int cityId,int countyId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("memberId", memberId);
		param.put("sexId", sexid);
		param.put("qqNumber", qqNumber);
		param.put("address", currAddress);
		param.put("homeTown", homeTown);
		param.put("provinceId", provinceId);
		param.put("cityId", cityId);
		param.put("countyId", countyId);
		param.put("skey", DbKeyUtil.GetDbCodeKey());
		return memberWriteDao.editMemberPersonalInfo(param);
	}
	
	
	@Override
	public MemberInfo memberComplanyInfo(long memberId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("skey", DbKeyUtil.GetDbCodeKey());
		return memberDao.memberComplanyInfo(param);
	}
	
	
	@Override
	public int editMemberComplanyInfo(long memberId,int provinceId, int cityId, int countyId,String contactsName,
			String contactsPhone, String contactQQ,String contactEmail, String companyProfile) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("memberId", memberId);
		param.put("provinceId", provinceId);
		param.put("cityId", cityId);
		param.put("countyId", countyId);
		param.put("contactsName", contactsName);
		param.put("contactsQQ", contactQQ);
		param.put("contactsEmail", contactEmail);
		param.put("companyProfile", companyProfile);
		param.put("skey", DbKeyUtil.GetDbCodeKey());
		
		return memberWriteDao.editMemberComplanyInfo(param);
	}
	
	
	@Override
	public List<MemberInfo> friendInvitation(PageEntity entity) {
		entity.getMap().put("skey", DbKeyUtil.GetDbCodeKey());
		return memberDao.friendInvitation(entity);
	}
	
	
	@Override
	public List<Friends> friendList(PageEntity entity) {
		
		return memberDao.friendList(entity);
	}
	
	
	@Override
	public List<Friends> selectConfirmFriendList(PageEntity entity) {
		
		return memberDao.selectConfirmFriendList(entity);
	}
	
	@Override
	public MyPoint points(long memberId,int memberType) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("memberType", memberType);
		return memberDao.points(param);
	}
	
	
	@Override
	public List<IntegralGETRecord> addPoints(PageEntity entity) {
		List<IntegralGETRecord> list = memberDao.addPoints(entity);
		PageUtil.ObjectToPage(entity, list);
		return list;
	}
	
	
	@Override
	public MyRedPackage myRedpackage(int memberType, long memberId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("memberType", memberType);
		return memberDao.myRedPackage(param);
	}
	
	
	@Override
	public List<RadPackage> redPackages(PageEntity entity) {
		
		return memberDao.redPackages(entity);
	}
	
	
	@Override
	public MemberVouchers myVouchers(long memberId, int memberType) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("memberType", memberType);
		return memberDao.myVouchers(param);
	}
	
	
	@Override
	public List<MemberVouchers> vouchers(PageEntity entity) {
		List<MemberVouchers> list = memberDao.vouchers(entity);
		PageUtil.ObjectToPage(entity, list);
		return list;
	}
	
	
	@Override
	public List<MemberVouchers> useVouchers(PageEntity entity) {
		List<MemberVouchers> list = memberDao.useVochers(entity);
		for (int i = 0; i < list.size(); i++) {
			String ctaStatu = list.get(i).getCtaStatus();
			String pStatu	= list.get(i).getpStatus();
			if(ctaStatu != null && !ctaStatu.equals("")){//债权投资
				if(ctaStatu.equals("-1") || ctaStatu.equals("0") || ctaStatu.equals("2")){
					list.get(i).setStatuName("冻结中");
				}else if(ctaStatu.equals("1")){
					list.get(i).setStatuName("失败");
				}else if(ctaStatu.equals("3") || ctaStatu.equals("4")){
					list.get(i).setStatuName("成功");
				}
				list.get(i).setStatu(Integer.parseInt(ctaStatu));
				list.get(i).setLoanTypeName("债权投资");
			}else{//项目投资
				if (pStatu.equals("0") || pStatu.equals("2")) {
					list.get(i).setStatuName("冻结中");
				}else if(pStatu.equals("1")){
					list.get(i).setStatuName("流标");
				}else if(pStatu.equals("3") || pStatu.equals("4")){
					list.get(i).setStatuName("成功");
				}
				list.get(i).setStatu(Integer.parseInt(pStatu));
				list.get(i).setLoanTypeName("项目投资");
			}
		}
		
		PageUtil.ObjectToPage(entity, list);
		return list;
	}
	
	@Override
	public long getRemainderTotal(long memberId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("memberId", memberId);
		param.put("sKey", DbKeyUtil.GetDbCodeKey());
		return memberDao.getRemainderTotal(param);
	}
	
	
	@Override
	public List<CityInfoEntity> getCityList(int pId) {
		return dictionariesDaoImpl.getCityList(pId);
	}
	
	@Override
	public List<CountyInfoEntity> getCountyList(int cId) {
		return dictionariesDaoImpl.getCountyList(cId);
	}
	
	@Override
	public List<ProvinceInfoEntity> getProvinceList() {
		return dictionariesDaoImpl.getProvinceList();
	}
	
	
	@Override
	public List<NationInfoEntity> GetNationList() {
		return dictionariesDaoImpl.GetNationList();
	}
	
	
	
	@Override
	public SecurityInfo securityInfo(long memberId, int memberType) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("memberType", memberType);
		param.put("skey", DbKeyUtil.GetDbCodeKey());
		return memberDao.securityInfo(param);
	}
	
	@Override
	public MemberInfo findMemberInfoByParam(String logname, String memberPwd,int memberType) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("logName", logname);
		param.put("memberPwd", memberPwd);
		param.put("memberType", memberType);
		param.put("skey", DbKeyUtil.GetDbCodeKey());
		return memberDao.findMemberInfoByParam(param);
	}
	
	@Override
	public List<MemberInfo> serachMemberByParam(PageEntity entity) {
		entity.getMap().put("sKey",DbKeyUtil.GetDbCodeKey());
		return memberDao.serachMemberByParam(entity);
	}
	
	@Override
	public int applyAddFriends(long myId, long fId) {
		if(myId == fId){
			return -4;
		}
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("myId", myId);
		param.put("fId", fId);
		return memberWriteDao.applyAddFriends(param);
	}

	@Override
	public List<ExchangeRecords> exchangeRecords(PageEntity entity) {
		
		return memberDao.exchangeRecords(entity);
	}
	
	@Override
	public int agreeAapplyForFriend(long myId, long fId) {
		if(myId == fId){
			return -4;
		}
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("myId", myId);
		param.put("fId", fId);
		return memberWriteDao.agreeAapplyForFriend(param);
	}
	
	@Override
	public int selectMemberIsExist(String loginName,String phone) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("skey", DbKeyUtil.GetDbCodeKey());
		param.put("loginName", loginName);
		param.put("phone", phone);
		return memberDao.selectMemberIsExist(param);
	}
	
	@Override
	public Long selectMemberIdByPhone(String loginName,String phone) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("loginName", loginName);
		param.put("skey", DbKeyUtil.GetDbCodeKey());
		param.put("phone", phone);
		return memberDao.selectMemberIdByPhone(param);
	}

	@Override
	public int confirmReceipt(Map<String, Object> param) {
		return memberWriteDao.confirmReceipt(param);
	}
	
	@Override
	public MemberInfo loadMemberInfo(long memberId,int memberType) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("memberType", memberType);
		return memberDao.loadMemberInfo(param);
		
	}


	@Override
	public int sign(long memberId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		
		//判断今日是否已经签到
		int result = memberDao.checkTodaySignNum(param);
		
		if(result>0){
			return -1;//今日已经签到,不能重复签到
		}
		return memberWriteDao.sign(param);
		
	}


	@Override
	public UntreatedMessageEntity loadUntreatedMessage(long memberId) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		
		return memberDao.loadUntreatedMessage(param);
		
	}
	
	
	
	@Override
	public int countEmail(String email) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("skey", DbKeyUtil.GetDbCodeKey());
		param.put("email", email);
		return memberDao.countEmail(param);
	}
	
	@Override
	public Agreement agreement() {
		
		return memberDao.agreement();
	}
	
	
	@Override
	public int TestTransaction(boolean exit) {
		int chars = (int)(Math.random()*26+97);
		char param = (char)chars;
		char param2 = (char)(chars+1);
		int couunt = memberDao.countName("张三");
		System.out.println("查询张三的总个数："+couunt);
		int result = memberWriteDao.TestTransaction(param,chars);
		int result2 = memberWriteDao.TestTransaction(param2,chars);
		if(chars %3 == 0){
			exit = true;
		}
		if(chars %4 == 0){
			
			throw new RuntimeException("跑忙了,"+param+"_"+param+" ,事物回滚");
		}
		return result;
	}
	
	
	@Override
	public MemberThirdAuthInfo getAutoGiroStatu(Long memberId,int memberType) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("memberId", 	memberId);
		param.put("memberType", memberType);
		return memberDao.getAutoGiroStatu(param);
	}
	
}
