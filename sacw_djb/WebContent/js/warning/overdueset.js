$(function() {
    $('#code').select2();
    $('#code').bind('change', function() {
        searchProperty();
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

    $('#input-unit-name').click(function() {
        var inputObj = $('#input-unit-name');
        var offset = inputObj.offset();
        $("#unit-wrapper").css({
            left : offset.left + "px",
            top : offset.top + inputObj.outerHeight() + "px"
        }).slideDown("fast");
        $('body').bind('mousedown', onBodyDown);
    });

});

function searchProperty() {
    var params = $("#form-search").serialize();
    $("#table_properties").bootstrapTable('refresh', {
        url : locat + '/overdue/selectPropertiesByCode?' + params
    });
}

function excelExp() {
    var code = $("#code").val();
    window.location.href = locat + '/overdue/export?code=' + code;
}

$("#table_properties").bootstrapTable({
    classes : 'table table-hover table-no-bordered',
    sidePagination : 'server',
    pagination : true,
    striped : true,
    toolbar : '#div-property-toolbar',
    pagesize : 10,
    queryParams : function(params) {
        params.page = params.offset / params.limit + 1;
        params.rows = params.limit;
        return params;
    },
    url : locat + '/overdue/selectPropertiesByCode?id=9911000000001&statusCode=9911000000001',
    columns : [ {
        title : '序号',
        formatter : function(value, row, index) {
            return index + 1;
        }
    }, {
        title : '财物名称',
        field : 'property_name',
        align : 'center'
    }, {
        title : '财物数量',
        field : 'number',
        align : 'center'
    }, {
        title : '案件名称',
        field : 'case_name',
        align : 'center'
    }, {
        title : '处置权单位名称',
        field : 'permit_unit_mc',
        align : 'center'
    }, {
        title : '入库时间',
        field : 'register_date',
        formatter : dateFormatter,
        align : 'center'
    }, {
        title : '存放位置',
        field : 'save_location_mc',
        align : 'center'
    }, {
        title : '逾期天数',
        field : 'overdue_days',
        align : 'center'
    } ]
});

function show() {
    layer.open({
        type : 1,
        title : '预期设置',
        // area : [ '1400px', '940px' ],
        area : getArea(480, 270),
        content : $("#overduesetdiv")
    });
    $("input[type='number']").val('');
    spanData('9911000000001');
}

function submit1() {
    var code = $("#codeSetting").val();

    if ($("#days").val() || $("#daystx").val()) {
        $.getJSON(locat + '/overdue/updateSetting', {
            id : code,
            statusCode : code,
            warningPeriod : $("#days").val(),
            warningTime : $("#daystx").val()
        }, function(data) {
            if (data == 1) {
                top.layer.alert("修改成功");
                spanData($("#codeSetting").val());
            }
        });
    }
}

$("#codeSetting").change(function() {
    spanData($("#codeSetting").val());
});

function spanData(code) {
    $("#days").val('');
    $("#daystx").val('');
    $.getJSON(locat + '/overdue/setting?statusCode=' + code, function(data) {
        $("#span1").text("当前：" + data.warningPeriod + "天");
        $("#span2").text("当前：" + data.warningTime + "天");
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