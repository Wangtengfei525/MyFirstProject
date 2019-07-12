$(function() {

    var monday = moment().day(1);

    var friday = moment().day(5);

    $('#input-start-time').datepicker({
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd 00:00:00'
    });
    $('#input-end-time').datepicker({
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd 23:59:59'
    });

    $('#input-start-time').datepicker('setDate', monday.toDate());
    $('#input-end-time').datepicker('setDate', friday.toDate());

    $('#input-unit-name').click(function() {
        var inputObj = $('#input-unit-name');
        var offset = inputObj.offset();
        $("#unit-wrapper").css({
            left : offset.left + "px",
            top : offset.top + inputObj.outerHeight() + "px"
        }).slideDown("fast");
        $('body').bind('mousedown', onBodyDown);
    });

    // 初始化单位下拉菜单
    var setting = {
        data : {
            key : {
                children : 'children',
                name : 'name'
            }
        },
        callback : {
            onClick : function(event, treeId, treeNode, clickFlag) {
                $('#input-unit-name').val(treeNode.name);
                $('#input-unit-id').val(treeNode.id);
                hideUnitTree();
            }
        }
    };

    $.get(locat + '/system/unit/tree', function(data) {
        $.fn.zTree.init($("#tree-unit"), setting, data);
    });
    $('#table-statistics').bootstrapTable({
        classes : 'table table-hover',
        sidePagination : 'server',
        url : locat + '/statistics/property?' + $('#form-search').serialize(),
        striped : true,
        pagination : true,
        paginationLoop : false,
        onlyInfoPagination : true,
        cache : false,
        escape : true,
        columns : [ {
            title : '单位名称',
            field : 'unitName',
            align : 'center'
        }, 
        
      /*  {
            title : '案件入库',
            field : 'caseIn',
            align : 'center'
        },*/
        
        {
            title : '财物入库',
            field : 'propertyIn',
            align : 'center'
        }, 
       /* {
            title : '案件出库',
            field : 'caseOut',
            align : 'center'
        }, */
        
        {
            title : '财物出库',
            field : 'propertyOut',
            align : 'center'
        }, 
        /*{
            title : '案件借调',
            field : 'caseSecondment',
            align : 'center'
        }, 
        */
        {
            title : '财物借调',
            field : 'propertySecondment',
            align : 'center'
        }, 
        
        /*{
            title : '案件归还',
            field : 'caseBack',
            align : 'center'
        }, */
        
        {
            title : '财物归还',
            field : 'propertyBack',
            align : 'center'
        }, {
            title : '案件总数',
            field : 'caseAll',
            align : 'center'
        }, {
            title : '财物总数',
            field : 'propertyAll',
            align : 'center'
        }, 
        
       /* {
            title : '案件总入库',
            field : 'caseAllIn',
            align : 'center'
        }, */
        
        {
            title : '财物总入库',
            field : 'propertyAllIn',
            align : 'center'
        }, 
        
     /*   {
            title : '案件总出库',
            field : 'caseAllOut',
            align : 'center'
        },*/
        
        {
            title : '财物总出库',
            field : 'propertyAllOut',
            align : 'center'
        } 
        
        
        ]
    });
});

/**
 * 隐藏单位树
 * 
 * @returns
 */
function hideUnitTree() {
    $("#unit-wrapper").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

/**
 * 点击页面隐藏单位树
 * 
 * @param event
 * @returns
 */
function onBodyDown(event) {
    if (event.target.id == 'input-unit-name' || event.target.id == 'unit-wrapper' || $(event.target).parents('#unit-wrapper').length > 0) {
        return;
    }
    hideUnitTree();
}
/**
 * 搜索记录
 * 
 * @param params
 * @returns
 */
function refresh() {
    var params = $('#form-search').serialize();
    $('#table-statistics').bootstrapTable('refresh', {
        url : locat + '/statistics/property?' + params
    });
}


//
/**
 * 打开统计设置
 * 
 * @returns
 */
function setting() {
    top.layer.open({
        type : 2,
        title : '统计单位设置',
        maxmin : true,
        area : getArea(1000, 600),
        content : locat + '/html/statistics/statistics-setting.html'
    });
}

/**
 * 导出
 * 
 * @returns
 */
function exp() {

    var sendUnitId = $("#input-unit-id").val();
    var startTime = $("#input-start-time").val();
    var endTime = $("#input-end-time").val();

    window.open(locat + '/statistics/property/export?' + $('#form-search').serialize());
}