<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>新增接收器信息表</title>
		<!-- 引入样式 -->
		<!--<link rel="stylesheet" href="${base}/resources/common/css/Element-index.css">-->
		<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
	</head>

	<body>
		<div id="app" style="width: 50%;">
			<el-form :model="form" :rules="rules2" ref="form" label-width="120px">
				<el-form-item label="接收器名称：" prop="name">
					<el-input type="text" v-model="form.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="位置：" prop="address">
					<el-input type="text" v-model="form.address" auto-complete="off"></el-input>
				</el-form-item>
				<!--<el-form-item label="报警时间：" prop="alarmTime">
					<el-input type="text" v-model="form.alarmTime" auto-complete="off" ></el-input>
				</el-form-item>
				<el-form-item label="报警声音：" prop="alarmVoice">
					<el-input type="text" v-model="form.alarmVoice" auto-complete="off" ></el-input>
				</el-form-item>
				<el-form-item label="是否发送短信：" prop="isSend">
					<el-input type="text" v-model="form.isSend" auto-complete="off" ></el-input>
				</el-form-item>
				<el-form-item label="发送手机：" prop="phone">
					<el-input type="text" v-model="form.phone" auto-complete="off" ></el-input>
				</el-form-item>-->
				<el-form-item label="备注：" prop="remark">
					<el-input type="text" v-model="form.remark" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label-width="30px">
					<el-button type="success" icon="el-icon-check" circle @click="submitForm('form')"></el-button>
					<el-button icon="el-icon-refresh" circle @click="resetForm('form')"></el-button>
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
	<script type="text/javascript">
		var Main = {
			data() {
				var validateIdCardName = (rule, value, callback) => {
					if(!value) {
						callback(new Error('请输入名称！'));
						return false;
					} else {
						callback();
					}
				};
				var validateAddress = (rule, value, callback) => {
					if(!value) {
						callback(new Error('请输入地址！'));
						return false;
					} else {
						callback();
					}
				};
				return {
					form: {
						name: "",
						address: "",
						//						alarmTime: "",
						//						alarmVoice: "",
						//						isSend: "",
						//						phone: "",
						remark: "",
					},
					rules2: {
						name: [{
							validator: validateIdCardName,
							trigger: 'blur'
						}],
						address: [{
							validator: validateAddress,
							trigger: 'blur'
						}],
					}
				};
			},
			methods: {
				submitForm(form) {
					this.$refs[form].validate((valid) => {
						if(valid) {
							var name = this.form.name;
							var address = this.form.address;
							//							var alarmTime = this.form.alarmTime;
							//							var alarmVoice = this.form.alarmVoice;
							//							var isSend = this.form.isSend;
							//							var phone = this.form.phone;
							var remark = this.form.remark;
							var that = this;
							$.ajax({
								type: 'post',
								url: '${base}/acceptor/acceptorSave.jhtml',
								data: {
									"name": name,
									"address": address,
									//									"alarmTime": alarmTime,
									//									"alarmVoice": alarmVoice,
									//									"isSend": isSend,
									//									"phone": phone,
									"remark": remark,
								},
								dataType: "json",
								success: function(date) {
									if(date) {
										that.$message({
											message: '新增成功！',
											type: 'success',
											duration: 1000,
										});
										setTimeout(function() {
											window.location.href = "${base}/acceptor/index.jhtml";
										}, 1000);
									} else {
										that.$message({
											message: '新增失败！',
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
						} else {
							console.log('error submit!!');
							return false;
						}
					});
				},
				resetForm(form) {
					this.$refs[form].resetFields();
				}
			}
		}
		var Ctor = Vue.extend(Main)
		new Ctor().$mount('#app')
	</script>

</html>