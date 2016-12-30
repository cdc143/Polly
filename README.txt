Polynomial Parser v1.0

Compile:
cd to poly directory and run make
If you want to recompile the program or trash the generated class files, run make clean

Execute:
cd to the poly directory and run the Driver with the appropriate arguments (java Driver [args])

Arguments:
If no arguments are given, the program executes in interactive mode. Prompts user for input and echos the
output to terminal in a continuous loop until the user presses Ctrl-C

If one argument is given, it is interpreted as a filename which will try to be opened by the program.
The program will then write its output to a file named as [input file name].out

Third party libraries:
Uses the xjep library to easily parse and simplify polynomials. This was simply much easier than constructing my own
grammar and hand written parser
Standard java libraries for manipulated strings and using basic java data structures (ie ArrayList)

Included is a file which demonstrates some sample inputs that test the programs functionality. The sample
inputs and what they are intended to test is as follows:

Test                            Purpose                                                   Output
0=0                             Simple test with no simplification needed                 
x^2+2x+3=3x-1                   Basic polynomial test                                     
x^2                             Invalid expression                                        
x^3+xy-3=0                      Polynomial that should just be echoed                     
x-(0-x)=0                       Test of distibutivity of -                                
x^2-2x(=9)                      Invalid placement of ()                                   
x^2+x+1=y-z                     Variables that occur on the right don't occur on the left  
x-(0-(0-x))=0                   More complex distributivity test                          
(8-x=y                          Lacking )                                               
5.5x^4-y^5+z^7=z^7+5.5x^4-y^5   Test for numbers with decimals     
