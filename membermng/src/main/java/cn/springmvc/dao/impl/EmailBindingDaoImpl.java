

/** 
* @Title: MobileEmailBindingDaoImpl.java 
* @Package cn.securitycenter.dao.impl 
* @Description: TODO 
* Copyright: Copyright (c) 2016 
* Company:成都四象联创科技有限公司 
* @author 刘利 
* @date 2016-3-29 上午11:33:21 
* @version V5.0 */
 
package cn.springmvc.dao.impl; 

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import cn.springmvc.dao.EmailBindingDao;
 

/** 
 * @author 刘利 
 * @Description: 会员Email绑定
 * @since 
 * @date 2016-3-29 上午11:33:21  */
@Repository("emailBindingDaoImpl")
public class EmailBindingDaoImpl extends SqlSessionDaoSupport implements EmailBindingDao {
	@Resource(name="sqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Override
	public int updatepersonEmail(Map<String, Object> map) {
		
		return getSqlSession().update("emailBinding.updatepersonEmail", map);
		
	}

	@Override
	public int updatecompanyEmail(Map<String, Object> map) {
		
		return getSqlSession().update("emailBinding.updatepersonEmail", map);
		
	}
}

