<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="../../common/cm-addr.jsp"></jsp:include>
<div class="" id="checkDetail">
	<div class="container add_type_contianer">
	<div class="container body addContainer">
		<div class="main_container">
			<div class="w-content ishow pic-add">
			<div class="infoDisplay">
			<fieldset class="personAuthentication"><!-- 个人认证 -->
			<legend>项目基本信息</legend>
			<table>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">借款项目名称：</label></td>
					<td class="con" id="inputMoney">借款项目名称</td>
				</tr>	
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">借款金额：</label></td>
					<td class="con" id="inputMoney">借款金额</td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">还款方式：</label></td>
					<td class="con" id="inputMoney">还款方式</td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">还款保障：</label></td>
					<td class="con" id="inputMoney">还款保障</td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">借款用途：</label></td>
					<td class="con" id="inputMoney">借款用途</td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">还款来源：</label></td>
					<td class="con" id="inputMoney">还款来源</td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">借款期限：</label></td>
					<td class="con" id="inputMoney">借款期限</td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">最大投资比例：</label></td>
					<td class="con" id="inputMoney">设置最大投资比例</td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">年化利率：</label></td>
					<td class="con" id="inputMoney">年化利率</td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">起投金额：</label></td>
					<td class="con" id="inputMoney">起投金额</td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">加价幅度：</label></td>
					<td class="con" id="inputMoney">加价幅度</td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">投资上限：</label></td>
					<td class="con" id="inputMoney">投资上限</td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">项目描述：</label></td>
					<td class="con" id="inputMoney">项目描述</td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">借款人信息：</label></td>
					<td class="con" id="inputMoney">借款人信息</td>
				</tr>
				<tr class="col-md-7 col-md-offset-5">	
					<td class="tt">
						<button class="btn btn-info btnMod">修改</button>
					</td>
				</tr>
			</table>
			</fieldset>
			<div>
			</div>			
			</div>
			<div class="infoMod" style="display:none">
			<fieldset class="personAuthentication"><!-- 个人认证 -->
			<legend>项目基本信息修改</legend>
			<table>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">借款项目名称：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>	
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">借款金额：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">还款方式：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">还款保障：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">借款用途：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">还款来源：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">借款期限：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">最大投资比例：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">年化利率：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">起投金额：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">加价幅度：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">投资上限：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">项目描述：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>
				<tr class="col-md-6">
					<td class="tt"><label style="width:107px;">借款人信息：</label></td>
					<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
				</tr>
				<tr class="col-md-7 col-md-offset-5">
					<td class="tt">
						<button class="btn btn-success btnPreserve">保存</button>
						<button class="btn btn-default btnCancel">取消</button>
					</td>
				</tr>
			</table>
			</fieldset>
			</div>
			<fieldset class="person" style="display:block"><!-- 个人 -->
				<legend>会员基本信息</legend>
				<div class="w-content ishow">
					<table>
						<tr>
							<td class="tt"><label>会员编号：</label><span>会员编号</span></td>
							<td class="tt"><label>会员用户名：</label><span>会员用户名</span></td>
							<td class="tt"><label>真实姓名：</label><span>真实姓名</span></td>
						</tr>
						<tr>
							<td class="tt"><label>身份证号：</label><span>20221552255412554411</span></td>
							<td class="tt"><label>手机号码：</label><span>18454845847</span></td>
							<td class="tt"><label>最高学历：</label><span>本科</span></td>
						</tr>
						<tr>
							<td class="tt"><label>婚姻状况：</label><span>已婚</span></td>
							<td class="tt"><label>地区：</label><span>北京</span></td>
							<td class="tt"><label>月均收入：</label><span>10000元</span></td>
							
						</tr>
						<tr>
							<td class="tt"><label>注册时间：</label><span>2016-4-20</span></td>
						</tr>
					</table>
				</div>
			</fieldset>
			<fieldset class="enterprise" style="display:none"><!-- 企业 -->
				<legend>会员基本信息</legend>
				<div class="w-content ishow">
					<table>
						<tr>
							<td class="tt"><label>会员编号：</label><span>会员编号</span></td>
							<td class="tt"><label>会员用户名：</label><span>会员用户名</span></td>
							<td class="tt"><label>企业名称：</label><span>企业名称</span></td>
						</tr>
						<tr>
							<td class="tt"><label>营业执照号：</label><span>20221552255412554411</span></td>
							<td class="tt"><label>联系人：</label><span>哈哈</span></td>
							<td class="tt"><label>联系人手机号：</label><span>18454845847</span></td>
						</tr>
						<tr>
							<td class="tt"><label>地区：</label><span>北极</span></td>
							<td class="tt"><label>注册时间：</label><span>2016-04-27</span></td>
						</tr>
					</table>
				</div>
			</fieldset>
			<fieldset>
				<legend>借款信用统计</legend>
				<div class="w-content ishow">
					<table>
						<tr>
							<td class="tt"><label>成功借款：</label><span>N</span>笔</td>
							<td class="tt"><label>成功还款：</label><span>N</span>笔</td>
							<td class="tt"><label>成功投资：</label><span>N</span>笔</td>
						</tr>
						<tr>
							<td class="tt"><label>逾期还款：</label><span>N</span>笔</td>
							<td class="tt"><label>严重逾期还款：</label><span>N</span>笔</td>
							<td class="tt"><label>逾期未还款：</label><span>N</span>笔</td>
						</tr>
						<tr>
							<td class="tt"><label>提前还款：</label><span>N</span>笔</td>
						</tr>
					</table>
				</div>
			</fieldset>
			<fieldset>
				<legend>投资统计信息</legend>
				<div class="w-content ishow">
					<table>
						<tr>
							<td class="tt"><label>成功投资笔数：</label><span>N</span>笔</td>
							<td class="tt"><label>成功投资金额：</label><span>N</span>笔</td>
							<td class="tt"><label>累计已收本金：</label><span>N</span>笔</td>
						</tr>
						<tr>
							<td class="tt"><label>累计已收收益：</label><span>N</span>笔</td>
							<td class="tt"><label>待收本金：</label><span>N</span>笔</td>
							<td class="tt"><label>待收收益：</label><span>N</span>笔</td>
						</tr>
					</table>
				</div>
			</fieldset>
			<fieldset class="personAuthentication"><!-- 个人认证 -->
				<legend>借款会员认证信息</legend>
				<div class="w-content ishow">
					<table>
						<tr>
							<td class="tt"><a>实名认证</a></td>
							<td class="tt"><a>手机认证</a></td>
							<td class="tt"><a>征信认证</a></td>
							<td class="tt"><a>住址认证</a></td>
							<td class="tt"><a>婚姻认证</a></td>
							<td class="tt"><a>工作认证</a></td>
							<td class="tt"><a>学历认证</a></td>
							<td class="tt"><a>股权认证</a></td>
						</tr>
						<tr>
							<td class="tt"><a>职称认证</a></td>
							<td class="tt"><a>社保认证</a></td>
							<td class="tt"><a>房产认证</a></td>
							<td class="tt"><a>车产认证</a></td>
							<td class="tt"><a>银行流水认证</a></td>
							<td class="tt"><a>其它</a></td>
						</tr>
					</table>
				</div>
			</fieldset>
			<fieldset class="enterpriseAuthentication" style="display:none"><!-- 企业认证 -->
				<legend>借款会员认证信息</legend>
				<div class="w-content ishow">
					<table>
						<tr>
							<td class="ts"><a>营业执照认证</a></td>
							<td class="ts"><a>工商执照认证</a></td>
							<td class="ts"><a>组织机构代码认证</a></td>
							<td class="ts"><a>开户许可证认证</a></td>
							<td class="ts"><a>企业银行流水认证</a></td>
							<td class="ts"><a>实地考察认证</a></td>
							<td class="ts"><a>税务登记认证</a></td>
							<td class="ts"><a>批文认证认证</a></td>
						</tr>
						<tr>
							<td class="ts"><a>财务资料认证</a></td>
							<td class="ts"><a>监管单位认证</a></td>
							<td class="ts"><a>房产认证</a></td>
							<td class="ts"><a>车产认证</a></td>
							<td class="ts"><a>担保考察认证</a></td>
							<td class="ts"><a>法人身份认证</a></td>
							<td class="ts"><a>其它</a></td>
						</tr>
					</table>
				</div>
			</fieldset>
			<fieldset>
				<legend>历史审核记录</legend>
				<table id="table_history_record" class="display">
					<thead>
						<tr>
							<th></th>
			                <th>审核管理员名称</th>
			                <th>审核点</th>
			                <th>审核时间</th>
			                <th>审核状态</th>
			                <th>审核意见</th>			                
			            </tr>
			        </thead>
			        <tbody>
			        <%for (int i = 0; i < 15; i++) {%>
			        	<tr>
			        		<td><input type="checkbox"></td>
			        		<td>管理员名称</td>
			                <td>审核点</td>
			                <td>2016-04-26</td>
			                <td>同意</td>
			                <td>意见</td>
			            </tr>
					<%}%>
			       	</tbody>
			    </table>
			    </fieldset>
			    <fieldset>
				<legend>历史审核附件</legend>
				<table id="table_history_enclosure" class="display">
					<thead>
						<tr>
							<th></th>
			                <th>附件名称</th>
			                <th>上传审核点</th>
			                <th>上传审核管理员名称</th>
			                <th>上传时间</th>
			                <th>备注</th>
			                <th>操作</th>			                
			            </tr>
			        </thead>
			        <tbody>
			        <%for (int i = 0; i < 15; i++) {%>
			        	<tr>
			        		<td><input type="checkbox"></td>
			        		<td>附件名称</td>
			                <td>上传审核点</td>
			                <td>管理员名称</td>
			                <td>2016-04-26</td>
			                <td>备注</td>
			                <td>
			                	<a href="javascript:;" class="btn-delete" onclick="del()">删除</a>
			                	<a href="javascript:;" class="btn-delete" onclick="down()">下载</a>
							</td>
			            </tr>
					<%}%>
			       	</tbody>
			    </table>
			    </fieldset>
			    <fieldset>
				<legend>附件删除记录</legend>
				<table id="table_history_del" class="display">
					<thead>
						<tr>
							<th></th>
			                <th>附件名称</th>
			                <th>上传审核点</th>
			                <th>操作删除管理员名称</th>
			                <th>删除时间</th>
			                <th>备注</th>
			            </tr>
			        </thead>
			        <tbody>
			        <%for (int i = 0; i < 15; i++) {%>
			        	<tr>
			        		<td><input type="checkbox"></td>
			        		<td>附件名称</td>
			                <td>上传审核点</td>
			                <td>管理员名称</td>
			                <td>2016-04-26</td>
			                <td>备注</td>
			            </tr>
					<%}%>
			       	</tbody>
			    </table>
			    </fieldset>
				<fieldset class="personAuthentication"><!-- 个人认证 -->
				<legend>上传项目审核附件信息</legend>
				<table>
					<tr class="col-md-6">
						<td class="tt"><label style="width:107px;">附件名称：</label></td>
						<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
					</tr>	
					<tr class="col-md-6">
						<td class="tt"><label style="width:107px;">附件备注：</label></td>
						<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
					</tr>
					<tr class="col-md-6">
						<td class="tt"><label style="width:107px;">上传附件：</label></td>
						<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
					</tr>
					<tr class="col-md-6">
						<td class="tt"><label style="width:107px;">选择审核结果：</label></td>
						<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
					</tr>
					<tr class="col-md-6">
						<td class="tt"><label style="width:107px;">填写审核意见：</label></td>
						<td class="con" id="inputMoney"><input type="text" class="" datatype="amcountM" /></td>
					</tr>
					<tr class="col-md-7 col-md-offset-5">
						<td class="tt">
							<button class="btn btn-success btnPreserve">提交</button>
							<button class="btn btn-default btnCancel">重置</button>
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
		</div>
	</div>
	</div>
	</div>
    <!-- 公用js -->
	<jsp:include page="../../common/cm-js.jsp"></jsp:include>
	<!-- 私用js -->
	<script type="text/javascript">
	$('#table_history_record').DataTable({
		scrollX:true,
		autoWidth : false,
		"aaSorting" : [ ],//默认第几个排序
		"aoColumnDefs" : [
		//{"bVisible": false, "aTargets": [ 3 ]}, //控制列的隐藏显示
		{
			"orderable" : false,
			"aTargets" : [0,1,2,3,4,5]
		} // 制定列不参与排序
		],
	});
	$('#table_history_enclosure').DataTable({
		scrollX:true,
		autoWidth : false,
		"aaSorting" : [ ],//默认第几个排序
		"aoColumnDefs" : [
		//{"bVisible": false, "aTargets": [ 3 ]}, //控制列的隐藏显示
		{
			"orderable" : false,
			"aTargets" : [0,1,2,3,4,5,6]
		} // 制定列不参与排序
		],
	});
	$('#table_history_del').DataTable({
		scrollX:true,
		autoWidth : false,
		"aaSorting" : [ ],//默认第几个排序
		"aoColumnDefs" : [
		//{"bVisible": false, "aTargets": [ 3 ]}, //控制列的隐藏显示
		{
			"orderable" : false,
			"aTargets" : [0,1,2,3,4,5]
		} // 制定列不参与排序
		],
	});
	/*********修改切换*********/
	$(function(){
		$(".btnMod").click(function(){
			$(".infoMod").show();
			$(".infoDisplay").hide();
		});
		$(".btnPreserve").click(function(){
			$(".infoDisplay").show();
			$(".infoMod").hide();
		});
		$(".btnCancel").click(function(){
			$(".infoDisplay").show();
			$(".infoMod").hide();
		});
	});
	</script>
