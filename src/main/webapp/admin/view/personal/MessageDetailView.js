Ext.define('App.view.personal.MessageDetailView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.PersonalMessageDetailView',
    title: '查看消息',
    iconCls: 'list',
    y: 100,
    width: 500,
    height: 300,
    modal: true,
    resizable: false,
    border: false,
    layout: 'fit',
    items:[{
    	xtype: 'form',
    	border: false,
    	bodyPadding: 10,
		defaults: { 
		    margins: '0 0 10 0',
		    xtype: 'textfield',
		    labelWidth: 30,
		    labelAlign: 'right',
		    anchor: '50%'
		},
		layout: 'anchor',
		items: [{
			name: 'title',
			fieldLabel: '标题',
			readOnly: true
		},{
			name: 'from',
			fieldLabel: '来源',
			readOnly: true
		},{
			xtype: 'textarea',
			fieldLabel: '内容',
			name: 'content',
			anchor: '100%',
			rows: 11,
			readOnly: true
		}]
    }],
    buttons: [{text: '关闭',action: 'cannel'}],
    buttonAlign: 'center',
    initComponent: function() {
    	this.callParent(arguments);
    }
});
    