$(function() {
    new Vue({
        el : '#app',
        data : function() {
            return {
                settings : [],
                properties : [],
                data : {},
                Id : null,
                property_name : null,
                case_name : null,
                contents : {}
            };
        },
        methods : {
            // 加载养护周期设置
            loadPeriod : function() {
                var _this = this;
                var index = top.layer.load();
                $.get(locat + '/car-conserve/period/all').done(function(data) {
                    _this.settings = data.rows;
                    top.layer.close(index);
                }).fail(function() {
                    top.layer.close(index);
                });
            },
            // 加载有养护内容设置的财物信息
            loadProperties : function() {
                var _this = this;
                var index = top.layer.load();

                this.Id = null;
                this.property_name = null;
                this.case_name = null;
                this.contents = [];

                $.get(locat + '/property/split/list/has-own-contents').done(function(data) {
                    _this.properties = data.rows;
                    top.layer.close(index);
                }).fail(function() {
                    top.layer.close(index);
                });
            },
            // 加载养护内容
            loadContents : function(property) {
                if (!property) {
                    return;
                }
                this.Id = property.id;
                this.case_name = property.case_name;
                this.property_name = property.property_name;
                var _this = this;
                var index = top.layer.load();
                $.get(locat + '/car-conserve/content/list', {
                    splitId : property.id
                }).done(function(data) {
                    top.layer.close(index);
                    var contents = data.rows;
                    _this.contents = contents;

                }).fail(function() {
                    top.layer.close(index);
                });
            },
            // 删除养护内容
            deleteContents : function() {
                var _this = this;
                var splitId = _this.Id;
                if (!splitId) {
                    return;
                }
                top.layer.confirm('确认删除该养护设置？', function(index) {
                    top.layer.close(index);
                    index = top.layer.load();
                    $.post(locat + '/car-conserve/content/delete', {
                        splitId : Id
                    }).done(function(data) {
                        top.layer.close(index);
                        var icon = 2;
                        if (data.success) {
                            icon = 1;
                            _this.loadProperties();
                        }
                        top.layer.msg(data.message, {
                            icon : icon
                        });
                    }).fail(function() {
                        top.layer.close(index);
                        top.layer.msg('删除设置出错，请联系管理员', {
                            icon : 2
                        });
                    });
                });

            },
            // 添加养护内容设置
            addSet : function() {
                $.post(locat + '/car-conserve/content/set', {
                    splitId : '2858861eb7614cabb34e257fad4d40d0',
                    conserveContentCodes : 'car_01,car_02,car_03'
                });
            },
            // 保存养护内容设置
            saveContents : function() {
                var contents = this.contents;
                var _this = this;
                var codes = [];
                contents.forEach(function(e) {
                    if (e.enabled == 1) {
                        codes.push(e.conserveContentCode);
                    }
                });
                if (codes.length == 0) {
                    top.layer.msg('请至少选择一项养护内容', {
                        icon : 2
                    });
                    return;
                }
                var index = top.layer.load();
                $.post(locat + '/car-conserve/content/set', {
                    splitId : this.Id,
                    conserveContentCodes : codes.join(',')
                }).done(function(data) {
                    top.layer.close(index);
                    var icon = 2;
                    if (data.success) {
                        icon = 1;
                        _this.loadProperties();
                    }
                    top.layer.msg(data.message, {
                        icon : icon
                    });
                }).fail(function() {
                    top.layer.close(index);
                    top.layer.msg('保存设置出错，请联系管理员', {
                        icon : 2
                    });
                });
            },
            // 保存养护周期设置
            save : function() {
                var _this = this;
                var index = top.layer.load();
                var settings = this.settings;

                $.ajax({
                    url : locat + '/car-conserve/period/set',
                    type : 'post',
                    data : JSON.stringify(settings),
                    contentType : 'application/json;charset=utf-8',
                    success : function(data) {
                        top.layer.close(index);
                        var icon = 2;
                        if (data.success) {
                            icon = 1;
                            _this.loadPeriod();
                        }
                        top.layer.msg(data.message, {
                            icon : icon
                        });
                    },
                    error : function() {
                        top.layer.close(index);
                        top.layer.msg('保存设置错误，请联系管理员', {
                            icon : 2
                        });
                    }
                });

            },
            addContents : function() {
                var _this = this;
                top.layer.open({
                    title : '添加养护内容设置',
                    type : 2,
                    area : getArea(1000, 600),
                    maxmin : true,
                    content : locat + '/html/car/content-add.html',
                    end : function() {
                        _this.loadProperties();
                    }
                });
            }

        },
        mounted : function() {
            this.loadPeriod();
            this.loadProperties();
        }

    });

});