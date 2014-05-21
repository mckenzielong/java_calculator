
import calculator.SyntaxTreeException;
import java.util.StringTokenizer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Little suite of unit tests for buildSyntaxTree using JUnit 4. Tried to exhaustively bad
 * formatting cases, and other issues with variables, and arithmetic issues.
 *
 * While not a java package, I used JUnit.  JUnit might go against the assignment rules, but I used 
 * this to help test my application, so it will be included in this package.
 * 
 * Also runs the examples given in the assignment document.
 *
 * @author McKenzie Long
 */
public class CalculatorJUnitTest {

   public CalculatorJUnitTest() {
   }

   @BeforeClass
   public static void setUpClass() {
   }

   @AfterClass
   public static void tearDownClass() {
   }

   @Before
   public void setUp() {
   }

   @After
   public void tearDown() {
   }

   //Failing cases
   //test missing right bracket
   @Test(expected = SyntaxTreeException.class)
   public void testMissingRightBracket() throws SyntaxTreeException {
      String testString = "add(4,5";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test massing left bracket
   @Test(expected = SyntaxTreeException.class)
   public void testMissingLeftBracket() throws SyntaxTreeException {
      String testString = "add(letx,3,3)4,5)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test an illformatted LROperation, with 3 elements
   @Test(expected = SyntaxTreeException.class)
   public void testBadLROperation() throws SyntaxTreeException {
      String testString = "add(4,3,3)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test missing right operand in LR expression
   @Test(expected = SyntaxTreeException.class)
   public void testBadLROperation2() throws SyntaxTreeException {
      String testString = "add(3,)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test missing left operand in LR expression
   @Test(expected = SyntaxTreeException.class)
   public void testBadLROperation3() throws SyntaxTreeException {
      String testString = "add(,2)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test non defined variable being used
   @Test(expected = SyntaxTreeException.class)
   public void testNonExistentVariable() throws SyntaxTreeException {
      String testString = "add(3,add(5,batman)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test variable being redefined
   @Test(expected = SyntaxTreeException.class)
   public void testRedefinedVariable() throws SyntaxTreeException {
      String testString = "add(3,let(batman,4,let(batman,1,1)))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test let expression missing expression
   @Test(expected = SyntaxTreeException.class)
   public void testBadLetOperation() throws SyntaxTreeException {
      String testString = "let(batman,4,)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test let expression missing variable value
   @Test(expected = SyntaxTreeException.class)
   public void testBadLetOperation2() throws SyntaxTreeException {
      String testString = "let(batman,,4)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test let expression missing variable name
   @Test(expected = SyntaxTreeException.class)
   public void testBadLetOperation3() throws SyntaxTreeException {
      String testString = "let(,4,4)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test let expression integer instead of variable
   @Test(expected = SyntaxTreeException.class)
   public void testBadLetOperation4() throws SyntaxTreeException {
      String testString = "let(5,4,4)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test let expression LRoperation instead of variable
   @Test(expected = SyntaxTreeException.class)
   public void testBadLetOperation5() throws SyntaxTreeException {
      String testString = "let(add(1,1),4,4)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test let expression integer instead of variable
   @Test(expected = IllegalArgumentException.class)
   public void testBadVariableName() throws SyntaxTreeException {
      String testString = "let(sdafdsa!,4,4)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test using a variable out of scope
   @Test(expected = SyntaxTreeException.class)
   public void testVariableOutOfScope() throws SyntaxTreeException {
      String testString = "let(x,let(z,2,z),add(z,x))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test using a variable that isn't created as the value
   @Test(expected = SyntaxTreeException.class)
   public void testVariableUseInValue() throws SyntaxTreeException {
      String testString = "let(x,add(x,2),add(z,x))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test a terminator as a root node
   @Test(expected = SyntaxTreeException.class)
   public void testBadRootNode() throws SyntaxTreeException {
      String testString = "(let(add(1,1),4,4))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test integer as root node
   public void testBadRootNode2() throws SyntaxTreeException {
      String testString = "5(let(add(1,1),4,4))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test variable as root node
   public void testBadRootNode3() throws SyntaxTreeException {
      String testString = "huzzah(let(add(1,1),4,4))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test a ridiculously large number
   @Test(expected = NumberFormatException.class)
   public void testBadInteger() throws SyntaxTreeException {
      String testString = "add(213123123123123123123123123123123211,1)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens);
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test simple add
   @Test()
   public void testAdd() throws SyntaxTreeException {
      String testString = "add(41,1)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("41 + 1 = 42", 42, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test negative numbers
   @Test()
   public void testNegativeNumbers() throws SyntaxTreeException {
      String testString = "add(-41,1)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("-41 + 1 = -40", -40, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test simple subtract
   @Test()
   public void testSub() throws SyntaxTreeException {
      String testString = "sub(43,1)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("43 - 1 = 42", 42, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test simple subtract, negative answer
   @Test()
   public void testSub2() throws SyntaxTreeException {
      String testString = "sub(1,43)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("1 - 43 = 42", -42, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test multiply
   @Test()
   public void testMul() throws SyntaxTreeException {
      String testString = "mult(2,21)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("2 * 21 = 42", 42, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test divide
   @Test()
   public void testDiv() throws SyntaxTreeException {
      String testString = "div(9,3)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("9 / 3 = 3", 3, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test dividing by zero
   @Test(expected = ArithmeticException.class)
   public void testDivByZero() throws SyntaxTreeException {
      String testString = "div(9,0)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         calculator.Calculator.buildSyntaxTree(tokens).evaluate();
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test simple variable assignment
   @Test()
   public void testSimpleLet() throws SyntaxTreeException {
      String testString = "let(x,42,x)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("x=42; x is 42", 42, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test variable assignment with a nested operation value
   @Test()
   public void testLetNestedValue() throws SyntaxTreeException {
      String testString = "let(x,mult(21,2),x)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("x=21*2; x is 42", 42, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test variable assignment with a nested operation value and expression
   @Test()
   public void testLetNestedExpression() throws SyntaxTreeException {
      String testString = "let(x,mult(21,1),add(x,x))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("x=21*1; x+x; is 42", 42, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test variable assignment with a nested let operation value
   @Test()
   public void testLetNestedLetValue() throws SyntaxTreeException {
      String testString = "let(x,let(z,mult(21,1),z),add(x,x))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("x=21*1; x+x; is 42", 42, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test variable assignment with a nested let operation value and expression
   @Test()
   public void testLetNestedLetExpression() throws SyntaxTreeException {
      String testString = "let(x,let(z,mult(21,1),z),let(z,add(x,x),z))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("x=z, z=21*1; z=x+x, z; is 42", 42, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test just integer
   @Test()
   public void testInteger() throws SyntaxTreeException {
      String testString = "42";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("42", 42, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test assignment example
   @Test()
   public void testExample1() throws SyntaxTreeException {
      String testString = "add(1,2)";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("1+2 is 3", 3, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test assignment example
   @Test()
   public void testExample2() throws SyntaxTreeException {
      String testString = "add(1,mult(2,3))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("1+(2*3) is 7", 7, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test assignment example
   @Test()
   public void testExample3() throws SyntaxTreeException {
      String testString = "mult(add(2,2),div(9,3))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("(2+2) * (9/3) is 12", 12, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test assignment example
   @Test()
   public void testExample4() throws SyntaxTreeException {
      String testString = "let(a,5,add(a,a))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("x=5; x+x is 10", 10, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test assignment example
   @Test()
   public void testExample5() throws SyntaxTreeException {
      String testString = "let(a,5,let(b,mult(a,10),add(b,a)))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("a=5; b = (a*10); b + a is 55", 55, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

   //test assignment example
   @Test()
   public void testExample6() throws SyntaxTreeException {
      String testString = "let(a,let(b,10,add(b,b)),let(b,20,add(a,b)))";
      StringTokenizer tokens = new StringTokenizer(testString, "\\(|\\)|,", true);
      try {
         assertEquals("a = (b = 10; b+b); b=20; a + b is 40", 40, calculator.Calculator.buildSyntaxTree(tokens).evaluate());
      } catch (SyntaxTreeException e) {
         System.out.println(e.getMessage());
         throw e;
      }
   }

}
