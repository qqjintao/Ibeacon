<html>

	<head>
		<meta charset="utf-8">
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">

		<title>BRTMap 在线体验</title>

		<style type="text/css">
			* {
				margin: 0;
				padding: 0;
				-webkit-box-sizing: border-box;
				box-sizing: border-box;
				list-style: none;
			}
			
			html,
			body {
				height: 100%;
			}
			
			#brtmap {
				height: 100%;
				position: relative;
			}
			
			.startNav {
				position: absolute;
				left: 10px;
				bottom: 50px;
				height: 38px;
				line-height: 38px;
				font-size: 13px;
				background-color: #0099FF;
				color: #FFFFFF;
				padding: 0 20px;
				z-index: 10;
				border-radius: 5px;
			}
			
			.startNav:hover {
				background-color: #0088FF;
				cursor: pointer;
			}
			
			.startNav.off {
				background-color: #CCCCCC;
				cursor: default;
			}
			
			.stopNav {
				position: absolute;
				left: 10px;
				bottom: 50px;
				height: 38px;
				line-height: 38px;
				font-size: 13px;
				background-color: #FF3300;
				color: #FFFFFF;
				padding: 0 20px;
				z-index: 10;
				border-radius: 5px;
				display: none;
			}
			
			.stopNav:hover {
				background-color: #FF2200;
				cursor: pointer;
			}
			
			.btn {
				display: inline-block;
				width: 70px;
				height: 30px;
				line-height: 30px;
				text-align: center;
				font-size: 12px;
				color: #FFFFFF;
				margin: 0 5px;
				border-radius: 3px;
				cursor: pointer;
			}
			
			.btn.start {
				background-color: #21B393;
			}
			
			.btn.start:hover {
				background-color: #14c3a1;
			}
			
			.btn.end {
				background-color: #FF3300;
			}
			
			.btn.end:hover {
				background-color: #ff5d35;
			}
		</style>

	</head>

	<body>
		<audio id="brtAudio" controls="controls" style=" position: fixed; top: -9999px;" src="https://web.sdk.map.brtbeacon.com/api/voice/txt_to_voice?text=%E6%99%BA%E7%9F%B3%E5%9C%B0%E5%9B%BE%E6%A8%A1%E6%8B%9F%E5%AF%BC%E8%88%AA%E5%BC%80%E5%A7%8B%EF%BC%8C%E5%85%A8%E7%A8%8B181%E7%B1%B3%EF%BC%8C%E4%BB%8EF1%E5%B1%82%E9%A6%99%E5%A5%88%E5%84%BF%E5%88%B0F1%E5%B1%82%E6%97%A0%E5%90%8D%E5%9C%B0%E5%9D%80%EF%BC%8C%E5%A4%A7%E7%BA%A6%E9%9C%80%E8%A6%813%E5%88%86%E9%92%9F%E3%80%82%E8%AF%B7%E5%8B%BF%E5%AE%8C%E5%85%A8%E4%BE%9D%E8%B5%96%E5%AF%BC%E8%88%AA%EF%BC%8C%E6%B3%A8%E6%84%8F%E6%AD%A5%E8%A1%8C%E5%AE%89%E5%85%A8&amp;types=0"></audio>

		<div id="brtmap" class="mapboxgl-map">
			<div class="mapboxgl-canvas-container mapboxgl-interactive mapboxgl-touch-drag-pan mapboxgl-touch-zoom-rotate"><canvas class="mapboxgl-canvas" tabindex="0" aria-label="Map" width="1157" height="800" style="position: absolute; width: 1157px; height: 800px;"></canvas></div>
			<div class="mapboxgl-control-container">
				<div class="mapboxgl-ctrl-top-left">
					<div class="brtmap-floor-ctrl" style="display: block;">
						<div class="ctrl-box">F1</div>
						<ul class="ctrl-ul" show-align="down">
							<li floor-id="00230029F01" class=" active">F1</li>
							<li floor-id="00230029F02" class="">F2</li>
							<li floor-id="00230029F03" class="">F3</li>
						</ul>
					</div>
				</div>
				<div class="mapboxgl-ctrl-top-right"></div>
				<div class="mapboxgl-ctrl-bottom-left">
					<div class="mapboxgl-ctrl" style="display: block;">
						<a class="mapboxgl-ctrl-logo" target="_blank" href="http://www.brtbeacon.com" aria-label="brtmap logo"></a>
					</div>
				</div>
				<div class="mapboxgl-ctrl-bottom-right">
					<div class="brtmap-zoom-ctrl" style="display: block;">
						<a class="ctrl-in">+</a>
						<a class="ctrl-out off off off off off off off off off off off off off off off off off off off off off off off">-</a>
					</div>
					<div class="mapboxgl-ctrl mapboxgl-ctrl-attrib"></div>
				</div>
				<div class="mapboxgl-ctrl-custom"></div>
				<div class="mapboxgl-ctrl-hidden">
					<div class="mapboxgl-ctrl mapboxgl-ctrl-scale" style="width: 72.1484px; display: none;">30m</div>
				</div>
			</div>
		</div>

		<ul class="floor" id="floor" style="display: block;">
			<li class="active" floor-id="00230029F01" onclick="setFloor(&quot;00230029F01&quot;)">F1</li>
			<li class="" floor-id="00230029F02" onclick="setFloor(&quot;00230029F02&quot;)">F2</li>
			<li class="" floor-id="00230029F03" onclick="setFloor(&quot;00230029F03&quot;)">F3</li>
		</ul>

		<div class="startNav " id="startNav" style="display: block;">
			开始导航
		</div>

		<div class="stopNav" id="stopNav" style="display: none;">
			暂停导航
		</div>

		<!--brtmap 3D -->
		<link rel="stylesheet" type="text/css" href="http://files.brtbeacon.net/BRTMap/3D/brtmap-2.2.0.css">
		<script type="text/javascript" src="http://files.brtbeacon.net/BRTMap/3D/brtmap-2.2.0.js"></script>

		<!--brtmap symbol-->
		<script type="text/javascript" src="http://files.brtbeacon.com/BRTMap/3D/brtmap.symbol.js"></script>

		<script type="text/javascript">
			var buildingID = "00230029";
			var token = "0c6e9ad875014b06b539e89576b9ff6d";

			var $floor = document.getElementById('floor'),
				$startNav = document.getElementById('startNav'),
				$stopNav = document.getElementById('stopNav'),
				$Audio = document.getElementById('brtAudio'),
				$tips = document.getElementById('tips');

			var arrayMarker = [],
				$startMarker, $endMarker, $locationMarker, $routeResult, $popup, $mapClickPOI;

			var startPoint, endPoint, startPOI, endPOI, isAnimate = false,
				isStopNav = false;

			//初始化地图
			var $map = new brtmap.Map({
				container: 'brtmap',
				token: token,
				buildingID: buildingID

			});

			// mapready
			$map.on('mapready', function() {

				console.log('mapready -> $map ->', $map);

				var floorHtml = '';
				/*加载楼层*/
				for(var i = 0; i < $map.mapInfoArray.length; i++) {

					var floorInfo = $map.mapInfoArray[i];

					floorHtml += "<li class='" + (i == 0 ? 'active' : '') + "' floor-id='" + floorInfo.mapID + "' onclick='setFloor(\"" + floorInfo.mapID + "\")'>" + floorInfo.floorName + "</li>";
				}

				$floor.innerHTML = floorHtml;

				$startMarker = new brtmap.Symbol.Marker({
					url: 'http://files.brtbeacon.net/pro/demo/map/images/start_marker.png',
					size: 0.8,
					offset: [0, -20]
				}).addTo($map);

				$endMarker = new brtmap.Symbol.Marker({
					url: 'http://files.brtbeacon.net/pro/demo/map/images/end_marker.png',
					size: 0.8,
					offset: [0, -20]
				}).addTo($map);

				$locationMarker = new brtmap.Symbol.Marker({
					url: 'http://files.brtbeacon.net/pro/demo/map/images/location_marker.png',
					size: 0.8,
					offset: [0, -10],
					type: 'map'
				}).addTo($map);

			});

			// click
			$map.on('click', function(e) {

				if(isAnimate) return;

				$popup = new brtmap.Popup({
						closeButton: false
					})
					.setLngLat(e.lngLat)
					.setHTML("<a class='btn start' onclick='setStartPoint(" + e.lngLat.lng + "," + e.lngLat.lat + "," + $map.currentMapInfo.floorNumber + ")'>设置起点</a><a class='btn end' onclick='setEndPoint(" + e.lngLat.lng + "," + e.lngLat.lat + "," + $map.currentMapInfo.floorNumber + ")'>设置终点</a>")
					.addTo($map);

				$mapClickPOI = getPOI(e.point);

			});

			//play audio
			function playAudio(text, callback) {

				$Audio.src = 'https://web.sdk.map.brtbeacon.com/api/voice/txt_to_voice?text=' + encodeURIComponent(text) + '&types=0';
				$Audio.play();

			}

			//set floor
			function setFloor(floorID, callback) {

				$map.setFloor(floorID, function() {
					callback && callback();
				});

				for(var i = 0; i < $floor.children.length; i++) {

					$floor.children[i].className = $floor.children[i].className.replace(/active/gi, '');

					var _floorID = $floor.children[i].getAttribute('floor-id');

					if(floorID == _floorID) {
						$floor.children[i].className += " active";
					}
				}

			}

			//get poi
			function getPOI(point) {

				var features = $map.queryRenderedFeatures(point);

				var poi;

				for(var i = 0; i < features.length; i++) {

					var _layer = features[i].layer;

					if(_layer.id.indexOf('facility') > -1 || _layer.id.indexOf('asset') > -1 || _layer.id.indexOf('room') > -1) {

						poi = features[i].properties;

					}

				}

				if(!poi) return null;

				var _floorInfo = getFloorInfoByIndex(poi.floor);

				if(_floorInfo) {

					return {
						NAME: poi.NAME == "" ? '无名地址' : poi.NAME,
						POI_ID: poi.POI_ID,
						CATEGORY_ID: poi.CATEGORY_ID,
						LABEL_X: poi.LABEL_X,
						LABEL_Y: poi.LABEL_Y,
						FLOOR_NAME: _floorInfo.floorName,
						FLOOR_NUMBER: _floorInfo.floorNumber,
						FLOOR_ID: _floorInfo.mapID
					}

				}

				return null;
			}

			//get floorInfo
			function getFloorInfoByIndex(floorIndex) {

				for(var i = 0; i < $map.mapInfoArray.length; i++) {

					if($map.mapInfoArray[i].floorNumber == floorIndex) {
						return $map.mapInfoArray[i];
					}

				}

				return null;

			}

			// set start
			function setStartPoint(lng, lat, floor) {

				$startMarker.setLnglat([lng, lat], getFloorInfoByIndex(floor).mapID);

				startPoint = {
					lng: lng,
					lat: lat,
					floor: floor
				};

				startPOI = $mapClickPOI;

				$popup.remove();

				addRoute();

			}

			// set end
			function setEndPoint(lng, lat, floor) {

				$endMarker.setLnglat([lng, lat], getFloorInfoByIndex(floor).mapID);

				endPoint = {
					lng: lng,
					lat: lat,
					floor: floor
				};

				endPOI = $mapClickPOI;

				$popup.remove();

				addRoute();
			}

			//规格路径
			function addRoute() {

				if(startPoint && endPoint) {

					$startNav.className = $startNav.className.replace(/off/gi, '');

					$map.requestRoute(startPoint, endPoint, function(result) {

						$routeResult = result;

						console.log('$routeResult ->', result);

						$map.setRouteColor("#5E98FF", "#c3d9ff");

						$map.showRoute();

					}, function(error) {
						console.log("route error callback");
					});

				}

			}

			// animate nav
			function animateNav() {

				if(isAnimate) {
					return;
				}
				isAnimate = true;

				var partArray = $routeResult.detailedResult[0]._allRoutePartArray;

				var distanceCount = 0,
					arrPoint = [];

				for(var i = 0; i < partArray.length; i++) {

					var route = partArray[i].route;

					for(var k = 0; k < route.length - 1; k++) {

						var point1 = brtmap.CoordProjection.lngLatToMercator(route[k][0], route[k][1]),
							point2 = brtmap.CoordProjection.lngLatToMercator(route[k + 1][0], route[k + 1][1]);

						var d = Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y));

						distanceCount += d;

						var angle = getTwoPointAngle(point1, point2);

						for(var j = 0.01; j < d; j += 0.08) {

							var dx = point1.x * (1 - (j / d)) + point2.x * (j / d),
								dy = point1.y * (1 - (j / d)) + point2.y * (j / d);

							var lnglat = brtmap.CoordProjection.mercatorToLngLat(dx, dy);

							arrPoint.push({
								lnglat: lnglat,
								angle: angle,
								floor: partArray[i].mapInfo.floorNumber,
								floorID: partArray[i].mapInfo.mapID
							});

						}

					}

				}

				var index = 0,
					changeAngle = -1,
					changeFloor;

				(function() {

					if(index >= arrPoint.length) {

						isAnimate = false;

						isStopNav = false;

						playAudio("您已到达终点附近，模拟导航结束，欢迎再次使用智石地图");

						$floor.style.display = 'block';

						$startNav.style.display = 'block';

						$stopNav.style.display = 'none';

						return false;
					}

					var _callee = arguments.callee;

					if(isStopNav) {
						window.setTimeout(_callee, 100);
						return;
					}

					var point = arrPoint[index];

					if(point.angle != changeAngle) {

						$locationMarker.setRotate(point.angle + 90);

						changeAngle = point.angle;

					}

					$map.showRoute({
						x: point.lnglat.lng,
						y: point.lnglat.lat,
						floor: point.floor
					});
					$locationMarker.setLnglat([point.lnglat.lng, point.lnglat.lat], getFloorInfoByIndex(point.floor).mapID);
					$map.setCenter(point.lnglat);

					index++;

					if(point.floor != $map.currentMapInfo.floorNumber || (changeFloor && point.floor != changeFloor)) {

						setFloor(point.floorID, function() {

							$map.setZoom(21);
							window.setTimeout(_callee, 500);

						});

						changeFloor = point.floor;
					} else {
						window.setTimeout(_callee, 15);

						changeFloor = point.floor;
					}

				})();

				var _mins = Math.ceil(distanceCount / 1.5 / 60);

				var audioText = "运用iBeacon技术之老人健康管理系统导航开始，全程" + Math.ceil(distanceCount) + "米，从" + startPOI.FLOOR_NAME + "层" + startPOI.NAME + "到" + endPOI.FLOOR_NAME + "层" + endPOI.NAME + "，大约需要" + _mins + "分钟。请勿完全依赖导航，注意步行安全";

				playAudio(audioText);

			}

			// getTwoPointAngle
			function getTwoPointAngle(point1, point2) {

				if(point1.constructor != Array) {
					point1 = [point1.x, point1.y];
				}
				if(point2.constructor != Array) {
					point2 = [point2.x, point2.y];
				}

				var x1 = point1[0],
					y1 = point1[1],
					x2 = point2[0],
					y2 = point2[1];

				var x = Math.abs(x1 - x2),
					y = Math.abs(y1 - y2),
					z = Math.sqrt(x * x + y * y);

				var rotat = Math.round(Math.asin(y / z) / Math.PI * 180);

				// 第一象限
				if(x2 >= x1 && y2 <= y1) {
					rotat = rotat;
				}
				// 第二象限
				else if(x2 <= x1 && y2 <= y1) {
					rotat = 180 - rotat;
				}
				// 第三象限
				else if(x2 <= x1 && y2 >= y1) {
					rotat = 180 + rotat;
				}
				// 第四象限
				else if(x2 >= x1 && y2 >= y1) {
					rotat = 360 - rotat;
				}

				return rotat; //角度
			}

			// start nav
			$startNav.addEventListener('click', function() {

				function _callee() {

					$map.easeTo({
						zoom: 21,
						pitch: 0,
						center: [startPoint.lng, startPoint.lat]
					});

					if(!isStopNav) {
						window.setTimeout(function() {
							animateNav();
						}, 800);
					}

				}

				if(startPoint.floor != endPoint.floor || startPoint.floor != $map.currentMapInfo.floorNumber || endPoint.floor != $map.currentMapInfo.floorNumber) {
					setFloor(getFloorInfoByIndex(startPoint.floor).mapID, function() {
						_callee();
					});
				} else {
					_callee();
				}

				isStopNav = false;

				$floor.style.display = 'none';

				$startNav.style.display = 'none';

				$stopNav.style.display = 'block';

			}, false);

			$stopNav.addEventListener('click', function() {

				isStopNav = true;

				$floor.style.display = 'block';

				$startNav.style.display = 'block';

				$stopNav.style.display = 'none';

			}, false);
		</script>

	</body>

</html>