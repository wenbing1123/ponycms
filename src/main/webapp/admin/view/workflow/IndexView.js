Ext.define('App.view.workflow.IndexView', {
    extend: 'Ext.panel.Panel',    
    alias: 'widget.WorkflowIndexView',
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
        	labelWidth: 60,
        	labelAlign: 'right'
        },
        layout: 'column',
        items :[{
            fieldLabel: '流程名称',
            name: 'search_LIKES_name',
            width: 200
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
    	title: '流程列表',
    	margin: '3 3 3 3',
    	padding: 0,
    	border: false,
    	frame: true,
    	xtype: 'gridpanel',
    	store:'workflow.WorkflowStore',
        columnLines : true,
        stripeRows: true, //隔行换色
        loadMask: true,
        tbar: [{text:'部署流程',iconCls:'create',action:'create'}],
        columns: [
            {xtype: 'rownumberer', text: '序号', width: 36},
            {header: '流程名称', dataIndex: 'name', width: 200},
            {header: '流程描述', dataIndex: 'desc', flex: 1},
            {header: '创建时间', dataIndex: 'createtime', width: 150},
            {header: 'Xml', width: 50,renderer: function(){
            	var opt = '<a href="javascript:;" action="seeXml">查看</a>';
              	return opt;
            }},
            {header: '流程图', width: 50,renderer: function(){
            	var opt = '<a href="javascript:;" action="seeImg">查看</a>';
              	return opt;
            }},
            {header: '操作', width: 100,renderer: function(){
            	var opt = '<a href="javascript:;" action="defForm">定义表单</a>';
            	opt += '&nbsp;&nbsp;<a href="javascript:;" action="delete">删除</div></a>';
              	return opt;
            }}
        ],
        bbar:{
            xtype: 'pagingtoolbar',
            store:'workflow.WorkflowStore',
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