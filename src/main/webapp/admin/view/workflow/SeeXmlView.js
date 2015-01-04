Ext.define('App.view.workflow.SeeXmlView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.WorkflowSeeXmlView',
    title: '流程定义查看',
    iconCls: 'list',
    y: 60,
    width: 800,
    height: 500,
    modal: true,
    resizable: false,
    border: false,
    layout: 'fit',
    items: [{
    	xtype: 'textarea',
    	readOnly: true,
    	autoScroll: true
    }],
    buttons: [{text: '关闭',action: 'cancel'}],
    buttonAlign: 'center',
    
    initComponent: function() {
    	this.callParent(arguments);
    }
});