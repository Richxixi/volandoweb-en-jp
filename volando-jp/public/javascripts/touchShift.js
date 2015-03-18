$(function(){
	//參數設定:
	//wipeLeft 向左滑動
	//wipeRight 向右滑動
	//wipeUp 向上滑動
	//wipeDown 向下滑動
	//min_move_x 水平移動最小像
	//min_move_y 垂直移動最小像素
	/*
	$("#imagegallery").touchwipe({
		 wipeLeft: function() { alert("left"); },
		 wipeRight: function() { alert("right"); },
		 wipeUp: function() { alert("up"); },
		 wipeDown: function() { alert("down"); },
		 min_move_x: 20,
		 min_move_y: 20,
		 preventDefaultEvents: true
	});*/
 
 
	$('#imagegallery').cycle({
		timeout: 0,
		fx: 'scrollHorz',
		next: '#next',
		prev: '#prev' 
	});
 
	$("#imagegallery").touchwipe({
 		wipeLeft: function() {
 	 		$("#imagegallery").cycle("next");
 
 		},
 		wipeRight: function() {
 	 		$("#imagegallery").cycle("prev");			
 		}
	});
})