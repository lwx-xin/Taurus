// new Date().toStr("yyyy-MM-dd HH:mm:ss")
Date.prototype.toStr = function(fmt){
    if(this == "" || this == null){
        return "";
    }
    var o = {
        "M+": this.getMonth()+1,
        "d+": this.getDate(),
        "H+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth()+3)/3),
        "S": this.getMilliseconds()
    }
    if(/(y+)/.test(fmt)){
        fmt = fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4-RegExp.$1.length))
    }
    for(var k in o)
        if(new RegExp("("+k+")").test(fmt))fmt =
            fmt.replace(RegExp.$1, (RegExp.$1.length==1)?(o[k]):(("00"+o[k]).substr((""+o[k]).length)));
    return fmt;
}

// "2020-11-30 11:05:51".toDate()
String.prototype.toDate = function(){
    if(this == "" || this == null){
        return "";
    }
    var ymd = this.split(" ")[0];
    var hms = this.split(" ")[1];

    var ymd_arr = ymd.split("/");
    if(ymd_arr!=null && ymd_arr[0]==ymd){
        ymd_arr = ymd.split("-");
    }
    if(hms!=null){
        var hms_arr = hms.split(":");
        return new Date(ymd_arr[0]==null?0:ymd_arr[0], ymd_arr[1]-1==null?1:ymd_arr[1]-1, ymd_arr[2]==null?1:ymd_arr[2], hms_arr[0]==null?0:hms_arr[0], hms_arr[1]==null?0:hms_arr[1], hms_arr[2]==null?0:hms_arr[2]);
    }
    return new Date(ymd_arr[0]==null?0:ymd_arr[0], ymd_arr[1]-1==null?1:ymd_arr[1]-1, ymd_arr[2]==null?1:ymd_arr[2]);
}

