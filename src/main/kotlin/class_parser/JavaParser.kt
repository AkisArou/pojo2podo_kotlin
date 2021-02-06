package class_parser

import class_property.ClassPropertySGP
import class_property.JavaProperty
import parsed_result.ParsedResult

class JavaParser : ClassParser() {
    override fun isComment(line: String): Boolean {
        return line.trim().startsWith("/") || line.trim().startsWith("*")
    }

    override fun isClassNameContainingLine(line: String): Boolean {
        return Regex("class\\s(\\w+)").containsMatchIn(line)
    }

    override fun isStartingBlock(line: String): Boolean {
        return line.endsWith("{") || line.startsWith("}")
    }

    override fun isEndingBlock(line: String): Boolean {
        return blockCount >= 1 && (line.startsWith("}") || line.endsWith("}"))
    }

    override fun isEmptyLine(line: String): Boolean {
        return line.isEmpty() || line == " "
    }

    override fun isMethod(line: String): Boolean {
        return Regex("\\(([^)]+)\\)").containsMatchIn(line)
    }

    override fun isMethodAnnotation(line: String): Boolean {
        return line.trim().startsWith("@")
    }

    override fun getClassName(classNameContainingLine: String): String {
        val splat = classNameContainingLine.split("class")[1]
        return splat.split(" ")[1]
    }

    override fun getClassProperties(rawProps: List<String>): List<ClassPropertySGP> {
        val cp: MutableList<ClassPropertySGP> = mutableListOf()

        for (prop in rawProps) {
            if (prop.contains(",")) {
                val pProps = mutableListOf<String>()
                var defaultValue = ""


                val multiProps = prop.split(",").toMutableList()
                val multiPropsLen = multiProps.size
                val indexProp = multiProps[0].split(" ")
                val lastProp = multiProps[multiPropsLen - 1]
                val defaultValueIdx = lastProp.indexOf("=")

                if (defaultValueIdx > -1) {
                    defaultValue = lastProp.substring(defaultValueIdx, lastProp.length)
                    multiProps[multiPropsLen - 1] = lastProp
                        .replaceRange(defaultValueIdx, defaultValue.length, "")
                }

                val propAccType = indexProp.joinToString(" ")
                pProps.add("${multiProps[0]} $defaultValue")

                for (i in 1 until multiPropsLen)
                    pProps.add("$propAccType ${multiProps[i].trim()} $defaultValue")

                for (s in pProps)
                    cp.add(parseRawProperty(s))
            } else {
                cp.add(parseRawProperty(prop))
            }
        }

        return cp
    }

    override fun parseRawProperty(propertyRaw: String): ClassPropertySGP {
        val javaPropUnparsed = propertyRaw.trim().replace(";", "")

        val parts = javaPropUnparsed.split(" ").toMutableList()

        if (parts[0] != JavaProperty.PROTECTED.accessorName &&
            parts[0] != JavaProperty.PUBLIC.accessorName &&
            parts[0] != JavaProperty.PRIVATE.accessorName
        )
            parts.add(0, JavaProperty.PUBLIC.accessorName)

        val javaProp = JavaProperty()
        javaProp.setPropName(parts[2])
        javaProp.setPropType(parts[1])
        javaProp.setPropAccessorFromString(parts[0])

        val hasDefaultValue = javaPropUnparsed.indexOf("=") > -1

        if (hasDefaultValue) {
            javaProp.setDefaultVal(parts.last())
        } else {
            javaProp.setDefaultVal("")
        }

        return javaProp
    }

    override fun parse(classString: String): ParsedResult {
        val classBodyLines = classString.split("\n")
        val classProperties = mutableListOf<String>()
        var classNameContainingLine = ""

        for (line: String in classBodyLines) {
            if (isComment(line) || isMethodAnnotation(line) || blockCount >= 1 || isEmptyLine(line) || isMethod(line))
                continue
            else if (isClassNameContainingLine(line))
                classNameContainingLine = line
            else if (isStartingBlock(line))
                addBlockCount()
            else if (isEndingBlock(line))
                subBlockCount()
            else
                classProperties.add(line)
        }

        return ParsedResult(
            getClassName(classNameContainingLine),
            getClassProperties(classProperties)
        )
    }
}