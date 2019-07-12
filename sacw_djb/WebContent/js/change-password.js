function save() {
    var params = $('#form-password').serializeArray();

    var index = top.layer.load();
    $.post(locat + '/system/change-password', params).done(function(data) {
        var icon = 2;
        top.layer.close(index);
        if (data.success) {
            icon = 1;
            top.layer.close(top.layer.getFrameIndex(window.name));
        }
        top.layer.msg(data.message, {
            icon : icon
        });
    }).fail(function() {
        top.layer.close(index);
        top.layer.msg('密码修改失败，请联系管理员', {
            icon : 2
        });
    });

}