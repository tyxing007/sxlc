

/** 
* @Title: PostLoanManageServiceImpl.java 
* @Package cn.springmvc.service.impl 
* @Description: TODO 
* Copyright: Copyright (c) 2016 
* Company:成都四象联创科技有限公司 
* @author 刘利 
* @date 2016-4-15 下午6:09:26 
* @version V5.0 */
 
package cn.springmvc.service.impl; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import product_p2p.kit.dbkey.DbKeyUtil;
import product_p2p.kit.optrecord.InsertAdminLogEntity;
import product_p2p.kit.pageselect.PageEntity;
import product_p2p.kit.pageselect.PageUtil; 
import cn.springmvc.dao.PostLoanManageDao;
import cn.springmvc.dao.PostLoanManageListDao;
import cn.springmvc.dao.impl.IdGeneratorUtil;
import cn.springmvc.dao.impl.OptRecordWriteDaoImpl;
import cn.springmvc.model.LoanRepayUrgedRecordEntity;
import cn.springmvc.model.PostProjectEntity;
import cn.springmvc.model.ProjectAfterLoanAttachmentEntity;
import cn.springmvc.model.ProjectAfterLoanInfoEntity;
import cn.springmvc.model.ProjectAppRecordEntity;
import cn.springmvc.model.ReplayStatisticsEntity;
import cn.springmvc.service.PostLoanManageService;

/** 
 * @author 刘利 
 * @Description: 贷后管理
 * @since 
 * @date 2016-4-15 下午6:09:26  */
@Service("postLoanManageServiceImpl")
public class PostLoanManageServiceImpl implements PostLoanManageService {
	@Resource(name="postLoanManageListDaoImpl")
	private PostLoanManageListDao postLoanManageListDao; 
	@Resource(name="postLoanManageDaoImpl")
	private PostLoanManageDao postLoanManageDao; 
	@Resource(name="optRecordWriteDaoImpl")
	private OptRecordWriteDaoImpl optRecordWriteDaoImpl;
	
	@Override
	public void getOverdueCollection(
			PageEntity pageEntity) {

		List<PostProjectEntity> list = postLoanManageListDao.getOverdueCollection(pageEntity);
		PageUtil.ObjectToPage(pageEntity, list);
		
	}
	@Override
	public int insertLoanRepayUrgedRecord(
			LoanRepayUrgedRecordEntity loanRepayUrgedRecordEntity,
			InsertAdminLogEntity logentity,String[] sIpInfo) {
		
		if(loanRepayUrgedRecordEntity == null) {
			return 0;
		}
		int result = postLoanManageDao.insertLoanRepayUrgedRecord(loanRepayUrgedRecordEntity);
		if(result == 1) {
		    logentity.setsDetail("添加催收记录 :"+loanRepayUrgedRecordEntity.getUrgedDetail());
			optRecordWriteDaoImpl.InsertAdminOptRecord(logentity, sIpInfo);
		}
		return result;
		
	}
	@Override
	public PostProjectEntity getBillingDetails(int repayID) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("skey",     DbKeyUtil.GetDbCodeKey());
		map.put("repayID",                   repayID);
		return postLoanManageListDao.getBillingDetails(map);
	}
	@Override
	public int insertProjectAfterLoanInfo(Map<String, Object> map,
			InsertAdminLogEntity logentity, String[] sIpInfo) {
		
		IdGeneratorUtil generatorUtil = IdGeneratorUtil.GetIdGeneratorInstance();
		long id = generatorUtil.GetId();
		map.put("lId", id); 
		int result = postLoanManageDao.insertProjectAfterLoanInfo(map);
		if(result == 1) {
			generatorUtil.SetIdUsed(id); 
		    logentity.setsDetail("添加贷后监管信息 :"+map.get("details"));
			optRecordWriteDaoImpl.InsertAdminOptRecord(logentity, sIpInfo);
		}else {
			generatorUtil.SetIdUsedFail(id);
		}
		return result;
	}
	@Override
	public void selectProjectAfterLoanList(PageEntity pageEntity) {
		
		List<ProjectAfterLoanInfoEntity> list = postLoanManageListDao.selectProjectAfterLoanList(pageEntity);
		PageUtil.ObjectToPage(pageEntity, list);
		
	}
	@Override
	public List<ProjectAfterLoanInfoEntity> selectProjectAfterLoanFront(
			long applyId) {
		
		return postLoanManageListDao.selectProjectAfterLoanFront(applyId);
		
	}
	@Override
	public List<ProjectAfterLoanAttachmentEntity> selectProjectAfterLoanAttach(
			long infoId) {
		
		return postLoanManageListDao.selectProjectAfterLoanAttach(infoId);
		
	}
	@Override
	public ProjectAfterLoanInfoEntity selectProjectAfterLoandetail(long infoId) {
		
		return postLoanManageListDao.selectProjectAfterLoandetail(infoId);
		
	}
	@Override
	public int deleteProjectAfterLoanInfoByID(long infoId,InsertAdminLogEntity 
			logentity,String[] sIpInfo) {
		
		ProjectAfterLoanInfoEntity entity = postLoanManageListDao.selectProjectAfterLoandetail(infoId);
		postLoanManageDao.deleteAfterLoanAttachByinfoId(infoId);
		int result = postLoanManageDao.deleteProjectAfterLoanInfoByID(infoId);
		if(result == 1) { 
		    logentity.setsDetail("删除贷后监管信息 :"+entity.getAbstracts());
			optRecordWriteDaoImpl.InsertAdminOptRecord(logentity, sIpInfo);
		}
		return result;
		
	}
	@Override
	public void getProjectLoanList(PageEntity pageEntity) {
		
		List<ProjectAppRecordEntity> list = postLoanManageListDao.getProjectLoanList(pageEntity);
		PageUtil.ObjectToPage(pageEntity, list); 
		
	}
	@Override
	public void getMaturingBillThiree(PageEntity pageEntity) {
		
		List<ReplayStatisticsEntity> list = postLoanManageListDao.getMaturingBillThiree(pageEntity);
		PageUtil.ObjectToPage(pageEntity, list); 
		
	}
	@Override
	public void getLatePayments(PageEntity pageEntity) {
		
		List<ReplayStatisticsEntity> list = postLoanManageListDao.getLatePayments(pageEntity);
		PageUtil.ObjectToPage(pageEntity, list);
		
	}
	@Override
	public void getLoanRepay(PageEntity pageEntity) {
		
		List<PostProjectEntity> list = postLoanManageListDao.getLoanRepay(pageEntity);
		PageUtil.ObjectToPage(pageEntity, list);
		
	}
	@Override
	public void getRepayBillingDetails(PageEntity pageEntity) {
		
		List<PostProjectEntity> list = postLoanManageListDao.getRepayBillingDetails(pageEntity);
		PageUtil.ObjectToPage(pageEntity, list);
		
	}
	@Override
	public void getMaturingBill(PageEntity pageEntity) {
		
		List<PostProjectEntity> list = postLoanManageListDao.getMaturingBill(pageEntity);
		PageUtil.ObjectToPage(pageEntity, list);
		
	}

}
