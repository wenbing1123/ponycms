Ext.define('App.view.user.AddView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.UserAddView',
    requires: ['App.ux.DomainComboBox','App.ux.GroupComboBox','App.ux.form.MultiSelect','App.ux.form.ItemSelector'],
    title: '创建用户',
    iconCls: 'add',
    y: 100,
    width: 600,
    height: 530,
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
		        name: 'fullname',
		        fieldLabel: '真实姓名',
		        anchor: '50%'
		    },{ 
		        name: 'email',
		        fieldLabel: 'E-Mail',
		        vtype: 'email',
		        anchor: '50%'
		    },{ 
		        name: 'phone',
		        fieldLabel: '联系电话',
		        vtype: 'mobilephone',
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
		        layout: 'column',
		        xtype: 'panel',
		        border: false,
		        style: {
		        	marginBottom: '5px'
		        },
		        defaults:{
		        	anchor: '50%',
		        	labelAlign: 'right',
		        	labelWidth: 60
		        },
		        items: [{
					xtype: 'domaincombo',
					name: 'domain',
					fieldLabel: '所属域'
				},{
					xtype: 'groupcombo',
					name: 'group',
					fieldLabel: '所属组'
				}],
	            anchor: '100%'
			},{
				xtype: 'itemselector',
				fieldLabel: '拥有角色',
				name: 'roles',
				anchor: '100%',
				height: 210,
				store : Ext.create('Ext.data.Store',{
					fields: ['id','name'],
					autoLoad: true,
					proxy: {
						type: 'ajax',   
				        url: 'user/getRoles.do',
						reader: 'json'
					}
				}),
				valueField: 'id',
	            displayField: 'name'
			}]
		}]
    }],
    buttons: [{text: '保存',action: 'save'},{text: '关闭',action: 'close'}],
    buttonAlign: 'center',
    
    initComponent: function() {
    	this.callParent(arguments);
    }
});