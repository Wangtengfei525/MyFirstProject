$(function() {

	initCategoryTypeTree();

	initCategoryTree();

	initCategoryTable();

});

/**
 * 初始化分类树
 * 
 * @returns
 */
function initCategoryTree() {
	var setting = {
		data : {
			key : {
				children : 'children',
				name : 'name'
			}
		},
		async : {
			url : locat + '/system/category/tree',
			enable : true,
			type : 'get',
			dataFilter : function(treeId, parentNode, responseData) {
				return responseData.rows;
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {

			}
		}
	};

	$.fn.zTree.init($("#tree-category"), setting, null);
}

/**
 * 初始化分类类型树
 * 
 * @returns
 */
function initCategoryTypeTree() {
	var setting = {
		data : {
			key : {
				children : 'children',
				name : 'name'
			}
		},
		async : {
			url : locat + '/system/category-type/list',
			enable : true,
			type : 'get',
			dataFilter : function(treeId, parentNode, responseData) {
				return responseData.rows;
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {
				$('#table-category').bootstrapTable('refresh', {
					url : locat + '/system/category/list?typeCode=' + treeNode.code
				});
			}
		}
	};

	$.fn.zTree.init($("#tree-category-type"), setting, null);
}

/**
 * 初始化分类表格
 * 
 * @returns
 */
function initCategoryTable() {

	$('#table-category').bootstrapTable({
		classes : 'table table-hover table-no-bordered',
		sidePagination : 'server',
		striped : true,
		pagination : true,
		paginationLoop : false,
		pageSize : 15,
		toolbar : '#toolbar',
		escape : true,
		queryParams : function(params) {
			params.page = params.offset / params.limit + 1;
			params.rows = params.limit;
			return params;
		},
		columns : [ {
			title : '序号',
			formatter : function(value, row, index) {
				return index + 1;
			}
		}, {
			title : '名称',
			field : 'name'
		}, {
			title : '代码',
			field : 'code'
		}, {
			title : '操作',
			field : 'id',
			formatter : function(value, row, index) {
				var arr = [];
				arr.push('<a class="btn btn-sm btn-flat btn-primary" onclick="updateCategory(\'' + row.id + '\');">编辑</a>');
				arr.push('<a class="btn btn-sm btn-flat btn-danger" onclick="removeCategory(\'' + row.id + '\');">删除</a>');
//				arr.push('<a class="btn btn-sm btn-flat btn-success" onclick="addChildren(\'' + row.id + '\');">添加下级</a>');
				return arr.join(' ');
			}
		} ]
	});
}

/**
 * 刷新类型树
 * 
 * @returns
 */
function refreshTypeTree() {
	$.fn.zTree.getZTreeObj("tree-category-type").reAsyncChildNodes(null, 'refresh');
}

/**
 * 删除类别
 * 
 * @returns
 */
function removeType() {
	var selected = $.fn.zTree.getZTreeObj("tree-category-type").getSelectedNodes();
	if (selected.length !== 1) {
		return;
	}

	top.layer.confirm('确认删除该分类吗？', function(index) {
		top.layer.close(index);
		$.post(locat + '/system/category-type/delete', {
			id : selected[0].id
		}).then(function(data) {
			var icon = 2;
			if (data.success) {
				icon = 1;
				refreshTypeTree();
			}
			top.layer.msg(data.message, {
				icon : icon
			});
		});
	});
}

/**
 * 添加类别
 * 
 * @param id
 *            类别id
 * @returns
 */
function addType(id) {

	top.layer.open({
		type : 2,
		area : getArea(450, 300),
		title : '添加字典类别',
		content : locat + '/html/system/category-type-edit.html',
		end : refreshTypeTree
	});
}

/**
 * 修改选中类别
 * 
 * @returns
 */
function updateType() {
	var selected = $.fn.zTree.getZTreeObj("tree-category-type").getSelectedNodes();
	if (selected.length !== 1) {
		top.layer.msg('请选择要修改的类别！');
		return;
	}
	top.layer.open({
		type : 2,
		area : getArea(450, 300),
		title : '修改字典类别',
		content : locat + '/html/system/category-type-edit.html?id=' + selected[0].id,
		end : refreshTypeTree
	});
}

/**
 * 新增字典项
 * 
 * @returns
 */
function addCategory() {
	var selected = $.fn.zTree.getZTreeObj("tree-category-type").getSelectedNodes();
	if (selected.length !== 1) {
		top.layer.msg('请选择字典类别！');
		return;
	}
	top.layer.open({
		type : 2,
		area : getArea(450, 300),
		title : '添加字典项',
		content : locat + '/html/system/category-edit.html?typeId=' + selected[0].id,
		end : function() {
			$('#table-category').bootstrapTable('refresh', {
				url : locat + '/system/category/list?typeCode=' + selected[0].code
			});
		}
	});

}

/**
 * 编辑字典项
 * 
 * @param id
 *            字典项id
 * @returns
 */
function updateCategory(id) {
	top.layer.open({
		type : 2,
		area : getArea(450, 320),
		title : '编辑字典项',
		content : locat + '/html/system/category-edit.html?id=' + id,
		end : function() {
			$('#table-category').bootstrapTable('refresh');
		}
	});

}

/**
 * 删除字典项
 * 
 * @param id
 *            字典项id
 * @returns
 */
function removeCategory(id) {

	top.layer.confirm('确认删除该字典项吗？', function(index) {
		top.layer.close(index);
		$.post(locat + '/system/category/delete', {
			id : id
		}).then(function(data) {
			var icon = 2;
			if (data.success) {
				icon = 1;
				$('#table-category').bootstrapTable('refresh');
			}
			top.layer.msg(data.message, {
				icon : icon
			});
		});
	})
}

function addChildren(id){
	top.layer.open({
		type : 2,
		area : getArea(450, 350),
		title : '添加下级',
		content : locat + '/html/system/category-addchildern.html?id=' + id,
		
	});
}