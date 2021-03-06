package cn.membermng.model;

import java.sql.Timestamp;

import product_p2p.kit.datatrans.IntegerAndString;
import product_p2p.kit.datatrans.TimestampAndString;



/***
* 用于封装已结清的还款记录信息
* 
* @author 李杰
* @Description: TODO
* @since
* @date 2016-4-5 下午2:55:09
* 
*/
public class Cleared {

	private Long		loanId;					//借款编号
	private String		projectNo;				//项目编号
	private String		projectName;			//借款项目名称
	private long 		amount;					//借款金额
	private String		sAmount;				//
	private long 		sjAmount;				//最低借款金额
	private String		sSjinAmount;			//
	private int			yearRate;				//年化利率
	private String		sYearRate;				//
	private String		holdDate;				//放款时间
	private String		settleDate;				//结清时间
	private long		repayPrincipal;			//已还本金
	private String		sRepayPrincipal;		//
	private long		repayInterest;			//已还利息
	private String		sRepayInterest;			//
	private long		repayOverdueInterest;	//逾期利息
	private String		sRepayOverdueInterest;	//
	private long		repayOverdue;			//逾期罚金
	private String		sRepayOverdue;			//
	private long		repayPenalty;			//违约金
	private String		sRepayPenalty;			//
	private int			statu;					//结清方式	>0 提前还款 《=0 正常结清
	private int 		sOverStatu;				//结清方式 》存在逾期还款 《=0不存在逾期还款
	
	
	public Cleared() {}


	public Cleared(Long loanId, String projectNo, String projectName,
			long amount, String sAmount, long sjAmount, String sSjinAmount,
			int yearRate, String sYearRate, String holdDate, String settleDate,
			long repayPrincipal, String sRepayPrincipal, long repayInterest,
			String sRepayInterest, long repayOverdueInterest,
			String sRepayOverdueInterest, long repayOverdue,
			String sRepayOverdue, long repayPenalty, String sRepayPenalty,
			int statu) {
		super();
		this.loanId = loanId;
		this.projectNo = projectNo;
		this.projectName = projectName;
		this.amount = amount;
		this.sAmount = sAmount;
		this.sjAmount = sjAmount;
		this.sSjinAmount = sSjinAmount;
		this.yearRate = yearRate;
		this.sYearRate = sYearRate;
		this.holdDate = holdDate;
		this.settleDate = settleDate;
		this.repayPrincipal = repayPrincipal;
		this.sRepayPrincipal = sRepayPrincipal;
		this.repayInterest = repayInterest;
		this.sRepayInterest = sRepayInterest;
		this.repayOverdueInterest = repayOverdueInterest;
		this.sRepayOverdueInterest = sRepayOverdueInterest;
		this.repayOverdue = repayOverdue;
		this.sRepayOverdue = sRepayOverdue;
		this.repayPenalty = repayPenalty;
		this.sRepayPenalty = sRepayPenalty;
		this.statu = statu;
	}


	public Long getLoanId() {
		return loanId;
	}


	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}


	public String getProjectNo() {
		return projectNo;
	}


	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public long getAmount() {
		return amount;
	}


	public void setAmount(long amount) {
		this.amount = amount;
		java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00"); 
		this.sAmount =  myformat.format(Double.valueOf(IntegerAndString.LongToString(amount))) ;      
	}


	public String getsAmount() {
		return sAmount;
	}


	public void setsAmount(String sAmount) {
		this.sAmount = sAmount;
	}


	public long getSjAmount() {
		return sjAmount;
	}


	public void setSjAmount(long sjAmount) {
		this.sjAmount = sjAmount;
		java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00"); 
		this.sSjinAmount =  myformat.format(Double.valueOf(IntegerAndString.LongToString(sjAmount))) ;     
	}


	public String getsSjinAmount() {
		return sSjinAmount;
	}


	public void setsSjinAmount(String sSjinAmount) {
		this.sSjinAmount = sSjinAmount;
	}


	public int getYearRate() {
		return yearRate;
	}


	public void setYearRate(int yearRate) {
		this.yearRate = yearRate;
		java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00"); 
		this.sYearRate =  myformat.format(Double.valueOf(IntegerAndString.LongToString(yearRate))) ;     
	}


	public String getsYearRate() {
		return sYearRate;
	}


	public void setsYearRate(String sYearRate) {
		this.sYearRate = sYearRate;
	}


	public String getHoldDate() {
		return holdDate;
	}


	public void setHoldDate(String holdDate) {
		this.holdDate = TimestampAndString.TimestampToString2(Timestamp.valueOf(holdDate));
	}


	public String getSettleDate() {
		return settleDate;
	}


	public void setSettleDate(String settleDate) {
		this.settleDate = TimestampAndString.TimestampToString2(Timestamp.valueOf(settleDate));
	}


	public long getRepayPrincipal() {
		return repayPrincipal;
	}


	public void setRepayPrincipal(long repayPrincipal) {
		this.repayPrincipal = repayPrincipal;
		java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00"); 
		this.sRepayPrincipal =  myformat.format(Double.valueOf(IntegerAndString.LongToString(repayPrincipal))) ;    
	 }


	public String getsRepayPrincipal() {
		return sRepayPrincipal;
	}


	public void setsRepayPrincipal(String sRepayPrincipal) {
		this.sRepayPrincipal = sRepayPrincipal;
	}


	public long getRepayInterest() {
		return repayInterest;
	}


	public void setRepayInterest(long repayInterest) {
		this.repayInterest = repayInterest;
		java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00"); 
		this.sRepayInterest =  myformat.format(Double.valueOf(IntegerAndString.LongToString(repayInterest))) ;    
	}


	public String getsRepayInterest() {
		return sRepayInterest;
	}


	public void setsRepayInterest(String sRepayInterest) {
		this.sRepayInterest = sRepayInterest;
	}


	public long getRepayOverdueInterest() {
		return repayOverdueInterest;
	}


	public void setRepayOverdueInterest(long repayOverdueInterest) {
		this.repayOverdueInterest = repayOverdueInterest;
		java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00"); 
		this.sRepayOverdueInterest =  myformat.format(Double.valueOf(IntegerAndString.LongToString(repayOverdueInterest))) ;   
	}


	public String getsRepayOverdueInterest() {
		return sRepayOverdueInterest;
	}


	public void setsRepayOverdueInterest(String sRepayOverdueInterest) {
		this.sRepayOverdueInterest = sRepayOverdueInterest;
	}


	public long getRepayOverdue() {
		return repayOverdue;
	}


	public void setRepayOverdue(long repayOverdue) {
		this.repayOverdue = repayOverdue;
		java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00"); 
		this.sRepayOverdue =  myformat.format(Double.valueOf(IntegerAndString.LongToString(repayOverdue))) ;    
	}


	public String getsRepayOverdue() {
		return sRepayOverdue;
	}


	public void setsRepayOverdue(String sRepayOverdue) {
		this.sRepayOverdue = sRepayOverdue;
	}


	public long getRepayPenalty() {
		return repayPenalty;
	}


	public void setRepayPenalty(long repayPenalty) {
		this.repayPenalty = repayPenalty;

		java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00"); 
		this.sRepayPenalty =  myformat.format(Double.valueOf(IntegerAndString.LongToString(repayPenalty))) ;   
	}


	public String getsRepayPenalty() {
		return sRepayPenalty;
	}


	public void setsRepayPenalty(String sRepayPenalty) {
		this.sRepayPenalty = sRepayPenalty;
	}


	public int getStatu() {
		return statu;
	}


	public void setStatu(int statu) {
		this.statu = statu;
	}


	public int getsOverStatu() {
		return sOverStatu;
	}


	public void setsOverStatu(int sOverStatu) {
		this.sOverStatu = sOverStatu;
	}
	
	
	
}

