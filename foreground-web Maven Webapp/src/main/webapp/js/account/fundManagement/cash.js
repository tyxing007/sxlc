$(function(){
	$(".TC").addClass("down");
	$(".TC2").addClass("down2");
});
jQuery.fn.changeRemarks = function(){//用来剔除特殊字符
	var remarks2 = $(this).val() ;
	var cont = remarks2.length;
	for(var c = 0;c < cont ; c++){
		remarks2=remarks2.replace(/["'<>%;)(&+]/,"");//暂时使用！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
	}
	$(this).val(remarks2);
};

/* 2016-04-06  胥福星   弹出层提示  */
$(function(){
	$(".cashInput1").focus(function(){
		$(this).parent().layoutFocus("需大于1元");
	});
	$(".cashInput1").blur(function(){
		$(this).parent().parent().find(".tip").remove();
	});
});
/* 获取动态验证码的弹出提示层js代码  */
jQuery.fn.layoutCode = function(str){
	var s = str;
	var m = '<span class="dynamicVerificationSpan">' + s + '</span>';
	this.parent().css('position','relative');
	this.parent().append(m);
	this.parent().find(".dynamicVerificationSpan").css("left",this.offset().left - this.parent().offset().left + this.innerWidth() + 10);
	this.parent().find(".dynamicVerificationSpan").css("top",this.offset().top - this.parent().offset().top );
};
$(function(){
	/* 验证码提示层      */
	$(".codeGet").click(function(){
		if($(this).parent().parent().find(".dynamicVerificationSpan").length > 0){
			return false;
		}else{
			$(this).parent().layoutCode("已发送，3分钟后可重新获取");
			$(this).parent().parent().find(".tipError").remove();
		}
		setTimeout(function(){
			$(".codeGet").parent().parent().find(".dynamicVerificationSpan").remove();
			$(".codeGet").html("重新获取");
		},30000);
	});
	$(".cashCodeGet").blur(function(){
		var str = this.value;
		var reg = new RegExp("^[0-9]{5,6}$");
		if(reg.test(str)){
			$(this).parent().layoutSuccess();
			$(this).css("border-color","#ddd");
			$(".codeGet").parent().parent().find(".dynamicVerificationSpan").remove();
		}else{
			$(".codeGet").parent().parent().find(".dynamicVerificationSpan").remove();
		}
	});
	
	/*...输入的金额大小...*/
	var cashSpan1;
	var cashInput2;
	var minMoney = 1;//最小提现金额
	$('.cashInput1').focus(function(){//输入金额
		$(this).parent().layoutClean();
		cashSpan1=$('.cashSpan').text();//最大金额获取
		cashSpan1 = parseFloat((cashSpan1 + '').replace(/\,/g, ''));
		$(this).parent().layoutFocus("需大于"+minMoney+"元");
	});
	$('.cashInput1').blur(function(){
		cashInput2=$('.cashInput1').val();//提现金额
		var cashInput3=$('.cashInput1').attr("lang");//lang
		if(this.value == cashInput3)return;
		if(parseFloat((cashInput2 + '').replace(/\,/g, '')) > cashSpan1){
			this.value = parseFloat(cashSpan1).toFixed(2);
			this.value = $('.cashInput1').format(this.value);			
		}
		if(parseFloat((cashInput2 + '').replace(/\,/g, '')) < minMoney){
			this.value = parseFloat(minMoney).toFixed(2);
			this.value = $('.cashInput1').format(this.value);			
		}		
		/*...实际到账金额...*/
		var moneyFormat2=$('.moneyFormat1').text();//手续费
		//实际到账
		var moneyZhang1=$('.moneyZhang').text((cashInput2 + '').replace(/\,/g, '')-moneyFormat2);
		
	});
	/*....银行卡...*/
	var  chooseCard=1;
	if($('.clearfix li label').length == 0){
		chooseCard = 0;
	}
	else{
		$('.clearfix li label').click(function(){
			chooseCard=$(this).attr("value");
		});
	}
	$('.beiZhu').blur(function(){
		$('.beiZhu').changeRemarks($('.beiZhu').val());
	});
	$("#tiXian").Validform({
		tiptype:3,//提示信息类型
		btnSubmit:".cashBtn", //#btn_sub是该表单下要绑定点击提交表单事件的按钮;如果form内含有submit按钮该参数可省略;
		datatype:extdatatype,//扩展验证类型
		ajaxPost:{//使用ajax提交时
			url:"",
			datatype:"json",
			success:function(data,obj){
	        },
	        error:function(data,obj){
	            console.log(data.status);
	        }
		}
	});
	
	
});

