Ext.define('App.store.user.UserStore', {
    extend: 'Ext.data.Store',
    fields: ['id','username','enabled','locked','ipAddress','fullname','email','phone','lastLoginIp','lastLoginDate'
       ,{name:'status',convert:function(value,record){
	    	var enabled = record.get('enabled');
	    	var locked = record.get('locked');
	    	var status = 1;
	    	if(enabled){
	    		if(locked){
	    			status = 2;
	    		}
	    	}else{
	    		status = 3;
	    	}
	    	return status;
       }
    }],
    pageSize: App.pageSize,
    autoLoad: false, //当初始化时不加自动载数据,手动加载数据
    proxy: {
        type: 'ajax',
        url: 'user/datagrid.do',
        actionMethods: {read:'POST'},
        reader: {
            type:'json',
            totalProperty:'total',
            root:'rows'
        },
        extraParams: {}
    }
});