var groupId;

$(function() {

    groupId = getParameter(window.location.search, 'groupId');

    $('#input-conserve-time').datepicker({
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd 00:00:00',
        todayBtn : 'linked'
    });

    $('#input-qrcode').blur(loadProperty);

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
});

/**
 * 添加养护记录
 * 
 * @param complete
 *            添加后是否完成
 * @returns
 */
function addCarConserve(complete) {
    var status = complete ? '0' : '4';
    $('#input-status').val(status);

    var data = $('#form-add').serializeArray();
    console.log(JSON.stringify(data));
    var carConserveInfo={"status":data[1].value,"qrCode":data[2].value,
    		"conserveTime":data[3].value,"predictTime":data[4].value,"conserveMan":data[5].value,
    		"conserveTextName":data[6].value,"conserveText":data[7].value,"conserveRemark":data[8].value};
    console.log(JSON.stringify(carConserveInfo));
    
    /*
    $.post(locat + '/carConserve/add', data).then(function(data) {
        var icon = 2;
        if (data.success) {
            icon = 1;
            top.layer.close(top.layer.getFrameIndex(window.name));
        }
        top.layer.msg(data.message, {
            icon : icon
        });
    });
    */
    $.ajax({
    	url:locat+'/carConserve/add',
    	type:'post',
    	data:JSON.stringify(carConserveInfo),
    	contentType:'application/json;charset:UTF-8',
    	success:function(data){
    		var icon = 2;
            if (data.success) {
                icon = 1;
                top.layer.close(top.layer.getFrameIndex(window.name));
            }
            top.layer.msg(data.message, {
                icon : icon
            });
    	},
    	error:function(jqXHR){
    		console.log("error:"+jqXHR.status);
    	}
    });
}

/**
 * 根据输入二维码加载车辆信息
 * 
 * @returns
 */
function loadProperty() {
    var qrCode = $('#input-qrcode').val();
    if (!qrCode) {
        return;
    }
    var index = top.layer.load();
    $('#form-add input').val('');
    $.get(locat + '/property/get', {
        qrCode : qrCode
    }).then(function(data) {
        var property = data.property;
        if (property) {
            property.carMessage = property.goodsSpecial;
            property.propertyLocation = property.kwmc;
            $('#form-add').find('[name]').each(function(index, element) {
                var $ele = $(element);
                var name = $ele.attr('name');
                $ele.val(property[name]);
            });
            $("#caseName").val(property.case_name);
            $("#remake").val(property.remake);
            $("#predictTime").val('0.5');
            $('#input-conserve-time').datepicker('setDate', new Date());
            $('#input-group-id').val(groupId);
        }
        top.layer.close(index);
    });
}