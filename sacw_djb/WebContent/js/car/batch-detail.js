var groupId;

$(function() {

    groupId = getParameter(window.location.search, 'groupId');

    if (!groupId) {
        top.layer.msg('未提供养护组id，请联系管理员');
        return;
    }

    $('#input-group-id').val(groupId);

    getGroupInfo(groupId);

    var datepickerconfig = {
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd 00:00:00',
        todayBtn : 'linked'
    };

    $('#input-start-time').datepicker(datepickerconfig);
    $('#input-end-time').datepicker(datepickerconfig);

    // 初始化附件记录表格
    $('#table-files').bootstrapTable({
        url : locat + '/annex/list?exchangeId=' + groupId,
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        striped : true,
        columns : [ {
            title : '序号',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, {
            field : 'file_name',
            title : '附件名称'
        }, {
            field : 'suffix_name',
            title : '附件类型'
        }, {
            field : 'id',
            title : '操作',
            width : 280,
            formatter : function(value, row, index) {
                var arr = [];
                arr.push('<a class="btn btn-xs btn-flat btn-primary" onclick="downloadAnnex(\'' + row.id + '\')">下载</a>');
                arr.push('<a class="btn btn-xs btn-flat btn-danger" onclick="deleteAnnex(\'' + row.id + '\')">删除</a>');
                return arr.join(' ');
            }
        } ]
    });

    $('#table-car').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        url : locat + '/carConserve/list?groupId=' + groupId,
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

    $("#file").on("change", function() {
        var fileImg = $("#file")[0];
        var fileList = fileImg.files;

        var data = new FormData();
        for (var i = 0; i < fileList.length; i++) {
            data.append('file', fileList[i]);
        }
        var newurl = locat + "/annex/upload?exchangeId=" + groupId;
        var index = top.layer.load();
        $.ajax({
            type : "post",
            url : newurl,
            async : true,
            data : data,
            processData : false,
            contentType : false,
            cache : false,
            success : function(data) {
                top.layer.close(index);
                var icon = 2;
                if (data.success) {
                    icon = 1;
                    $('#table-files').bootstrapTable('refresh');
                }
                top.layer.msg(data.message, {
                    icon : icon
                });
            },
            error : function() {
                top.layer.close(index);
                top.layer.msg('上传失败，请联系管理员', {
                    icon : 2
                });
            }
        });
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
        area : getArea(1000, 550),
        content : locat + '/html/car/car-add.html?groupId=' + $('#input-group-id').val(),
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
                location.reload(true);
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

/**
 * 完成养护
 * 
 * @returns
 */
function complete() {
    var files = $('#input-file')[0].files;

    if (files.length == 0) {
        top.layer.msg('请选择养护任务表文件', {
            icon : 2
        });
        return;
    }

    var formData = new FormData();
    formData.append('id', groupId);
    formData.append('file', files[0]);

    var index = top.layer.load();
    $.ajax({
        type : 'post',
        url : locat + '/carConserve/group/complete',
        data : formData,
        contentType : false,
        processData : false,
        success : function(data) {
            top.layer.close(index);
            var icon = 2;
            if (data.success) {
                icon = 1;
                getGroupInfo(groupId);
                refresh();
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        },
        error : function() {
            top.layer.close(index);
            top.layer.msg('请求失败，请联系管理员', {
                icon : 2
            });
        }
    });

}

/**
 * 加载养护组信息
 * 
 * @param id
 * @returns
 */
function getGroupInfo(id) {
    var index = top.layer.load();
    $.get(locat + '/carConserve/group/get', {
        id : id
    }).then(function(data) {
        top.layer.close(index);
        if (!data.group) {
            return;
        }
        $('#form-group').find('[name]').each(function(index, element) {
            var $ele = $(element);
            var name = $ele.attr('name');
            $ele.val(data.group[name]);
        });
        $('#input-conserve-time').val(moment(data.group['conserveTime']).format('YYYY-MM-DD HH:mm:ss'));
        if (data.group.completed === 1) {
            $('#input-completed').val('已完成');
            $('.complete-conserve').hide();
        } else {
            $('#input-completed').val('未完成');
            $('.complete-conserve').show();
        }

    });
}

/**
 * 下载附件
 * 
 * @param id
 *            附件id
 * @returns
 */
function downloadAnnex(id) {
    window.open(locat + '/annex/download?id=' + id);
}

/**
 * 删除附件
 * 
 * @param id
 * @returns
 */
function deleteAnnex(id) {
    top.layer.confirm('确认删除该附件？', function(index) {
        top.layer.close(index);
        $.post(locat + '/annex/delete', {
            id : id
        }).then(function(data) {
            var icon = 2;
            if (data.success) {
                icon = 1;
                $('#table-files').bootstrapTable('refresh');
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        });
    });
}