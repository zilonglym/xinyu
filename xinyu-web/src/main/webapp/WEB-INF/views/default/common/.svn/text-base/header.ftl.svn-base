	<div class="easyui-panel" style="padding:5px;">
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm0'">首页</a>
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm5'">商品管理</a>
		
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm3'">订单管理</a>
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm8'">发货模块</a>
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm4'">库存管理</a>
		
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm6'">报表管理</a>
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm7'">系统管理</a>
	</div>
	
	
	<div id="mm0" >
		<div><a onclick="addPanel('${ctx}/index','首页','tt')">首页</a></div>
		<div><a onclick="">个人资料</a></div>
		<div><a href="${ctx}/logout" target="_self">退出</a></div>
	</div>
	
	<div id="mm3" >
			<#if menuList??>
				<#list menuList as obj>
					<#if obj.menus=='TRADE'>
					<div><a onclick="addPanel('${ctx}/${obj.link}','${obj.title}','${obj.id}')">${obj.title}</a></div>
					</#if>
				</#list>
			</#if>
	</div>
	
	<div id="mm8" >
			<#if menuList??>
				<#list menuList as obj>
					<#if obj.menus=='QM'>
					<div><a onclick="addPanel('${ctx}/${obj.link}','${obj.title}','${obj.id}')">${obj.title}</a></div>
					</#if>
				</#list>
			</#if>
	</div>
	<div id="mm4" >
		<#if menuList??>
				<#list menuList as obj>
					<#if obj.menus=='STORAGE'>
					<div><a onclick="addPanel('${ctx}/${obj.link}','${obj.title}','${obj.id}')">${obj.title}</a></div>
					</#if>
				</#list>
			</#if>
	</div>
	<div id="mm5" >
		<#if menuList??>
				<#list menuList as obj>
					<#if obj.menus=='ITEM'>
					<div><a onclick="addPanel('${ctx}/${obj.link}','${obj.title}','${obj.id}')">${obj.title}</a></div>
					</#if>
				</#list>
			</#if>
	</div>
	<div id="mm6" >
		<#if menuList??>
				<#list menuList as obj>
					<#if obj.menus=='REPORT'>
					<div><a onclick="addPanel('${ctx}/${obj.link}','${obj.title}','${obj.id}')">${obj.title}</a></div>
					</#if>
				</#list>
			</#if>
	</div>
	<div id="mm7" >
		<#if menuList??>
				<#list menuList as obj>
					<#if obj.menus=='SYSTEM'>
					<div><a onclick="addPanel('${ctx}/${obj.link}','${obj.title}','${obj.id}')">${obj.title}</a></div>
					</#if>
				</#list>
			</#if>
	</div>
