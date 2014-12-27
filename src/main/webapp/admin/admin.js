Ext.Loader.setConfig({enabled: true});

App.pageSize = 20;
App.init = function(){
	Ext.QuickTips.init(); 
	Ext.form.Field.prototype.msgTarget = 'qtip'; 
	Ext.BLANK_IMAGE_URL = App.base + '/extjs4/resources/s.gif';
}

Ext.application({
    name: 'App',
    appFolder: App.base + '/admin',
    autoCreateViewport: true,
    
    controllers: [
    'IndexController',
    'ProfileController',
    'UserController','RoleController','DomainController','GroupController','DicController','FileController','CfgController','LogController'         
    ],
    
    launch: function(){
   	
    	App.init();
    	App.msg.init();
		
		var hideMask = function () {
	        Ext.get('loading').remove();
	        Ext.fly('loading-mask').animate({
	            opacity:0,
	            remove:true
	        });
	    };
	    Ext.defer(hideMask, 250);
    	
    }
});

App.msg = function(){
	var msgCt;
	function createBox(t, s){
		return '<div class="msg"><h3>' + t + '</h3><p>' + s + '</p></div>';
	}
	return {
		error: function(msg){
			Ext.MessageBox.show({title: '错误',msg: msg,buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});
		},
		alert: function(msg){
			Ext.MessageBox.show({title: '提示',msg: msg,buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.INFO});
		},
		confirm: function(msg,fn){
			Ext.MessageBox.show({title: '确认',msg: msg,buttons: Ext.MessageBox.YESNO,icon: Ext.MessageBox.QUESTION,fn:function(btn){if(btn == 'yes'){Ext.callback(fn);}}});
		},
		tip : function(msg){
			if(!msgCt){
	            msgCt = Ext.DomHelper.insertFirst(document.body, {id: 'msg-div'}, true);
	        }
	        if(msg != undefined){
				var s = Ext.String.format.apply(String, Array.prototype.slice.call(arguments, 0));
				var m = Ext.DomHelper.append(msgCt, createBox('操作提示', s), true);
				m.hide();
	            m.slideIn('t').ghost('t', {delay: 3000, remove: true});
	        }
		},
		init : function(){
			if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
		}
	};
}();

App.ajax = function(url,fn,params){
	if(typeof(params) == 'undefined') params = {};
	Ext.Ajax.request({
		url: url,
		method: 'POST',
		params: params,
		headers: {
			dataType: 'json'
		},
		success: function(response){
			var result = Ext.JSON.decode(response.responseText);
			Ext.callback(fn,this,[result]);
		}
	});
};

Ext.Ajax.on('requestcomplete', function(conn,response,options){
	ajaxcallback(conn,response,options);
}, this);
Ext.Ajax.on('requestexception', function(conn,response,options){
	ajaxcallback(conn,response,options);
}, this);
function ajaxcallback(conn,response,options){
	if(response.status == 0){
		Pas.msg.error('与服务器失去连接,可能服务器已关闭');
	}else{
		var loginStatus = response.getResponseHeader("loginStatus");
		if(loginStatus=="noLogin"){
			Pas.msg.error('未登录本系统');
		}else if(loginStatus=="accessDenied"){
			Pas.msg.error('你没有访问权限');
		}
	}
}

Ext.apply(Ext.form.field.VTypes, {
	ipv4: function(val, field) {
        return (/^((1?\d?\d|(2([0-4]\d|5[0-5])))\.){3}(1?\d?\d|(2([0-4]\d|5[0-5])))$/).test(val);
    },
    ipv4Text: 'IP地址输入不合法',
    excel:function(val,field){
    	var vl = val.toLowerCase();
    	if(vl.lastIndexOf('.xls') > 0 || vl.lastIndexOf('.xlsx') > 0){
    		return true;
    	}
    	return false;
    },
    excelText:'请选择正确的excel文件',
    mobilephone:function(val,field){  
        try{  
            if(/^(13[0-9]|15[0-9]|18[0-9])[0-9]{8}$/.test(val))
                return true;  
            return false;  
        }catch(e){  
            return false;  
        }  
    },  
    mobilephoneText:'手机号码位数有误或有非法字符'
});