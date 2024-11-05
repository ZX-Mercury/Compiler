# 关于编译器 IR 阶段的一些讨论

## 11月4日
- 1. for 循环块的结尾应该要跳到哪里？
- - T. 应该回到 condition 对应的 block:
aaa

- 2. 当body中有break或者continue的时候，应该怎么处理？
- - T. 1.对于break，原本循环的condition判断为假的跳转目标对应的block就是break跳转的目标；

- - 2.对于continue，跳回到condition；

- 3. atomExprssion的this指针应该如何处理？
- - T. TODO。

## 11月5日
- 1. 我想要先完成“int main(){return 0;}”的编译，应该如何开始？

- 2. 如何给变量命名，原名+特殊符号+某个全局数字是个合理的方案吗？

- 3. return a+b; 这种表达式的处理？

- 4. IR Builder 和 Printer 应该分开还是杂糅呢？似乎很多人都是吧二者放在一起的。。

- 5. Type 以及 value 的设计似乎还不太完备，感觉有点无从下手。。
