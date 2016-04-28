
package cn.springmvc.dao.impl; 

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;

import product_p2p.kit.datatrans.IntegerAndString;

import cn.springmvc.dao.HandleThreePartyDao;


/** 
 * 第三方交互相关操作
* @author 朱祖轶 
* @Description: TODO 
* @since 
* @date 2016-3-28 下午2:12:59  */
@Component("handleThreePartyDaoImpl")
public class HandleThreePartyDaoImpl extends SqlSessionDaoSupport implements HandleThreePartyDao{
	@Override
	@Resource(name="sqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Override
	public int insertThirdInterfaceRecord(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return 0;
		return getSqlSession().insert("ThreePartyXML.insertThirdInterfaceRecord", map);
	}

	@Override
	public Map<String, Object> openAccountBack(Map<String, Object> map) {
			
		// TODO Auto-generated method stub return null;
		getSqlSession().selectOne("ThreePartyXML.openAccountBack",map);
		return map;
	}

	@Override
	public Map<String, Object> rechargeBack(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return null;
		getSqlSession().selectOne("ThreePartyXML.rechargeBack",map);
		return map;
	}
	@Override
	public Map<String, Object> authorizeBack(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return null;
		getSqlSession().selectOne("ThreePartyXML.authorizeBack",map);
		return map;
	}
	@Override
	public Map<String, Object> ProjectFail(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return null;
		getSqlSession().selectOne("ThreePartyXML.ProjectFail",map);
		return map;
	}

	@Override
	public Map<String, Object> MemberWithdrawalBack_Qianduoduo(
			Map<String, Object> map) {
		
		// TODO Auto-generated method stub return null;
		getSqlSession().selectOne("ThreePartyXML.MemberWithdrawalBack_Qianduoduo",map);
		return map;
	}

	@Override
	public Map<String, Object> WithdraBack(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return null;
		getSqlSession().selectOne("ThreePartyXML.WithdraBack",map);
		return map;
	}
	@Override
	public String generateorderNo(String prefix) {
		Map<String, Object> map=new HashMap<String, Object>();
		// TODO Auto-generated method stub return null;
		map.put("orderNamePre", prefix);
		map.put("num", 14);
		getSqlSession().selectOne("ThreePartyXML.generateorderNo", map);
		String order="";
		order=map.get("newOrderNo").toString();
		return order;
	}
	
	@Override
	public int CreditorTransfer(Map<String, Object> map) {
		// TODO Auto-generated method stub return null;
		getSqlSession().selectOne("ThreePartyXML.CreditorTransfer", map);
		int rule=IntegerAndString.StringToInt(map.get("result").toString(), 0);
		return rule;
	}
	@Override
	public Map<String, Object> addInvestIncome(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return null;
		getSqlSession().selectOne("ThreePartyXML.addInvestIncome", map);
		return map;
	}
	@Override
	public int updateProjrctTransStatu(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return 0;
		return getSqlSession().update("ThreePartyXML.updateProjrctTransStatu", map);
	}
	@Override
	public Map<String, Object> TransFail(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return null;
		getSqlSession().selectOne("ThreePartyXML.TransFail", map);
		return map;
	}
	@Override
	public Map<String, Object> ReturnOfDeposit(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return null;
		getSqlSession().selectOne("ThreePartyXML.ReturnOfDeposit", map);
		return map;
	}
	@Override
	public int SurpriseRedBack(Map<String, Object> map) {
		
		// TODO Auto-generated method stub 
		getSqlSession().selectOne("ThreePartyXML.SurpriseRedBack", map);
		int rule=IntegerAndString.StringToInt(map.get("result").toString(), 0);
		return rule;
	}
	@Override
	public int DataTagBySurpriseRed(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return 0;
		getSqlSession().selectOne("ThreePartyXML.DataTagBySurpriseRed", map);
		int rule=IntegerAndString.StringToInt(map.get("result").toString(), 0);
		return rule;
	}
	@Override
	public int insertMemberTransferRecord(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return 0;
		return getSqlSession().insert("ThreePartyXML.insertMemberTransferRecord", map);
	}
	@Override
	public int MemberTransferBack(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return 0;
		getSqlSession().selectOne("ThreePartyXML.MemberTransferBack", map);
		int rule=IntegerAndString.StringToInt(map.get("result").toString(), 0);
		return rule;
	}
	@Override
	public int PlatformAwardMoneyBack(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return 0;
		getSqlSession().selectOne("ThreePartyXML.PlatformAwardMoneyBack", map);
		int rule=IntegerAndString.StringToInt(map.get("result").toString(), 0);
		return rule;
	}

	@Override
	public int RepayBackQianduoduo(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return 0;
		getSqlSession().selectOne("ThreePartyXML.RepayBackQianduoduo", map);
		int rule=IntegerAndString.StringToInt(map.get("result").toString(), 0);
		return rule;
	}
	@Override
	public int AddVipPurchaseRecord(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return 0;
		return getSqlSession().insert("ThreePartyXML.AddVipPurchaseRecord", map);
	}
	@Override
	public int PurchaseVipBack(Map<String, Object> map) {
		
		// TODO Auto-generated method stub return 0;
		getSqlSession().selectOne("ThreePartyXML.PurchaseVipBack", map);
		int rule=IntegerAndString.StringToInt(map.get("result").toString(), 0);
		return rule;
	}
}
