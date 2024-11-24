package com.craftinginterpreters.lox;


//    @Override
//    public String visit(NPR)
//这里可以写一个逆波兰表达式语法树解析类，通过改变Visitor接口中的方法
//class RpnVisitor implements Expr.Visitor<String> {
//    @Override
//    public String visitBinaryExpr(Expr.Binary expr) {
//        String left = expr.left.accept(this);
//        String right = expr.right.accept(this);
//        return left + " " + right + " " + expr.operator.lexeme;
//    }
//
//    @Override
//    public String visitGroupingExpr(Expr.Grouping expr) {
//        return expr.expression.accept(this);
//    }
//
//    @Override
//    public String visitLiteralExpr(Expr.Literal expr) {
//        if (expr.value == null) return "nil";
//        return expr.value.toString();
//    }
//
//    @Override
//    public String visitUnaryExpr(Expr.Unary expr) {
//        String right = expr.right.accept(this);
//        return right + " " + expr.operator.lexeme;
//    }
//}

class AstPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }//这里的this是AstPrinter,为什么
    //this是当前对象实例

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();
         builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");
        return builder.toString();
    }
    public static void main(String[] args) {
        Expr expression = new Expr.Binary(
                new Expr.Unary(
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Literal(123)
                ),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Grouping(new Expr.Literal(45.67)
                )
        );
        System.out.println(new AstPrinter().print(expression));
        //通过定义不同的接受者，来使表达式有不同的解析方法
        //通过改变不同接受者中的Visitor接口
        //如果我们想要逆波兰表达式的解析方法，我们应该定义一个新的visitor,分别实现不同的那四个Visitor接口
    }
}

/*visitBinaryExpr 方法：
这个方法处理二元运算符表达式，例如加法（+）、减法（-）等。
expr.operator.lexeme 获取运算符的文本表示，如 "+" 或 "-"。
expr.left 和 expr.right 分别代表左操作数和右操作数。
parenthesize 函数用于将运算符和操作数括起来，形成一个格式化的字符串输出，比如 (operator left right)。
*/

/*
visitGroupingExpr 方法：
处理分组表达式，通常是为了改变运算符优先级，例如 (a + b)。
        "group" 是一个标识符，用来表示这是一个分组表达式。
expr.expression 是被分组的表达式。
同样地，使用 parenthesize 函数来构建一个格式化的字符串，例如 (group expression)。
*/

/*
visitLiteralExpr 方法：
处理字面量表达式，比如数字 42 或字符串 "hello"。
如果 expr.value 为 null，则返回字符串 "nil"，这可能用来表示空值。
否则，直接返回字面量的字符串表示形式。
*/

/*
visitUnaryExpr 方法：
处理一元运算符表达式，如负号 - 或逻辑非 !。
expr.operator.lexeme 获取运算符的文本表示。
expr.right 表示该运算符的操作数。
使用 parenthesize 函数来创建一个包含运算符和操作数的字符串，例如 (operator operand)。
 */