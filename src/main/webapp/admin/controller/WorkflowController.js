Ext.define('App.controller.WorkflowController', {
    extend: 'Ext.app.Controller',
    
    views: ['workflow.IndexView','workflow.CreateView','workflow.SeeXmlView','workflow.SeeImgView','workflow.SetFormView'],
    
    stores: ['workflow.WorkflowStore','workflow.FieldStore'],
    
    init: function() {
        
        this.control({
        	
        	'WorkflowIndexView': {
        		render: function(gp){ //整个页面渲染完成
        			var gridpanel = gp.down('gridpanel');
        			gridpanel.getStore().load();
        			
        			var search_btn = gp.down('button[action=search]');
        			search_btn.on('click',function(){
        				var form = gp.down('form');
        				if(form.getForm().isValid()){
							gridpanel.getStore().proxy.extraParams = form.getForm().getValues();
							gridpanel.getStore().load();
			    		}
        			});
        			
        			var create_btn = gp.down('button[action=create]');
        			create_btn.on('click',function(){
        				Ext.widget("WorkflowCreateView").show();
        			});
        			
        			//表格事件
                    gridpanel.on('cellclick', function(grid, td, cellIndex, record, tr, rowIndex, e) {
                   		var aTag = e.getTarget('a');
                   		if(aTag != undefined){
	                   		var opt = aTag.attributes['action'].nodeValue;
	                   		if(opt == 'seeXml'){
	                   			var w = Ext.widget("WorkflowSeeXmlView");
	                   			w.show();
	                   			Ext.Ajax.request({
	                   				url: 'workflowController.do?getXml',
	                   				params: {"id": record.data.id},  
	                   	            waitMsg: '加载中...',
	                   	            success: function(response,options){
	                   	            	w.down('textarea').setValue(response.responseText);
	                   	            }
	                   			});
	                   		}
	                   		if(opt == 'seeImg'){
	                   			var w = Ext.widget("WorkflowSeeImgView");
	                   			w.show();
	                   			w.add({
	                   				xtype: 'image',
	                   				src: 'workflowController.do?getImg&id=' + record.data.id
	                   			});
	                   		}
	                   		if(opt == 'defForm'){
	                   			Ext.widget("WorkflowSetFormView").show();
	                   		}
	                   		if(opt == 'delete'){
	                   			App.msg.confirm('确定要删除选定记录？',function(){
	                   				App.ajax('workflowController.do?remove', function(result){
	    								App.msg.tip(result.message);
	    								if(result.success){
	    	        						gridpanel.getStore().load();
	    	        					}
	    							},{'id': record.data.id});
	            				});
	                   		}
                   		}
                    });
        			
        		}
        	},
        	
        	'WorkflowCreateView': {
        		render: function(gp){ //整个页面渲染完成
        			gp.down('button[action=accept]').on('click',function(){
        				var form = gp.down('form');
						if(form.getForm().isValid()){
							form.getForm().submit({
			    				url: 'workflowController.do?saveOrUpdate',
			    				success: function(form, action){
			    					App.msg.tip(action.result.message);
			    					gp.hide();
			    					if(action.result.success){
				    					c.getStore('workflow.WorkflowStore').load();
			    					}
			    				}
			    			});
						}
        			});
        			gp.down('button[action=cancel]').on('click',function(){
        				gp.hide();
        			});
        		}
        	},
        	
        	'WorkflowSeeXmlView': {
        		render: function(gp){ //整个页面渲染完成
        			gp.down('button[action=cancel]').on('click',function(){
        				gp.hide();
        			});
        		}
        	},
        	
        	'WorkflowSeeImgView': {
        		render: function(gp){ //整个页面渲染完成
        			gp.down('button[action=cancel]').on('click',function(){
        				gp.hide();
        			});
        		}
        	}
        	
        });
    }
});