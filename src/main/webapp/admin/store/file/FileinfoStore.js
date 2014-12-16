Ext.define('App.store.file.FileinfoStore', {
    extend: 'Ext.data.Store',
    fields: ['id','type','name','ext','path','size','creator','createtime','category'],
    pageSize: App.pageSize,
    autoLoad: false, //当初始化时不加自动载数据,手动加载数据
    proxy: {
        type: 'ajax',
        url: 'file/datagrid.do',
        actionMethods: {read:'POST'},
        reader: {
            type:'json',
            totalProperty:'total',
            root:'rows'
        },
        extraParams: {}
    }
});