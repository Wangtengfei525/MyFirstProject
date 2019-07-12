$(function() {
    var ids = getParameter(window.location.search, 'ids');

    if (!ids) {
        top.layer.alert('未指定借调记录id，请联系管理员！');
        return;
    }

    $('#input-secondment-ids').val(ids);

    $('#input-delayed-return-time').datepicker({
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd',
        todayBtn : 'linked'
    });
    $('#input-delayed-return-time').datepicker('setDate', new Date());

});

/**
 * 延期
 * 
 * @returns
 */
function save() {
    top.layer.confirm('确认延期吗？', function(index) {
        top.layer.close(index);
        index = top.layer.load();
        var params = $('#form-delay').serializeArray();
        $.post(locat + '/secondment/delay/add', params).done(function(data) {
            top.layer.close(index);
            var icon = 2;
            if (data.success) {
                icon = 1;
                top.layer.close(top.layer.getFrameIndex(window.name));
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        }).fail(function(data) {
            top.layer.close(index);
            top.layer.msg('延期失败', {
                icon : 2
            });
        });
    })
}