var locat = (window.location + '').split('/');
var audio;

$.ajaxSetup({
    cache : false
});
$(function() {
    if ('main' == locat[3]) {
        locat = locat[0] + '//' + locat[2];
    } else {
        locat = locat[0] + '//' + locat[2] + '/' + locat[3];
    }

    if (window != top) {
        top.location.href = location.href;
    }
});

function login() {
    var username = $("#username").val();
    var password = $("#password").val();
    var LoginT = true;
    if (username == null || username == '') {
        // LoginT = false;
        layer.tips('用户名不能为空', '#name');
        return;
    }
    if (password == null || password == '') {
        layer.tips('密码不能为空', '#password');
        // LoginT = false;
        return;
    }
    var params = $('#login-form').serializeArray();

    // loading层
    var index = layer.load(1, {
        shade : [ 0.1, '#fff' ]
    // 0.1透明度的白色背景
    });
    $.ajax({
        url : locat + "/system/login",
        data : {
            username : username,
            password : password
        },
        type : "post",
        success : function(data) {
            if (data.success) {
                window.location = locat + "/html/main.html";
            } else {
                if (data.message == "帐号不存在") {
                    layer.tips('帐号不存在', '#name');
                } else if (data.message == "帐号已锁定") {
                    layer.tips('帐号已锁定', '#name');
                } else if (data.message == "密码错误") {
                    layer.tips('密码错误', '#password');
                } else {
                    layer.tips(data.message, '#name');
                }
                layer.close(index);
            }
        },
        error : function() {

            layer.cose(index);
        }
    });

}
function on_return() {
    if (event.keyCode == 13) {
        login();
    }
}

$(document).keyup(function(event) {
    if (event.keyCode == 13) {
        $("#submit").trigger("click");
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
}, 3000);
