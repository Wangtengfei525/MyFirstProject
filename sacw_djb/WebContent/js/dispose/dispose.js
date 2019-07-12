'use strict';
var exchangeTable = $('#exchangeTable');
var searchForm = $('#search');

var exchangeService = {
	save : function() {
		if (!$('#editForm').valid()) {
			return;
		}
		var url;
		if ($('#id').val()) {
			url = locat + '/exchange/update'
		} else {
			url = locat + '/exchange/add'
		}
		var params = $('#editForm').serializeArray();
		$.post(url, params).done(function(data) {
			if (data.success) {
				// layer.closeAll();
				$('#editLayer').window('close');
				exchangeService.reload();
			}
			layer.alert(data.message);
		});
	},
	reload : function() {
		$('#exchangeTable').datagrid('reload');
	},
	edit : function() {
		var selected = $('#exchangeTable').datagrid('getSelections');
		if (selected.length != 1) {
			layer.alert('请选择要编辑的记录！');
			return;
		}
		$('#editForm').form('load', selected[0]);
		$('#editForm input[name="sendTime"]').val(dateFormatter(selected[0].sendTime));
		$('#annexTable').datagrid({
			url : locat + '/exchange/annex/list?exchangeId=' + selected[0].id,
			idFiled : 'id',
			pagination : false,
			fit : false,
			columns : [ [ {
				field : 'checked',
				checkbox : true
			}, {
				title : '附件名称',
				field : 'name',
				width : 100,
				sortable : true
			}, {
				title : '交换记录ID',
				field : 'exchangeId',
				width : 30,
				sortable : true
			} ] ]

		});
		$('#propertyTable').datagrid({
			url : locat + '/property/list/byexchange?exchangeId=' + selected[0].id,
			idFiled : 'id',
			pagination : false,
			fit : false,
			columns : [ [ {
				field : 'checked',
				checkbox : true
			}, {
				title : '财物名称',
				field : 'propertyName',
				width : 100
			}, {
				title : '财物类型',
				field : 'propertyType',
				width : 100
			}, {
				title : '存储位置',
				field : 'saveLocationMc',
				width : 100
			} ] ]

		});
		// var index = layer.open({
		// type : 1,
		// title : '编辑交换记录',
		// content : $('#editLayer'),
		// area : [ '600px', '400px' ]
		// });
		// layer.full(index);
		$('#editLayer').window({
			maximized : true
		});

	},
	del : function(id) {
		layer.confirm('确定要删除该记录吗？', function(index) {
			layer.close(index);
			$.post(locat + '/exchange/delete', {
				id : id
			}).done(function(data) {
				if (data.success) {
					exchangeService.reload();
				}
				layer.alert(data.message);
			});
		});
	},
	search : function() {
		var param = searchForm.serialize();
		exchangeTable.datagrid({
			url : locat + '/exchange/list?' + param
		});
	}
};

exchangeTable.datagrid({
	url : locat + '/exchange/list',
	idFiled : 'id',
	toolbar : '#toolbar',
	queryParams : {
		sort : 'sendTime',
		order : 'desc'
	},
	columns : [ [ {
		field : 'checked',
		checkbox : true
	}, {
		title : '案件名称',
		field : 'caseName',
		width : 100,
		sortable : true
	}, {
		title : '交换操作名称',
		field : 'exchangeOperationName',
		width : 30,
		sortable : true
	}, {
		title : '发送单位名称',
		field : 'sendUnitName',
		width : 70,
		sortable : true
	}, {
		title : '发送人姓名',
		field : 'sendPersonName',
		width : 30,
		sortable : true
	}, {
		title : '发送时间',
		field : 'sendTime',
		width : 45,
		formatter : dateFormatter,
		sortable : true
	}, {
		title : '操作结果',
		field : 'operationResult',
		width : 50,
		sortable : true
	}, {
		title : '接收状态',
		field : 'receiveState',
		width : 20,
		formatter : function(value) {
			return value === 'Y' ? '已接收' : '未接收';
		},
		sortable : true
	}, {
		title : '操作',
		field : 'id',
		width : 20,
		formatter : function(value) {
			var str = '<a onclick="exchangeService.del(\'' + value + '\')">删除<a>';
			return str;
		}
	} ] ],
	onDblClickRow : exchangeService.edit
});

// editForm.validate();
