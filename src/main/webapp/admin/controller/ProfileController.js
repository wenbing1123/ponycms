Ext.define('App.controller.ProfileController', {
    extend: 'Ext.app.Controller',
    
    views: ['personal.ProfileView'],
   
    init: function() {
        
        this.control({
        	
        	'viewport': {
        		render: function(gp){ //整个页面渲染完成
        			
        		}
        	}
        });
        
    }
});