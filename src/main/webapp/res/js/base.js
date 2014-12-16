
//全局方法定义

/**
 * 设置或者获取cookie值
 * 
 */
var _cookie = function(name, value, minutes, path, domain, secure){
	if(arguments.length == 1){
		var cookie_start = document.cookie.indexOf(name);
		var cookie_end = document.cookie.indexOf(';', cookie_start);
		return cookie_start == -1 ? '' : unescape(document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length)));
	}else{
		var expires = new Date();
		expires.setTime(expires.getTime() + minutes*60*1000);
		document.cookie = escape(name) + '=' + escape(value)
		+ (expires ? '; expires=' + expires.toGMTString() : '')
		+ (path ? '; path=' + path : '/')
		+ (domain ? '; domain=' + domain : '')
		+ (secure ? '; secure' : '');
	}
};

/**
 * 把表单序列化为对象
 * 
 */
var _serializeObject = function(form){
	var o = {};
	$.each(form.serializeArray(), function(index) { 
		if (o[this['name']]) { 
			o[this['name']] = o[this['name']] + "," + this['value'];
		}else{
			o[this['name']] = this['value'];
		}
	});
	return o;
}

/**
 * 扩展字符串函数startWith
 * 
 */
String.prototype.startWith=function(s){
	if(s==null||s==""||this.length==0||s.length>this.length)
		return false;
	if(this.substr(0,s.length)==s)
		return true;
	else
		return false;
 	return true;
};
/**
 * 扩展字符串函数endWith
 * 
 */
String.prototype.endWith=function(s){
	if(s==null||s==""||this.length==0||s.length>this.length)
		return false;
	if(this.substring(this.length-s.length)==s)
		return true;
	else
		return false;
	return true;
};

/**
 * 扩展日期库函数
 * 
 * @param fmt
 * @returns
 */
Date.prototype.format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}