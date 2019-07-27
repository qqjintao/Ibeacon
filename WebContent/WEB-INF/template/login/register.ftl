<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>登录界面</title>
		<!-- 引入样式 -->
		<!--<link rel="stylesheet" href="${base}/resources/common/css/Element-index.css">-->
		<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
		<link rel="stylesheet" href="${base}/resources/common/css/register/registerCss.css">
	</head>

	<body>
		<center>
			<div id="app" class="lg-container">
				<el-form :model="registerForm" :rules="rules2" ref="registerForm" label-width="100px">
					<el-form-item>
						<el-radio-group v-model="registerForm.radioButton" size="medium" v-on:change="show">
							<el-radio-button label="mobileRadio">手 机 注 册</el-radio-button>
							<el-radio-button label="mailboxRadio">邮 箱 注 册</el-radio-button>
						</el-radio-group>
						<el-button type="primary" plain round @click="login">已有帐号，直接登录</el-button>
					</el-form-item>
					<el-form-item label="手机号码:" prop="mobile_phone">
						<el-input type="text" v-model="registerForm.mobile_phone" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item v-if="registerForm.seen" label="邮箱:" prop="mailbox">
						<el-input type="text" v-model="registerForm.mailbox" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item label="设置密码:" prop="password">
						<el-input type="password" v-model="registerForm.password" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item label="确认密码:" prop="passwords">
						<el-input type="password" v-model="registerForm.passwords" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item label="用户名:" prop="nickname">
						<el-input type="text" v-model="registerForm.nickname" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item label="验证码:" prop="verificationCode">
						<el-col :span="11">
							<el-input type="text" v-model="registerForm.verificationCode"></el-input>
						</el-col>
						<el-col :span="10"> <span><img id="vcodeimg"
				src="${base}/common/captcha.jhtml?captchaId=${captchaId}"/></span> </el-col>
						<el-col class="line" :span="1">
							<el-button type="text" @click="changeVcode">换一张</el-button>
						</el-col>
					</el-form-item>
					<el-form-item label="短信验证码:" prop="verification_code_phoneRegistry">
						<el-col :span="11">
							<el-input type="text" v-model="registerForm.verification_code_phoneRegistry"></el-input>
						</el-col>
						<el-col :span="10">
							<el-button type="info" plain @click="codePhone" v-bind:disabled="disabledInput">获取短信验证码</el-button>
						</el-col>
					</el-form-item>
					<el-form-item label="注册类型：" prop="radio">
						<template>
							<el-radio-group v-model="registerForm.radio">
								<el-radio label="1">顾客</el-radio>
								<el-radio label="2">企业</el-radio>
								<el-radio label="3">管理员</el-radio>
							</el-radio-group>
						</template>
					</el-form-item>
					<el-form-item prop="checked" style="float: left;">
						<el-checkbox v-model="registerForm.checked">我已阅读并同意<span style="color: blue;"><a href="#">《老人健康管理系统协议》</a></span></el-checkbox>
					</el-form-item>
					<el-form-item label-width="30px">
						<el-button type="primary" round @click="submitForm('registerForm')" style="width: 100%;">注册</el-button>
					</el-form-item>
				</el-form>
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
				var validateMobile_phone = (rule, value, callback) => {
					var type = this.registerForm.radio;
					if(!value) {
						callback(new Error('请输入手机号码!'));
						return false;
					} else if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(value))) {
						callback(new Error('输入手机号码有误!'));
						return false;
					} else {
						$.ajax({
							type: 'post',
							url: '${base}/login/check_username.jhtml',
							data: {
								"username": value,
								"type": type,
							},
							dataType: "json",
							success: function(date) {
								if(date) {
									callback();
									return true;
								} else {
									callback(new Error('该项输入已被人使用过！'));
									return false;
								}
							},
							error: function(date) {
								callback(new Error('系统错误！'));
								return false;
							}
						});
						return false;
					}
				};
				var validatePassword = (rule, value, callback) => {
					var regex = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}');
					if(!value) {
						callback(new Error('请输入密码!'));
						return false;
					} else if(!regex.test(value)) {
						callback(new Error('密码中必须包含字母、数字、特称字符，至少8个字符，最多30个字符!'));
						return false;
					} else {
						callback();
					}
				};
				var validatePasswords = (rule, value, callback) => {
					var value1 = this.registerForm.password;
					if(!value) {
						callback(new Error('请输入密码!'));
						return false;
					} else if(value1 != value) {
						callback(new Error('您输入的两次密码不一致!'));
						return false;
					} else {
						callback();
					}
				};
				var checkVerificationCode = (rule, value, callback) => {
					if(!value) {
						callback(new Error('请输入验证码!'));
						return false;
					} else {
						callback();
					}
				};
				var checkVerification_code_phoneRegistry = (rule, value, callback) => {
					if(!value) {
						callback(new Error('请输入手机验证码!'));
						return false;
					} else {
						callback();
					}
				};
				var checkChecked = (rule, value, callback) => {
					if(!value) {
						callback(new Error('未阅读《老人健康管理系统协议》!'));
						return false;
					} else {
						callback();
					}
				};
				var checkMailbox = (rule, value, callback) => {
					var type = this.registerForm.radio;
					var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
					if(!value) {
						callback(new Error('请输入您的电子邮箱地址！'));
						return false;
					} else if(!reg.test(value)) {
						callback(new Error('输入电子邮箱地址有误!'));
						return false;
					} else {
						$.ajax({
							type: 'post',
							url: '${base}/login/check_username.jhtml',
							data: {
								"username": value,
								"type": type,
							},
							dataType: "json",
							success: function(date) {
								if(date) {
									callback();
									return true;
								} else {
									callback(new Error('该项输入已被人使用过！'));
									return false;
								}
							},
							error: function(date) {
								callback(new Error('系统错误！'));
								return false;
							}
						});
						return false;
					}
				};
				var nicknameChecked = (rule, value, callback) => {
					var type = this.registerForm.radio;
					if(!value) {
						callback(new Error('请输入您的用户名！'));
						return false;
					} else {
						$.ajax({
							type: 'post',
							url: '${base}/login/check_username.jhtml',
							data: {
								"username": value,
								"type": type,
							},
							dataType: "json",
							success: function(date) {
								if(date) {
									callback();
									return true;
								} else {
									callback(new Error('该项输入已被人使用过！'));
									return false;
								}
							},
							error: function(date) {
								callback(new Error('系统错误！'));
								return false;
							}
						});
						return false;
					}
				};
				return {
					registerForm: {
						mobile_phone: '',
						password: '',
						passwords: '',
						nickname: '',
						verificationCode: '',
						verification_code_phoneRegistry: '',
						checked: '',
						seen: false,
						mailbox: '',
						radioButton: 'mobileRadio',
						radio: '1',
					},
					disabledInput: false,
					rules2: {
						mobile_phone: [{
							validator: validateMobile_phone,
							trigger: 'blur'
						}],
						//						password: [{
						//							validator: validatePassword,
						//							trigger: 'blur'
						//						}],
						passwords: [{
							validator: validatePasswords,
							trigger: 'blur'
						}],
						verificationCode: [{
							validator: checkVerificationCode,
							trigger: 'blur'
						}],
						verification_code_phoneRegistry: [{
							validator: checkVerification_code_phoneRegistry,
							trigger: 'blur'
						}],
						nickname: [{
							validator: nicknameChecked,
							trigger: 'blur'
						}],
						checked: [{
							validator: checkChecked,
							trigger: 'blur'
						}],
						mailbox: [{
							validator: checkMailbox,
							trigger: 'blur'
						}],
					}
				};
			},
			methods: {
				show: function() {
					this.registerForm.seen = !this.registerForm.seen
				},
				login: function() {
					window.location.href = "${base}/login/login.jhtml";
				},
				changeVcode: function() {
					$("#vcodeimg").attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
				},
				codePhone: function() {
					var mobile_phone = this.registerForm.mobile_phone;
					this.disabledInput = true;
					var that = this;
					if(mobile_phone != null && mobile_phone != "") {
						var texts = "已向" + mobile_phone + "发送短信验证码!";
						this.$message({
							message: texts,
							type: 'success'
						});
						$.ajax({
							url: "${base}/login/mobileCode.jhtml",
							type: "post",
							dataType: "json",
							data: {
								"phone":mobile_phone,
							},
							success: function(data) {
								if(data) {
									that.$message({
										message: '注意查收手机验证码！！',
										type: 'success',
										duration: 2000,
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
					} else {
						this.$message.error('请先输入手机号码!');
					}
				},
				submitForm(registerForm) {
					this.$refs[registerForm].validate((valid) => {
						if(valid) {
							var mobile_phone = this.registerForm.mobile_phone;
							var password = this.registerForm.password;
							var captcha = this.registerForm.verificationCode;
							var verification_code_phoneRegistry = this.registerForm.verification_code_phoneRegistry;
							var nickname = this.registerForm.nickname;
							var type = this.registerForm.radio;
							var seen = this.registerForm.seen;
							var that = this;
							if(!seen) {
								var mailbox = null;
							} else {
								var mailbox = this.registerForm.mailbox;
							}
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
										url: '${base}/login/registerSave.jhtml',
										data: {
											"mobilePhone": mobile_phone,
											"mailBox": mailbox,
											"nickName": nickname,
											"passWord": password,
											"type": type,
											"captcha": captcha,
											"phoneCode":verification_code_phoneRegistry,
											"captchaId": "${captchaId}",
										},
										dataType: 'json',
										success: function(date) {
											if(date == 2) {
												that.$message({
													message: '注册成功！',
													type: 'success',
													duration: 1000,
												});
												setTimeout(function() {
													window.location.href = "${base}/login/login.jhtml";
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
													message: '手机短信验证码错误！',
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
				resetForm(registerForm) {
					this.$refs[registerForm].resetFields();
				}
			}
		}
		var Ctor = Vue.extend(Main)
		new Ctor().$mount('#app')
	</script>

</html>