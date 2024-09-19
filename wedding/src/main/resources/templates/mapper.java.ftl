package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
* <p>
* ${table.comment!} Mapper 接口
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
