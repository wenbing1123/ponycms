Ext.define('App.controller.CfgController', {
    extend: 'Ext.app.Controller',
    
    views: ['cfg.IndexView'],
    
    init: function() {
        
        this.control({
        	
        	'CfgIndexView': {
        		render: function(gp){ //整个页面渲染完成
        			App.ajax('cfgController.do?get', function(result){
        				gp.setValues(result);
					});
        			gp.down('button[action=save]').on('click',function(){
        				if(gp.getForm().isValid()){
        					gp.getForm().submit({
			    				url: 'cfgController.do?update',
			    				success: function(form, action){
			    					App.msg.tip(action.result.message);
			    					if(action.result.success){
			    						App.ajax('cfgController.do?get', function(result){
			    	        				gp.setValues(result);
			    						});
			    					}
			    				}
			    			});
        				}
                    });
        			gp.down('button[action=refresh]').on('click',function(){
        				App.ajax('cfgController.do?get', function(result){
            				gp.setValues(result);
    					});
                    });
        		}
        	}
        });
        
    }
});