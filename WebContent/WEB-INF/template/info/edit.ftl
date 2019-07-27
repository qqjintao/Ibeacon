<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>修改顾客信息表</title>
		<!-- 引入样式 -->
		<!--<link rel="stylesheet" href="${base}/resources/common/css/Element-index.css">-->
		<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
		<link rel="stylesheet" href="${base}/resources/common/css/info/infoCss.css">
	</head>

	<body>
		<div id="app">
			<el-form :model="form" :rules="rules2" ref="form" label-width="100px">
				<table>
					<tbody>
						<tr>
							<td>
								<el-form-item label="姓名：" prop="idCardName">
									<span v-if="form.seen">${info.idCardName}</span>
									<el-input type="text" v-model="form.idCardName" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="英语名：" prop="englishName">
									<span v-if="form.seen">${info.englishName}</span>
									<el-input type="text" v-model="form.englishName" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>
							<td rowspan="3">
								<span v-if="form.seen">
									[#if info.headPortrait!=null]
									<img src=${info.headPortrait} class="avatar">
									[/#if]
								</span>
								<el-upload class="avatar-uploader" action="${base}/common/fileSave.jhtml" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload" v-if="!form.seen">
									<img :src="form.imageUrl" class="avatar">
									<i v-else class="el-icon-plus avatar-uploader-icon"></i>
								</el-upload>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="年龄：" prop="age">
									<span v-if="form.seen">${info.age}</span>
									<el-input type="text" v-model="form.age" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>

							<td>
								<el-form-item label="婚姻状况：" prop="maritalStatus">
									<el-select v-model="form.maritalStatus" placeholder="请选择">
										<el-option v-for="item in form.maritalStatusList" :key="item.value" :label="item.label" :value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="出生日期：" prop="dateOfBirth">
									<span v-if="form.seen">${info.dateOfBirth}</span>
									<el-date-picker v-if="!form.seen" v-model="form.dateOfBirth" type="date" placeholder="选择日期" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd">
									</el-date-picker>
								</el-form-item>
							</td>

							<td>
								<el-form-item label="性别：" prop="sex">
									<el-select v-model="form.sex" placeholder="请选择">
										<el-option v-for="item in form.sexList" :key="item.value" :label="item.label" :value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="民族：" prop="nation">
									<span v-if="form.seen">${info.nation}</span>
									<el-input type="text" v-model="form.nation" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>
							<td colspan="2">
								<el-form-item label="国籍：" prop="nationality">
									<span v-if="form.seen">${info.nationality}</span>
									<el-input type="text" v-model="form.nationality" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>

						</tr>
						<tr>
							<td>
								<el-form-item label="政治面貌：" prop="politicsStatus">
									<span v-if="form.seen">${info.politicsStatus}</span>
									<el-input type="text" v-model="form.politicsStatus" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>

							<td colspan="2">
								<el-form-item label="出生地：" prop="birthplace">
									<span v-if="form.seen">${info.birthplace}</span>
									<el-input type="text" v-model="form.birthplace" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<el-form-item label="身份证：" prop="idCard">
									<span v-if="form.seen">${info.idCard}</span>
									<el-input type="text" v-model="form.idCard" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="现居住地：" prop="province">
									<el-select v-model="form.province" placeholder="请选择省" @change="provinceChange">
										<el-option v-for="item in form.provinceList" :key="item.value" :label="item.label" :value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="" prop="city">
									<el-select v-model="form.city" placeholder="请选择市" @change="cityChange">
										<el-option v-for="item in form.cityList" :key="item.value" :label="item.label" :value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="" prop="district">
									<el-select v-model="form.district" placeholder="请选择区">
										<el-option v-for="item in form.districtList" :key="item.value" :label="item.label" :value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<el-form-item label="" prop="streetAddress">
									<span v-if="form.seen">${info.streetAddress}</span>
									<el-input type="text" v-model="form.streetAddress" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="户口所住地：" prop="domicileProvince">
									<el-select v-model="form.domicileProvince" placeholder="请选择省" @change="domicileProvinceChange">
										<el-option v-for="item in form.domicileProvinceList" :key="item.value" :label="item.label" :value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="" prop="domicileCity">
									<el-select v-model="form.domicileCity" placeholder="请选择市" @change="domicileCityChange">
										<el-option v-for="item in form.domicileCityList" :key="item.value" :label="item.label" :value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="" prop="domicileDistrict">
									<el-select v-model="form.domicileDistrict" placeholder="请选择区">
										<el-option v-for="item in form.domicileDistrictList" :key="item.value" :label="item.label" :value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<el-form-item label="" prop="domicile">
									<span v-if="form.seen">${info.domicile}</span>
									<el-input type="text" v-model="form.domicile" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="住宅电话：" prop="homePhone">
									<span v-if="form.seen">${info.homePhone}</span>
									<el-input type="text" v-model="form.homePhone" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>
							<td colspan="2">
								<el-form-item label="手机：" prop="phone">
									<span v-if="form.seen">${info.phone}</span>
									<el-input type="text" v-model="form.phone" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="办公电话：" prop="officePhone">
									<span v-if="form.seen">${info.officePhone}</span>
									<el-input type="text" v-model="form.officePhone" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>
							<td colspan="2">
								<el-form-item label="邮箱：" prop="mailbox">
									<span v-if="form.seen">${info.mailbox}</span>
									<el-input type="text" v-model="form.mailbox" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<el-form-item label="备注：" prop="remark">
									<span v-if="form.seen">${info.remark}</span>
									<el-input type="textarea" v-model="form.remark" auto-complete="off" v-if="!form.seen"></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<center>
									<el-form-item label-width="30px">
										<el-button type="primary" icon="el-icon-edit" circle v-if="form.seen" @click="show"></el-button>
										<el-button icon="el-icon-refresh" circle v-if="form.seen" @click="reload"></el-button>
										<el-button type="success" icon="el-icon-check" circle v-if="!form.seen" @click="submitForm('form')"></el-button>
										<el-button type="info" icon="el-icon-back" circle v-if="!form.seen" @click="reload"></el-button>
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
					var validateIdCardName = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入姓名！'));
							return false;
						} else {
							callback();
						}
					};
					var validateEnglishName = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入英语名！'));
							return false;
						} else {
							callback();
						}
					};
					var validateAge = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请选择年龄！'));
							return false;
						} else {
							callback();
						}
					};
					var validateSex = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请选择性别！'));
							return false;
						} else {
							callback();
						}
					};
					var validateBirthplace = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入出生地！'));
							return false;
						} else {
							callback();
						}
					};
					var validateDateOfBirth = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请选择出生日期！'));
							return false;
						} else {
							callback();
						}
					};
					var validateNationality = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入国籍！'));
							return false;
						} else {
							callback();
						}
					};
					var validateIdCard = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入身份证！'));
							return false;
						} else {
							callback();
						}
					};
					var validateNation = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入民族！'));
							return false;
						} else {
							callback();
						}
					};
					var validatePoliticsStatus = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入政治面貌！'));
							return false;
						} else {
							callback();
						}
					};
					var validateMaritalStatus = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请选择婚姻状况！'));
							return false;
						} else {
							callback();
						}
					};
					var validateProvince = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请选择现居住地省！'));
							return false;
						} else {
							callback();
						}
					};
					var validateCity = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请选择现居住地市'));
							return false;
						} else {
							callback();
						}
					};
					var validateDistrict = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请选择现居住地区！'));
							return false;
						} else {
							callback();
						}
					};
					var validateStreetAddress = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入现居住地街道地址！'));
							return false;
						} else {
							callback();
						}
					};
					var validateDomicileProvince = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请选择户口所在地省！'));
							return false;
						} else {
							callback();
						}
					};
					var validateDomicileCity = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请选择户口所在地市！'));
							return false;
						} else {
							callback();
						}
					};
					var validateDomicileDistrict = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请选择户口所在地区！'));
							return false;
						} else {
							callback();
						}
					};
					var validateDomicile = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入户口所在地街道地址！'));
							return false;
						} else {
							callback();
						}
					};
					var validateMailbox = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入邮箱！'));
							return false;
						} else {
							callback();
						}
					};
					var validatePhone = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入手机号码！'));
							return false;
						} else {
							callback();
						}
					};
					var validateHomePhone = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入住宅电话！'));
							return false;
						} else {
							callback();
						}
					};
					var validateOfficePhone = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入办公电话！'));
							return false;
						} else {
							callback();
						}
					};
					return {
						form: {
							seen: true,
							imageUrl: "${info.headPortrait}",
							idCardName: "${info.idCardName}",
							englishName: "${info.englishName}",
							age: "${info.age}",
							sex: "${info.sex}",
							birthplace: "${info.birthplace}",
							dateOfBirth: "${info.dateOfBirth}",
							idCard: "${info.idCard}",
							nationality: "${info.nationality}",
							nation: "${info.nation}",
							politicsStatus: "${info.politicsStatus}",
							maritalStatus: "${info.maritalStatus}",
							city: "${info.city}",
							province: "${info.province}",
							streetAddress: "${info.streetAddress}",
							district: "${info.district}",
							domicileCity: "${info.domicileCity}",
							domicileProvince: "${info.domicileProvince}",
							domicileDistrict: "${info.domicileDistrict}",
							domicile: "${info.domicile}",
							phone: "${info.phone}",
							mailbox: "${info.mailbox}",
							homePhone: "${info.homePhone}",
							officePhone: "${info.officePhone}",
							remark: "${info.remark}",
							sexList: [{
								value: '女',
								label: '女'
							}, {
								value: '男',
								label: '男'
							}, ],
							maritalStatusList: [{
									value: '未婚',
									label: '未婚'
								},
								{
									value: '已婚',
									label: '已婚'
								},
							],
							provinceList: [
								[#list provinces as province] {
									value: '${province.name}',
									label: '${province.code}'
								},
								[/#list]],
								cityList: [

								],
								districtList: [

								],
								domicileProvinceList: [
									[#list provinces as province] {
										value: '${province.name}',
										label: '${province.code}'
									},
									[/#list]],
									domicileCityList: [

									],
									domicileDistrictList: [

									],
								},
								rules2: {
															idCardName: [{
																validator: validateIdCardName,
																trigger: 'blur'
															}],
									//						englishName: [{
									//							validator: validateEnglishName,
									//							trigger: 'blur'
									//						}],
															age: [{
																validator: validateAge,
																trigger: 'blur'
															}],
															sex: [{
																validator: validateSex,
																trigger: 'blur'
															}],
									//						birthplace: [{
									//							validator: validateBirthplace,
									//							trigger: 'blur'
									//						}],
									//						dateOfBirth: [{
									//							validator: validateDateOfBirth,
									//							trigger: 'blur'
									//						}],
									//						nationality: [{
									//							validator: validateNationality,
									//							trigger: 'blur'
									//						}],
															idCard: [{
																validator: validateIdCard,
																trigger: 'blur'
															}],
									//						nation: [{
									//							validator: validateNation,
									//							trigger: 'blur'
									//						}],
									//						politicsStatus : [{
									//							validator: validatePoliticsStatus,
									//							trigger: 'blur'
									//						}],
									//						maritalStatus: [{
									//							validator: validateMaritalStatus,
									//							trigger: 'blur'
									//						}],
									//						province: [{
									//							validator: validateProvince,
									//							trigger: 'blur'
									//						}],
									//						city: [{
									//							validator: validateCity,
									//							trigger: 'blur'
									//						}],
									//						district: [{
									//							validator: validateDistrict,
									//							trigger: 'blur'
									//						}],
									//						streetAddress: [{
									//							validator: validateStreetAddress,
									//							trigger: 'blur'
									//						}],
									//						domicileProvince: [{
									//							validator: validateDomicileProvince,
									//							trigger: 'blur'
									//						}],
									//						domicileCity : [{
									//							validator: validateDomicileCity,
									//							trigger: 'blur'
									//						}],
									//						domicileDistrict: [{
									//							validator: validateDomicileDistrict,
									//							trigger: 'blur'
									//						}],
									//						domicile: [{
									//							validator: validateDomicile,
									//							trigger: 'blur'
									//						}],
															mailbox: [{
																validator: validateMailbox,
																trigger: 'blur'
															}],
															phone: [{
																validator: validatePhone,
																trigger: 'blur'
															}],
									//						homePhone : [{
									//							validator: validateHomePhone,
									//							trigger: 'blur'
									//						}],
									//						officePhone : [{
									//							validator: validateOfficePhone,
									//							trigger: 'blur'
									//						}],
								}
							};
						},
						methods: {
							show: function() {
								this.form.seen = !this.form.seen;
							},
							reload: function() {
								window.location.reload();
							},
							handleAvatarSuccess(res, file) {
								this.form.imageUrl = file.response;
							},
							beforeAvatarUpload(file) {
								const isJPG = file.type === 'image/jpeg';
								const isLt2M = file.size / 1024 / 1024 < 2;
								if(!isJPG) {
									this.$message.error('上传头像图片只能是 JPG 格式!');
								}
								if(!isLt2M) {
									this.$message.error('上传头像图片大小不能超过 2MB!');
								}
								return isJPG && isLt2M;
							},
							provinceChange() {
								var province = this.form.province;
								var that = this;
								$.ajax({
									type: 'post',
									url: '${base}/dataDictionary/onChange.jhtml',
									data: {
										"province": province,
									},
									dataType: "json",
									success: function(date) {
										var Data = "[";
										for(var i = 0; i < date[0].citys.length; i++) {
											Data += "{";
											Data += "value:'" + date[0].citys[i].name + "',";
											Data += "label:'" + date[0].citys[i].code + "'";
											Data += "},";
										}
										Data += "];"
										var out = eval(Data);
										that.form.cityList = out;
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
							cityChange() {
								var province = this.form.province;
								var city = this.form.city;
								var that = this;
								$.ajax({
									type: 'post',
									url: '${base}/dataDictionary/onChange.jhtml',
									data: {
										"province": province,
										"city": city,
									},
									dataType: "json",
									success: function(date) {
										var Data = "[";
										for(var i = 0; i < date[0].districts.length; i++) {
											Data += "{";
											Data += "value:'" + date[0].districts[i].name + "',";
											Data += "label:'" + date[0].districts[i].code + "'";
											Data += "},";
										}
										Data += "];"
										var out = eval(Data);
										that.form.districtList = out;
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
							domicileProvinceChange() {
								var domicileProvince = this.form.domicileProvince;
								var that = this;
								$.ajax({
									type: 'post',
									url: '${base}/dataDictionary/onChange.jhtml',
									data: {
										"province": domicileProvince,
									},
									dataType: "json",
									success: function(date) {
										var Data = "[";
										for(var i = 0; i < date[0].citys.length; i++) {
											Data += "{";
											Data += "value:'" + date[0].citys[i].name + "',";
											Data += "label:'" + date[0].citys[i].code + "'";
											Data += "},";
										}
										Data += "];"
										var out = eval(Data);
										that.form.domicileCityList = out;
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
							domicileCityChange() {
								var province = this.form.domicileProvince;
								var city = this.form.domicileCity;
								var that = this;
								$.ajax({
									type: 'post',
									url: '${base}/dataDictionary/onChange.jhtml',
									data: {
										"province": province,
										"city": city,
									},
									dataType: "json",
									success: function(date) {
										var Data = "[";
										for(var i = 0; i < date[0].districts.length; i++) {
											Data += "{";
											Data += "value:'" + date[0].districts[i].name + "',";
											Data += "label:'" + date[0].districts[i].code + "'";
											Data += "},";
										}
										Data += "];"
										var out = eval(Data);
										that.form.domicileDistrictList = out;
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
							submitForm(form) {
								this.$refs[form].validate((valid) => {
									if(valid) {
										var imageUrl = this.form.imageUrl;
										var idCardName = this.form.idCardName;
										var englishName = this.form.englishName;
										var age = this.form.age;
										var sex = this.form.sex;
										var birthplace = this.form.birthplace;
										var dateOfBirth = this.form.dateOfBirth;
										var nationality = this.form.nationality;
										var idCard = this.form.idCard;
										var nation = this.form.nation;
										var politicsStatus = this.form.politicsStatus;
										var maritalStatus = this.form.maritalStatus;
										var province = this.form.province;
										var city = this.form.city;
										var district = this.form.district;
										var streetAddress = this.form.streetAddress;
										var domicileProvince = this.form.domicileProvince;
										var domicileCity = this.form.domicileCity;
										var domicileDistrict = this.form.domicileDistrict;
										var domicile = this.form.domicile;
										var mailbox = this.form.mailbox;
										var phone = this.form.phone;
										var homePhone = this.form.homePhone;
										var officePhone = this.form.officePhone;
										var remark = this.form.remark;
										var that = this;
										$.ajax({
											type: 'post',
											url: '${base}/info/infoUpdate.jhtml',
											data: {
												"id": '${info.id}',
												"createDate": '${info.createDate}',
												"createName": '${info.createName}',
												"headPortrait": imageUrl,
												"idCardName": idCardName,
												"englishName": englishName,
												"age": age,
												"sex": sex,
												"birthplace": birthplace,
												"dateOfBirth": dateOfBirth,
												"nationality": nationality,
												"idCard": idCard,
												"nation": nation,
												"politicsStatus": politicsStatus,
												"maritalStatus": maritalStatus,
												"province": province,
												"city": city,
												"district": district,
												"streetAddress": streetAddress,
												"domicileProvince": domicileProvince,
												"domicileCity": domicileCity,
												"domicileDistrict": domicileDistrict,
												"domicile": domicile,
												"mailbox": mailbox,
												"phone": phone,
												"homePhone": homePhone,
												"officePhone": officePhone,
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
														window.location.href = "${base}/info/index.jhtml";
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