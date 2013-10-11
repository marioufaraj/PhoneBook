/*
 * Robbie Pecjo masc1518
 * Professor Riggins
 * Programming Assignment #3
 * 12/05/2012
 */import data_structures.*;

public class PhoneNumber implements Comparable<PhoneNumber> {
    String areaCode, prefix, number;
    String phoneNumber;
    DictionaryADT<PhoneNumber, String> book;
    // Constructor.  Creates a new PhoneNumber instance.  The parameter
    // is a phone number in the form xxx-xxx-xxxx, which is area code -
    // prefix - number.  The phone number must be validated, and an
    // IllegalArgumentException thrown if it is invalid.
    public PhoneNumber(String n) {
    	verify(n);
    	phoneNumber = n;
    }

    // Follows the specifications of the Comparable Interface.
	public int compareTo(PhoneNumber n) {
    	return ((Comparable<String>)phoneNumber).compareTo(n.phoneNumber);
    }

    // Returns an int representing the hashCode of the PhoneNumber.
    public int hashCode() {
    	return phoneNumber.hashCode();
    }

    // Private method to validate the Phone Number.  Should be called
    // from the constructor.
    private void verify(String n) {
    	byte[] b = n.getBytes(); //returns array of bytes
    	if(!(b[3] == '-') || !(b[7] == '-') )
    		throw new IllegalArgumentException();
    	for(int i = 0; i < 3; i++)
    		if(b[i] < 0X30 || b[i] > 0X39)
    			throw new IllegalArgumentException();
    }

    // Returns the area code of the Phone Number.
    //XXX-123-4567
    public String getAreaCode() {
		return phoneNumber.substring(0, 3);

    }

    // Returns the prefix of the Phone Number.
    //123-XXX-4567
    public String getPrefix() {
		return phoneNumber.substring(4, 6);

    }

    // Returns the Phone Number.
    //123-456-XXXX
    public String getNumber() {
		return phoneNumber.substring(8, 12);

    }

    // Returns the Phone Number.
    public String toString() {
		return phoneNumber;

    }


}
