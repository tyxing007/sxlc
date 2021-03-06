

/** 
* @Title: GenerateRepayListDaoImpl.java 
* @Package cn.springmvc.dao.impl 
* @Description: TODO 
* Copyright: Copyright (c) 2016 
* Company:成都四象联创科技有限公司 
* @author 刘利 
* @date 2016-4-1 下午1:50:46 
* @version V5.0 */
 
package cn.springmvc.dao.impl; 

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;  

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
 
import cn.springmvc.dao.ReplayProjectDetailListDao;  
import cn.springmvc.model.CreditorTransBaseEntity;
import cn.springmvc.model.ProjectBaseInfoEntitys;
import cn.springmvc.model.ProjectDetailEntity;

/** 
 * @author 刘利 
 * @Description: 查询项目详情
 * @since 
 * @date 2016-4-1 下午1:50:46  */
@Repository("replayProjectDetailListDaoImpl")
public class ReplayProjectDetailListDaoImpl extends SqlSessionDaoSupport implements ReplayProjectDetailListDao {
	@Resource(name="sqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	 
	@Override
	public ProjectDetailEntity selectProjectDetailByID(long id) {

		ProjectDetailEntity projectAppRecordEntity = null;
		projectAppRecordEntity = getSqlSession().selectOne("GenerateRepay.selectProjectdetail",id);
		return  projectAppRecordEntity;

	}

	@Override
	public ProjectBaseInfoEntitys selectProjectBaseInfoById(long lId) {
		ProjectBaseInfoEntitys entity = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", lId);
		entity = getSqlSession().selectOne("GenerateRepay.selectProjectBaseInfo", param);
		return entity;
	}

	@Override
	public CreditorTransBaseEntity selectCreditorTransInfoById(long lId) {
		
		CreditorTransBaseEntity entity = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", lId);
		entity = getSqlSession().selectOne("GenerateRepay.selectCreditorTransBaseInfo", param);
		return entity;
		
	}
}

