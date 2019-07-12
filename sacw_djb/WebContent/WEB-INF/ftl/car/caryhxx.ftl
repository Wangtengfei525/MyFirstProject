
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<style type="text/css" media=print>
.noprint{display : none }
table.gridtable td{
    height:20px;
	border-width: 1px;
	border-style: solid;
	border-color: #666666;
}
 tr{
  
	border-width: 1px;
	border-style: solid;
	border-color: #666666;
}

</style> 
<script type="text/javascript">
function printsetup(){
    try{
    	  wb.execwb(8,1);
    }catch(e){
    	  alert("您的浏览器不支持此功能,请使用IE浏览器");
    }
	  // 打印页面设置
	
	  }
	 function printpreview(){
		   try{
				 // 打印页面预览
				PageSetup_Null();
				  wb.execwb(7,1);
		    }catch(e){
		    	  alert("您的浏览器不支持此功能,请使用IE浏览器");
		    }

	
	 }
	 
	 function printit(){
	   window.print();
	 }
var HKEY_Root,HKEY_Path,HKEY_Key;
 HKEY_Root="HKEY_CURRENT_USER";
 HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
//设置网页打印的页眉页脚为空
function PageSetup_Null()
 {
 try
 {
 var Wsh=new ActiveXObject("WScript.Shell");
 HKEY_Key="header";
 Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
 HKEY_Key="footer";
 Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
 }
 catch(e)
 {
 
 }
 }
 //设置网页打印的页眉页脚为默认值
 function PageSetup_Default()
 {
 try
 {
 var Wsh=new ActiveXObject("WScript.Shell");
 HKEY_Key="header";
 Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"&w&b页码,&p/&P");
 HKEY_Key="footer";
 Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"&u&b&d");
 }
 catch(e)
 {}
 }

</script>
				</head>

				<body>
 <OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 id=wb name=wb width=0></OBJECT>

<div style="width:624px;height:800px;background-color: #FFFFFF">
<div style="font-size:10px;font-family: 黑体;">
 单位：
</div>
<div style="font-size:30px;font-family: 黑体;text-align:center;">
涉案车辆日常维护保养登记表
<br>
</div>
<div style="font-size:10px;font-family: 黑体;text-align:right;width:570px;margin-left:100px;margin-top:20px">
制表时间： ${date?string("yyyy-MM-dd HH:mm:ss")}
</div>
<table class="gridtable" style="width:670px;height:850px;text-align:center;border:1px solid black;border-collapse:separate; border-spacing:10px;">
<tr style="font-size:20px;font-family: 黑体;text-align:center;height:22px;">
<td style="width:20%">
车  牌   号
</td>
<td style="width:30%"></td>
<td style="width:20%">类 别</td>
<td colspan="2"></td>
</tr>
<tr style="font-size:20px;font-family: 黑体;text-align:center;height:22px;margin-top:15px">
<td >
入库时间
</td>
<td ><SPAN STYLE="font-size:12px;">${carTime}</SPAN></td>
<td >出库时间</td>
<td colspan="2"></td>
</tr>
<tr style="width:100%;height:100px;font-size:18px;font-family: 黑体;height:22px; margin-top:15px">
<td>车辆信息</td>
<td><SPAN STYLE="font-size:12px;">${CarMessage}</SPAN></td>
<td style="width:20%">存放位置</td>
<td colspan="2"><SPAN STYLE="font-size:12px;">${Location}</SPAN></td>
</tr>
<tr style="font-size:11px;font-family: 黑体;background-color:#CCCCFF;width:100%;height:22px; "><td colspan="5">保养记录</td></tr>
<tr>
<td colspan="5" style="text-align: top">
<table style="height:762px;width:100%">
<tr style="font-size:11px;font-family: 黑体;width:100%;height:22px; ">
<td style="width:5%;">序号</td>
<td style="width:15%;">日期</td>
<td style="width:35%;">保养项目</td>
<td style="width:25%;">车辆情况</td>
<td style="width:25%;">备注</td>
</tr>
<#setting datetime_format="yyyy-MM-dd HH:mm:Ss"/>
	<#list yhxx as property>
			<tr style="font-size: 10px; font-family: 黑体;">
				<td>${property_index + 1}</td>
				<td>${property.conserveTime?date!}</td>
				<td>${property.conserveTextName!}</td>
				<td>
					<#if property.status ??&&property.status==1>
						养护申请
					<#elseif property.status ??&&property.status==0>
						养护完成
					<#elseif property.status ??&&property.status==2>
						审核通过
					<#elseif property.status ??&&property.status==3>
						审核不通过
					<#else>
						养护中
					</#if>
				</td>
				<td>${property.conserveRemark!}</td>
			</tr>
		</#list>
</table></td>
</tr>
</table>
</div>
 <input type=button name=button_print value="打印" class="noprint" onclick="javascript:printit()">
 <input type=button name=button_setup value="打印页面设置" class="noprint" onclick="javascript:printsetup();">
 <input type=button name=button_show value="打印预览" class="noprint" onclick="javascript:printpreview();">
</body>
</html>