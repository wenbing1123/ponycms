Ext.define('App.controller.UserController', {
    extend: 'Ext.app.Controller',
    
    views: ['user.IndexView','user.AddView'],
    stores: ['user.UserStore'],
    models: [],
    
    init: function() {
        
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
    							App.ajax('log/remove.do', function(result){
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
        			
        		}
        	}
        });
        
    }
});