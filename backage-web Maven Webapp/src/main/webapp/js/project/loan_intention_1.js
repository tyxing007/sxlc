$(function(){
	//表格初始化
	$('#table_id').DataTable(
			{	
				ajax: {  
					"url": appPath+"/project/getLoanPurposeData",   
					"dataSrc": "results", 
					"type": "POST",
					"data": function ( d ) {
						//加密
						var memberNo = encrypt.encrypt($("#memberNo").val());
						var logname = encrypt.encrypt($("#logname").val());
						var personalPhone = encrypt.encrypt($("#personalPhone").val());
						var repayWay = encrypt.encrypt($("#repayWay").val());
						d.memberNo = memberNo;
						d.logname = logname;
						d.personalPhone = personalPhone;
						d.repayWay = repayWay;
						
					}  
				},
				columns: [  
				          {title:'',sWidth:"3%", 
				        	  "mRender": function (data, type, full) {
				        		  sReturn = '<input type="checkbox" class="tr-checkbox" value="1" />';
				        		  return sReturn;
				        	  }
				          },
				          { title:"会员编号","data": "memberNo"},  
				          { title:"会员用户名","data": "logname"},  
				          { title:"会员名称","data": "personalName", 
				        	  "mRender": function (data, type, full) {
				        		  	if(data !=null && data !=''){
				        	    		return data;
				        	    	}else{
				        	    		return "";
				        	    	}  
				        	  }
				          },  
				          { title:"会员联系号码","data": "personalPhone", 
				        	  "mRender": function (data, type, full) {
				        		  if(data !=null && data !=''){
				        	    		return data;
				        	    	}else{
				        	    		return "";
				        	    	}  
				        	  }
				          },  
				          { title:"借款金额(元)","data": "amountStr"},  
				          { title:"借款期限","data": "deadline"},  
				          { title:"期限类型","data": "deadlineType", 
				        	  "mRender": function (data, type, full) {
				        		  if(data==0){
				        			  return "天标";
				        		  }else if(data==1){
				        			  return "月标";
				        		  }else if(data==2){
				        			  return "年标";
				        		  }else{
				        			  return "";
				        		  }
				        	  }
				          },  
				          { title:"还款方式","data": "repayWay", 
				        	  "mRender": function (data, type, full) {
				        		  	if(data == 0){
				        	    		return "等额本息";
				        	    	}else if(data == 1){
				        	    		return '每月还息，到期还本';
				        	    	}else if(data == 2){
				        	    		return "到期还息本";
				        	    	}else if(data == 3){
				        	    		return "等额本金";
				        	    	}else{
				        	    		return "";
				        	    	}  
				        	  }
				          },  
				          { title:"借款用途","data": "uses", 
				        	  "mRender": function (data, type, full) {
				        		  	if(data==null){
				        		  		return "";
				        	  		}else if(data.length>8){//当内容长度大于8时隐藏详细信息
				        	    		return '<a href="javascript:;" onclick="showText(this,1)" title="借款用途">'+data.substring(0,7)+'...</a>';
				        	    	}else {
				        	    		return data;
				        	    	} 
				        	  }
				          },  
				          { title:"还款来源","data": "repaySource", 
				        	  "mRender": function (data, type, full) {
				        		  	if(data==null){
				        		  		return "";
				        	  		}else if(data.length>8){//当内容长度大于8时隐藏详细信息
				        	    		return '<a href="javascript:;" onclick="showText(this,2)" title="还款来源">'+data.substring(0,7)+'...</a>';
				        	    	}else {
				        	    		return data;
				        	    	} 
				        	  }
				          },  
				          { title:"借款描述","data": "projectDescript", 
				        	  "mRender": function (data, type, full) {
				        		  	if(data==null){
				        		  		return "";
				        	  		}else if(data.length>8){//当内容长度大于8时隐藏详细信息
				        	    		return ' <a href="javascript:;" onclick="showText(this,3)" title="借款描述">'+data.substring(0,7)+'...</a>';
				        	    	}else {
				        	    		return data;
				        	    	} 
				        	  }
				          },  
				          { title:"提交意向时间","data": "recordDate"},  
				          { title:"有无分配理财顾问","data": "financial", 
				        	  "mRender": function (data, type, full) {
				        		  if(data>0){
				        			  return "有";
				        		  }else{
				        			  return "无";
				        		  }
				        	  }
				          },  
				          { title:"提交借款申请时间","data": "sendrecordDate"}  
				          ],
			  aaSorting : [ [ 5, "desc" ],[ 12, "desc" ],[ 14, "desc" ] ],//默认第几个排序
	          aoColumnDefs : [
	                          {
	                        	  "orderable" : false,
	                        	  "aTargets" : [ 0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 13 ]
	                          } // 制定列不参与排序
	                          ],
	          pagingType: "simple_numbers",//设置分页控件的模式  
	          processing: true, //打开数据加载时的等待效果  
	          serverSide: true,//打开后台分页  
	          searching: false,
	          scrollCollapse: true,
	          scrollX : "100%",
			  scrollXInner : "100%",scrollY:500,
			  scrollY : 500,
			  scrollYInner : 500,
	          rowCallback:function(row,data){//添加单击事件，改变行的样式      
	          },
	});//表格初始化完毕
	 
	//表格单选效果(有复选框)
	 $('#table_id tbody').on( 'click', 'tr', function () {
		    var $this = $(this);
		    var $checkBox = $this.find("input:checkbox");
	        if ( $this.hasClass('selected') ) {
	        	 $checkBox.prop("checked",false);
	        	$this.removeClass('selected');
	        } else {
	        	$(".tr-checkbox").prop("checked",false);
	        	$checkBox.prop("checked",true);
	        	$('#table_id tr.selected').removeClass('selected');
	        	$this.addClass('selected');
	        }
	  });
	
	 /**
	  * 查询按钮
	  */
	 $(".glyphicon-search").on("click",function(){
		$('#table_id').DataTable().ajax.reload();
		
	 });
	 
	 
	//默认禁用搜索和排序
	/* $.extend( $.fn.dataTable.defaults, {
	    searching: true,
	    ordering:  false
	} ); */
	
	
	/*验证*/
	validform5("layui-layer-btn0","saveNotice",true,"5");
	//查看审批记录
	$('#loan_exam_record').on('click', function(){
		var data = $('#table_id').DataTable().rows('.selected').data();
		if(data.length<1){
				layer.alert("请选择要查看的项目！",{icon:0});
				return;
		}
		var applyid = data[0].applyid;
	    layer.open({
	        type: 2,
	        title: '查看审批记录',
	        maxmin: true,
	        shadeClose: true, //点击遮罩关闭层
	        area : ['800px' , '520px'],
	        content: appPath+'/project/toCheckRecordPg?content='+applyid
	    });
	});
	//拒绝借款
	$('#refuse_payment').on('click', function(){
		var data = $('#table_id').DataTable().rows('.selected').data();
		if(data.length<1){
				layer.alert("请选择要拒绝借款的会员！",{icon:0});
				return;
		}
		if(data[0].dealStatu==1){
			layer.alert("该意向借款已提交申请！",{icon:0});
			return;
		}
		var params={};
		var id = data[0].id;
		params.id=encrypt.encrypt(id+"");
		
		layer.confirm('确定拒绝借款？', {
			btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax( {  
					url:appPath+"/project/refuseBorrow",
					data:params,
					type:'post',  
					cache:false,  
					dataType:'json',  
					success:function(data) { 
						if(data==1){
							layer.alert("操作成功",{icon:1});
						}else if(data==0){
							layer.alert("操作失败",{icon:2});  
						}
					},  
					error : function() {  
						layer.alert("服务器异常",{icon:2});  
					}  
				});
			  
			});
	});
	//借款会员拉黑
	$("#loan_member_black").on("click",function(){
		var data = $('#table_id').DataTable().rows('.selected').data();
		if(data.length<1){
				layer.alert("请选择要拉黑的会员！",{icon:0});
				return;
		}
		var memberID = data[0].memberID;
        layer.prompt({title: '填写拉黑原因', formType: 2}, function(text){
        	$.ajax( {  
				url:appPath+"/project/blockMember",
				data:{"memberId":encrypt.encrypt(memberID),"remark":encrypt.encrypt(text)},
				type:'post',  
				cache:false,  
				dataType:'json',  
				success:function(data) { 
					if(data==0){
						layer.alert("操作成功",{icon:1});
					}else if(data==-1){
						layer.alert("会员不存在",{icon:2});  
					}else if(data==-2){
						layer.alert("会员已被拉黑",{icon:2});  
					}
				},  
				error : function() {  
					layer.alert("服务器异常",{icon:2});  
				}  
			});
        	
        });	      
	});
});
/******补充资料*******/
function addInfo(){
	var data = $('#table_id').DataTable().rows('.selected').data();
	if(data.length<1){
			layer.alert("请选择要操作的数据！",{icon:0});
			return;
	}
	if(data[0].dealStatu==1){
		layer.alert("该意向借款已提交申请！",{icon:0});
		return;
	}
	var id = data[0].id;
	var applyid = data[0].applyid;
//	$(".right_col").load("project/toAddInformationPg",{"id":encrypt.encrypt(id+""),"applyid":encrypt.encrypt(applyid+"")});
	window.location.href=appPath+"/project/toAddInformationPg?content="+id+"&start="+applyid;
}
/******查看借款项目详情*******/
function view_detail(){
	var data = $('#table_id').DataTable().rows('.selected').data();
	if(data.length<1){
			layer.alert("请选择项目！",{icon:0});
			return;
	}
	var applyId = data[0].applyid;//Project_App_Record 表Apply_Id
	window.location.href=appPath+"/project/toProjectdetailPg?content="+applyId;
}
/******分配理财顾问*******/
function allocation(){
		var data = $('#table_id').DataTable().rows('.selected').data();
		if(data.length<1){
				layer.alert("请选择要操作的数据！",{icon:0});
				return;
		}
		if(data[0].financial>0){//已有理财顾问
			layer.alert("已分配理财顾问！",{icon:0});
			return;
		}
		var memberID = data[0].memberID;
//		$(".right_col").load("project/toDistributionPg",{"memberID":encrypt.encrypt(memberID),"Logname": encrypt.encrypt(Logname),"PersonalName":encrypt.encrypt(PersonalName)});
		window.location.href=appPath+"/project/toDistributionPg?content="+memberID;
}


/**
 * 简介弹出框显示
 */
function showText(btn,type){
	var data = $('#table_id').DataTable().row($(btn).parents('tr')).data();
	var title = $(btn).attr("title");
	var content="";
	if(type==1){
		content = data.uses;
	}else if(type==2){
		content = data.repaySource;
	}else if(type==3){
		content = data.projectDescript;
	}
	layer.open({
	    type: 1,
	    area: ['400px', '300px'], //高宽
	    title: title,
	    content: content,//DOM或内容
	    btn:['关闭']
		  ,cancel: function(index){
		  	//取消的回调
		  }
	});
};