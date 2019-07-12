$(function() {
	 /*var ids = getParameter(window.location.search, 'ids');
	    if (!ids) {
	        top.layer.alert('请求不正确，请传入财物ID');
	    }*/
	

	
	$("#test").val("hello");
	
	
	
	
	
	
	
    $('#select-status').select2();
    $('#select-status').bind('select2:select', function(e) {
        refresh();
        if ($('#select-status').val() != '0') {
            $('#table-secondment').bootstrapTable('hideColumn', 'id');
            $('#table-secondment').bootstrapTable('hideColumn', 'propertyTypeCode');
            /*$("#b1").attr("style","display:block;");*/
           
    	 /* $("#form-search").append("<p >我是子元素append</p>");
    	  
    	  $("#form-search").append('<input class="btn btn-default btn-flat"    name="name1"  value="选中的借调1"   onclick="BatchSecond();"/>');
    	*/
           // $("#b1").attr("style","display:none;");
            $("#b1").css('display','none'); 
            
            $("#b1").addClass("btn btn-xs btn-flat btn-primary")
            
           

            
        } else {
            $('#table-secondment').bootstrapTable('showColumn', 'id');
            $('#table-secondment').bootstrapTable('showColumn', 'propertyTypeCode'); 
           // $("#b1").attr("style","display:block;");
            $("#b1").css('display','block'); 
            
        }
      refresh();
        
    });

    var datepickerconfig = {
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd',
        todayBtn : 'linked'
    };

    $('#input-start-time').datepicker(datepickerconfig);
    $('#input-end-time').datepicker(datepickerconfig);

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
    
   /* $.get(locat + '/system/unit/tree', function(data) {
        $.fn.zTree.init($("#tree-unit"), setting, data);
    });*/
 
    // 初始化入库申请表格
    $('#table-secondment').bootstrapTable({
        classes : 'table table-hover table-no-bordered table-striped',
        sidePagination : 'server',
        url : locat + '/secondment/secondment-list',
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
        		 field : 'propertyTypeCode',
                   title:'是否借调',
                	formatter:function(value,row,index)
                	{		
                			return '<input   type="checkbox"  id="second" value=' + row.id + '        name="check"     />';            		
                	} 	
     		
                },*/
        	/*{
        		checkbox:true
        	},  */ 
        	
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
        
        
       /* {
            title : '财物所属单位编码',
            align : 'center',
            field : 'organizerCode',
        }, 
        */
        
        {
            title : '案件单位',
            align : 'center',
            field : 'organizerName',
        }, 
        
        
       /* {
            title : '数量',
            align : 'center',
            field : 'number',
        },    */
       /* {
            title : '财物状态',
            align : 'center',
            field : 'propertyStatus',
        },*/
        /*
        {
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
      /*  {
            title : '二维码',
            align : 'center',
            field : 'qrCode',
        },*/
        
        {
            field : 'remark',
            align : 'center',
            title : '案件备注'
        }
        
        , 
        {
            field : 'id',
            title : '操作',
            align : 'center',
            width : '120px',
            formatter : function(value, row, index) {
                var arr = [];
               // arr.push('<a class="btn btn-sm btn-flat btn-primary" onclick="openSecondment(\'' + row.id + '\')">借调</a>');
                
                /*arr.push('<a class="btn btn-sm btn-flat btn-primary" onclick="openSecondment(\'' + row.id + '\',\'' + row.organizerName + '\')">借调</a>');
                */
                
                arr.push('<a class="btn btn-sm btn-flat btn-primary" onclick="openSecondment(\'' + row.id + '\',\'' + row.organizerCode + '\')">借调</a>');
                     
                // return '<a class="btn btn-xs btn-flat btn-primary" onclick="assignStore(\'' + row.id + '\',\'' + ids + '\');">分配</a>';
            
               // arr.push('<a class="btn btn-sm btn-flat btn-danger" onclick="sendBack(\'' + row.id + '\')">退回</a>');
                return arr.join(' ');
            }
        } 
        
        ],
        onDblClickRow : function(row) {
        	//var status=
        	var  propertyStatus=$('#select-status option:selected').val();//选中的值
        //	alert(propertyStatus);
        	var   propertyStatusCode="";
        	if(propertyStatus==="0")
        		{
        		propertyStatusCode="yrkygh";//未借调的 财物查出来的       是已入库 和  已归还的财物
        			//alert(propertyStatusCode);
        		}
        	else
        		{
        		propertyStatusCode="9911000000006";
        			//alert(propertyStatusCode);
        		}
        	
        	
        	openSecondDetailsView(row.id,propertyStatusCode);
       
        
        }
        
       
             
    });

});


/**
 * 
 * @param id   此时的id是财物的id
 * @returns
 */





//传递过去的是案件的id
/*function openSecondment(id) {

    top.layer.open({
        type : 2,
        scrollabr : false,
        title : '财物借调',
        area : getArea(1600, 800),
        maxmin : true,
        content : locat + '/html/secondment/batchSecondment.html?id=' + id,
        end : function() {
            $('#table-secondment').bootstrapTable('refresh');
        }
    });

}*/

/*function openSecondment(id,organizerName) {

    top.layer.open({
        type : 2,
        scrollabr : false,
        title : '财物借调',
        area : getArea(1600, 800),
        maxmin : true,
        content : locat + '/html/secondment/batchSecondment.html?id=' + id+'&organizerName='+organizerName,
        end : function() {
            $('#table-secondment').bootstrapTable('refresh');
        }
    });

}*/

function openSecondment(id,organizerCode) {

    top.layer.open({
        type : 2,
        scrollabr : false,
        title : '财物借调',
        area : getArea(1600, 800),
        maxmin : true,
        content : locat + '/html/secondment/batchSecondment.html?id=' + id+'&OrganizerCode='+organizerCode,
        end : function() {
            $('#table-secondment').bootstrapTable('refresh');
        }
    });

}










/*function openBatchSecondment(id) {

    top.layer.open({
        type : 2,
        scrollabr : false,
        title : '财物批量借调',
        area : getArea(1600, 800),
        maxmin : true,
        content : locat + '/html/secondment/secondment-info.html?id=' + id, 
        content : locat + '/html/secondment/batchSecondment.html?id=' + id,
        
        end : function() {
            $('#table-secondment').bootstrapTable('refresh');
        }
    });

}
*/












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
        $.post(locat + '/secondment/send-back', {
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
            $('#table-secondment').bootstrapTable('refresh');
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
    $('#table-secondment').bootstrapTable('refresh', {
        url : locat + '/secondment/secondment-list?' + params
    });
}


/**
 * 查询逾期
 * 
 * @returns
 */
function overdues() {
    top.layer.open({
        type : 2,
        title : '逾期查询',
        area : getArea(1200, 600),
        maxmin : true,
        content : locat + '/html/secondment/overdue.html'
    });
}


//批量借调的方法

/*function   BatchSecond()
{
	
		 var propertyId=new Array();  //创建一个数组  来存储选中的财物的id 
		$('input[name="check"]:checked').each(function(){  
		     propertyId.push($(this).val());//向数组中添加元素 
		}); 
		var propertyNum=$("input[type='checkbox']:checked").length;  //获取被选中财物的数量 
		var propertyIds=propertyId.join(",");//将数组元素连接起来以构建一个字符串  
    	alert(propertyIds);   
			top.layer.confirm('确定要借调这'+propertyNum+'件财物吗？', function(index) {
		        top.layer.close(index);
		        $.post(locat + '/secondment/batchSecond', {		        	
		        	ids1 : ids1,
		            saveIds : storeIds
		        }).then(function(data) {
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
}*/



//

/*var selections = $('#table-putin').bootstrapTable('getSelections');
var i = 0;
var idsArr = [];
if (selections.length === 0) {
    top.layer.msg('请选择要分配的物品！', {
        icon : 2
    });
    return;
}
for (i = 0; i < selections.length; i++) {
    idsArr.push(selections[i].id);
    console.log(selections[i].id);
}
console.log(idsArr.toString());
newassignStore(idsArr.join(','));*/

/*function   BatchSecond()
{
	
		 var propertyId=new Array();  //创建一个数组  来存储选中的财物的id 
		$('input[name="check"]:checked').each(function(){  
		     propertyId.push($(this).val());//向数组中添加元素 
		}); 
		var propertyNum=$("input[type='checkbox']:checked").length;  //获取被选中财物的数量 
		var propertyIds=propertyId.join(",");//将数组元素连接起来以构建一个字符串  
    	alert(propertyIds);   
		var   choice=window.confirm("确定这"+propertyNum+"件财物要进行借调操作吗?")
		if(choice)
			{	
    		top.layer.open({
    	        type : 2,
    	        scrollabr : false,
    	        title : '财物批量借调',
    	        area : getArea(1600, 800),
    	        maxmin : true,
    	        content : locat + '/html/secondment/secondment-info.html?id=' + id, 
    	        content : locat + '/html/secondment/batchSecondment.html?propertyIds=' + propertyIds,
    	        
    	        end : function() {
    	            $('#table-secondment').bootstrapTable('refresh');
    	        }
    	    });
    		
 	
			}
    		
}
*/


/*function   BatchSecond()
{
	
		 var propertyId=new Array();  //创建一个数组  来存储选中的财物的id 
		$('input[name="check"]:checked').each(function(){  
		     propertyId.push($(this).val());//向数组中添加元素 
		}); 
		var propertyNum=$("input[type='checkbox']:checked").length;  //获取被选中财物的数量 
		var propertyIds=propertyId.join(",");//将数组元素连接起来以构建一个字符串  
    	alert(propertyIds);   
		
		
		if (propertyIds==null||propertyIds=="") {
		    top.layer.msg('请选择要借调的物品！', {
		        icon : 2
		    });
		    return;
		}
		
		var selections = $('#table-secondment').bootstrapTable('getSelections');
		console.log(typeof(selections));
		
		 for (i = 0; i < selections.length; i++) {
		    	if(selections[i].property_type=="车辆"){
		    		top.layer.msg('车辆不能批量入库', {
		                icon : 2
		            });
		    		return;
		    	}
		 }
		
		
		 for (var i= 0; i < selections.length-1; i++) {
			 for(var j= i+1; j< selections.length; j++)
				 {
				 if(selections[i].organizerCode!=selections[j].organizerCode){
			    		top.layer.msg('不同单位的财物(车辆)不能批量入库', {
			                icon : 2
			            });
			    		return;
			    	}
				 
				 }
		    	
		 }
		 
    		top.layer.open({
    	        type : 2,
    	        scrollabr : false,
    	        title : '财物批量借调',
    	        area : getArea(1600, 800),
    	        maxmin : true,
    	        content : locat + '/html/secondment/secondment-info.html?id=' + id, 
    	        content : locat + '/html/secondment/batchSecondment.html?propertyIds=' + propertyIds,
    	        
    	        end : function() {
    	            $('#table-secondment').bootstrapTable('refresh');
    	        }
    	    });		
}
*/


function    BatchSecond()
{
	

var selections = $('#table-secondment').bootstrapTable('getSelections');
	
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
		    		top.layer.msg('不同单位的财物(车辆)不能批量入库', {
		                icon : 2
		            });
		    		return;
		    	}
			 
			 }
	    	
	 }
    
    var  propertyIds=idsArr.toString();
   // alert("财物的id组成的字符串是"+propertyIds);
    
    console.log(idsArr.toString());
   // window.open(locat + '/put-in/newprint-detail?idlist=' + idsArr.toString());

   
    top.layer.open({
        type : 2,
        scrollabr : false,
        title : '财物批量借调',
        area : getArea(1600, 800),
        maxmin : true,
        /*content : locat + '/html/secondment/secondment-info.html?id=' + id,*/ 
        content : locat + '/html/secondment/batchSecondment.html?propertyIds=' + propertyIds,
        
        end : function() {
            $('#table-secondment').bootstrapTable('refresh');
        }
    });		














}




