import org.nfunk.jep.*;
import org.lsmp.djep.xjep.*;
import org.lsmp.djep.sjep.*;
import java.util.ArrayList;
import java.util.Arrays;
public class PolyParser  {

  //Evaluates the passed in string as an expression, and returns the result in the form expr=0
  public String evaluateExpr(String expr) {
    String result="";
    try {
      String [] exprs = expr.split("="); //Split string into left and right hand expressions
      String expr1 = exprs[0];
      String expr2 = exprs[1];
      expr1 = simplifyExpr(expr1); //Simplify both halves
      expr2 = simplifyExpr(expr2);

      String [] terms1 = expr1.split("\\+"); //Collect terms from both halves
      String [] terms2 = expr2.split("\\+");
      ArrayList<Double> coffs1 = getCoffs(terms1); //Collect vars and coffs from each half
      ArrayList<String> vars1 = getVars(terms1);
      ArrayList<Double> coffs2 = getCoffs(terms2);
      ArrayList<String> vars2 = getVars(terms2);
      for (int i = 0; i<vars2.size();i++){ //For every variable on the right hand side
        int j = vars1.indexOf(vars2.get(i)); //See if its in the left hand list
        if(j == -1){ //IIf not, add it and its corresponding coff*-1 to the left list
          vars1.add(vars2.get(i));
          coffs1.add(coffs2.get(i)*-1);
        }
        else { //Else, just do the math and adjust the coff in the left hand list
          coffs1.set(j,(coffs1.get(j)-coffs2.get(i)));
        }
      }
      for (int i = 0; i<vars1.size(); i++){

      }
    for (int i = 0; i<coffs1.size(); i++){ //Prepare the string for printing
      result = result +coffs1.get(i)+vars1.get(i)+"+";
    }
    result = result.substring(0, result.length() - 1) + "";
    result = simplifyExpr(result);
    if(result == null){
      return "Error parsing expression. Please validate that the expression is correct and try again";
    }
    result = result.replaceAll("\\+-","-"); //Cleaning up the output
    result = result.replaceAll("\\*","");
    result = result.replaceAll("-0.0","0.0");
    result = result.replaceAll("0.0\\+","");
    result = result.substring(0, result.length()) + "=0";

    return result;
    }
    //If any errors occur, simply write that the expression could not be parsed properly
    catch (NullPointerException | ArrayIndexOutOfBoundsException | ParseException e){
      return "Error parsing expression. Please validate that the expression is correct and try again";
    }
  }

//Parses an array of terms and produces a list with all of its variables
  private ArrayList<Double> getCoffs (String [] terms) {
    ArrayList<Double> coffs = new ArrayList <Double>();
    Double coff = null;
    for(String term : terms) {
      String [] factors = term.split("\\*"); //Split apart each leading number and its variable
      if(factors.length==1) {
        try{ //If a number only, add it to the list
          coff=Double.parseDouble(factors[0]);
          coffs.add(coff);
        }
        catch(NullPointerException | NumberFormatException e){ //If var only, add a coff of 1.0 to the list
          coff = 1.0;
          coffs.add(coff);
        }
      }
      else{
        try{ //Else, coff is always the first element in any term
        coff = Double.parseDouble(factors[0]);
        coffs.add(coff);
      }
      catch(NullPointerException | NumberFormatException e){} //If error here, there was an error parsing
      }
    }

    return coffs;
  }

//Parses an array of terms and produces a list with all of its variables
  private ArrayList<String> getVars (String [] terms){
    ArrayList<String> vars = new ArrayList <String>();
    String var = null;
    for(String term : terms){
      String [] factors = term.split("\\*"); //Split apart each leading number and its variable
      if(factors.length==1){
        try{ //If a number only, set its corresponding variable to empty
          Double.parseDouble(factors[0]);
          vars.add("");
        }
        catch(NumberFormatException | NullPointerException e){ //Else, add the variable
          var = factors[0];
          for(int i =0; i<vars.size(); i++){
              if(checkAnagram(var,vars.get(i))){
                  var = vars.get(i);
                  break;
              }
          }
          vars.add(var);
        }
      }
      else{ //If both leading number and variable present, the variable is always the second part
        var = factors[1];
        for(int i = 0; i<vars.size(); i++){
            if(checkAnagram(var,vars.get(i))){
                var = vars.get(i);
                break;
            }
        } 
        vars.add(var);
      }
    }
    return vars; //Return the ArrayList of all the variables in the expression
  }

// Parses supplied polynomial expression and returns the simplifed version as a String
  private String simplifyExpr (String expr) throws ParseException{
    String simpExpr = null;

    XJep j = new XJep();
    PolynomialCreator pc = new PolynomialCreator(j);
    j.setImplicitMul(true);
    j.setAllowUndeclared(true);
    try{
    Node n = j.parse(expr);
    n = j.preprocess(n);
    Node simp = pc.expand(n);
    simp = pc.simplify(simp);
    simpExpr = j.toString(simp);
    return simpExpr;
  }
  catch(ParseException e){
    return null;
  }
  }
 public boolean checkAnagram(String str1, String str2) {
    if (str1.length() != str2.length())
        return false;

    char[] a = str1.toCharArray();
    char[] b = str2.toCharArray();

    Arrays.sort(a);
    Arrays.sort(b);
    return Arrays.equals(a, b);
}
}
