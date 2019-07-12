$("#statistics").datagrid({
	fit:true,
	fitColumns : true,
	url : locat+'/overdue/statistics',
	columns :[[{
		title :'单位名称',
		field : 'name',
		align : 'center',
		width : 100
	},{
		title :'公安登记逾期案件',
		field : 'GADJAJ',
		align : 'center',
		width : 100
	},{
		title :'公安登记逾期财物',
		field : 'GADJCW',
		align : 'center',
		width : 100
	},{
		title :'公安移送逾期案件',
		field : 'GAYSAJ',
		align : 'center',
		width : 100
	},{
		title :'公安移送逾期财物',
		field : 'GAYSCW',
		align : 'center',
		width : 100
	},{
		title :'检察院移送逾期案件',
		field : 'JSYYSAJ',
		align : 'center',
		width : 100
	},{
		title :'检察院移送逾期财物',
		field : 'JCYYSCW',
		align : 'center',
		width : 100
	},{
		title :'法院移送逾期案件',
		field : 'FYYSAJ',
		align : 'center',
		width : 100
	},{
		title :'法院移送逾期财物',
		field : 'FYYSCW',
		align : 'center',
		width : 100
	}]]
})