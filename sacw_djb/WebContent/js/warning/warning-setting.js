$(function() {

    $('#table-warning').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        url : locat + '/warning/setting/list',
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
            field : 'name',
            title : '名称'
        }, {
            field : 'rfIp',
            title : '射频天线IP'
        }, {
            field : 'rfPort',
            title : '射频天线端口'
        }, {
            field : 'cameraIp',
            title : '摄像头IP'
        }, {
            field : 'cameraPort',
            title : '摄像头端口'
        }, {
            field : 'id',
            title : '操作',
            formatter : function(value, row, index) {
                var arr = [];
                arr.push('<a class="btn btn-sm btn-flat btn-default" onclick="edit(\'' + row.id + '\')">查看 / 修改</a>');
                if(row.state=="0"){
                	arr.push('<a class="btn btn-sm btn-flat btn-default" onclick="start(\'' + row.id + '\')">开启</a>');
                }else{
                	arr.push('<a class="btn btn-sm btn-flat btn-default" onclick="start(\'' + row.id + '\')">重启</a>');
                }
                arr.push('<a class="btn btn-sm btn-flat btn-default" onclick="stop(\'' + row.id + '\')">关闭</a>');
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
    $('#table-warning').bootstrapTable('refresh');
}

/**
 * 删除
 * 
 * @param id
 * @returns
 */
function del(id) {
    top.layer.confirm('确认删除该设置？', function(index) {
        top.layer.close(index);
        index = top.layer.load();
        $.post(locat + '/warning/setting/delete', {
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
        title : '编辑报警设置',
        maxmin : true,
        area : getArea(900, 520),
        end : function() {
            refresh();
        },
        content : locat + '/html/warning/warning-edit.html?id=' + id
    });
}

function add() {
    top.layer.open({
        type : 2,
        title : '添加报警设置',
        maxmin : true,
        area : getArea(900, 520),
        end : function() {
            refresh();
        },
        content : locat + '/html/warning/warning-edit.html'
    });
}

/**
 * 关闭扫描
 * 
 * @returns
 */
function stop(id) {

    top.layer.confirm('确认关闭该处报警？', function(index) {
        top.layer.close(index);
        index = top.layer.load();
        $.post(locat + '/warning/stop', {
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
            top.layer.msg('关闭失败', {
                icon : 2
            });
        });
    });
}

/**
 * 开启扫描
 * 
 * @returns
 */
function start(id) {
    top.layer.confirm('确认开启该处报警？', function(index) {
        top.layer.close(index);
        index = top.layer.load();
        $.post(locat + '/warning/start', {
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
            top.layer.msg('开启失败', {
                icon : 2
            });
        });
    });
}

function closeAll() {
    top.layer.confirm('确认关闭所有报警？', function(index) {
        top.layer.close(index);
        index = top.layer.load();
        $.post(locat + '/warning/stop-all').done(function(data) {
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
            top.layer.msg('关闭失败', {
                icon : 2
            });
        });
    });
}

function status() {
    var index = top.layer.load();
    $.get(locat + '/warning/status').done(function(data) {
        top.layer.close(index);
        if (data.success) {
            top.layer.alert(data.message);
        } else {
            top.layer.msg(data.message, {
                icon : 2
            });
        }
    }).fail(function() {
        top.layer.close(index);
        top.layer.msg('查看信息失败', {
            icon : 2
        });
    });
}