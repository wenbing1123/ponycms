Ext.define('App.ux.GroupComboBox', {
    extend: 'App.ux.form.TreePicker',
    alias: 'widget.groupcombo',
    rootVisible: false,
    store: Ext.create('Ext.data.TreeStore',{
    	fields: ['id','text'],
    	autoLoad: false,
    	root: {
	        id: null,
	        text: '请选择...',
	        expanded: true
    	},
      	proxy: {  
           type: 'ajax',   
           url: 'groupController.do?treeData',
		   reader: 'json'
        }
    }),
    minPickerHeight: 200,
    displayField: 'text',
    valueField: 'id'
});