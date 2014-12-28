Ext.define('App.controller.DomainController', {
    extend: 'Ext.app.Controller',

    views: [
       'domain.IndexView','domain.CreateView','domain.UpdateView'
    ],
    
    stores: ['domain.DomainStore'],
    
    init: function() {
    	
    	var c = this;
    	
    	this.control({
        	
        	'DomainIndexView': {
        		render: function(gp){ 
        			
        			var treepanel = gp.down('treepanel');
        			treepanel.getStore().load();
        			
        			gp.down('button[action=create]').on('click',function(){
        				Ext.widget("DomainCreateView").show();
                    });
                    gp.down('button[action=refresh]').on('click',function(){
                       treepanel.getStore().load();
                    });
                    
                    treepanel.on('itemcontextmenu', function(view, record, items, index, e){
                    	e.preventDefault();  
                        e.stopEvent();
                        var menu = new Ext.menu.Menu({  
                            floating: true,  
                            items: [{  
                                text: "添加子域",
                                iconCls: 'create',
                                handler: function(){
                                	var w = Ext.widget("DomainCreateView");
    	                    		w.show();
    	                    		var f = w.down('form');
    	                    		f.getForm().setValues({
    	                    			parentId: record.data.id,
    	                    			parentName: record.data.text
    	                    		});
                                }
                            },{  
                                text: "修改",
                                iconCls: 'update',
                                handler: function(){
                                	var w = Ext.widget("DomainUpdateView");
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
                                	App.msg.confirm('将删除域及子域，确定删除记录？',function(){
    	                   				App.ajax('domainController.do?remove', function(result){
    	    								App.msg.tip(result.message);
    	    								if(result.success){
    	    									var pId = record.data.parentId;
    	    									var store = treepanel.getStore();
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
        	
        	'DomainCreateView': {
        		render: function(gp){ 
        			gp.down('button[action=accept]').on('click',function(){
        				var form = gp.down('form');
						if(form.getForm().isValid()){
							var pId = form.getForm().findField('parentId').getValue();
							form.getForm().submit({
			    				url: 'domainController.do?save',
			    				success: function(form, action){
			    					App.msg.tip(action.result.message);
			    					gp.hide();
			    					if(action.result.success){
				    					var store = c.getStore('domain.DomainStore');
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
        	
        	'DomainUpdateView': {
        		render: function(gp){ 
        			gp.down('button[action=accept]').on('click',function(){
        				var form = gp.down('form');
						if(form.getForm().isValid()){
							var pId = form.getForm().findField('parentId').getValue();
							form.getForm().submit({
			    				url: 'domainController.do?update',
			    				success: function(form, action){
			    					App.msg.tip(action.result.message);
			    					gp.hide();
			    					if(action.result.success){
				    					var store = c.getStore('domain.DomainStore');
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