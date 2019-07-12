$(function() {

   /* var id = getParameter(window.location.search, 'id');
    if (!id) {
        top.layer.alert('未指定交换记录id，请联系管理员！');
    }*/
	
	 var id = getParameter(window.location.search, 'id');
	    if (!id) {
	        top.layer.alert('未指定具体的财物，请联系管理员！');
	    }
    
    

    var tempId = uuid();

    var datepickerconfig = {
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd',
        todayBtn : 'linked'
    };

    $('#input-operation-time').datepicker(datepickerconfig);
    $('#input-expected-return-time').datepicker(datepickerconfig);

    // 事件绑定
    //之前的这个id是交换记录(exchange)的id，现在的这个id是财物表(Property)的id 
    
    $('#btn-secondment').click(function() {
        secondment(id, tempId);
    });
    $('#btn-print-detail').click(function() {
        printDetail(id);
    });
    $('#btn-print-receipt').click(function() {
        printReceipt(id);
    });
    $('#btn-batch-assign').click(function() {
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

    $('#btn-capture').click(function() {
        var url = null;
        if ('ActiveXObject' in window) {
            url = locat + '/html/annex/capture.html?CaptureID=' + tempId;
        } else {
            url = locat + '/html/annex/photo.html?exchangeId=' + tempId
        }
        console.log(url);
        // 图像采集
        top.layer.open({
            type : 2,
            title : '图像采集',
            area : getArea(1400, 940),
            content : url,
            maxmin : true,
            end : getFiles
        });
    });

    $('#btn-upload').click(function() {
        openUpload(tempId);
    });

    // 初始化财物表格
    $('#table-property').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        striped : true,
        singleSelect : true,
        toolbar : '#div-property-toolbar',
        height : 330,
        url : locat + '/property/list/byexchange?exchangeId=' + id,
        columns : [ {
            checkbox : true
        }, {
            data : null,
            title : '序号',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, {
            field : 'propertyName',
            title : '名称'
        }, {
            field : 'propertyType',
            title : '类型'
        }, {
            field : 'number',
            title : '数量'
        }, {
            field : 'remake_number',
            title : '备注数量'
        }, {
            field : 'kwmc',
            title : '存储位置'
        }
        // , {
        // field : null,
        // title : '操作',
        // width : 280,
        // formatter : function(value, row, index) {
        // var arr = [];
        // arr.push('<a class="btn btn-xs btn-flat btn-danger"
        // onclick="openStore(\'' + row.kwbm + '\');">开柜</a>');
        // return arr.join(' ');
        // }
        // }
        ],
        onDblClickRow : function(row) {
            openPropertyView(row.id);
        }
    });

    // 初始化附件表格
    $('#table-files').bootstrapTable({
        url : locat + '/annex/list?exchangeId=' + tempId,
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        toolbar : '#div-file-toolbar',
        striped : true,
        height : 340,
        columns : [ {
            title : '序号',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, {
            field : 'file_name',
            title : '名称'
        }, {
            field : 'suffix_name',
            title : '后缀名'
        }, {
            field : 'id',
            title : '操作',
            width : 280,
            formatter : function(value, row, index) {
                var arr = [];
                arr.push('<a class="btn btn-xs btn-flat btn-primary" onclick="downloadAnnex(\'' + row.id + '\')">下载</a>');
                arr.push('<a class="btn btn-xs btn-flat btn-danger" onclick="deleteAnnex(\'' + row.id + '\')">删除</a>');
                return arr.join(' ');
            }
        } ]
    });

    // 获取交换记录数据
  //  getExchange(id);
    
    
    getPropertyInfo(id);

});

/**
 * 刷新财物数据
 * 
 * @param id
 *            交换记录id
 * @returns
 */
function getProperties(id) {
    $('#table-property').bootstrapTable('refresh');
}

/**
 * 刷新附件数据
 * 
 * @param id
 *            交换记录id
 * @returns
 */
function getFiles(id) {
    $('#table-files').bootstrapTable('refresh');
}

/**
 * 获取交换记录信息
 * 
 * @param id
 * @returns
 */
/*function getExchange(id) {
    $.get(locat + '/exchange/get', {
        id : id
    }).then(function(data) {
        var date = new Date();
        $('#form-exchange').find('[name]').each(function(index, element) {
            var $ele = $(element);
            var name = $ele.attr('name');
            $ele.val(data.exchange[name]);
        });
        $('#input-operation-time').datepicker('setDate', date);
        $('#input-expected-return-time').datepicker('setDate', moment(date).add(15, 'days').toDate());
        $('#input-remark').val('');
    });
}*/


/**
 * 现在是通过财物的id查出财物的相关信息 
 * @param id
 * @returns
 */

function getPropertyInfo(id) {
    $.get(locat + '/property/findPropertyById', {
        id : id
    }).then(function(data) {
        var date = new Date();
        $('#form-exchange').find('[name]').each(function(index, element) {
            var $ele = $(element);
            var name = $ele.attr('name');
           // $ele.val(data.exchange[name]);
            $ele.val(data.property[name]);
            
            
        });
        $('#input-operation-time').datepicker('setDate', date);
        $('#input-expected-return-time').datepicker('setDate', moment(date).add(15, 'days').toDate());
        $('#input-remark').val('');
    });
}























/**
 * 分配入柜
 * 
 * @param ids
 *            逗号分隔财物id
 * @returns
 */
function assignStore(ids) {
    top.layer.open({
        type : 2,
        title : '分配入柜',
        area : [ '800px', '420px' ],
        content : locat + '/html/store/assign-store.html?ids=' + ids,
        end : function() {
            $('#table-property').bootstrapTable('refresh');
        }
    });
}

/**
 * 设置备注数量
 * 
 * @param id
 *            财物id
 * @param originNumber
 *            原数量
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
 * 开柜
 * 
 * @param id
 *            柜子id
 * @returns
 */
function openStore() {
    var a = $("#table-property").bootstrapTable('getSelections');
    if (a.length === 0) {
        top.layer.msg('未选中数据', {
            icon : 2
        });
        return;
    }
    var id = a[0].kwbm;
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
}

/**
 * 打印条码
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
 * 打印清单
 * 
 * @param id
 * @returns
 */
function printDetail(id) {
    window.open(locat + '/secondment/print-detail?id=' + id);
}

/**
 * 打印回执单
 * 
 * @param id
 * @returns
 */
function printReceipt(id) {
    window.open(locat + '/secondment/print-receipt?id=' + id);
}

/**
 * 借调
 * 
 * @param id
 *            交换记录id
 * @returns
 */
function secondment(id, tempId) {
    top.layer.confirm('确认借调吗？', function(index) {
        top.layer.close(index);
        index = top.layer.load();
        $.post(locat + '/secondment/second', {
            id : id,
            tempId : tempId,
            operationTime : $('#input-operation-time').val(),
            expectedReturnTime : $('#input-expected-return-time').val(),
            remark : $('#input-remark').val()
        }).done(function(data) {
            top.layer.close(index);
            var icon = 2;
            if (data.success) {
                icon = 1;
                top.layer.close(top.layer.getFrameIndex(window.name));
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        }).fail(function() {
            top.layer.close(index);
            top.layer.msg('借调失败', {
                icon : 2
            });
        });
    });
}

/**
 * 下载附件
 * 
 * @param id
 *            附件id
 * @returns
 */
function downloadAnnex(id) {
    window.open(locat + '/annex/download?id=' + id);
}

/**
 * 删除附件
 * 
 * @param id
 * @returns
 */
function deleteAnnex(id) {
    top.layer.confirm('确认删除该附件？', function(index) {
        top.layer.close(index);
        $.post(locat + '/annex/delete', {
            id : id
        }).then(function(data) {
            var icon = 2;
            if (data.success) {
                icon = 1;
                getFiles();
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        });
    });
}
