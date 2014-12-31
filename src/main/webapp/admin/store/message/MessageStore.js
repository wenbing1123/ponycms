Ext.define('App.store.message.MessageStore', {
    extend: 'Ext.data.Store',
    fields: ['id','title','content','from','ifRead','createtime'],
    pageSize: App.pageSize,
    autoLoad: false, //当初始化时不加自动载数据,手动加载数据
    proxy: {
        type: 'ajax',
        url: 'messageController.do?gridData&desc_createtime',
        actionMethods: {read:'POST'},
        reader: {
            type:'json',
            totalProperty:'total',
            root:'rows'
        },
        extraParams: {}
    }
});