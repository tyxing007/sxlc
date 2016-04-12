package product_p2p.kit.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/***
 * 字符串辅助类
 * @author LiJie
 *
 */
public class StringUtils {

	
	
	
	/***
	* 检查身份证格式是否正确 
	* @author 李杰
	* @Title: checkIdCard
	* @param idCard 
	* @date 2016-3-23 上午11:15:45
	 */
	public static boolean checkIdCard(String idCard){
		Pattern pattern = Pattern.compile("^\\d{17}(\\d|x|X){1}$");
		return exParent(pattern, idCard);
	}
	
	
	/***
	 * 检查用户电话号码
	 * @param phone					用户电话号码()
	 * @return
	 */
	public static boolean checkPhone(String phone){
		Pattern pattern = Pattern.compile("^1\\d{10}");
		return exParent(pattern,phone);
	}
	
	
	/***
	 * 检查用户名是否有效
	 * @param useName				用户名称(2-8位中文)
	 */
	public static boolean checkUserName(String userName){
		Pattern pattern = Pattern.compile("[\u4E00-\u9FA5\uf900-\ufa2d]{2,12}");
		return exParent(pattern, userName);
	}
	
	/**
	 * 检查登录名/用户名格式是否正确
	 * @author TGF
	 * @param logName  登录名/用户名(6~16位的数字、字母、或汉字至少包含2种组成)
	 */
	public static boolean checkCarLogName(String logName){
		Pattern pattern = Pattern.compile("(?!^(\\d+|[a-zA-Z]+|[\u4e00-\u9fa5]+)$)^[\\w\u4e00-\u9fa5]{6,16}$");
		return exParent(pattern, logName);
	}
	
	/**
	 * 检查密码格式是否正确
	 * @author TGF
	 * @param password 用户密码(由6~16位的数字和字母组成)
	 */
	public static boolean checkPassword(String password){
		Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
		return exParent(pattern, password);
	}
	
	/**
	 * 检查邀请码格式是否正确
	 * @author TGF
	 * @param inviteCode  邀请码(11位手机号，或者12位数字邀请码)
	 */
	public static boolean checkInviteCode(String inviteCode){
		Pattern pattern = Pattern.compile("^1\\d{10}$|^\\d{12}$");
		return exParent(pattern, inviteCode);
	}
	
	/**
	 * 检查真实姓名格式是否正确
	 * @author TGF
	 * @param trueName  真实姓名(2到12位中文字符)
	 */
	public static boolean checkTrueName(String trueName){
		Pattern pattern = Pattern.compile("^[\u4E00-\u9FA5\uf900-\ufa2d]{2,12}$");
		return exParent(pattern, trueName);
	}
	
	/**
	 * 检查金额格式是否正确
	 * @author TGF
	 * @param money  金额
	 */
	public static boolean checkMoney(String money){
		Pattern pattern = Pattern.compile("^[1-9]([0-9]{0,26})(\\.[0-9]{1,2})?$");
		return exParent(pattern, money);
	}
	
	/**
	 * 检查邮箱格式是否正确
	 * @author TGF
	 * @param mail  邮箱
	 */
	public static boolean checkMail(String mail){
		Pattern pattern = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return exParent(pattern, mail);
	}
	
	/**
	 * 检查车牌号格式是否正确
	 * @author TGF
	 * @param carNumber  车牌号(省（汉字）+市（A-Z）+组合（数字、字母)
	 */
	public static boolean checkCarNumber(String carNumber){
		Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$");
		return exParent(pattern, carNumber);
	}
	
	/**
	 * 检查地址格式是否正确
	 * @author TGF
	 * @param address  地址(1~25位的数字、字母或汉字)
	 */
	public static boolean checkAddress(String address){
		Pattern pattern = Pattern.compile("^[\u4E00-\u9FA5A-Za-z0-9]{1,25}$");
		return exParent(pattern, address);
	}
	
	/**
	 * 检查营业执照号格式是否正确
	 * @author TGF
	 * @param businessNum  营业执照号(15位数字)
	 */
	public static boolean checkBusinessNum(String businessNum){
		Pattern pattern = Pattern.compile("^[0-9]{15}$");
		return exParent(pattern, businessNum);
	}
	
	/**
	 * 检查银行卡号格式是否正确
	 * @author TGF
	 * @param bankNum  银行卡号(16-19位数字)
	 */
	public static boolean checkBankNum(String bankNum){
		Pattern pattern = Pattern.compile("^\\d{16,19}$");
		return exParent(pattern, bankNum);
	}
	
	/**
	 * 检查QQ号格式是否正确
	 * @author TGF
	 * @param qq  QQ号(5~12位数字)
	 */
	public static boolean checkQQ(String qq){
		Pattern pattern = Pattern.compile("^\\d{5,12}$");
		return exParent(pattern, qq);
	}
	
	/**
	 * 检查角色名称
	 * @author TGF
	 * @param roleName  角色名称(10字以下)
	 */
	public static boolean checkRoleName(String roleName){
		Pattern pattern = Pattern.compile("^[\u4E00-\u9FA5]{0,10}$");
		return exParent(pattern, roleName);
	}
	
	/**
	 * 检查角色描述
	 * @author TGF
	 * @param roleMark  角色描述(125字以下)
	 */
	public static boolean checkRoleMark(String roleMark){
		Pattern pattern = Pattern.compile("^[\u4E00-\u9FA5]{0,125}$");
		return exParent(pattern, roleMark);
	}
	
	
	private static boolean exParent(Pattern pattern,String source){
		Matcher matcher = pattern.matcher(source);
		return matcher.matches();
	}
	
	/**
	* 获取6位短信验证码
	* @author 李杰 
	* @Title: varCode 
	* @param @return 设定文件 
	* @return String 返回类型 		6位短信随机验证码
	* @date 2016-3-21 上午9:43:17
	* @throws
	 */
	public static String varCode(){
		return (Math.random()+"").substring(1, 7);
	}
	
}
