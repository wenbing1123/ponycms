Ext.define('App.view.user.IndexView', {
    extend: 'Ext.panel.Panel',    
    alias: 'widget.UserIndexView',
    layout: 'border',
    items: [{
    	region: 'north',
    	xtype: 'form',
    	margin: '3 3 3 3',
    	height: 33,
    	frame: true,
    	collapsible: false,
    	defaultType: 'textfield',
        defaults: {
        	labelWidth: 45,
        	labelAlign: 'right'
        },
        layout: 'column',
        items :[{
            fieldLabel: '用户名',
            name: 'search_LIKES_username',
            width: 150
        },{
        	labelWidth: 20,
            fieldLabel: 'Ip',
            name: 'search_LIKES_ipAddress',
            width: 150
        },{
        	margin: '0 0 0 5',
        	xtype: 'button',
        	iconCls: 'search',
        	text: '查询',
        	action: 'search'
        }]
    },{
    	region: 'center',
    	iconCls: 'list',
    	title: '用户列表',
    	margin: '3 3 3 3',
    	padding: 0,
    	border: false,
    	frame: true,
    	xtype: 'gridpanel',
    	store:'user.UserStore',
        tbar: [{text:'添加用户',iconCls:'add',action:"add"},{text:'删除用户',iconCls:'delete',action:"delete"}],
        columnLines : true,
        stripeRows: true, //隔行换色
        loadMask: true,
        columns: [
            {xtype: 'rownumberer', text: '序号', width: 36},
            {header: '用户名', dataIndex: 'username', width: 100},
            {header: '状态', dataIndex: '', width: 50, renderer:function(value){
            	return '<div class="success" style="display:block;width:16px;height:16px;"></span>';
            }},
            {header: '绑定Ip', dataIndex: 'ipAddress', width: 100},
            {header: '最后登录Ip', dataIndex: 'lastLoginIp', width: 100},
            {header: '最后登录时间', dataIndex: 'lastLoginDate', width: 150},
            {header: 'Email', dataIndex: 'description', width: 200},
            {header: '联系电话', dataIndex: 'description', flex: 1},
            {header: '操作', width: 100, renderer: function(){
            	var opt = '<a href="javascript:;" action="update">修改</a>\t';
            	opt += '<a href="javascript:;" action="remove">删除</a>';
            	return opt;
            }}
        ],
        bbar:{
            xtype: 'pagingtoolbar',
            store: 'user.UserStore',
            displayInfo: true
        }
    }],
    
    initComponent: function() {
    	Ext.apply(this.items[1],{
    		selModel: Ext.create('Ext.selection.CheckboxModel',{
    			mode: 'MULTI'
    		})
    	});
    	this.callParent(arguments);
    }
});