var parentStore;

$(function() {

    var id = getParameter(window.location.search, 'id');

    if (id) {
        getStore(id);
    }

    var parentId = getParameter(window.location.search, 'parentId');

    if (parentId) {
        $('#input-store-parent-id').val(parentId);
        getParent(parentId);
    }

    $('#select-store-type').select2();
    $('#select-store-type').bind('change', function() {
        var value = $('#select-store-type').val();
        if (value === '1') {
            $('#div-store-contro').hide();
            $('#input-store-contro').val(0);
        } else {
            $('#div-store-contro').show();
        }
    });

    $('#input-store-name').bind('input propertychange', function() {
        var name = $('#input-store-name').val();
        var newName = parentStore ? parentStore.storeUnitName + '/' + name : name;
        $('#input-store-unit-name').val(newName);
    });

});

/**
 * 获取储物柜信息
 * 
 * @param id
 * @returns
 */
function getStore(id) {
    var index = top.layer.load();
    $.get(locat + '/store/get', {
        id : id
    }).done(function(data) {
        top.layer.close(index);
        $('#form-store-edit').find('[name]').each(function(index, element) {
            var $ele = $(element);
            var name = $ele.attr('name');
            $ele.val(data.store[name]);
        });
        $('#select-store-type').val(data.store.treeType).trigger('change');
        if (data.store.parentId != '0') {
            getParent(data.store.parentId);
        } else {
            $('#input-store-parent-id').val('0');
            $('#input-store-parent-name').val('顶级储物柜');
        }
    }).fail(function() {
        top.layer.close(index);
        top.layer.msg('加载储物柜信息失败');
    });
}

/**
 * 获取父储物柜信息
 * 
 * @param id
 * @returns
 */
function getParent(parentId) {
    var index = top.layer.load();
    $.get(locat + '/store/get', {
        id : parentId
    }).then(function(data) {
        top.layer.close(index);
        parentStore = data.store;
        $('#input-store-parent-id').val(data.store.id);
        $('#input-store-parent-name').val(data.store.storeUnitName);
    }).fail(function() {
        top.layer.close(index);
        top.layer.msg('加载父储物柜信息失败');
    });
}

function addStore() {

}

function save() {
    var url;
    var data = $('#form-store-edit').serializeArray();
    if ($('#input-id').val()) {
        url = locat + '/store/update';
    } else {
        url = locat + '/store/add';
    }

    $.post(url, data).then(function(data) {
        var icon = 2;
        if (data.success) {
            icon = 1;
            top.layer.close(top.layer.getFrameIndex(window.name));
        }
        top.layer.msg(data.message, {
            icon : icon
        });
    });

}
