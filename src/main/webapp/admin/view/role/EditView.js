Ext.define('App.view.role.EditView', {
    extend: 'Ext.window.Window',    
    alias: 'widget.RoleEditView',
    
    title: '编辑角色',
    iconCls: 'edit',
    y: 100,
    width: 600,
    height: 400,
    modal: true,
    resizable: false,
    layout: 'fit',
    items: [{
    	xtype: 'form',
    	border: false,
    	layout: 'fit',
		items: [{
			xtype: 'tabpanel',
			border: false,
			items: [{
				title: '基本信息',
				border: false,
				padding: '10,15',
				defaults: { 
				    margins: '0 0 10 0',
				    xtype: 'textfield',
				    labelWidth: 60,
				    labelAlign: 'right'
				},
				layout: 'anchor',
				items: [{
					name: 'id',
					xtype: 'hiddenfield'
				},{ 
			        name: 'name',
			        fieldLabel: '角色名称',
			        allowBlank: false, 
			        blankText: '角色名称不能为空',
			        anchor: '50%'
			    },{
		            fieldLabel: '角色类型',
		            name: 'type',
		            xtype: 'combo',
		            allowBlank: false, 
			        blankText: '角色不能为空',
		            store: Ext.create('Ext.data.ArrayStore',{
		            	 fields: ['value','label'],
		            	 autoLoad: true, 
		            	 proxy: {  
		                     type: 'ajax', 
		                     url: 'role/type.do'    
		                 }, 
		                 reader: 'json'
		            }),
		            emptyText: '请选择...',
		            displayField: 'label',
		            valueField: 'value',
		            anchor: '50%'
		        },{ 
			        xtype		: 'fieldcontainer',
			        fieldLabel	: '设置',
			        defaultType	: 'checkboxfield',
			        layout: 'column',
			        items: [{
		                boxLabel  : '是否为超级管理员',
		                name      : 'isSuper',
		                checked   : false,
		                margin: '0 10 0 0'
		            },{
		                boxLabel  : '是否启用',
		                name      : 'status',
		                checked   : true
		            }],
		            anchor: '100%'
			    },{ 
			        name: 'priority',
			        fieldLabel: '优先级',
			        value: 10,
			        anchor: '50%'
			    },{ 
			        name: 'description',
			        xtype: 'textareafield',
			        fieldLabel: '描述',
			        anchor: '90%'
			    }]
			},{
				title: '权限信息',
				border: false,
				xtype: 'treepanel',
				tbar: [{
    	    		iconCls: 'accept',
    	    		text: '全选',
    	    		action: 'check'
    	    	},'-',{
    	    		iconCls: 'reverse',
    	    		text: '反选',
    	    		action: 'reverse'
    	    	},'-',{
    	    		iconCls: 'cancel',
    	    		text: '不选',
    	    		action: 'uncheck'
    	    	},'->',{
    	    		iconCls: 'expand',
    	    		tooltip: '全部展开',
    	    		action: 'expand'
    	    	},{
    	    		iconCls: 'collapse',
    	    		tooltip: '全部折叠',
    	    		action: 'collapse'
    	    	}],
				rootVisible: true,
				store: Ext.create('Ext.data.TreeStore',{
					fields: ['id','text','expanded','attributes'],
	            	autoLoad: false, 
	            	proxy: {  
	                    type: 'ajax', 
	                    url: 'role/module.do'    
	                }, 
	                reader: 'json',
	                root: {
	                	id: null,
	                    text: '系统模块',
	                    expanded: true,
	                },
				})
			}]
		}]
    }],
    buttons: [{text: '保存',action: 'save'},{text: '关闭',action: 'close'}],
    buttonAlign: 'center',
    
    initComponent: function() {
    	this.callParent(arguments);
    }
});