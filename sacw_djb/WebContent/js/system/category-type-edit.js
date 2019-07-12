$(function() {
	var id = getParameter(window.location.search, 'id');
	if (id) {
		$('#btn-save').text('更新');
		var index = top.layer.load();
		$.get(locat + '/system/category-type/get', {
			id : id
		}).then(function(data) {
			top.layer.close(index);
			var type = data.type;
			if (type) {
				$('#form-type').find('[name]').each(function(index, element) {
					var $ele = $(element);
					var name = $ele.attr('name');
					$ele.val(data.type[name]);
				});
			}
		});
	}
});

function addType() {
	var title, id = $('#input-id').val();
	var arr = $('#form-type').serializeArray();
	if (id) {
		url = locat + '/system/category-type/update';
		title = '确认更新类别吗？';
	} else {
		url = locat + '/system/category-type/add';
		title = '确认添加类别吗？';
	}
	top.layer.confirm(title, function(index) {
		$.post(url, arr).then(function(data) {
			var icon = 2;
			if (data.success) {
				icon = 1;
				top.layer.close(top.layer.getFrameIndex(window.name));
			}
			top.layer.msg(data.message, {
				icon : icon
			});
		});
	});
}