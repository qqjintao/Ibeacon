<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>登录信息列表</title>
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
								<el-form-item label="手机号码：" prop="mobilePhone">
									<el-input type="text" v-model="form.mobilePhone" auto-complete="off"></el-input>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="邮箱：" prop="mailBox" label-width="120px">
									<el-input type="text" v-model="form.mailBox" auto-complete="off"></el-input>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="昵称：" prop="nickName" label-width="120px">
									<el-input type="text" v-model="form.nickName" auto-complete="off"></el-input>
								</el-form-item>
							</td>
							
						</tr>
						<tr>
							<td>
								<el-form-item label="登录类型：" prop="radio">
									<template>
										<el-radio-group v-model="form.radio">
											<el-radio label="1">顾客</el-radio>
											<el-radio label="2">企业</el-radio>
											<el-radio label="3">管理员</el-radio>
										</el-radio-group>
									</template>
								</el-form-item>
							</td>
							<td colspan="3">
								<el-form-item>
									<el-button type="primary" @click="add" round>注册用户</el-button>
									<el-button type="primary" @click="submitForm('form')" round>查询</el-button>
									<el-button type="primary" @click="exportDate" round>导出当前数据</el-button>
									<el-button type="primary" @click="exportAll" round>导出全部数据</el-button>
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
				<el-table-column prop="mobilePhone" label="手机号码">
				</el-table-column>
				<el-table-column prop="mailBox" label="邮箱">
				</el-table-column>
				<el-table-column prop="nickName" label="昵称">
				</el-table-column>
				<el-table-column prop="isLocked" label="是否锁定">
				</el-table-column>
				<el-table-column prop="type" label="类型">
				</el-table-column>
				<el-table-column label="操作" width="360px">
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
							mobilePhone: "${logins.mobilePhone}",
							mailBox: "${logins.mailBox}",
							nickName: "${logins.nickName}",
							radio: 
							[#if logins.type!=null]
							"${logins.type}"
							[#else]
							'1'
							[/#if]
							,
						},
						tableData: [
							[#list page.getContent() as loginList] {
								id: '${loginList.id}',
								mobilePhone: '${loginList.mobilePhone}',
								mailBox: '${loginList.mailBox}',
								nickName: '${loginList.nickName}',
								isLocked: 
								[#if loginList.isLocked=='Y']
								'锁定'
								[#else]
								'未锁定'
								[/#if]
								,
								type: 
								[#if loginList.type=='1']
								'顾客'
								[#elseif loginList.type=='2']
								'企业'
								[#else]
								'管理员'
								[/#if]
								,
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
								window.location.href = "${base}/login/register.jhtml";
							},
							handleEdit(row) {
								window.location.href = "${base}/login/loginEdit.jhtml?id=" + row;
							},
							exportDate() {
								var mobilePhone = this.form.mobilePhone;
								var mailBox = this.form.mailBox;
								var nickName = this.form.nickName;
								var type = this.form.radio;;
								window.location.href = "${base}/login/export.jhtml?mobilePhone=" + mobilePhone +
								"&mailBox=" + mailBox + "&nickName=" + nickName + "&type=" + type
								+ "&pageNumber=" + "${page.getPageNumber()}";
							},
							exportAll() {
								window.location.href = "${base}/login/export.jhtml";
							},
							deleteSelection() {
								let ids = [];
								this.multipleSelection.map((item) => {
									ids.push(item.id)
								});
								window.location.href = "${base}/login/deleteSelection.jhtml?ids=" + ids;
							},
							handleCurrentChange(val) {
								var mobilePhone = this.form.mobilePhone;
								var mailBox = this.form.mailBox;
								var nickName = this.form.nickName;
								var type = this.form.radio;
								window.location.href = "${base}/login/list.jhtml?mobilePhone=" + mobilePhone +
								"&mailBox=" + mailBox + "&nickName=" + nickName + "&type=" + type
								+ "&pageNumber=" + val;
							},
							handleDelete(index, rows, id) {
								this.$confirm('确认关闭？')
									.then(_ => {
										rows.splice(index, 1);
										var that = this;
										$.ajax({
											type: 'post',
											url: '${base}/login/loginDelete.jhtml',
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
										var mobilePhone = this.form.mobilePhone;
										var mailBox = this.form.mailBox;
										var nickName = this.form.nickName;
										var type = this.form.radio;
										var that = this;
										window.location.href = "${base}/login/list.jhtml?mobilePhone=" + mobilePhone +
								"&mailBox=" + mailBox + "&nickName=" + nickName + "&type=" + type;
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