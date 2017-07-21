//<script src="/foryou/js/jquery.cookie.js"></script>
document.write("<script src='/js/jquery.cookie.js'><\/script>");
 /* 
 */
// 返回cookies字符串中指定键对应的值
function getCookie(cname) {
	/*var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i].trim();
		if (c.indexOf(name) == 0)
			return c.substring(name.length, c.length);
	}
	return "";*/
	//return $.cookie(cname);
	//var cookieVar;
	//$.getScript("/foryou/js/jquery.cookie.js").done(function(){
          return  $.cookie(cname);
    //});

}