Ext.define('App.controller.IndexController', {
    extend: 'Ext.app.Controller',
   
    init: function() {
        
        this.control({
        	
        	'viewport': {
        		render: function(gp){ //整个页面渲染完成
        			
        			var menuview = gp.down('menuview');
        			var contentview = gp.down('contentview');
        			
        			var url = 'menu.do';
        			App.ajax(url, function(menus){
    					for(var i=0 ; i<menus.length; i++){
    						 menuview.add(Ext.create('Ext.tree.Panel',{
							 title : menus[i].text,
							 iconCls : menus[i].iconCls,
                             autoScroll : true,
                             rootVisible : false,
                             animate: true,
                             border: false,
                             viewConfig:{
                                 loadingText : "正在加载..."
                             },
                             store:Ext.create('Ext.data.TreeStore',{
                                 fields:['id','text','iconCls',{name:'port',mapping:'attributes.port'}],
                                 root:{
                                     children: menus[i].children
                                 }
                             }),
                             listeners:{
                            	 'afterrender': function(view){
                            		 view.expandAll();
                            	 },
                                 'itemclick': function(view,record){
                                     var tabId = 'menu_' + record.data.id;
                                     var tabCmp = contentview.getComponent(tabId);
                                     if(!tabCmp){
                                         var contentXtype = record.data.port; //如果存在XTYPE
                                         if(contentXtype != ''){
                                        	 contentview.add(Ext.widget(contentXtype,{
                                                 id : tabId,
                                                 iconCls: record.data.iconCls==''?'leaf':record.data.iconCls,
                                                 title : record.data.text,
                                                 closable: true
                                             })).show();
                                         }
                                     }else{
                                    	 contentview.setActiveTab(tabCmp);
                                     }
                                 }
                            }}));
    					}
        			});
        		}
        	}
        });
    }
});