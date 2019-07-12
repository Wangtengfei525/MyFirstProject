$('#roleTable').bootstrapTable({
    url : locat + '/system/role/list',
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
        width : 10,
        formatter : function(value, row, index) {
            return index + 1;
        }
    }, {
        title : '角色名称',
        field : 'name'
    }, {
        title : '角色描述',
        field : 'description'
    }, {
        title : '创建时间',
        field : 'createTime',
        formatter : function(value) {
            if (value == null)
                return null;
            return moment(value).format('YYYY-MM-DD HH:mm:ss');
        }
    }, {
        title : '更新时间',
        field : 'updateTime',

        formatter : function(value) {
            if (value == null)
                return null;
            return moment(value).format('YYYY-MM-DD HH:mm:ss');
        }
    }, {
        field : 'id',
        title : '操作',
        width : 240,
        formatter : function(value, row, index) {
            var arr = [];
            arr.push('<a class="btn btn-sm btn-flat btn-default" onclick="openEdit(\'' + row.id + '\',\'' + row.name + '\',\'' + row.description + '\')">修改</a>');
            arr.push('<a class="btn btn-sm btn-flat btn-default" onclick="assignRole(\'' + row.id + '\',\'' + row.name + '\')">权限分配</a>');
            arr.push('<a class="btn btn-sm btn-flat btn-danger" onclick="deleteRoel(\'' + row.id + '\',\'' + row.name + '\')">删除</a>');
            return arr.join(' ');
        }
    } ] ]
});
var index;
// 角色新增
function add() {
    $('#id', "#editForm").val('');
    $("#name", "#editForm").val('');
    $("#description", "#editForm").val('');
    index = layer.open({
        type : 1,
        content : $("#editLayer"),
        area : [ '500px', '350px' ]
    });
}
// 角色修改
function openEdit(id, name, description) {
    $('#id', "#editForm").val(id);
    $("#name", "#editForm").val(name);
    $("#description", "#editForm").val(description);
    index = layer.open({
        type : 1,
        title : name + '角色信息修改',
        content : $("#editLayer"),
        area : [ '500px', '350px' ]
    });
}
// 角色删除
function deleteRoel(id, name) {

    top.layer.confirm('确认删除角色' + name + '吗？', function(index) {
        $.post(locat + '/system/role/delete', {
            ids : id
        }).then(function(data) {
            if (data.success) {
                top.layer.close(index);
                layer.alert('删除角色' + name + '成功！');
                $('#roleTable').bootstrapTable('refresh');
            }
        });
    })
}
var permissionTree;
// 角色权限分配
function assignRole(id, name) {
    $("#permissionRoleId").val(id);
    var settings = {
        check : {
            enable : true
        },
        async : {
            enable : true,
            url : locat + '/system/permission/list?roleId=' + id,
            dataFilter : function(treeId, parentNode, responseData) {
                return responseData.rows;
            },
            type : 'get'
        },
        data : {
            key : {
                url : ''
            },
            simpleData : {
                enable : true,
                idKey : 'id',
                pIdKey : 'parentId',
                rootId : null
            }
        }
    };
    permissionTree = $.fn.zTree.init($("#permissionUl"), settings);
    index = layer.open({
        title : '设置权限-[' + name + ']',
        type : 1,
        content : $("#permissionLayer"),
        area : [ '320px', '500px' ]
    });
}
// 角色权限保存
function permissionsave() {
    var nodes = permissionTree.getCheckedNodes();
    var id = $("#permissionRoleId").val();
    var permissions = [];
    for (var i = 0; i < nodes.length; i++) {
        permissions.push(nodes[i].id);
    }
    $.post(locat + '/system/role/assign-permissions', {
        id : id,
        permissions : permissions.join(',')
    }).done(function(data) {
        layer.close(index);
        layer.alert(data.message);
    });
}
// 角色信息保存
function save() {

    var url;
    if ($('#id').val()) {
        url = locat + '/system/role/update';
    } else {
        url = locat + '/system/role/add';
    }
    var params = $("#editForm").serializeArray();
    $.post(url, params).done(function(data) {
        if (data.success) {
            layer.close(index);
            $('#roleTable').bootstrapTable('refresh');
        }
        layer.alert(data.message);
    });
}
// 列表更新
function refresh() {
    $('#roleTable').bootstrapTable('refresh', {
        url : locat + '/system/log/list'
    });
}