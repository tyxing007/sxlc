//加密设置
var encrypt = new JSEncrypt();
encrypt.setPublicKey(publicKey_common);
//启用停用
function ofableDisable(id,statu) {
	//获取选取对象
	var mess ="确认启用？";
	if(statu == 0){
		var mess ="确认停用？";
	}
	var status =statu;
	//获取选取对象
	layer.confirm(mess, {
		btn: ['确定', '取消']
	}, function(index, layero){
		//按钮【按钮一】的回调
		$.ajax({
			type : 'post',
			url : appPath + "/coop/ofOrOpen.do",
			data : {
				partNerId : encrypt.encrypt("" + id), 
				statu : encrypt.encrypt("" +statu)
			},
			success : function (msg) {
				if(status ==1){
			  		if (msg == 1) {
			  			layer.alert("启用成功!",{icon:1});
			  			var table = $('#partnerTb').DataTable();
						table.ajax.reload();
			  		}else {
			  			layer.alert("启用失败!",{icon:2});
			  		}
			  	}else{
			  		if (msg == 1) {
			  			layer.alert("停用成功!",{icon:1});
			  			var table = $('#partnerTb').DataTable();
						table.ajax.reload();
			  		}else {
			  			layer.alert("停用失败!",{icon:2});
			  		}
			  	}	
			}
		});
		//执行完关闭
		layer.close(index);
	}, function(index){
		//按钮【按钮二】的回调
	});
}

$(function () {
	validform5("layui-layer-btn0","savePartner",false,"5");
});

		
/**
 * 增加/修改操作
 * @param type 1：增加 2：修改
 * @returns
 */
var ue = UE.getEditor('editor');
var num=0;
function addOrUpdate(type){
	//初始化
	//清空数据
	document.getElementById("savePartner").reset();
//	ue.reset();
	$("#showImg").attr("src","");
	$("#fileList").html("");
	$("#filePicker").html("选择图片");//清除样式
	//操作
	var title="";
	if(type==1){
		title="添加合作伙伴";
		if(num==0){
			ue.addListener("ready", function () {
	        	ue.setContent("");
		    });
			num += 1;
		}else{
			ue.setContent("");
		}
	}else if(type==2){
		title="修改合作伙伴";
		var data = $('#partnerTb').DataTable().rows('.selected').data();
		if(data.length <= 0){
			layer.alert("请选择要修改的数据！",{icon:0});
			return;
		}
		$("#coopId").val(data[0].id);	
		$("#title").val(data[0].name);
		$("#link").val(data[0].url);	
		$("#pictureUrl").val(data[0].logo);
		if(num==0){
			ue.addListener("ready", function () {
	        	ue.setContent(data[0].content);
		    });
			num += 1;
		}else{
			ue.setContent(data[0].content);
		}
		$("#showImg").attr("src",$("#hostPath").val()+data[0].logo);
	}
	
	layer.open({
	    type: 1,
	    area: ['1100px', '600px'], //高宽
	    title: title,
	    content: $(".pic-add"),//DOM或内容
	    btn:['确定', '取消']
		  ,yes: function(index, layero){ //或者使用btn1
//				$("#content").val(content);
//				ue.destroy();
				
				//加密参数
				var data={};
				var title = encrypt.encrypt($("#title").val());
				data.title = title;
				var url = encrypt.encrypt($("#link").val());
				data.url = url;
				var logo = encrypt.encrypt($("#pictureUrl").val());
				data.logo = logo;
				var content = ue.getContent();
				data.content = encrypt.encrypt(content);
				 $(".layui-layer-btn0").addClass("disabled");
				if (type == 1) {
					$.ajax( {  
						url:appPath+"/coop/save.do",
						data:data,
						type:'post',  
						cache:false,  
						dataType:'json',  
						success:function(data) { 
							if(data==1){
								layer.alert("操作成功",{icon:1});
								$(".layui-layer-btn1").click();
								 var table = $('#partnerTb').DataTable();
								 table.ajax.reload();
							}else{
								layer.alert("操作失败",{icon:2});  
							}
							 $(".layui-layer-btn0").removeClass("disabled");
						},  
						error : function() {  
							layer.alert("服务器异常",{icon:2});  
						}  
					});
				}else {
					var coopId = encrypt.encrypt($("#coopId").val()+"");
					data.partnerId = coopId;
					$.ajax( {  
						url:appPath+"/coop/update.do",
						data:data,
						type:'post',  
						cache:false,  
						dataType:'json',  
						success:function(data) { 
							if(data==1){
								layer.alert("修改成功",{icon:1});
								$(".layui-layer-btn1").click();
								 var table = $('#partnerTb').DataTable();
								 table.ajax.reload();
							}else{
								layer.alert("修改失败",{icon:2});  
							}
							 $(".layui-layer-btn0").removeClass("disabled");
						},  
						error : function() {  
							layer.alert("服务器异常",{icon:2});  
						}  
					});
				}
			  
		  },cancel: function(index){//或者使用btn2（concel）
//			  ue.destroy();
		  }
	});
	//上传插件初始化
	expUpload("#pictureUrl");
}

/** 合作伙伴 **/

$(function() {
	$('#partnerTb').DataTable(
	{
		autoWidth : false,
		scrollY : 500,
		pagingType: "simple_numbers",//设置分页控件的模式  
		lengthMenu:[[5,10,25,50,-1],[5,10,25,50,"全部"]],
		colReorder : false,
		scrollX : true,
		sScrollX : "100%",
		sScrollXInner : "100%",
		bScrollCollapse : true,  
		processing: true, //打开数据加载时的等待效果  
        serverSide: true,//打开后台分页  
        ajax: {  
            "url": appPath + "/coop/list.do",   
            "dataSrc": "results", 
            "type":"post",
            "data": function ( d ) {  
                var level1 = $('#level1').val();  
                //添加额外的参数传给服务器  
                d.extra_search = level1;
            } 
        },
        columns: [  
                  {title:'<input type="checkbox" class="table-checkbox"  value="1" />',
                	  "mRender": function (data, type, full) {
                		  sReturn = '<input type="checkbox" value="1" />';
                		  return sReturn;
                	  }
//                	  "sClass": "table-checkbox"
                  },
                  { title:"合作伙伴id","data": "id" },
                  { title:"添加时间","mRender": function (data, type, full) {
                	  var sReturn ="";
                	  if(full.createTime!=null && full.createTime!=""){
                		  var a = full.createTime;
                		  sReturn = a.substring(0,a.length-2);
                	  }
                	  	  return sReturn;
	              	  }
	               },  
                  { title:"合作伙伴图片","data": "logo", 
    	        	  "mRender": function (data, type, full) {
    	        		  return '<a href="javascript:void(0);" class="btn-det" onclick="viewPic(this)">查看图片</a>';
    	        	  }
    	          }, 
                  { title:"合作伙伴名称","data": "name" },
                  { title:"合作伙伴链接","data": "url" },
                  { title:"合作伙伴简介","data": "introduction" },
                  { title:"最后一次管理员操作","data": "adminName" }  ,
                  { title:"状态","data": "statu", 
                	  "mRender": function (data, type, full) {
                		 if (full.statu==0) {
                			 return "<font color='red'>无效</font>";
                		 }else {
                			 return "有效";
                		 }
                	  } 
                  },  
                  { title:"操作","data": "deptStatu",
                  	"mRender": function (data, type, full) {
                  		 if(full.statu==false){
                  			return "<a onclick=\"ofableDisable("+full.id+",1);\" href=\"javascript:void(0);\">启用</a>";
                  		 }else{
                  			return "<a onclick=\"ofableDisable("+full.id+",0);\" href=\"javascript:void(0);\">停用</a>";
                  		 }
                	  } 
                  }
                  
        ],
        aoColumnDefs : [
        				// {"bVisible": false, "aTargets": [ 3 ]}, //控制列的隐藏显示
        				{
        					sDefaultContent: '',
        					orderable : false,
        					aTargets: [ '_all' ]
        				}
        				],
        rowCallback:function(row,data){//添加单击事件，改变行的样式      
//        	if($.inArray(data.DT_RowId,selected)!==-1){
//        		$(row).addClass('selected'); 
//        	}
        }
});
});

function viewPic(btn) {
	var imgRealURL =$("#hostPath").val();
    var data = $('#partnerTb').DataTable().row($(btn).parents('tr')).data();
    var url = data.logo;
    if(data!=""){
    	$("#picView").attr("src",imgRealURL+url);
    	$(".hideHtml").hide();
    }else{
    	$(".hideHtml").show();
    	$("#picView").attr("src","");
    }
	layer.open({
	    type: 1,
	    area: ['500px', '400px'], //高宽
	    title: "查看头像",
//	    maxmin: true,
	    content: $(".pic-view"),//DOM或内容
	    btn:['关闭']
		  ,cancel: function(index){
		  	//取消的回调
		  }
	});
}

$(function() {
	//单选
	$('#partnerTb tbody').on( 'click', 'tr', function () {
		var $this = $(this);
		var $checkBox = $this.find("input:checkbox");
		 if ( $(this).hasClass('selected') ) {
			 $checkBox.prop("checked",false);
				$(this).removeClass('selected');
			}
			else {
				$('tr.selected').removeClass('selected');
				$this.siblings().find("input:checkbox").prop("checked",false);
				$checkBox.prop("checked",true);
				$(this).addClass('selected');
			}
		
	} );
});