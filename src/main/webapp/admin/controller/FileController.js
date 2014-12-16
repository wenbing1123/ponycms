Ext.define('App.controller.FileController', {
    extend: 'Ext.app.Controller',

    views: [
       'file.IndexView'
    ],
    
    stores: ['file.FileinfoStore','category.CategoryStore'],
    
    init: function() {
    	
    	var c = this;
    	
    	this.control({
        	
        	'FileIndexView': {
        		render: function(gp){ 
        			
        			var gridpanel = gp.down('gridpanel');
        			gridpanel.getStore().load();
        			
        			var delete_btn = gp.down('button[action=remove]');
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
    							App.ajax('file/remove.do', function(result){
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