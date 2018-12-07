$(function(){
	
	$("#datetimepicker1").click(function(){
		
	
	//设置日期时间控件
	 $('#datetimepicker1').datetimepicker({
	        language: 'zh-CN',//显示中文
	        format: 'yyyy-mm-dd',//显示格式
	        minView: "month",//设置只显示到月份
	        initialDate: new Date(),
	        autoclose: true,//选中自动关闭
	        todayBtn: true,//显示今日按钮
	        locale: moment.locale('zh-cn')
	    });
	    //默认获取当前日期
	    var today = new Date();
	    var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1) + "-" + today.getDate();
	    //对日期格式进行处理
	    var date = new Date(nowdate);
	    var mon = date.getMonth() + 1;
	    var day = date.getDate();
	    var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day);
	    document.getElementById("inDate").value = mydate;
	    
	});
	    
	  //设置日期时间控件
	$("#datetimepicker2").click(function(){
		 $('#datetimepicker2').datetimepicker({
		        language: 'zh-CN',//显示中文
		        format: 'yyyy-mm-dd',//显示格式
		        minView: "month",//设置只显示到月份
		        initialDate: new Date(),
		        autoclose: true,//选中自动关闭
		        todayBtn: true,//显示今日按钮
		        locale: moment.locale('zh-cn')
		    });
		    //默认获取当前日期
		    var today = new Date();
		    var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1) + "-" + today.getDate();
		    //对日期格式进行处理
		    var date = new Date(nowdate);
		    var mon = date.getMonth() + 1;
		    var day = date.getDate();
		    var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day);
		    document.getElementById("outDate").value = mydate;
	});
});
