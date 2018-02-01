<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script src="${ctx}/static/scripts/cainiao_print.js" type="text/javascript"></script>
	</head>
	<body>
		<br />
		<select id="printList" name="printList"></select>

		<input type="button" id="BtnPrint" name="BtnPrint" value="打印" onclick="printWeb();"  />

		<input type="button" onclick="doGetPrinters()"  value="刷新打印机列表"/>

		<input type="button" onclick="doPrinterConfig()"  value="当前打印机"/>
		<script>
			request = {
				cmd : "print",
				requestID : "12345678901234567890",
				version : "1.0",
				task : {
					taskID : '' + printTaskId,
					preview : false,
					printer : defaultPrinter,
					documents : [{
						"documentID" : "884552481660280113",
						"contents" : [{
							"templateURL" : "http://cloudprint.cainiao.com/cloudprint/template/getStandardTemplate.json?template_id=801",
							"signature" : "ALIBABACAINIAOWANGLUO",
							"data" : {
								"sender" : {
									"address" : {
										"detail" : "湖南省湘潭市岳塘区双拥南路25号高新创业园A3栋3楼星宇物流客服部",
										"province" : "湖南省",
										"district" : "",
										"city" : "湘潭市"
									},
									"name" : "天际(马亮)",
									"mobile" : "0731-52777568"
								},
								"shippingOption" : {
									"services" : {
										"SVC-COD" : {
											"value" : "200"
										}
									},
									"title" : "",
									"code" : "COD"
								},
								"routingInfo" : {
									"routeCode" : "380D-56-04",
									"origin" : {
										"code" : "YTO"
									},
									"sortation" : {
										"name" : "580-001 620"
									},
									"consolidation" : {
										"name" : "",
										"code" : "591906"
									}
								},
								"recipient" : {
									"phone" : "",
									"address" : {
										"detail" : "福建省 福州市 鼓楼区. 洪山镇杨桥西路融汇江山10号店安徳鲁森(350001).",
										"province" : "福建省",
										"town" : "",
										"district" : "鼓楼区",
										"city" : "福州市"
									},
									"name" : "张竹青",
									"mobile" : "18120850756"
								},
								"waybillCode" : "884552481660280113"
							}
						}, {
							"data" : {
								"item_name" : "买家留言:检查好，确保无质量问题再发货，包装严实些卖家留言:\\r\\n天际DGD30-30ZWD紫砂电炖锅(1件) | 精品刀具4件套(1件) | 手套(1件) | "
							},
							"templateURL" : "http://cloudprint.cainiao.com/cloudprint/customArea/queryCustomAreaList4Top.json?custom_area_id=642230"
						}]
					}]
				}
			};
		</script>
	</body>
</html>