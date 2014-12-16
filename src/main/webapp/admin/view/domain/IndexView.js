Ext.define('App.view.domain.IndexView', {
    extend: 'Ext.panel.Panel',    
    alias: 'widget.DomainIndexView',
	
    layout: 'border',
    items: [{
    	region: 'center',
    	title: '域列表',
    	iconCls: 'org',
    	margin: '3 3 3 3',
    	padding: 0,
    	border: false,
    	frame: true,
    	xtype: 'treepanel',
    	rootVisible: false,
	    multiSelect: false,
    	store: 'domain.DomainStore',
        tbar: [{text:'添加顶级域',iconCls:'add',action:"add"},{text:'刷新域',iconCls:'refresh',action:"refresh"}],
        columnLines : true,
        stripeRows: true, //隔行换色
        loadMask: true,
        columns: [
            {xtype: 'treecolumn', text: '名称', dataIndex: 'text', width: 250},
            {header: '代码', dataIndex: 'attributes', width: 150, renderer: function(attributes){
            	return attributes.code;
            }},
            {header: '排序', dataIndex: 'attributes', width: 50, renderer: function(attributes){
            	return attributes.order;
            }},
            {header: '描述', dataIndex: 'attributes', flex: 1, renderer: function(attributes){
            	return attributes.description;
            }},
            {header: '操作', renderer: function(val){
            	var opt = '<a href="javascript:;" action="add">添加子域</a>\t';
            	opt += '<a href="javascript:;" action="update">编辑</a>\t';
            	opt += '<a href="javascript:;" action="remove">删除</a>';
            	return opt;
            }, width: 150}
        ]
    }],
    
    initComponent: function() {
    	this.callParent(arguments);
    }
});