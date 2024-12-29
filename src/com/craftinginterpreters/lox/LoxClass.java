package com.craftinginterpreters.lox;

import java.util.List;
import java.util.Map;


/*
我们有实例上的方法，但是没有办法定义可以直接在类对象上调用的“静态”方法。
添加对它们的支持，在方法之前使用 class 关键字指示该方法是一个挂载在类对象 上的静态方法。
  class Math {
      class square(n){
      return n * n;
      }
   }
  print Math.square(3); // Prints "9".
你可以用你喜欢的方式解决这问题，但是 Smalltalk 和 Ruby 使用的“metaclasses” 是一种特别优雅的方法。
提示：让 LoxClass 继承 LoxInstance，然后开始实现。
*/
class LoxClass implements LoxCallable {
    final String name;
    final LoxClass superclass;

    private final Map<String, LoxFunction> methods;

    LoxClass(String name, LoxClass superclass, Map<String, LoxFunction> methods) {
        this.superclass = superclass;
        this.name = name;
        this.methods = methods;
    }

    LoxFunction findMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }
        if (superclass != null) {
            return superclass.findMethod(name);
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        LoxInstance instance = new LoxInstance(this);
        LoxFunction initializer = findMethod("init");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }
        return instance;
    }

    @Override
    public int arity() {
        LoxFunction initializer = findMethod("init");
        if (initializer == null) return 0;
        return initializer.arity();
    }
    //所期望 的参数数量
}