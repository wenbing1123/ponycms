Ext.define('App.controller.GroupController', {
    extend: 'Ext.app.Controller',

    views: [
       'group.IndexView','group.CreateView','group.UpdateView'
    ],
    
    stores: ['domain.DomainStore','group.GroupStore'],
    
    init: function() {
    	
    	var c = this;
    	c.domainId = null;
    	
    	this.control({
        	
        	'GroupIndexView': {
        		render: function(gp){ 
        			
        			var treepanel = gp.down('treepanel[region=west]');
        			treepanel.getStore().load();
        			
        			var gridpanel = gp.down('treepanel[region=center]');
        			treepanel.on('itemclick',function(view,record){
        				c.domainId = record.data.id;
        				Ext.apply(gridpanel.getStore().proxy.extraParams, {domainId: record.data.id});
        				gridpanel.getStore().load();
        				gridpanel.setTitle('组列表【'+record.data.text+'】');
        			});
        			
        			gp.down('button[action=create]').on('click',function(){
                        if(c.domainId != undefined){
                        	var w = Ext.widget("GroupCreateView");
                    		w.show();
                    		var f = w.down('form');
                    		f.getForm().setValues({
                    			domain: c.domainId
                    		});
                        }else{
                        	App.msg.alert('请从左侧选择域');
                        }
                    });
        			
                    gp.down('button[action=refresh]').on('click',function(){
                    	 if(c.domainId != undefined){
                    		 gridpanel.getStore().load();
                         }else{
                         	App.msg.alert('请从左侧选择域');
                         }
                    });
                    
                    gridpanel.on('itemcontextmenu', function(view, record, items, index, e){
                     	e.preventDefault();  
                         e.stopEvent();
                         var menu = new Ext.menu.Menu({  
                             floating: true,  
                             items: [{  
                                 text: "添加子组",
                                 iconCls: 'create',
                                 handler: function(){
                                 	var w = Ext.widget("GroupCreateView");
     	                    		w.show();
     	                    		var f = w.down('form');
     	                    		f.getForm().setValues({
     	                    			domain: c.domainId,
     	                    			parentId: record.data.id,
     	                    			parentName: record.data.text
     	                    		});
                                 }
                             },{  
                                 text: "修改",
                                 iconCls: 'update',
                                 handler: function(){
                                 	var w = Ext.widget("GroupUpdateView");
     	                    		w.show();
     	                    		var f = w.down('form');
     	                    		f.getForm().setValues({
     	                    			id: record.data.id,
     	                    			name: record.data.text,
     	                    			code: record.data.attributes.code,
     	                    			description: record.data.attributes.description
     	                    		});
     	                    		f.loadRecord(record);
                                 }
                             },{  
                                 text: "删除",
                                 iconCls: 'delete',
                                 handler: function(){
                                 	App.msg.confirm('将删除域及子组，确定删除记录？',function(){
     	                   				App.ajax('groupController.do?remove', function(result){
     	    								App.msg.tip(result.message);
     	    								if(result.success){
     	    									var pId = record.data.parentId;
     	    									var store = gridpanel.getStore();
     	    									if(pId==null || pId==''){
     	    										store.load();
     	    									}else{
     	    										var pNode = store.getNodeById(pId);
     	    										pNode.set('leaf',false);
     	    			    						pNode.set('expanded',false);
     	    			    						pNode.set('loading',false);
     	    			    						pNode.set('loaded',false);
     	    			    						pNode.expand();
     	    			    						if(!pNode.hasChildNodes()){
     	    			    							pNode.set('leaf',true);
     	    			    						}
     	    									
     	    									}
     	    	        					}
     	    							},{'ids': record.data.id});
     	            				});
                                 }
                             }]
                         });
                         menu.showAt(e.getXY());
                     });
        		}
        	},
        	
        	'GroupCreateView': {
        		render: function(gp){ 
        			gp.down('button[action=accept]').on('click',function(){
        				var form = gp.down('form');
						if(form.getForm().isValid()){
							var pId = form.getForm().findField('parentId').getValue();
							form.getForm().submit({
			    				url: 'groupController.do?save',
			    				success: function(form, action){
			    					App.msg.tip(action.result.message);
			    					gp.hide();
			    					if(action.result.success){
				    					var store = c.getStore('group.GroupStore');
				    					if(pId==null || pId==''){
				    						store.load();
				    					}else{
				    						var pNode = store.getNodeById(pId);
				    						pNode.set('leaf',false);
				    						pNode.set('expanded',false);
				    						pNode.set('loading',false);
				    						pNode.set('loaded',false);
				    						pNode.expand();
				    					}
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
        	
        	'GroupUpdateView': {
        		render: function(gp){ 
        			gp.down('button[action=accept]').on('click',function(){
        				var form = gp.down('form');
						if(form.getForm().isValid()){
							var pId = form.getForm().findField('parentId').getValue();
							form.getForm().submit({
			    				url: 'groupController.do?update',
			    				success: function(form, action){
			    					App.msg.tip(action.result.message);
			    					gp.hide();
			    					if(action.result.success){
				    					var store = c.getStore('group.GroupStore');
				    					if(pId==null || pId==''){
				    						store.load();
				    					}else{
				    						var pNode = store.getNodeById(pId);
				    						pNode.set('leaf',false);
				    						pNode.set('expanded',false);
				    						pNode.set('loading',false);
				    						pNode.set('loaded',false);
				    						pNode.expand();
				    					}
			    					}
			    				}
			    			});
						}
        			});
        			gp.down('button[action=cancel]').on('click',function(){
        				gp.hide();
        			});
        		}
        	}
        
    	});
    }
});