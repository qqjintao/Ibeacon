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
								<el-form-item label="姓名：" prop="idCardName">
									<el-input type="text" v-model="form.idCardName" auto-complete="off"></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<center>
									<el-form-item label-width="30px">
										<el-button type="primary" @click="add" round>新增顾客个人信息表</el-button>
										<el-button type="primary" @click="submitForm('form')" round>查询</el-button>
										[#if login.type!='1']
										<el-button type="primary" @click="exportDate" round>导出当前数据</el-button>
										<el-button type="primary" @click="exportAll" round>导出全部数据</el-button>
										<el-upload class="upload-demo" style="display:inline" action="${base}/import/uploadExecl.jhtml?downtype=info" :before-upload="beforeAvatarUpload" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" multiple :limit="3" :on-exceed="handleExceed">
											<el-button type="primary" round>点击导入数据</el-button>
											<span slot="tip" class="el-upload__tip">只能上传xls/xlsx文件，且不超过100MB</span>
										</el-upload>
										[/#if]
									</el-form-item>
								</center>
							</td>
						</tr>
					</tbody>
				</table>
			</el-form>
			<!--list表-->
			<hr>
			<el-table stripe ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55">
				</el-table-column>
				<el-table-column prop="name" label="姓名" width="120">
					<template slot-scope="scope">
						<el-popover trigger="hover" placement="top">
							<p>英文名: {{ scope.row.englishName }}</p>
							<div slot="reference" class="name-wrapper">
								<el-tag size="medium">{{ scope.row.idCardName }}</el-tag>
							</div>
						</el-popover>
					</template>
				</el-table-column>
				<el-table-column prop="age" label="年龄">
				</el-table-column>
				<el-table-column prop="sex" label="性别">
				</el-table-column>
				<el-table-column prop="birthplace" label="出生地" show-overflow-tooltip>
				</el-table-column>
				<el-table-column label="出生日期" width="120" show-overflow-tooltip>
					<template slot-scope="scope">
						<i class="el-icon-time"></i>
						<span style="margin-left: 10px">{{ scope.row.dateOfBirth }}</span>
					</template>
				</el-table-column>
				<el-table-column prop="idCard" label="身份证" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="nation" label="民族" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="politicsStatus" label="政治面貌" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="maritalStatus" label="婚姻状况" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="streetAddress" label="现居住地街道地址" show-overflow-tooltip>
					<template slot-scope="scope">
						<el-popover trigger="hover" placement="bottom">
							<p>现居住地省: {{ scope.row.province }}</p>
							<p>现居住地市: {{ scope.row.city }}</p>
							<p>现居住地区: {{ scope.row.district }}</p>
							<div slot="reference" class="name-wrapper">
								<el-tag size="medium">{{ scope.row.streetAddress }}</el-tag>
							</div>
						</el-popover>
					</template>
				</el-table-column>
				<el-table-column prop="domicile" label="户口居住街道地址" show-overflow-tooltip>
					<template slot-scope="scope">
						<el-popover trigger="hover" placement="bottom">
							<p>户口所在省: {{ scope.row.domicileProvince }}</p>
							<p>户口所在市: {{ scope.row.domicileCity }}</p>
							<p>户口所在区: {{ scope.row.domicileDistrict }}</p>
							<div slot="reference" class="name-wrapper">
								<el-tag size="medium">{{ scope.row.domicile }}</el-tag>
							</div>
						</el-popover>
					</template>
				</el-table-column>
				<el-table-column prop="mailbox" label="邮箱" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="phone" label="手机" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="homePhone" label="住宅电话" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="officePhone" label="办公电话" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="remark" label="备注" show-overflow-tooltip>
				</el-table-column>
				<el-table-column label="操作" width="180">
					<template slot-scope="scope">
						<el-button size="mini" @click="handleEdit(scope.row.id)">编辑</el-button>
						<el-button size="mini" type="danger" @click.native.prevent="handleDelete(scope.$index,tableData,scope.row.id)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div class="block">
				<el-pagination background @current-change="handleCurrentChange" :current-page="${page.getPageNumber()}" :page-size="${page.getPageSize()}" layout="total, prev, pager, next, jumper" :total="${page.getTotal()}">
				</el-pagination>
			</div>
			<div style="margin-top: 20px">
				<el-button @click="deleteSelection()">批量删除</el-button>
				<el-button @click="toggleSelection()">取消选择</el-button>
			</div>
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
						form: {
							idCardName: "${idCardName}",
						},
						tableData: [
							[#list page.getContent() as infos] {
								id: "${infos.id}",
								idCardName: '${infos.idCardName}',
								englishName: '${infos.englishName}',
								age: '${infos.age}',
								sex: '${infos.sex}',
								birthplace: '${infos.birthplace}',
								dateOfBirth: '${infos.dateOfBirth}',
								nationality: '${infos.nationality}',
								idCard: '${infos.idCard}',
								nation: '${infos.nation}',
								politicsStatus: '${infos.politicsStatus}',
								maritalStatus: '${infos.maritalStatus}',
								province: '${infos.province}',
								city: '${infos.city}',
								district: '${infos.district}',
								streetAddress: '${infos.streetAddress}',
								domicileProvince: '${infos.domicileProvince}',
								domicileCity: '${infos.domicileCity}',
								domicileDistrict: '${infos.domicileDistrict}',
								domicile: '${infos.domicile}',
								mailbox: '${infos.mailbox}',
								phone: '${infos.phone}',
								homePhone: '${infos.homePhone}',
								officePhone: '${infos.officePhone}',
								remark: '${infos.remark}',
							},
							[/#list]],
								multipleSelection: [],
								rules2: {

								},
							};
						},
						methods: {
							toggleSelection(rows) {
								if(rows) {
									rows.forEach(row => {
										this.$refs.multipleTable.toggleRowSelection(row);
									});
								} else {
									this.$refs.multipleTable.clearSelection();
								}
							},
							handleCurrentChange(val) {
								var idCardName = this.form.idCardName;
								window.location.href = "${base}/info/index.jhtml?idCardName=" + idCardName + "&pageNumber=" + val;
							},
							deleteSelection() {
								let ids = [];
								this.multipleSelection.map((item) => {
									ids.push(item.id)
								});
								window.location.href = "${base}/info/deleteSelection.jhtml?ids=" + ids;
							},
							handleSelectionChange(val) {
								this.multipleSelection = val;
							},
							add() {
								window.location.href = "${base}/info/infoAdd.jhtml";
							},
							handleEdit(row) {
								window.location.href = "${base}/info/infoEdit.jhtml?id=" + row;
							},
							exportDate() {
								var idCardName = this.form.idCardName;
								window.location.href = "${base}/info/export.jhtml?idCardName=" + idCardName + "&pageNumber=" + "${page.getPageNumber()}";
							},
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
								window.location.href = "${base}/info/export.jhtml";
							},
							handleDelete(index, rows, id) {
								this.$confirm('确认关闭？')
									.then(_ => {
										rows.splice(index, 1);
										var that = this;
										$.ajax({
											type: 'post',
											url: '${base}/info/infoDelete.jhtml',
											data: {
												"id": id,
											},
											dataType: "json",
											success: function(date) {
												if(date) {
													that.$message({
														message: '删除成功！',
														type: 'success',
														duration: 1000,
													});
												} else {
													that.$message({
														message: '删除失败！',
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
									})
									.catch(_ => {});
							},
							submitForm(form) {
								this.$refs[form].validate((valid) => {
									if(valid) {
										var idCardName = this.form.idCardName;
										var that = this;
										window.location.href = "${base}/info/index.jhtml?idCardName=" + idCardName;
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