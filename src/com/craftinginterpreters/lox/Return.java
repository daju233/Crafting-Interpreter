package com.craftinginterpreters.lox;

class Return extends RuntimeException {
    final Object value;

    Return(Object value) {
        //这个类使用 Java 运行时异常类来封装返回值。其中那个奇怪的带有 null 和 false 的 父类构造器方法，禁用了一些我们不需要的 JVM 机制。
        // 因为我们只是使用该异常类来控 制流，而不是真正的错误处理，所以我们不需要像堆栈跟踪这样的开销。
        super(null, null, false, false);
        this.value = value;
    }
}