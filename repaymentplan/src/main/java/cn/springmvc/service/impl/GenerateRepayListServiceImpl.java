

/** 
* @Title: GenerateRepayListService.java 
* @Package cn.springmvc.service.impl 
* @Description: TODO 
* Copyright: Copyright (c) 2016 
* Company:成都四象联创科技有限公司 
* @author 刘利 
* @date 2016-4-1 下午1:59:16 
* @version V5.0 */
 
package cn.springmvc.service.impl; 

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import product_p2p.kit.datatrans.IntegerAndString;
import product_p2p.kit.datatrans.TimestampAndString;
import product_p2p.kit.dbkey.DbKeyUtil;
import cn.springmvc.dao.GenerateRepayListDao;
import cn.springmvc.dao.ReplayProjectDetailListDao;
import cn.springmvc.dao.impl.IdGeneratorUtil;
import cn.springmvc.model.CreditorTransBaseEntity;
import cn.springmvc.model.LoanRepayEntitys;
import cn.springmvc.model.ProjectBaseInfoEntitys;
import cn.springmvc.model.ProjectDetailEntity;
import cn.springmvc.service.GenerateRepayListService;
import cn.springmvc.utitls.RepalyUtitls;

/** 
 * @author 刘利 
 * @Description: 项目发布后生成还款计划
 * @since 
 * @date 2016-4-1 下午1:59:16  */
@Service("generateRepayListServiceImpl")
public class GenerateRepayListServiceImpl implements GenerateRepayListService {
	@Resource(name="generateRepayListDaoImpl")
	private  GenerateRepayListDao generateRepayListDao; 
	@Resource(name="replayProjectDetailListDaoImpl")
	private  ReplayProjectDetailListDao replayProjectDetailListDao; 
	@Override
	public int GenerateRepayList(long applyID) {
		
		ProjectDetailEntity projectAppRecordEntity = null;
		projectAppRecordEntity = replayProjectDetailListDao.selectProjectDetailByID(applyID);
		if(projectAppRecordEntity == null ){
			return -1;
		}
		IdGeneratorUtil generatorUtil = IdGeneratorUtil.GetIdGeneratorInstance();  
		//3:年、2:月、1:日	 0：天标 1：月标 2：年标
		int deadLineType = projectAppRecordEntity
				.getProjectBaseInfoentity().getDeadlineType() + 1;	 
		//借款金额
		String amounts = projectAppRecordEntity
				.getProjectBaseInfoentity().getAmounts();
		//年化利率
		String yearrates = projectAppRecordEntity
				.getProjectBaseInfoentity().getYearRates();
		//借款期限
		int  deadline = projectAppRecordEntity
				.getProjectBaseInfoentity().getDeadline();
		//还款方式
		int replayway = projectAppRecordEntity
				.getProjectBaseInfoentity().getRepayWay();
		 SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		 String presentDate3 = sdf3.format(new Date());//获取当前系统时间
		 List<LoanRepayEntitys> planList = RepalyUtitls.getIncomePlan2(deadLineType,amounts,yearrates,Short.valueOf(deadline+""),Short.valueOf(replayway+""), presentDate3);
		 LoanRepayEntitys planEntity = new LoanRepayEntitys();
		 String planStr = ""; 
		 int iSize = planList.size();
		 for(int m = 0;m < iSize; m++ ){ 
			Long id = generatorUtil.GetId();
			planEntity = planList.get(m); 
			if(planStr.equals("")) {
				planStr = RepalyUtitls.StringToLong(planEntity.getCorpus())+","+RepalyUtitls.StringToLong(planEntity.getInterest())+","+planEntity.getRetrieveDateTime()+","+(m+1) +","+id+"";
			}else{
				planStr = planStr+";"+RepalyUtitls.StringToLong(planEntity.getCorpus())+","+RepalyUtitls.StringToLong(planEntity.getInterest())+","+planEntity.getRetrieveDateTime()+","+(m+1) +","+id+"";
			}   
		 }
		 Map<String,Object> map =new HashMap<String,Object>();
		 map.put("iAppId", applyID);
		 map.put("sInfo",  planStr);
		 map.put("sKey",   DbKeyUtil.GetDbCodeKey());
		 int iResult  = generateRepayListDao.GenerateRepayList(map); 
		 String[] str = planStr.split(";");
		 if(str.length >0){
			for (int i = 0; i < str.length; i++) {
				String[] info = str[i].split(",");
				if(info.length>3){
					if(iResult == 0){
						generatorUtil.SetIdUsed(Long.valueOf(info[4]));
					}else{
						generatorUtil.SetIdUsedFail(Long.valueOf(info[4]));
					}
				}
			}
		 }
		 return iResult; 
	}
	
	@Override
	public long GetForecastIncome(long lProId, long lInvestAmount) {
		long lResult = 0;
		ProjectBaseInfoEntitys entity = replayProjectDetailListDao.selectProjectBaseInfoById(lProId);
		//3:年、2:月、1:日	 0：天标 1：月标 2：年标
		int deadLineType = entity.getDeadlineType() + 1;	 
		//借款金额
		String amounts = IntegerAndString.LongToString2(lInvestAmount); //entity.getAmounts();
		//年化利率
		String yearrates = entity.getYearRates();
		//借款期限
		int  deadline = entity.getDeadline();
		//还款方式
		int replayway = entity.getRepayWay();
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String presentDate3 = sdf3.format(new Date());//获取当前系统时间
		List<LoanRepayEntitys> planList = RepalyUtitls.getIncomePlan2(deadLineType,amounts,yearrates,Short.valueOf(deadline+""),Short.valueOf(replayway+""), presentDate3);
		
		int iSize = 0;
		LoanRepayEntitys repayEntity = null;
		if(planList!=null){
			iSize = planList.size();
		}
		for(int i=0;i<iSize;i++){
			repayEntity = planList.get(i);
			lResult = lResult + IntegerAndString.StringToLong(repayEntity.getInterest());			// repayEntity.getsDRepayInterest();
		}
		
		return lResult;
		
	}

	@Override
	public long CreditorTransForecastIncome(long lProId, long lInvestAmount) {
		
		long lResult = 0;
		
		CreditorTransBaseEntity entity = replayProjectDetailListDao.selectCreditorTransInfoById(lProId);
		if(entity == null){
			return lResult;
		}
		int iDealType = entity.getiSurplusType();
		long[] iResult = {0,0};
		Date dNow = new Date();
		TimestampAndString.getDateSubMonthDay(dNow, entity.getTtEndDate(), iResult, iDealType);
		
		//3:年、2:月、1:日	 0：天标 1：月标 2：年标
		int deadLineType = entity.getiSurplusType() + 1;	 
		//借款金额
		String amounts = IntegerAndString.LongToString2(lInvestAmount); //entity.getAmounts();
		//年化利率
		String yearrates = IntegerAndString.LongToString2(entity.getiYearRate());
		//借款期限
		int  deadline = (int)iResult[0];
		//还款方式
		int replayway = entity.getsRepayWay();
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String presentDate3 = sdf3.format(new Date());//获取当前系统时间
		List<LoanRepayEntitys> planList = RepalyUtitls.getIncomePlan2(deadLineType,amounts,yearrates,Short.valueOf(deadline+""),Short.valueOf(replayway+""), presentDate3);
		
		int iSize = 0;
		LoanRepayEntitys repayEntity = null;
		if(planList!=null){
			iSize = planList.size();
		}
		for(int i=0;i<iSize;i++){
			repayEntity = planList.get(i);
			lResult = lResult + repayEntity.getsDRepayInterest();
		}
		
		if(iResult[1]>0){
			//3:年、2:月、1:日	 0：天标 1：月标 2：年标
			deadLineType = entity.getiSurplusType();	 
			//借款期限
			deadline = (int)iResult[1];
			planList = RepalyUtitls.getIncomePlan2(deadLineType,amounts,yearrates,Short.valueOf(deadline+""),Short.valueOf(replayway+""), presentDate3);
					
			iSize = 0;
			repayEntity = null;
			if(planList!=null){
				iSize = planList.size();
			}
			for(int i=0;i<iSize;i++){
				repayEntity = planList.get(i);
				lResult = lResult + repayEntity.getsDRepayInterest();
			}
		}
		
		return lResult;
		
	}

}

