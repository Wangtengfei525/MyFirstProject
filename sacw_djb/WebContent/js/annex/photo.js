$(function () {
    var exchangeId = getParameter(window.location.search, 'exchangeId');
    if (!exchangeId) {
        top.layer.alert('未提供交换记录id，请联系管理员');
        return;
    }
    var rotate = 0; // 旋转角度
    var video = document.getElementById('video');
    var canvas = document.getElementById('canvas');
    var btnshoot = document.getElementById('shoot');
    var btnupload = document.getElementById('upload');
    var btnrotateleft = document.getElementById('rotate-left');
    var btnrotateright = document.getElementById('rotate-right');
    var checkupload = document.getElementById('upload-after-take');
    var context = canvas.getContext('2d');
    var width = 1600;
    var height = 0;
    var captured = false;
    var fileindex = 1;

    btnshoot.addEventListener('click', takephoto);
    btnupload.addEventListener('click', upload);
    btnrotateleft.addEventListener('click', rotateLeft);
    btnrotateright.addEventListener('click', rotateRight);

    navigator.mediaDevices.getUserMedia({
        video: true,
        audio: false
    }).then(function (mediaStream) {
        video.srcObject = mediaStream;
        video.onloadedmetadata = function () {
            video.play();
        };
    }).catch(function (error) {
        top.layer.alert('摄像头开启失败');
    });

    video.addEventListener('canplay', function (ev) {
        height = video.videoHeight / (video.videoWidth / width);
        video.setAttribute('width', width);
        video.setAttribute('height', height);
        canvas.setAttribute('width', width);
        canvas.setAttribute('height', height);
    }, false);

    /**
     * 拍摄
     */
    function takephoto() {
        captured = true;
        if (rotate % 180 == 0) {
            context.drawImage(video, 0, 0, width, height);
        } else {
            context.save();
            context.translate(height / 2, width / 2);
            context.rotate(rotate * Math.PI / 180);
            context.drawImage(video, -width / 2, -height / 2, width, height);
            context.restore();
        }
        if (checkupload.checked) {
            upload();
        }
    }

    /**
     * 上传
     */
    function upload() {

        if (!captured) {
            top.layer.alert('还未拍摄');
            return;
        }

        var index = top.layer.load();

        $.post(locat + '/annex/upload-base64', {
            data: canvas.toDataURL('image/png'),
            exchangeId: exchangeId,
            file_name: 'image' + fileindex++ + ".png"
        }).done(function (data) {
            top.layer.close(index);
            var icon = 2;
            if (data.success) {
                icon = 1;
            }
            top.layer.msg(data.message, {
                icon: icon
            });
        }).fail(function () {
            top.layer.close(index);
            top.layer.msg('上传失败', {
                icon: 2
            });
        });

    }

    /**
     * 左旋90度
     */
    function rotateLeft() {
        rotate += 90;
        fixRotate();
        $('video').css('transform', 'rotate(' + rotate + 'deg)');
        resizeCanvas();
    }

    /**
     * 右旋90度
     */
    function rotateRight() {
        rotate -= 90;
        fixRotate();
        $('video').css('transform', 'rotate(' + rotate + 'deg)');
        resizeCanvas();
    }

    /**
     * 旋转大小超 ±360后重设角度
     */
    function fixRotate() {
        if (rotate >= 360) {
            rotate -= 360;
        } else if (rotate <= -360) {
            rotate += 360;
        }
    }

    /**
     * 重设canvas大小
     */
    function resizeCanvas() {
        if (rotate % 180 == 0) {
            canvas.setAttribute('width', width);
            canvas.setAttribute('height', height);
        } else {
            canvas.setAttribute('width', height);
            canvas.setAttribute('height', width);
        }
    }

});