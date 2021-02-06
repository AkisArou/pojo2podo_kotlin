import class_builder.DartClassBuilder
import class_parser.JavaParser

fun main(args: Array<String>) {
//    val mockDartFileName = "mockDart.dart"
//    val mockJavaFileName = "mockJava.java"
    val mockJavaClass = """     
//Random comment
public class MyBean {
    private String first, second, third = "value";
    private String someProperty;
    public int someProperty2;
    protected String someProperty3;
    int someInt;

    @Annotated
    public String getSomeProperty() {
        return someProperty;
    }

    public void setSomeProperty(String someProperty) {
        this.someProperty = someProperty;
    }

    public static void someStatic() {
        return null;
    }
}
""".trimIndent()

    val jp = JavaParser()
    val dcb = DartClassBuilder()

    val result = jp.parse(mockJavaClass)
    val dc = dcb.build(result)
    println(dc)
}