$(function() {

    $('#select-handled').select2();
    $('#select-handled').bind('select2:select', function(e) {
        refresh();
    });

    $("#table-warning").bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        url : locat + '/warning/list?' + $('#form-search').serialize(),
        striped : true,
        pagination : true,
        paginationLoop : false,
        escape : true,
        queryParams : function(params) {
            params.page = params.offset / params.limit + 1;
            params.rows = params.limit;
            return params;
        },
        columns : [ {
            title : '序号',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, {
            title : '异常出库位置',
            field : 'settingName'
        }, {
            title : '异常出库时间',
            field : 'warningTime',
            formatter : function(value, row, index) {
                if (value == null) {
                    return null;
                }
                return moment(value).format("YYYY-MM-DD HH:mm:ss");
            }
        }, {
            title : '是否已处理',
            field : 'handled',
            formatter : function(value, row, index) {
                return value == 1 ? '已处理' : '未处理';
            }
        }, {
            title : '备注',
            field : 'remark'
        }, {
            title : '操作',
            field : 'id',
            formatter : function(value, row, index) {
                var arr = [];
                arr.push('<a class="btn btn-default btn-sm btn-flat" onclick="detail(\'' + row.id + '\')">查看详情<a>');
                if (row.handled == 0) {
                    arr.push('<a class="btn btn-danger btn-sm btn-flat" onclick="handle(\'' + row.id + '\')">处理<a>');
                }
                return arr.join(' ');
            }
        } ]
    });

    setInterval(function() {
        refresh();
    }, 3000);

});

/**
 * 加载非法出库列表
 * 
 * @returns
 */
function refresh() {
    var params = $('#form-search').serialize();
    $("#table-warning").bootstrapTable('refresh', {
        url : locat + '/warning/list?' + params
    });
}

/**
 * 处理非法出库物品
 * 
 * @param id
 * @returns
 */
function handle(id) {

    top.layer.prompt({
        title : '请填写备注'
    }, function(remark, index) {
        top.layer.close(index);
        index = top.layer.load();
        $.post(locat + '/warning/handle', {
            id : id,
            remark : remark
        }).done(function(data) {
            top.layer.close(index);
            var icon = 2;
            if (data.success) {
                icon = 1;
                refresh();
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        }).fail(function() {
            top.layer.close(index);
            top.layer.msg('处理失败，请联系管理员', {
                icon : 2
            });
        });
    });
}

/**
 * 查看详情
 * 
 * @param id
 * @returns
 */
function detail(id) {

    top.layer.open({
        type : 2,
        title : '异常出库详情',
        maxmin : true,
        area : getArea(1000, 600),
        content : locat + '/html/warning/warning-detail.html?id=' + id
    });

}

/**
 * 全部处理
 * 
 * @returns
 */
function handleAll() {

    top.layer.prompt({
        title : '请填写备注'
    }, function(remark, index) {
        top.layer.close(index);
        var url = locat + '/warning/handle/all';
        index = top.layer.load();
        $.post(url, {
            remark : remark
        }).done(function(data) {
            top.layer.close(index);
            var icon = 2;
            if (data.success) {
                icon = 1;
                refresh();
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        }).fail(function() {
            top.layer.close(index);
            top.layer.msg('处理失败，请联系管理员', {
                icon : 2
            });
        });

    });

}
