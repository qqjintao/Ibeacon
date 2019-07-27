<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>编辑登录信息</title>
		<!-- 引入样式 -->
		<!--<link rel="stylesheet" href="${base}/resources/common/css/Element-index.css">-->
		<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
	</head>

	<body>
		<center>
			<div id="app" style="width: 50%;">
				<el-form :model="loginForm" :rules="rules2" ref="loginForm" label-width="100px" class="demo-ruleForm">
					<el-form-item label="手机号码：" prop="mobilePhone">
						<span v-if="loginForm.seen">${logins.mobilePhone}</span>
						<el-input type="text" v-model="loginForm.mobilePhone" auto-complete="off" v-if="!loginForm.seen"></el-input>
					</el-form-item>
					<el-form-item label="邮箱：" prop="mailBox">
						<span v-if="loginForm.seen">${logins.mailBox}</span>
						<el-input type="text" v-model="loginForm.mailBox" auto-complete="off" v-if="!loginForm.seen"></el-input>
					</el-form-item>
					<el-form-item label="用户名：" prop="nickName">
						<span v-if="loginForm.seen">${logins.nickName}</span>
						<el-input type="text" v-model="loginForm.nickName" auto-complete="off" v-if="!loginForm.seen"></el-input>
					</el-form-item>
					<el-form-item label="是否锁定：" prop="isLocked">
						<span v-if="loginForm.seen">
							[#if logins.isLocked=='Y']
								锁定
							[#else]
								未锁定
							[/#if]
						</span>				
						<el-select v-model="loginForm.isLocked" placeholder="请选择" v-if="!loginForm.seen"> 
					    <el-option
					      v-for="item in loginForm.options"
					      :key="item.value"
					      :label="item.label"
					      :value="item.value">
					    </el-option>
					</el-select>	
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
					} else if(value == '${logins.mobilePhone}') {
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
					} else if(value == '${logins.mailBox}') {
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
					} else if(value == '${logins.nickName}') {
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
				var isLockedChecked = (rule, value, callback) => {
					if(!value) {
						callback(new Error('请选择是否锁定！'));
						return false;
					} else {
						callback();
					}
				};
				return {
					loginForm: {
						radio: '${logins.type}',
						seen: true,
						mobilePhone: '${logins.mobilePhone}',
						mailBox: '${logins.mailBox}',
						nickName: '${logins.nickName}',
						isLocked:'${logins.isLocked}',
						options: [{
					          value: 'Y',
					          label: '锁定'
					        },
					        {
					          value: 'N',
					          label: '未锁定'
					        },],				        
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
												isLocked: [{
													validator: isLockedChecked,
													trigger: 'blur'
												}],
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
							var isLocked = this.loginForm.isLocked;
							var that = this;
							window.location.href="${base}/login/listUpdata.jhtml?mobilePhone=" + mobilePhone +
								"&mailBox=" + mailBox + "&nickName=" + nickName + "&isLocked=" + isLocked+"&id=" + "${logins.id}";		
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