$("#table-permisssion").bootstrapTable({
    url : locat + '/system/permission/list?tree=false',
    classes : 'table table-hover table-no-bordered',
    sidePagination : 'server',
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
    columns : [ [ {
        title : '序号',
        formatter : function(value, row, index) {
            return index + 1;
        }
    }, {
        title : '权限名称',
        field : 'name'
    }, {
        title : '显示顺序',
        field : 'sort'
    }, {
        title : '权限路径',
        field : 'url'
    }, {
        title : '是否显示',
        field : 'display',
        formatter : function(value) {
            if (value != 1) {
                return '否';
            } else {
                return '是';
            }
        }
    }, {
        title : '权限描述',
        field : 'description'
    }, {
        title : '操作',
        field : 'id',
        formatter : function(value, row, index) {
            var arr = [];
            arr.push('<a class="btn btn-sm btn-default btn-flat" onclick="edit(\'' + row.id + '\')">修改 / 查看</a>');
            arr.push('<a class="btn btn-sm btn-danger btn-flat" onclick="del(\'' + row.id + '\')">删除</a>');
            return arr.join(' ');
        }
    } ] ]
});

/**
 * 添加菜单
 * 
 * @returns
 */
function add() {
    top.layer.open({
        type : 2,
        title : '添加菜单',
        area : getArea(600, 400),
        maxnin : true,
        content : locat + '/html/system/permission-add.html'
    });
}

/**
 * 刷新
 * 
 * @returns
 */
function refresh() {
    $('#table-permisssion').bootstrapTable('refresh');
}

/**
 * 删除
 * 
 * @param id
 * @returns
 */
function del(id) {
    top.layer.confirm('确认删除该权限？', {
        icon : 2,
        title : '警告'
    }, function(index) {
        top.layer.close(index);
        var index = top.layer.load();
        $.post(locat + '/system/permission/delete', {
            ids : id
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
        }).fail(function(data) {
            top.layer.close(index);
            top.layer.msg('删除失败，请联系管理员', {
                icon : 2
            });
        });

    });
}

/**
 * 修改
 * 
 * @returns
 */
function edit(id) {
    top.layer.open({
        type : 2,
        title : '修改权限',
        area : getArea(600, 400),
        maxnin : true,
        content : locat + '/html/system/permission-edit.html?id=' + id
    });
}
