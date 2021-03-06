<%@page import="product_p2p.kit.datatrans.IntegerAndString"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	long memberId =IntegerAndString.StringToLong(request.getParameter("content"), 0);
%>
<!DOCTYPE html>


<div class="nav-md">
	<div class="container body">
		<div class="main_container">
			<!-- 主要内容 -->
				<!-- 地址导航 -->
			<jsp:include page="../../common/cm-addr.jsp"></jsp:include>
			<div class="nav-tabs-con active">
				<div class="recommenber">
					<span><samp>原始推荐人：</samp><label id="realName"></label></span>
					<span><samp>编号：</samp><label id="memberNo"></label></span>
					<span><samp>手机号：</samp><label id="phone"></label></span>
					<div class="buttonSet2">
						<button class="obtn obtn-query">确定</button>
						<button class="obtn obtn-query">取消</button>
					</div>
				</div>
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
								<span class="con-item"><span>会员编号</span><input type="text" class="notspecial" id="memberNoT"></span>
								<span class="con-item"><span>会员姓名</span><input type="text" class="notspecial"  id="realNameT"></span>
								<span class="con-item"><span>手机号</span><input type="text" class="notspecial"  id="phoneT"></span>
								<button class="obtn obtn-query glyphicon glyphicon-search">查询</button>
							</form>
					  	</div>
					 </div>
				</div> 
				<div class="data_display">
						
						<div class="panel-body">
							<table id="table_id" class="display">
								<thead>
								</thead>
								<tbody>
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
	<script type="text/javascript" src="js/member/MyInVite.js"></script>
		<script type="text/javascript">
		var memberId = <%=memberId %>;
		var encrypt = new JSEncrypt();
		encrypt.setPublicKey(publicKey_common);
		//result 为加密后参数
		memberId = encrypt.encrypt(memberId+"");
		showMemberInviteMan(memberId);//当下邀請人
		showExpectInviteList(memberId);//期望的邀请人列表
	</script>
</div>

