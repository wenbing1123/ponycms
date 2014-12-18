Ext.define('App.controller.UserController', {
    extend: 'Ext.app.Controller',
    
    views: ['user.IndexView','user.AddView'],
    stores: ['user.UserStore'],
    models: [],
    
    init: function() {
    	
    	var c = this;
        
        this.control({
        	
        	'UserIndexView': {
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
        				Ext.widget("UserAddView").show();
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
    							App.ajax('user/remove.do', function(result){
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
	                   			
	                   		}
	                   		if(opt == 'remove'){
	                   			App.msg.confirm('确定要删除选定记录？',function(){
	                   				App.ajax('user/remove.do', function(result){
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
        	
        	'UserAddView': {
        		render: function(gp){ //整个页面渲染完成
        			var domaincombo = gp.down('domaincombo');
        			var groupcombo = gp.down('groupcombo');
        			
        			domaincombo.on('select',function(){
        				var domainId = domaincombo.getValue();
        				groupcombo.clearValue();
        				groupcombo.getStore().load({params:{domainId:domainId}});
        			});
        			
        			gp.down('button[action=close]').on('click',function(){
        				gp.hide();
        			});
        			gp.down('button[action=save]').on('click',function(){
        				var form = gp.down('form');
						if(form.getForm().isValid()){
							form.getForm().submit({
			    				url: 'user/save.do',
			    				success: function(form, action){
			    					App.msg.tip(action.result.message);
			    					gp.hide();
			    					c.getStore('user.UserStore').load();
			    				}
			    			});
						}
        			});
        		}
        	}
        	
        });
        
    }
});