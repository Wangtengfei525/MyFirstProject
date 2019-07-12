$(function() {
    $('#select-status').select2();
    $('#select-status').bind('select2:select', function(e) {
        refresh();
        if ($('#select-status').val() != '0') {
            $('#table-back').bootstrapTable('hideColumn', 'id');
            $('#table-back').bootstrapTable('hideColumn', 'batchBack');
           /* $("#b1").attr("style","display:block;");*/
            
            $("#b1").attr("style","display:none;"); 
            
        } else {
            $('#table-back').bootstrapTable('showColumn', 'id');
            $('#table-back').bootstrapTable('showColumn', 'batchBack');
            /*$("#b1").attr("style","display:none;"); */
            
            $("#b1").attr("style","display:block;");
        }
        refresh();
    });

    $('#input-start-time').datepicker({
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd 00:00:00'
    });
    $('#input-end-time').datepicker({
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd 23:59:59'
    });
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

    // 初始化入库申请表格
    $('#table-back').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        url : locat + '/back/back-list',
        striped : true,
        pagination : true,
        paginationLoop : false,
        cache : false,
        escape : true,
        queryParams : function(params) {
            params.page = params.offset / params.limit + 1;
            params.rows = params.limit;
            return params;
        },
        columns : [
        	
        	/* {
        		 field:"batchBack",
                 title:'是否归还',
              	formatter:function(value,row,index)
              	{
              		return '<input   type="checkbox"   value=' + row.id + '        name="check"     />';
              	} 	
              },*/
              
        	/*{
        		checkbox:true
        	},     
              */
      
        	{
            title : '序号',
            align : 'center',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, 
        {
            title : '案件名称',
            align : 'center',
            field : 'caseName',
        },
        {
            title : '案件单位',
            align : 'center',
            field : 'organizerName',
        },
        
        
        
        /* {
            title : '财物类型',
            align : 'center',
            field : 'propertyType',
        }, 
        {
            title : '数量',
            align : 'center',
            field : 'number',
        },    */
       /* {
            title : '财物状态',
            align : 'center',
            field : 'propertyStatus',
        },*/
        
        /*{
            title : '存储位置',
            align : 'center',
            field : 'kwmc',
        },*/
       
        {
        	 field : 'createTime',
             align : 'center',
             title : '案件日期',
             formatter : function(value, row, index) {
                 if (value == null) {
                     return null;
                 }
                 return moment(value).format('YYYY-MM-DD HH:mm:ss');
             }
        }, 
        /*{
            title : '二维码',
            align : 'center',
            field : 'qrCode',
        },*/
        {
            field : 'remark',
            align : 'center',
            title : '备注'
        }
        
        , 
          {
            field : 'id',
            title : '操作',
            align : 'center',
            width : '120px',
            formatter : function(value, row, index) {
                var arr = [];
                arr.push('<a class="btn btn-sm btn-flat btn-primary" onclick="openBack(\'' + row.id + '\',\'' + row.organizerCode + '\')">归还</a>');
               // arr.push('<a class="btn btn-sm btn-flat btn-danger" onclick="sendBack(\'' + row.id + '\')">退回</a>');
                return arr.join(' ');
            }
        } 
        
        
        ],
        onDblClickRow : function(row) {
        	var  propertyStatus=$('#select-status option:selected').val();//选中的值
        //	alert(propertyStatus);
        	var   propertyStatusCode="";
        	if(propertyStatus==="0")
        		{
        		propertyStatusCode="9911000000006";//未归还的查询出来的是已经借调的财物
        			//alert(propertyStatusCode);
        		}
        	else
        		{
        		propertyStatusCode="9911000000005";
        			//alert(propertyStatusCode);
        		}
        	
        	
        	
            openBackDetailsView(row.id,propertyStatusCode);
        }
    });

});

/**
 * 打开归还对话框
 * 
 * @param id
 *            交换记录id
 * @returns
 */
function openBack(id,organizerCode) {

    top.layer.open({
        type : 2,
        scrollabr : false,
        title : '财物归还',
        // area : [ '1600px', '800px' ],
        area : getArea(1600, 800),
        content : locat + '/html/back/batchBack.html?id=' + id+'&OrganizerCode='+organizerCode,
        end : function() {
            $('#table-back').bootstrapTable('refresh');
        }
    });

}

/**
 * 退回
 * 
 * @param id
 *            交换记录id
 * @returns
 */
function sendBack(id) {
    top.layer.prompt({
        formType : 2,
        title : '退回原因：'
    }, function(value, index, elem) {
        top.layer.close(index);
        index = top.layer.load();
        $.post(locat + '/back/send-back', {
            id : id,
            remark : value
        }).done(function(data) {
            top.layer.close(index);
            var icon = 2;
            if (data.success) {
                icon = 1;
            }
            top.layer.msg(data.message, {
                icon : icon
            });
            $('#table-back').bootstrapTable('refresh');
        }).fail(function() {
            top.layer.close(index);
        });
    });

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
 * 搜索记录
 * 
 * @param params
 * @returns
 */
function refresh() {
    var params = $('#form-search').serialize();
    $('#table-back').bootstrapTable('refresh', {
        url : locat + '/back/back-list?' + params
    });
}


/*function   BatchBack()
{
	
		 var propertyId=new Array();  //创建一个数组  来存储选中的财物的id 
		$('input[name="check"]:checked').each(function(){  
		     propertyId.push($(this).val());//向数组中添加元素 
		}); 
		var propertyNum=$("input[type='checkbox']:checked").length;  //获取被选中财物的数量 
		var propertyIds=propertyId.join(",");//将数组元素连接起来以构建一个字符串  
    //	alert(propertyIds);   
	//	var  choice=alert("确定要归还这"+propertyNum+"件财物吗?")	
    //	var   choice=window.confirm("确定这"+propertyNum+"件财物要进行归还操作吗?")
    	
    	if (propertyIds==null||propertyIds=="") {
		    top.layer.msg('请选择要归还的物品！', {
		        icon : 2
		    });
		    return;
		}
    	
    	
    	if(choice)
    		{
    		top.layer.open({
    	        type : 2,
    	        scrollabr : false,
    	        title : '财物批量归还',
    	        area : getArea(1600, 800),
    	        maxmin : true,
    	        content : locat + '/html/secondment/secondment-info.html?id=' + id, 
    	        content : locat + '/html/back/batchBack.html?propertyIds=' + propertyIds,
    	        
    	        end : function() {
    	            $('#table-back').bootstrapTable('refresh');
    	        }
    	    });
    	
    	
    		}
    	
}
*/
function  BatchBack()
{

	var selections = $('#table-back').bootstrapTable('getSelections');
	
	if (selections.length === 0) {
	    top.layer.msg('请选择要分配的物品！', {
	        icon : 2
	    });
	    return;
	}
	    var i = 0;
	   
	    var idsArr = [];
	    for (i = 0; i < selections.length; i++) {
	        idsArr.push(selections[i].id);
	      //  console.log(selections[i].id);
	    }
	   
	   
	    for (var i= 0; i < selections.length-1; i++) {
			 for(var j= i+1; j< selections.length; j++)
				 {
				 if(selections[i].organizerCode!=selections[j].organizerCode){
			    		top.layer.msg('不同单位的财物(车辆)不能批量归还', {
			                icon : 2
			            });
			    		return;
			    	}
				 
				 }
		    	
		 }
	    
	    var  propertyIds=idsArr.toString();
	  //  alert("财物的id组成的字符串是"+propertyIds);
	    
	    console.log(idsArr.toString());
	   // window.open(locat + '/put-in/newprint-detail?idlist=' + idsArr.toString());

	   
	    top.layer.open({
	        type : 2,
	        scrollabr : false,
	        title : '财物批量归还',
	        area : getArea(1600, 800),
	        maxmin : true,
	        /*content : locat + '/html/secondment/secondment-info.html?id=' + id,*/ 
	        content : locat + '/html/back/batchBack.html?propertyIds=' + propertyIds,
	        
	        end : function() {
	            $('#table-back').bootstrapTable('refresh');
	        }
	    });		











}


