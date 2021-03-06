package cn.springmvc.model;
/**
 * 项目审批活动点实体类
 * @author 刘利
 *
 */
public class ActivitiesProjectEntity {
	/**
	 * 项目审批活动点id
	 */
	private long  id;	
	/**
	 * 活动点名称
	 */
	private String apName;	
	/**
	 * 审核权限
	 */
	private long roleID;
	/**
	 * 状态 0：无效 1：有效
	 */
	private int statu;

	/**
	 * 关联操作名称
	 */
	private String optName; 
	private int roleType ;
	private int result;
	public String getApName() {
		return apName;
	}
	public void setApName(String apName) {
		this.apName = apName;
	}
	public long getRoleID() {
		return roleID;
	}
	public void setRoleID(long roleID) {
		this.roleID = roleID;
	}
	
	public int getStatu() {
		return statu;
	}
	public void setStatu(int statu) {
		this.statu = statu;
	}
	public String getOptName() {
		return optName;
	}
	public void setOptName(String optName) {
		this.optName = optName;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ActivitiesProjectEntity [id=" + id + ", apName=" + apName
				+ ", roleID=" + roleID + ", Statu=" + statu + ", optName="
				+ optName + "]";
	}
	public int getRoleType() {
		return roleType;
	}
	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}

}
