$(function() {
    var settings = {
        async : {
            url : locat + '/store/category',
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
                refresh(treeNode.id);
            }
        }
    };

    $.get(locat + '/store/category').then(function(data) {
        $.fn.zTree.init($("#tree-category"), settings);
    });

    // 初始化财物表格
    $('#table-property').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        striped : true,
        height : 300,
        pagination : true,
        paginationLoop : false,
        pageSize : 5,
        pageList : [ 5, 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
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
        }, {
            title : '物品名称',
            field : 'propertyName'
        }, {
            title : '数量',
            field : 'number'
        }, {
            title : '备注数量',
            field : 'remake_number'
        }, {
            title : '物品类型',
            field : 'propertyType'
        }, {
            title : '案件名称',
            field : 'caseName'
        }, {
            title : '财物状态',
            field : 'propertyStatus'
        }, {
            title : '操作',
            field : 'id',
            formatter : function(value, row, index) {
                return '<a class="btn btn-xs btn-flat btn-primary" onclick="openPropertyView(\'' + row.id + '\')">详情<a>';
            }
        } ]
    });
});
/**
 * 获取（刷新）目录树
 * 
 * @returns
 */
function getCategory() {
    $.fn.zTree.getZTreeObj('tree-category').reAsyncChildNodes(null, 'refresh');
}

/**
 * 获取（刷新）财物表
 * 
 * @returns
 */
function getProperties(id) {
    $('#table-property').bootstrapTable('refresh', {
        url : locat + '/property/list?kwbm=' + id
    });
}

/**
 * 添加柜子
 * 
 * @returns
 */
function add() {
    var url = locat + '/html/store/store-edit.html';
    var selected = $.fn.zTree.getZTreeObj('tree-category').getSelectedNodes();
    if (selected.length == 1) {
        url += '?parentId=' + selected[0].id;
    }
    top.layer.open({
        type : 2,
        title : '添加储物柜',
        area : getArea(450, 500),
        content : url,
        end : function() {
            getCategory();
        }
    });
}

/**
 * 更新柜子
 * 
 * @param id
 *            柜子id
 * @returns
 */
function updateStore(id) {
    stopBubbling(event);
    top.layer.open({
        type : 2,
        title : '更新储物柜',
        area : getArea(450, 500),
        content : locat + '/html/store/store-edit.html?id=' + id,
        end : function() {
            refresh(id);
        }
    });
}

/**
 * 获取储物柜，生成展示区
 * 
 * @param parentId
 *            父级id
 * @returns
 */
function getStoresBak(parentId) {
    var index = top.layer.load();
    $.get(locat + '/store/queryByParentId', {
        parentId : parentId
    }).done(function(data) {
        top.layer.close(index);
        json = data.rows;
        $("#div-stores").empty();
        for (var i = 0; i < json.length; i++) {
            var id = json[i].id;
            generateStoreDiv(id, json[i].storeName);
        }
    }).fail(function() {
        top.layer.close(index);
        top.layer.msg('加载数据失败，请联系管理员', {
            icon : 2
        });
    });
}

/**
 * 获取储物柜，生成展示区
 * 
 * @param parentId
 *            父级id
 * @returns
 */
function getStores(id) {
    var index = top.layer.load();
    $.get(locat + '/store/children-with-amount', {
        id : id
    }).done(function(data) {
        json = data.rows;
        if (json.length == 0) {
            top.layer.close(index);
            return;
        }
        $("#div-stores").empty();
        for (var i = 0; i < json.length; i++) {
            var store = json[i];
            var str = null;
            var arr = [];
            var num = store.propertiesAmount;
            if (num != 0) {
                arr.push('<div onclick="refresh(\'' + store.id + '\')" class="store store-not-empty">');
            } else {
                arr.push('<div onclick="refresh(\'' + store.id + '\')" class="store store-empty">');
            }
            arr.push('<div><a class="btn btn-sm btn-flat btn-primary2" onclick="updateStore(\'' + store.id + '\')">修改</a>');
            arr.push('<a class="btn btn-sm btn-flat btn-danger2 pull-right" onclick="deleteStore(\'' + store.id + '\')">删除</a></div>');
            arr.push('<span>' + store.storeUnitName + '</span><br/><span>财物数量：' + num + '</span>');
            if (store.storeContro != '0') {
                arr.push('<div style="text-align: center;bottom:0px;"><button class="btn btn-xs btn-flat btn-danger2"  onclick="opens(\'' + store.id + '\')">开柜</button></div></div>');

            } else {
                arr.push('</div>');
            }
            $("#div-stores").append(arr.join(''));
        }
        top.layer.close(index);
    }).fail(function() {
        top.layer.close(index);
        top.layer.msg('加载数据失败，请联系管理员', {
            icon : 2
        });
    });
}

function generateStoreDiv(id, storeName, storeUnitName) {
    $.get(locat + '/store/queryProNumByStoreId', {
        id : id
    }).then(function(data) {
        var str = null;
        var arr = [];
        var num = data;
        if (num != 0) {
            arr.push('<div ondblclick="refresh(\'' + id + '\')" class="store store-not-empty">');
        } else {
            arr.push('<div ondblclick="refresh(\'' + id + '\')" class="store store-empty">');
        }
        arr.push('<div><a class="btn btn-sm btn-flat btn-primary" onclick="updateStore(\'' + id + '\')">修改</a>');
        arr.push('<a class="btn btn-sm btn-flat btn-danger pull-right" onclick="deleteStore(\'' + id + '\')">删除</a></div>');
        arr.push('<span>' + storeName + '</span><br/><span>财物数量：' + num + '</span>');
        arr.push('</div>');
        $("#div-stores").append(arr.join(''));
    });
}

/**
 * 刷新柜子及物品信息
 * 
 * @param id
 *            柜子id
 * @returns
 */
function refresh(id) {
    getStores(id);
    getProperties(id);
}

/**
 * 删除柜子
 * 
 * @param id
 * @returns
 */
function deleteStore(id, event) {
    stopBubbling(event);
    top.layer.confirm('确认删除该柜子？', function(index) {
        $.post(locat + '/store/delete', {
            id : id
        }).then(function(data) {
            var icon = 2;
            if (data.success) {
                icon = 1;
                getCategory();
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        });
    });
}

/**
 * 删除选中节点
 * 
 * @returns
 */
function deleteSelected() {
    var selected = $.fn.zTree.getZTreeObj('tree-category').getSelectedNodes();
    if (selected.length != 1) {
        return;
    }
    deleteStore(selected[0].id);
}

/**
 * 修改选中节点
 * 
 * @returns
 */
function updateSelected() {
    var selected = $.fn.zTree.getZTreeObj('tree-category').getSelectedNodes();
    if (selected.length != 1) {
        return;
    }
    updateStore(selected[0].id);
}
/**
 * 开柜子
 * 
 * @param id
 * @param m
 * @returns
 */
function opens(id, event) {
    $.post(locat + '/store/open', {
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
    stopBubbling(event);
}

/*******************************************************************************
 * 处理点击事件冲突冒泡法
 * 
 * @param e
 */
function stopBubbling(e) {
    e = window.event || e;
    if (e.stopPropagation) {
        e.stopPropagation(); // 阻止事件 冒泡传播
    } else {
        e.cancelBubble = true; // ie兼容
    }
}

/**
 * 导出
 * 
 * @returns
 */
function exp() {

    // 三秒后方可再次点击
    $('#btn-export').attr('disabled', true);

    setTimeout(function() {
        $('#btn-export').attr('disabled', false);
    }, 3000);

    var selected = $.fn.zTree.getZTreeObj('tree-category').getSelectedNodes();
    var url = locat + '/store/export';
    if (selected.length == 1) {
        url += '?id=' + selected[0].id;
    }

    window.open(url);
}