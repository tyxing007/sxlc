<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">



<div class="nav-md">
	<div class="container body">
		<div class="main_container">
			<!-- 主要内容 -->
				<!-- 地址导航 -->
			<jsp:include page="../../common/cm-addr.jsp"></jsp:include>
			<div class="nav-tabs-con active">
				<div class="search">
					<div class="panel panel-success">
						<div class="panel-heading">
							<div class="i-fl search_title">条件查询</div>
							<div class="i-fr action_item">
								<ul class="list_item list-inline">
									<li><a class="state">展开&nbsp;<span class="glyphicon glyphicon-chevron-down"></span> </a></li>
								</ul>
							</div>
						</div>
						<div class="panel-body">
							<form id="" class="" action="">
								<span class="con-item"><span>项目编号</span><input type="text" class="notspecial" ></span>
								<span class="con-item"><span>项目名称</span><input type="text" class="notspecial" ></span>
								<span class="con-item"><span>投资时间</span><input type="text" id="startDate" class="dateInput Wdate" onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')||\'2020-10-01\'}' })" ><span class="line"></span><input type="text" id="endDate" class="dateInput Wdate"  onFocus="WdatePicker({minDate: '#F{$dp.$D(\'startDate\')}' ,maxDate:'2020-10-01' })" ></span>
								<span class="con-item"><span>放款时间</span><input type="text" id="startDate1" class="dateInput Wdate" onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate1\')||\'2020-10-01\'}' })" ><span class="line"></span><input type="text" id="endDate1" class="dateInput Wdate"  onFocus="WdatePicker({minDate: '#F{$dp.$D(\'startDate1\')}' ,maxDate:'2020-10-01' })" ></span>
								<button class="obtn obtn-query glyphicon glyphicon-search">查询</button>
							</form>
					  	</div>
					 </div>
				</div> 
				<div class="data_display">
						
						<div class="panel-body">
							<table id="table_id" class="display">
								<thead>
									<tr>
										<th class="table-checkbox"></th>
										<th>项目编号</th>
										<th>项目名称</th>
										<th>投资时间</th>
										<th>投资总金额</th>
										<th>使用红包总金额</th>
										<th>使用代金券总额</th>
										<th>放款时间</th>
										<th>投资总进度</th>
									</tr>
								</thead>
								<tbody>
									<%
										for (int i = 0; i < 15; i++) {
									%>
									<tr>
										<td><input type="checkbox" /></td>
										<td>1</td>
										<td>项目名称</td>
										<td>投资时间</td>
										<td class="moneyFormat">1200</td>
										<td class="moneyFormat">1200</td>
										<td class="moneyFormat">1200</td>
										<td>放款时间</td>
										<td>投资总进度</td>
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
		</div>
	
		<!-- 公用js -->
	<jsp:include page="../../common/cm-js.jsp"></jsp:include>
	
	<!-- 私用js -->
	<script type="text/javascript" src="js/member/member.js"></script>
	<script type="text/javascript">
		$(function(){
	$('#table_id').DataTable({
		"scrollX":true,
		//"scrollY":true,
		"aaSorting" : [ [ 3,4,5,6,7, "desc" ] ],//默认第几个排序
		"aoColumnDefs" : [
		//{"bVisible": false, "aTargets": [ 3 ]}, //控制列的隐藏显示
		{
			"orderable" : false,
			"aTargets" : [0,1,2,8]
		} // 制定列不参与排序
		],
	});
});
	</script>
</div>
