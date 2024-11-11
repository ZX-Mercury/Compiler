# 关于编译器 IR 阶段的一些讨论

## 11月4日
1. for 循环块的结尾应该要跳到哪里？
- T. 应该回到 condition 对应的 block。

2. 当 body 中有 break 或者 continue 的时候，应该怎么处理？  
   T. 一者，对于 break ，原本循环的 condition 判断为假的跳转目标对应的 block 就是 break 跳转的目标；<br>
   二者，对于 continue ，跳回到 condition；

3. atomExprssion的this指针应该如何处理？  
T. TODO。

## 11月5日
1. 我想要先完成 “int main(){return 0;}” 的编译，应该如何开始？  
A. 先完成 “function define node” 的设计，然后参考下问。
---
2. return a+b; 这种表达式的处理？  
A. 先把 expression 的值存在一个临时寄存器（% + 数字），然后 “ret 寄存器” 。
---
3. 如何给变量命名，原名+特殊符号+某个全局数字是个合理的方案吗？似乎可行，那这个全局数字该怎么选取呢？  
A. 给变量命名，最重要的有两点：一者，要保证不会重名；二者，要保证在用到已命名变量时能够找到。  
所以，第一点，在函数所定义的 node 中，要记录一个数字，表示当前函数中的变量个数，
然后在命名时，用这个数字附在后面来命名。第二点，要把命名的变量记录在一个 map 中，这样在用到变量时，可以通过名字找到对应的寄存器。
（semantic checker 中 scope 和它很相似）
---
4. IR Builder 和 Printer 应该分开还是杂糅呢？似乎很多人都是把二者放在一起的……  
A. 分开可，不分开亦可。分开更分明；不分开，用 toString 函数即可。
---
5. Type 以及 value 的设计似乎还不太完备，感觉有点无从下手……  
A. ?
---

## 11月6日
1. phi 指令一般用在什么地方？除了文档举例的循环外，还有其他地方吗？
- T. ~~也许三目运算终会用到？~~ 应该不会，用 select 足矣。
- A. 在 codegen 阶段，可以完全避开 phi 指令，而使用 alloca 指令。
---
2. 
> 昨天的 “Builder 和 Printer” 问题其实在文档中有回答：
>> 虽然本章中所有的 LLVM IR 的例子都是字符串形式的，但是在实际编写代码的时候不需要转换为字符串，
而是应该转换为 LLVM IR 的文字形式对应的节点。
>
> 所以我选择把二者分离。

在 function 对应的类里，要包含哪些东西？函数返回类型、函数名、参数列表、
以及函数所含的若干 block。这完备了吗？
（参考：src.MIR.IREntity.function）

T. 似乎还需要一个 map，记录变量名和寄存器的对应关系。

---
3. 上一个问题是针对 function 的类，那在 builder 里对应的visit function应该包含哪些东西？  
我目前加一个boolean isMemberFunction和指示函数是否结束的endBlock，完备了吗？
（参考：src.MIR.IRBuilder 中的public void visit(funcDefNode it)）

T. 似乎足够了。

---
4.  
       class A {
         int a;
         bool m;
       };
会被转化为（By godbolt.org）

    %class.A = type <{ i32, i8, [3 x i8] }>
“\[3 x i8]”的位置（第一个还是最后一个）有讲究吗？

A. 没什么讲究。

---
5. 遇到 continue，后面的内容**在本阶段**保留还是直接优化掉？
- T. 在 “continue 是在循环中的条件语句里”这种情况下，后面在条件分支外的东西还是有必要保留，可能吧……？

## 11月7日
took a break

## 11月8日
1. 在 godbolt 在线转换网站上，把下面代码转为 LLVM，

       int f(int x){
         return x+1;
       }

       int main(){
         int i=3;
         int j=f(i);
         return j;
       }
得到

       define i32 @f(int)(i32 %0) {
         %2 = alloca i32
         store i32 %0, ptr %2              // *%2 = x
         %3 = load i32, ptr %2             // %3 = *%2 = x
         %4 = add i32 %3, 1                // %4 = %3 + 1
         ret i32 %4
       }
   
       define i32 @main() {
         %1 = alloca i32
         %2 = alloca i32
         %3 = alloca i32
         store i32 0, ptr %1
         store i32 3, ptr %2
         %4 = load i32, ptr %2
         %5 = call i32 @f(int)(i32 %4)
         store i32 %5, ptr %3
         %6 = load i32, ptr %3
         ret i32 %6
       }
main 函数里的 %1 并没有真正参与运算，它有什么作用？

A. 没什么作用。

---
2. Instruction 这个类（以及它派生出的 branchInst、callInst 等类）的定位究竟是什么？
具体而言，只要保证一个 Instruction 类能够转化出 LLVM IR 中的一个指令（或者一个字符串形式的指令），就可以了吗？

A. 确实如此，没有检查正确性的必要，也没必要记录或推断其他信息。

---