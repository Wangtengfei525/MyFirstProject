(function() {

    var imgSuffix = [ 'bmp', 'jpg', 'png', 'gif' ];

    /*var cwid = getParameter(window.location.search, 'cwid');
    if (!cwid) {
        top.layer.alert('未指定交换记录id，请联系管理员！');
        return;
    }*/
    
    var cwid = getParameter(window.location.search, 'cwid');
    if (!cwid) {
        top.layer.alert('未指定案件id，请联系管理员！');
        return;
    }
    
    var cwid = getParameter(window.location.search, 'propertyStatusCode');
    if (!cwid) {
        top.layer.alert('未获取到财物状态编码，请联系管理员！');
        return;
    }
    
    
    
    

    var vm = new Vue({
        el : '#app',
        data : function() {
            return {
                info : {
                    caze : {},
                    properties : [],
                    /*persons : [],*/
                    /*annexs : [],*/
                    exchange : {}
                },
                locat : locat
            };
        },
        filters : {
            datetime : function(value) {
                if (!value) {
                    return null;
                }
                return moment(value).format('YYYY-MM-DD HH:mm:ss');
            }

        },
        methods : {
            refresh : function() {
                var _this = this;
                var i = top.layer.load();
                $.get(this.locat + '/exchange/info', {
                    id : cwid
                }).done(function(data) {
                	debugger;
                    _this.info = data;
                    top.layer.close(i);
                }).fail(function() {
                    top.layer.close(i);
                })
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
        }
    });
})();
