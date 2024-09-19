var ${entity?uncap_first}Config = [
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    {
    prop:"${field.propertyName}",
    label:"${field.comment}"
    }<#if field_has_next>,</#if>
</#list>
]
export default ${entity?uncap_first}Config
