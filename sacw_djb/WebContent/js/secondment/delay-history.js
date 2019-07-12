$(function() {
    var secondmentId = getParameter(window.location.search, 'secondmentId');

    if (!secondmentId) {
        top.layer.alert('未指定借调记录id，请联系管理员！');
        return;
    }

    new Vue({
        el : '#app',
        data : function() {
            return {
                delays : [],
                locat : locat,
                secondmentId : secondmentId
            };
        },
        filters : {
            datetime : function(value) {
                if (!value) {
                    return null;
                }
                return moment(value).format('YYYY年M月D日 H时m分s秒');
            },
            date : function(value) {
                if (!value) {
                    return null;
                }
                return moment(value).format('YYYY年M月D日');
            }
        },
        methods : {
            loadDelays : function() {
                var _this = this;
                var i = top.layer.load();
                $.get(this.locat + '/secondment/delay/list', {
                    secondmentId : this.secondmentId
                }).done(function(data) {
                    top.layer.close(i);
                    if (data.success) {
                        _this.delays = data.rows;
                    } else {
                        top.layer.msg(data.message, {
                            icon : 2
                        });
                    }
                }).fail(function() {
                    top.layer.close(i);
                    top.layer.msg('加载延期历史失败，请联系管理员', {
                        icon : 2
                    });
                })
            }
        },
        mounted : function() {
            this.loadDelays();
        }
    });

});