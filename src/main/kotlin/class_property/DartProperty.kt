package class_property

class DartProperty : ClassProperty(), ClassPropertySGP {
    companion object Accessors {
        val PRIVATE = Accessor("_", PropertyAccessorSemantics.PRIVATE)
        val PUBLIC = Accessor("", PropertyAccessorSemantics.PUBLIC)
        val PROTECTED = Accessor("@protected", PropertyAccessorSemantics.PROTECTED)
    }

    override fun setPropAccessorFromString(accStr: String) {
        when (accStr) {
            PUBLIC.accessorName -> accessor = PUBLIC
            PRIVATE.accessorName -> accessor = PRIVATE
            PROTECTED.accessorName -> accessor = PROTECTED
        }
    }

    override fun setDefaultVal(defaultValue: String) {
        this.defaultValue = defaultValue
    }

    override fun setPropName(name: String) {
        this.name = name
    }

    override fun setPropType(type: String) {
        this.type = type
    }

    override fun fetchAccessorName(): String {
        return accessor.accessorName
    }

    override fun fetchDefaultVal(): String {
        return defaultValue
    }

    override fun fetchName(): String {
        return name
    }

    override fun fetchPropType(): String {
        return type
    }

    override fun getPropertyString(isForConstructor: Boolean): String {
        var propertyString = ""

        if (accessor == PROTECTED && !isForConstructor)
            propertyString += accessor.accessorName + "\n\t"

        if (accessor == PRIVATE)
            propertyString += "$type ${accessor.accessorName}$name"
        else
            propertyString += "$type $name"

        if (defaultValue.isNotEmpty())
            propertyString += " =$defaultValue"

        if (!isForConstructor) {
            propertyString += ";"
        }

        return propertyString

    }
}