Ext.define('App.view.personal.UpdatePwdView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.PersonalUpdatePwdView',
    title: '修改密码',
    iconCls: 'lock',
    y: 100,
    width: 300,
    height: 200,
    modal: true,
    resizable: false,
    border: false,
    layout: 'fit',
    items: [{
    	xtype: 'form',
    	border: false,
    	bodyPadding: 10,
		defaults: { 
		    margins: '0 0 10 0',
		    xtype: 'textfield',
		    inputType: 'password',
		    labelWidth: 70,
		    labelAlign: 'right'
		},
		layout: 'anchor',
		items: [{
			name: 'oldPassword',
			fieldLabel: '旧密码',
			allowBlank: false,
			blankText: '请输入旧密码'
		},{
			name: 'newPassword',
			fieldLabel: '新密码',
			allowBlank: false,
			blankText: '请输入新密码'
		},{
			fieldLabel: '新密码',
			validator: function(value){
				var pw = this.previousSibling().value;
				if(value != pw){
					return '两次输入的密码不一致';
				}else{
					return true;
				}
			}
		}]
    }],
    buttons: [{text: '保存',action: 'accept'},{text: '关闭',action: 'cannel'}],
    buttonAlign: 'center',
    initComponent: function() {
    	this.callParent(arguments);
    }

});