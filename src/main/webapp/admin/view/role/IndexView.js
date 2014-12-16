Ext.define('App.view.role.IndexView', {
    extend: 'Ext.panel.Panel',    
    alias: 'widget.RoleIndexView',
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
        	labelWidth: 30,
        	labelAlign: 'right'
        },
        layout: 'column',
        items :[{
            fieldLabel: '名称',
            name: 'search_LIKES_name',
            width: 150
        },{
            fieldLabel: '类型',
            name: 'search_EQS_type',
            xtype: 'combo',
            store: Ext.create('Ext.data.ArrayStore',{
            	 fields: ['value','label'],
            	 autoLoad: true, 
            	 proxy: {  
                     type: 'ajax',   
                     url: 'role/type.do', 
    			     reader: 'json'    
                 }
            }),
            emptyText: '请选择...',
            displayField: 'label',
            valueField: 'value',
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
    	title: '角色列表',
    	margin: '3 3 3 3',
    	padding: 0,
    	border: false,
    	frame: true,
    	xtype: 'gridpanel',
    	store:'role.RoleStore',
        tbar: [{text:'添加角色',iconCls:'add',action:"add"},{text:'删除角色',iconCls:'delete',action:"delete"}],
        columnLines : true,
        stripeRows: true, //隔行换色
        loadMask: true,
        columns: [
            {xtype: 'rownumberer', text: '序号', width: 36},
            {header: '角色名称', dataIndex: 'name', width: 100},
            {header: '角色类型', dataIndex: 'type', width: 100,renderer:function(value){
            	return lang.message('Role.Type.'+value);
            }},
            {header: '优先级', dataIndex: 'priority', width: 50},
            {header: '管理员', dataIndex: 'isSuper', width: 50, renderer: function(value){
            	if(value==true){
            		return '是';
            	}else{
            		return '否';
            	}
            }},
            {header: '状态', dataIndex: 'status', width: 50,renderer:function(value){
            	if(value==undefined || value==true){
            		return '<div class="success" style="display:block;width:16px;height:16px;"></span>';
            	}else{
            		return '<div class="error" style="display:block;width:16px;height:16px;"></span>';
            	}
            }},
            {header: '描述', dataIndex: 'description', flex: 1},
            {header: '操作', width: 100, renderer: function(){
            	var opt = '<a href="javascript:;" action="update">修改</a>\t';
            	opt += '<a href="javascript:;" action="remove">删除</a>';
            	return opt;
            }}
        ],
        bbar:{
            xtype: 'pagingtoolbar',
            store: 'role.RoleStore',
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