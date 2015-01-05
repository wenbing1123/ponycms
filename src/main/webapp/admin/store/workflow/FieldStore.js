Ext.define('App.store.workflow.FieldStore', {
    extend: 'Ext.data.Store',
    fields: ['id','name','label','type','input','form'],
    autoLoad: false, //当初始化时不加自动载数据,手动加载数据
    proxy: {
        type: 'ajax',
        url: 'formController.do?fieldList',
        reader:'json'
    }
});