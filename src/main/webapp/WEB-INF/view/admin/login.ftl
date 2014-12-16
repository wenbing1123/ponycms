<#import "/WEB-INF/ftl/pony.ftl" as p>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include "common/head.ftl"/>
	<title><@p.message code="Admin.Title"/></title>
	<link rel="shortcut icon" href="${base}/res/img/favicon.ico"/>
	<link rel="stylesheet" type="text/css" href="${base}/res/css/login.css"/>
	<script type="text/javascript" src="${base}/res/js/jquery.js"></script>
	<script language="javascript" src="${base}/res/js/login.js"></script> 
</head>
<body>
	<span class="span_left"></span><span class="span_right"></span>
	<div class="loginBody">
	    <span class="systemLogo"></span> 
	    <form action="${base}/ponycms/admin/login.do" method="post">
		    <div class="loginBox">
			    <ul>
				    <li><input name="j_username" value="admin" type="text" class="loginUser"/></li>
				    <li><input name="j_password" value="admin" type="password" class="loginPwd"/></li>
				    <li><input type="submit" class="loginBtn" value="登录"/>&nbsp;&nbsp;<span class="error">${message?default("")}</span></li>
			    </ul>
		    </div>
	    </form>
    </div>
</body>
</html>