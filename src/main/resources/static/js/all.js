/**
 * 
 */


function getUserAuth(username){
	var userLogin = new FormData();
	userLogin.append('login',username);
	
	$.ajax({
		type : "POST", 
		contentType:false,
        processData:false,
		url : "/account/getAuthority",
		data : userLogin,
	}).success(function(data) {
		console.log(data);
		if(data=="ROLE_STUDENT"){
			$(".studentnav").css("display","inline-block");
			$(".teachernav").css("display","none");
			$(".adminnav").css("display","none");
		}else if(data=="ROLE_TEACHER"){
			$(".studentnav").css("display","none");
			$(".teachernav").css("display","inline-block");
			$(".adminnav").css("display","none");
		}else if(data=="ROLE_ADMIN"){
			$(".studentnav").css("display","none");
			$(".teachernav").css("display","none");
			$(".adminnav").css("display","inline-block");
		}else{
			$(".studentnav").css("display","none");
			$(".teachernav").css("display","none");
			$(".adminnav").css("display","none");
		}
	}).fail(function(err) {
		console.log(err);
	})
}


