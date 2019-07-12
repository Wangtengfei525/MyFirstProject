$(function() {

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
    $('#table-property').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        striped : true,
        pagination : true,
        paginationLoop : false,
        url : locat + '/property/list?' + $('#form-search').serialize(),
        pageJump : true,
        onDblClickRow : function(row) {
            openPropertyView(row.id);
        },
        queryParams : function(params) {
            params.page = params.offset / params.limit + 1;
            params.rows = params.limit;
            return params;
        },
        columns : [ [ {
            title : '序号',
            align : 'center',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, {
            title : '财物名称',
            align : 'center',
            field : 'propertyName',
        }, {
            title : '案件名称',
            align : 'center',
            field : 'caseName',
        }, {
            title : '数量',
            align : 'center',
            field : 'number',
            width : 80,
            formatter : function(value, row, index) {
                return value + ' ' + row.meteringUnit;
            }
        }, {
            title : '财物状态',
            align : 'center',
            field : 'propertyStatus',
        }, {
            title : '权限单位',
            align : 'center',
            field : 'permitUnitMc',
        }, {
            title : '二维码',
            align : 'center',
            field : 'qrCode',
        } ] ]

    });
});

/**
 * 打印条形码
 * 
 * @param id
 *            财物id
 * @returns
 */
function printCode(id) {
    top.layer.confirm('确认打印条码？', function(index) {
        top.layer.close(index);
        $.post(locat + '/property/print-code', {
            id : id
        }).then(function(data) {
            var icon = 2;
            if (data.success) {
                icon = 1;
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        });
    });
}

/**
 * 刷新财物信息
 * 
 * @returns
 */
function refresh() {
    var params = $('#form-search').serialize();
    $('#table-property').bootstrapTable('refresh', {
        url : locat + '/property/list?' + params
    });
}
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
