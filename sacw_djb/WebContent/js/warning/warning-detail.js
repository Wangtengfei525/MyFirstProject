$(function() {

    var id = getParameter(window.location.search, 'id');

    if (!id) {
        top.layer.msg('未提供报警记录id，请联系管理员', {
            icon : 2
        });

        return;
    }

    new Vue({
        el : '#app',
        data : function() {
            return {
                warning : {},
                properties : [],
                photos : [],
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
            loadWarning : function() {
                var _this = this;
                var index = top.layer.load();
                $.get(locat + '/warning/get', {
                    id : id
                }).done(function(data) {
                    if (data.success) {
                        _this.warning = data.warning;
                    } else {
                        top.layer.msg(data.message, {
                            icon : 2
                        });
                    }
                    top.layer.close(index);
                }).fail(function() {
                    top.layer.close(index);
                    top.layer.msg('报警信息加载失败，请联系管理员', {
                        icon : 2
                    });
                });
            },
            loadProperties : function() {
                var _this = this;
                var index = top.layer.load();
                $.get(locat + '/warning/properties', {
                    id : id
                }).done(function(data) {
                    if (data.success) {
                        _this.properties = data.rows;
                    } else {
                        top.layer.msg(data.message, {
                            icon : 2
                        });
                    }
                    top.layer.close(index);
                }).fail(function() {
                    top.layer.close(index);
                    top.layer.msg('财物信息加载失败，请联系管理员', {
                        icon : 2
                    });
                });
            },
            loadPhotos : function() {
                var _this = this;
                var index = top.layer.load();
                $.get(locat + '/warning/photos', {
                    id : id
                }).done(function(data) {
                    if (data.success) {
                        _this.photos = data.rows;
                    } else {
                        top.layer.msg(data.message, {
                            icon : 2
                        });
                    }
                    top.layer.close(index);
                }).fail(function() {
                    top.layer.close(index);
                    top.layer.msg('照片加载失败，请联系管理员', {
                        icon : 2
                    });
                });
            },
            propertyDetail : function(id) {
                openPropertyView(id);
            }
        },
        mounted : function() {
            this.loadWarning();
            this.loadProperties();
            this.loadPhotos();
        }
    });

});

/**
 * 获取设置信息
 * 
 * @param id
 * @returns
 */
function getSetting(id) {

}