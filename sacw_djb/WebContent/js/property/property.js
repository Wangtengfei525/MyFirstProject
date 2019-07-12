$(function() {
	
    $('#select-splited').select2();
    $('#select-splited').bind('select2:select', function(e) {
        refresh();
    });
    $.get(locat + '/system/category/list', {
        typeCode : 'cwsx'
    }).then(function(data) {
        data.rows.unshift({
            name : '全部',
            code : ''
        });
        $.map(data.rows, function(row) {
            //row.text = row.name;
        	row.text = row.name;
            row.id = row.code;
            row.children = null;
        });
        $('#select-status').select2({
            data : data.rows
        });
        $('#select-status').bind('change', refresh);

        refresh();
    });
    $('#btn-batch-assigns').click(function() {
        // 批量入柜
        var selections = $('#table-property').bootstrapTable('getSelections');
        var i = 0;
        var idsArr = [];
        if (selections.length === 0) {
            top.layer.msg('请选择要分配的物品！', {
                icon : 2
            });
            return;
        }
        for (i = 0; i < selections.length; i++) {
            idsArr.push(selections[i].id);
        }
        assignStore(idsArr.join(','));
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
    $('#table-property').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        striped : true,
        pagination : true,
        paginationLoop : false,
        pageJump : true,
        escape : false,
       /* onDblClickRow : function(row) {
            openPropertyView(row.id);
        },*/
        
        queryParams : function(params) {
            params.page = params.offset / params.limit + 1;
            params.rows = params.limit;
            return params;
        },
        columns : [ [
        	
        /*	{
            checkbox : true
        },
        */
        {
            title : '序号',
            align : 'center',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, 
        
        {
            title : '财物名称',
            align : 'center',
            field : 'propertyName',
        }, {
            title : '财物类型',
            align : 'center',
            field : 'propertyType',
        }, {
            title : '数量',
            align : 'center',
            field : 'number',
        },    
        {
            title : '财物状态',
            align : 'center',
            field : 'propertyStatus',
        }, {
            title : '存储位置',
            align : 'center',
            field : 'kwmc',
        },
       
        {
        	 field : 'registerDate',
             align : 'center',
             title : '登记日期',
             formatter : function(value, row, index) {
                 if (value == null) {
                     return null;
                 }
                 return moment(value).format('YYYY-MM-DD HH:mm:ss');
             }
        }, 
        {
            title : '二维码',
            align : 'center',
            field : 'qrCode',
        },
        {
            field : 'remake',
            align : 'center',
            title : '备注'
        }, 
   
        {
            title : '操作',
            align : 'center',
            field : 'id',
            width : 230,
            formatter : function(value, row, index) {
                var arr = [];
                arr.push('<a class="btn btn-sm btn-flat btn-default" onclick="printCode(\'' + row.id + '\')" >打印条码<a>');
                arr.push('<a class="btn btn-sm btn-flat btn-default" onclick="setRemarkNumber(\'' + row.id + '\',' + row.remake_number + ');">备注数量</a>');
               // arr.push('<a class="btn btn-sm btn-flat btn-default" onclick="propertySplit(\'' + row.id + '\')">拆分<a>');
                return arr.join(' ');
            }
        } ] ]

    });
});

/**
 * 打印条形码
 * 
 * @param id
 *            财物id
 * @returns
 */
function printCode(id) {
    top.layer.confirm('确认打印条码？', function(index) {
        top.layer.close(index);
        $.post(locat + '/property/print-code', {
            id : id
        }).then(function(data) {
            var icon = 2;
            if (data.success) {
                icon = 1;
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        });
    });
}

/**
 * 刷新财物信息
 * 
 * @returns
 */
function refresh() {
    var params = $('#form-search').serialize();
  /*  var searchUrl =window.location.href;
    var searchData =searchUrl.split("=");        //截取 url中的“=”,获得“=”后面的参数
    var  searchText =decodeURI(searchData[1]);  
    */
    
    $('#table-property').bootstrapTable('refresh', {
        url : locat + '/property/list?' + params
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

/**
 * 
 * @param id
 * @param originNumber
 * @returns
 */
function setRemarkNumber(id, originNumber) {
    top.layer.prompt({
        formType : 0,
        value : originNumber,
        title : '备注数量：'
    }, function(value, index, elem) {
        var exp = /^\d{0,6}$/;
        if (!value.match(exp)) {
            top.layer.msg('输入类型错误，请输入数值型', {
                icon : 2
            });
            return;
        }
        top.layer.close(index);
        $.post(locat + '/property/update-remark-number', {
            id : id,
            number : value
        }).then(function(data) {
            var icon = 2;
            if (data.success) {
                icon = 1;
            }
            top.layer.msg(data.message, {
                icon : icon
            });
            $('#table-property').bootstrapTable('refresh');
        });
    });

}

/**
 * 库位重置
 * 
 * @param ids
 *            逗号分隔财物id
 * @returns
 */

function assignStore(ids) {
    top.layer.open({
        type : 2,
        title : '分配入柜',
        area : [ '900px', '520px' ],
        content : locat + '/html/store/assign-store.html?ids=' + ids,
        end : function() {
            $('#table-property').bootstrapTable('refresh');
        }
    });
}
