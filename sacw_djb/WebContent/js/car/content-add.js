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
            $.map(selected, function(data) {
                codeArr.push(data.code);
            });
            $('#input-conserve-content-code').val(codeArr.join(','));
        });
    });

    $('#table-split').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        pagination : true,
        sidePagination : 'server',
        striped : true,
        url : locat + '/property/split/list/motor',
        singleSelect : true,
        clickToSelect : true,
        queryParams : function(params) {
            params.page = params.offset / params.limit + 1;
            params.rows = params.limit;
            return params;
        },
        columns : [ {
            checkbox : true
        }, {
            title : '序号',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, {
            field : 'case_name',
            title : '案件名称'
        }, {
            field : 'property_name',
            title : '财物名称'
        }, {
            field : 'number',
            title : '数量'
        },{
            field : 'remake',
            title : '备注说明'
        } ]
    });

});

/**
 * 刷新拆分财物表格
 * 
 * @returns
 */
function refresh() {
    var params = $('#form-search').serialize();
    $('#table-split').bootstrapTable('refresh', {
        url : locat + '/property/split/list/motor?' + params
    });
}

/**
 * 添加养护内容设置
 * 
 * @returns
 */
function addContents() {
    var codes = $('#input-conserve-content-code').val();
    var selections = $('#table-split').bootstrapTable('getSelections');
    if (codes.length == 0) {
        top.layer.msg('请至少选择一项养护内容', {
            icon : 2
        });
        return;
    }
    if (selections.length == 0) {
        top.layer.msg('请选择一辆机动车', {
            icon : 2
        });
        return;
    }

    var index = top.layer.load();
    $.post(locat + '/car-conserve/content/set', {
        splitId : selections[0].id,
        conserveContentCodes : codes
    }).done(function(data) {
        top.layer.close(index);
        var icon = 2;
        if (data.success) {
            icon = 1;
        }
        top.layer.msg(data.message, {
            icon : icon
        });
    }).fail(function() {
        top.layer.close(index);
        top.layer.msg('保存设置出错，请联系管理员', {
            icon : 2
        });
    });

}