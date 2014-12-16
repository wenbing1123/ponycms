Ext.define('App.controller.DomainController', {
    extend: 'Ext.app.Controller',

    views: [
       'domain.IndexView'
    ],
    
    stores: ['domain.DomainStore'],
    
    init: function() {
    	
    	var c = this;
    	
    	this.control({
        	
        	'DomainIndexView': {
        		render: function(gp){ 
        			
        			var treepanel = gp.down('treepanel');
        			treepanel.getStore().load();
        			
        			gp.down('button[action=add]').on('click',function(){
                       
                    });
                    gp.down('button[action=refresh]').on('click',function(){
                       treepanel.getStore().load();
                    });
                    
                    treepanel.on('cellclick', function(grid, td, cellIndex, record, tr, rowIndex, e) {
                   		var aTag = e.getTarget('a');
                   		if(aTag != undefined){
	                    	var opt = aTag.attributes['action'].nodeValue;
	                    	if(opt == 'add'){
	                   			
	                   		}
	                    	if(opt == 'update'){
	                   			
	                   		}
	                   		if(opt == 'remove'){
	                   			App.msg.confirm('将删除域及子域，确定删除记录？',function(){
	                   				App.ajax('domain/remove.do', function(result){
	    								App.msg.tip(result.message);
	    								if(result.success){
	    									treepanel.getStore().load();
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