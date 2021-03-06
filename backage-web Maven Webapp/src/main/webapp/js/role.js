/*角色管理*/
/**
 * 版块：角色管理
 * 内容介绍：
 * 
 */
$(function(){
	//启用停用
	$(".btn-enable").on("click touchstart",function(){
		//获取选取对象
		
		layer.confirm('确定启用？', {
		  btn: ['确定', '取消']
		}, function(index, layero){
		  //按钮【按钮一】的回调
			
			//执行完关闭
		  	layer.close(index);
		}, function(index){
		  //按钮【按钮二】的回调
		});
	});
	
	$(".btn-disable").on("click touchstart",function(){
		//获取选取对象
		
		layer.confirm('确定停用？', {
		  btn: ['确定', '取消']
		}, function(index, layero){
		  //按钮【按钮一】的回调
			
			//执行完关闭
		  	layer.close(index);
		}, function(index){
		  //按钮【按钮二】的回调
		});
	});
	
	$("a.btn-det").on("click touchstart",function(){
		//获得选取的对象
		
		layer.open({
		    type: 1,
		    area: ['560px', '480px'], //高宽
		    title: "角色权限",
		    content: "角色权限"//DOM或内容
		});
	});
	
	//绑定弹窗事件，具体根据不同类编写代码，注意区别类名
	//添加部门
	$(".obtn-dept-add").on("click touchstart",function(){
		layer.open({
		    type: 1,
		    area: ['480px', '320px'], //高宽
		    title: "添加部门",
		    content: $(".dept-add"),//DOM或内容
		    btn:['确定', '取消']
			  ,yes: function(index, layero){ //或者使用btn1
			    //确定的回调
			  	
			  },cancel: function(index){//或者使用btn2（concel）
			  	//取消的回调
			  }
		});
	});
	
	//修改部门
	$(".obtn-dept-mod").on("click touchstart",function(){
		//获得选取的对象
		
		layer.open({
		    type: 1,
		    area: ['480px', '320px'], //高宽
		    title: "修改部门",
		    content: $(".dept-mod"),//DOM或内容
		    btn:['确定', '取消']
			  ,yes: function(index, layero){ //或者使用btn1
			    //确定的回调
			  },cancel: function(index){//或者使用btn2（concel）
			  	//取消的回调
			  }
		});
	});
	
	//删除部门
	$(".obtn-dept-del").on("click touchstart",function(){
		//获得选取的对象
		
		layer.confirm('确定删除该部门？', {
		  btn: ['确定', '取消']
		}, function(index, layero){
		  //按钮【按钮一】的回调
			
			layer.close(index);
		}, function(index){
		  //按钮【按钮二】的回调
		});
	});
	
	
/*	$(".obtn-role-add").on("click touchstart",function(){
		layer.open({
		    type: 1,
		    area: ['480px', '320px'], //高宽
		    title: "添加角色",
		    content: $(".role-add"),//DOM或内容
		    btn:['确定', '取消']
			  ,yes: function(index, layero){ //或者使用btn1
			    //确定的回调
			  },cancel: function(index){//或者使用btn2（concel）
			  	//取消的回调
			  }
		});
	});
	
	$(".obtn-role-mod").on("click touchstart",function(){
		//获得选取的对象
		
		//之后
		layer.open({
		    type: 1,
		    area: ['480px', '320px'], //高宽
		    title: "修改角色",
		    content: $(".role-mod"),//DOM或内容
		    btn:['确定', '取消']
			  ,yes: function(index, layero){ //或者使用btn1
			    //确定的回调
			  },cancel: function(index){//或者使用btn2（concel）
			  	//取消的回调
			  }
		});
	});

	
	$(".obtn-role-del").on("click touchstart",function(){
		//获得选取的对象
		
		layer.confirm('确定删除该条信息？', {
		  btn: ['确定', '取消']
		}, function(index, layero){
		  //按钮【按钮一】的回调
			
			//执行完关闭
		  	layer.close(index);
		}, function(index){
		  //按钮【按钮二】的回调
		});
	});
	
	*/
	
	//==============理财顾问
/*	$(".obtn-adviser-add").on("click touchstart",function(){
		layer.open({
		    type: 1,
		    area: ['480px', '320px'], //高宽
		    title: "添加理财顾问",
		    content: $(".adviser-add"),//DOM或内容
		    btn:['确定', '取消']
			  ,yes: function(index, layero){ //或者使用btn1
			    //确定的回调
			  },cancel: function(index){//或者使用btn2（concel）
			  	//取消的回调
			  }
		});
	});
	
	$(".obtn-adviser-del").on("click touchstart",function(){
		//获得选取的对象
		
		layer.confirm('确定移除该理财顾问？', {
		  btn: ['确定', '取消']
		}, function(index, layero){
		  //按钮【按钮一】的回调
			
			//执行完关闭
		  	layer.close(index);
		}, function(index){
		  //按钮【按钮二】的回调
		});
	});*/
	
	//添加职务
	$(".obtn-post-add").on("click touchstart",function(){
		layer.open({
		    type: 1,
		    area: ['480px', '320px'], //高宽
		    title: "添加职务",
		    content: $(".post-add"),//DOM或内容
		    btn:['确定', '取消']
			  ,yes: function(index, layero){ //或者使用btn1
			    //确定的回调
			  },cancel: function(index){//或者使用btn2（concel）
			  	//取消的回调
			  }
		});
	});
	
	$(".obtn-post-mod").on("click touchstart",function(){
		//获得选取的对象
		
		//之后
		layer.open({
		    type: 1,
		    area: ['480px', '320px'], //高宽
		    title: "修改职务",
		    content: $(".post-mod"),//DOM或内容
		    btn:['确定', '取消']
			  ,yes: function(index, layero){ //或者使用btn1
			    //确定的回调
			  },cancel: function(index){//或者使用btn2（concel）
			  	//取消的回调
			  }
		});
	});
	
	$(".obtn-post-del").on("click touchstart",function(){
		//获得选取的对象
		
		//删除某个职务，当有员工拥有改职务时，不能进行删除
		layer.confirm('确定删除该职务？', {
		  btn: ['确定', '取消']
		}, function(index, layero){
		  //按钮【按钮一】的回调
			
			//执行完关闭
		  	layer.close(index);
		}, function(index){
		  //按钮【按钮二】的回调
		});
	});
	
	
	
});

//function