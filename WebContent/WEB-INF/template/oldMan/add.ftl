<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>新增老人信息表</title>
		<!-- 引入样式 -->
		<!--<link rel="stylesheet" href="${base}/resources/common/css/Element-index.css">-->
		<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
		<link rel="stylesheet" href="${base}/resources/common/css/oldMan/oldManCss.css">
	</head>

	<body>
		<div id="app">
			<el-form :model="form" :rules="rules2" ref="form" label-width="100px" class="demo-ruleForm">
				<table>
					<tbody>
						<tr>
							<td>
								<el-form-item label="姓名：" prop="name">
									<el-input type="text" v-model="form.name" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="英语名：" prop="englishName">
									<el-input type="text" v-model="form.englishName" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
							<td rowspan="3">
								<el-upload class="avatar-uploader" action="${base}/common/fileSave.jhtml" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload" >
									<img :src="form.imageUrl" class="avatar">
									<i v-else class="el-icon-plus avatar-uploader-icon"></i>
								</el-upload>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="年龄：" prop="age">
									<el-input type="text" v-model="form.age" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="政治面貌：" prop="politicsStatus">
									<el-input type="text" v-model="form.politicsStatus" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="国籍：" prop="nationality">
									<el-input type="text" v-model="form.nationality" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
							<td>
								<el-form-item label="民族：" prop="nation">
									<el-input type="text" v-model="form.nation" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="性别：" prop="sex">
									<el-select v-model="form.sex" placeholder="请选择">
										<el-option v-for="item in form.sexList" :key="item.value" :label="item.label" :value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</td>
							<td colspan="2">
								<el-form-item label="身体状况：" prop="physical">
									<el-input type="text" v-model="form.physical" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="关系：" prop="relation">
									<el-input type="text" v-model="form.relation" auto-complete="off" ></el-input>
								</el-form-item>
								</el-form-item>
							</td>
							<td colspan="2">
								<el-form-item label="联系电话：" prop="phone">
									<el-input type="text" v-model="form.phone" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<el-form-item label="身份证：" prop="identityCard">
									<el-input type="text" v-model="form.identityCard" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<el-form-item label="医保账户：" prop="account">
									<el-input type="text" v-model="form.account" auto-complete="off" ></el-input>
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
									<el-input type="text" v-model="form.streetAddress" auto-complete="off" ></el-input>
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
									<el-input type="text" v-model="form.domicile" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="负责医生：" prop="doctor">
									<el-input type="text" v-model="form.doctor" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
							<td colspan="2">
								<el-form-item label="医生电话：" prop="doctorPhone">
									<el-input type="text" v-model="form.doctorPhone" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="负责护士：" prop="nurse">
									<el-input type="text" v-model="form.nurse" auto-complete="off" ></el-input>
								</el-form-item>
							</td>

							<td colspan="2">
								<el-form-item label="护士电话：" prop="nursePhone">
									<el-input type="text" v-model="form.nursePhone" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td>
								<el-form-item label="紧急联系人：" prop="emergencyName">
									<el-input type="text" v-model="form.emergencyName" auto-complete="off" ></el-input>
								</el-form-item>
							</td>

							<td colspan="2">
								<el-form-item label="紧急联系电话：" prop="emergencyCall" label-width="120px">
									<el-input type="text" v-model="form.emergencyCall" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<el-form-item label="备注：" prop="remark">
									<el-input type="textarea" v-model="form.remark" auto-complete="off" ></el-input>
								</el-form-item>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<el-form-item label-width="30px">
									<el-button type="success" icon="el-icon-check" circle  @click="submitForm('form')"></el-button>
									<el-button type="info" icon="el-icon-back" circle  @click="reload"></el-button>
								</el-form-item>
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
					var validateName = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入姓名！'));
							return false;
						} else {
							callback();
						}
					};
					var validateEnglishName = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入英语名'));
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
					var validateRelation = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入关系！'));
							return false;
						} else {
							callback();
						}
					};
					var validatePhysical = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请选择健康状况！'));
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
					var validateIdentityCard = (rule, value, callback) => {
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
					var validateAccount = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请选择医保账户！'));
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

					var validateDoctor = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请选择主负责医生姓名！'));
							return false;
						} else {
							callback();
						}
					};
					var validateDoctorPhone = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入主负责医生手机！'));
							return false;
						} else {
							callback();
						}
					};
					var validateNurse = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入主负责护士姓名'));
							return false;
						} else {
							callback();
						}
					};
					var validateNursePhone = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入主负责护士手机！'));
							return false;
						} else {
							callback();
						}
					};
					var validateEmergencyName = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入紧急联系人姓名！'));
							return false;
						} else {
							callback();
						}
					};
					var validateEmergencyCall = (rule, value, callback) => {
						if(!value) {
							callback(new Error('请输入紧急联系人手机！'));
							return false;
						} else {
							callback();
						}
					};
					return {
						form: {
							sexList: [{
								value: '女',
								label: '女'
							}, {
								value: '男',
								label: '男'
							}, ],
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
																	name: [{
																		validator: validateName,
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
																	relation: [{
																		validator: validateRelation,
																		trigger: 'blur'
																	}],
											//						physical: [{
											//							validator: validatePhysical,
											//							trigger: 'blur'
											//						}],
											//						nationality: [{
											//							validator: validateNationality,
											//							trigger: 'blur'
											//						}],
											//						identityCard: [{
											//							validator: validateIdentityCard,
											//							trigger: 'blur'
											//						}],
											//						nation: [{
											//							validator: validateNation,
											//							trigger: 'blur'
											//						}],
											//						politicsStatus: [{
											//							validator: validatePoliticsStatus,
											//							trigger: 'blur'
											//						}],
											//						account: [{
											//							validator: validateAccount,
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
											//						domicileCity: [{
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
											//						homePhone: [{
											//							validator: validateHomePhone,
											//							trigger: 'blur'
											//						}],
											//						officePhone: [{
											//							validator: validateOfficePhone,
											//							trigger: 'blur'
											//						}],
											//						doctor: [{
											//							validator: validateDoctor,
											//							trigger: 'blur'
											//						}],
											//						doctorPhone: [{
											//							validator: validateDoctorPhone,
											//							trigger: 'blur'
											//						}],
											//						nurse: [{
											//							validator: validateNurse,
											//							trigger: 'blur'
											//						}],
											//						nursePhone: [{
											//							validator: validateNursePhone,
											//							trigger: 'blur'
											//						}],
																	emergencyName: [{
																		validator: validateEmergencyName,
																		trigger: 'blur'
																	}],
																	emergencyCall: [{
																		validator: validateEmergencyCall,
																		trigger: 'blur'
																	}],
										}
									};
								},
								methods: {
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
												var name = this.form.name;
												var englishName = this.form.englishName;
												var age = this.form.age;
												var sex = this.form.sex;
												var relation = this.form.relation;
												var physical = this.form.physical;
												var identityCard = this.form.identityCard;
												var nationality = this.form.nationality;
												var nation = this.form.nation;
												var politicsStatus = this.form.politicsStatus;
												var account = this.form.account;
												var province = this.form.province;
												var city = this.form.city;
												var district = this.form.district;
												var streetAddress = this.form.streetAddress;
												var domicileProvince = this.form.domicileProvince;
												var domicileCity = this.form.domicileCity;
												var domicileDistrict = this.form.domicileDistrict;
												var domicile = this.form.domicile;
												var doctor = this.form.doctor;
												var doctorPhone = this.form.doctorPhone;
												var nurse = this.form.nurse;
												var nursePhone = this.form.nursePhone;
												var emergencyName = this.form.emergencyName;
												var emergencyCall = this.form.emergencyCall;
												var phone = this.form.phone;
												var mailbox = this.form.mailbox;
												var homePhone = this.form.homePhone;
												var officePhone = this.form.officePhone;
												var remark = this.form.remark;
												var that = this;
												$.ajax({
													type: 'post',
													url: '${base}/oldMan/oldManUpdate.jhtml',
													data: {
														"headPortrait": imageUrl,
														"name": name,
														"englishName": englishName,
														"age": age,
														"sex": sex,
														"relation": relation,
														"physical": physical,
														"identityCard": identityCard,
														"nationality": nationality,
														"nation": nation,
														"politicsStatus": politicsStatus,
														"account": account,
														"province": province,
														"city": city,
														"district": district,
														"streetAddress": streetAddress,
														"domicileProvince": domicileProvince,
														"domicileCity": domicileCity,
														"domicileDistrict": domicileDistrict,
														"domicile": domicile,
														"doctor": doctor,
														"doctorPhone": doctorPhone,
														"nurse": nurse,
														"nursePhone": nursePhone,
														"emergencyName": emergencyName,
														"emergencyCall": emergencyCall,
														"phone": phone,
														"mailbox": mailbox,
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
																window.location.href = "${base}/oldMan/index.jhtml";
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