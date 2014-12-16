Ext.define('App.view.dic.IndexView', {
    extend: 'Ext.panel.Panel',    
    alias: 'widget.DicIndexView',
    layout: 'border',
    items: [{
    	region: 'west',
    	title: '分类列表',
    	width: 230,
    	minWidth: 200,
    	maxWidth: 250,
    	split: true,
        collapsible: true,
        iconCls: 'category',
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
    	store: 'category.CategoryStore'
    },{
    	region: 'center',
    	title: '字典列表',
    	iconCls: 'dictionary',
    	margin: '3 3 3 3',
    	padding: 0,
    	border: false,
    	frame: true,
    	xtype: 'gridpanel',
    	rootVisible: false,
	    multiSelect: false,
    	store:'dic.DicStore',
        tbar: [{text:'保存',iconCls:'save',action:"save"},{text:'增加',iconCls:'add',action:"add"},{text:'刷新',iconCls:'refresh',action:"refresh"}],
        columnLines : true,
        stripeRows: true, //隔行换色
        loadMask: true,
        columns: [
          {header: '字典名称', dataIndex: 'name', width: 200},
          {header: '显示顺序', dataIndex: 'order', width: 100},
          {header: '字典描述', dataIndex: 'description', flex: 1},
          {header: '操作', renderer: function(val){
          	var opt = '<a href="javascript:;" action="remove">删除</a>';
          	return opt;
          }, width: 150}
      ]
    }],
    
    initComponent: function() {
    	this.callParent(arguments);
    }
});