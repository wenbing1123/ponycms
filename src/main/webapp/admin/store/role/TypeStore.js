Ext.define('App.store.role.TypeStore', {
    extend: 'Ext.data.ArrayStore',
    fields: ['value','label'],
    autoLoad: false, //当初始化时不加自动载数据,手动加载数据
    proxy: {
        type: 'ajax',
        url: 'role/type.do',
        actionMethods: {read:'POST'},
        reader: {
            type:'json'
        }
    }
});