Ext.define('App.store.role.RoleStore', {
    extend: 'Ext.data.Store',
    fields: ['id','name','type','priority','isDefault','isSuper','status','description'],
    pageSize: App.pageSize,
    autoLoad: false, //当初始化时不加自动载数据,手动加载数据
    proxy: {
        type: 'ajax',
        url: 'role/datagrid.do?desc_createtime',
        actionMethods: {read:'POST'},
        reader: {
            type:'json',
            totalProperty:'total',
            root:'rows'
        },
        extraParams: {}
    }
});