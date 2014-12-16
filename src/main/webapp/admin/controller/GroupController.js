Ext.define('App.controller.GroupController', {
    extend: 'Ext.app.Controller',

    views: [
       'group.IndexView'
    ],
    
    stores: ['group.GroupStore'],
    
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
        			
        			gp.down('button[action=add]').on('click',function(){
                        if(c.domainId != undefined){
                        	
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
                    
        			gridpanel.on('cellclick', function(grid, td, cellIndex, record, tr, rowIndex, e) {
                   		var aTag = e.getTarget('a');
                   		if(aTag != undefined){
	                    	var opt = aTag.attributes['action'].nodeValue;
	                    	if(opt == 'add'){
	                   			
	                   		}
	                    	if(opt == 'update'){
	                   			
	                   		}
	                   		if(opt == 'remove'){
	                   			App.msg.confirm('将删除组及子组，确定删除记录？',function(){
	                   				App.ajax('group/remove.do', function(result){
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