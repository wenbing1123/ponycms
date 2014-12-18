Ext.define('App.controller.RoleController', {
    extend: 'Ext.app.Controller',
    
    views: ['role.IndexView','role.AddView','role.EditView'],
    stores: ['role.RoleStore'],
    models: [],
    
    init: function() {
        
    	var c = this;
    	
        this.control({
        	
        	'RoleIndexView': {
        		render: function(gp){ //整个页面渲染完成
        			var gridpanel = gp.down('gridpanel');
        			gridpanel.getStore().load();
        			
        			var search_btn = gp.down('button[action=search]');
        			search_btn.on('click',function(){
        				var form = gp.down('form');
        				if(form.getForm().isValid()){
							gridpanel.getStore().proxy.extraParams = form.getForm().getValues( );
							gridpanel.getStore().load();
			    		}
        			});
        			
        			var add_btn = gp.down('button[action=add]');
        			add_btn.on('click',function(){
        				Ext.widget("RoleAddView").show();
        			});
        			
        			var delete_btn = gp.down('button[action=delete]');
        			delete_btn.on('click',function(){
        				var r = gridpanel.getSelectionModel().getSelection();
                   		if(r && r.length > 0){
                   			App.msg.confirm('确定要删除选定记录？',function(){
    							var ids = '';
    							for(var i=0;i<r.length;i++){
    								ids += r[i].data.id;
    								ids += ',';
    							}
    							ids = ids.substring(0,ids.length-1);
    							App.ajax('role/remove.do', function(result){
    								App.msg.tip(result.message);
    								if(result.success){
    	        						gridpanel.getStore().load();
    	        					}
    							},{'ids': ids});
            				});
                   		}else{
                   			App.msg.alert('请选择要删除的记录!');
                   		}
        			});
        			
        			//表格事件
                    gridpanel.on('cellclick', function(grid, td, cellIndex, record, tr, rowIndex, e) {
                   		var aTag = e.getTarget('a');
                   		if(aTag != undefined){
	                   		var opt = aTag.attributes['action'].nodeValue;
	                   		if(opt == 'update'){
	                   			c.roleId = record.data.id;
	                   			var w = Ext.widget("RoleEditView");
	                   			w.show();
	                   			w.down('form').loadRecord(record);
	                   		}
	                   		if(opt == 'remove'){
	                   			App.msg.confirm('确定要删除选定记录？',function(){
	                   				App.ajax('role/remove.do', function(result){
	    								App.msg.tip(result.message);
	    								if(result.success){
	    	        						gridpanel.getStore().load();
	    	        					}
	    							},{'ids': record.data.id});
	            				});
	                   		}
                   		}
                    });
        			
        		}
        	},
        	
        	'RoleAddView': {
        		render: function(gp){ //整个页面渲染完成
        			var treepanel = gp.down('treepanel');
        			treepanel.getStore().load();
        			
        			var root = treepanel.getRootNode();
        			//业务逻辑，选中节点时，不选中子节点，选中父节点;不选中节点时，不选中子节点，不选中父节点
        			treepanel.on('checkchange',function(node, checked){ //监听事件
        				var parentNode = node.parentNode;
        				if(!Ext.isEmpty(parentNode)) {   
        					if(checked == true){
				            	c.checknode(parentNode,checked);
        					}
				        }
				        node.set('checked',checked);
				        if(checked == false){
					        node.eachChild(function(child){
					        	child.set('checked',checked);
					        	treepanel.fireEvent('checkchange', child, checked); //分发事件(触发事件)
					        });
				        }
        			});
        			gp.down('button[action=check]').on('click',function(){
        				c.checkchildnode(root);   
        			});
        			gp.down('button[action=reverse]').on('click',function(){
        				c.reversechildnode(root);  
        			});
        			gp.down('button[action=uncheck]').on('click',function(){
        				var nodes = treepanel.getChecked();  
                        if (nodes && nodes.length) {  
                            for (var i = 0; i < nodes.length; i++) {  
                               	nodes[i].set('checked',false);
                            }  
                        }  
        			});
        			gp.down('button[action=expand]').on('click',function(){
        				treepanel.expandAll(); 
        			});
        			gp.down('button[action=collapse]').on('click',function(){
        				treepanel.collapseAll();   
        			});
        			
        			var isSuper = gp.down('checkboxfield[name=isSuper]');
        			isSuper.on('change',function(){
        				if(isSuper.getRawValue()){
        					treepanel.disable(true);
        				}else{
        					treepanel.enable(true);
        				}
        			});
        			
        			gp.down('button[action=close]').on('click',function(){
        				gp.hide();
        			});
        			gp.down('button[action=save]').on('click',function(){
        				var form = gp.down('form');
						if(form.getForm().isValid()){
							//module ids string
							var modules = '';
							var perms = '';
                        	var nodes = treepanel.getChecked();  
	                        if (nodes && nodes.length) {
	                            for (var i = 0; i < nodes.length; i++) {  
	                            	modules += nodes[i].get('id');
	                            	modules += ',';
	                            	if(nodes[i].get('attributes').perm != undefined){
		                            	perms += nodes[i].get('attributes').perm;
		                            	perms += ',';
	                            	}
	                            } 
	                            modules = modules.substring(0,modules.length-1);
	                            perms = perms.substring(0,perms.length-1);
	                        }
	                        form.add([{
	                        	xtype: 'hiddenfield',
	                        	name: 'modules',
	                        	value: modules
	                        },{
	                        	xtype: 'hiddenfield',
	                        	name: 'perms',
	                        	value: perms
	                        }]);
							form.getForm().submit({
			    				url: 'role/save.do',
			    				success: function(form, action){
			    					App.msg.tip(action.result.message);
			    					gp.hide();
			    					c.getStore('role.RoleStore').load();
			    				}
			    			});
						}
        			});
        		}
            },
            
            'RoleEditView' : {
            	render: function(gp){ //整个页面渲染完成
        			var treepanel = gp.down('treepanel');
        			Ext.apply(treepanel.getStore().proxy.extraParams, {roleId: c.roleId});
        			treepanel.getStore().load();
        			
        			var root = treepanel.getRootNode();
        			//业务逻辑，选中节点时，不选中子节点，选中父节点;不选中节点时，不选中子节点，不选中父节点
        			treepanel.on('checkchange',function(node, checked){ //监听事件
        				var parentNode = node.parentNode;
        				if(!Ext.isEmpty(parentNode)) {   
        					if(checked == true){
				            	c.checknode(parentNode,checked);
        					}
				        }
				        node.set('checked',checked);
				        if(checked == false){
					        node.eachChild(function(child){
					        	child.set('checked',checked);
					        	treepanel.fireEvent('checkchange', child, checked); //分发事件(触发事件)
					        });
				        }
        			});
        			gp.down('button[action=check]').on('click',function(){
        				c.checkchildnode(root);   
        			});
        			gp.down('button[action=reverse]').on('click',function(){
        				c.reversechildnode(root);  
        			});
        			gp.down('button[action=uncheck]').on('click',function(){
        				var nodes = treepanel.getChecked();  
                        if (nodes && nodes.length) {  
                            for (var i = 0; i < nodes.length; i++) {  
                               	nodes[i].set('checked',false);
                            }  
                        }  
        			});
        			gp.down('button[action=expand]').on('click',function(){
        				treepanel.expandAll(); 
        			});
        			gp.down('button[action=reverse]').on('click',function(){
        				treepanel.collapseAll();   
        			});
        			
        			var isSuper = gp.down('checkboxfield[name=isSuper]');
        			isSuper.on('change',function(){
        				if(isSuper.getRawValue()){
        					treepanel.disable(true);
        				}else{
        					treepanel.enable(true);
        				}
        			});
        			
        			gp.down('button[action=close]').on('click',function(){
        				gp.hide();
        			});
        			gp.down('button[action=save]').on('click',function(){
        				var form = gp.down('form');
						if(form.getForm().isValid()){
							//module ids string
							var modules = '';
							var perms = '';
                        	var nodes = treepanel.getChecked();  
	                        if (nodes && nodes.length) {
	                            for (var i = 0; i < nodes.length; i++) {  
	                            	modules += nodes[i].get('id');
	                            	modules += ',';
	                            	if(nodes[i].get('attributes').perm != undefined){
		                            	perms += nodes[i].get('attributes').perm;
		                            	perms += ',';
	                            	}
	                            } 
	                            modules = modules.substring(0,modules.length-1);
	                            perms = perms.substring(0,perms.length-1);
	                        }
	                        form.add([{
	                        	xtype: 'hiddenfield',
	                        	name: 'modules',
	                        	value: modules
	                        },{
	                        	xtype: 'hiddenfield',
	                        	name: 'perms',
	                        	value: perms
	                        }]);
							form.getForm().submit({
			    				url: 'role/update.do',
			    				success: function(form, action){
			    					App.msg.tip(action.result.message);
			    					gp.hide();
			    					c.getStore('role.RoleStore').load();
			    				}
			    			});
						}
        			});
            	}
            }
        	
        });
    },
    
    //=======controller定义方法===========================================
    checkchildnode : function(node) {  
        var childnodes = node.childNodes;  
        for (var i = 0; i < childnodes.length; i++) {  
            var rootnode = childnodes[i];  
            rootnode.set('checked',true);  
            if (rootnode.childNodes.length > 0) {   
                this.checkchildnode(rootnode);   
            }  
        }  
    },
    
    reversechildnode : function(node) {  
        var childnodes = node.childNodes;  
        for (var i = 0; i < childnodes.length; i++) {  
            var rootnode = childnodes[i];  
            if (rootnode.childNodes.length > 0) { 
                this.reversechildnode(rootnode);   
                var childChecked = this.childhaschecked(rootnode);
                if(childChecked){
                	rootnode.set('checked',true); 
                }
            }else{
            	if(rootnode.get('checked')){
                	rootnode.set('checked',false); 
                }else{
                	rootnode.set('checked',true); 
                }
            }
        }  
    },
    
    checknode : function(node, checked){
        if(node.get('checked')==null) return false;
        var childChecked = this.childhaschecked(node);
        if(childChecked>0){
        	node.set('checked', true); 
        }else{
        	node.set('checked', false); 
        }
        node.getOwnerTree().fireEvent('check', node, checked); //分发事件(触发事件)
        var parentNode = node.parentNode;  
        if( parentNode !== null){  
            this.checknode(parentNode,checked);
        }  
    },
    
    childhaschecked : function(node){
    	var childNodes = node.childNodes;  
        var checkedNum = 0;  
        if(childNodes || childNodes.length>0){  
            for(var i=0;i<childNodes.length;i++){  
                if(childNodes[i].get('checked')){  
                    checkedNum += 1; 
                }  
            }  
        }  
        return checkedNum;  
    } 

});