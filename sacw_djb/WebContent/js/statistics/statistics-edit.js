$(function() {

    var id = getParameter(window.location.search, 'id');

    $('#select-type').select2();
    $('#select-contains-children').select2();

    // 初始化单位下拉菜单
    var setting = {
        data : {
            key : {
                children : 'children',
                name : 'name'
            }
        },
        callback : {
            onClick : function(event, treeId, treeNode, clickFlag) {
                $('#input-unit-name').val(treeNode.name);
                $('#input-unit-id').val(treeNode.id);
                hideUnitTree();
            }
        }
    };

    $.get(locat + '/system/unit/tree', function(data) {
        $.fn.zTree.init($("#tree-unit"), setting, data);
    });

    $('#input-unit-name').click(function() {
        var inputObj = $('#input-unit-name');
        var offset = inputObj.offset();
        $("#unit-wrapper").css({
            left : offset.left + "px",
            top : offset.top + inputObj.outerHeight() + "px"
        }).slideDown("fast");
        $('body').bind('mousedown', onBodyDown);
    });

    if (id) {
        getStore(id);
    }

});

/**
 * 获取统计单位信息
 * 
 * @param id
 * @returns
 */
function getStore(id) {
    $.get(locat + '/statistics/unit/get', {
        id : id
    }).then(function(data) {
        $('#form-unit-edit').find('[name]').each(function(index, element) {
            var $ele = $(element);
            var name = $ele.attr('name');
            $ele.val(data.unit[name]);
        });

        $('#select-type').trigger('change');
        $('#select-contains-children').trigger('change');
    });
}

function save() {
    var url;
    var data = $('#form-unit-edit').serializeArray();
    if ($('#input-id').val()) {
        url = locat + '/statistics/unit/update';
    } else {
        url = locat + '/statistics/unit/add';
    }

    $.post(url, data).then(function(data) {
        var icon = 2;
        if (data.success) {
            icon = 1;
            top.layer.close(top.layer.getFrameIndex(window.name));
        }
        top.layer.msg(data.message, {
            icon : icon
        });
    });

}

/**
 * 隐藏单位树
 * 
 * @returns
 */
function hideUnitTree() {
    $("#unit-wrapper").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

/**
 * 点击页面隐藏单位树
 * 
 * @param event
 * @returns
 */
function onBodyDown(event) {
    if (event.target.id == 'input-unit-name' || event.target.id == 'unit-wrapper' || $(event.target).parents('#unit-wrapper').length > 0) {
        return;
    }
    hideUnitTree();
}
