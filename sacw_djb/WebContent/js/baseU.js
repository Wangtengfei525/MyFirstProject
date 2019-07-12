/**
 * 附件上传
 * 
 * @returns
 */
var uploader;
function upload() {
	var $ = jQuery, $wrap = $('#uploader'),

	// 图片容器
	$queue = $('<ul class="filelist"></ul>').appendTo($wrap.find('.queueList')),

	// 状态栏，包括进度和控制按钮
	$statusBar = $wrap.find('.statusBar'),

	// 文件总体选择信息。
	$info = $statusBar.find('.info'),

	// 上传按钮
	$upload = $wrap.find('.uploadBtn'),

	// 没选择文件之前的内容。
	$placeHolder = $wrap.find('.placeholder'),

	// 总体进度条
	$progress = $statusBar.find('.progress').hide(),

	// 添加的文件数量
	fileCount = 0,

	// 添加的文件总大小
	fileSize = 0,

	// 优化retina, 在retina下这个值是2
	ratio = window.devicePixelRatio || 1,

	// 缩略图大小
	thumbnailWidth = 110 * ratio, thumbnailHeight = 110 * ratio;
	$.get(locat + '/annex/uuid', function(data) {
		var uuid = data.message;
		uploader = WebUploader.create({
			pick : {
				id : '#filePicker',
				label : '点击选择图片'
			},
			dnd : '#uploader .queueList',
			paste : document.body,
			accept : {
				title : 'Images',
				extensions : 'gif,jpg,jpeg,bmp,png',
				mimeTypes : 'image/*'
			},
			swf : locat + '/assets/webuploader/Uploader.swf',
			server : locat + '/annex/upload',
			duplicate : false,
			resize : false,
			disableGlobalDnd : true,

			chunked : true,
		});
		// 添加“添加文件”的按钮，
		uploader.addButton({
			id : '#filePicker2',
			label : '继续添加'
		});
		uploader.on('fileQueued', function(file) {
			var $li = $('<li id="' + file.id + '">' + '<p class="title">' + file.name + '</p>' + '<p class="imgWrap"></p>' + '<p class="progress"><span></span></p>' + '</li>'),

			$btns = $('<div class="file-panel">' + '<span class="cancel">删除</span></div>').appendTo($li),

			$wrap = $li.find('p.imgWrap');

			if (file.getStatus() === 'invalid') {
				showError(file.statusText);
			} else {
				$wrap.text('预览中');
				uploader.makeThumb(file, function(error, src) {
					if (error) {
						$wrap.text('不能预览');
						return;
					}

					var img = $('<img src="' + src + '">');
					$wrap.empty().append(img);
				}, thumbnailWidth, thumbnailHeight);

				file.rotation = 0;
			}

			$btns.on('click', 'span', function() {
				uploader.removeFile(file);

			});

			$li.appendTo($queue);
		});

		uploader.on('uploadBeforeSend', function(block, data) {
			data.exchangeId = uuid;
		});

		uploader.on('uploadFinished', function() {
			var id = $('#exchangeTable').datagrid('getSelected').id;
			var remark = $('#caseForm input[name=\'remark\']').val();
			var operationTime = $('#caseForm input[name=\'operationTime\']').val();
			$.post(locat + '/out/out', {
				id : id,
				tempId : uuid,
				remark : remark,
				operationTime : operationTime
			}).done(function(data) {
				$.messager.progress('close');
				$.messager.alert('提示', data.message);
				if (data.success) {
					$('#putinLayer').dialog('close');
					$('#exchangeTable').datagrid('reload');
				}
			});
		});
	});

}