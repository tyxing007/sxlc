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
	<title>保荐机构管理-担保机构管理</title>
	<!-- 公用meta -->
	<jsp:include page="../common/top-meta.jsp"></jsp:include>
	<!-- 私用meta -->
	<!-- 公用css -->
	<jsp:include page="../common/cm-css.jsp"></jsp:include>
	<!-- 私用css -->
	<link rel="stylesheet" href="css/recommend/guarantee_manage.css"></link>
</head>

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<!-- 头部 -->
			<jsp:include page="../common/cm-top.jsp">
				<jsp:param value="9" name="top_menu_index"/>
			</jsp:include>
			
			<!-- 左侧菜单 -->
			<jsp:include page="../common/cm-recommend.jsp"></jsp:include>
			<!-- 主要内容 -->
			<div class="right_col" role="main">
				<!-- 地址导航 -->
				<jsp:include page="../common/cm-addr.jsp"></jsp:include>
				
				<div class="data_display">
					<div class="panel panel-success">
						<div class="panel-heading">
							<div class="action_item">
								<button class="obtn glyphicon glyphicon-plus" onclick="manageAdd()">代偿</button>
								<button class="obtn glyphicon glyphicon-pencil" onclick="manageMod()">导出</button>
							</div>
						</div>
						
						<div class="panel-body">
							<table id="table_id" class="display i-b2">
								<thead>
									<tr>
										<th class="table-checkbox"></th>
										<th>添加时间</th>
										<th>编号</th>
										<th>担保机构名称</th>
										<th>营业执照号</th>
										<th>组织机构代码</th>
										<th>税务登记号</th>
										<th>注册资本</th>
										<th>注册地址</th>
										<th>法人姓名</th>
										<th>法人身份证号</th>
										<th>法人手机号</th>
										<th>联系人姓名</th>
										<th>联系人手机号</th>
										<th>第三方支付账号</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<%
										for (int i = 0; i < 15; i++) {
									%>
									<tr>
										<td><input type="checkbox" /></td>
										<td>添加时间</td>
										<td>编号</td>
										<td>担保机构名称</td>
										<td>营业执照号</td>
										<td>组织机构代码</td>
										<td>税务登记号</td>
										<td>注册资本</td>
										<td>注册地址</td>
										<td>法人姓名</td>
										<td>法人身份证号</td>
										<td>法人手机号</td>
										<td>联系人姓名</td>
										<td>联系人手机号</td>
										<td>第三方支付账号</td>
										<td>有效</td>
										<td><a class="obtn" href="javascript:;" onclick="enable()">停用/启用</a></td>
									</tr>
									<%
										}
									%>
								</tbody>
							</table>
						</div>
						
					</div>
				</div>
			</div>
			<!-- 尾部 -->
			
		</div>
	</div>
	<!-- 公用js -->
	<jsp:include page="../common/cm-js.jsp"></jsp:include>
	
	<!-- 私用js -->
	<script type="text/javascript" src="js/recommend/guarantee-manage.js"></script>
	<script type="text/javascript">
		$('#table_id').DataTable({
			autoWidth : false,
			"aaSorting" : [ [ 1, "desc" ] ],//默认第几个排序
			"aoColumnDefs" : [
			//{"bVisible": false, "aTargets": [ 3 ]}, //控制列的隐藏显示
			{
				"orderable" : false,
				"aTargets" : [0,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]
			} // 制定列不参与排序
			],
		});
	</script>
</body>

</html>