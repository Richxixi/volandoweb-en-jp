$(function() {
	
	H = $(window).height();
	W = $(window).width();
	$('#contents').css({'margin-top' : H+'px'});
	$(window).resize(function(){
		var w = $(window).width();
		var h = $(window).height();
		$('#contents').css({'margin-top' : h+'px'});
	});
	
	$.sublime_slideshow({
		src:[
		{url:"/public/images/spring/slider/1.jpg"},
		{url:"/public/images/spring/slider/2.jpg"},
		{url:"/public/images/spring/slider/3.jpg"},
		{url:"/public/images/spring/slider/4.jpg"}
		],
		duration:   7,
		fade:       2,
		scaling:    false,
		rotating:   false,
		overlay:    "/public/images/pattern.png"
	});
	
	$('body').fadeIn();
	
	$('.flip').bind("click",function(){		
		var elem = $(this);		
		if(elem.data('flipped')){
			elem.revertFlip();
			elem.data('flipped',false)
		}
		else{
			elem.flip({
				direction:'lr',
				speed: 350,
				color : '#3E6A37',
				onBefore: function(){
					elem.html(elem.siblings('.data').html());
				}
			});
			elem.data('flipped',true);
		}
	});
	
});
