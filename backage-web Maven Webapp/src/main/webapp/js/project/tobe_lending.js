$(function(){
//	查看投资记录
	$('#tobe_lending_invest_record').click(function(){
    	layer.open({
            type: 2,
            title: '投资记录',
            maxmin: true,
            shadeClose: true, //点击遮罩关闭层
            area : ['900px' , '580px'],
            content: 'web/project/tobe_lending_invest_record.jsp'
        });
	});
});