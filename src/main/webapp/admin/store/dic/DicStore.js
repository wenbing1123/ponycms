Ext.define('App.store.dic.DicStore', {
    extend: 'Ext.data.Store',
    fields: ['id','category','name','order','description'],
    pageSize: App.pageSize,
    autoLoad: false, //当初始化时不加自动载数据,手动加载数据
    proxy: {
        type: 'ajax',
        url: 'dic/datagrid.do',
        actionMethods: {read:'POST'},
        reader: {
            type:'json',
            totalProperty:'total',
            root:'rows'
        },
        extraParams: {}
    }
});