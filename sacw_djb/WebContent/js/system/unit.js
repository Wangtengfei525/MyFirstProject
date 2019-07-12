$(function() {

    $.get(locat + '/system/category/list', {
        typeCode : '1000000000001'
    }).then(function(data) {
        $.map(data.rows, function(row) {
            row.text = row.name;
            row.id = row.code;
            // 加了children会被认为是分组而不能选择
            row.children = null;
        });
        $('#select-type').select2({
            data : data.rows
        });
    });

    var setting = {
        data : {
            key : {
                children : 'children',
                name : 'name'
            }
        },
        async : {
            url : locat + '/system/unit/tree',
            enable : true,
            type : 'get'
        },
        callback : {
            onClick : function(event, treeId, treeNode, clickFlag) {

                $('#form-unit')[0].reset();
                $('#btn-save').text('保存');

                $('#form-unit').find('[name]').each(function(index, element) {
                    var $ele = $(element);
                    var name = $ele.attr('name');
                    $ele.val(treeNode[name]);
                });
                $('#select-type').trigger('change');
                $('.not-update').attr('disabled', true);
            }
        }
    };

    $.fn.zTree.init($("#tree-unit"), setting, null);
});

/**
 * 删除选中单位
 * 
 * @returns
 */
function remove() {
    var selected = $.fn.zTree.getZTreeObj("tree-unit").getSelectedNodes();
    if (selected.length !== 1) {
        return;
    }

    top.layer.confirm('确认删除选中的单位吗？', function(index) {
        top.layer.close(index);

        $.post(locat + '/system/unit/delete', {
            id : selected[0].id
        }).then(function(data) {
            var icon = 2;
            if (data.success) {
                icon = 1;
                $.fn.zTree.getZTreeObj("tree-unit").reAsyncChildNodes(null, 'refresh');
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        });

    });
}

/**
 * 添加单位
 * 
 * @returns
 */
function add() {
    $('#form-unit')[0].reset();
    $('#btn-save').text('添加');
    var selected = $.fn.zTree.getZTreeObj("tree-unit").getSelectedNodes();
    if (selected.length === 1) {
        $('#select-type').val(selected[0].type);
        $('#input-parent-id').val(selected[0].id);
    }
    $('.not-update').attr('disabled', false);
    $('#input-id').val('');
    $('#select-type').trigger('change');
}

/**
 * 保存单位信息
 * 
 * @returns
 */
function save() {
    var arr = $('#form-unit').serializeArray();
    var url, text, id = $('#input-id').val();
    if (id) {
        url = locat + '/system/unit/update';
        text = '确认更新该单位信息吗？';
    } else {
        url = locat + '/system/unit/add';
        text = '确认添加该单位信息吗？';
    }

    top.layer.confirm(text, function(index) {
        top.layer.close(index);

        $.post(url, arr).then(function(data) {
            var icon = 2;
            if (data.success) {
                icon = 1;
                $.fn.zTree.getZTreeObj("tree-unit").reAsyncChildNodes(null, 'refresh');
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        });

    });
}
