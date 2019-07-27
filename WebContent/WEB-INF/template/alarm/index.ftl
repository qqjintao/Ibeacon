<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>接收器表列表</title>
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
								<el-form-item label="设备名称：" prop="name">
									<el-input type="text" v-model="form.name" auto-complete="off"></el-input>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="设备位置：" prop="address">
									<el-input type="text" v-model="form.address" auto-complete="off"></el-input>
								</el-form-item>
							</td>
							<td>
								<center>
									<el-form-item label-width="30px">
										<el-button type="primary" @click="submitForm('form')" round>查询</el-button>
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
				<el-table-column prop="name" label="设备名称">
				</el-table-column>
				<el-table-column prop="address" label="设备位置">
				</el-table-column>
				<el-table-column prop="alarmTime" label="相隔警报时间" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="alarmVoice" label="警报声音" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="isSend" label="是否发送短信" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="phone" label="发送的手机号码" show-overflow-tooltip>
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
				<el-table-column label="操作" width="180">
					<template slot-scope="scope">
						<el-button size="mini" @click="handleEdit(scope.row.id)">设置警报处理</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div class="block">
				<el-pagination background @current-change="handleCurrentChange" :current-page="${page.getPageNumber()}" :page-size="${page.getPageSize()}" layout="total, prev, pager, next, jumper" :total="${page.getTotal()}">
				</el-pagination>
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
							name: "",
							address: "",
						},
						tableData: [
							[#list page.getContent() as acceptors] {
								id: '${acceptors.id}',
								createDate: '${acceptors.createDate}',
								createName: '${acceptors.createName}',
								modifyDate: '${acceptors.modifyDate}',
								modifyName: '${acceptors.modifyName}',
								name: '${acceptors.name}',
								address: '${acceptors.address}',
								alarmTime: '${acceptors.alarmTime}',
								alarmVoice: '${acceptors.alarmVoice}',
								isSend: '${acceptors.isSend}',
								phone: '${acceptors.phone}',
								remark: '${acceptors.remark}',
							},
							[/#list]],
								multipleSelection: [],
								rules2: {

								},
							};
						},
						methods: {
							handleSelectionChange(val) {
								this.multipleSelection = val;
							},
							handleEdit(row) {
								window.location.href = "${base}/acceptor/alarmEdit.jhtml?id=" + row;
							},
							handleCurrentChange(val) {
								var name = this.form.name;
								var address = this.form.address;
								window.location.href = "${base}/acceptor/alarm.jhtml?name=" + name + "&address=" + address + "&pageNumber=" + val;
							},
							submitForm(form) {
								this.$refs[form].validate((valid) => {
									if(valid) {
										var name = this.form.name;
										var address = this.form.address;
										var that = this;
										window.location.href = "${base}/acceptor/alarm.jhtml?name=" + name + "&address=" + address;
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