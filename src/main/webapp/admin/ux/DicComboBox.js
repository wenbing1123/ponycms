Ext.define('App.ux.DicComboBox', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.diccombo',
    store: Ext.create('Ext.data.ArrayStore',{
    	fields: ['id','name'],
    	autoLoad: false, 
      	proxy: {  
           type: 'ajax',   
           url: 'categoryController.do?listData',
           reader: 'json',
           extraParams: {type : this.category}
        }
    }),
    emptyText: '请选择...',
    displayField: 'name',
    valueField: 'id'
});