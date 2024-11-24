//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf(test("hello,world,java",1,false));
    }
    static <R, G, B> String test (R param, G param2, B param3){
        return param.toString()+"  "+param2.toString()+"  "+param3.toString();
    }
}