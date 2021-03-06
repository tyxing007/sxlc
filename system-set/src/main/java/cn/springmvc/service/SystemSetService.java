
package cn.springmvc.service; 

import java.util.Map;

import product_p2p.kit.optrecord.InsertAdminLogEntity;

import cn.springmvc.model.CreditorEntity;
import cn.springmvc.model.SystemInfoSetEntity;
import cn.springmvc.model.SystemSetEntity;





/** 
 * 系统设置
* @author ZZY 
* @Description: TODO 
* @since 
* @date 2016-3-15 下午7:25:22  */
public interface SystemSetService {

	/** 
	* findSystemSet 查询系统设置表信息
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: findSystemSet 
	* @Description: TODO 
	* @param @return 设定文件 
	* @return SystemSetEntity 返回类型 
	* @throws 
	*/
	public SystemSetEntity findSystemSet();

	/** 
	* updateSystemSet 修改系统设置 
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: updateSystemSet 
	* @Description: TODO 
	* @param @param systemSetEntity
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int updateSystemSet(SystemSetEntity systemSetEntity,InsertAdminLogEntity entity,String[] sIpInfo);
	
	/** 
	* insertSystemSet 添加系统设置 
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: insertSystemSet 
	* @Description: TODO 
	* @param @param systemSetEntity
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int insertSystemSet(SystemSetEntity systemSetEntity);
	
	/** 
	* findSystemInfoSet 查询平台设置信息
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: findSystemSet 
	* @Description: TODO 
	* @param @return 设定文件 
	* @return SystemSetEntity 返回类型 
	* @throws 
	*/
	public SystemInfoSetEntity findSystemInfoSet();
	
	/** 
	* updateSystemInfoSet 删除平台设置 
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: updateSystemInfoSet 
	* @Description: TODO 
	* @param @param systemSetInfoEntity
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int updateSystemInfoSet();
	
	/** 
	* insertSystemInfoSet 添加平台设置 
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: insertSystemInfoSet 
	* @Description: TODO 
	* @param @param systemInfoSetEntity
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int insertSystemInfoSet(SystemInfoSetEntity systemInfoSetEntity,InsertAdminLogEntity entity,String[] sIpInfo);
	
	
	/** 
	* findInterestMngFee 查询利息管理费
	* TODO(描述)
	* @author 朱祖轶  
	* * @Title: findInterestMngFee 
	* @Description: TODO 
	* @param @return 设定文件 
	* @return String 返回类型 
	* @date 2016-4-13 下午4:08:18
	* @throws 
	*/
	public String findInterestMngFee();
	
	
	/** 
	* setInterestMngFee 设置利息管理费 
	* TODO(描述)
	* @author 朱祖轶  
	* * @Title: setInterestMngFee 
	* @Description: TODO 
	* @param @param PriceDatum
	* @param @return 设定文件 
	* @return int 返回类型 
	* @date 2016-4-13 下午4:13:30
	* @throws 
	*/
	public int setInterestMngFee(String PriceDatum,InsertAdminLogEntity entity,String[] sIpInfo);
}


