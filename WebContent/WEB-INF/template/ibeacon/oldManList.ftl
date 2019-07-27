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
								<el-form-item label="姓名：" prop="name">
									<el-input type="text" v-model="form.name" auto-complete="off"></el-input>
								</el-form-item>
							</td>
							<td>
								<el-form-item label-width="30px">
									<el-button type="primary" @click="submitForm('form')" round>查询</el-button>
								</el-form-item>
							</td>
						</tr>
					</tbody>
				</table>
			</el-form>
			<!--list表-->
			<hr>
			<el-table stripe ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%">
				<el-table-column prop="name" label="姓名" width="120">
					<template slot-scope="scope">
						<el-popover trigger="hover" placement="top">
							<p>英文名: {{ scope.row.englishName }}</p>
							<div slot="reference" class="name-wrapper">
								<el-tag size="medium">{{ scope.row.name }}</el-tag>
							</div>
						</el-popover>
					</template>
				</el-table-column>
				<el-table-column prop="age" label="年龄">
				</el-table-column>
				<el-table-column prop="sex" label="性别">
				</el-table-column>
				<el-table-column prop="nationality" label="国籍" show-overflow-tooltip>
					<template slot-scope="scope">
						<el-popover trigger="hover" placement="top">
							<p>民族: {{ scope.row.nation }}</p>
							<p>政治面貌: {{ scope.row.politicsStatus}}</p>
							<div slot="reference" class="name-wrapper">
								<el-tag size="medium">{{ scope.row.nationality }}</el-tag>
							</div>
						</el-popover>
					</template>
				</el-table-column>
				<el-table-column prop="identityCard" label="身份证" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="physical" label="身体状况" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="phone" label="联系电话" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="relation" label="关系" show-overflow-tooltip>
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
				<el-table-column prop="account" label="医保账户" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="doctor" label="负责医生" show-overflow-tooltip>
					<template slot-scope="scope">
						<el-popover trigger="hover" placement="top">
							<p>医生电话: {{scope.row.doctorPhone}}</p>
							<div slot="reference" class="name-wrapper">
								<el-tag size="medium">{{scope.row.doctor}}</el-tag>
							</div>
						</el-popover>
					</template>
				</el-table-column>
				<el-table-column prop="nurse" label="负责护士" show-overflow-tooltip>
					<template slot-scope="scope">
						<el-popover trigger="hover" placement="top">
							<p>护士电话: {{scope.row.nursePhone}}</p>
							<div slot="reference" class="name-wrapper">
								<el-tag size="medium">{{scope.row.nurse}}</el-tag>
							</div>
						</el-popover>
					</template>
				</el-table-column>
				<el-table-column prop="emergencyName" label="紧急联系人" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="emergencyCall" label="紧急联系电话" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="remark" label="备注" show-overflow-tooltip>
				</el-table-column>
				<el-table-column label="操作" width="180">
					<template slot-scope="scope">
						[#if seen]
						<el-button size="mini" @click="handleEdit(scope.row.id)">配置</el-button>
						[#else]
						<el-button size="mini" @click="deletecofig()">取消配置</el-button>
						[/#if]
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
							name: "${name}",
						},
						tableData: [
							[#list page.getContent() as oldMans] {
								id: "${oldMans.id}",
								name: '${oldMans.name}',
								englishName: '${oldMans.englishName}',
								age: '${oldMans.age}',
								sex: '${oldMans.sex}',
								physical: '${oldMans.physical}',
								phone: '${oldMans.phone}',
								nationality: '${oldMans.nationality}',
								identityCard: '${oldMans.identityCard}',
								nation: '${oldMans.nation}',
								politicsStatus: '${oldMans.politicsStatus}',
								relation: '${oldMans.relation}',
								account: '${oldMans.account}',
								province: '${oldMans.province}',
								city: '${oldMans.city}',
								district: '${oldMans.district}',
								streetAddress: '${oldMans.streetAddress}',
								domicileProvince: '${oldMans.domicileProvince}',
								domicileCity: '${oldMans.domicileCity}',
								domicileDistrict: '${oldMans.domicileDistrict}',
								domicile: '${oldMans.domicile}',
								doctor: '${oldMans.doctor}',
								doctorPhone: '${oldMans.doctorPhone}',
								nurse: '${oldMans.nurse}',
								nursePhone: '${oldMans.nursePhone}',
								emergencyName: '${oldMans.emergencyName}',
								emergencyCall: '${oldMans.emergencyCall}',
								remark: '${oldMans.remark}',
							},
							[/#list]],
								multipleSelection: [],
								rules2: {

								},
							};
						},
						methods: {
							handleEdit(row) {
								var that = this;
								$.ajax({
									type: 'post',
									url: '${base}/ibeacon/config.jhtml',
									data: {
										"id": row,
										"ibeaconId": "${ibeaconId}",
									},
									dataType: "json",
									success: function(date) {
										if(date) {
											that.$message({
												message: '配置成功！',
												type: 'success',
												duration: 1000,
											});
											setTimeout(function() {
												window.location.href = "${base}/ibeacon/index.jhtml";
											}, 1000);
										} else {
											that.$message({
												message: '配置失败！',
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
							},
							handleCurrentChange(val) {
								var name = this.form.name;
								window.location.href = "${base}/ibeacon/oldManList.jhtml?name=" + name + "&pageNumber=" + val + "&ibeaconId=" + "${ibeaconId}";
							},
							deletecofig() {
								window.location.href = "${base}/ibeacon/deletecofig.jhtml?ibeaconId=" + "${ibeaconId}";
							},
							submitForm(form) {
								this.$refs[form].validate((valid) => {
									if(valid) {
										var name = this.form.name;
										var that = this;
										window.location.href = "${base}/ibeacon/oldManList.jhtml?name=" + name + "&ibeaconId=" + "${ibeaconId}";
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