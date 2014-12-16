Ext.define('App.controller.CfgController', {
    extend: 'Ext.app.Controller',
    
    views: ['cfg.IndexView'],
    
    init: function() {
        
        this.control({
        	
        	'CfgIndexView': {
        		render: function(gp){ //整个页面渲染完成
        			
        		}
        	}
        });
        
    }
});