'use strict';
var dictTable = $('#dictTable');
var searchForm = $('#search');

$('#dictTable').datagrid({
	url : locat + '/system/dict/list',
	toolbar : '#toolbar',
	idFiled : 'id',
	columns : [ [ {
		field : 'checked',
		checkbox : true
	}, {
		title : '字典名称',
		field : 'codeText',
		width : 50,
		sortable : true
	}, {
		title : '字典代码',
		field : 'code',
		width : 50,
		sortable : true
	}, {
		title : '字典值',
		field : 'value',
		width : 50
	}, {
		title : '值描述',
		field : 'valueText',
		width : 50
	} ] ]
});

var dictService = {
	add : function() {
		$('#editForm').form('clear');
		$('#name').removeAttr('disabled');
		layer.open({
			type : 1,
			title : '添加字典',
			content : $('#editLayer'),
			area : [ '300px', '500px' ]
		});
	},
	save : function() {
		if (!$('#editForm').valid()) {
			return;
		}
		var url;
		if ($('#id').val()) {
			url = locat + '/system/dict/update'
		} else {
			url = locat + '/system/dict/add'
		}
		var params = $('#editForm').serializeArray();
		$.post(url, params).done(function(data) {
			if (data.success) {
				layer.closeAll();
				dictService.reload();
			}
			layer.alert(data.message);
		});
	},
	reload : function() {
		$('#dictTable').datagrid('reload');
	},
	edit : function() {
		var selected = $('#dictTable').datagrid('getSelections');
		if (selected.length != 1) {
			layer.alert('请选择要编辑的字典！');
			return;
		}
		$('#editForm').form('load', selected[0]);
		layer.open({
			type : 1,
			title : '编辑字典',
			content : $('#editLayer'),
			area : [ '300px', '500px' ]
		});
	},
	del : function() {
		var selected = $('#dictTable').datagrid('getSelections');
		if (selected.length != 1) {
			layer.alert('请选择一个要删除的字典！');
			return;
		}
		layer.confirm('确定要删除选择的字典吗？', function(index) {
			layer.close(index);
			var id = selected[0].id;
			$.post(locat + '/system/dict/delete', {
				id : id
			}).done(function(data) {
				if (data.success) {
					dictService.reload();
				}
				layer.alert(data.message);
			});
		});
	},
	search : function() {
		debugger;
		var param = searchForm.serialize();
		dictTable.datagrid({
			url : locat + '/system/dict/list?' + param
		});
	}
};
   

editForm.validate();