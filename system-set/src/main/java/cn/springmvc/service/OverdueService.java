
package cn.springmvc.service; 

import java.util.List;
import java.util.Map;

import product_p2p.kit.optrecord.InsertAdminLogEntity;
import product_p2p.kit.pageselect.PageEntity;

import cn.springmvc.model.CreditorEntity;
import cn.springmvc.model.OverdueEntity;



/** 
 * 逾期设置
* @author ZZY 
* @Description: TODO 
* @since 
* @date 2016-3-15 上午9:29:30  */
public interface OverdueService {
	/** 
	* findAllOverdue 逾期费用设置查询 
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: findAllOverdue 
	* @Description: TODO 
	* @param @return 设定文件 
	* @return List<OverdueEntity> 返回类型 
	* @throws 
	*/
	public List<OverdueEntity> findAllOverdue(PageEntity pageEntity);
	
	/** 
	* findOverdueLimit 逾期程度界限查询
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: findOverdueLimit 
	* @Description: TODO 
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public Integer findOverdueLimit();
	
	
	/** 
	* findMaxOverdue 查询目前最大的逾期时间设置 
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: findMaxOverdue 
	* @Description: TODO 
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int findMaxOverdue();
	
	
	/** 
	* insertOverdueLimit 添加 逾期程度设置 
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: insertOverdueLimit 
	* @Description: TODO 
	* @param @param map
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int insertOverdueLimit(Map<String, Object> map,InsertAdminLogEntity entity,String[] sIpInfo);
	
	/** 
	* updateOverdueLimit 修改逾期程度设置 
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: updateOverdueLimit 
	* @Description: TODO 
	* @param @param map
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int updateOverdueLimit(Map<String, Object> map,InsertAdminLogEntity entity,String[] sIpInfo);
	
	
	/** 
	* insertOverdue 添加逾期天数设置 
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: insertOverdue 
	* @Description: TODO 
	* @param @param overdueEntity
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int insertOverdue(OverdueEntity overdueEntity,InsertAdminLogEntity entity,String[] sIpInfo);
	
	
	/** 
	* deleteOverdue 删除逾期天数设置 
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: deleteOverdue 
	* @Description: TODO 
	* @param @param map
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int deleteOverdue(Map<String, Object> map,InsertAdminLogEntity entity,String[] sIpInfo);
}

