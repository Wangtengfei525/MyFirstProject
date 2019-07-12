$('#input-start-time,#input-end-time').datepicker({
    autoclose : true,
    language : 'zh-CN',
    format : 'yyyy-mm-dd 00:00:00'
});

$('#type').select2();

$('#logTable').bootstrapTable({
    url : locat + '/system/log/list',
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
        title : '类型',
        field : 'type',
        formatter : function(value) {
            if (value != null) {
                var typename = null;
                switch (value) {
                case 1:
                    typename = "访问日志";
                    break;
                case 2:
                    typename = "异常日志";
                    break;
                case 3:
                    typename = "操作日志";
                    break;
                case 4:
                    typename = "登录日志";
                    break;
                }
                return typename;
            } else {
                return null;
            }
        }
    }, {
        title : '内容',
        field : 'content',
    }, {
        title : '记录时间',
        field : 'logTime',
        formatter : function(value) {
            if (value == null)
                return null;
            return moment(value).format('YYYY-MM-DD HH:mm:ss');
        }
    } ] ]
});

function refresh() {
    var params = $('#form-search').serialize();
    $('#logTable').bootstrapTable('refresh', {
        url : locat + '/system/log/list?' + params
    });
}
