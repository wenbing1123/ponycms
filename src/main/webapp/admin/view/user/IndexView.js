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
            fieldLabel: '状态',
            name: 'status',
            width: 150,
            xtype: 'combo',
            store: Ext.create('Ext.data.ArrayStore',{
            	fields: ['value','label'],
            	data: [[1,'启用'],[2,'锁定'],[3,'禁用']]
            }),
            valueField: 'value',
            displayField: 'label',
            emptyText: '请选择...'
        },{
        	xtype: 'hiddenfield',
        	name: 'search_EQB_enabled'
        },{
        	xtype: 'hiddenfield',
        	name: 'search_EQB_locked'
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
            {header: '状态', dataIndex: 'status', width: 50, renderer:function(value){
            	if(value==1)
            		return '<div class="icon success"></span>';
            	if(value==2)
            		return '<div class="icon lock"></span>';
            	if(value==3)
            		return '<div class="icon error"></span>';
            }},
            {header: '绑定Ip', dataIndex: 'ipAddress', width: 100},
            {header: 'Email', dataIndex: 'email', width: 150},
            {header: '真实姓名', dataIndex: 'fullname', width: 100},
            {header: '联系电话', dataIndex: 'phone', width: 100},
            {header: '最后登录Ip', dataIndex: 'lastLoginIp', width: 100},
            {header: '最后登录时间', dataIndex: 'lastLoginDate', flex: 1},
            {header: '操作', width: 120, renderer: function(){
            	var opt = '<a href="javascript:;" action="reset">密码重置</a>\t';
            	opt += '<a href="javascript:;" action="update">修改</a>\t';
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