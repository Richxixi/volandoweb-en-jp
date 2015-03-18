<?php
function HexToRGB($hex) {
$hex = ereg_replace("#", "", $hex);
$color = array();

if(strlen($hex) == 3) {
	$color['r'] = hexdec(substr($hex, 0, 1) . $r);
	$color['g'] = hexdec(substr($hex, 1, 1) . $g);
	$color['b'] = hexdec(substr($hex, 2, 1) . $b);
}
else if(strlen($hex) == 6) {
	$color['r'] = hexdec(substr($hex, 0, 2));
	$color['g'] = hexdec(substr($hex, 2, 2));
	$color['b'] = hexdec(substr($hex, 4, 2));
}

return $color;
}

$text  = $_GET["string"];
$size = $_GET["size"];
$width = $_GET["width"];
$height = $_GET["height"];
$colorH2RGB = HexToRGB($_GET["color"]);

$image = imagecreate($width, $height); // width = 400, height = 35
$bg    = imagecolorallocate($image, 255, 255, 255); // 背景色
imagecolortransparent($image,$bg);//轉換成透明底

//$font  = 'BlackRegular.ttc'; // 字型
$font  = 'black.ttc'; // 字型
$black_color = imagecolorallocate($image, $colorH2RGB['r'] , $colorH2RGB['g'] , $colorH2RGB['b'] ); // 字的顏色

// imagettftext($image, 大小, 旋轉, 與左邊的距離, 與上面的距離, $black_color, $font, $text);
imagettftext($image, $size, 0, 0, 20, $black_color, $font, $text);

header('Content-type: image/gif');
imagegif($image);
imagedestroy($image);
?>