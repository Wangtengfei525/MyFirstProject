$(function() {

    var datepickerconfig = {
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd 00:00:00',
        todayBtn : 'linked'
    };

    $('#input-start-time').datepicker(datepickerconfig);
    $('#input-end-time').datepicker(datepickerconfig);

    $('#table-car').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        url : locat + '/carConserve/list',
        striped : true,
        // height : 600,
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
            title : '案件名称',
            align : 'center',
            field : 'caseName'
        }, {
            title : '车辆信息',
            align : 'center',
            field : 'carMessage',
            formatter : function(value, row, index) {
                var text;
                if (value == null || value.length < 13) {
                    text = value;
                } else {
                    text = value.substring(0, 10) + '...';
                }
                return '<lable title="' + value + '">' + text + '</lable>';

            }
        }, {
            title : '养护内容',
            align : 'center',
            field : 'conserveTextName',
            formatter : function(value, row, index) {
                var text;
                if (value == null || value.length < 15) {
                    text = value;
                } else {
                    text = value.substring(0, 12) + '...';
                }
                return '<lable title="' + value + '">' + text + '</lable>';

            }
        }, {
            title : '养护人',
            align : 'center',
            field : 'conserveMan'
        }, {
            title : '养护时间',
            align : 'center',
            field : 'conserveTime',
            formatter : function(value, row, index) {
                if (value == null) {
                    return null;
                }
                return moment(value).format('YYYY-MM-DD');
            }
        }, {
            title : '存放位置',
            align : 'center',
            field : 'propertyLocation'
        }, {
            title : '二维码',
            align : 'center',
            field : 'qrCode'
        }, {
            title : '当前状态',
            align : 'center',
            field : 'status',
            width : 200,
            formatter : function(value, row, index) {
                var arr = [];
                if (value == '0') {
                    arr.push('养护完成 ' + '<a class="btn btn-sm btn-flat btn-primary" onclick="returnKey(\'' + row.id + '\')">查看<a>');
                } else if (value == '1') {
                    arr.push('申请养护 ' + '<a class="btn btn-sm btn-flat btn-primary" onclick="returnKey(\'' + row.id + '\')">查看<a>');
                } else if (value == '2') {
                    arr.push('审核通过 ' + '<a class="btn btn-sm btn-flat btn-primary" onclick="returnKey(\'' + row.id + '\')">查看<a>');
                } else if (value == '3') {
                    arr.push('审核不通过 ' + '<a  class="btn btn-sm btn-flat btn-primary" oonclick="returnKey(\'' + row.id + '\')">查看<a>');
                } else {
                    arr.push('养护中 ' + '<a class="btn btn-sm btn-flat btn-danger" onclick="returnKey(\'' + row.id + '\')">交还钥匙<a>');
                }
                arr.push('<a class="btn btn-sm btn-flat btn-danger" onclick="remove(\'' + row.id + '\')">删除</a>');
                return arr.join(' ');
            }
        } ]
    });
});

/**
 * 添加车辆养护信息
 * 
 * @returns
 */
function add() {
    top.layer.open({
        type : 2,
        title : '新增车辆养护',
        area : getArea(1100, 680),
        content : locat + '/html/car/car-add.html',
        end : function() {
            $('#table-car').bootstrapTable('refresh');
        }
    })
}

/**
 * 交还钥匙
 * 
 * @param id
 *            养护记录id
 * @returns
 */
function returnKey(id) {
    top.layer.open({
        type : 2,
        area : getArea(1200, 600),
        title : '养护记录详情',
        content : locat + '/html/car/car-detail.html?id=' + id,
        end : refresh
    });
}

/**
 * 刷新养护记录
 * 
 * @returns
 */
function refresh() {
    var params = $('#form-search').serialize();
    $('#table-car').bootstrapTable('refresh', {
        url : locat + '/carConserve/list?' + params
    });
}

/**
 * 删除养护记录
 * 
 * @param id
 *            养护记录id
 * @returns
 */
function remove(id) {
    top.layer.confirm('确认删除该记录？', function(index) {
        top.layer.close(index);
        index = top.layer.load();
        $.post(locat + '/carConserve/delete', {
            id : id
        }).then(function(data) {
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
            top.layer.msg('删除失败，请联系管理员', {
                icon : 2
            });
        });
    });
}