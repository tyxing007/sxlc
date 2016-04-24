<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
	<base href="<%=basePath%>">
	<title>银行卡管理</title>
	<!-- 公用meta -->
	<jsp:include page="../../common/top-meta.jsp"></jsp:include>
	<!-- 私用meta -->
	<!-- 公用css -->
	<jsp:include page="../../common/cm-css.jsp"></jsp:include>
	<!-- 私用css -->
</head>

<body class="nav-md">
	<div class="container body">
		<div class="container body">
		<div class="main_container">
		
			<div class="w-content ishow pic-add">
				<table>
					<tr>
						<td class="tt">专家姓名</td>
						<td class="con"><input type="text" class="" /></td>
					</tr>
					<tr>
						<td class="tt">职称</td>
						<td class="con"><input type="text" class="" /></td>
					</tr>
					<tr>
						<td class="tt">头像</td>
						<td class="con portrait-box">
							<!--dom结构部分-->
							<div id="uploader">
							    <!--用来存放item-->
							    <div class="" id="fileList"></div>
							    <div id="filePicker">选择头像</div>
							    <span class="rec-dimensions">建议尺寸：100*100</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="tt" valign="top">简介</td>
						<td class="con">
							<textarea rows="6" cols="" class="ta-noresize w500">测试</textarea>
						</td>
					</tr>
				</table>
			</div>
			
		</div>
	</div>
	<!-- 公用js -->
	<jsp:include page="../../common/cm-js.jsp"></jsp:include>
	
	<!-- 私用js -->
</body>

</html>