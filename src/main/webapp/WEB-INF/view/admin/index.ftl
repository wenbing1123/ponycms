<#import "/WEB-INF/ftl/pony.ftl" as p>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<#include "common/head.ftl"/>
	<title><@p.message code="Admin.Title"/></title>
	<link rel="stylesheet" type="text/css" href="${base}/extjs4/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/res/css/icon.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/res/css/main.css"/>
</head>
<body>
	<div id="loading-mask"></div>
	<div id="loading">
		<div class="loading-indicator">
	        <img src="${base}/res/img/preloader.gif" width="32" height="32" style="margin-right:8px;float:left;vertical-align:top;"/><@p.message code="Admin.Title"/>
	        <br/><span id="loading-msg"><@p.message code="Admin.LoadResource"/></span>
	    </div>
	</div>
	<div id="header">
		<@p.message code="Admin.CurrentUser"/>:&nbsp;<@shiro.principal property="username"/>&nbsp;&nbsp;<a href="logout.do"><@p.message code="Admin.Logout"/></a>
	</div>
</body>
<script type="text/javascript">document.getElementById('loading-msg').innerHTML = '<@p.message code="Admin.LoadCore"/>';</script>
<script type="text/javascript" src="${base}/extjs4/ext-all.js"></script>
<script type="text/javascript" src="${base}/extjs4/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript">document.getElementById('loading-msg').innerHTML = '<@p.message code="Admin.LoadApp"/>';</script>
<script type="text/javascript">document.getElementById('loading-msg').innerHTML = '<@p.message code="Admin.InitSystem"/>';</script>
<script type="text/javascript" src="${base}/res/js/base.js"></script>
<script type="text/javascript" src="${base}/res/js/message_zh_CN.js"></script>
<script type="text/javascript">Ext.ns('App');App.base = "${base}";</script>
<script type="text/javascript" src="${base}/admin/admin.js"></script>
</html>