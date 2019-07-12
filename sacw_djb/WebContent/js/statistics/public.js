$("#statistics").datagrid({
	fit : true,
	toolbar : '#tb',
	fitColumns : true,
	url : locat+'/statistics/psb',
	columns : [ [ {
		title :'单位名称',
		field : 'name',
		align : 'center',
		width : 200
	}, {
		title : '财物归还',
		field : 'GHCW',
		align : 'center',
		width : 100
	}, {
		title : '财物借调',
		field : 'JDCW',
		align : 'center',
		width : 100
	}, {
		title : '财物出库',
		field : 'CKCW',
		align : 'center',
		width : 100
	}, {
		title : '财物入库',
		field : 'RKCWZS',
		align : 'center',
		width : 100
	}, {
		title : '案件归还',
		field : 'GHAJ',
		align : 'center',
		width : 100
	}, {
		title : '案件借调',
		field : 'JDAJ',
		align : 'center',
		width : 100
	}, {
		title : '案件出库',
		field : 'CKAJ',
		align : 'center',
		width : 100
	}, {
		title : '案件入库',
		field : 'RKAJZS',
		align : 'center',
		width : 100
	}, /*{
		title : '物品数量(扣除已出库)',
		field : 'CWSL',
		align : 'center',
		width : 100
	}, {
		title : '案件数量(扣除已出库)',
		field : 'AJSL',
		align : 'center',
		width : 100
	},*/ {
		title : '物品数量(总)',
		field : 'CWZS',
		align : 'center',
		width : 100
	}, {
		title : '案件数量(总)',
		field : 'AJZS',
		align : 'center',
		width : 100
	}
	]]
})

function searchD(){
	var start = $("#sd").datebox('getValue');
	var end = $("#ed").datebox('getValue');
	$.post(
		locat+'/statistics/psb',
		{startTime:start,endTime:end},
		function(json){
			$("#statistics").datagrid('loadData',json);
		},
		'json'
	);
}

function exporter(){
	var data = $("#statistics").datagrid('getRows');
	var Json = JSON.stringify(data);
	console.log();
	$("#json").val(Json);
	$("#form1").submit();
	//window.location.href = locat+'/statistics/abc?json='+Json;
	/*$.post(
		locat+'/statistics/abc',
		{json : Json}
	)*/
}