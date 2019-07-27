<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>登录界面</title>
		<!-- 引入样式 -->
		<!--<link rel="stylesheet" href="${base}/resources/common/css/Element-index.css">-->
		<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
		<link rel="stylesheet" href="${base}/resources/common/css/login/loginCss.css">
	</head>

	<body>
		<center>
			<div id="app" class="lg-container">
				<h1>老人健康管理系统</h1>
				<el-form :model="loginForm" :rules="rules2" ref="loginForm" label-width="100px">
					<el-form-item label="用户名：" prop="username">
						<el-input type="text" v-model="loginForm.username" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item label="密码：" prop="password">
						<el-input type="password" v-model="loginForm.password" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item label="验证码：" prop="verificationCode">
						<el-col :span="11">
							<el-input type="text" v-model="loginForm.verificationCode"></el-input>
						</el-col>
						<el-col :span="10"> <span><img id="vcodeimg"
				src="${base}/common/captcha.jhtml?captchaId=${captchaId}"/></span> </el-col>
						<el-col class="line" :span="1">
							<el-button type="text" @click="changeVcode">换一张</el-button>
						</el-col>
					</el-form-item>
					<el-form-item label="登录类型：" prop="radio">
						<template>
							<el-radio-group v-model="loginForm.radio">
								<el-radio label="1">顾客</el-radio>
								<el-radio label="2">企业</el-radio>
								<el-radio label="3">管理员</el-radio>
							</el-radio-group>
						</template>
					</el-form-item>
					<el-form-item label-width="30px" prop="checked">
						<el-col :span="5">
							<el-checkbox v-model="loginForm.checked">自动登录</el-checkbox>
						</el-col>
						<el-col :span="12">&nbsp;</el-col>
						<el-col :span="3">
							<el-button type="text" style="color: #5A5E66;" @click="forget">忘记密码</el-button>
						</el-col>
						<el-col :span="1"> | </el-col>
						<el-col :span="3">
							<el-button type="text" style="color: #5A5E66;" @click="register">免费注册</el-button>
						</el-col>
					</el-form-item>
					<el-form-item label-width="30px">
						<el-button type="primary" round @click="submitForm('loginForm')" style="width: 100%;">登录</el-button>
					</el-form-item>
					<el-form-item label-width="30px">
						<el-col class="other_text">
							<span style="background-color:#f4f4f4">使用合作网站帐号登录</span>
						</el-col>
						<el-col class="other mt15">
							<a href="#">支付宝</a> |
							<a href="#">新浪微博</a>
							|
							<a href="#">QQ</a> |
							<a href="#">微信</a>
						</el-col>
					</el-form-item>
				</el-form>
			</div>
		</center>
	</body>
	<!-- 引入 Vue -->
	<!--<script src="${base}/resources/common/js/Element-vue.js"></script>-->
	<script src="https://unpkg.com/vue/dist/vue.js"></script>
	<!-- 引入组件库 -->
	<!--<script src="${base}/resources/common/js/Element-index.js"></script>-->
	<script src="https://unpkg.com/element-ui/lib/index.js"></script>
	<!-- 引入JQ -->
	<script type="text/javascript" src="${base}/resources/common/js/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="${base}/resources/common/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="${base}/resources/common/js/jsbn.js"></script>
	<script type="text/javascript" src="${base}/resources/common/js/prng4.js"></script>
	<script type="text/javascript" src="${base}/resources/common/js/rng.js"></script>
	<script type="text/javascript" src="${base}/resources/common/js/rsa.js"></script>
	<script type="text/javascript" src="${base}/resources/common/js/base64.js"></script>
	<script type="text/javascript">
		var Main = {
			data() {
				var checkVerificationCode = (rule, value, callback) => {
					if(!value) {
						callback(new Error('请输入验证码!'));
						return false;
					} else {
						callback();
					}
				};
				var validateUsername = (rule, value, callback) => {
					if(value === '') {
						callback(new Error('请输入用户名或者邮箱或者手机号！'));
						return false;
					} else {
						callback();
					}
				};
				var validatePassword = (rule, value, callback) => {
					var regex = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}');
					if(!value) {
						callback(new Error('请输入密码!'));
						return false;
					} else {
						callback();
					}
				};
				var checkChecked = (rule, value, callback) => {
					if(!value) {
						$.cookie("checked", "false", {
							expire: -1
						});
						$.cookie("username", "", {
							expires: -1
						});
						$.cookie("password", "", {
							expires: -1
						});
					} else {
						var username = this.loginForm.username;
						var password = this.loginForm.password;
						var type = this.loginForm.radio;
						$.cookie("checked", "true", {
							expires: 7
						}); //存储一个带7天期限的cookie
						$.cookie("username", username, {
							expires: 7
						});
						$.cookie("password", password, {
							expires: 7
						});
						$.cookie("type", type, {
							expires: 7
						});
					}
					callback();
				};
				var checkRadio = (rule, value, callback) => {
					if(!value) {
						callback(new Error('请选择登录类型！'));
						return false;
					} else {
						callback();
					}
				};
				var checkedCookie, type;
				var usernameCookie = $.cookie("username");
				var passwordCookie = $.cookie("password");
				if($.cookie("checked") == "true") {
					checkedCookie = true;
				} else {
					checkedCookie = false;
				}
				if($.cookie("type") != null) {
					radioCookie = $.cookie("type");
				} else {
					radioCookie = '1';
				}
				return {
					loginForm: {
						username: usernameCookie,
						password: passwordCookie,
						verificationCode: '',
						checked: checkedCookie,
						radio: radioCookie,
					},
					rules2: {
						username: [{
							validator: validateUsername,
							trigger: 'blur'
						}],
						//												password: [{
						//													validator: validatePassword,
						//													trigger: 'blur'
						//												}],
						verificationCode: [{
							validator: checkVerificationCode,
							trigger: 'blur'
						}],
						checked: [{
							validator: checkChecked,
							trigger: 'blur'
						}],
						radio: [{
							validator: checkRadio,
							trigger: 'blur'
						}],
					}
				};
			},
			methods: {
				changeVcode: function() {
					// 更换验证码
					$("#vcodeimg").attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
				},
				register: function() {
					window.location.href = "${base}/login/register.jhtml";
				},
				forget: function() {
					window.open("${base}/login/forgotPassword.jhtml");
				},
				submitForm(loginForm) {
					this.$refs[loginForm].validate((valid) => {
						if(valid) {
							var username = this.loginForm.username;
							var password = this.loginForm.password;
							var type = this.loginForm.radio;
							var captcha = this.loginForm.verificationCode
							var that = this;
							$.ajax({
								url: "${base}/common/public_key.jhtml",
								type: "GET",
								dataType: "json",
								cache: false,
								success: function(data) {
									var rsaKey = new RSAKey();
									rsaKey.setPublic(b64tohex(data.modulus), b64tohex(data.exponent));
									password = hex2b64(rsaKey.encrypt(password));
									$.ajax({
										type: 'post',
										url: '${base}/login/check.jhtml',
										data: {
											"userName": username,
											"password": password,
											"type": type,
											"captcha": captcha,
											"captchaId": "${captchaId}",
										},
										dataType: "json",
										success: function(date) {
											if(date == 2) {
												that.$message({
													message: '恭喜，登录成功！',
													type: 'success',
													duration: 1000,
												});
												setTimeout(function() {
													window.location.href = "${base}/login/index.jhtml";
												}, 1000);
											} else if(date == 1) {
												$("#vcodeimg").attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
												that.$message({
													message: '验证码传输有误！',
													type: 'warning',
													duration: 3000,
												});
											} else if(date == 3) {
												$("#vcodeimg").attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
												that.$message({
													message: '账号密码输入错误！',
													type: 'warning',
													duration: 3000,
												});
											} else if(date == 4) {
												$("#vcodeimg").attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
												that.$message({
													message: '验证码输入有误！',
													type: 'warning',
													duration: 3000,
												});
											} else if(date == 5) {
												that.$message({
													message: '系统发生错误！',
													type: 'error',
													duration: 1000,
												});
//												setTimeout(function() {
//													window.location.href = "404.jsp";
//												}, 1000);
											} else {
												that.$message({
													message: '账号密码不存在！',
													type: 'warning',
													duration: 3000,
												});
											}
										},
										error: function(date) {
											that.$message({
												message: '系统异常！',
												type: 'error',
												duration: 3000,
											});
										}
									});
								}
							});
						} else {
							console.log('error submit!!');
							return false;
						}
					});
				},
				resetForm(loginForm) {
					this.$refs[loginForm].resetFields();
				}
			}
		}
		var Ctor = Vue.extend(Main)
		new Ctor().$mount('#app')
	</script>

</html>