package class_builder

import parsed_result.ParsedResult

interface ClassBuilder {
    fun build(result: ParsedResult): String
}