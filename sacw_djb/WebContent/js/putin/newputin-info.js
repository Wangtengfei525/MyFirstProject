//write by yiyu
//财物添加次数
var incrementnumber = 0;
//财物数组
var propertyarray = new Array();

var exchange_id;

$(document).ready(function() {
	
	
	var settingsArray=new Array();
	
	var tempId = uuid();
	
	// 入库
	$("#btn-putin").click(function() {
//		var index = top.layer.load();
		getval();
	});
	
	//上传附件
	$('#btn-upload').click(function() {
		openUpload(tempId);
		exchange_id=tempId;
    });
	
	//日历控件初始化
	$("#input-operation-time").datepicker({
        autoclose : true,
        language : 'zh-CN',
        format : 'yyyy-mm-dd',
        todayBtn : 'linked'
    });
	$('#input-operation-time').datepicker('setDate', new Date());
	
	$('#input-unit-name').click(function(){
        var inputObj = $('#input-unit-name');
        var offset = inputObj.offset();
        $("#unit-wrapper").css({
            left : offset.left + "px",
            top : offset.top + inputObj.outerHeight() + "px"
        }).slideDown("fast");
        $('body').bind('mousedown', onBodyDown);
        
    });
	
	$('#input-type-name').click(function(){
        var inputObj = $('#input-type-name');
        var offset = inputObj.offset();
        $("#type-wrapper").css({
            left : offset.left + "px",
            top : offset.top + inputObj.outerHeight() + "px"
        }).slideDown("fast");
        $('body').bind('mousedown', onBodyDownType);
        
    });
	
	// 初始化财物类别下拉菜单
    var settings = {
        data : {
            key : {
                children : 'children',
                name : 'name'
            }
        },
        callback : {
            onClick : function(event, treeId, treeNode, clickFlag) {
                $('#input-type-name').val(treeNode.name);
                $('#input-type-id').val(treeNode.id);
                hideUnitTreeType();
            }
        }
    };
    $.get(locat + '/system/unit/propertytypetree', function(data) {
        $.fn.zTree.init($("#type-unit"), settings, data);
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
    
    //新增财物
    $("#newproperty").click(function(){
    	incrementnumber++;
    	$("#putmoreproperty").append('<form class="form-horizontal" id="'+
    			incrementnumber+'property">'+
    			'<div class="form-group"><label class="control-label col-xs-3">财物名称'+
    			'</label><div class="col-xs-9">'+
    			'<input class="form-control" id="'+incrementnumber+'propertyName" />'+
    			'</div></div>'+
    			'<div class="form-group"><label class="control-label col-xs-3">财物类别</label>'+
    			'<div class="col-xs-9"><input id="'+incrementnumber+'input-type-id" type="text" style="display: none;" name="sendUnitId"/>'+
    			'<input id="'+incrementnumber+'input-type-name" class="form-control" type="text" placeholder="财物类别" readonly />'+
    			'</div></div>'+'<div class="form-group"><label class="control-label col-xs-3">财物数量</label>'+
    			'<div class="col-xs-9"><input class="form-control" id="'+
    			incrementnumber+'propertyNumber" onkeyup="if(this.value.length==1){this.value=this.value.replace(\/[^1-9]\/g,\'\')}else{this.value=this.value.replace(\/\\D/g,\'\')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(\/[^1-9]\/g,\'\')}else{this.value=this.value.replace(\/\\D\/g,\'\')}" /></div></div>'+
    			'<div class="form-group"><label class="control-label col-xs-3">财物描述</label>'+
    			'<div class="col-xs-9"><textarea class="form-control" rows="4" id="'+
    			incrementnumber+'property-input"></textarea></div></div>'+
    			'<div class="form-group"><label class="control-label col-xs-3">财物备注</label>'+
    			'<div class="col-xs-9"><textarea class="form-control" rows="4" id="'+
    			incrementnumber+'property-input-remark"></textarea></div></div>'+'</form>');
    	
    	$("#increatetree").append('<div id="'+incrementnumber+'type-wrapper" style="display: none; position: absolute; z-index: 99999; background-color: #fff; max-height: 300px; overflow: auto;">'
    	        +'<ul id="'+incrementnumber+'type-unit" class="ztree"></ul>'+
   	        '</div>');
    	
    	$('#'+incrementnumber+'input-type-name').click(function(){
            var inputObj = $('#'+incrementnumber+'input-type-name');
            var offset = inputObj.offset();
            $('#'+incrementnumber+'type-wrapper').css({
                left : offset.left + "px",
                top : offset.top + inputObj.outerHeight() + "px"
            }).slideDown("fast");
            $('body').bind('mousedown', onBodyDownType);
            
        });
    	
    	// 初始化财物类别下拉菜单
        var setting1 = {
            data : {
                key : {
                    children : 'children',
                    name : 'name'
                }
            },
            callback : {
                onClick : function(event, treeId, treeNode, clickFlag) {
                	console.log("back");
                    $('#'+incrementnumber+'input-type-name').val(treeNode.name);
                    $('#'+incrementnumber+'input-type-id').val(treeNode.id);
                    hideUnitTreeType();
                }
            }
        };
        
        $.get(locat + '/system/unit/propertytypetree', function(data) {
            $.fn.zTree.init($('#'+incrementnumber+'type-unit'), setting1, data);
        });
    });
    
 // 初始化附件表格
    $('#table-files').bootstrapTable({
        url : locat + '/annex/list?exchangeId=' + tempId,
        classes : 'table table-hover table-no-bordered',
        sidePagination : 'server',
        toolbar : '#div-file-toolbar',
        striped : true,
        height : 340,
        columns : [ {
            title : '序号',
            formatter : function(value, row, index) {
                return index + 1;
            }
        }, {
            field : 'file_name',
            title : '名称'
        }, {
            field : 'suffix_name',
            title : '后缀名'
        }, {
            field : 'id',
            title : '操作',
            width : 280,
            formatter : function(value, row, index) {
                var arr = [];
                arr.push('<a class="btn btn-xs btn-flat btn-primary" onclick="downloadAnnex(\'' + row.id + '\')">下载</a>');
                arr.push('<a class="btn btn-xs btn-flat btn-danger" onclick="deleteAnnex(\'' + row.id + '\')">删除</a>');
                return arr.join(' ');
            }
        } ]
    });
});

/**
 * 
 * 获取文本框值
 * 
 * @returns
 */
function getval() {
	var caseName = $("#caseName").val();
	var sendUnitName = $("#input-unit-name").val();
	var create_time=$("#gettime").find("input").val();
	if(caseName!="" && sendUnitName!=""){
		var casestring = {"caseName":caseName,"organizerName":sendUnitName,"create_time":create_time};
		var index = top.layer.load();
		console.log(index+"");
		$.ajax({
			url:locat+"/newcase/newcaseinsert",
			type:"post",
			dataType:"text",
			contentType:"application/json;charset=UTF-8",
			data:JSON.stringify(casestring),
			success:function(data){
	            var icon = 1;
				getpropertyval(data);
				putfilecaseid(exchange_id,data);
				top.layer.close(index);
		        top.layer.close(2);
		        top.layer.msg("入库成功", {
	                icon : icon
	            });
			},
			error:function(jqXHR){
				console.log("Error:"+jqXHR.status);
				top.layer.close(index);
				top.layer.close(index-1);
			}
		});
	}
	else{
		clearcaseval();
		alert("文本框不能为空");
	}
}

/**
 * 获取财物文本框
 * 
 * 
 * @returns
 */
function getpropertyval(id){
	if(incrementnumber == 0){
		var propertyName = $("#propertyName").val();
		var propertyType = $("#input-type-name").val();
		var propertyNumber = $("#propertyNumber").val();
		var goods=$("#property-input").val();
		var property_input_remark = $("#property-input-remark").val();
		var create_time=$("#gettime").find("input").val();
		var firstproperty = {"property_name":propertyName,"property_type":propertyType,"number":propertyNumber,"remark":property_input_remark,"case_id":id,"create_time":create_time,"goods":goods};
		propertyarray.push(firstproperty);
	}
	else{
		var propertyName = $("#propertyName").val();
		var propertyType = $("#input-type-name").val();
		var propertyNumber = $("#propertyNumber").val();
		var goods=$("#property-input").val();
		var property_input_remark = $("#property-input-remark").val();
		var create_time=$("#gettime").find("input").val();
		var firstproperty = {"property_name":propertyName,"property_type":propertyType,"number":propertyNumber,"remark":property_input_remark,"case_id":id,"create_time":create_time,"goods":goods};
		propertyarray.push(firstproperty);
		for(;incrementnumber>0;incrementnumber--){
			propertyName = $("#"+incrementnumber+"propertyName").val();
			propertyNumber = $("#"+incrementnumber+"propertyNumber").val();
			property_input_remark = $("#"+incrementnumber+"property_input_remark").val();
			goods=$("#"+incrementnumber+"property-input").val();
			propertyType = $("#"+incrementnumber+"input-type-name").val();
			create_time=$("#gettime").find("input").val();
			var moreproperty = {"property_name":propertyName,"property_type":propertyType,"number":propertyNumber,"remark":property_input_remark,"case_id":id,"create_time":create_time,"goods":goods};
			propertyarray.push(moreproperty);
		}
	}
	$.ajax({
		url:locat+"/newproperty/newpropertyinsert",
		type:"post",
		data:JSON.stringify(propertyarray),
		dataType:"text",
		contentType:"application/json;charset=UTF-8",
		success:function(data){
		},
		error:function(jqXHR){
			console.log("error:"+jqXHR.status);
		}
	});
}

/**
 * 
 * 清空案件文本框值
 * 
 * @returns
 */
function clearcaseval(){
	$("#caseName").val("");
	$("#sendUnitName").val("");
	$("#sendPersonName").val("");
	$("#case-input-remark").val("");
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
 * 点击页面隐藏类别树
 * 
 * @param event
 * @returns
 */
function onBodyDownType(event) {
    if (event.target.id == 'input-type-name' || event.target.id == 'type-wrapper' || $(event.target).parents('#type-wrapper').length > 0) {
        return;
    }
    if (event.target.id == incrementnumber+'input-type-name' || event.target.id == incrementnumber+'type-wrapper' || $(event.target).parents('#'+incrementnumber+'type-wrapper').length > 0) {
        return;
    }
    hideUnitTreeType();
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
 * 隐藏单位树
 * 
 * @returns
 */
function hideUnitTreeType() {
    $('#'+incrementnumber+'type-wrapper').fadeOut("fast");
    $("#type-wrapper").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownType);
}

/**
 * 给附件增加案件id
 * 
 * @param id
 * @param case_id
 * @returns
 */
function putfilecaseid(id,case_id){
	$.ajax({
		url:locat+"/newcase/putfilecaseid?id="+id+"&case_id="+case_id,
		type:'get',
		dataType:'text',
		contentType:"application/json;charset=UTF-8",
		success:function(data){
		},
		error:function(jqXHR){
			console.log("error:"+jqXHR.status);
		}
	});
}

/**
 * 刷新附件数据
 * 
 * @param id
 *            交换记录id
 * @returns
 */
function getFiles(id) {
    $('#table-files').bootstrapTable('refresh');
}

/**
 * 下载附件
 * 
 * @param id
 *            附件id
 * @returns
 */
function downloadAnnex(id) {
    window.open(locat + '/annex/download?id=' + id);
}

/**
 * 删除附件
 * 
 * @param id
 * @returns
 */
function deleteAnnex(id) {
    top.layer.confirm('确认删除该附件？', function(index) {
        top.layer.close(index);
        $.post(locat + '/annex/delete', {
            id : id
        }).then(function(data) {
            var icon = 2;
            if (data.success) {
                icon = 1;
                getFiles();
            }
            top.layer.msg(data.message, {
                icon : icon
            });
        });
    });
}
