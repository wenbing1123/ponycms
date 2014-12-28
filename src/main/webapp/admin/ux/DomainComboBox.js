Ext.define('App.ux.DomainComboBox', {
    extend: 'App.ux.form.TreePicker',
    alias: 'widget.domaincombo',
    rootVisible: false,
    store: Ext.create('Ext.data.TreeStore',{
    	fields: ['id','text'],
    	autoLoad: true,
    	root: {
	        id: null,
	        text: '请选择...',
	        expanded: true
    	},
      	proxy: {  
           type: 'ajax',   
           url: 'domainController.do?treeData',
		   reader: 'json'
        }
    }),
    minPickerHeight: 200,
    displayField: 'text',
    valueField: 'id'
});