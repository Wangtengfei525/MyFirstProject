/**
 * bootstrap table 扩展，在分页栏添加页面跳转输入框
 */
(function($) {
	'use strict';

	$.extend($.fn.bootstrapTable.defaults, {
		pageJump : true
	});

	var BootstrapTable = $.fn.bootstrapTable.Constructor;

	var _initPagination = BootstrapTable.prototype.initPagination;

	BootstrapTable.prototype.initPagination = function() {
		_initPagination.apply(this, arguments);
		if (this.options.pagination && this.options.pageJump) {
			var $pagination = this.$pagination;
			var $pagejump = $pagination.find('.page-jump');
			if ($pagejump.length == 0) {
				var $pageList = $pagination.find('div.pagination');
				$pageList.before('<div class="page-jump pull-right" style="margin-top:10px;"></div>');
				$pagejump = $pagination.find('.page-jump');
			}
			var html = [];
			html.push('<form class="form-inline">');
			html.push('<input class="form-control input-jump" style="width:70px;" type="number" value="' + this.options.pageNumber + '"/>');
			html.push('<a class="btn btn-default btn-jump">跳转</a>');
			html.push('</form>');
			$pagejump.html(html.join(''));

			var $jump = $pagination.find('.btn-jump');
			$jump.off('click').on('click', $.proxy(jump, this));
		}
	}

	function jump(event) {
		var number = this.$pagination.find('input.input-jump').val();
		if (number < 1) {
			number = 1;
		}
		if (number > this.options.totalPages) {
			number = this.options.totalPages;
		}
		this.options.pageNumber = number;
		this.updatePagination(event);
		return false;
	}

})(jQuery);