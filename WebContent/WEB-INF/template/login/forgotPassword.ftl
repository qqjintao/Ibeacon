<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>登录界面</title>
		<!-- 引入样式 -->
		<!--<link rel="stylesheet" href="${base}/resources/common/css/Element-index.css">-->
		<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
		<link rel="stylesheet" href="${base}/resources/common/css/forgotPassword/forgotPasswordCss.css">
	</head>

	<body>
		<div id="app1" class="lg-container">
			<el-form :model="forgotForm" :rules="rules2" ref="forgotForm" label-width="100px">
				<el-form-item label="手机号码：" prop="mobile_phone">
					<el-input type="text" v-model="forgotForm.mobile_phone" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="验证码:" prop="verificationCode">
					<el-col :span="11">
						<el-input type="text" v-model="forgotForm.verificationCode"></el-input>
					</el-col>
					<el-col :span="10"> <span><img id="vcodeimg"
				src="${base}/common/captcha.jhtml?captchaId=${captchaId}"/></span> </el-col>
					<el-col class="line" :span="1">
						<el-button type="text" @click="changeVcode">换一张</el-button>
					</el-col>
				</el-form-item>
				<el-form-item label="短信验证码:" prop="verification_code_phoneRegistry">
					<el-col :span="11">
						<el-input type="text" v-model="forgotForm.verification_code_phoneRegistry"></el-input>
					</el-col>
					<el-col :span="10">
						<el-button type="info" plain @click="codePhone" v-bind:disabled="disabledInput">获取短信验证码</el-button>
					</el-col>
				</el-form-item>
				<el-form-item label="设置密码:" prop="password">
					<el-input type="password" v-model="forgotForm.password" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="确认密码:" prop="passwords">
					<el-input type="password" v-model="forgotForm.passwords" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="注册类型：" prop="radio">
					<template>
						<el-radio-group v-model="forgotForm.radio">
							<el-radio label="1">顾客</el-radio>
							<el-radio label="2">企业</el-radio>
							<el-radio label="3">管理员</el-radio>
						</el-radio-group>
					</template>
				</el-form-item>
				<el-form-item label-width="30px">
					<el-button type="primary" round @click="submitForm('forgotForm')" style="width: 100%;">修改密码</el-button>
				</el-form-item>
			</el-form>
		</div>
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
		var app1 = new Vue({
			el: '#app1',
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
					var type = this.forgotForm.radio;
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
								if(!date) {
									callback();
									return true;
								} else {
									callback(new Error('手机号码没注册过！'));
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
				var checkVerification_code_phoneRegistry = (rule, value, callback) => {
					if(!value) {
						callback(new Error('请输入手机验证码!'));
						return false;
					}  else {
						callback();
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
				var checkRadio = (rule, value, callback) => {
					if(!value) {
						callback(new Error('请选择登录类型！'));
						return false;
					} else {
						callback();
					}
				};
				var validatePasswords = (rule, value, callback) => {
					var value1 = this.forgotForm.password;
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
				return {
					forgotForm: {
						mobile_phone: '',
						verificationCode: '',
						password: '',
						passwords: '',
						verification_code_phoneRegistry: '',
						radio: '1',
					},
					disabledInput: false,
					rules2: {
						mobile_phone: [{
							validator: validateUsername,
							trigger: 'blur'
						}],
						verificationCode: [{
							validator: checkVerificationCode,
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
												verification_code_phoneRegistry: [{
													validator: checkVerification_code_phoneRegistry,
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
				submitForm(forgotForm) {
					this.$refs[forgotForm].validate((valid) => {
						if(valid) {
							var mobile_phone = this.forgotForm.mobile_phone;
							var captcha = this.forgotForm.verificationCode;
							var verification_code_phoneRegistry = this.forgotForm.verification_code_phoneRegistry;
							var passwords = this.forgotForm.passwords;
							var type = this.forgotForm.radio;
							var that = this;
							$.ajax({
								url: "${base}/common/public_key.jhtml",
								type: "GET",
								dataType: "json",
								cache: false,
								success: function(data) {
									var rsaKey = new RSAKey();
									rsaKey.setPublic(b64tohex(data.modulus), b64tohex(data.exponent));
									passwords = hex2b64(rsaKey.encrypt(passwords));
									$.ajax({
										type: 'post',
										url: '${base}/login/updatePassword.jhtml',
										data: {
											"mobilePhone": mobile_phone,
											"passWord": passwords,
											"type": type,
											"captcha": captcha,
											"captchaId": "${captchaId}",
											"phoneCode":verification_code_phoneRegistry,
										},
										dataType: 'json',
										success: function(date) {
											if(date == 2) {
												that.$message({
													message: '修改成功！',
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
													message: '修改密码！',
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
												$("#vcodeimg").attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
												that.$message({
													message: '手机短信验证码输入有误！',
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
				resetForm(forgotForm) {
					this.$refs[forgotForm].resetFields();
				}
			}
		})
	</script>

</html>