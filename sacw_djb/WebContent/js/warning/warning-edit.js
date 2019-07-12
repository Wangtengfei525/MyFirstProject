$(function() {

    var id = getParameter(window.location.search, 'id');

    $('#input-camera-ip').inputmask();
    $('#input-rf-ip').inputmask();

    if (id) {
        getSetting(id);
    }

});

/**
 * 获取设置信息
 * 
 * @param id
 * @returns
 */
function getSetting(id) {
    var index = top.layer.load();
    $.get(locat + '/warning/setting/get', {
        id : id
    }).done(function(data) {
        $('#form-setting-edit').find('[name]').each(function(index, element) {
            var $ele = $(element);
            var name = $ele.attr('name');
            $ele.val(data.setting[name]);
        });
        top.layer.close(index);
    }).fail(function() {
        top.layer.close(index);
        top.layer.msg('加载设置信息失败，请联系管理员', {
            icon : 2
        });
    });
}

/**
 * 保存
 * 
 * @returns
 */
function save() {
    var url;
    var data = $('#form-setting-edit').serializeArray();
    if ($('#input-id').val()) {
        url = locat + '/warning/setting/update';
    } else {
        url = locat + '/warning/setting/add';
    }
    $.post(url, data).then(function(data) {
        var icon = 2;
        if (data.success) {
            icon = 1;
            top.layer.close(top.layer.getFrameIndex(window.name));
        }
        top.layer.msg(data.message, {
            icon : icon
        });
    });

}