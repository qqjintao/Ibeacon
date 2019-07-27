<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>登录界面</title>
		<!-- 引入样式 -->
		<!--<link rel="stylesheet" href="${base}/resources/common/css/Element-index.css">-->
		<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
	</head>

	<body>
		<center>
			<div id="app" style="width: 50%;">
				<el-form :model="loginForm" :rules="rules2" ref="loginForm" label-width="100px" class="demo-ruleForm">
					<el-form-item label="手机号码：" prop="mobilePhone">
						<span v-if="loginForm.seen">${login.mobilePhone}</span>
						<el-input type="text" v-model="loginForm.mobilePhone" auto-complete="off" v-if="!loginForm.seen"></el-input>
					</el-form-item>
					<el-form-item label="邮箱：" prop="mailBox">
						<span v-if="loginForm.seen">${login.mailBox}</span>
						<el-input type="text" v-model="loginForm.mailBox" auto-complete="off" v-if="!loginForm.seen"></el-input>
					</el-form-item>
					<el-form-item label="用户名：" prop="nickName">
						<span v-if="loginForm.seen">${login.nickName}</span>
						<el-input type="text" v-model="loginForm.nickName" auto-complete="off" v-if="!loginForm.seen"></el-input>
					</el-form-item>
					<el-form-item label="原密码：" prop="password">
						<span v-if="loginForm.seen">${login.passWord}</span>
						<el-input type="password" v-model="loginForm.password" auto-complete="off" v-if="!loginForm.seen"></el-input>
					</el-form-item>
					<el-form-item label="新密码：" prop="password1" v-if="!loginForm.seen">
						<el-input type="password" v-model="loginForm.password1" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item label="确认密码：" prop="password2" v-if="!loginForm.seen">
						<el-input type="password" v-model="loginForm.password2" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item label="登录类型：" prop="radio">
						<template>
							<el-radio-group v-model="loginForm.radio" disabled>
								<el-radio label="1">顾客</el-radio>
								<el-radio label="2">企业</el-radio>
								<el-radio label="3">管理员</el-radio>
							</el-radio-group>
						</template>
					</el-form-item>
					<el-form-item label-width="30px">
						<el-button type="primary" icon="el-icon-edit" circle v-if="loginForm.seen" @click="show"></el-button>
						<el-button icon="el-icon-refresh" circle v-if="loginForm.seen" @click="reload"></el-button>
						<el-button type="success" icon="el-icon-check" circle v-if="!loginForm.seen" @click="submitForm('loginForm')"></el-button>
						<el-button type="info" icon="el-icon-back" circle v-if="!loginForm.seen" @click="reload"></el-button>
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
				var validateMobile_phone = (rule, value, callback) => {
					var type = this.loginForm.radio;
					if(!value) {
						callback(new Error('请输入手机号码!'));
						return false;
					} else if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(value))) {
						callback(new Error('输入手机号码有误!'));
						return false;
					} else if(value == '${login.mobilePhone}') {
						callback();
						return true;
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
				var checkMailbox = (rule, value, callback) => {
					var type = this.loginForm.radio;
					var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
					if(!value) {
						callback();
						return true;
					} else if(!reg.test(value)) {
						callback(new Error('输入电子邮箱地址有误!'));
						return false;
					} else if(value == '${login.mailBox}') {
						callback();
						return true;
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
					var type = this.loginForm.radio;
					if(!value) {
						callback(new Error('请输入您的用户名！'));
						return false;
					} else if(value == '${login.nickName}') {
						callback();
						return true;
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
				var passwordChecked = (rule, value, callback) => {
					var regex = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}');
					if(!value) {
						callback(new Error('请输入原密码！'));
						return false;
					} else if(!regex.test(value)) {
						callback(new Error('密码中必须包含字母、数字、特称字符，至少8个字符，最多30个字符!'));
						return false;
					} else {
						callback();
					}
				};
				var password1Checked = (rule, value, callback) => {
					var regex = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}');
					if(!value) {
						callback(new Error('请输入新密码!'));
						return false;
					} else if(!regex.test(value)) {
						callback(new Error('密码中必须包含字母、数字、特称字符，至少8个字符，最多30个字符!'));
						return false;
					} else {
						callback();
					}
				};
				var password2Checked = (rule, value, callback) => {
					var value1 = this.loginForm.password1;
					if(!value) {
						callback(new Error('请输入确认密码!'));
						return false;
					} else if(value1 != value) {
						callback(new Error('您输入的两次密码不一致!'));
						return false;
					} else {
						callback();
					}
				};
				return {
					loginForm: {
						radio: '${login.type}',
						seen: true,
						mobilePhone: '${login.mobilePhone}',
						mailBox: '${login.mailBox}',
						nickName: '${login.nickName}',
					},
					rules2: {
						mobilePhone: [{
							validator: validateMobile_phone,
							trigger: 'blur'
						}],
						mailBox: [{
							validator: checkMailbox,
							trigger: 'blur'
						}],
						nickName: [{
							validator: nicknameChecked,
							trigger: 'blur'
						}],
						//						password: [{
						//							validator: passwordChecked,
						//							trigger: 'blur'
						//						}],
						//						password1: [{
						//							validator: password1Checked,
						//							trigger: 'blur'
						//						}],
						//						password2: [{
						//							validator: password2Checked,
						//							trigger: 'blur'
						//						}],
					}
				};
			},
			methods: {
				show: function() {
					this.loginForm.seen = !this.loginForm.seen
				},
				reload: function() {
					window.location.reload();
				},
				submitForm(loginForm) {
					this.$refs[loginForm].validate((valid) => {
						if(valid) {
							var mobilePhone = this.loginForm.mobilePhone;
							var mailBox = this.loginForm.mailBox;
							var nickName = this.loginForm.nickName;
							var oldPassword = this.loginForm.password;
							var newPassword = this.loginForm.password2;
							var that = this;
							$.ajax({
								url: "${base}/common/public_key.jhtml",
								type: "GET",
								dataType: "json",
								cache: false,
								success: function(data) {
									var rsaKey = new RSAKey();
									rsaKey.setPublic(b64tohex(data.modulus), b64tohex(data.exponent));
									oldPassword = hex2b64(rsaKey.encrypt(oldPassword));
									newPassword = hex2b64(rsaKey.encrypt(newPassword));
									$.ajax({
										type: 'post',
										url: '${base}/login/updateLogin.jhtml',
										data: {
											"mobilePhone": mobilePhone,
											"mailBox": mailBox,
											"nickName": nickName,
											"oldPassword": oldPassword,
											"newPassword": newPassword,
										},
										dataType: "json",
										success: function(date) {
											if(date == 0) {
												that.$message({
													message: '修改成功！',
													type: 'success',
													duration: 3000,
												});
												setTimeout(function() {
													window.location.reload();
												}, 1000);
											} else if(date == 1) {
												that.$message({
													message: '未登录！',
													type: 'warning',
													duration: 3000,
												});
											} else if(date == 2) {
												that.$message({
													message: '旧密码输入有误！',
													type: 'warning',
													duration: 3000,
												});
											} else {
												that.$message({
													message: '修改失败（）！',
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