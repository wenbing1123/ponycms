Ext.define('App.controller.DicController', {
    extend: 'Ext.app.Controller',

    views: [
       'dic.IndexView'
    ],
    
    stores: ['dic.DicStore','category.CategoryStore'],
    
    init: function() {
    	
    	var c = this;
    	c.category = null;
    	
    	this.control({
        	
        	'DicIndexView': {
        		render: function(gp){ 
        			
        			var treepanel = gp.down('treepanel');
        			Ext.apply(treepanel.getStore().proxy.extraParams, {type: 'dic'});
        			treepanel.getStore().load();
        			
        			var gridpanel = gp.down('gridpanel');
        			var editingPlugin = gridpanel.editingPlugin;
        			treepanel.on('itemclick',function(view,record){
        				c.category = record.data.id;
        				Ext.apply(gridpanel.getStore().proxy.extraParams, {category: record.data.id});
        				gridpanel.getStore().load();
        				gridpanel.setTitle('字典列表【'+record.data.text+'】');
        			});
        			
        			gp.down('button[action=create]').on('click',function(){
                        if(c.category != undefined){
                        	editingPlugin.cancelEdit();
	                    	var row = Ext.create('App.model.DicModel',{
	                    		category: c.category,
	                    		order: 10
	                    	});
	                    	var store = gridpanel.getStore();
	                    	store.insert(store.getCount(), row);
	                    	editingPlugin.startEdit(store.getCount()-1, 0);
                        }else{
                        	App.msg.alert('请从左侧选择分类');
                        }
                     });
        			
        			gp.down('button[action=save]').on('click',function(){
	                    if(c.category != undefined){
	                    	editingPlugin.cancelEdit();
	                    	gridpanel.getStore().sync({
	                    		callback: function(batch,options){
	                    			gridpanel.getStore().load();
	                    		}
	                    	}); //同步到后台
	                    }else{
	                    	App.msg.alert('请从左侧选择分类');
	                    }
	                });
        			
                     gp.down('button[action=refresh]').on('click',function(){
                    	 if(c.category != undefined){
                    		 gridpanel.getStore().load();
                         }else{
                         	App.msg.alert('请从左侧选择分类');
                         }
                     });
                    
        			gridpanel.on('cellclick', function(grid, td, cellIndex, record, tr, rowIndex, e) {
                   		var aTag = e.getTarget('a');
                   		if(aTag != undefined){
	                    	var opt = aTag.attributes['action'].nodeValue;
	                   		if(opt == 'delete'){
	                   			editingPlugin.cancelEdit();
	                   			gridpanel.getStore().removeAt(rowIndex);
	                   		}
                   		}
                    });
        		}
        	}
        
    	});
    }
});