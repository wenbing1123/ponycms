Ext.define('App.view.personal.DocumentView', {
    extend: 'Ext.panel.Panel',    
    alias: 'widget.PersonalDocumentView',
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
            fieldLabel: '文件名称',
            name: 'search_LIKES_name',
            width: 200
        },{
            fieldLabel: '所属分类',
            name: 'search_EQS_category',
            width: 200,
            xtype: 'combo',
            store: Ext.create('Ext.data.ArrayStore',{
	           	 fields: ['value','label'],
	           	 autoLoad: true, 
	           	 proxy: {  
                    type: 'ajax',   
                    url: 'categoryController.do?listData',
   			     	reader: 'json',
   			     	extraParams: {type : 'file'}
                 }
            }),
            emptyText: '请选择...',
            displayField: 'label',
            valueField: 'value'
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
    	title: '文件列表',
    	margin: '3 3 3 3',
    	padding: 0,
    	border: false,
    	frame: true,
    	xtype: 'gridpanel',
    	store:'file.FileinfoStore',
        tbar: [{text:'删除文件',iconCls:'delete',action:"remove"}],
        columnLines : true,
        stripeRows: true, //隔行换色
        loadMask: true,
        columns: [
            {xtype: 'rownumberer', text: '序号', width: 36},
            {header: '标题', dataIndex: 'title', width: 200},
            {header: '描述', dataIndex: 'description', flex: 1},
            {header: '创建时间', dataIndex: 'createtime', width: 150},
            {header: '状态', dataIndex: 'status', width: 150},
            {header: '流程名称', dataIndex: 'workflow.name', width: 150},
            {header: '附件', width: 50,renderer: function(){
            	var opt = '<a href="javascript:void(0);" action="download">下载</a>';
            	return opt;
            }},
            {header: '审批历史', width: 50,renderer: function(){
            	var opt = '<a href="javascript:void(0);" action="seeApprove">查看</a>';
            	return opt;
            }},
            {header: '操作', width: 50,renderer: function(){
            	var opt = '<a href="javascript:void(0);" action="submit">提交</a>';
            	opt += '&nbsp;&nbsp;<a href="javascript:void(0);" action="delete">删除</a>';
            	return opt;
            }}
        ],
        bbar:{
            xtype: 'pagingtoolbar',
            store: 'file.FileinfoStore',
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