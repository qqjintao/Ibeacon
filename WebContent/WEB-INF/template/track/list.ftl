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
			<audio id="brtAudio" controls="controls" style=" position: fixed; top: -9999px;" src="https://web.sdk.map.brtbeacon.com/api/voice/txt_to_voice?text=%E6%99%BA%E7%9F%B3%E5%9C%B0%E5%9B%BE%E6%A8%A1%E6%8B%9F%E5%AF%BC%E8%88%AA%E5%BC%80%E5%A7%8B%EF%BC%8C%E5%85%A8%E7%A8%8B181%E7%B1%B3%EF%BC%8C%E4%BB%8EF1%E5%B1%82%E9%A6%99%E5%A5%88%E5%84%BF%E5%88%B0F1%E5%B1%82%E6%97%A0%E5%90%8D%E5%9C%B0%E5%9D%80%EF%BC%8C%E5%A4%A7%E7%BA%A6%E9%9C%80%E8%A6%813%E5%88%86%E9%92%9F%E3%80%82%E8%AF%B7%E5%8B%BF%E5%AE%8C%E5%85%A8%E4%BE%9D%E8%B5%96%E5%AF%BC%E8%88%AA%EF%BC%8C%E6%B3%A8%E6%84%8F%E6%AD%A5%E8%A1%8C%E5%AE%89%E5%85%A8&amp;types=0"></audio>
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
								<el-form-item label="major：" prop="major">
									<el-input type="text" v-model="form.major" auto-complete="off"></el-input>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="minor：" prop="minor">
									<el-input type="text" v-model="form.minor" auto-complete="off"></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="配置老人姓名：" prop="oldManName" label-width="120px">
									<el-input type="text" v-model="form.oldManName" auto-complete="off"></el-input>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="接收器名称：" prop="acceptorName" label-width="120px">
									<el-input type="text" v-model="form.acceptorName" auto-complete="off"></el-input>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="接收器地址：" prop="address" label-width="120px">
									<el-input type="text" v-model="form.address" auto-complete="off"></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<el-form-item>
									<el-button type="primary" @click="add" round>新增iBeacon模拟数据</el-button>
									<el-button type="primary" @click="submitForm('form')" round>查询</el-button>
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
				<el-table-column prop="time" label="感应时间">
				</el-table-column>
				<el-table-column prop="oldMan" label="配置老人姓名">
				</el-table-column>
				<el-table-column prop="name" label="接收器名称">
				</el-table-column>
				<el-table-column prop="address" label="接收器地址">
				</el-table-column>
				<el-table-column prop="alarm_time" label="警报时间">
				</el-table-column>
				<el-table-column prop="alarm_voice" label="警报声音">
				</el-table-column>
				<el-table-column prop="phone" label="发送手机号码">
				</el-table-column>
				<el-table-column label="操作" width="360px">
					<template slot-scope="scope">
						<el-button size="mini" @click="open(scope.row.time,scope.row.alarm_time,scope.row.alarm_voice)">警报</el-button>
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
							UUID: "${iBeaconInput.UUID}",
							major:"${iBeaconInput.major}",
							minor:"${iBeaconInput.minor}",
							oldManName: "${oldManName}",
							acceptorName: "${acceptorName}",
							address: "${address}",
						},
						tableData: [
							[#list page.getContent() as Vo] {
								id: '${Vo.id}',
								UUID: '${Vo.UUID}',
								major: '${Vo.major}',
								minor: '${Vo.minor}',
								time: '${Vo.time}',
								oldMan: '${Vo.oldMan}',
								name: '${Vo.name}',
								address: '${Vo.address}',
								alarm_time: '${Vo.alarmTime}',
								alarm_voice: '${Vo.alarmVoice}',
								phone: '${Vo.phone}',
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
							add() {
								window.location.href = "${base}/iBeaconInput/iBeaconInputAdd.jhtml";
							},
							handleCurrentChange(val) {
								var UUID = this.form.UUID;
								var oldManName = this.form.oldManName;
								var major = this.form.major;
							    var minor = this.form.minor;
							    var acceptorName = this.form.acceptorName;
							    var address = this.form.address;
								window.location.href = "${base}/iBeaconInput/index.jhtml?UUID=" + UUID + "&oldManName=" + oldManName 
								+ "&major=" + major + "&minor=" + minor + "&acceptorName=" + acceptorName 
								+ "&address=" + address
								+ "&pageNumber=" + val;
							},
							open(time,alarm_time,alarm_voice,callback) {
								if(alarm_time-time>0){
									 this.$message('未到警报时间');
								}else{
									var $Audio = document.getElementById('brtAudio');
									$Audio.src = 'https://web.sdk.map.brtbeacon.com/api/voice/txt_to_voice?text=' + encodeURIComponent(alarm_voice) + '&types=0';
									$Audio.play();
								}
							},
							submitForm(form) {
								this.$refs[form].validate((valid) => {
									if(valid) {
										var UUID = this.form.UUID;
										var oldManName = this.form.oldManName;
										var major = this.form.major;
									    var minor = this.form.minor;
									    var acceptorName = this.form.acceptorName;
									    var address = this.form.address;
										var that = this;
										window.location.href = "${base}/iBeaconInput/index.jhtml?UUID=" + UUID + "&oldManName=" + oldManName 
											+ "&major=" + major + "&minor=" + minor + "&acceptorName=" + acceptorName + "&address=" + address;
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