//*********获取语音验证码*********//

var AddInterValObj; // timer变量，控制时间
var adcount = 60; // 间隔函数，1秒执行
var addCount; // 当前剩余秒数
var hash;
var codeTimestamp;

function sendAddmes() {
	/*
	 * var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	 * if(!myreg.test($("#email").val())) { layertest('请输入有效的邮箱') return
	 * false; }else{
	 */
	addCount = adcount;　　 // 设置button效果，开始计时
	$("#addSendCode").attr("disabled", "true");
	$("#addSendCode").val("" + addCount + "秒后重新获取").css({
		"background-color": "#002e5b"
	});
	AddInterValObj = window.setInterval(SetAddnTime, 1000); // 启动计时器，1秒执行一次
	　　 // 向后台发送处理数据
	var email = document.getElementById("email").value;
	console.log(email);
	$.ajax({　　
		type: "POST", // 用POST方式传输
		dataType: "JSON", // 数据格式:JSON
		contentType: "application/json",
		data: "email=" + email,
		url: '/account/sendmessage', // 目标地址
		error: function(data) {
			console.log(data);　　
		},
		success: function(data) {
			hash = data.hash;
			codeTimestamp = data.time;　　
		}
	});
	// }
}

// timer处理函数
function SetAddnTime() {
	if (addCount == 0) {
		window.clearInterval(AddInterValObj); // 停止计时器
		$("#addSendCode").removeAttr("disabled"); // 启用按钮
		$("#addSendCode").val("重新获取验证码").css({
			"background-color": "#0097a8"
		});
	} else {
		addCount--;
		$("#addSendCode").val("" + addCount + "秒后重新获取").css({
			"background-color": "#D1D4D3"
		});
	}
}

/*
 * function telphone(){ var myreg =
 * /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
 * if(!myreg.test($("#add_phone").val())) { layertest('请输入有效的手机号码');
 * $('.login_ipt').addClass('error'); return false; }else{
 * $('.login_ipt').removeClass('error'); } }
 * $(document).on('blur','.login_ipt',function(){ telphone(); });
 */

// code 验证
function code_test() {
	if ($('#verificationCode').val() == '') {
		layertest('验证码不能为空');
		$('#verificationCode').addClass('error');
	} else {
		$('#verificationCode').removeClass('error');
	}
}
$(document).on('blur', '.code', function() {
	code_test();
});

// layer modal
function layertest(content) {
	layer.open({
		content: content,
		btn: '我知道了'
	});
}
// layer loading
function loading(content) {
	layer.open({
		type: 2,
		content: content
	});
}

function registerCheck() {
	var verificationCode = document.getElementById("verificationCode").value;
	var userDTO = $('#registerForm').serializeObject();
	if ($('.error').length > 0) {
		layertest('请您填写验证码');
		return false;
	} else {
		$.ajax({
			type: "POST", // 用POST方式传输
			　dataType: "JSON", // 数据格式:JSON
			contentType: "application/json",
			url: "/account/register?hash=" + hash + "&time=" + codeTimestamp + "&verificationCode=" + verificationCode,
			data: JSON.stringify(userDTO),
		}).success(function(message) {
			layertest('注册成功!');
			console.log(message);
			window.location.href = "../registerInfo.html";
		}).fail(function(err) {
			console.log(err);
			layertest('注册失败!');
		})
		return false;
	}
}
// update btn click
/*
 * $(document).on('submit','#registerForm',function(event){
 * event.preventDefault(); //阻止form表单默认提交 if($('.error').length >0){
 * layertest('请您填写验证码'); }else{ //loading('跳转中'); if(checkCode ==
 * document.getElementById("code").value) { $.ajax({ type: "post", url:
 * "/account/register", data: $('#registerForm').serialize(),
 * }).success(function(message) { layertest('注册成功!');
 * console.log($('#registerForm').serialize());
 * //setTimeout("javascript:location.href='../registerInfo.html'", 2000);
 * console.log(message); }).fail(function(err){ console.log(err); })
 * 
 * }else { layertest('验证码错误'); }
 *  } })
 */