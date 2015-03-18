function runstart(){
	$('nav#head').animate({'margin-top' : '-110px'},'slow'); 
	$("loading").fadeIn();
}

//window.setTimeout(function(){show5();},5000);
//zcheng
$(window).load(function() {
	window.setTimeout("runstart();", 4000);
});


$(function() {
	/*
	$(window).scroll(function () {
		var bodyheight = $('#contents').height();
		var mainheight = bodyheight-300;
		var s = $(this).scrollTop();
		var m = mainheight;
		if(s > m) {
			$('nav#head').stop().animate({'margin-top' : '0px'},'slow');
		}
		else if(s < m) {	
			$('nav#head').stop().animate({'margin-top' : '-110px'},'slow');
		}
	});
	*/
	$('nav#head').hover(
		function(){  
			$(this).stop().animate({'margin-top' : '0px'},'slow'); 
		},
		function(){
			$(this).stop().animate({'margin-top' : '-110px'},/*'slow'*/4000);
			//setTimeout("runstart();", 2000);
		}
	);
	
	var onMouseOutOpacity = 0.67;
	/*$('#thumbs ul.thumbs li').opacityrollover({
		mouseOutOpacity:   onMouseOutOpacity,
		mouseOverOpacity:  1.0,
		fadeSpeed:         'fast',
		exemptionSelector: '.selected'
	});
	
	var gallery = $('#thumbs').galleriffic({
		delay:                     5000,
		numThumbs:                 15,
		preloadAhead:              10,
		enableTopPager:            true,
		enableBottomPager:         true,
		maxPagesToShow:            10,
		imageContainerSel:         '#slideshow',
		controlsContainerSel:      '#controls',
		captionContainerSel:       '#caption',
		loadingContainerSel:       '#loading',
		renderSSControls:          true,
		renderNavControls:         true,
		playLinkText:              'Play Slideshow',
		pauseLinkText:             'Pause Slideshow',
		prevLinkText:              '&lsaquo; Previous Photo',
		nextLinkText:              'Next Photo &rsaquo;',
		nextPageLinkText:          'Next &rsaquo;',
		prevPageLinkText:          '&lsaquo; Prev',
		enableHistory:             false,
		autoStart:                 true,
		syncTransitions:           true,
		defaultTransitionDuration: 900,
		onSlideChange:function(prevIndex, nextIndex) {
			this.find('ul.thumbs').children()
				.eq(prevIndex).fadeTo('fast', onMouseOutOpacity).end()
				.eq(nextIndex).fadeTo('fast', 1.0);
		},
		onPageTransitionOut:       function(callback) {
			this.fadeTo('fast', 0.0, callback);
		},
		onPageTransitionIn:        function() {
			this.fadeTo('fast', 1.0);
		}
	});*/
		
});
