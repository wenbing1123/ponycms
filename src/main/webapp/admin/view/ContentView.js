Ext.define('App.view.ContentView', {
    extend: 'Ext.tab.Panel',    
    alias: 'widget.contentview',
    requires: ['App.ux.portal.PortalPanel', 'App.ux.portal.PortalColumn'],
    getTools: function(){
        return [{
            xtype: 'tool',
            type: 'gear',
            handler: function(e, target, header, tool){
                var portlet = header.ownerCt;
                portlet.setLoading('加载中...');
                Ext.defer(function() {
                    portlet.setLoading(false);
                }, 2000);
            }
        }];
    },
    initComponent: function() {
    	Ext.apply(this, {
    		items: [{
    	    	iconCls: 'home',
    	    	title: '我的桌面',
    	    	xtype: 'portalpanel',
    	    	tbar: [{
    	    		iconCls: 'save',
    	    		text: '保存'
    	    	},'-',{
    	    		iconCls: 'add',
    	    		text: '配置'
    	    	}],
    			items: [{
    	            id: 'col-1',
    	            items: [{
    	            	iconCls: 'list',
    	                title: '通知公告',
    	                tools: this.getTools(),
    	                closable: false,
    	                collapsible: false,
    	                height: 250
    	            }]
    	        },{
    	            id: 'col-2',
    	            items: [{
    	            	iconCls: 'list',
    	                title: '待办任务',
    	                tools: this.getTools(),
    	                closable: false,
    	                collapsible: false,
    	                height: 250
    	            }]
    	        },{
    	            id: 'col-3',
    	            items: [{
    	            	iconCls: 'list',
    	                title: '我的消息',
    	                tools: this.getTools(),
    	                closable: false,
    	                collapsible: false,
    	                height: 250
    	            }]
    	        }]
    	    }]
    	});
    	this.callParent(arguments);
    }
    
});