$(function() {

    $('#input-start-time').datepicker({
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd 00:00:00',
        todayBtn : 'linked'
    });

    $('#input-end-time').datepicker({
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd 23:59:59',
        todayBtn : 'linked'
    });

    $('#table-car').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        url : locat + '/carConserve/group/list?' + $('#form-search').serialize(),
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
            align : 'center',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, {
            title : '养护组名称',
            align : 'center',
            field : 'name'
        }, {
            title : '养护时间',
            align : 'center',
            field : 'conserveTime',
            formatter : function(value, row, index) {
                if (value == null) {
                    return null;
                }
                return moment(value).format('YYYY-MM-DD HH:mm:ss');
            }
        }, {
            title : '是否已完成',
            align : 'center',
            field : 'completed',
            formatter : function(value, row, index) {
                return value == 0 ? '未完成' : '已完成';
            }
        }, {
            title : '操作',
            align : 'center',
            field : 'id',
            formatter : function(value, row, index) {
                var arr = [];
                arr.push('<a class="btn btn-sm btn-default btn-flat" onclick="groupExport(\'' + row.id + '\')">导出</a>');
                if (row.completed) {
                    arr.push('<a class="btn btn-sm btn-default btn-flat" onclick="detail(\'' + row.id + '\')">查看</a>');
                } else {
                    arr.push('<a class="btn btn-sm btn-danger btn-flat" onclick="detail(\'' + row.id + '\')">完成养护</a>');
                }
                arr.push('<a class="btn btn-sm btn-danger btn-flat" onclick="del(\'' + row.id + '\')">删除</a>');

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
    $('#table-car').bootstrapTable('refresh', {
        url : locat + '/carConserve/group/list?' + params
    });
}

/**
 * 添加养护组
 * 
 * @returns
 */
function add() {
    top.layer.open({
        type : 2,
        area : getArea(1000, 600),
        title : '新增批量养护',
        maxmin : true,
        content : locat + '/html/car/batch-add.html',
        end : function() {
            refresh();
        }
    });
}

/**
 * 养护设置
 * 
 * @returns
 */
function conserveSetting() {
    top.layer.open({
        type : 2,
        area : getArea(1000, 700),
        maxmin : true,
        title : '养护设置',
        content : locat + '/html/car/conserve-setting.html'
    });
}

/**
 * 导出养护内容
 * 
 * @returns
 */
function groupExport(id) {
    window.open(locat + '/carConserve/group/export?id=' + id);
}

/**
 * 删除养护组
 * 
 * @param id
 *            养护组id
 * @returns
 */
function del(id) {
    top.layer.confirm('删除批量养护组将删除组下所有养护记录，请谨慎操作。是否继续？', {
        icon : 2,
        title : '警告'
    }, function(index) {
        top.layer.close(index);
        var index = top.layer.load();
        $.post(locat + '/carConserve/group/delete', {
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
        }).fail(function(data) {
            top.layer.close(index);
            top.layer.msg('删除失败，请联系管理员', {
                icon : 2
            });
        });

    });
}

/**
 * 养护组详情
 * 
 * @returns
 */
function detail(id) {
    top.layer.open({
        type : 2,
        area : getArea(1200, 700),
        title : '批量养护明细',
        maxmin : true,
        end : function() {
            refresh();
        },
        content : locat + '/html/car/batch-detail.html?groupId=' + id
    });
}
