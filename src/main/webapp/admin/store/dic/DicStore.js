Ext.define('App.store.dic.DicStore', {
    extend: 'Ext.data.Store',
    model: 'App.model.DicModel',
    pageSize: App.pageSize,
    autoLoad: false, //当初始化时不加自动载数据,手动加载数据
    proxy: {
        type: 'ajax',
        api: {
        	read: 'dicController.do?gridData',
        	create: 'dicController.do?save',
        	update: 'dicController.do?update',
        	destroy: 'dicController.do?remove'
        },
        reader:'json',
        writer:'json'
    }
});