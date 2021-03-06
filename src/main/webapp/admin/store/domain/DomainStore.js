Ext.define('App.store.domain.DomainStore', {
    extend: 'Ext.data.TreeStore',
    fields: ['id','text','iconCls','expanded','leaf','children','attributes'],
    autoLoad: false, //当初始化时不加自动载数据,手动加载数据
    nodeParam: 'id',
    root: {
    	id: null,
        expanded: true
    },
    proxy: {
        type: 'ajax',
        url:  'domainController.do?treeData',
        actionMethods: {read:'POST'}
    }
});