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
    $('#table-carstatistics').bootstrapTable({
        classes : 'table table-hover',
        sidePagination : 'server',
        url : locat + '/statistics/car?' + $('#form-search').serialize(),
        striped : true,
        pagination : true,
        paginationLoop : false,
        onlyInfoPagination : true,
        cache : false,
        escape : true,
        queryParams : function(params) {
            params.page = params.offset / params.limit + 1;
            params.rows = params.limit;
            return params;
        },
        columns : [ {
            title : '单位名称',
            field : 'unitName',
            align : 'center'
        }, {
            title : '机动车入库',
            field : 'motorVehicleIn',
            align : 'center'
        }, {
            title : '机动车出库',
            field : 'motorVehicleOut',
            align : 'center'
        }, {
            title : '非机动车入库',
            field : 'nonMotorVehicleIn',
            align : 'center'
        }, {
            title : '非机动车出库',
            field : 'nonMotorVehicleOut',
            align : 'center'
        }, {
            title : '机动车数量(总数)',
            field : 'motorVehicleAll',
            align : 'center'
        }, {
            title : '机动车出库（总数）',
            field : 'motorVehicleAllOut',
            align : 'center'
        }, {
            title : '非机动车数量(总数)',
            field : 'nonMotorVehicleAll',
            align : 'center'
        }, {
            title : '非机动车出库（总数）',
            field : 'nonMotorVehicleAllOut',
            align : 'center'
        } ]
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
    $('#table-carstatistics').bootstrapTable('refresh', {
        url : locat + '/statistics/car?' + params
    });
}

/**
 * 导出
 * 
 * @returns
 */
function exp() {
    var params = $('#form-search').serialize();
    window.open(locat + '/statistics/car/export?' + params);
}