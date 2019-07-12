var propertyidarray=new Array();
$(function() {
    $('#select-status').select2();
    $('#select-status').bind('select2:select', function(e) {
        refresh();
        if ($('#select-status').val() != '9911000000003') {
            $('#table-putin').bootstrapTable('hideColumn', 'id');
        } else {
            $('#table-putin').bootstrapTable('showColumn', 'id');
        }
    });

    $('#input-start-time').datepicker({
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd 00:00:00'
    });
    $('#input-end-time').datepicker({
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd 23:59:59'
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

    /*
    
    // 初始化入库申请表格
    $('#table-putin').bootstrapTable({
        classes : 'table table-hover table-no-bordered table-striped',
        sidePagination : 'server',
        url : locat + '/put-in/newputin-list',
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
            align : 'center',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, {
            field : 'property_name',
            align : 'center',
            title : '财物名称'
        }, {
            field : 'case_name',
            align : 'center',
            title : '案件名称'
        }, {
            field : 'create_name',
            align : 'center',
            title : '时间',
            formatter : function(value, row, index) {
                if (value == null) {
                    return null;
                }
                return moment(value).format('YYYY-MM-DD HH:mm:ss');
            }
        }, {
            field : 'remake',
            align : 'center',
            title : '备注'
        }, {
            field : 'id',
            title : '操作',
            align : 'center',
            width : '120px',
            formatter : function(id) {
                var arr = [];
                arr.push('<a class="btn btn-sm btn-flat btn-primary" onclick="openPutin(\'' + id + '\')">入库</a>');
                return arr.join(' ');
            }
        } ]
    });
    
    */
    
    var tablestring=$("#form-search").serialize();
    
 // 初始化入库申请表格
    $('#table-putin').bootstrapTable({
        classes : 'table table-hover table-no-bordered table-striped',
        sidePagination : 'server',
        url : locat + '/put-in/newputin-list?'+tablestring,
        striped : true,
        pagination : true,
        paginationLoop : false,
        cache : false,
        escape : true,
        clickToSelect:true,
//        queryParams : function(params) {
//            params.page = params.offset / params.limit + 1;
//            params.rows = params.limit;
//            console.log(params.toString());
//            return params;
//        },
        columns : [ {
        	checkbox:true
        },{
            title : '序号',
            align : 'center',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, {
            field : 'case_name',
            align : 'center',
            title : '案件名称'
        }, {
            field : 'organizer_name',
            align : 'center',
            title : '发送单位'
        }, {
            field : 'create_time',
            align : 'center',
            title : '登记日期',
            formatter : function(value, row, index) {
                if (value == null) {
                    return null;
                }
                return moment(value).format('YYYY-MM-DD HH:mm:ss');
            }
        }, {
            field : 'remark',
            align : 'center',
            title : '备注'
        }, {
            field : 'id',
            title : '操作',
            align : 'center',
            width : '200px',
            formatter : function(id) {
                var arr = [];
                //arr.push('<input onclick="getpropertyid(\''+id+'\')" type="checkbox" />');
                arr.push('<input class="btn btn-xs btn-flat btn-primary" onclick="openPutin(\''+id+'\')" type="button" value="入库">');
//                arr.push('<input class="btn btn-xs btn-flat btn-primary" onclick="newassignStore(\''+id+'\')" type="button" value="补录财物">');
//                arr.push('<input class="btn btn-xs btn-flat btn-primary" onclick="newassignStore(\''+id+'\')" type="button" value="打印清单">');
//                arr.push('<input class="btn btn-xs btn-flat btn-primary" onclick="newassignStore(\''+id+'\')" type="button" value="打印回执单">');
                return arr.join(' ');
            }
        },{
        	field:'id',
        	align:'center',
        	title:"是否补录",
        	formatter : function(id) {
                var arr = [];
                arr.push('<input class="btn btn-xs btn-flat btn-primary" onclick="newproperty(\''+id+'\')" type="button" value="补录财物">');
                return arr.join(' ');
            }
        } ],
        onDblClickRow : function(row) {
            newopenDetailsView(row.id);
        }
    });

});

/**
 * 
 * 批量入库
 * 
 * @returns
 */
function newmoreassignStore(){
	var selections = $('#table-putin').bootstrapTable('getSelections');
    var i = 0;
    var idsArr = [];
    if (selections.length === 0) {
        top.layer.msg('请选择要分配的物品！', {
            icon : 2
        });
        return;
    }
    for (i = 0; i < selections.length; i++) {
    	if(selections[i].property_type=="车辆"){
    		top.layer.msg('车辆不能批量入库', {
                icon : 2
            });
    		return;
    	}
    	
    	
        idsArr.push(selections[i].id);
        console.log(selections[i].id);
    }
    console.log(idsArr.toString());
    newassignStore(idsArr.join(','));
}


/**
 * 
 * 财物入库
 * 
 * @param ids
 * @returns
 */
function newassignStore(ids){
	top.layer.open({
        type : 2,
        title : '分配入柜',
        area : [ '900px', '520px' ],
        content : locat + '/html/store/newassign-store.html?ids=' + ids,
        end : function() {
            $('#table-putin').bootstrapTable('refresh');
        }
    });
}


function getpropertyid(id){
	propertyidarray.push(id);
	alert(propertyidarray.length);
}

/**
 * 打开入库对话框
 * 
 * @param id
 *            交换记录id
 * @returns
 */
function openPutin(id) {
	
	alert(id);
	
    top.layer.open({
        type : 2,
        scrollabr : false,
        title : '财物入库',
        area : getArea(1600, 800),
        maxmin : true,
        content : locat + '/html/putin/putin-info.html?id=' + id,
        end : function() {
            $('#table-putin').bootstrapTable('refresh');
        }
    });

}

/**
 * 单机版
 * 新增、登记
 * write by yiyu
 * 
 */
function newinsert(){
	top.layer.open({
		type : 2,
		scrollabr : true,
		title : "财物入库",
		area : getArea(1000,700),
		maxmin : true,
		content : locat + "/html/putin/newputin-info.html",
		end : function(){
			$("#table-putin").bootstrapTable("refresh");
		}
	});
}

/**
 * 打印清单
 * @returns
 */
function newprintdetail(){
	var selections = $('#table-putin').bootstrapTable('getSelections');
	
    var i = 0;
    
    if (selections.length === 0) {
        top.layer.msg('请选择要分配的物品！', {
            icon : 2
        });
        return;
    }
    var idsArr = [];
    for (i = 0; i < selections.length; i++) {
        idsArr.push(selections[i].id);
        console.log(selections[i].id);
    }
    console.log(idsArr.toString());
    window.open(locat + '/put-in/newprint-detail?idlist=' + idsArr.toString());
}


/**
 * 
 *打印清单
 * @returns
 */
function newprintreceipt(){
	var selections = $('#table-putin').bootstrapTable('getSelections');
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
        console.log(selections[i].id);
    }
    console.log(idsArr.toString());
	window.open(locat + '/put-in/print-receipt?idlist='+idsArr.toString());
}


/**
 * 退回
 * 
 * @param id
 *            交换记录id
 * @returns
 */
function sendBack(id) {
    top.layer.prompt({
        formType : 2,
        title : '退回原因：'
    }, function(value, index, elem) {
        top.layer.close(index);
        var i = top.layer.load();
        $.post(locat + '/put-in/send-back', {
            id : id,
            remark : value
        }).done(function(data) {
            top.layer.close(i);
            var icon = 2;
            if (data.success) {
                icon = 1;
            }
            top.layer.msg(data.message, {
                icon : icon
            });
            $('#table-putin').bootstrapTable('refresh');
        }).fail(function() {
            top.layer.close(i);
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

/**
 * 搜索记录
 * 
 * @param params
 * @returns
 */
function refresh() {
    var params = $('#form-search').serialize();
    $('#table-putin').bootstrapTable('refresh', {
        url : locat + '/put-in/newputin-list?' + params
    });
}

/**
 * 交换详情（包含财物信息、案件信息、交换记录信息、附件信息）
 * 
 * @param id
 *            财物id
 * @returns
 */
function newopenDetailsView(id) {
    top.layer.open({
        type : 2,
        title : '交换记录详情',
        area : getArea(950, 600),
        maxmin : true,
        content : locat + '/html/exchange/newdetail.html?cwid=' + id
    });
}

/**
*
* 补录页面
* 
*/
function newproperty(id){
	top.layer.open({
		type : 2,
		scrollabr : true,
		title : "财物补录",
		area : getArea(1000,700),
		maxmin : true,
		content : locat + "/html/putin/putproperty.html",
		end : function(){
			$("#table-putin").bootstrapTable("refresh");
		}
	});
}
