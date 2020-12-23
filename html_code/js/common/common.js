
$(function(){
	// var systemErrMsg = localStorage.getItem("systemErrMsg");
	// var redirectUrl = localStorage.getItem("systemErrRedirect");
	// localStorage.removeItem("systemErrMsg");
	// localStorage.removeItem("systemErrRedirect");
	var systemErrMsg = getCookie("systemErrMsg");
	var redirectUrl = getCookie("systemErrRedirect");
	var sourcePath = getCookie("systemErrSourcePath");
	removeCookie("systemErrMsg");
	removeCookie("systemErrRedirect");
	removeCookie("systemErrSourcePath");
	
	if(isNotNull(redirectUrl)){
		hintWarning("系统提示", "重定向"+redirectUrl, null)
		//top.location.href=redirectUrl;
	}
	if(isNotNull(systemErrMsg)){
		hintWarning("系统提示", systemErrMsg, null)
		//layer.msg(systemErrMsg);
	}
	if(isNotNull(sourcePath)){
		hintWarning("系统提示", "异常资源:"+sourcePath, null)
	}
});

function ajax(param){
	
	var isAsync = false;
	if(isNotNull(param.async)){
		isAsync = param.async;
	}
	
	var dataType = "json";
	if(isNotNull(param.dataType)){
		dataType = param.dataType;
	}
	
	if(isNotNull(param.data)){
		$.each(param.data, function(k, v){
			param.url = param.url.replace("{"+k+"}", v);
		});
	}
	
	$.ajax({
		url: param.url,
		data: param.data,
		dataType: param.dataType,
		type: param.type,
		async: isAsync,//false,同步；true,异步
		beforeSend: function () {
			//发送请求前
			if(param.beforeSend!=null){
				param.beforeSend();
			}
		},
		success: function(data){
			//console.log(data)
			
			if(isNotNull(data.message)){
				hintInfo("提示",data.message, null);
			}
			
			if(param.success!=null){
				param.success(data);
			}
		},
		error: function(data){
			layer.msg("err");
			if(param.error!=null){
				param.error(data);
			}
		},
		complete: function(request,status){
			var sysErrMessage = request.getResponseHeader("systemErrMsg");
			var redirectUrl = request.getResponseHeader("systemErrRedirect");
			var sourcePath = request.getResponseHeader("systemErrSourcePath");
			console.log("错误信息:"+sysErrMessage)
			console.log("重定向页面:"+redirectUrl)
			console.log("异常资源:"+redirectUrl)
			if(isNotNull(redirectUrl)){
				// localStorage.setItem("systemErrRedirect", redirectUrl);
				//setCookie("systemErrRedirect", redirectUrl);
				top.location.href=redirectUrl;
			}
			if(isNotNull(sysErrMessage)){
				// localStorage.setItem("systemErrMsg", sysErrMessage);
				//setCookie("systemErrMsg", sysErrMessage);
				layer.msg(sysErrMessage);
			}
			if(status=="error"){
				
			} else if(status=="timeout"){
				
			} else if(status=="success"){
				
			}
		}
	});
}

function openWin(url){
	window.location.href = url+"?v="+new Date().getTime();
}

function hintWarning(title, messgae, clickFun){
	toastr.options = {
		"closeButton": true,
		"debug": false,
		"progressBar": true,
		"positionClass": "toast-top-right",
		"onclick": clickFun,
		"showDuration": "400",
		"hideDuration": "1000",
		"timeOut": "5000",
		"extendedTimeOut": "1000",
		"showEasing": "swing",
		"hideEasing": "linear",
		"showMethod": "fadeIn",
		"hideMethod": "fadeOut"
	}
	
	toastr['success'](messgae, title);
}

function hintInfo(title, messgae, clickFun){
	toastr.options = {
		"closeButton": true,
		"debug": false,
		"progressBar": true,
		"positionClass": "toast-top-right",
		"onclick": clickFun,
		"showDuration": "400",
		"hideDuration": "1000",
		"timeOut": "5000",
		"extendedTimeOut": "1000",
		"showEasing": "swing",
		"hideEasing": "linear",
		"showMethod": "fadeIn",
		"hideMethod": "fadeOut"
	}
	
	toastr['info'](messgae, title);
}

function isNull(obj){
	if(obj==null || obj=="" || (typeof obj=="String" && obj.trim()=="") || JSON.stringify(obj)=="{}" || JSON.stringify(obj)=="[]"){
		return true;
	}
	return false;
}

function isNotNull(obj){
	return !isNull(obj);
}

function setCookie(key, value){
	$.cookie(key, value, { expires: 1, path: '/' });
}

function getCookie(key){
	return $.cookie(key)
}

function removeCookie(key){
	$.cookie(key, "", { expires: 0, path: '/' });
}

function getPageParam(){
	var objs = new Object();
	var strs = window.location.href.split("?");
	if(isNull(strs) || strs.length==1){
		return objs;
	}
	var params = strs[1].split("&");
	for(var i=0;i<params.length;i++){
		var key = params[i].split("=")[0];
		var value = params[i].split("=")[1];
		objs[key]=value;
	}
	return objs;
}