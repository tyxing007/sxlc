<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
    <title>实名认证</title>
    <jsp:include page="../../common/top_meta.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="css/account/account.css">
	<link rel="stylesheet" type="text/css" href="css/account/personalCenter/securityCenter.css">
</head>
<body> 
    <jsp:include page="../../common/top.jsp"></jsp:include>
   	<jsp:include page="../../common/mainPageTop.jsp"></jsp:include>
   	<div class="main">
   		<div class="clearfix">
		   	<jsp:include page="../../account/accountCommonLeft.jsp"></jsp:include>
   			<div class="accountRight">
			   	<jsp:include page="../../account/accountCommonRightTop.jsp"></jsp:include>
   				<div class="accountMain clearfix">
   				<!-- 在此处写入代码 -->
   					<div class="authenHeader">
	   					<span>实名认证</span>
	   					<a class="back" href="javascript:history.back();">返回</a>
	   				</div>
   					<div class="authenContent">
	   					<div class="authen">
	   						<p class="toolTips">请填写您本人真实有效的身份信息，一旦认证成功，信息将不可更改。</p>
	   						<form id="securityCheck">
		   						<ul class="inputArea">
				   					<li class="inputList">
				   						<div class="mustLabel"><span><samp>*</samp>姓名：</span></div>
				   						<input id="inputRN" class="inputText inputRealName" type="text" datatype="enterperson" lang="请输入姓名" maxlength="20"/>
				   					</li>
				   					<li class="inputList">
				   						<div class="mustLabel"><span><samp>*</samp>身份证号：</span></div>
				   						<input id="inputID" class="inputText personId" type="text" lang="请输入您的身份证号" datatype="IDCard" maxlength="18"/>
				   					</li>
				   					<!-- <li class="inputList">
				   						<div class="mustLabel"><span><samp>*</samp>附件：</span></div>
				   						<div class="previewPicture">图片预览部分</div>
				   						<div class="previewPicture">
				   							<img class="previewImg" id="previewImg" src="resource/img/account/common/opacity.png">
				   							<input type="file"accept=".png,.jpg" class="previewInput" id="previewInput" onchange="preImg(this.id,'previewImg')" onblur="if(document.getElementById('previewImg').src=='null')document.getElementById('previewImg').src='resource/img/account/common/opacity.png'" >
				   						</div>
				   						<span class="hint">（请上传附件图片）</span>
				   					</li>	 -->
		   					        <li class="contetnList">
		   								<div id="submitRN" class="btn emailBtn nameBtn phoneSubmit">确定</div>
		   							</li>
				   				</ul>
	   						</form>
	   					</div>
	   				</div>
   				</div>
   			</div>
   		</div>
   	</div>
   	<jsp:include page="../../common/bottom.jsp"></jsp:include>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugs/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/account/account.js"></script>
	<script type="text/javascript" src="js/account/personalCenter/securityCenter.js"></script>
	<script type="text/javascript">
		/* 验证     */
			$(function(){
				$("#securityCheck").Validform({
					tiptype:3,//提示信息类型
					btnSubmit:".phoneSubmit", //#btn_sub是该表单下要绑定点击提交表单事件的按钮;如果form内含有submit按钮该参数可省略;
					//btnReset:"#btnreset1",
					datatype:extdatatype,//扩展验证类型
					//showAllError:true,//提交前验证显示所有错误
					ajaxPost:{//使用ajax提交时
						url:"http://182.150.178.88:8031/GEB_P2P_Foreqround/selectmemberProvince.action",
						datatype:"jsonp",
						success:function(data,obj){
				            //data是返回的json数据;
				            //obj是当前表单的jquery对象;
				        },
				        error:function(data,obj){
				            //data是{ status:**, statusText:**, readyState:**, responseText:** };
				            //obj是当前表单的jquery对象;
				            console.log(data.status);
				        }
					}
				});
			});
	</script>
</body>
</html>