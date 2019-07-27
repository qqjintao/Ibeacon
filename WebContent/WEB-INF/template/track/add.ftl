<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>模拟增加iBeacon数据</title>
		<!-- 引入样式 -->
		<!--<link rel="stylesheet" href="${base}/resources/common/css/Element-index.css">-->
		<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
	</head>

	<body>
		<div id="app" style="width: 50%;">
			<h1>模拟增加iBeacon数据</h1>
			<el-form :model="form" :rules="rules2" ref="form" label-width="100px" class="demo-ruleForm">
				<el-form-item label="UUID：" prop="UUID">
					<el-input type="text" v-model="form.UUID" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="major：" prop="major">
					<el-input type="text" v-model="form.major" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="minor：" prop="minor">
					<el-input type="text" v-model="form.minor" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="接收器设备名称：" prop="name">
					<el-input type="text" v-model="form.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="逗留时间：" prop="time">
					<el-input type="text" v-model="form.time" auto-complete="off"></el-input>
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
						callback(new Error('请输入姓名！'));
						return false;
					} else {
						callback();
					}
				};
				return {
					form: {

					},
					rules2: {
						//						idCardName: [{
						//							validator: validateIdCardName,
						//							trigger: 'blur'
						//						}],
					}
				};
			},
			methods: {
				submitForm(form) {
					this.$refs[form].validate((valid) => {
						if(valid) {
							var UUID = this.form.UUID;
							var major = this.form.major;
							var minor = this.form.minor;
							var name = this.form.name;
							var time = this.form.time;
							var that = this;
							$.ajax({
								type: 'post',
								url: '${base}/iBeaconInput/iBeaconInputSave.jhtml',
								data: {
									"UUID": UUID,
									"major": major,
									"minor": minor,
									"name": name,
									"time": time,
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
											window.location.href = "${base}/iBeaconInput/index.jhtml";
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