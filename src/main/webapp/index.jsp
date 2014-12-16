<%@ page language="java" pageEncoding="utf-8"%>
<%
	String base = request.getContextPath();
	//暂时未做前台内容，默认重定向至管理首页
	response.sendRedirect(base + "/ponycms/admin/main.do");
	
%>