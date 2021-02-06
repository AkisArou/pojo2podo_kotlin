package class_builder

import class_property.DartProperty
import parsed_result.ParsedResult


class DartClassBuilder : ClassBuilder {
    override fun build(result: ParsedResult): String {
        var dartProperties = ""
        var dartConstructorProperties = ""

        for (prop in result.classProperties) {
            val dp = DartProperty.fromPropertyGetter(prop)
            val dps = dp.getPropertyString(false)
            dartProperties += "\t$dps \n"
            dartConstructorProperties += "\t\t@required ${dp.getPropertyString(true)},\n"
        }

        dartConstructorProperties = dartConstructorProperties.trim()

        return """
class ${result.className} {
$dartProperties
            
    ${result.className}({
        $dartConstructorProperties
    })
}
""".trimIndent()
    }
}