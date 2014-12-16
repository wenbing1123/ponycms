Ext.define('App.store.log.LogStore', {
    extend: 'Ext.data.Store',
    fields: ['id','operate','user','createtime','ip','url','success','message','url'],
    pageSize: App.pageSize,
    autoLoad: false, //当初始化时不加自动载数据,手动加载数据
    proxy: {
        type: 'ajax',
        url: 'log/datagrid.do?desc_createtime',
        actionMethods: {read:'POST'},
        reader: {
            type:'json',
            totalProperty:'total',
            root:'rows'
        },
        extraParams: {}
    }
});