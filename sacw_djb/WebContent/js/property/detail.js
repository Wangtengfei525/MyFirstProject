(function() {

    var cwid = getParameter(window.location.search, 'cwid');
    if (!cwid) {
        top.layer.alert('未指定财物id，请联系管理员！');
        return;
    }

    var nodeCodes = {
        '1001001' : '涉案款物登记',
        '1002001' : '申请入库',
        '1002002' : '入库反馈',
        '1002003' : '退回入库',
        '1003001' : '借调申请',
        '1003002' : '借调反馈',
        '1003003' : '退回借调',
        '1004001' : '归还申请',
        '1004002' : '归还反馈',
        '1004003' : '退回归还',
        '1005001' : '公安款物移送保管中心',
        '1005002' : '保管中心移送检察院',
        '1005003' : '保管中心退回公安',
        '1005004' : '检察院确认接收',
        '1005005' : '保管中心反馈公安',
        '1006001' : '检察院移送法院',
        '1006002' : '法院接收',
        '1006003' : '通知保管中心',
        '1006004' : '法院退回',
        '1007001' : '法院移送检察院、公安、财政',
        '1007002' : '检察院、公安、财政接收反馈',
        '1007003' : '权限变更为财政',
        '1007004' : '检察院、公安、财政退回',
        '1008001' : '法院移送',
        '1008002' : '保管中心权限变更',
        '1009001' : '涉案财物处理',
        '1010001' : '监督发起',
        '1010002' : '监督反馈',
        '1011001' : '出库申请',
        '1011002' : '出库反馈',
        '1011003' : '退回出库'
    };

    var imgSuffix = [ 'bmp', 'jpg', 'png', 'gif' ];

    var vm = new Vue({
        el : '#app',
        data : function() {
            return {
                info : {
                    caze : {},
                    property : {
                        photos : []
                    },
                    annexs : []
                },
                exchanges : [],
                logs : [],
                locat : locat
            };
        },
        filters : {
            datetime : function(value) {
                if (!value) {
                    return null;
                }
                return moment(value).format('YYYY-MM-DD HH:mm:ss');
            },
            nodeName : function(value) {
                return nodeCodes[value];
            }
        },
        methods : {
            refresh : function() {
                var _this = this;
                var i = top.layer.load();
                $.get(this.locat + '/property/info', {
                    id : cwid
                }).done(function(data) {
                    _this.info = data;
                    top.layer.close(i);
                }).fail(function() {
                    top.layer.close(i);
                });
            },
            loadExchanges : function() {
                var _this = this;
                var i = top.layer.load();
                $.get(this.locat + '/exchange/list/by-property', {
                    propertyId : cwid
                }).done(function(data) {
                    _this.exchanges = data.rows;
                    top.layer.close(i);
                }).fail(function() {
                    top.layer.close(i);
                });
            },
            loadLogs : function() {
                var _this = this;
                var i = top.layer.load();
                $.get(this.locat + '/property/log/list', {
                    id : cwid
                }).done(function(data) {
                    _this.logs = data.rows;
                    top.layer.close(i);
                }).fail(function() {
                    top.layer.close(i);
                });
            },
            imgPreview : function(id) {
                window.open(this.locat + '/annex/preview?id=' + id);
            },
            isImage : function(value) {
                if (!value) {
                    return false;
                }

                var suffix = value.toLowerCase().replace(/\./g, '');

                for (var i = 0; i < imgSuffix.length; i++) {
                    if (imgSuffix[i] === suffix) {
                        return true;
                    }
                }

                return false;

            }
        },
        mounted : function() {
            this.refresh();
            this.loadExchanges();
            this.loadLogs();
        }
    });
})();
