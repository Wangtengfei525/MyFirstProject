$(function() {

    window.onresize = resize;
    document.getElementById('mainiframe').onload = resize;

    // 用户信息加载
    getUserinfo();

    var audio;

    // 菜单加载
    $.get(locat + '/system/permissions', function(menlist) {
        for (var i = 0; i < menlist.length; i++) {
            var menlistul = menlist[i];
            if (menlistul) {
                var lis;
                if (menlistul.children.length > 0) {// 有第二层
                    lis = '<li class="treeview"><a href="#"><i class="fa fa-link"></i><span>' + menlistul.name + '</span><span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i>'
                    lis += '</span></a><ul class="treeview-menu">';
                    var chidren = menlistul.children;
                    for (var j = 0; j < chidren.length; j++) {//    
                        lis += '<li class="iframe"><a href="#" onclick="openiframe(\'' + chidren[j].url + '\')"><i class="fa fa-circle-o"></i>' + chidren[j].name + '</a></li>'
                    }

                    lis += '</ul></li>';
                } else {// 没有第二层
                    lis = '<li class="iframe"> <a href="#" onclick="openiframe(\'' + menlistul.url + '\')"><i class="fa fa-link"></i><span>' + menlistul.name + '</span></a></li>';
                }
                $("#menullist").append(lis);
            }

        }
    });

    // 每隔3秒执行一次
    window.setInterval(function() {
        $.get(locat + '/warning/count').done(function(data) {
            if (data.count > 0) {
                if (!audio) {
                    audio = document.createElement('audio');
                    audio.src = locat + '/html/1020.mp3';
                    audio.loop = true;
                }
                audio.play();
                top.layer.msg('有' + data.count + '个物品异常出库，请前往报警预警中查看！', {
                    offset : '80px'
                });
            } else {
                if (audio) {
                    audio.pause();
                    audio = null;
                }
            }
        }).fail(function() {
            if (audio) {
                audio.pause();
                audio = null;
            }
            top.layer.msg('异常出库报警异常，请联系管理员', {
                icon : 2
            });
        });
    }, 100000);

    getSecondmentOverdues();

});






/**
 * 获取用户数据
 * 
 * @returns
 */
function getUserinfo() {
    var index = top.layer.load();
    $.get(locat + '/system/current-user').done(function(data) {
        top.layer.close(index);
        if (data.success) {
            $('#user-name').html(data.user.name);
        } else {
            top.layer.msg(data.message, {
                icon : 2
            });
        }
    }).fail(function() {
        top.layer.close(index);
        top.layer.msg('加载个人信息失败，请联系管理员', {
            icon : 2
        });
    });

}

function openiframe(url) {
    url = locat + '/html' + url;
    $("#mainiframe").attr("src", url);
}

/**
 * 调整iframe高度
 * 
 * @returns
 */
function resize() {
    var height = $(window).height();
    $("#mainiframe").height(height - 100);
}

/**
 * 修改密码
 * 
 * @returns
 */
function changePassword() {
    top.layer.open({
        type : 2,
        title : '密码修改',
        maxmin : true,
        area : getArea(550, 350),
        content : locat + '/html/change-password.html'
    });
}

/**
 * 退出登录
 * 
 * @returns
 */
function logout() {

    layer.confirm('确定退出系统？', function(i) {
        layer.close(i);
        $.post(locat + '/system/logout', function(data) {
            if (data.success) {
                setTimeout(function() {
                    window.location = locat + '/html/login.html';
                }, 500);
            }
        });
    });

}

/**
 * 获取借调逾期信息
 * 
 * @returns
 */
function getSecondmentOverdues() {
    $.get(locat + '/secondment/overdues').done(function(data) {
        if (data.success && data.total > 0) {
            top.layer.confirm(data.total + '个借调物品即将（已经）逾期', {
                offset : 'rb',
                btn : [ '查看', '取消' ],
                icon : '7',
                title : '提醒'
            }, function(index) {
                top.layer.close(index);
                top.layer.open({
                    type : 2,
                    title : '借调逾期查询',
                    area : getArea(1200, 600),
                    maxmin : true,
                    content : locat + '/html/secondment/overdue.html'
                });
            });
        }
    }).fail(function() {
        top.layer.msg('查询借调逾期信息失败，请联系管理员');
    });
}





