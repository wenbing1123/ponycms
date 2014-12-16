Ext.define('App.view.MenuView', {
    extend: 'Ext.panel.Panel',    
    
    alias: 'widget.menuview',
    
    initComponent: function() {
    	
    	 Ext.apply(this, {
             animCollapse: true,
             split: true,
             collapsible: true,
             layout:{
                 type: 'accordion',
                 animate: true
             }
         });
    	 
    	this.callParent(arguments);
    }
});