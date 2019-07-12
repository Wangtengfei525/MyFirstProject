$(function() {

    var id = getParameter(window.location.search, 'id');
    if (!id) {
        top.layer.alert('未定指定养护记录id，请联系管理员');
        return;
    }

    $('#btn-return-key').click(function() {
        returnKey(id);
    });

    var index = top.layer.load();
    $.get(locat + '/carConserve/get', {
        id : id
    }).then(function(data) {
        top.layer.close(index);
        if (!data.conserve) {
            return;
        }
        $('#form-detail').find('[name]').each(function(index, element) {
            var $ele = $(element);
            var name = $ele.attr('name');
            $ele.val(data.conserve[name]);
        });
        $('#input-conserve-time').val(moment(data.conserve['conserveTime']).format('YYYY-MM-DD HH:mm:ss'));
        if (data.conserve.exchangeKey === '0') {
            $('#input-return-key').val('否');
            $('#input-return-key').css('color', 'red');
            $('#btn-return-key').show();
            $('#btn-file').show();
        } else {
            $('#input-return-key').val('是');
            $('#btn-return-key').hide();
            $('#btn-file').hide();
        }

        initConserveTable(data.conserve.qrCode);
    });

    // 初始化附件记录表格
    $('#table-files').bootstrapTable({
        url : locat + '/annex/list?exchangeId=' + id,
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

    $("#file").on("change", function() {
        var fileImg = $("#file")[0];
        var fileList = fileImg.files;

        var data = new FormData();
        for (var i = 0; i < fileList.length; i++) {
            data.append('file', fileList[i]);
        }
        var newurl = locat + "/annex/upload?exchangeId=" + id;
        if (data) {
            $.ajax({
                type : "post",
                url : newurl,
                async : true,
                data : data,
                processData : false,
                contentType : false,
                cache : false,
                success : function(data) {
                    var icon = 2;
                    if (data.success) {
                        icon = 1;
                        $('#table-files').bootstrapTable('refresh');
                    }
                    top.layer.msg(data.message, {
                        icon : icon
                    });
                }
            });
        } else {
            alert('请打开控制台查看传递参数！');
        }
    });
});

/**
 * 初始化养护记录表格
 * 
 * @param qrCode
 * @returns
 */
function initConserveTable(qrCode) {
    // 初始化养护记录表格
    $('#table-conserve').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        url : locat + '/carConserve/list?qrCode=' + qrCode,
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
        },
        // {
        // title : '案件名称',
        // field : 'caseName'
        // }, {
        // title : '车辆信息',
        // field : 'carMessage',
        // formatter : function(value, row, index) {
        // var text;
        // if (value == null || value.length < 20) {
        // text = value;
        // } else {
        // text = value.substring(0, 20) + '……';
        // }
        // return text;
        //
        // }
        // },
        {
            title : '养护内容',
            field : 'conserveTextName'
        }, {
            title : '养护人',
            field : 'conserveMan'
        }, {
            title : '上次养护时间',
            field : 'conserveTime',
            formatter : function(value, row, index) {
                if (!value) {
                    return null;
                }
                return moment(value).format('YYYY-MM-DD HH:mm:ss')
            }
        }, {
            title : '存放位置',
            field : 'propertyLocation'
        }, {
            title : '当前状态',
            field : 'status',
            width : 200,
            formatter : function(value, row, index) {
                var arr = [];
                if (value === "4") {
                    arr.push('养护中 ' + '<a class="btn btn-sm btn-flat btn-danger" onclick="detail(\'' + row.id + '\');">交还钥匙<a>');
                } else if (value === '0') {
                    arr.push('养护完成 ' + '<a class="btn btn-sm btn-flat btn-primary" onclick="detail(\'' + row.id + '\');">查看<a>');
                } else if (value === '1') {
                    arr.push('申请养护 ' + '<a class="btn btn-sm btn-flat btn-primary" onclick="detail(\'' + row.id + '\');">查看<a>');
                } else if (value === '2') {
                    arr.push('审核通过 ' + '<a class="btn btn-sm btn-flat btn-primary" onclick="detail(\'' + row.id + '\');">查看<a>');
                } else if (value === '3') {
                    arr.push('审核不通过 ' + '<a class="btn btn-sm btn-flat btn-primary" onclick="detail(\'' + row.id + '\');">查看<a>');
                }
                arr.push('<a class="btn btn-sm btn-flat btn-danger" onclick="remove(\'' + row.id + '\')">删除</a>');
                return arr.join(' ');
            }
        } ]
    });
}

/**
 * 显示详情
 * 
 * @param id
 * @returns
 */
function detail(id) {
    window.location = locat + '/html/car/car-detail.html?id=' + id;
}

/**
 * 交还钥匙
 * 
 * @param id
 *            养护记录id
 * @param tempId
 *            附件临时id
 * @returns
 */
function returnKey(id, tempId) {
    top.layer.confirm('确认归还钥匙？', function(index) {
        top.layer.close(index);
        index = top.layer.load();
        $.post(locat + '/carConserve/return-key', {
            id : id
        }).then(function(data) {
            top.layer.close(index);
            var icon = 2;
            if (data.success) {
                icon = 1;
                top.layer.close(top.layer.getFrameIndex(window.name));
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        });
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
        });
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