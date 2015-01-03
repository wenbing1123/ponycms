Ext.define('App.view.workflow.SeeXmlView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.WorkflowSeeXmlView',
    title: '流程定义查看',
    iconCls: 'list',
    iconCls: 'list',
    y: 100,
    width: 600,
    height: 400,
    modal: true,
    resizable: false,
    border: false,
    layout: 'fit',
    buttons: [{text: '关闭',action: 'cancel'}],
    buttonAlign: 'center',
    
    initComponent: function() {
    	this.callParent(arguments);
    }
});