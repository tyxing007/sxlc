
package cn.springmvc.dao; 

import java.util.List;
import java.util.Map;

import cn.springmvc.model.CreditorEntity;
import cn.springmvc.model.IntegralRuleEntity;
import cn.springmvc.model.OverdueEntity;
import cn.springmvc.model.QuickRechargeFeeEntity;



/** 
 * 积分规则设置
* @author ZZY 
* @Description: TODO 
* @since 
* @date 2016-3-15 下午6:08:31  */
public interface HandleIntegralRuleDao {
	
	
	/** 
	* updateRegPointsIntegralRule 修改邀请注册积分规则 
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: updateRegPointsIntegralRule 
	* @Description: TODO 
	* @param @param map
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int updateRegPointsIntegralRule(Map<String, Object> map);
	
	
	/** 
	* deleteIntegralRule 根据id删除积分规则
	* TODO 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: deleteIntegralRule 
	* @Description: TODO 
	* @param @param map type 2:项目投资积分规则 3:债权转让投资积分规则 4:充值积分规则 5:借款积分规则 ;id;membertype
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int deleteIntegralRule(Map<String, Object> map);
	
	
	/** 
	* insetRegPointsIntegralRule 添加  邀请注册积分规则 
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: insetRegPointsIntegralRule 
	* @Description: TODO 
	* @param @param map membertype 0:推荐达人  1:理财顾问 2:会员 ;regPoints ;signPoints
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int insetRegPointsIntegralRule(Map<String, Object> map);
	
	
	/** 
	* insertIntegralRule 添加 项目投资 \债权转让投资\ 充值 \  借款 积分 
	* TODO(这里描述这个方法适用条件 – 可选) 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: insertIntegralRule 
	* @Description: TODO 
	* @param @param map 
	* membertype 0:推荐达人  1:理财顾问 2:会员 
	* type 1:邀请注册积分规则 2:项目投资积分规则 3:债权转让投资积分规则 4:充值积分规则 5:借款积分规则
	* id;amount;points
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int insertIntegralRule(Map<String, Object> map);
	
	/** 
	* deleteIntegralRule 根据id修改积分规则
	* TODO 
	* TODO(这里描述这个方法的执行流程 – 可选) 
	* TODO(这里描述这个方法的使用方法 – 可选) 
	* TODO(这里描述这个方法的注意事项 – 可选) 
	* * @Title: deleteIntegralRule 
	* @Description: TODO 
	* @param @param map type 2:项目投资积分规则 3:债权转让投资积分规则 4:充值积分规则 5:借款积分规则 ;id;membertype
	* @param @return 设定文件 
	* @return int 返回类型 
	* @throws 
	*/
	public int updateIntegralRule(int membertype,int type,long amount,long points,long id);
}

