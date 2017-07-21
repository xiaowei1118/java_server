
function getSignature(){
	
//	定义申请获得的appKey和appSecret
	var appkey = "XXXXXXXX";  
	var secret = "13234234234234";  

	// 创建参数表  
	var param = {};  
	param["password"]="zc0829";  
	param["phone"]="xiaowei";  
    param['campusId']="1";
	// 对参数名进行字典排序  
	var array = new Array();  
	for(var key in param)  
	{  
		array.push(key);  
	}  
	array.sort();  

	// 拼接有序的参数名-值串  
	var paramArray = new Array();  
	// paramArray.push(appkey);  
	for(var index in array)  
	{  
		var key = array[index];  
		paramArray.push(key +"="+ param[key]);  
	}  
	paramArray.push(secret); 
	
	var shaSource = paramArray.join("");  
	console.log(shaSource);
	var sign = $.md5(shaSource);  
	return sign;
}   