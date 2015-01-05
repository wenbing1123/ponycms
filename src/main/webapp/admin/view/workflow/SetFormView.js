Ext.define('App.view.workflow.SetFormView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.WorkflowSetFormView',
    title: '流程表单定义',
    iconCls: 'update',
    y: 60,
    width: 800,
    height: 600,
    modal: true,
    resizable: false,
    border: false,
    layout: 'fit',
    items: [{
    	xtype: 'form',
    	border: true,
    	layout: 'anchor',
    	bodyPadding: 10,
    	defaults: { 
		    margins: '0 0 10 0',
		    xtype: 'textfield',
		    labelWidth: 60,
		    labelAlign: 'right'
		},
		items: [{
			xtype: 'hiddenfield',
			name: 'id'
		},{ 
			xtype: 'combo',
	        name: 'template',
	        fieldLabel: '表单模板',
	        anchor: '65%',
	        store:Ext.create('App.store.workflow.FormTplStore'),
	        valueField: 'value',
	        displayField: 'label'
	    },{
	    	xtype: 'hiddenfield',
	    	name: 'fieldIds'
	    },{
	    	xtype: 'gridpanel',
	    	title: '表单字段',
	    	iconCls: 'list',
	    	margin: '3 3 3 3',
	    	padding: 0,
	    	border: false,
	    	frame: true,
	    	xtype: 'gridpanel',
	    	rootVisible: false,
		    multiSelect: false,
	    	store:'workflow.FieldStore',
	        tbar: [{text:'增加',iconCls:'create',action:"create"},{text:'刷新',iconCls:'refresh',action:"refresh"}],
	        columnLines : true,
	        stripeRows: true, //隔行换色
	        loadMask: true,
	        columns: [
	          {header: '名称', dataIndex: 'name', width: 200,},
	          {header: '标签', dataIndex: 'label', width: 100},
	          {header: '类型', dataIndex: 'type', width: 200},
	          {header: '样式', dataIndex: 'input', width: 100},
	          {header: '操作', renderer: function(val){
	          	var opt = '<a href="javascript:;" action="update">修改</a>';
	          	opt += '&nbsp;&bnsp;<a href="javascript:;" action="delete">删除</div></a>';
	          	opt += '&nbsp;&bnsp;<a href="javascript:;" action="option">条目</div></a>';
	          	return opt;
	          }, width: 40}
	        ]
	    }]
    }],
    buttons: [{text: '保存',action: 'accept'},{text: '关闭',action: 'cancel'}],
    buttonAlign: 'center',
    
    initComponent: function() {
    	this.callParent(arguments);
    }
});