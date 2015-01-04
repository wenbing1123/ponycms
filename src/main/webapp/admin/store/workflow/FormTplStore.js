Ext.define('App.store.workflow.FormTplStore', {
	fields: ['value','label'],
  	 autoLoad: true, 
  	 proxy: {  
       type: 'ajax',   
       url: 'formController.do?getAllFormTpl',
	     	reader: 'json',
	     	extraParams: {type : 'file'}
    }
});