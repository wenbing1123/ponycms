Ext.define('App.view.domain.AddView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.DomainAddView',
    title: '创建域',
    iconCls: 'add',
    y: 100,
    width: 500,
    height: 240,
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
			name: 'parentId',
			xtype: 'hiddenfield'
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
	        name: 'order',
	        fieldLabel: '排序',
	        anchor: '50%'
	    },{ 
	    	xtype: 'textareafield',
	        name: 'description',
	        height: 80,
	        fieldLabel: '描述',
	        anchor: '95%'
	    }]
    }],
    buttons: [{text: '保存',action: 'ok'},{text: '关闭',action: 'close'}],
    buttonAlign: 'center',
    
    initComponent: function() {
    	this.callParent(arguments);
    }
});