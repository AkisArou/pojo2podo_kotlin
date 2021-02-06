package class_property

abstract class ClassProperty(
    protected var name: String,
    protected var type: String,
    protected var accessor: Accessor,
    protected var defaultValue: String
) {
}

enum class PropertyAccessorSemantics {
    PRIVATE,
    PUBLIC,
    PROTECTED
}

data class Accessor(val accessorName: String, val accessorSemantics: PropertyAccessorSemantics)

interface ClassPropertySetter {
    fun setPropAccessorFromString(accStr: String)
    fun setDefaultVal(defaultValue: String)
    fun setPropName(name: String)
    fun setPropType(type: String)
}

//TODO String return value to ClassPropertyGetter<Enum>
interface ClassPropertyGetter {
    fun fetchAccessorName(): String
    fun fetchDefaultVal(): String
    fun fetchName(): String
    fun fetchPropType(): String
}

interface ClassPropertyResultProvider {
    //TODO isForConstructor
    fun getPropertyString(isForConstructor: Boolean): String
}


interface ClassPropertySGP : ClassPropertySetter, ClassPropertyGetter, ClassPropertyResultProvider;