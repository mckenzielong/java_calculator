java_calculator
===============

Simple calculator written in java, with some basic error checking for syntax / other errors.

Valid variable names are [a-zA-Z], and namespace collisions currently are not allowed.

**Valid Expressions:**
 * add(expression, expression)
 * sub(expression, expression)
 * mult(expression, expression)
 * div(expression, expression)
 * let(name, expression, expression)

**Potential error messages:**
 * *Variable name: name has not been declared at scope: #-#*
 * *Variable name: name has already been declared at scope: #-#* 
 * *There is # unclosed brackets.* means an operation is incomplete.
 * *Comma separator already set for class class name at scope level: #*
 * *Left bracket already set for class name at scope level: #* means we recieved a '(' token at an unexpected time.
 * *class is not valid at scope level: #* means isValidSyntax check failed 
 * *Variable for class name at scope: # is expected to be set, but is not.* means no variable name given for let operation.
 * *class calculator.LetOperation at scope: # is not correctly formatted.* means let operation is missing second seperator.
 * *Unexpected parent case encountered at scope level: #* means we pushed a bad parent onto the stack.  This can be from an improperly formed expression.
