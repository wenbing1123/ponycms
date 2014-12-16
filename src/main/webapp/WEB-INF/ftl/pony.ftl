<#ftl strip_whitespace=true>

<#global shiro = JspTaglibs["/WEB-INF/tld/shiro.tld"]/>

<#macro message code>${springMacroRequestContext.getMessage(code)}</#macro>
<#macro messageText code, text>${springMacroRequestContext.getMessage(code, text)}</#macro>
<#macro messageArgs code, args>${springMacroRequestContext.getMessage(code, args)}</#macro>
<#macro messageArgsText code, args, text>${springMacroRequestContext.getMessage(code, args, text)}</#macro>