package parsed_result

import class_property.ClassPropertySGP

data class ParsedResult(
    val className: String,
    val classProperties: List<ClassPropertySGP>
)
