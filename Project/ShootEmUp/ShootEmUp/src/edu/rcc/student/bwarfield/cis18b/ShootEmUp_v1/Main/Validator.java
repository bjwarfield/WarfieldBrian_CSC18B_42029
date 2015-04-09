
package edu.rcc.student.bwarfield.cis18b.ShootEmUp_v1.Main;


public class Validator {
   
   
   public static boolean matchEmail(String email){
       //valid email (name@site.domain)
       return email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$");
   }
   public static boolean matchPassword(String password){
       //match Password must be 6-20 characters long, AlphaNumeric or @ # $ %
       return password.matches("^([a-zA-Z0-9@#$%]{6,20})$");
   }
   public static boolean matchHandle(String handle){
       //match Password must be 3-20 characters long, AlphaNumeric or @ # $ %
       return handle.matches("^([ a-zA-Z0-9@#$%]{3,20})$");
   }
   
}
