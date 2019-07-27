<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>iBeacon列表查询页面</title>
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
								<el-form-item label="UUID：" prop="UUID">
									<el-input type="text" v-model="form.UUID" auto-complete="off"></el-input>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="配置老人姓名：" prop="oldManName" label-width="120px">
									<el-input type="text" v-model="form.oldManName" auto-complete="off"></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<el-form-item>
									<el-button type="primary" @click="add" round>新增iBeacon</el-button>
									<el-button type="primary" @click="submitForm('form')" round>查询</el-button>
									[#if login.type!='1']
									<el-button type="primary" @click="exportDate" round>导出当前数据</el-button>
									<el-button type="primary" @click="exportAll" round>导出全部数据</el-button>
									<el-upload class="upload-demo" style="display:inline" action="${base}/import/uploadExecl.jhtml?downtype=ibeacon" :before-upload="beforeAvatarUpload" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" multiple :limit="3" :on-exceed="handleExceed">
										<el-button type="primary" round>点击导入数据</el-button>
										<span slot="tip" class="el-upload__tip">只能上传xls/xlsx文件，且不超过100MB</span>
									</el-upload>
									[/#if]
								</el-form-item>
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
				<el-table-column prop="UUID" label="UUID">
				</el-table-column>
				<el-table-column prop="major" label="Major">
				</el-table-column>
				<el-table-column prop="minor" label="Minor">
				</el-table-column>
				<el-table-column prop="electric" label="剩余电量">
				</el-table-column>
				<el-table-column prop="mode" label="是否开机状态">
				</el-table-column>
				<el-table-column prop="oldMan" label="配置老人姓名">
				</el-table-column>
				<el-table-column prop="modifyDate" label="修改时间" show-overflow-tooltip>
					<template slot-scope="scope">
						<el-popover trigger="hover" placement="bottom">
							<p><i class="el-icon-time"></i>创建时间: {{ scope.row.createDate }}</p>
							<div slot="reference" class="name-wrapper">
								<el-tag size="medium"><i class="el-icon-time"></i>{{ scope.row.modifyDate }}</el-tag>
							</div>
						</el-popover>
					</template>
				</el-table-column>
				<el-table-column prop="modifyName" label="修改人" show-overflow-tooltip>
					<template slot-scope="scope">
						<el-popover trigger="hover" placement="bottom">
							<p>创建人: {{ scope.row.createName }}</p>
							<div slot="reference" class="name-wrapper">
								<el-tag size="medium">{{ scope.row.modifyName }}</el-tag>
							</div>
						</el-popover>
					</template>
				</el-table-column>
				<el-table-column prop="remark" label="备注" show-overflow-tooltip>
				</el-table-column>
				<el-table-column label="操作" width="360px">
					<template slot-scope="scope">
						<el-button size="mini" @click="handleEdit(scope.row.id)">编辑</el-button>
						<el-button size="mini" @click="open(scope.row.id)">配置老人</el-button>
						<el-button size="mini" @click="editcofig(scope.row.id,scope.row.id1)">查询配置</el-button>
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
							UUID: "${UUID}",
							oldManName: "${oldManName}",
						},
						tableData: [
							[#list page.getContent() as iBeacons] {
								id: '${iBeacons.id}',
								UUID: '${iBeacons.UUID}',
								major: '${iBeacons.major}',
								minor: '${iBeacons.minor}',
								mode: '${iBeacons.mode}',
								electric: 
								[#if iBeacons.electric == '1']
								"弱",
								[#elseif iBeacons.electric == '2']
								"一般",
								[#elseif iBeacons.electric == '3']
								"中等",
								[#elseif iBeacons.electric == '4']
								"强",
								[#else]
								"超强",
								[/#if]
								id1: '${iBeacons.oldman}',
								oldMan: '${iBeacons.name}',
								createDate: '${iBeacons.createDate}',
								createName: '${iBeacons.createName}',
								modifyDate: '${iBeacons.modifyDate}',
								modifyName: '${iBeacons.modifyName}',
								remark: '${iBeacons.remark}',
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
							handleSelectionChange(val) {
								this.multipleSelection = val;
							},
							add() {
								window.location.href = "${base}/ibeacon/ibeaconAdd.jhtml";
							},
							handleEdit(row) {
								window.location.href = "${base}/ibeacon/ibeaconEdit.jhtml?id=" + row;
							},
							exportDate() {
								var UUID = this.form.UUID;
								var oldManName = this.form.oldManName;
								window.location.href = "${base}/ibeacon/export.jhtml?UUID=" + UUID + "&oldManName=" + oldManName + "&pageNumber=" + "${page.getPageNumber()}";
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
								window.location.href = "${base}/ibeacon/export.jhtml";
							},
							deleteSelection() {
								let ids = [];
								this.multipleSelection.map((item) => {
									ids.push(item.id)
								});
								window.location.href = "${base}/ibeacon/deleteSelection.jhtml?ids=" + ids;
							},
							handleCurrentChange(val) {
								var UUID = this.form.UUID;
								var oldManName = this.form.oldManName;
								window.location.href = "${base}/ibeacon/index.jhtml?UUID=" + UUID + "&oldManName=" + oldManName + "&pageNumber=" + val;
							},
							handleDelete(index, rows, id) {
								this.$confirm('确认关闭？')
									.then(_ => {
										rows.splice(index, 1);
										var that = this;
										$.ajax({
											type: 'post',
											url: '${base}/ibeacon/ibeaconDelete.jhtml',
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
							open(id) {
								window.location.href = "${base}/ibeacon/oldManList.jhtml?ibeaconId=" + id;
							},
							editcofig(id, oldman) {
								window.location.href = "${base}/ibeacon/oldManList.jhtml?ibeaconId=" + id + "&id=" + oldman;
							},
							submitForm(form) {
								this.$refs[form].validate((valid) => {
									if(valid) {
										var UUID = this.form.UUID;
										var oldManName = this.form.oldManName;
										var that = this;
										window.location.href = "${base}/ibeacon/index.jhtml?UUID=" + UUID + "&oldManName=" + oldManName;
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