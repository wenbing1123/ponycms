Ext.define('App.view.personal.ProfileView', {
    extend: 'Ext.form.Panel',    
    alias: 'widget.PersonalProfileView',
    layout: {
    	type: 'hbox',
    	pack: 'center',
    	align: 'top',
    	padding: 30,
    },
    border: false,
    tbar: [{text:'刷新',iconCls:'refresh',action:'refresh'},'-',{text:'修改密码',iconCls:'lock',action:'updatePwd'}],
    items: [{
    	xtype: 'tabpanel',
    	width: 500,
        height: 400,
    	items: [{
    		title: '基本信息',
    		layout: 'anchor',
    		bodyPadding: '10px',
    		defaults: {
    			xtype: 'textfield',
    			labelAlign: 'right',
    			labelWidth:	100,
    			anchor: '70%'
    		},
    		items: [{
    			name: 'username',
    			fieldLabel: '用户名',
    			readOnly: true
    		},{
    			name: 'fullname',
    			fieldLabel: '姓名',
    			readOnly: true
    		},{
    			name: 'ipAddress',
    			fieldLabel: 'Ip',
    			readOnly: true
    		},{
    			name: 'email',
    			fieldLabel: '电子邮箱',
    			readOnly: true
    		},{
    			name: 'phone',
    			fieldLabel: '联系电话',
    			readOnly: true
    		},{
    			name: 'lastLoginIp',
    			fieldLabel: '最后登录Ip',
    			readOnly: true
    		},{
    			name: 'lastLoginDate',
    			fieldLabel: '最后登录时间',
    			readOnly: true
    		}]
    	}]
    }],
    
    initComponent: function() {
    	this.callParent(arguments);
    },
    
    setValues: function(values) {
    	this.getForm().setValues(values);
    }

});