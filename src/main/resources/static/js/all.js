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
			$(".studentnav").css("display","inline-block");
			$(".teachernav").css("display","none");
			$(".adminnav").css("display","none");
		}
	}).fail(function(err) {
		console.log(err);
	})
}

function getPersonalAuth(username){
	var userLogin = new FormData();
	userLogin.append('login',username);
	$.ajax({
		type : "POST", 
		contentType:false,
        processData:false,
		url : "/account/getAuthority",
		data : userLogin,
	}).success(function(data) {
		if(data=="ROLE_STUDENT"){
			$(".all").css("display","inline-block");
			$(".studentPerson").css("display","inline-block");
		}else{
			$(".all").css("display","inline-block");
			$(".studentPerson").css("display","none");
		}
	}).fail(function(err) {
		console.log(err);
	})
}

function reloadData(curTable,dataList) {
    var currentPage = curTable.page()
    curTable.clear()
    curTable.rows.add(dataList)
    curTable.page(currentPage).draw( false );
}


