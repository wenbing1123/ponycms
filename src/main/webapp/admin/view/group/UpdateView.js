Ext.define('App.view.group.UpdateView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.GroupUpdateView',
    title: '更新域',
    iconCls: 'update',
    y: 100,
    width: 500,
    height: 220,
    modal: true,
    resizable: false,
    border: false,
    layout: 'fit',
    items: [{
    	xtype: 'form',
    	border: true,
    	layout: 'anchor',
    	bodyPadding: 10,
    	defaults: { 
		    margins: '0 0 10 0',
		    xtype: 'textfield',
		    labelWidth: 60,
		    labelAlign: 'right'
		},
		items: [{
			name: 'id',
			xtype: 'hiddenfield'
		},{
			name: 'parentId',
			xtype: 'hiddenfield',
			disabled: true
		},{ 
	        name: 'name',
	        fieldLabel: '域名称',
	        allowBlank: false, 
	        blankText: '域名称不能为空',
	        anchor: '50%'
	    },{ 
	        name: 'code',
	        fieldLabel: '域代码',
	        anchor: '50%'
	    },{ 
	    	xtype: 'textareafield',
	        name: 'description',
	        height: 80,
	        fieldLabel: '描述',
	        anchor: '95%'
	    }]
    }],
    buttons: [{text: '保存',action: 'accept'},{text: '关闭',action: 'cancel'}],
    buttonAlign: 'center',
    
    initComponent: function() {
    	this.callParent(arguments);
    }
});