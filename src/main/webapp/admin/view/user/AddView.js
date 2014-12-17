Ext.define('App.view.user.AddView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.UserAddView',
    
    title: '创建用户',
    iconCls: 'add',
    y: 100,
    width: 600,
    height: 500,
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
			xtype: 'fieldset',
			title: '基本信息',
			defaults: { 
			    margins: '0 0 10 0',
			    xtype: 'textfield',
			    labelWidth: 60,
			    labelAlign: 'right'
			},
			items: [{ 
		        name: 'username',
		        fieldLabel: '用户名',
		        allowBlank: false, 
		        blankText: '用户名不能为空',
		        anchor: '50%'
		    },{ 
		    	xtype		: 'fieldcontainer',
		        fieldLabel	: '设置',
		        defaultType	: 'checkboxfield',
		        layout: 'column',
		        items: [{
	                boxLabel  : '是否启用',
	                name      : 'enabled',
	                checked   : true,
	                margin: '0 10 0 0'
	            }],
	            anchor: '100%'
		    },{ 
		        name: 'ipAddress',
		        fieldLabel: '绑定IP',
		        vtype: 'ipv4',
		        anchor: '50%'
		    },{ 
		        name: 'email',
		        fieldLabel: 'E-Mail',
		        vtype: 'email',
		        anchor: '50%'
		    },{ 
		        name: 'phone',
		        fieldLabel: '联系电话',
		        vtype: 'ipv4',
		        anchor: '50%'
		    }]
		},{
			xtype: 'fieldset',
			title: '权限信息',
			defaults: { 
			    margins: '0 0 10 0',
			    xtype: 'textfield',
			    labelWidth: 60,
			    labelAlign: 'right'
			},
			items: [{
				xtype: 'combo',
				fieldLabel: '所属域组',
				anchor: '50%'
			},{
				xtype: 'combo',
				fieldLabel: '拥有角色',
				multiSelect: true,
				anchor: '100%'
			}]
		}]
    }],
    buttons: [{text: '保存',action: 'save'},{text: '关闭',action: 'close'}],
    buttonAlign: 'center',
    
    initComponent: function() {
    	this.callParent(arguments);
    }
});