$(function() {
	var id = getParameter(window.location.search, 'id');
	var typeId = getParameter(window.location.search, 'typeId');

	// 传入id 更新
	if (id) {
		$('#btn-save').text('更新');
		var index = top.layer.load();
		$.get(locat + '/system/category/get', {
			id : id
		}).then(function(data) {
			top.layer.close(index);
			var category = data.category;
			if (category) {
				$('#form-category').find('[name]').each(function(index, element) {
					var $ele = $(element);
					var name = $ele.attr('name');
					$ele.val(category[name]);
				});
			}
		});
	} else if (typeId) {
		$('#input-type-id').val(typeId);
	} else {
		top.layer.msg('既未传入字典id或字典分类id，请联系管理员');
		top.layer.close(top.layer.getFrameIndex(window.name));
	}
});

function addCategory() {
	var title, id = $('#input-id').val();
	var arr = $('#form-category').serializeArray();
	if (id) {
		url = locat + '/system/category/update';
		title = '确认更新字典吗？';
	} else {
		url = locat + '/system/category/add';
		title = '确认添加字典吗？';
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