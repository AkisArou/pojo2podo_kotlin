package class_parser

import class_property.ClassPropertySGP
import parsed_result.ParsedResult

abstract class ClassParser {
    var blockCount: Int = 0

    fun addBlockCount() {
        blockCount += 1
    }

    fun subBlockCount() {
        blockCount -= 1
    }

    protected abstract fun isComment(line: String): Boolean
    protected abstract fun isClassNameContainingLine(line: String): Boolean
    protected abstract fun isStartingBlock(line: String): Boolean
    protected abstract fun isEndingBlock(line: String): Boolean
    protected abstract fun isEmptyLine(line: String): Boolean
    protected abstract fun isMethod(line: String): Boolean
    protected abstract fun isMethodAnnotation(line: String): Boolean

    protected abstract fun getClassName(classNameContainingLine: String): String
    protected abstract fun getClassProperties(rawProps: List<String>): List<ClassPropertySGP>
    protected abstract fun parseRawProperty(propertyRaw: String): ClassPropertySGP
    abstract fun parse(classString: String): ParsedResult
}