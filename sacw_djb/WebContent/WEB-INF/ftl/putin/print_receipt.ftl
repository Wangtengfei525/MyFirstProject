<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>入库回执单</title>
</head>
<style type="text/css" media=print>
.noprint {
	display: none
}

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

	<div style="width: 624px; height: 800px; background-color: #FFFFFF">
		<div style="font-size: 10px; font-family: 黑体;">
			单位：${(case.organizerName)!}
		</div>
		<div style="font-size: 30px; font-family: 黑体; text-align: center;">
			涉案财物接收回执单<br>
		</div>
		<div style="font-size: 10px; font-family: 黑体; text-align: right; width: 570px; margin-left: 100px; margin-top: 20px">
			接收时间：${date?string("yyyy年MM月dd日")}
		</div>
		<table class="gridtable" style="width: 670px; height: 850px; text-align: center; border: 1px solid black; border-collapse: collapse;">
			
			<tr style="font-size: 20px; font-family: 黑体; text-align: center; height: 22px;">
				<td colspan="4">财 物 信 息</td>
			</tr>
			<#list properties as property>
			<tr style="font-size: 18px; font-family: 黑体; width: 100%; height: 22px;">
				<td style="width: 300px;">案件名称</td>
				<td colspan="3">${(property.caseName)!}</td>
			</tr>
			
			<tr style="width: 100%; height: 100px; font-size: 18px; font-family: 黑体; height: 22px">
				<td style="width: 20%">移交单位</td>
				<td colspan="3">${(case.organizerName)!}</td>
			
			</tr>
			
			<tr style="width: 100%; height: 100px; font-size: 18px; font-family: 黑体; height: 22px">
				<td style="width: 20%">财物二维码</td>
				<td colspan="3">${property.qrCode}</td>
			</tr>
			<tr style="font-size: 18px; font-family: 黑体; width: 100%; height: 22px;">
				<td style="width: 20%;">财物数量</td>
				<td style="width: 30%;">${total!}</td>
				<td style="width: 20%;">财物等级</td>
				<td style="width: 30%;">
					<span style="font-size: 14px; font-family: 黑体;">
						<span style="font-size: 18px; font-family: 黑体;">一般□</span>
						
						<span style="font-size: 18px; font-family: 黑体;">/贵重□</span>
						
						<span style="font-size: 18px; font-family: 黑体;">/特殊□</span>
					</span>
				</td>
			</tr>
			</#list>
			<tr style="font-size: 18px; font-family: 黑体; width: 100%; height: 22px;">
				<td style="width: 20%;">库管员</td>
				<td style="width: 30%;"></td>
				<td style="width: 20%;">保管单位</td>
				<td style="width: 30%;">成都市涉案财物管理中心</td>
			</tr>
			<tr style="width: 100%; height: 100px; font-family: 黑体; font-size: 18px;">
				<td style="width: 20%">初步分析意见/备注</td>
				<td colspan="3"></td>
			</tr>
			<tr style="width: 100%; height: 30px; font-family: 黑体; font-size: 18px;">
				<td colspan="4" style="width: 335px">
					涉案财物由民警事先封存未清点
					<span style="font-size: 22px; font-family: 黑体;">□</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 涉案财物由接收人清点后封存
					<span style="font-size: 22px; font-family: 黑体;">□</span>
				</td>
			</tr>
			<tr style="width: 100%; height: 30px; font-family: 黑体; font-size: 18px;">
				<td>移送方式</td>
				<td colspan="3" style="width: 335px">上门接收□&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 民警移送□</td>
			</tr>
			<tr style="font-size: 18px; font-family: 黑体;">
				<td>涉案财物交接人</td>
				<td></td>
				<td>涉案财物接收人</td>
				<td></td>
			</tr>
		</table>
		<div style="font-size: 10px; font-family: 黑体;">注：本表一式两份，办案单位保存一份，涉案财物保管中心留一份存档。</div>
	</div>
	<input type=button name=button_print value="打印" class="noprint" onclick="javascript:printit()">
	<input type=button name=button_setup value="打印页面设置" class="noprint" onclick="javascript:printsetup();">
	<input type=button name=button_show value="打印预览" class="noprint" onclick="javascript:printpreview();">
</body>
</html>