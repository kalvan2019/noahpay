$(document).ready(function() {// 显示许可证
	$('#license').click(function() {
		$('#bg').show();
		$('#license-show').show();
	});
	$('i.Lclose').click(function() {
		$('#bg').hide();
		$('#license-show').hide();
	});
	var wH = $(window).height();
	var wW = $(window).width();
	var hW = $('#license-show').outerWidth();
	var hH = $('#license-show').outerHeight();
	$('#license-show').find('img').css('width',hW);
	$('#license-show').css('bottom', 0);
	$('#license-show').css('left', (wW - hW) / 2);
});