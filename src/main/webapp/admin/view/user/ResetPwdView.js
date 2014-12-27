Ext.define('App.view.user.ResetPwdView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.UserResetPwdView',
    title: '重置密码',
    iconCls: 'reset',
    y: 100,
    width: 300,
    height: 120,
    modal: true,
    resizable: false,
    border: false,
    layout: 'fit',
    items: [{
    	xtype: 'form',
    	border: true,
    	layout: 'anchor',
    	bodyPadding: 10,
		items: [{
			xtype: 'hiddenfield',
			name: 'id'
		},{
			xtype: 'combo',
			name: 'random',
			fieldLabel: '重置为',
			labelAlign: 'right',
			labelWidth: 60,
			anchor: '90%',
			allowBlank: false,
			blankText: '请选择密码重置方式',
			store: Ext.create('Ext.data.ArrayStore',{
				fields: ['value','label'],
				data: [[false,'默认密码'],[true,'随机密码']]
			}),
			valueField: 'value',
			displayField: 'label',
			value: false
		},{
			xtype: 'label'
		}]
    }],
    buttons: [{text: '确定',action: 'ok'},{text: '关闭',action: 'close'}],
    buttonAlign: 'center',
    
    initComponent: function() {
    	this.callParent(arguments);
    }
});