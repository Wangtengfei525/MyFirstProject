$(function() {

    $('#select-status').select2();
    $('#select-status').bind('change', function() {
        refresh();
        if ($('#select-status').val() == '2') {
            $('#table-overdue').bootstrapTable('showColumn', 'returnTime');
            $('#btn-delay-batch').hide();
        } else {
            $('#table-overdue').bootstrapTable('hideColumn', 'returnTime');
            $('#btn-delay-batch').show();
        }
    });

    var tableconfig = {
        classes : 'table table-hover table-no-bordered table-striped',
        sidePagination : 'server',
        url : locat + '/secondment/overdues?' + getSearchParameters(),
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
        rowStyle : function(row, index) {

            if (row.returned) {
                return '';
            }

            var days = row.overdueDays;
            if (days <= 0) {
                return {
                    classes : 'info'
                };
            } else if (days == 0) {
                return {
                    classes : 'warning'
                };
            } else {
                return {
                    classes : 'danger'
                };
            }
        },
        columns : [ {
            checkbox : true
        }, {
            title : '序号',
            align : 'center',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, {
            field : 'propertyName',
            align : 'center',
            title : '财物名称'
        }, {
            field : 'caseName',
            align : 'center',
            title : '案件名称'
        }, {
            field : 'secondmentUnitName',
            align : 'center',
            title : '借调单位'
        }, {
            field : 'secondmentTime',
            align : 'center',
            title : '借调时间',
            width : 100,
            formatter : formatter
        }, {
            field : 'expectedReturnTime',
            align : 'center',
            title : '预计归还时间',
            width : 100,
            formatter : formatter
        }, {
            field : 'returnTime',
            align : 'center',
            title : '归还时间',
            width : 100,
            formatter : formatter
        }, {
            field : 'overdueDays',
            align : 'center',
            title : '状态',
            width : 100,
            formatter : function(value, row, index) {
                if (row.returned) {
                    return '已归还';
                }

                if (value < 0) {
                    return -value + '天后到期';
                } else if (value == 0) {
                    return '今天到期';
                } else {
                    return '已逾期' + value + '天';
                }

            },
        }, {
            field : 'id',
            align : 'center',
            title : '操作',
            width : 250,
            formatter : function(value, row, index) {
                var arr = [];
                if (!row.returned) {
                    arr.push('<a class="btn btn-sm btn-danger btn-flat" onclick="delay(\'' + value + '\')">延期</a>');
                }
                arr.push('<a class="btn btn-sm btn-default btn-flat" onclick="delayInfo(\'' + value + '\')">延期记录</a>');
                arr.push('<a class="btn btn-sm btn-default btn-flat" onclick="openDetailsView(\'' + row.exchangeId + '\')">借调信息</a>');
                return arr.join(' ');
            }
        } ],
        onDblClickRow : function(row) {
            openPropertyView(row.propertyId);
        }
    };

    $('#table-overdue').bootstrapTable(tableconfig);
    $('#table-overdue').bootstrapTable('hideColumn', 'returnTime');

});

function getSearchParameters() {
    return $('#form-search').serialize();
}

function formatter(value) {
    if (value == null)
        return null;
    return moment(value).format('YYYY-MM-DD');
}

function refresh() {
    $('#table-overdue').bootstrapTable('refresh', {
        url : locat + '/secondment/overdues?' + getSearchParameters()
    });
}

/**
 * 借调延期
 * 
 * @param id
 * @returns
 */
function delay(ids) {
    top.layer.open({
        type : 2,
        scrollabr : false,
        title : '借调延期',
        area : getArea(800, 450),
        maxmin : true,
        content : locat + '/html/secondment/delay.html?ids=' + ids,
        end : function() {
            refresh();
        }
    });
}

/**
 * 批量延期
 * 
 * @returns
 */
function delaySeleted() {
    var selections = $('#table-overdue').bootstrapTable('getSelections');

    if (selections.length === 0) {
        top.layer.msg('请选择要延期的记录', {
            icon : 2
        });
        return;
    }

    var i = 0;
    var idsArr = [];

    for (i = 0; i < selections.length; i++) {
        idsArr.push(selections[i].id);
    }

    delay(idsArr.join(','));
}

/**
 * 查看延期记录
 * 
 * @returns
 */
function delayInfo(id) {
    top.layer.open({
        type : 2,
        scrollabr : false,
        title : '借调延期历史',
        area : getArea(900, 500),
        maxmin : true,
        content : locat + '/html/secondment/delay-history.html?secondmentId=' + id,
        end : function() {
            refresh();
        }
    });
}
