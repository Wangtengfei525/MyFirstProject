$(function(){
	
	$('#input-unit-name').click(function() {
        var inputObj = $('#input-unit-name');
        var offset = inputObj.offset();
        $("#unit-wrapper").css({
            left : offset.left + "px",
            top : offset.top + inputObj.outerHeight() + "px"
        }).slideDown("fast");
        $('body').bind('mousedown', onBodyDown);
    });
	// 初始化单位下拉菜单
    var setting = {
        data : {
            key : {
                children : 'children',
                name : 'name'
            }
        },
        callback : {
            onClick : function(event, treeId, treeNode, clickFlag) {
                $('#input-unit-name').val(treeNode.name);
                $('#input-unit-id').val(treeNode.id);
                hideUnitTree();
            }
        }
    };

    $.get(locat + '/system/unit/tree', function(data) {
        $.fn.zTree.init($("#tree-unit"), setting, data);
    });
    
    var tablestring=$("#form-search").serialize();
    
    $.ajax({
    	url:locat + '/put-in/getallcaselist?'+tablestring,
    	type:'get',
		contentType:"application/json;charset=UTF-8",
    	success:function(data){
    		console.log(JSON.stringify(data));
    	},
    	error:function(jqXHR){
    		console.log("error"+jqXHR.status);
    	}
    });
    
    // 初始化入库申请表格
       $('#table-putin').bootstrapTable({
           classes : 'table table-hover table-no-bordered table-striped',
           sidePagination : 'server',
           url : locat + '/put-in/getallcaselist?'+tablestring,
           striped : true,
           pagination : true,
           paginationLoop : false,
           cache : false,
           escape : true,
           clickToSelect:true,
           columns : [ {
           	checkbox:true
           },{
               title : '序号',
               align : 'center',
               formatter : function(value, row, index) {
                   return index + 1;
               }
           }, {
               field : 'case_name',
               align : 'center',
               title : '案件名称'
           }, {
               field : 'organizer_name',
               align : 'center',
               title : '发送单位'
           }, {
               field : 'create_time',
               align : 'center',
               title : '登记日期',
               formatter : function(value, row, index) {
                   if (value == null) {
                       return null;
                   }
                   return moment(value).format('YYYY-MM-DD HH:mm:ss');
               }
           }, {
               field : 'remark',
               align : 'center',
               title : '备注'
           }, {
           	field:'id',
           	align:'center',
           	title:"是否补录",
           	formatter : function(id) {
                   var arr = [];
                   arr.push('<input class="btn btn-xs btn-flat btn-primary" onclick="newproperty(\''+id+'\')" type="button" value="补录财物">');
                   return arr.join(' ');
               }
           } ],
           onDblClickRow : function(row) {
               newopenDetailsView(row.id);
           }
       });
	
});

/**
 * 搜索记录
 * 
 * @param params
 * @returns
 */
function refresh() {
    var params = $('#form-search').serialize();
    $('#table-putin').bootstrapTable('refresh', {
        url : locat + '/put-in/getallcaselist?' + params
    });
}
/**
 * 点击页面隐藏单位树
 * 
 * @param event
 * @returns
 */
function onBodyDown(event) {
    if (event.target.id == 'input-unit-name' || event.target.id == 'unit-wrapper' || $(event.target).parents('#unit-wrapper').length > 0) {
        return;
    }
    hideUnitTree();
}
/**
 * 隐藏单位树
 * 
 * @returns
 */
function hideUnitTree() {
    $("#unit-wrapper").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

/**
 *
 * 补录页面
 * 
 */
function newproperty(id){
	top.layer.open({
		type : 2,
		scrollabr : true,
		title : "财物补录",
		area : getArea(1000,700),
		maxmin : true,
		content : locat + "/html/putin/putmoreproperty.html?id="+id,
		end : function(){
			$("#table-putin").bootstrapTable("refresh");
		}
	});
}

/**
 * 交换详情（包含财物信息、案件信息、交换记录信息、附件信息）
 * 
 * @param id
 *            财物id
 * @returns
 */
function newopenDetailsView(id) {
    top.layer.open({
        type : 2,
        title : '交换记录详情',
        area : getArea(950, 600),
        maxmin : true,
        content : locat + '/html/exchange/newdetail.html?cwid=' + id
    });
}
