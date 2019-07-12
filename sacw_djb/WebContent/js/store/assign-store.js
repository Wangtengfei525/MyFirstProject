$(function() {

    var ids = getParameter(window.location.search, 'ids');
    if (!ids) {
        top.layer.alert('请求不正确，请传入财物ID');
    }

    // 初始化目录树
    var setting = {
        data : {
            key : {
                children : 'children',
                name : 'storeName'
            }
        },
        callback : {
            onClick : function(event, treeId, treeNode, clickFlag) {
                $('#table-store').bootstrapTable('refresh', {
                    url : locat + '/store/queryByParentId',
                    query : {
                        parentId : treeNode.id
                    }
                });
            }
        },
        /*
        view : {
            addDiyDom : function(treeId, treeNode) {
                var $li = $('#' + treeNode.tId + '_a');
                $li.append('<button class="btn btn-default btn-xs btn-flat" style="margin-left:10px;" onclick="assignStore(\'' + treeNode.id + '\',\'' + ids + '\');">分配</button>');
            }
        }
        */
    };

    var index = top.layer.load();
    $.get(locat + '/store/queryAll').done(function(data) {
        $.fn.zTree.init($("#tree-category"), setting, data.rows);
        top.layer.close(index);
    }).fail(function() {
        top.layer.close(index);
    });

    // 初始化柜子表格
    $('#table-store').bootstrapTable({
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        striped : true,
        height : 400,
        columns : [ 
       
        {
            title : '序号',
            formatter : function(value, row, index) {
                return index + 1;
         }
        }, 
        {
        	title:'是否分配',
        	formatter:function(value,row,index)
        	{
        		return '<input   type="checkbox"   value=' + row.id + '        name="check"     />';
        	}
        	
        }
       ,
       {
    	 title:'1',
    	 formatter:function(value,row,index)
     	{
     		return '<input   type="hidden"   value=' + ids + '         id="ids"    name="ids"   />';
     	}  
       }
     ,
        {
            field : 'storeUnitName',
            title : '名称'
        }, 
        {
            field : 'storeContro',
            title : '是否智能柜',
            formatter : function(value, row, index) {
                return value === '0' ? '否' : '是';
            }
        }, 
        
        {
            field : 'id',
            title : '操作',
            formatter : function(value, row, index) {
                // if (row.treeType === '1') {
                // return null;
                // }
                return '<a class="btn btn-xs btn-flat btn-primary" onclick="assignStore(\'' + row.id + '\',\'' + ids + '\');">分配</a>';
           
            
            }
        } 
        
        
        
        ]
    });

});


/**
 * 
 * 
 * @returns
 */

/**
 * 这个实现的功能是这件物品分到多个柜子里面去   
 * 只有一个物品id   有多个柜子id
 * 
 * @returns
 */
/*function   assignManyStore(ids){
	top.layer.confim('确定分配到选中的这些柜子吗',function(index){

		var checkstoreId= [];
		 
		          $("input[name='check']:checked").each(function(i){//把所有被选中的复选框的值存入数组
		              checkID[i] =$(this).val();
		          });
		
}};*/

function  assignManyStore()
{
	//获取该财物的id  
	var  ids1=""
		 var id = document.getElementsByName("ids");//获取name为ids的对象
		 for (var i = 0; i < id.length; i++) {
			             /* if (cks[i].checked) {
			                 /* storeId += "storeId=" + cks[i].value + "&"; 
			                 storeId +=cks[i].value + ",";
			               alert(storeId);
			                 j++;
			             } */
			     ids1=id[0].value+"";
			         }	  
		/* alert("物品的id是"+ids1);
	*/

	var id=new Array();  //创建一个数组
	/*var   ids1="";*/
	$('input[name="check"]:checked').each(function(){  
	    id.push($(this).val());//向数组中添加元素  
	});  
	var storeIds=id.join(',');//将数组元素连接起来以构建一个字符串  
	 alert("物品的id是"+ids1);
	alert("所有柜子的id组成的字符串是"+storeIds);  //获取所有柜子的id  组合起来的一个字符串



}


function   a1()
{
	var  ids1=""    //物品的  id
		 var id =document.getElementsByName("ids");//获取name为ids的对象
	      ids1 = id[0].value;
		 var storeId=new Array();  //创建一个数组  来存储选中的柜子的id 
		$('input[name="check"]:checked').each(function(){  
			debugger;
		     storeId.push($(this).val());//向数组中添加元素 
		}); 
		var storeNum=$("input[type='checkbox']:checked").length;  //获取被选中柜子的数量 
		var storeIds=storeId.join(",");//将数组元素连接起来以构建一个字符串  
		
		/*var  s=storeIds.split(",");
		var   s1="";*/
			/*console.log(s);*/
    	
		    
			top.layer.confirm('确定分配到这'+storeNum+'个柜子吗？', function(index) {
		        top.layer.close(index);
		        $.post(locat + '/property/assign-manystore', {
		        	
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
}








/**
 * 分配入柜
 * 
 * @param storeId
 *            柜子id
 * @param ids
 *            财物id
 * @returns
 */
function assignStore(storeId, ids) {
    top.layer.confirm('确定分配到该柜吗？', function(index) {
        top.layer.close(index);
        
        $.post(locat + '/property/assign-store', {
            ids : ids,
            saveId : storeId
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
    
    
    
}
