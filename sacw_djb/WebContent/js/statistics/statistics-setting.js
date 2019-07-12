$(function() {

    $('#select-type').select2({
        placeholder : '类型'
    });

    $('#select-type').bind('select2:select', function(e) {
        refresh();
    });

    $('#table-unit').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        url : locat + '/statistics/unit/list?' + $('#form-search').serialize(),
        striped : true,
        pagination : true,
        paginationLoop : false,
        cache : false,
        escape : true,
        queryParams : function(params) {
            params.page = params.offset / params.limit + 1;
            params.rows = params.limit;
            return params;
        },
        columns : [ {
            title : '序号',
            align : 'center',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, {
            field : 'type',
            align : 'center',
            title : '类型',
            formatter : function(value, row, index) {
                var txt = value;
                if (value === 'statistics_car') {
                    txt = '车辆统计';
                } else if (value == 'statistics_property') {
                    txt = '财物统计';
                }
                return txt;
            }
        }, {
            field : 'unitId',
            align : 'center',
            title : '单位编号'
        }, {
            field : 'unitName',
            align : 'center',
            title : '单位名称'
        }, {
            field : 'containsChildren',
            align : 'center',
            title : '是否统计下级',
            formatter : function(value, row, index) {
                return value == 0 ? '否' : '是';
            }
        }, {
            field : 'sort',
            align : 'center',
            title : '排序列'
        }, {
            field : 'id',
            title : '操作',
            align : 'center',
            width : '200px',
            formatter : function(value, row, index) {
                var arr = [];
                arr.push('<a class="btn btn-sm btn-flat btn-primary" onclick="edit(\'' + row.id + '\')">查看 / 修改</a>');
                arr.push('<a class="btn btn-sm btn-flat btn-danger" onclick="del(\'' + row.id + '\')">删除</a>');
                return arr.join(' ');
            }
        } ]
    });

});

/**
 * 刷新
 * 
 * @returns
 */
function refresh() {
    var params = $('#form-search').serialize();
    $('#table-unit').bootstrapTable('refresh', {
        url : locat + '/statistics/unit/list?' + params
    });
}

/**
 * 删除
 * 
 * @param id
 * @returns
 */
function del(id) {
    top.layer.confirm('确认删除该统计单位？', function(index) {
        top.layer.close(index);
        index = top.layer.load();
        $.post(locat + '/statistics/unit/delete', {
            id : id
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
            top.layer.msg('删除失败', {
                icon : 2
            });
        });
    });
}

function edit(id) {
    top.layer.open({
        type : 2,
        title : '编辑统计单位',
        maxmin : true,
        area : getArea(800, 500),
        end : function() {
            refresh();
        },
        content : locat + '/html/statistics/statistics-edit.html?id=' + id
    });
}

function add() {
    top.layer.open({
        type : 2,
        title : '添加统计单位',
        maxmin : true,
        area : getArea(600, 400),
        end : function() {
            refresh();
        },
        content : locat + '/html/statistics/statistics-edit.html'
    });
}