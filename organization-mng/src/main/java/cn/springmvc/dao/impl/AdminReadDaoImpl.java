package cn.springmvc.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import product_p2p.kit.pageselect.PageEntity;
import cn.springmvc.dao.IAdminReadDao;
import cn.springmvc.model.Admin;

@Repository
public class AdminReadDaoImpl extends SqlSessionDaoSupport implements IAdminReadDao {

	
	@Override
	public List<Admin> getAdminsByParam(PageEntity entity) {
		
		return getSqlSession().selectList("adminDaoImpl.getListByParam", entity ,new RowBounds(entity.getPageNum(), entity.getPageSize()));
	}

	

	@Autowired
	@Override
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}



}
