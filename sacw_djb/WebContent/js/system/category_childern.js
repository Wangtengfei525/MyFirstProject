	// 初始化单位下拉菜单
function shu(){
	var setting = {
			data : {
				key : {
					children : 'children',
					name : 'name'
				}
			},
			callback : {
				onClick : function(event, treeId, treeNode, clickFlag) {
					$('#input-split-name').val(treeNode.name);
					$('#input-split-id').val(treeNode.id);
					hideUnitTree();
				}
			}
		};
		$.get(locat + '/system/category/tree?typeCode=9000001', function(data) {
			$.fn.zTree.init($("#tree-split"), setting, data);
		});
}
	
	/**
	 * 隐藏单位树
	 * 
	 * @returns
	 */
	function hideUnitTree() {
		$("#split-wrapper").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}

	/**
	 * 点击页面隐藏单位树
	 * 
	 * @param event
	 * @returns
	 */
	function onBodyDown(event) {
		if (event.target.id == 'input-split-name' || event.target.id == 'split-wrapper' || $(event.target).parents('#split-wrapper').length > 0) {
			return;
		}
		hideUnitTree();
	}