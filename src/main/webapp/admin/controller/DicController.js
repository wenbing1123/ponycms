Ext.define('App.controller.DicController', {
    extend: 'Ext.app.Controller',

    views: [
       'dic.IndexView'
    ],
    
    stores: ['dic.DicStore','category.CategoryStore'],
    
    init: function() {
    	
    	var c = this;
    	c.categoryId = null;
    	
    	this.control({
        	
        	'DicIndexView': {
        		render: function(gp){ 
        			
        			var treepanel = gp.down('treepanel');
        			Ext.apply(treepanel.getStore().proxy.extraParams, {type: 'dic'});
        			treepanel.getStore().load();
        			
        			var gridpanel = gp.down('gridpanel');
        			treepanel.on('itemclick',function(view,record){
        				c.categoryId = record.data.id;
        				Ext.apply(gridpanel.getStore().proxy.extraParams, {categoryId: record.data.id});
        				gridpanel.getStore().load();
        				gridpanel.setTitle('字典列表【'+record.data.text+'】');
        			});
        			
        			gp.down('button[action=save]').on('click',function(){
	                    if(c.categoryId != undefined){
	                    	
	                    }else{
	                    	App.msg.alert('请从左侧选择分类');
	                    }
	                });
        			
        			gp.down('button[action=add]').on('click',function(){
                        if(c.categoryId != undefined){
                        	
                        }else{
                        	App.msg.alert('请从左侧选择分类');
                        }
                     });
        			
                     gp.down('button[action=refresh]').on('click',function(){
                    	 if(c.categoryId != undefined){
                    		 gridpanel.getStore().load();
                         }else{
                         	App.msg.alert('请从左侧选择分类');
                         }
                     });
                    
        			gridpanel.on('cellclick', function(grid, td, cellIndex, record, tr, rowIndex, e) {
                   		var aTag = e.getTarget('a');
                   		if(aTag != undefined){
	                    	var opt = aTag.attributes['action'].nodeValue;
	                   		if(opt == 'remove'){
	                   			App.msg.confirm('将删除选择字典，确定删除记录？',function(){
	                   				App.ajax('dic/remove.do', function(result){
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
        	}
        
    	});
    }
});