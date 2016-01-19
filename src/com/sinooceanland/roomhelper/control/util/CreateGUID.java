<<<<<<< HEAD
package com.sinooceanland.roomhelper.control.util;
import java.util.UUID;
/**
 * Create GUID
 * @author Administrator
 *
 */
public class CreateGUID {

 public static final String GenerateGUID(){
  UUID uuid = UUID.randomUUID();
  return uuid.toString();  
 }
 /**
  * @param args
  */
 public static void main(String[] args) {
  // TODO Auto-generated method stub
  System.out.println(GenerateGUID());
 }
=======
package com.sinooceanland.roomhelper.control.util;
import java.util.UUID;
/**
 * Create GUID
 * @author Administrator
 *
 */
public class CreateGUID {

 public static final String GenerateGUID(){
  UUID uuid = UUID.randomUUID();
  return uuid.toString();  
 }
 /**
  * @param args
  */
 public static void main(String[] args) {
  // TODO Auto-generated method stub
  System.out.println(GenerateGUID());
 }
>>>>>>> bd7db19437929ed8e3a979a4de8392288bb9ee94
}