

/** 
* @Title: CoutEntity.java 
* @Package cn.springmvc.model 
* @Description: TODO 
* Copyright: Copyright (c) 2016 
* Company:成都四象联创科技有限公司 
* @author 刘利 
* @date 2016-4-27 下午8:35:15 
* @version V5.0 */
 
package cn.springmvc.model; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import product_p2p.kit.datatrans.IntegerAndString;

/** 
 * @author 刘利 
 * @Description: 统计
 * @since 
 * @date 2016-4-27 下午8:35:15  */

public class CoutEntity {
	private String day;//按天统计
	private String month;//按月统计
	private String week;//按周统计统计
	private int    countInvestMember;//新增投资会员
	private int    countMember;//新增会员
	private int    countLoanMember;//新增借款会员
	private long   sumInvest;//新增投资
	private long   countFrontAccess;//前台访问记录
	
	public long    pRechargeFee;//充值统计
	public String  spRechargeFee;//充值统计
	public long    pwidthdrawFee;//提现统计
	public String  spwidthdrawFee;//提现统计
    private long    timeStempD;//时间戳
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		 this.day = day;
		 SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     Date date = null;
		try {
			date = simpleDateFormat .parse(day);
		} catch (ParseException e) {
			 
		}
		timeStempD = date.getTime();
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public int getCountInvestMember() {
		return countInvestMember;
	}
	public void setCountInvestMember(int countInvestMember) {
		this.countInvestMember = countInvestMember;
	}
	public int getCountMember() {
		return countMember;
	}
	public void setCountMember(int countMember) {
		this.countMember = countMember;
	}
	public int getCountLoanMember() {
		return countLoanMember;
	}
	public void setCountLoanMember(int countLoanMember) {
		this.countLoanMember = countLoanMember;
	}
	public long getSumInvest() {
		return sumInvest;
	}
	public void setSumInvest(long sumInvest) {
		this.sumInvest = sumInvest;
	}
	public long getCountFrontAccess() {
		return countFrontAccess;
	}
	public void setCountFrontAccess(long countFrontAccess) {
		this.countFrontAccess = countFrontAccess;
	}
	public long getpRechargeFee() {
		return pRechargeFee;
	}
	public void setpRechargeFee(long pRechargeFee) {
		this.pRechargeFee = pRechargeFee;
		this.spRechargeFee = IntegerAndString.LongToString(pRechargeFee);
	}
	public String getSpRechargeFee() {
		return spRechargeFee;
	}
	public void setSpRechargeFee(String spRechargeFee) {
		this.spRechargeFee = spRechargeFee;
	}
	public long getPwidthdrawFee() {
		return pwidthdrawFee;
	}
	public void setPwidthdrawFee(long pwidthdrawFee) {
		this.pwidthdrawFee = pwidthdrawFee;
		this.spwidthdrawFee = IntegerAndString.LongToString(pwidthdrawFee);
	}
	public String getSpwidthdrawFee() {
		return spwidthdrawFee;
	}
	public void setSpwidthdrawFee(String spwidthdrawFee) {
		this.spwidthdrawFee = spwidthdrawFee;
	}
	public long getTimeStempD() {
		return timeStempD;
	}
	public void setTimeStempD(long timeStempD) {
		this.timeStempD = timeStempD;
	}
	 
}

