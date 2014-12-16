Ext.define('App.view.group.IndexView', {
    extend: 'Ext.panel.Panel',    
    alias: 'widget.GroupIndexView',
    layout: 'border',
    items: [{
    	region: 'west',
    	title: '域列表',
    	width: 230,
    	minWidth: 200,
    	maxWidth: 250,
    	split: true,
        collapsible: true,
        iconCls: 'org',
        tools : [
			{xtype: 'tool', type: 'refresh', handler: function(e, target, header, tool){
			   var panel = header.ownerCt;
			   panel.getStore().load();
			}}
        ],
		margins : '3 0 3 3',
		frame: true,
		xtype: 'treepanel',
    	autoScroll : true,
        rootVisible : false,
        animate: true,
        border: false,
        padding: 0,
        viewConfig:{
            loadingText : "正在加载..."
        },
    	store: 'domain.DomainStore'
    },{
    	region: 'center',
    	title: '组列表',
    	iconCls: 'group',
    	margin: '3 3 3 3',
    	padding: 0,
    	border: false,
    	frame: true,
    	xtype: 'treepanel',
    	rootVisible: false,
	    multiSelect: false,
    	store:'group.GroupStore',
        tbar: [{text:'添加顶级组',iconCls:'add',action:"add"},{text:'刷新组',iconCls:'refresh',action:"refresh"}],
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
          	var opt = '<a href="javascript:;" action="add">添加子组</a>\t';
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