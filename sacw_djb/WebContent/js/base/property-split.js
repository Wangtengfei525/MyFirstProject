// 初始化财物表格
var cwid;

var property;

var rules = {
    splitName : {
        required : true,
        maxlength : 100
    },
    splitTypeCode : 'required',
    splitAmount : {
        required : true,
        max : Number.MAX_VALUE,
        min : 1
    },
    splitVolume : {
        max : Number.MAX_VALUE,
        min : 0
    },
    splitRemark : {
        maxlength : 100
    }
};

$(function() {

    cwid = getParameter(window.location.search, 'cwid');
    if (!cwid) {
        top.layer.alert('未指定财物id，请联系管理员！');
        return;
    }

    $('#input-property-id').val(cwid);

    $('#input-split-name').click(function() {
        var inputObj = $('#input-split-name');
        var offset = inputObj.offset();
        $("#split-wrapper").css({
            left : offset.left + "px",
            top : offset.top + inputObj.outerHeight() + "px"
        }).slideDown("fast");
        $('body').bind('mousedown', onBodyDown);
    });
    $('#splitSaveLocationName').click(function() {
        var inputObj = $('#splitSaveLocationName');
        var offset = inputObj.offset();
        $("#split-category").css({
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
                $('#input-split-name').val(treeNode.name);
                $('#input-split-type-code').val(treeNode.code);
                hideUnitTree();
            }
        }
    };
    var settings = {
            data : {
                key : {
                    children : 'children',
                    name : 'storeUnitName'
                }
            },
            callback : {
                onClick : function(event, treeId, treeNode, clickFlag) {
                    $('#splitSaveLocationName').val(treeNode.storeUnitName);
                    $('#splitSaveLocationCode').val(treeNode.id);
                    hideUnitTree2();
                }
            }
        };
    $.get(locat + '/system/category/tree?typeCode=9000001', function(data) {
    	var data2 = data;
        $.fn.zTree.init($("#tree-split"), setting, data);
    });
    $.get(locat + '/store/tree', function(data) {
    	var data2 = data;
    	$.fn.zTree.init($("#tree-category"), settings, data);
    });
    datafrom(cwid);
    initSplitTable();

    $('#form-split').validate({
        rules : rules
    });
});

function initSplitTable() {
    $('#cwcfsList').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        pagination : true,
        sidePagination : 'server',
        striped : true,
        url : locat + '/property/split/list?propertyId=' + cwid,
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
        }, {
            field : 'propertyName',
            title : '原财物名称'
        }, {
            field : 'splitName',
            title : '拆分后名称'
        }, {
            field : 'propertyAmount',
            title : '原数量'
        }, {
            field : 'splitAmount',
            title : '拆分后数量'
        }, {
            field : 'splitTypeName',
            title : '拆分后类型'
        }, {
            field : 'splitVolume',
            title : '体积'

        },  {
            field : 'splitSaveLocationName',
            title : '储存位置'

        }, {
            field : 'splitRemark',
            width : 180,
            title : '备注说明'
        }, {
            field : 'id',
            title : '操作',
            width : 120,
            formatter : function(value, row, index) {
                var arr = [];
                arr.push('<a class="btn btn-sm btn-flat btn-primary" onclick="update(\'' + row.id + '\');">修改</a>');
                arr.push('<a class="btn btn-sm btn-flat btn-danger" onclick="remove(\'' + row.id + '\',' + row.remake_number + ');">删除</a>');
                return arr.join(' ');
            }
        } ]
    });
}

// 表单赋值原数据
function datafrom(id) {
    var index = top.layer.load();
    $.get(locat + '/property/get', {
        id : id
    }).done(function(data) {
        property = data.property;
        if (property) {
            $('#form-exchange').find('[name]').each(function(index, element) {
                var $ele = $(element);
                var name = $ele.attr('name');
                if (property[name]) {
                    $ele.val(property[name]);
                }
            });
        }
        copyProperty();
        top.layer.close(index);
    }).fail(function() {
        top.layer.close(index);
        top.layer.msg('获取财物信息失败，请联系管理员', {
            icon : 2
        });
    });

}

/**
 * 拆分财物保存
 * 
 * @returns
 */
function cwcfsave() {
    var id = $('#input-split-id').val();
    var message, url;
    var valid = $('#form-split').valid(rules);
    if (!valid) {
        return;
    }
    if (id.length > 30) {
        url = locat + '/property/split/update';
        message = '确认更新该记录？';
    } else {
        url = locat + '/property/split/add';
        message = '确认添加财物拆分记录？';
    }

    top.layer.confirm(message, function(index) {
        top.layer.close(index);
        var arr = $('#form-split').serializeArray();
        var index = top.layer.load();
        $.post(url, arr).done(function(data) {
            top.layer.close(index);
            var icon = 2;
            if (data.success) {
                icon = 1;
                cwcfclear();
                $('#cwcfsList').bootstrapTable('refresh');
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        }).fail(function() {
            top.layer.close(index);
            top.layer.msg('删除失败，请联系管理员', {
                icon : 2
            });
        });
    });

}
/**
 * 拆分数据清空
 * 
 * @returns
 */
function cwcfclear() {
    $('#form-split')[0].reset();
    $('#btn-add').html('新增');
    copyProperty();
}

/**
 * 修改拆分信息
 * 
 * @param id
 * @returns
 */
function update(id) {
    var index = top.layer.load();
    $('#form-split')[0].reset();
    $.get(locat + '/property/split/get', {
        id : id
    }).then(function(data) {
        var split = data.split;
        if (split) {
            $('#form-split').find('[name]').each(function(index, element) {
                var $ele = $(element);
                var name = $ele.attr('name');
                $ele.val(split[name]);
            });
            $('#select-split-type-code').val(split.splitTypeCode);
            $('#select-split-type-code').trigger('change');
        }
        top.layer.close(index);
    });
    $('#btn-add').html('更新');
}

/**
 * 删除
 * 
 * @param id
 *            拆分财物id
 * @returns
 */
function remove(id) {
    top.layer.confirm('确认删除该拆分信息？', function(index) {
        var index = top.layer.load();
        $.post(locat + '/property/split/delete', {
            id : id
        }).done(function(data) {
            top.layer.close(index);
            var icon = 2;
            if (data.success) {
                icon = 1;
                $('#cwcfsList').bootstrapTable('refresh');
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        }).fail(function() {
            top.layer.close(index);
            top.layer.msg('删除失败，请联系管理员', {
                icon : 2
            });
        });
    });
}

/**
 * 隐藏单位树
 * 
 * @returns
 */
function hideUnitTree() {
	$("#split-wrapper").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
/**
 * 隐藏单位树
 * 
 * @returns
 */
function hideUnitTree2() {
	$("#split-category").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
/**
 * 点击页面隐藏单位树
 * 
 * @param event
 * @returns
 */
function onBodyDown(event) {
    if (event.target.id == 'input-split-name'||event.target.id == 'split-wrapper'|| $(event.target).parents('#split-wrapper').length > 0) {
    	hideUnitTree2();
    	return;
    }else if(event.target.id == 'splitSaveLocationName'||event.target.id == 'split-category'||$(event.target).parents('#split-category').length > 0){
    	hideUnitTree();
    	return;
    }else{
    	hideUnitTree2();
    	hideUnitTree();
    }
}

/**
 * 复制原财物属性到新财物属性中
 * 
 * @returns
 */
function copyProperty() {
    if (property) {
        $('#splitName').val(property.propertyName);
        $('#splitAmount').val(property.number);
       var saveLocationMc = property.kwmc;
    	var saveLocationCode = property.kwbm;
        $('#splitSaveLocationCode').val(saveLocationCode);
        $('#splitSaveLocationName').val(saveLocationMc);
    }

}