<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
<head>
	<base href="<%=basePath%>">
    <title>注册</title>
    <jsp:include page="../common/top_meta.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="css/loginRegister/register.css">
</head>
<body>
    <jsp:include page="../common/top.jsp"></jsp:include>
   	<jsp:include page="../common/mainPageTop.jsp"></jsp:include>
    <!-- 在这里加入页面内容 -->
    <!--伍成然2016-3-27  -->
   	<div class="main">
   		<!--注册框  -->
   		<div class="register-box">
   			<div class="person-enterprise" >
	   			<div class="radio-select">
					<label class="radio-select1 on">
					<input class="radioclass1" type="radio" name="optionsRadios" value="0" checked="checked">
					   个人用户
					</label>
				</div>
				<div class="radio-select">
					<label class="radio-select2 ">
					<input class="radioclass2" type="radio" name="optionsRadios" value="1">
					    企业用户
					</label>
				</div>
				<div class="rlink">
					已有账户,
					<a href="login.html">立即登录</a>
				</div>	
			</div>
			
			<div class="content1 activeUp ctnt">
				<form id="testbox" method="post">
					<div class="input-group clearfix">
			    		<div class="inputUserName clearfix">
			    			<div class="left-title"><span>*</span>用户名:</div>
			    			<input type="text" class="input-user-name persolName" value="请输入用户名" datatype="logname" name="userName" maxlength="16"
							onFocus="if(value==defaultValue){value='';this.style.color='#000';}" 
							onBlur="if(!value){value=defaultValue;this.style.color='#bfbfbf';}" 
							style="color:#bfbfbf">
						</div>
						<div class="inputTelNum clearfix">
			    			<div class="left-title"><span>*</span>联系人手机号:</div>
			    			<input type="text" class="input-tel-num persolPhone" datatype="zPhone" name="personPhone" value="请输入联系人手机号" maxlength="11"
							onFocus="if(value==defaultValue){value='';this.style.color='#000';}" 
							onBlur="if(!value){value=defaultValue;this.style.color='#bfbfbf';}" 
							style="color:#bfbfbf">
						</div>
						<div class="inputPassword clearfix">
							<div class="left-title"><span>*</span>密码:</div>
			    			<input type="password" class="input-password" name="input-password" datatype="regpass" maxlength="16" >
			    			<span class="hint hint1">请设置您的帐号密码</span>
							<img class="eye-ico1 show" src="resource/img/loginRegister/mimabkj.png">
							<img class="eye-ico2" src="resource/img/loginRegister/mimakj.png">	
						</div>
						<div class="inputPassword2 clearfix">
							<div class="left-title"><span>*</span>确认密码:</div>
			    			<input type="password" class="input-password2" recheck="input-password" datatype="regpass" maxlength="16" >
			    			<span class="hint hint2">请确认您的帐号密码</span>
						</div>
						<div class="inputInviteCode clearfix">
							<div class="left-title">邀请码:</div>
			    			<input type="text" class="input-invite-code yaoqing" datatype="inviteCode" ignore="ignore" value="请输入邀请码" maxlength="12"
							onFocus="if(value==defaultValue){value='';this.style.color='#000';}" 
							onBlur="if(!value){value=defaultValue;this.style.color='#bfbfbf';}" 
							style="color:#bfbfbf">
						</div>
						<div class="inputImgCheck clearfix">
							<div class="left-title"><span>*</span>图形验证码:</div>
							<div class="registerCode">
			    				<input type="text" class="img-check" datatype="imgcode" value="请输入图形验证码"  maxlength="6" 
								onFocus="if(value==defaultValue){value='';this.style.color='#000';}" 
								onBlur="if(!value){value=defaultValue;this.style.color='#bfbfbf';}" 
								style="color:#bfbfbf">
								<img src="authImage.html" class="imgCode">
							</div>
						</div>
						<div class="inputTelCheck clearfix">
							<div class="left-title"><span>*</span>手机验证:</div>
							<div class="telCode">
								<input type="text" class="tel-check" datatype="Z6" value="请输入手机验证码" maxlength="6" 
								onFocus="if(value==defaultValue){value='';this.style.color='#000';}" 
								onBlur="if(!value){value=defaultValue;this.style.color='#bfbfbf';}" 
								style="color:#bfbfbf">
								<input type="button" class="get-tel-check" value="获取验证码">
							</div>
						</div>
						<div class="checkbox-link clearfix" >
							<div class="checkBox" id="checkRule1">
					        	<label>
					          		<input type="checkbox" class="check-box"><label class="XYdetail">我已阅读并同意使用条款和隐私条款</label>
					       		</label>
					       	</div>		     		      		
				      	</div>
				      	<div class="loginBtn"">
				      		<input type="button" class="register-btn ctntRegisterSub" value="立即注册">
				    	</div>
		    		</div>
		    	</form>
			</div>
				
			
			<div class="content2 ctnt">
				<form id="companyCheck">
					<div class="input-group clearfix">
			    		<div class="inputUserName clearfix">
			    			<div class="left-title"><span>*</span>用户名:</div>
			    			<input type="text" class="input-user-name persolName" value="请输入用户名" datatype="logname" maxlength="16"
							onFocus="if(value==defaultValue){value='';this.style.color='#000';}" 
							onBlur="if(!value){value=defaultValue;this.style.color='#bfbfbf';}" 
							style="color:#bfbfbf">
						</div>
						<div class="inputTelNum clearfix">
			    			<div class="left-title"><span>*</span>联系人手机号:</div>
			    			<input type="text" class="input-tel-num persolPhone" datatype="zPhone" value="请输入联系人手机号" maxlength="11"
							onFocus="if(value==defaultValue){value='';this.style.color='#000';}" 
							onBlur="if(!value){value=defaultValue;this.style.color='#bfbfbf';}" 
							style="color:#bfbfbf">
						</div>
						<div class="inputPassword clearfix">
							<div class="left-title"><span>*</span>密码:</div>
			    			<input type="password" class="input-password" id="input-password" name="input-password2" datatype="regpass" maxlength="16" >
			    			<span class="hint hint1">请设置您的帐号密码</span>
							<img class="eye-ico1 show" src="resource/img/loginRegister/mimabkj.png">
							<img class="eye-ico2" src="resource/img/loginRegister/mimakj.png">	
						</div>
						<div class="inputPassword2 clearfix">
							<div class="left-title"><span>*</span>确认密码:</div>
			    			<input type="password" class="input-password2" recheck="input-password2" datatype="*" maxlength="16" >
			    			<span class="hint hint2">请确认您的帐号密码</span>
						</div>
						<div class="inputInviteCode clearfix">
							<div class="left-title">邀请码:</div>
			    			<input type="text" class="input-invite-code yaoqing" datatype="inviteCode" ignore="ignore" value="请输入邀请码" maxlength="12"
							onFocus="if(value==defaultValue){value='';this.style.color='#000';}" 
							onBlur="if(!value){value=defaultValue;this.style.color='#bfbfbf';}" 
							style="color:#bfbfbf">
						</div>
						<div class="inputImgCheck clearfix">
							<div class="left-title"><span>*</span>图形验证码:</div>
							<div class="registerCode">
			    				<input type="text" class="img-check" datatype="imgcode" value="请输入图形验证码"  maxlength="6" 
								onFocus="if(value==defaultValue){value='';this.style.color='#000';}" 
								onBlur="if(!value){value=defaultValue;this.style.color='#bfbfbf';}" 
								style="color:#bfbfbf">
								<img src="authImage.html" class="imgCode">
							</div>
						</div>
						<div class="inputTelCheck clearfix">
							<div class="left-title"><span>*</span>手机验证:</div>
							<div class="telCode">
								<input type="text" class="tel-check" datatype="Z6" value="请输入手机验证码" maxlength="6" 
								onFocus="if(value==defaultValue){value='';this.style.color='#000';}" 
								onBlur="if(!value){value=defaultValue;this.style.color='#bfbfbf';}" 
								style="color:#bfbfbf">
								<input type="button" class="get-tel-check" value="获取验证码">
							</div>
						</div>
						<div class="checkbox-link clearfix">
							<div class="checkBox"  id="checkRule2">
					        	<label>
					          		<input type="checkbox" checked class="check-box"><label class="XYdetail">我已阅读并同意使用条款和隐私条款</label>
					       		</label>
					       	</div>		     		      		
				      	</div>
				      	<div class="loginBtn">
				      		<input type="button" class="register-btn companySubmit" value="立即注册">
				    	</div>
		    		</div>
		    	</form>	
			</div>
   		</div>
   		<div class="photo">
			<img src="resource/img/loginRegister/grzc1_01.png" class="image1">
			<img src="resource/img/loginRegister/grzc1_04.png" class=image2>
		</div>
		
		<div class="addXY" style="display:none">
			<h1>${agreement.agreementTitle }</h1>
			<div>
				${agreement.agreementDetail }
			</div>
		</div>
   	</div>
   	<jsp:include page="../common/bottom.jsp"></jsp:include>
   	<script type="text/javascript" src="js/common/valid.js"></script>
	<script type="text/javascript" src="js/loginRegister/register.js"></script>
	<script type="text/javascript" src="js/loginRegister/md5.js"></script>
</body>
</html>