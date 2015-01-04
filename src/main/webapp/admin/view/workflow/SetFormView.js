Ext.define('App.view.workflow.SetFormView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.WorkflowSetFormView',
    title: '流程表单定义',
    iconCls: 'update',
    y: 60,
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
			xtype: 'hiddenfield',
			name: 'id'
		},{ 
			xtype: 'combo',
	        name: 'template',
	        fieldLabel: '表单模板',
	        anchor: '65%',
	        store:Ext.create('App.store.workflow.FormTplStore'),
	        valueField: 'value',
	        displayField: 'label'
	    }]
    }],
    buttons: [{text: '保存',action: 'accept'},{text: '关闭',action: 'cancel'}],
    buttonAlign: 'center',
    
    initComponent: function() {
    	this.callParent(arguments);
    }
});