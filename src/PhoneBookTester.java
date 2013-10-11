public class PhoneBookTester {
    public static void main(String [] args) {
    try {
        PhoneBook book = new PhoneBook(100000);
        book.load("p3_infile.txt");
        // Simple lookup
        PhoneNumber number = new PhoneNumber("619-583-5185");
        System.out.println("The owner of 619-583-5185 should be Klein, Jason");
        System.out.println("The owner of " + number + " is " +
            book.numberLookup(number));
				
				System.out.println(number.getAreaCode());
						
				
        // invalid insert (it should already be in the PhoneBook)
        if(book.addEntry(number, "Klein, Jason"))
            System.out.println("***** ERROR, duplicate entry added *****");
        // now remove this entry
        if(!book.deleteEntry(number))
            System.out.println("***** ERROR, deletion failed *****"); 
        // now search for the just deleted entry 
        String tmp = book.numberLookup(number);
        if(tmp != null)
            System.out.println("***** ERROR, found deleted entry "
                +  tmp + " *****");

        // now put Jason Klein back               
        if(!book.addEntry(number, "Klein, Jason"))            
            System.out.println("***** ERROR, insertion failed *****"); 
                           
        System.out.println(
            "====================================================");
        book.printByAreaCode("619");
        System.out.println(
            "====================================================");
				
				//book.printByAreaCode("195");
				
				System.out.println("Testing nameLookup...");
				System.out.println("Klein, Jason : \n"+book.nameLookup("Klein, Jason"));
        PhoneNumber Pnumber = new PhoneNumber("208-419-7940");
				System.out.println("892-217-0722 : \n"+book.numberLookup(Pnumber));

System.out.println("Mann, Betty : "+book.nameLookup("Mann, Betty").toString());
        
        }
    catch(Exception e) {
        System.out.println("***** SERIOUS ERROR, this should never happen *****");
        System.out.println("Exception thrown " + e);
        e.printStackTrace();
        }
    }
}        
