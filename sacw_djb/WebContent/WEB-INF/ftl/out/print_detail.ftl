<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>出库清单</title>
</head>
<style type="text/css" media=print>
.noprint {
	display: none
}
/*  .PageNext{page-break-after:   always;}//换页打印       */
table.gridtable td {
	height: 20px;
	border-width: 1px;
	border-style: solid;
	border-color: #666666;
}
</style>
<script type="text/javascript">
	function printsetup() {
		try {
			wb.execwb(8, 1);
		} catch (e) {
			alert("您的浏览器不支持此功能,请使用IE浏览器");
		}
		// 打印页面设置

	}
	function printpreview() {
		try {
			// 打印页面预览
			PageSetup_Null();
			wb.execwb(7, 1);
		} catch (e) {
			alert("您的浏览器不支持此功能,请使用IE浏览器");
		}

	}

	function printit() {
		window.print();
	}
	var HKEY_Root, HKEY_Path, HKEY_Key;
	HKEY_Root = "HKEY_CURRENT_USER";
	HKEY_Path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
	//设置网页打印的页眉页脚为空
	function PageSetup_Null() {
		try {
			var Wsh = new ActiveXObject("WScript.Shell");
			HKEY_Key = "header";
			Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
			HKEY_Key = "footer";
			Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
		} catch (e) {

		}
	}
	//设置网页打印的页眉页脚为默认值
	function PageSetup_Default() {
		try {
			var Wsh = new ActiveXObject("WScript.Shell");
			HKEY_Key = "header";
			Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "&w&b页码,&p/&P");
			HKEY_Key = "footer";
			Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "&u&b&d");
		} catch (e) {
		}
	}
</script>
</head>

<body>
	<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 id=wb name=wb width=0></OBJECT>
	<div style="background-color: #FFFFFF">
		<div style="font-size: 10px; font-family: 黑体;">单位：${(case.organizerName)!}</div>
		<div style="font-size: 18px; font-family: 黑体; text-align: center;">
			
		</div>
		<div style="font-size: 10px; font-family: 黑体; text-align: right; width: 95%">制表时间： ${date?string("yyyy-MM-dd HH:mm:ss")}</div>
		<table class="gridtable" style="text-align: center; border: 1px solid black; border-collapse: collapse;">
			<tr style="font-size: 11px; font-family: 黑体; background-color: #CCCCFF; width: 100%; height: 22px;">
				<td style="width: 5%;">序号</td>
				<td style="width: 9%;">物品名称</td>
				<td style="width: 7%;">数量（单位）</td>
				<td style="width: 7%;">物品种类</td>
				<td style="width: 13%;">物品描述</td>
				<td style="width: 5%;">物品来源</td>
				<td style="width: 15%;">案件名称</td>
				<td style="width: 12%;">存储位置</td>
				<td style="width: 12%;">二维码</td>
				<td style="width: 10%;">备注</td>
				<td style="width: 10%;">民警签字</td>
			</tr>

			<#list properties as property>
			<tr style="font-size: 10px; font-family: 黑体;">
				<td>${property_index + 1}</td>
				<td>${property.propertyName!}</td>
				<td>${property.number!}${property.meteringUnit!}</td>
				<td>${property.propertyType!}</td>
				<td>${property.goodsSpecial!}</td>
				<td>${property.source!}</td>
				<td>${property.caseName!}</td>
				<td>${property.kwmc!}</td>
				<td>${property.qrCode!}</td>
				<td></td>
				<td></td>
			</tr>
			</#list>
			<#list properties?size..20 as i>
			<tr style="font-size: 10px; font-family: 黑体;">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			</#list>
			<tr style="font-size: 11px; font-family: 黑体;">
				<td>出库方式</td>
				<td colspan="4" style="width: 335px">民警上门□&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 中心移送□</td>
				<td>封存方式</td>
				<td colspan="5" style="width: 335px">涉案财物由民警事先封存未清点□&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 涉案财物由接收人清点后封存□</td>
			</tr>
			<tr style="font-size: 11px; font-family: 黑体;">
				<td colspan="2">涉案财物交接人</td>
				<td colspan="2"></td>
				<td colspan="2">涉案财物接收人</td>
				<td colspan="5"></td>
			</tr>
		</table>
		<div style="font-size: 10px; font-family: 黑体;">注：本表一式两份，办案单位保存一份，涉案财物保管中心留一份存档。</div>
	</div>
	<input type=button name=button_print value="打印" class="noprint" onclick="javascript:printit()">
	<input type=button name=button_setup value="打印页面设置" class="noprint" onclick="javascript:printsetup();">
	<input type=button name=button_show value="打印预览" class="noprint" onclick="javascript:printpreview();">
</body>

</html>