//加密设置
var encrypt = new JSEncrypt();
encrypt.setPublicKey(publicKey_common);

$(function(){
//			validform5("layui-layer-btn0","addpassword",false,"3");
//			validform5("layui-layer-btn0","modpassword",false,"3");
//			validform5("layui-layer-btn0","server",false,"3");
//			validform5("layui-layer-btn0","port",false,"3");
//			validform5("layui-layer-btn0","emailinterface",false,"3");
//			validform5("layui-layer-btn0","emailpassword",false,"3");
			//表格初始化
			$('#table_id').DataTable(
					{	
						ajax: {  
							"url": appPath+"/config/getMsgSetData",   
							"dataSrc": "results", 
							"type": "POST",
							"data": function ( d ) {  
							}  
						},
						columns: [  
						          { title:"消息类型id","data": "typeID"},  
						          { title:"短信类型","data": "typeName"},  
						          { title:"短信内容","data": "msgDetail"},  
						          { title:"状态","data": "statu", 
						        	  "mRender": function (data, type, full) {
						        		  if(data==0){
						        			  return "<font color='red'>已禁用</font>";
						        		  }else if(data==1){
						        			  return "已启用";
						        		  }
						        	  }
						          },  
						          { title:"操作","data": "", 
						        	  "mRender": function (data, type, full) {
						        		  if(full.statu==0){
						        			  return "<a onclick=\"enableOrDisable(1,'"+full.typeID+"');\" href=\"javascript:void(0);\">启用</a>" ;
						        		  }else if(full.statu==1){
						        			  return "<a onclick=\"enableOrDisable(0,'"+full.typeID+"');\" href=\"javascript:void(0);\">停用</a>";
						        		  }
						        	  }
						          }
						          ],
			          aoColumnDefs : [
			                          {"bVisible": false, "aTargets": [0]}, //控制列的隐藏显示
			                          {
			                        	  "orderable" : false,
			                        	  "aTargets" : [1,2,3,4]
			                          } // 制定列不参与排序
			                          ],
			          pagingType: "simple_numbers",//设置分页控件的模式  
			          processing: true, //打开数据加载时的等待效果  
			          serverSide: true,//打开后台分页  
			//          info:false,
			          rowCallback:function(row,data){//添加单击事件，改变行的样式      
			          }
			 
			});//表格初始化完毕
			 
			//表格单选效果
			 $('#table_id tbody').on( 'click', 'tr', function () {
				    var $this = $(this);
			        if ( $this.hasClass('selected') ) {
			        	$this.removeClass('selected');
			        }
			        else {
			        	$('#table_id tr.selected').removeClass('selected');
			        	$this.addClass('selected');
			        }
			  });
			
});

/**
 * 增加/修改操作
 * @param type 1：增加 2：修改
 * @returns
 */
function addOrUpdate(type){
	//清空数据
	document.getElementById("dataForm").reset();
	$(".msginterfaceselect").val("");
	$(".msgtextarea").html("");
	//操作
	var title="";
	if(type==1){
		title="添加";
	}else if(type==2){
		title="修改";
		var data = $('#table_id').DataTable().rows('.selected').data();
		if(data.length<1){
			layer.alert("请选择要修改的数据！",{icon:0});
			return;
		}
		$(".msginterfaceselect").val(data[0].typeID);
		$(".msgtextarea").val(data[0].msgDetail);
	}
	//打开弹出窗口
	layer.open({
	    type: 1,
	    area: ['480px', '280px'], //高宽
	    title: title,
	    maxmin: true,
	    content: $("#msgadd"),//DOM或内容
	    btn:['确定', '取消']
		  ,yes: function(index, layero){ //或者使用btn1
			    //加密参数
				var data={};
				var typeID = encrypt.encrypt($(".msginterfaceselect").val());
				data.typeID=typeID;
				var messageMsgDetail = encrypt.encrypt($(".msgtextarea").val());
				data.messageMsgDetail=messageMsgDetail;
				
				NetUtil.ajax(
					appPath+"/config/addOrUpdateMsgContent",
					data,
					function(data) { 
						if(data > 0){
							layer.alert("操作成功",{icon:1});
							$(".layui-layer-btn1").click();
							 var table = $('#table_id').DataTable();
							 table.ajax.reload();
						}else if(data==0){
							layer.alert("操作失败",{icon:2});  
						}
					} 
				);
			  
		  },cancel: function(index){//或者使用btn2（concel）
		  	//取消的回调
		  }
	});
}


/**
 * 启用/停用功能
 * @param type 1：启用 0：停用
 * @param id 
 * @returns
 */
function enableOrDisable(type,id){
	var title="";
	if(type==1){
		title ='确定启用？';
	}else if(type==0){
		title ='确定停用？';
	}
	layer.confirm(title, {
		btn: ['确定', '取消']
	}, function(index, layero){
		NetUtil.ajax(
				  appPath+"/front/addOrUpdateMsgInterface.do",
				  {"statu":encrypt.encrypt(""+type),"typeID":encrypt.encrypt(id)},
				  function(data) { 
						if(data==1){
							layer.alert("操作成功",{icon:1});
							layer.close(index);
							var table = $('#table_id').DataTable();
							table.ajax.reload();
						}else if(data==0){
							layer.alert("操作失败",{icon:2});  
						}
					}
		 );
	}, function(index){
		//取消回调
	}); 
}

	
	/* 下拉框内字体颜色 */
	$(".msginterfaceselect").css("color","#aaa");
	$(".msginterfaceselect").change(function(){
		if($(this).val()!="请选择"){
			$(this).css("color","#000");
		}else{
			$(this).css("color","#aaa");
		}
	});
	/* 添加短信接口地址部分开始 */
	function msgAdd(){
		layer.open({
		    type: 1,
		    area: ['480px', '280px'], //高宽
		    title: "添加",
		    maxmin: true,
		    content: $("#msgadd"),//DOM或内容
		    btn:['确定', '取消']
			  ,yes: function(index, layero){ //或者使用btn1
			    //确定的回调
			  	//判断执行不同方法
			  	
			  },cancel: function(index){//或者使用btn2（concel）
			  	//取消的回调
			  }
		});
	}
	/* 添加短信接口地址部分结束 */
	/* 修改短信接口地址部分开始 */
	function msgMod(){
		layer.open({
			type: 1,
			area: ['480px', '280px'], //高宽
			title: "修改",
			maxmin: true,
			content: $("#modadd"),//DOM或内容
			btn:['确定', '取消']
		,yes: function(index, layero){ //或者使用btn1
			//确定的回调
			//判断执行不同方法
			
		},cancel: function(index){//或者使用btn2（concel）
			//取消的回调
		}
		});
	}
	/* 修改短信接口地址部分结束 */
