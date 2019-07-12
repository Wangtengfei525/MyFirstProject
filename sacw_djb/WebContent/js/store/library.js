$(function() {

    // 库区输入框绑定事件
    $('#input-store').click(function() {
        var inputObj = $('#input-store');
        var offset = inputObj.offset();
        $("#div-tree-wrapper").css({
            left : offset.left + "px",
            top : offset.top + inputObj.outerHeight() + "px"
        }).slideDown("fast");
        $('body').bind('mousedown', onBodyDown);
    });

    // 初始化库区下拉树

    var settings = {
        async : {
            url : locat + '/store/queryAll',
            enable : true,
            type : 'get',
            dataFilter : function(treeId, parentNode, data) {
                return data.rows;
            }
        },
        data : {
            key : {
                children : 'children',
                name : 'storeName'
            }
        },
        callback : {
            onClick : function(event, treeId, treeNode) {
                $('#input-store-id').val(treeNode.id);
                $('#input-store').val(treeNode.storeUnitName);
                hideUnitTree();
            }
        }
    };
    $.fn.zTree.init($("#tree-store"), settings);

    // 初始化表格
    $('#table-library').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        striped : true,
        // pagination : true,
        paginationLoop : false,
        escape : true,
        queryParams : function(params) {
            params.page = params.offset / params.limit + 1;
            params.rows = params.limit;
            return params;
        },
        columns : [ {
            title : '案件名称',
            field : 'case_name',
            align : 'center',
            width : 100
        }, {
            title : '财物名称',
            field : 'property_name',
            align : 'center',
            width : 100
        }, {
            title : '财物数量',
            field : 'number',
            align : 'center',
            width : 100
        }, {
            title : '存放位置',
            field : 'save_location_mc',
            align : 'center',
            width : 200
        }, {
            title : '二维码',
            field : 'qr_code',
            align : 'center',
            width : 100
        }, {
            title : '是否异常',
            field : 'sfyc',
            align : 'center',
            width : 100,
            formatter : function(value, row, index) {
                return value === 0 ? '否' : '<span style="color:red;">是</span>';
            }
        } ]
    });
});

/**
 * 隐藏单位树
 * 
 * @returns
 */
function hideUnitTree() {
    $("#div-tree-wrapper").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

/**
 * 点击页面隐藏单位树
 * 
 * @param event
 * @returns
 */
function onBodyDown(event) {
    if (event.target.id == 'input-store' || event.target.id == 'div-tree-wrapper' || $(event.target).parents('#div-tree-wrapper').length > 0) {
        return;
    }
    hideUnitTree();
}
function fileUpload() {
    var parentId = $("input[name='parentId']").val();
    if (parentId == null || parentId == '') {
        top.layer.alert('请选择库区！');
    } else {

        var url = locat + '/store/export?parentId=' + parentId;
        var data = new FormData();
        var filedata = $("#file")[0].files[0];
        data.append('file', filedata);

        index = top.layer.load();

        $.ajax({
            type : "post",
            url : url,
            async : true,
            data : data,
            processData : false,
            contentType : false,
            cache : false,
            success : function(dat) {
                top.layer.close(index);
                if (dat) {
                    $('#table-library').bootstrapTable('load', {
                        total : dat.length,
                        rows : dat
                    });
                }
            },
            error : function(data) {
                top.layer.close(index);
            }
        });
    }
}
