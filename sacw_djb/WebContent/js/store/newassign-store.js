$(function() {

    var ids = getParameter(window.location.search, 'ids');
    if (!ids) {
        top.layer.alert('请求不正确，请传入财物ID');
    }

    // 初始化目录树
    var setting = {
        data : {
            key : {
                children : 'children',
                name : 'storeName'
            }
        },
        callback : {
            onClick : function(event, treeId, treeNode, clickFlag) {
                $('#table-store').bootstrapTable('refresh', {
                    url : locat + '/store/queryByParentId',
                    query : {
                        parentId : treeNode.id
                    }
                });
            }
        },
        /*
        view : {
            addDiyDom : function(treeId, treeNode) {
                var $li = $('#' + treeNode.tId + '_a');
                $li.append('<button class="btn btn-default btn-xs btn-flat" style="margin-left:10px;" onclick="assignStore(\'' + treeNode.id + '\',\'' + ids + '\');">分配</button>');
            }
        }
        */
    };

    var index = top.layer.load();
    $.get(locat + '/store/queryAll').done(function(data) {
        $.fn.zTree.init($("#tree-category"), setting, data.rows);
        top.layer.close(index);
    }).fail(function() {
        top.layer.close(index);
    });

    // 初始化柜子表格
    $('#table-store').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        striped : true,
        height : 400,
        columns : [ {
            title : '序号',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, {
            field : 'storeUnitName',
            title : '名称'
        }, {
            field : 'storeContro',
            title : '是否智能柜',
            formatter : function(value, row, index) {
                return value === '0' ? '否' : '是';
            }
        }, {
            field : 'id',
            title : '操作',
            formatter : function(value, row, index) {
                // if (row.treeType === '1') {
                // return null;
                // }
                return '<a class="btn btn-xs btn-flat btn-primary" onclick="assignStore(\'' + row.id + '\',\'' + ids + '\');">分配</a>';
            }
        } ]
    });

});

/**
 * 分配入柜
 * 
 * @param storeId
 *            柜子id
 * @param ids
 *            财物id
 * @returns
 */
function assignStore(storeId, ids) {
    top.layer.confirm('确定分配到该柜吗？', function(index) {
        top.layer.close(index);
        $.post(locat + '/property/assign-store', {
            ids : ids,
            saveId : storeId
        }).then(function(data) {
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
