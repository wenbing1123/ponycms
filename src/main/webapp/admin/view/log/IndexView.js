Ext.define('App.view.log.IndexView', {
    extend: 'Ext.panel.Panel',    
    alias: 'widget.LogIndexView',
    layout: 'border',
    requires: ['App.ux.datetime.DateTimePicker','App.ux.datetime.DateTimeField'],
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
            fieldLabel: '用户',
            name: 'search_LIKES_user',
            width: 150
        },{
        	id: 'sdate',
        	xtype: 'datetimefield',
        	labelWidth: 60,
            fieldLabel: '时间 从',
            name: 'search_GED_createtime',
            width:205,
            format: 'Y-m-d H:i:s',
            submitFormat: 'Y-m-d H:i:s',
            maxValue: new Date().format("yyyy-MM-dd HH:mm:ss"),
            value: Ext.Date.add(new Date(), Ext.Date.DAY, -1),
            listeners: {
                'select': function () {
                    var start = Ext.getCmp('sdate').getValue();
                    Ext.getCmp('edate').setMinValue(start);
                    var endDate = Ext.getCmp('edate').getValue();
                    if (start > endDate) {
                        Ext.getCmp('edate').setValue(start);
                    }
                }
            }
        },{
        	id: 'edate',
        	xtype: 'datetimefield',
        	labelWidth: 20,
            fieldLabel: '到',
            name: 'search_LED_createtime',
            width:165,
            format: 'Y-m-d H:i:s',
            submitFormat: 'Y-m-d H:i:s',
            maxValue: new Date().format("yyyy-MM-dd HH:mm:ss"),
            value: new Date(),
            listeners: {
                select: function () {
                    var start = Ext.getCmp('sdate').getValue();
                    var endDate = Ext.getCmp('edate').getValue();
                    if (start > endDate) {
                        Ext.getCmp('sdate').setValue(endDate);
                    }
                }
            }
        },{
            fieldLabel: 'Ip',
            name: 'search_EQS_ip',
            vtype: 'ipv4',
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
    	title: '日志列表',
    	margin: '3 3 3 3',
    	padding: 0,
    	border: false,
    	frame: true,
    	xtype: 'gridpanel',
    	store:'log.LogStore',
        tbar: [{text:'删除日志',iconCls:'delete',action:"delete"}],
        columnLines : true,
        stripeRows: true, //隔行换色
        loadMask: true,
        columns: [
            {xtype: 'rownumberer', text: '序号', width: 36},
            {header: '操作', dataIndex: 'operate', width: 100},
            {header: '用户', dataIndex: 'user', width: 100},
            {header: '时间', dataIndex: 'createtime', width: 150},
            {header: 'Ip', dataIndex: 'ip', width: 150},
            {header: 'Url', dataIndex: 'url', width: 150},
            {header: '状态', dataIndex: 'success', width: 40, renderer: function(value){
            	if(value==undefined || value==true){
            		return '<div class="success" style="display:block;width:16px;height:16px;"></span>';
            	}else{
            		return '<div class="warn" style="display:block;width:16px;height:16px;"></span>';
            	}
            }},
            {header: '详细', dataIndex: 'message', flex: 1}
        ],
        bbar:{
            xtype: 'pagingtoolbar',
            store: 'log.LogStore',
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