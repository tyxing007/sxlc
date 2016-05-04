/**
 * 会员管理  个人会员
 * 资料认证
 * 会员实名认证
 * pr
 */

$(function(){
	if(btn=="1"){
		$("#submit").removeAttr("style");
	}else{
		$("#back").removeAttr("style");
	}
});

/**
 *  根据id 查询实名认证
 */
function reanlNameIdentyList(memberId){
	$.ajax( {  
		 url:appPath+"/memberidety/realNameIdenty.do",
			data:{
				content:memberId
			},
			type:'post',  
			cache:false,  
			dataType:'json',   
			success:function(data) { 
				if(data!=null){
					$("#applyId").val(data.aid);
					alert(data.aid);
					$("#memberId").val(memberId);
					if(data.realName!=null && data.realName!=""){
						$("#realName").text(data.realName);
					}
					if(data.nationName!=null && data.nationName!=""){
						$("#nation").text(data.nationName);
					}
					if(data.sexId==0){
						$("#sex").text("男");
					}else{
						$("#sex").text("女");
					}
					if(data.homeTown!=null && data.homeTown!=""){
						$("#native").text(data.homeTown);
					}
					if(data.sEndDate!=null && data.sEndDate!=""){
						$("#validTime").text(data.sEndDate);
					}else{
						$("#validTime").text("永久有效");
					}
					if(data.personalIDCard!="" && data.personalIDCard!=null){
						$("#idcard").text(data.personalIDCard);
					}
					var $img1 = $("#right_idcard");//身份证正面
					var $img2 = $("#left_idcard");//身份证饭面
					var profix = "";  //前缀
					if(data.positive!=null && data.positive !=""){
						$img1.src=data.positive;
					}
					if(data.reverse!=null && data.reverse !=""){
						$img2.src=data.reverse;
					}
					
				}
			},  
			error : function() {  
				layer.alert("服务器异常",{icon:2});  
			}  
		});
}

/**
 *  提交实名认证
 */
function submitIdentyList(){
	
	var memberId = $("#memberId").val();
	var attestId = $("#applyId").val();
	var statu = $("#statu").val();
	
	var encrypt = new JSEncrypt();
	encrypt.setPublicKey(publicKey_common);
	
	//result 为加密后参数
	memberId = encrypt.encrypt(memberId+"");
	attestId = encrypt.encrypt(attestId+"");
	statu = encrypt.encrypt(statu+"");
	$.ajax( {  
		 url:appPath+"/memberidety/submitRealName.do",
			data:{
				memberId:memberId,
				attestId:attestId,
				statu:statu
			},
			type:'post',  
			cache:false,  
			dataType:'json',   
			success:function(data) { 
				if(data!=null && data!=""){
					if(data == 1){
						layer.alert("审核成功。",{icon:1});  
					}else if(data == 2){
						layer.alert("该认证项已审核。",{icon:0}); 
					}else {
						layer.alert("审核失败。",{icon:2}); 
					}
				}
			},  
			error : function() {  
				layer.alert("服务器异常",{icon:2});  
			}  
		});
}