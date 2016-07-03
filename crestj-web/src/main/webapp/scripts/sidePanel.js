$(document).ready(function() {
	$('.defaultHidden').hide();
	$('.expandableToggler').click(function()
	{
		$(this).next('.expandablePanel').slideToggle(400);
	});
});