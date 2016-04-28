

/** 
* @Title: IndexServiceImpl.java 
* @Package cn.springmvc.service.impl 
* @Description: TODO 
* Copyright: Copyright (c) 2016 
* Company:成都四象联创科技有限公司 
* @author 刘利 
* @date 2016-4-8 上午11:08:03 
* @version V5.0 */
 
package cn.springmvc.service.impl; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import product_p2p.kit.datatrans.IntegerAndString;
import product_p2p.kit.dbkey.DbKeyUtil;
 
import cn.springmvc.dao.IndexDao;
import cn.springmvc.model.CreditorTransferListEntity;
import cn.springmvc.model.InvestEntity;
import cn.springmvc.model.ProjectAppRecordEntity;
import cn.springmvc.service.IndexService;

/** 
 * @author 刘利 
 * @Description: 网站首页
 * @since 
 * @date 2016-4-8 上午11:08:03  */
@Service("indexServiceImpl")
public class IndexServiceImpl implements IndexService {
	@Resource(name="indexDaoImpl")
	private  IndexDao indexDao;
	@Override
	public String selectIndexStatistic() {  
		
		Map<String, Object> map = indexDao.selectIndexStatistic(DbKeyUtil.GetDbCodeKey()); 
		
		StringBuffer results= new StringBuffer(map.get("countmember").toString());
		results.append(","+IntegerAndString.LongToString(Long.valueOf(map.get("IncomeTotal").toString())));
		results.append(","+IntegerAndString.LongToString(Long.valueOf(map.get("totalLoanAmount").toString())));
		results.append(","+IntegerAndString.LongToString(Long.valueOf(map.get("RiskReserveFund").toString())));  
       
		return results.toString();

	}
	@Override
	public List<ProjectAppRecordEntity> selectInvestListIndex() {
		
		return indexDao.selectInvestListIndex();
		
	}
	@Override
	public List<InvestEntity> selectInvestRecordIndex(Map<String, Object> map) {
		
		map.put("sKey", DbKeyUtil.GetDbCodeKey());
		return indexDao.selectInvestRecordIndex(map);
		
	}
	@Override
	public List<CreditorTransferListEntity> selectCreditorTransferListIndex() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sKey", DbKeyUtil.GetDbCodeKey());
		return indexDao.selectCreditorTransferListIndex(map);
		
	}
}

