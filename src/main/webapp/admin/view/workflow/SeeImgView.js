Ext.define('App.view.workflow.SeeImgView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.WorkflowSeeImgView',
    title: '流程图片查看',
    iconCls: 'list',
    y: 60,
    width: 800,
    height: 500,
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