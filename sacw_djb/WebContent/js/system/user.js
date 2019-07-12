function refresh() {
    var params = $('#form-search').serialize();
    $('#userTable').bootstrapTable('refresh', {
        url : locat + '/system/user/list?' + params
    });
}
// 初始化表格
$('#userTable').bootstrapTable({
    classes : 'table table-hover table-no-bordered',
    sidePagination : 'server',
    url : locat + '/system/user/list',
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
        width : 10,
        formatter : function(value, row, index) {
            return index + 1;
        }
    }, {
        title : '用户名',
        field : 'name'

    }, {
        title : '昵称',
        field : 'nickname'
    }, {
        title : '邮箱',
        field : 'email'
    }, {
        title : '手机',
        field : 'mobile'
    }, {
        title : '用户状态',
        field : 'state',
        width : 30,
        formatter : function(value) {
            return value === 1 ? '启用' : '禁用';
        }
    }, {
        title : '上次登录时间',
        field : 'lastLoginTime',
        formatter : function(value, row, index) {
            if (value == null) {
                return null;
            }
            return moment(value).format('YYYY-MM-DD HH:mm:ss');
        }
    }, {
        field : 'id',
        title : '操作',
        width : '220',
        formatter : function(value, row, index) {
            var arr = [];
            arr.push('<a class="btn btn-sm btn-flat btn-default" onclick="openOut(\'' + row.id + '\',\'edit\',\'' + row.nickname + '\')">修改</a>');
            arr.push('<a class="btn btn-sm btn-flat btn-default" onclick="assignRole(\'' + row.id + '\',\'' + row.name + '\')">角色分配</a>');
            arr.push('<a class="btn btn-sm btn-flat btn-danger" onclick="deleteuser(\'' + row.id + '\',\'' + row.name + '\')">删除</a>');
            return arr.join(' ');
        }
    } ]
});
var index;
/**
 * 打开用户信息窗口
 * 
 * @param id
 *            用户id
 * @returns
 */
function openOut(id, type, name) {
    var title = null;
    if (type == 'add') {
        title = '新增用户信息';
        $("input[type='text']", "#editForm").val('');
        $('input:radio:first', "#editForm").attr('checked', 'checked');
    } else if (type == 'edit') {
        title = '修改用户信息--' + name;
        userdataFrom(id);
    } else {
        title = '用户信息详情' + name;
        userdataFrom(id);
    }
    index = layer.open({
        type : 1,
        scrollabr : false,
        title : title,
        // area : [ '1600px', '800px' ],
        area : getArea(400, 540),
        content : $("#userinfodiv"),
        end : function() {
            $('#userTable').bootstrapTable('refresh');
        }
    });

}
function saveuserinfo() {
    var url;
    if ($('#id').val()) {
        url = locat + '/system/user/update';
    } else {
        url = locat + '/system/user/add';
    }
    var params = $('#editForm').serializeArray();
    $.post(url, params).done(function(data) {
        if (data.success) {
            layer.close(index);
            layer.alert('保存成功！');
            $('#userTable').bootstrapTable('refresh');
        }

    });
}
function userdataFrom(id) {

    $.get(locat + '/system/user/get', {
        id : id
    }).then(function(data) {
        $('#editForm').find('[name]').each(function(index, element) {
            var $ele = $(element);
            var name = $ele.attr('name');
            $ele.val(data.user[name]);
        });
    });
}
/**
 * 打开分配角色窗口
 * 
 * @param id
 *            用户id
 * @returns
 */
function assignRole(id, name) {
    $("#userid").val(id);
    if (id == 'b9ae1d8840084176a54038b5dc212424') {
        layer.alert('管理员禁止被分配角色');
        return;
    }
    index = layer.open({
        type : 1,
        title : '分配角色 - [ ' + name + ' ]',
        area : getArea(400, 540),
        content : $("#rloediv")
    });
    tree(id);
}
var treedata = null;
function tree(id) {

    var settings = {
        check : {
            enable : true
        },
        async : {
            enable : true,
            url : locat + '/system/role/list?userId=' + id,
            dataFilter : function(treeId, parentNode, responseData) {
                return responseData.rows;
            },
            type : 'get'
        },
        callback : {
            onAsyncSuccess : function() {
                // roleTree.expandAll(true);
            }
        }
    };
    treedata = $.fn.zTree.init($("#roleUl"), settings);
}
function saveRoles() {
    var nodes = treedata.getCheckedNodes();
    var id = $("#userid").val();
    var roles = [];
    for (var i = 0; i < nodes.length; i++) {
        roles.push(nodes[i].id);
    }
    $.post(locat + '/system/user/assign-roles', {
        id : id,
        roles : roles.join(',')
    }).done(function(data) {
        if (data.success) {
            layer.close(index);
            layer.alert('角色分配成功');

            $('#userTable').bootstrapTable('refresh');
        }
    });
}
function deleteuser(id, name) {
    top.layer.confirm('确认删除用户' + name + '吗？', function(index) {
        $.post(locat + '/system/user/delete', {
            ids : id
        }).then(function(data) {
            if (data.success) {
                top.layer.close(index);
                layer.alert('删除用户' + name + '成功！');
                $('#userTable').bootstrapTable('refresh');
            }
        });
    })
}