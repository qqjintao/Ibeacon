<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>新增操作文档信息表</title>
		<!-- 引入样式 -->
		<!--<link rel="stylesheet" href="${base}/resources/common/css/Element-index.css">-->
		<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
	</head>

	<body>
		<div id="app" style="width: 50%;">
			<el-form :model="form" :rules="rules2" ref="form" label-width="100px" class="demo-ruleForm">
				<el-form-item label="文档名称：" prop="name">
					<el-input type="text" v-model="form.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="文档上传：">
					<el-upload class="upload-demo" drag :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload" action="${base}/common/fileSave.jhtml">
						<i class="el-icon-upload"></i>
						<el-input v-model="input" v-if="false"></el-input>
						<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
						<div class="el-upload__tip" slot="tip">文件大小不超过1G</div>
					</el-upload>
				</el-form-item>
				<el-form-item label="文档地址：" prop="loadAddress">
					<el-input type="text" v-model="form.loadAddress" auto-complete="off" :disabled="true"></el-input>
				</el-form-item>
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
						callback(new Error('不允许为空！'));
						return false;
					} else {
						callback();
					}
				};
				return {
					form: {
						name: "",
						loadAddress: "",
						remark: "",
					},
					rules2: {
						name: [{
							validator: validateIdCardName,
							trigger: 'blur'
						}],
						loadAddress: [{
							validator: validateIdCardName,
							trigger: 'blur'
						}],
					}
				};
			},
			methods: {
				beforeAvatarUpload(file) {
					const isLt2M = file.size / 1024 / 1024 < 1024;
					if(!isLt2M) {
						this.$message.error('上传文件大小不能超过 1G!');
						return false;
					}
				},
				handleAvatarSuccess(res, file) {
					this.form.loadAddress = file.response;
				},
				submitForm(form) {
					this.$refs[form].validate((valid) => {
						if(valid) {
							var name = this.form.name;
							var loadAddress = this.form.loadAddress;
							var remark = this.form.remark;
							var that = this;
							$.ajax({
								type: 'post',
								url: '${base}/document/documentSave.jhtml',
								data: {
									"name": name,
									"loadAddress": loadAddress,
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
											window.location.href = "${base}/document/index.jhtml";
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