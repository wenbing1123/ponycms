
lang = {}

lang.messages = {
	"Role.Type.sys"  : "系统角色",
	"Role.Type.post" : "业务角色"
}

lang.message = function(code){
	if (code != null) {
		var content = lang.messages[code] != null ? lang.messages[code] : code;
		if (arguments.length == 1) {
			return content;
		} else {
			if ($.isArray(arguments[1])) {
				$.each(arguments[1], function(i, n) {
					content = content.replace(new RegExp("\\{" + i + "\\}", "g"), n);
				});
				return content;
			} else {
				$.each(Array.prototype.slice.apply(arguments).slice(1), function(i, n) {
					content = content.replace(new RegExp("\\{" + i + "\\}", "g"), n);
				});
				return content;
			}
		}
	}
};
