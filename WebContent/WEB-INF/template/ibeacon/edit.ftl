<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>修改顾客信息表</title>
		<!-- 引入样式 -->
		<!--<link rel="stylesheet" href="${base}/resources/common/css/Element-index.css">-->
		<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
	</head>

	<body>
		<div id="app" style="width: 50%;">
			<el-form :model="form" :rules="rules2" ref="form" label-width="100px" class="demo-ruleForm">
				<el-form-item label="UUID：" prop="UUID">
					<span v-if="form.seen">${ibeacon.UUID}</span>
					<el-input type="text" v-model="form.UUID" auto-complete="off" v-if="!form.seen"></el-input>
				</el-form-item>
				<el-form-item label="major：" prop="major">
					<span v-if="form.seen">${ibeacon.major}</span>
					<el-input type="text" v-model="form.major" auto-complete="off" v-if="!form.seen"></el-input>
				</el-form-item>
				<el-form-item label="minor：" prop="minor">
					<span v-if="form.seen">${ibeacon.minor}</span>
					<el-input type="text" v-model="form.minor" auto-complete="off" v-if="!form.seen"></el-input>
				</el-form-item>
				<el-form-item label="当前模式：" prop="mode">
					<el-tooltip :content="'Switch value: ' + value1" placement="top">
						<el-switch v-model="form.value1" active-color="#13ce66" inactive-color="#ff4949" active-value="开机" inactive-value="关机">
						</el-switch>
					</el-tooltip>
				</el-form-item>
				<el-form-item label="当前电量：" prop="electric">
					<el-rate v-model="form.value2" :texts="['弱', '一般', '中等', '强', '超强']" :colors="['#99A9BF', '#99A99F','#F7BA2A','#F7BA9A','#FF9900']" show-text>
					</el-rate>
				</el-form-item>
				<el-form-item label="备注：" prop="remark">
					<span v-if="form.seen">备注</span>
					<el-input type="text" v-model="form.remark" auto-complete="off" v-if="!form.seen"></el-input>
				</el-form-item>
				<el-form-item label-width="30px">
					<el-button type="primary" icon="el-icon-edit" circle v-if="form.seen" @click="show"></el-button>
					<el-button icon="el-icon-refresh" circle v-if="form.seen" @click="reload"></el-button>
					<el-button type="success" icon="el-icon-check" circle v-if="!form.seen" @click="submitForm('form')"></el-button>
					<el-button type="info" icon="el-icon-back" circle v-if="!form.seen" @click="reload"></el-button>
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
				var validateUUID = (rule, value, callback) => {
					var UUID = this.form.UUID;
					if(!value) {
						callback(new Error('请输入UUID！'));
						return false;
					} else {
						$.ajax({
							type: 'post',
							url: '${base}/ibeacon/check_UUID.jhtml',
							data: {
								"UUID": UUID,
							},
							dataType: "json",
							success: function(date) {
								if(date) {
									callback();
									return true;
								} else {
									callback(new Error('UUID已经被注册过！'));
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
					form: {
						seen: true,
						UUID: "${ibeacon.UUID}",
						major: "${ibeacon.major}",
						minor: "${ibeacon.minor}",
						value1: "${ibeacon.mode}",
						value2: "${ibeacon.electric}",
						remark: "${ibeacon.remark}",
					},
					rules2: {
						UUID: [{
							validator: validateUUID,
							trigger: 'blur'
						}],
					}
				};
			},
			methods: {
				show: function() {
					this.form.seen = !this.form.seen
				},
				reload: function() {
					window.location.reload();
				},
				submitForm(form) {
					this.$refs[form].validate((valid) => {
						if(valid) {
							var UUID = this.form.UUID;
							var major = this.form.major;
							var minor = this.form.minor;
							var mode = this.form.value1;
							var electric = this.form.value2;
							var remark = this.form.remark;
							var that = this;
							$.ajax({
								type: 'post',
								url: '${base}/ibeacon/ibeaconUpdate.jhtml',
								data: {
									"id": '${ibeacon.id}',
									"createDate": '${ibeacon.createDate}',
									"createName": '${ibeacon.createName}',
									"UUID": UUID,
									"major": major,
									"minor": minor,
									"mode": mode,
									"electric": electric,
									"remark": remark,
								},
								dataType: "json",
								success: function(date) {
									if(date) {
										that.$message({
											message: '更改成功！',
											type: 'success',
											duration: 1000,
										});
										setTimeout(function() {
											window.location.href = "${base}/ibeacon/index.jhtml";
										}, 1000);
									} else {
										that.$message({
											message: '修改失败！',
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