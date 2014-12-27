Ext.define('App.controller.UserController', {
    extend: 'Ext.app.Controller',
    
    views: ['user.IndexView','user.AddView','user.EditView','user.ResetPwdView'],
    stores: ['user.UserStore'],
    models: [],
    
    init: function() {
    	
    	var c = this;
        
        this.control({
        	
        	'UserIndexView': {
        		render: function(gp){ //整个页面渲染完成
        			
        			var gridpanel = gp.down('gridpanel');
        			gridpanel.getStore().load();
        			
        			var search_btn = gp.down('button[action=search]');
        			search_btn.on('click',function(){
        				var form = gp.down('form');
        				if(form.getForm().isValid()){
        					var combo = form.down('combo[name=status]');
        					var status = combo.getValue();
        					if(status==1){
        						form.getForm().setValues({
        							search_EQB_enabled: true,
        							search_EQB_locked: null
        						});
        					}else if(status==2){
        						form.getForm().setValues({
        							search_EQB_enabled: true,
        							search_EQB_locked: true
        						});
        					}else if(status==3){
        						form.getForm().setValues({
        							search_EQB_enabled: false,
        							search_EQB_locked: null
        						});
        					}else{
        						form.getForm().setValues({
        							search_EQB_enabled: null,
        							search_EQB_locked: null
        						});
        					}
							gridpanel.getStore().proxy.extraParams = form.getForm().getValues( );
							gridpanel.getStore().load();
			    		}
        			});
        			
        			var add_btn = gp.down('button[action=add]');
        			add_btn.on('click',function(){
        				Ext.widget("UserAddView").show();
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
    							App.ajax('user/remove.do', function(result){
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
	                   		if(opt == 'reset'){
	                   			var w = Ext.widget("UserResetPwdView");
	                   			w.show();
	                   			w.down('form').loadRecord(record);
	                   		}
	                   		if(opt == 'update'){
	                   			var w = Ext.widget("UserEditView");
	                   			w.show();
	                   			f = w.down('form');
	                   			f.loadRecord(record);
	                   			App.ajax('user/getUserRoles.do', function(data){
	                   				var groupcombo = f.down('groupcombo');
	                   				groupcombo.clearValue();
	                				groupcombo.getStore().load({params:{domainId:data.domainId}});
	                   				f.getForm().setValues({
	                   					domain: data.domainId,
	                   					group:	data.groupId,
	                   					roles:	data.roleIds
	                   				});
    							},{'id': record.data.id});
	                   		}
	                   		if(opt == 'remove'){
	                   			App.msg.confirm('确定要删除选定记录？',function(){
	                   				App.ajax('user/remove.do', function(result){
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
        	},
        	
        	'UserAddView': {
        		render: function(gp){ //整个页面渲染完成
        			var domaincombo = gp.down('domaincombo');
        			var groupcombo = gp.down('groupcombo');
        			
        			domaincombo.on('select',function(){
        				var domainId = domaincombo.getValue();
        				groupcombo.clearValue();
        				groupcombo.getStore().load({params:{domainId:domainId}});
        			});
        			
        			gp.down('button[action=close]').on('click',function(){
        				gp.hide();
        			});
        			gp.down('button[action=save]').on('click',function(){
        				var form = gp.down('form');
						if(form.getForm().isValid()){
							form.getForm().submit({
			    				url: 'user/save.do',
			    				success: function(form, action){
			    					App.msg.tip(action.result.message);
			    					gp.hide();
			    					c.getStore('user.UserStore').load();
			    				}
			    			});
						}
        			});
        		}
        	},
        	
        	'UserEditView': {
        		render: function(gp){ //整个页面渲染完成
        			var domaincombo = gp.down('domaincombo');
        			var groupcombo = gp.down('groupcombo');
        			
        			domaincombo.on('select',function(){
        				var domainId = domaincombo.getValue();
        				groupcombo.clearValue();
        				groupcombo.getStore().load({params:{domainId:domainId}});
        				groupcombo.setValue(c.userGroupId);
        			});
        			
        			gp.down('button[action=close]').on('click',function(){
        				gp.hide();
        			});
        			gp.down('button[action=save]').on('click',function(){
        				var form = gp.down('form');
						if(form.getForm().isValid()){
							form.getForm().submit({
			    				url: 'user/update.do',
			    				success: function(form, action){
			    					App.msg.tip(action.result.message);
			    					gp.hide();
			    					c.getStore('user.UserStore').load();
			    				}
			    			});
						}
        			});
        		}
        	},
        	
        	'UserResetPwdView' : {
        		render: function(gp){ //整个页面渲染完成
        			gp.down('button[action=close]').on('click',function(){
        				gp.hide();
        			});
        			gp.down('button[action=ok]').on('click',function(){
        				var form = gp.down('form');
						if(form.getForm().isValid()){
							form.getForm().submit({
			    				url: 'user/resetPassword.do',
			    				success: function(form, action){
			    					if(action.result.success){
			    						gp.down('label').setText('密码重置成功，密码为：'+action.result.object);
			    					}else{
			    						gp.down('label').setText('密码重置失败，错误为：'+action.result.message);
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