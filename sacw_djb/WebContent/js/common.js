$.ajaxSetup({
    cache : false
});

// bootstrapTable 默认属性
if ($.fn.bootstrapTable) {
    var defaults = $.fn.bootstrapTable.defaults;
    defaults.cache = false;
    defaults.classes = 'table table-hover table-no-bordered';
    defaults.striped = true;
    defaults.cache = false;
    defaults.escape = true;
}

var locat = (window.location + '').split('/');
if ('main' == locat[3]) {
    locat = locat[0] + '//' + locat[2];
} else {
    locat = locat[0] + '//' + locat[2] + '/' + locat[3];
};

/**
 * 日期格式化
 * 
 * @param value
 * @returns
 */
function dateFormatter(value) {
    if (value == null)
        return null;
    return moment(value).format('YYYY-MM-DD HH:mm:ss');
}

/**
 * 获取url的查询参数
 * 
 * @param search
 *            search url查询字符串
 * @param param
 *            参数名称
 * @returns 参数值
 */
function getParameter(search, param) {
    if (!search) {
        return null;
    }
    var reg = new RegExp("(^|&)" + param + "=([^&]*)(&|$)");
    var r = search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

/**
 * 计算layer弹层大小，当传入大小超出屏幕可视范围时重新计算大小
 * 
 * @param width
 *            宽度
 * @param height
 *            高度
 * @returns 计算后的area属性值：['800px','600px']
 */
function getArea(width, height) {
    var windowWidth = $(top).width();
    var windowHeight = $(top).height();
    return [ Math.min(width, windowWidth - 50) + 'px', Math.min(height, windowHeight - 30) + 'px' ];
}
// 附件页面
function openUpload(id) {
    if (id != null && id != '') {
        top.layer.open({
            type : 2,
            title : '附件上传',
            area : getArea(700, 560),
            content : locat + '/html/base/Upload.html?uploadID=' + id,
            end : function() {
                // 刷新附件列表
                getFiles();
            }
        });
    }
}



//传递过去的是一个随机生成的id
function openUploadNew(id) {
    if (id != null && id != '') {
        top.layer.open({
            type : 2,
            title : '附件上传',
            area : getArea(700, 560),
            content : locat + '/html/base/NewUpload.html?uploadID=' + id,
            end : function() {
                // 刷新附件列表
                getFiles();
            }
        });
    }
}










/**
 * 财物拆分
 * 
 * @param id
 *            财物id
 * @returns
 */
function propertySplit(id) {
    top.layer.open({
        type : 2,
        title : '财物拆分',
        area : getArea(1200, 740),
        maxmin : true,
        content : locat + '/html/base/property-split.html?cwid=' + id
    });
}

/**
 * 交换详情（包含财物信息、案件信息、交换记录信息、附件信息）
 * 
 * @param id
 *            财物id
 * @returns
 */
/*function openDetailsView(id) {
    top.layer.open({
        type : 2,
        title : '交换记录详情',
        area : getArea(950, 600),
        maxmin : true,
        content : locat + '/html/exchange/detail.html?cwid=' + id
    });
}*/
/**
 * 此时的id换成了案件的id
 * @param id
 * @returns
 */

function openDetailsView(id,propertyStatusCode) {
    top.layer.open({
        type : 2,
        title : '交换记录详情',
        area : getArea(950, 600),
        maxmin : true,
        content : locat + '/html/exchange/detail.html?cwid=' + id+'&propertyStatusCode='+propertyStatusCode
       // content : locat + '/html/exchange/detail.html?cwid=' + id
        
        
    });
}


//下面这个方法是借调页面特用的
function openSecondDetailsView(id,propertyStatusCode) {
    top.layer.open({
        type : 2,
        title : '交换记录详情',
        area : getArea(950, 600),
        maxmin : true,
        content : locat + '/html/exchange/SecondDetail1.html?cwid=' + id+'&propertyStatusCode='+propertyStatusCode
       // content : locat + '/html/exchange/detail.html?cwid=' + id
        
        
    });
}


//这个双击查询详情是归还页面所用的
function openBackDetailsView(id,propertyStatusCode) {
    top.layer.open({
        type : 2,
        title : '交换记录详情',
        area : getArea(950, 600),
        maxmin : true,
        content : locat + '/html/exchange/BackDetail.html?cwid=' + id+'&propertyStatusCode='+propertyStatusCode
       // content : locat + '/html/exchange/detail.html?cwid=' + id
        
        
    });
}

//这个查询详情是出库页面所用的
function openOutDetailsView(id,propertyStatusCode) {
    top.layer.open({
        type : 2,
        title : '交换记录详情',
        area : getArea(950, 600),
        maxmin : true,
        content : locat + '/html/exchange/OutDetail.html?cwid=' + id+'&propertyStatusCode='+propertyStatusCode
       // content : locat + '/html/exchange/detail.html?cwid=' + id
        
        
    });
}












/**
 * 财物详情
 * 
 * @param id
 *            单个财物详情（案件信息，财物信息）
 * @returns
 */
function openPropertyView(id) {
    top.layer.open({
        type : 2,
        title : '财物详情',
        area : getArea(1000, 600),
        maxmin : true,
        content : locat + '/html/property/detail.html?cwid=' + id
    });
}

/**
 * 生成uuid
 */
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
    s[8] = s[13] = s[18] = s[23] = "-";
    var uuid = s.join("");
    return uuid.replace(/-/g, '');
}

$(document).keyup(function(event) {
    if (event.keyCode == 13) {
        refresh();
    }
});