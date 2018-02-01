var webSocket;
$(document).ready(function(){
	cainiao.initConnect();
});
//初始化打印组件
var cainiao={};
	cainiao.initConnect=function(){
		    socket = new WebSocket('ws://127.0.0.1:13528');
		    //如果是https的话，端口是13529
		    //socket = new WebSocket('wss://localhost:13529');
		    // 打开Socket
		    socket.onopen = function(event){
		    	var request=cainiao.getRequestObject('getPrinters');
		    	socket.send(JSON.stringify(request));
		        // 监听Socket的关闭
		        socket.onclose = function(event)
		        {
		            console.log('Client notified socket has closed',event);
		        };
		    };
		    socket.onmessage = function(event){
		    	
		        var data = JSON.parse(event.data);
		        if ("getPrinters" == data.cmd) {
			    	$("#printList").find("option").each(function(){
			    		$(this).remove();
			    	});;
		        	var  printList=data.printers;
		            var html;
		            for(var i=0;i<printList.length;i++){
		            	var obj=printList[i];
		            	html="<option value='"+obj.name+"'>"+obj.name+"</option>"
		            	$(html).appendTo("#printList");
		            }
		        }else if ("setPrinterConfig"==data.cmd){
		        	
		        } else {
		           // alert("返回数据:" + JSON.stringify(data));
		           console.log(JSON.stringify(data));
		        }
	
		};
};
//构建请求的request对象
cainiao.getRequestObject=function(cmd) {
    var request  = new Object();
    request.requestID=cainiao.getUUID(8, 16);
    request.version="1.0";
    request.cmd=cmd;
    return request;
};
cainiao.getUUID=function(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length; 
    if (len) {
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
      var r;
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
      uuid[14] = '4';
      for (i = 0; i < 36; i++) {
        if (!uuid[i]) {
          r = 0 | Math.random()*16;
          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
        }
      }
    }
    return uuid.join('');
}
//执行打印
cainiao.doPrint=function(){
	var printer=$("#printList").val();
	var request = cainiao.getRequestObject("print");    
    request.task = new Object();
    request.task.taskID = cainiao.getUUID(8,10);
    request.task.preview = false;
    request.task.printer = printer;
    
    $.post(ctx+"/waybill/c_printData",{ids:ids,type:type},function(data){
    	if(data && data.ret==1){
    		console.log(data.results);
    		request.task.documents=eval(data.results);
    	 	socket.send(JSON.stringify(request));
    	 	$("#orderIndex").val(data.orderIndex);
    	 	$("#description").val(data.description);
    	 	$("#printIndex").val(data.printIndex);
    	 	$("#index").val(data.index);
    	}
    });   
};
cainiao.getJsonDataAjax=function(id){
	
};
