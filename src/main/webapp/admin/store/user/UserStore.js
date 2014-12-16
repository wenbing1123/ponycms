Ext.define('App.store.user.UserStore', {
    extend: 'Ext.data.Store',
    fields: ['id','username','enabled','locked','ipAddress','email','phone','lastLoginIp','lastLoginDate'],
    pageSize: App.pageSize,
    autoLoad: false, //当初始化时不加自动载数据,手动加载数据
    proxy: {
        type: 'ajax',
        url: 'user/datagrid.do',
        actionMethods: {read:'POST'},
        reader: {
            type:'json',
            totalProperty:'total',
            root:'rows'
        },
        extraParams: {}
    }
});