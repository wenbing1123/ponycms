Ext.define('App.controller.MessageController', {
    extend: 'Ext.app.Controller',
    
    views: ['personal.MessageView','personal.MessageDetailView'],
    
    stores: ['message.MessageStore'],
    
    init: function() {
        
        this.control({
        	
        	'PersonalMessageView': {
        		render: function(gp){ //整个页面渲染完成
        			var gridpanel = gp.down('gridpanel');
        			gridpanel.getStore().load();
        			
        			var search_btn = gp.down('button[action=search]');
        			search_btn.on('click',function(){
        				var form = gp.down('form');
        				if(form.getForm().isValid()){
        					var ifRead = form.getForm().findField('ifRead').getGroupValue();
        					if(ifRead != 'all'){
        						form.getForm().setValues({
        							search_EQB_ifRead: ifRead
        						});
        					}else{
        						form.getForm().setValues({
        							search_EQB_ifRead: null
        						});
        					}
							gridpanel.getStore().proxy.extraParams = form.getForm().getValues();
							gridpanel.getStore().load();
			    		}
        			});
        			
        			var ifRead_btn = gp.down('button[action=ifRead]');
        			ifRead_btn.on('click',function(){
        				var r = gridpanel.getSelectionModel().getSelection();
                   		if(r && r.length > 0){
							var ids = '';
							for(var i=0;i<r.length;i++){
								ids += r[i].data.id;
								ids += ',';
							}
							ids = ids.substring(0,ids.length-1);
							App.ajax('messageController.do?markRead', function(result){
								App.msg.tip(result.message);
								if(result.success){
	        						gridpanel.getStore().load();
	        					}
							},{'ids': ids});
                   		}else{
                   			App.msg.alert('请选择要标记的记录!');
                   		}
        			});
        			
        			var delete_btn = gp.down('button[action=delete]');
        			delete_btn.on('click',function(){
        				var r = gridpanel.getSelectionModel().getSelection();
                   		if(r && r.length > 0){
                   			App.msg.confirm('确定要删除选定记录？',function(){
    							var ids = '';
    							for(var i=0;i<r.length;i++){
    								ids += r[i].data.id;
    								ids += ',';
    							}
    							ids = ids.substring(0,ids.length-1);
    							App.ajax('messageController.do?remove', function(result){
    								App.msg.tip(result.message);
    								if(result.success){
    	        						gridpanel.getStore().load();
    	        					}
    							},{'ids': ids});
            				});
                   		}else{
                   			App.msg.alert('请选择要删除的记录!');
                   		}
        			});
        			
        			//表格事件
                    gridpanel.on('cellclick', function(grid, td, cellIndex, record, tr, rowIndex, e) {
                   		var aTag = e.getTarget('a');
                   		if(aTag != undefined){
	                   		var opt = aTag.attributes['action'].nodeValue;
	                   		if(opt == 'detail'){
	                   			var w = Ext.widget("PersonalMessageDetailView");
	                   			w.show();
	                   			w.down('form').loadRecord(record);
	                   		}
                   		}
                    });
        			
        		}
        	},
        	
        	'PersonalMessageDetailView':{
        		render: function(gp){ //整个页面渲染完成
        			gp.down('button[action=cannel]').on('click',function(){
        				gp.hide();
        			});
        		}
        	}
        });
        
    }
});