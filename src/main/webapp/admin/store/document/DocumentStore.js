Ext.define('App.store.document.DocumentStore', {
    extend: 'Ext.data.Store',
    fields: ['id','title','description','createtime'],
    pageSize: App.pageSize,
    autoLoad: false, //当初始化时不加自动载数据,手动加载数据
    proxy: {
        type: 'ajax',
        url: 'workflowController.do?gridData&desc_createtime',
        actionMethods: {read:'POST'},
        reader: {
            type:'json',
            totalProperty:'total',
            root:'rows'
        },
        extraParams: {}
    }
});