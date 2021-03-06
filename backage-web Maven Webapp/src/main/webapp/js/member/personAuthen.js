/*  状态切换       */
$(function(){
	
	//单选
	$('#table_id tbody').on( 'click', 'tr', function () {
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
	$(".auditState").each(function(){
		if($(this).val()==1){
			$(".buttonCombination button:last-child").hide();
			$(".buttonCombination button:first-child").show();
		}
	});
	$(".auditState").change(function(){
		if($(this).val()==1){
			$(".buttonCombination button:first-child").show();
			$(".buttonCombination button:last-child").hide();
		}if($(this).val()!=1){
			$(".buttonCombination button:first-child").hide();
			$(".buttonCombination button:last-child").show();
		}
	});
	
	
});


/**
 * 返回
 * @param suffix
 */
function back(suffix){
	var URL =  appPath+"/"+suffix; 
	window.location.href  = URL;
	
	//window.history.back();
}

/**
 * 查询按钮
 */
$(".glyphicon-search").on("click",function(){
	$('#table_id').DataTable().ajax.reload();
	
});

/*  跳转页面控制按钮显示         */
$(function(){


	/*var btn;*/
	
	

	/*var obj = window.localStorage.getItem("a");
	console.log(JSON.parse(obj).btn);*/

	/*$(".cerfiedBtn").click(function(){
		 jump(0,{btn:1});
	});
	$(".checkBtn").click(function(){
		 jump(0,{btn:2});
	});*/
	/*var btn = location.search.slice(5,location.search.length);*/
	
	
});

$(function(){/* 
	var obj = window.localStorage.getItem("a");
	console.log(JSON.parse(obj).btn); */	
});


/**
 * 跳转到审核或者查看详情页面
 * @param btn  区分页面
 * @param url  路径
 */
function jump(typeId,btn,url){
	/*window.localStorage.setItem("a",JSON.stringify(obj));*/
	var rowdata = $('#table_id').DataTable().rows('.selected').data();
	if(rowdata.length<1){
		layer.alert("请选择要处理的事务！",{icon:0});
		return;
	}
	var content = rowdata[0].memberID;
	if(typeId==7 || typeId ==8 ||typeId == 2 || typeId ==3 ||typeId ==5 ||typeId ==6 ||typeId ==9 ||typeId >11 ){//由于会员一对多，所以要传认证记录id,加上其他认证
		content = rowdata[0].certificationID;
	}
	location.href = url + '?start='+typeId+'&content='+content+'&draw='+btn;
 
}

/**
 * 根据认证类型查询所有需要认证的会员
 */
function IdentyList(attestTypeId){
	if(attestTypeId==0){
		attestTypeId=1;
	}
	var  arr =[];//控制需要隐藏的列
	if(attestTypeId==1||attestTypeId==0){//实名认证
		arr =[7,8,9,10,11,12,13,14,15,16];
	}else if(attestTypeId==2||attestTypeId==3||attestTypeId==5||attestTypeId==6||attestTypeId==9||attestTypeId==12 || attestTypeId==26 ){//其他一般认证
		arr =[3,4,5,6,7,8,9,10,11,12,13,14,15,16];
	}else if(attestTypeId==4){//住址认证
		arr =[3,4,5,6,8,9,10,11,12,13,14,15,16];
	}else if(attestTypeId==7){//房产认证
		arr =[3,4,5,6,7,10,11,12,14,15,16];
	}else if(attestTypeId==8){//车产认证
		arr =[3,4,5,6,7,8,9,14,15,16];
	}else if(attestTypeId==10){//婚姻认证
		arr =[3,4,5,6,7,8,9,10,11,12,13,15,16];
	}else if(attestTypeId==11){//学历认证
		arr =[3,4,5,6,7,8,9,10,11,12,13,14,16];
	}
	if(attestTypeId>11){
		arr =[3,4,5,6,7,8,9,10,11,12,13,14,15];
	}
	$('#table_id').DataTable(
			{	autoWidth : false,
				scrollY : 500,
				serverSide:true,
				pagingType: "simple_numbers",//设置分页控件的模式  
				paging : true,//分页
				searching : true,
	/*			processing:true,
				displayStart:0,*/
				info : true,// 左下角信息
//					ordering: false,//排序
				lengthMenu:[10,25,50,100],
//					aaSorting : [ [ 16, "desc" ] ],// 默认第几个排序
				colReorder : false,
				scrollX : true,
				sScrollX : "100%",
				sScrollXInner : "100%",
				bScrollCollapse : true,  
				processing: true, //打开数据加载时的等待效果  
		        serverSide: true,//打开后台分页  
		        ajax: {  
		            "url": appPath+"/memberidety/getIdetyListByType.do",   
		            "dataSrc": "results",   
		            "data": function ( d ) {
		            	var member_Name = $("#memberName").val();//会员姓名
		            	var user_Name =  $("#userName").val();//用户名
		            	var idcard ="";
		            	if($("#idcard")!=null && $("#idcard").val()!=undefined){
		            		idcard = $("#idcard").val();//身份证
		            	}
		            	
		                var statu = $("#statu").val();
		            	var encrypt = new JSEncrypt();
		            	encrypt.setPublicKey(publicKey_common);
		            	
		            	//result 为加密后参数
		            	member_Name = encrypt.encrypt(member_Name);
		            	user_Name = encrypt.encrypt(user_Name);
		            	idcard = encrypt.encrypt(idcard);
		            	attestTypeID = encrypt.encrypt(attestTypeId+"");
		            	statu = encrypt.encrypt(statu+"");
		            	d.memberName=member_Name;
		            	d.userName=user_Name;
		            	d.idcard=idcard;
		            	d.attestTypeID=attestTypeID;
		            	d.statu=statu;
		            } 
		        },
		        columns: [  
		                  {title:'<input type="checkbox" class="table-checkbox"  value="1" />',
		                	  "mRender": function (data, type, full) {
		                		  sReturn = '<input type="checkbox" value="1" />';
		                		  return sReturn;
		                	  }
		                  },
		                  { title:"会员登录名","data": "logname"},   
		                  { title:"姓名","data": "realName" },  
		                  { title:"性别","mRender":function(data, type, full){
		                	  var sReturn ="";
		                	  if(full.sexId==0){
		                		  sReturn = "<font >男</font>";
		                	  }else{
		                		  sReturn = "<font >女</font>";
		                	  }
		                  		return sReturn;
		                  	}
		                  }, 
		                  { title:"民族","data": "nationName"},   
		                  { title:"身份证号","data": "personalIDCard" },  
		                  { title:"籍贯","data": "homeTown" },
		                  { title:"住址","data": "address" },//7
		                  { title:"房产位置","data": "houseAddress" },//8
		                  { title:"房产面积","data": "area" },
		                  { title:"车辆品牌","data": "brand" },//10
		                  { title:"车辆型号","data": "model" },
		                  { title:"车牌号","data": "licensePlate" },
		                  { title:"价值（元）","data": "value" },//13
		                  { title:"婚姻状况","data": "homeTown" },//14
		                  { title:"最高学历","data": "homeTown" },//15
		                  { title:"认证类型","data": "attestTypeName" },//15
		                  { title:"申请时间","data": "sRecordDate" },
		                  { title:"有效期","mRender":function(data, type, full){
		                	  var sReturn ="";
		                	  if(full.sValidTime!="" && full.sValidTime!=null){
		                		  sReturn = "<font >"+full.sValidTime+"</font>";
		                	  }else{
		                		  sReturn = "<font >永久有效</font>";
		                	  }
		                  		return sReturn;
		                  	}
		                  },  
		        ],
		        aoColumnDefs : [
		                       {"bVisible": false, "aTargets":arr}, //控制列的隐藏显示
		        				
		        				{
		        						"orderable" : false,
		        						"aTargets" : [0,1,2,3]
		        					/* aTargets : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
		        							13, 14, 15 ] */
		        				} // 制定列不参与排序
		        				],
		        
		        rowCallback:function(row,data,index){//添加单击事件，改变行的样式      
		        },
		        oTableTools:{"sRowSelect":"multi"}
	});
}
