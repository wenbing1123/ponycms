Ext.define('App.view.cfg.IndexView', {
    extend: 'Ext.form.Panel',    
    alias: 'widget.CfgIndexView',
    layout: {
    	type: 'hbox',
    	pack: 'center',
    	align: 'top',
    	padding: 30,
    },
    border: false,
    tbar: [{text:'保存',iconCls:'save',action:'save'},'-',{text:'刷新',iconCls:'refresh',action:'refresh'}],
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
    			name: 'siteName',
    			fieldLabel: '站点名称'
    		},{
    			name: 'siteUrl',
    			fieldLabel: '站点Url'
    		},{
    			name: 'logo',
    			fieldLabel: '站点Logo'
    		},{
    			name: 'address',
    			fieldLabel: '公司地址'
    		},{
    			name: 'phone',
    			fieldLabel: '联系电话'
    		},{
    			name: 'zipcode',
    			fieldLabel: '邮政编码'
    		},{
    			name: 'email',
    			fieldLabel: '电子邮箱'
    		},{
    			name: 'certtext',
    			fieldLabel: '备案号'
    		}]
    	},{
    		title: '邮件信息',
    		layout: 'anchor',
    		bodyPadding: '10px',
    		defaults: {
    			xtype: 'textfield',
    			labelAlign: 'right',
    			labelWidth:	100,
    			anchor: '70%'
    		},
    		items: [{
    			name: 'smtpFromMail',
    			fieldLabel: '邮件发送者'
    		},{
    			name: 'smtpHost',
    			fieldLabel: '邮件发送主机'
    		},{
    			name: 'smtpPort',
    			fieldLabel: '邮件发送端口'
    		},{
    			name: 'smtpUsername',
    			fieldLabel: '邮件发送者用户名'
    		},{
    			name: 'smtpPassword',
    			fieldLabel: '邮件发送者密码'
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