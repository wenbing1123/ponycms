Ext.define('App.view.cfg.IndexView', {
    extend: 'Ext.panel.Panel',    
    alias: 'widget.CfgIndexView',
    layout: 'border',
    border: false,
    items: [{
    	region: 'center',
    	margin: '3 3 3 3',
    	frame: true,
    	border: false,
    	xtype: 'tabpanel',
    	tbar: [{text:'保存',iconCls:'save',action:'save'}],
    	items: [{
    		title: '基本设置',
    		xtype: 'propertygrid',
    		border: false,
    		source: {
        		"(name)": "My Object",
                "Created": Ext.Date.parse('10/15/2006', 'm/d/Y'),
                "Available": false,
                "Version": 0.01,
                "Description": "A test object"
        	}
    	},{
    		title: '邮件设置',
    		xtype: 'propertygrid',
    		border: false,
    		source: {}
    	}]
    }],
    
    initComponent: function() {
    	this.callParent(arguments);
    }

});