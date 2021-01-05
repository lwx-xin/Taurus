
$(function(){
	var systemErrMsg = getCookie("systemErrMsg");
	var redirectUrl = getCookie("systemErrRedirect");
	var sourcePath = getCookie("systemErrSourcePath");
	removeCookie("systemErrMsg");
	removeCookie("systemErrRedirect");
	removeCookie("systemErrSourcePath");
	
	if(isNotNull(redirectUrl)){
		hintWarning("系统提示", "重定向"+redirectUrl, null)
	}
	if(isNotNull(systemErrMsg)){
		hintWarning("系统提示", toUTF8(systemErrMsg), null)
	}
	if(isNotNull(sourcePath)){
		hintWarning("系统提示", "异常资源:"+toUTF8(sourcePath), null)
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
	
	var data;
	var formData;
	var processData = true;
	var contentType = "application/x-www-form-urlencoded";
	if(isNotNull(param.data)){
		$.each(param.data, function(k, v){
			param.url = param.url.replace("{"+k+"}", v);
		});
		if(param.data.files != null){
			formData = new FormData();
			$.each(param.data, function(k, v){
				formData.append(k, v);
			});
			formData.append("files", param.data.files);
			processData = false;
			contentType = false;
			data = formData;
		} else {
			data = param.data;
		}
	}
	
	$.ajax({
		url: param.url,
		data: data,
		dataType: param.dataType,
		type: param.type,
		async: isAsync,//false,同步；true,异步
		processData: processData, 
		contentType: contentType,
		headers: {  
			"req-flg": "ajax"  
		},
		beforeSend: function (request) {
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
			
			if(data.errCode=="-1"){
				try{
					swal("提示", "请输入账号", "error");
				}catch(e){
					//TODO handle the exception
				}
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
			
			if(isNotNull(sysErrMessage)){
				console.log("错误信息:"+toUTF8(sysErrMessage));
			}
			if(isNotNull(redirectUrl)){
				console.log("重定向页面:"+redirectUrl);
			}
			if(isNotNull(redirectUrl)){
				console.log("异常资源:"+toUTF8(sourcePath));
			}
			
			if(isNotNull(redirectUrl)){
				if(redirectUrl=="/html/login.html"){
					top.location.href=redirectUrl;
				} else {
					window.location.href=redirectUrl;
				}
			}
			if(isNotNull(sysErrMessage)){
				if(isNull(redirectUrl)){
					hintWarning("系统提示", toUTF8(sysErrMessage), null)
				} else {
					setCookie("systemErrMsg", sysErrMessage)
				}
			}
			
			if(status=="error"){
				
			} else if(status=="timeout"){
				
			} else if(status=="success"){
				
			}
		}
	});
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
		"timeOut": "2000",
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
		"timeOut": "2000",
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

//设置cookie
function setCookie(key, value){
	$.cookie(key, value, { expires: 1, path: '/' });
}

//获取cookie
function getCookie(key){
	return $.cookie(key)
}

//移除cookie
function removeCookie(key){
	$.cookie(key, "", { expires: 0, path: '/' });
}

//清除所有cookie
function clearAllCookie() {  
	var keys = document.cookie.match(/[^ =;]+(?=\=)/g);  
	if(keys) {  
		for(var i = keys.length; i--;)  
			$.cookie(keys[i], "", { expires: 0, path: '/' });
	}  
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

//获取分组下的code列表
function getCodes(codeGroups){
	var codeElements;
	var param = new Object();
	param["codeGroup"] = JSON.stringify(codeGroups);
	ajax({
		url: requestPath.code.url,
		data: param,
		type: requestPath.code.type,
		success: function(data){
			if(data.status){
				codeElements = data.data;
			}
		},
		error: function(data){
			layer.msg("err");
		}
	});
	return codeElements;
}

//获取code对应的名称
function getCodeName(group, value){
	var name = "";
	if(isNotNull(value)){
		$.each(Code, function(k, v){
			if(v.group == group && value == v.value){
				name = v.name;
				return false;
			}
		});
	}
	return name;
}

//退出登录
function logout(){
	setCookie("systemErrMsg","退出登录");
	//跳转登录页面
	top.location.href= "../login.html";
	// ajax({
	// 	url: requestPath.logout.url,
	// 	type: requestPath.login.type,
	// 	success: function(data){
	// 		if(data.status){
	// 			setCookie("systemErrMsg","退出登录");
	// 			//跳转登录页面
	// 			top.location.href= "../login.html";
	// 		}
	// 	},
	// 	error: function(data){
	// 		layer.msg("err");
	// 	}
	// });
}

function toUTF8(str){
	var ascll_str = ""
	// 带","的ascll编码
	var s = decodeURI(str);
	if(isNotNull(s)){
		var asclls = s.split(",");
		for(var i=0;i<asclls.length;i++){
			ascll_str += String.fromCharCode(asclls[i]);
		}
	}
	return ascll_str;
}