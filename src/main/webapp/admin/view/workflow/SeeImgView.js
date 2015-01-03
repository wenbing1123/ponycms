Ext.define('App.view.workflow.SeeImgView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.WorkflowSeeImgView',
    title: '流程图片查看',
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