<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>顾客信息表列表</title>
		<!-- 引入样式 -->
		<!--<link rel="stylesheet" href="${base}/resources/common/css/Element-index.css">-->
		<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
	</head>

	<body>
		<div id="app">
			<el-form :model="form" :rules="rules2" ref="form" label-width="100px" class="demo-ruleForm">
				<table>
					<tbody>
						<tr>
							<td>
								<center>
									<el-form-item label-width="30px">
										<el-button type="primary" @click="exportAll" round>导出全部数据</el-button>
										<el-upload class="upload-demo" style="display:inline" action="${base}/import/uploadExecl.jhtml?downtype=dataDictionary" :before-upload="beforeAvatarUpload" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" multiple :limit="3" :on-exceed="handleExceed">
											<el-button type="primary" round>点击导入数据</el-button>
											<span slot="tip" class="el-upload__tip">只能上传xls/xlsx文件，且不超过100MB</span>
										</el-upload>
									</el-form-item>
								</center>
							</td>
						</tr>
					</tbody>
				</table>
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
				return {

				};
			},
			methods: {
				handleExceed(files, fileList) {
					this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
				},
				beforeAvatarUpload(file) {
					let Xls = file.name.split('.');
					const isLt2M = file.size / 1024 / 1024 < 100;
					if(!isLt2M) {
						this.$message.error('上传头像图片大小不能超过 100MB!');
						return false;
					}
					if(Xls[1] === 'xls' || Xls[1] === 'xlsx') {
						return true;
					} else {
						this.$message.error('上传文件只能是 xls/xlsx 格式!')
						return false
					}
				},
				exportAll() {
					window.location.href = "${base}/dataDictionary/export.jhtml";
				},
			}
		}
		var Ctor = Vue.extend(Main)
		new Ctor().$mount('#app')
	</script>

</html>