package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    //我们把这个字段设置为静态的，这样在一个 REPL 会话中连续调用 run() 时就会重复 使用同一个解释器。
    // 目前这一点没有什么区别，但以后当解释器需要存储全局变量时就 会有区别。
    // 这些全局变量应该在整个 REPL 会话中持续存在。
    private static final Interpreter interpreter = new Interpreter();

    static boolean hadError = false;
    //如果用户从文件中运行 Lox 脚本，并且发生了运行时错误，我们在进程退出时设置 一个退出码，以便让调用进程知道。不是每个人都在乎 shell 的规矩，但我们在乎。
    static boolean hadRuntimeError = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            shut
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError) System.exit(65);
        if (hadRuntimeError) System.exit(70);
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        for (; ; ) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hadError = false;
        }
    }

    private static void run(String source) {
        //错误退出代码
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens(); // For now, just print the tokens.
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();
        // Stop if there was a syntax error.
        if (hadError) return;
        interpreter.interpret(statements);
    }

    static void error(int line, String message) {
        report(line, "", message);
    }

    static void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }

    static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() + "\n[line " + error.token.line + "]");
        hadRuntimeError = true;
    }

    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}