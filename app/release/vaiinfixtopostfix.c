#include <stdio.h>
int stack[20], top=-1;
void push (int x)
{
    top++;
    stack[top]=x;
 }
 int pop()
{
     return stack[top--];
   }
 int main() {
 
      char expression[20];
      printf("Enter the expression");
      scanf("%s",expression);
     
      int i =0;
      char symbol=expression[i];
      int operand1, operand2, result;
     
      while (symbol !='\0') {
         if (symbol >='0' && symbol <='9') {
             int num =symbol -'0';
             push(num);
    }
    else if (symbol == '+' || symbol == '-' || symbol =='*' || symbol == '/')
    {
      operand2=pop();
      operand1=pop();
      switch(symbol) {
         case '+':result= operand1 + operand2; break;
         case '-':result= operand1 - operand2; break;
         case '*':result= operand1 * operand2; break;
         case '/':result= operand1 / operand2; break;
         }
         push(result);
       }
 i++;
       symbol = expression[i];
     }
 printf("The result is %d",pop());
 return 0;
    }
			
			
