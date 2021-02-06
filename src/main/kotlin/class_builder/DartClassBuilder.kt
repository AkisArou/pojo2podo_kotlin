package class_builder

import class_property.DartProperty
import parsed_result.ParsedResult


//func (d *DartClassBuilder) Build(result *ParsedResult) string {
//    var dartProperties string
//    var dartConstructorProperties string
//
//    for _, prop := range result.classProperties {
//        dp := NewDartProperty(prop)
//        dps := dp.GetPropertyString(false)
//        dartProperties += "\t" + dps + "\n"
//        dartConstructorProperties += "\t\t" + requiredAnnotation + " " + dp.GetPropertyString(true) + ",\n"
//    }
//    dartConstructorProperties = strings.TrimSpace(dartConstructorProperties)
//
//    dartClass := fmt.Sprintf(`
//    %s %s {
//        %s
//        %s({
//        %s
//    })
//    }
//    `, classKeyword, result.className, dartProperties, result.className, dartConstructorProperties)
//    return dartClass
//
//}

class DartClassBuilder: ClassBuilder {
    override fun build(result: ParsedResult): String {
        var dartProperties = ""
        var dartConstructorProperties = ""

        for(prop in result.classProperties) {
            val dp = DartProperty()
            val dps = dp.getPropertyString(false)
            dartProperties += "\t $dps \n"
            dartConstructorProperties += "$\t\t @required ${dp.getPropertyString(true)},\n"
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