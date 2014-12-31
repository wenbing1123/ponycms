Ext.define('App.controller.ProfileController', {
    extend: 'Ext.app.Controller',
    
    views: ['personal.ProfileView','personal.UpdatePwdView'],
   
    init: function() {
        
        this.control({
        	
        	'PersonalProfileView': {
        		render: function(gp){ //整个页面渲染完成
        			App.ajax('profileController.do?get', function(result){
        				gp.setValues(result);
					});
        			gp.down('button[action=updatePwd]').on('click',function(){
        				Ext.widget('PersonalUpdatePwdView').show();
        			});
        		}
        	},
        	
        	'PersonalUpdatePwdView': {
        		render: function(gp){ //整个页面渲染完成
        			gp.down('button[action=cannel]').on('click',function(){
        				gp.hide();
        			});
        			gp.down('button[action=accept]').on('click',function(){
        				var form = gp.down('form');
						if(form.getForm().isValid()){
							form.getForm().submit({
			    				url: 'profileController.do?updatePwd',
			    				success: function(form, action){
			    					App.msg.tip(action.result.message);
			    					if(action.result.success){
			    						gp.hide();
			    					}
			    				}
			    			});
						}
        			});
        		}
        	}
        
        });
        
    }
});