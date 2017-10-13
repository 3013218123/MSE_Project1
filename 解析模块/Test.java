
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s="a|b@c";
		 String[] sArray=s.split("\\||@");
	
		 for(String i : sArray){
			 System.out.println(i);
			 
		 }
		
	}

}
