$(function() {

    $.get(locat + '/system/category/list', {
        typeCode : 'car_conserve_content'
    }).then(function(data) {
        $.map(data.rows, function(row) {
            row.text = row.name;
            row.children = null;
        });
        $('#input-conserve-content').select2({
            data : data.rows,
            multiple : true,
            closeOnSelect : false
        });
        $('#input-conserve-content').bind('change', function() {
            var selected = $('#input-conserve-content').select2('data');
            var codeArr = [];
            var textArr = [];
            $.map(selected, function(data) {
                codeArr.push(data.code);
                textArr.push(data.name);
            });
            $('#input-conserve-content-code').val(codeArr.join(','));
            $('#input-conserve-content-name').val(textArr.join(','));
        });
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

    $('#table-car').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        onlyInfoPagination : true,
        striped : true,
        pagination : true,
        paginationLoop : false,
        escape : true,
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
            title : '财物名称',
            align : 'center',
            field : 'propertyName'
        }, {
            title : '单位',
            align : 'center',
            field : 'permitUnitName'
        }, {
            title : '存放位置',
            align : 'center',
            field : 'propertyLocation'
        }, {
            title : '养护内容',
            align : 'center',
            field : 'conserveTextName'
        } ]
    });

});

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

/**
 * 查询待养护信息
 * 
 * @returns
 */
function refresh() {
    var params = $('#form-param').serialize();
    console.log(typeof(params));
    $('#table-car').bootstrapTable('refresh', {
        url : locat + '/carConserve/need-conserve?' + params
    });
}

function test(){
	var params = $('#form-param').serialize();
    console.log(typeof(params));
    console.log(params);
}

/**
 * 添加分组
 * 
 * @returns
 */
function add() {
    var params = $('#form-param').serializeArray();
    
    console.log(params);

    top.layer.confirm('将按条件自动生成养护记录，是否继续？', function(index) {
        top.layer.close(index);
        index = top.layer.load();
        $.post(locat + '/carConserve/group/add', params).done(function(data) {
            top.layer.close(index);
            if (data.success) {
                top.layer.close(top.layer.getFrameIndex(window.name));
            }
            top.layer.msg(data.message);
        }).fail(function(data) {
            top.layer.close(index);
            top.layer.msg('新建任务失败');
        });
    });

}
