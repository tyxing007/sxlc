
package cn.springmvc.dao; 

import java.util.List;
import java.util.Map;

import product_p2p.kit.pageselect.PageEntity;

import cn.springmvc.model.ProjectAppAttachmentEntity;
import cn.springmvc.model.ProjectAppAutoSetEntity;
import cn.springmvc.model.ProjectAppMngFeeEntity;
import cn.springmvc.model.ProjectAppRecordEntity;
import cn.springmvc.model.ProjectBaseInfoEntity;
import cn.springmvc.model.ProjectInvestRedPackageEntity;
import cn.springmvc.model.ProjectPurposeEntity;

/** 
 * 项目申请信息相关
* @author 朱祖轶 
* @Description: TODO 
* @since 
* @date 2016-3-22 下午5:52:03  */
public interface SelectProjectAppRecordDao {
	
	/** 
	* selectAllProjectPurpose 借款意向分页条件查询 
	* TODO(描述)
	* @author 朱祖轶  
	* * @Title: selectAllProjectPurpose 
	* @Description: TODO 
	* @param @param page
	* @param @return 设定文件 
	* @return List<ProjectPurposeEntity> 返回类型 
	* @date 2016-3-22 下午5:54:59
	* @throws 
	*/
	public List<ProjectPurposeEntity> selectAllProjectPurpose(PageEntity page);
	
	/** 
	* selectProjectPurposeById 根据借款意向id查询借款信息 
	* TODO(描述)
	* @author 朱祖轶  
	* * @Title: selectProjectPurposeById 
	* @Description: TODO 
	* @param @param map
	* @param @return 设定文件 
	* @return ProjectPurposeEntity 返回类型 
	* @date 2016-3-22 下午5:56:13
	* @throws 
	*/
	public ProjectPurposeEntity selectProjectPurposeById(Map<String, Object> map);
	
	
	/** 
	* selectProjectAppRecordById(根据意向记录id查询项目申请信息) 
	* TODO(描述)
	* @author 朱祖轶  
	* * @Title: selectProjectAppRecordById 
	* @Description: TODO 
	* @param @param map
	* @param @return 设定文件 
	* @return ProjectAppRecordEntity 返回类型 
	* @date 2016-3-22 下午5:57:20
	* @throws 
	*/
	public ProjectAppRecordEntity selectProjectAppRecordById(Map<String, Object> map);
	
	
	/** 
	* selectProjectBaseInfoById(根据项目基本信息ID查询项目基本信息) 
	* TODO(描述)
	* @author 朱祖轶  
	* * @Title: selectProjectBaseInfoById 
	* @Description: TODO 
	* @param @param map
	* @param @return 设定文件 
	* @return ProjectBaseInfoEntity 返回类型 
	* @date 2016-3-22 下午5:58:10
	* @throws 
	*/
	public ProjectBaseInfoEntity selectProjectBaseInfoById(Map<String, Object> map);
	
	
	/** 
	* selectProjectAppMngFeeById(根据项目申请id 查询项目费用设置) 
	* TODO(描述)
	* @author 朱祖轶  
	* * @Title: selectProjectAppMngFeeById 
	* @Description: TODO 
	* @param @param map
	* @param @return 设定文件 
	* @return ProjectAppMngFeeEntity 返回类型 
	* @date 2016-3-22 下午5:59:15
	* @throws 
	*/
	public ProjectAppMngFeeEntity selectProjectAppMngFeeById(Map<String, Object> map);
	
	
	/** 
	* selectProjectAppAutoSetById(根据项目申请id 查询自动投标设置 ) 
	* TODO(描述)
	* @author 朱祖轶  
	* * @Title: selectProjectAppAutoSetById 
	* @Description: TODO 
	* @param @param map
	* @param @return 设定文件 
	* @return ProjectAppAutoSetEntity 返回类型 
	* @date 2016-3-22 下午6:00:18
	* @throws 
	*/
	public ProjectAppAutoSetEntity selectProjectAppAutoSetById(Map<String, Object> map);
	
	
	/** 
	* selectProjectInvestRedPackageById(根据项目申请id 查询项目惊喜红包配置) 
	* TODO(描述)
	* @author 朱祖轶  
	* * @Title: selectProjectInvestRedPackageById 
	* @Description: TODO 
	* @param @param map
	* @param @return 设定文件 
	* @return List<ProjectInvestRedPackageEntity> 返回类型 
	* @date 2016-3-22 下午6:02:15
	* @throws 
	*/
	public List<ProjectInvestRedPackageEntity> selectProjectInvestRedPackageById(Map<String, Object> map);
	
	
	/** 
	* selectProjectAppAttachmentById(根据项目申请id 查询项目申请附件资料) 
	* TODO(描述)
	* @author 朱祖轶  
	* * @Title: selectProjectAppAttachmentById 
	* @Description: TODO 
	* @param @param map
	* @param @return 设定文件 
	* @return List<ProjectAppAttachmentEntity> 返回类型 
	* @date 2016-3-22 下午6:03:38
	* @throws 
	*/
	public List<ProjectAppAttachmentEntity> selectProjectAppAttachmentById(Map<String, Object> map);
}
