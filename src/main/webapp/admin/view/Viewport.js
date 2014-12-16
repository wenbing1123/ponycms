
Ext.define('App.view.Viewport', {
	extend: 'Ext.container.Viewport',
	layout: 'border',
	border: false,
	uses: [
		'App.view.MenuView',
		'App.view.ContentView'
	],
	initComponent: function() {
		Ext.apply(this, {
    		items: [{
            	region: 'north',
            	height: 64,
            	border: false,
            	contentEl: 'header'
            },{
            	region: 'west',
            	title: '功能导航',
            	iconCls: 'menu',
            	width: 220,
            	minWidth: 220,
            	maxWidth: 220,
            	xtype: 'menuview'
            },{
            	region: 'center',
            	xtype: 'contentview'
            }]
    	});
    
		this.callParent(arguments);
	}

});