Ext.define('App.view.workflow.SetFormView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.WorkflowSetFormView',
    title: '流程动态表单配置',
    iconCls: 'update',
    y: 100,
    width: 800,
    height: 600,
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
	        name: 'name',
	        fieldLabel: '流程名称',
	        allowBlank: false, 
	        blankText: '流程名称不能为空',
	        anchor: '65%'
	    },{
	    	xtype: 'filefield',
	    	name: 'xml',
	    	fieldLabel: '流程定义',
	    	allowBlank: false, 
	        blankText: '流程定义不能为空',
	    	anchor: '65%',
	    	buttonConfig: {
	    		text: '浏览...'
	    	}
	    },{
	    	xtype: 'filefield',
	    	name: 'img',
	    	fieldLabel: '流程图片',
	    	anchor: '65%',
	    	buttonConfig: {
	    		text: '浏览...'
	    	}
	    },{
	    	xtype: 'textarea',
	    	name: 'desc',
	        fieldLabel: '流程描述',
	        anchor: '95%',
	        rows: 7
	    }]
    }],
    buttons: [{text: '保存',action: 'accept'},{text: '关闭',action: 'cancel'}],
    buttonAlign: 'center',
    
    initComponent: function() {
    	this.callParent(arguments);
    }
});