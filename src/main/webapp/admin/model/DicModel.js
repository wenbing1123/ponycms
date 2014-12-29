Ext.define('App.model.DicModel', {
    extend: 'Ext.data.Model',

    fields:[
        {name:'id',type:'int',useNull:true},
        {name:'category',type:'string',useNull:true},
        {name:'name',type:'string',useNull:true},
        {name:'order',type:'int',useNull:true},
        {name:'description',type:'string',useNull:true}
    ]

});