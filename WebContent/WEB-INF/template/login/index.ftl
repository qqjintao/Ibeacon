<!DOCTYPE html>
<html>
	<head>
		<title>老人健康管理系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${base}/resources/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
		<link href="${base}/resources/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
		<link href="${base}/resources/assets/css/main-min.css" rel="stylesheet" type="text/css" />
	</head>

	<body>
		<div class="header">
			<div class="dl-title">
				<a href="#" title="" target="_blank">
					<span class="lp-title-port">运用iBeacon定位技术</span><span class="dl-title-text">之老人健康管理系统</span>
				</a>
			</div>
			<div class="dl-log">欢迎您，<span class="dl-log-user">${login.nickName}</span>
				<a onclick="window.location.href = '${base}/login/out.jhtml';" title="退出系统" class="dl-log-quit">[退出]</a>
			</div>
		</div>
		<div class="content">
			<div class="dl-main-nav">
				<ul id="J_Nav" class="nav-list ks-clear">
					<li class="nav-item dl-selected">
						<div class="nav-item-inner nav-home">信息录入</div>
					</li>
					<li class="nav-item">
						<div class="nav-item-inner nav-order">轨迹查询</div>
					</li>
					<li class="nav-item">
						<div class="nav-item-inner nav-goods">设备管理</div>
					</li>
					<li class="nav-item">
						<div class="nav-item-inner nav-permission">电子围栏</div>
					</li>
					<li class="nav-item">
						<div class="nav-item-inner nav-monitor">警报处理</div>
					</li>
					<li class="nav-item">
						<div class="nav-item-inner nav-inventory">操作文档</div>
					</li>
				</ul>
			</div>
			<ul id="J_NavContent" class="dl-tab-conten">
			</ul>
		</div>
		<script type="text/javascript" src="${base}/resources/assets/js/jquery-1.8.1.min.js"></script>
		<script type="text/javascript" src="${base}/resources/assets/js/bui.js"></script>
		<script type="text/javascript" src="${base}/resources/assets/js/config.js"></script>
		<script>
			BUI.use('common/main', function() {
				var config = [{
					id: 'info',
					homePage: 'loginTable',
					menu: [{
						text: '顾客信息',
						items: [{
								id: 'loginTable',
								text: '登录信息表',
								href: '${base}/login/loginTable.jhtml',
								closeable: false
							},
							[#if login.type='3']
							{
								id: 'loginList',
								text: '登录信息列表',
								href: '${base}/login/list.jhtml'
							},
							[/#if]
							{
								id: 'index',
								text: '顾客信息表',
								href: '${base}/info/index.jhtml'
							},
						]
					}, {
						text: '老人信息',
						items: [{
							id: 'oldManTable',
							text: '老人信息表',
							href: '${base}/oldMan/index.jhtml'
						}, ]
					}]
				}, {
					id: 'form',
					menu: [{
						text: '轨迹查询',
						items: [{
								id: 'trackTable',
								text: '轨迹查询表',
								href: '${base}/track/index.jhtml'
							},
							{
								id: 'iBeaconInput',
								text: '模拟IBeacon',
								href: '${base}/iBeaconInput/index.jhtml'
							},
						]
					}]
				}, {
					id: 'trajectory',
					menu: [{
						text: '设备管理',
						items: [{
								id: 'ibeaconTable',
								text: 'ibeacon管理',
								href: '${base}/ibeacon/index.jhtml'
							},
							{
								id: 'acceptorTable',
								text: '接收器管理',
								href: '${base}/acceptor/index.jhtml'
							},
						]
					}]
				}, {
					id: 'fence',
					menu: [{
						text: '电子围栏',
						items: [{
							id: 'fenceTable',
							text: '电子围栏',
							href: '${base}/fence/index.jhtml'
						}, ]
					}]
				}, {
					id: 'alarm',
					menu: [{
						text: '警报处理',
						items: [{
							id: 'alarmTable',
							text: '警报处理',
							href: '${base}/acceptor/alarm.jhtml'
						}, ]
					}]
				}, {
					id: 'document',
					menu: [{
						text: '操作文档',
						items: [{
							id: 'documentTable',
							text: '操作文档',
							href: '${base}/document/index.jhtml'
						}, 
						[#if login.type='3']
						{
							id: 'dataDictionary',
							text: '数据字典',
							href: '${base}/dataDictionary/index.jhtml'
						},
						[/#if]
						]
					}]
				}];
				new PageUtil.MainPage({
					modulesConfig: config
				});
			});
		</script>
	</body>
</html>