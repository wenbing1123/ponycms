Ext.define('App.view.personal.MessageView', {
    extend: 'Ext.panel.Panel',    
    alias: 'widget.PersonalMessageView',
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
        	labelWidth: 40,
        	labelAlign: 'right'
        },
        layout: 'column',
        items :[{
            fieldLabel: '标题',
            name: 'search_LIKES_title',
            width: 150
        },{
            fieldLabel: '来源',
            name: 'search_LIKES_from',
            width: 150
        },{
        	xtype: 'hiddenfield',
        	name: 'search_EQB_ifRead',
        	value: null
        },{
        	xtype: 'radiogroup',
        	fieldLabel: '标记',
        	columns: 3,
            vertical: true,
        	items: [
        	  {boxLabel: '全部', name: 'ifRead', inputValue: 'all', margin: '0 10 0 0', checked: true},
        	  {boxLabel: '已读', name: 'ifRead', inputValue: 'true', margin: '0 10 0 0'},
        	  {boxLabel: '未读', name: 'ifRead', inputValue: 'false'}
        	],
        	width: 210
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
    	title: '消息列表',
    	margin: '3 3 3 3',
    	padding: 0,
    	border: false,
    	frame: true,
    	xtype: 'gridpanel',
    	store: 'message.MessageStore',
        tbar: [{text:'标记为已读',iconCls:'email_open',action:"ifRead"},'-',{text:'删除消息',iconCls:'delete',action:"delete"}],
        columnLines : true,
        stripeRows: true, //隔行换色
        loadMask: true,
        columns: [
            {xtype: 'rownumberer', text: '序号', width: 36},
            {header: '标题', dataIndex: 'title', width: 200},
            {header: '已读', dataIndex: 'ifRead', width: 50, renderer: function(value){
            	if(value){
            		return '<div class="icon email_open"></div>';
            	}else{
            		return '<div class="icon email"></div>';
            	}
            }},
            {header: '来源', dataIndex: 'from', width: 100},
            {header: '内容', dataIndex: 'content', flex: 1},
            {header: '创建时间', dataIndex: 'createtime', width: 150},
            {header: '操作', width: 50,renderer: function(){
            	var opt = '<a href="javascript:void(0);" action="detail">查看</a>';
            	return opt;
            }}
        ],
        bbar:{
            xtype: 'pagingtoolbar',
            store: 'message.MessageStore',
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