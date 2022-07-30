/**
 *  This class defines a Person Object for 'Poised' based on data required when signing into a project
 */
public class Person {
    // Attributes holding data required for each kind of person object
    String type;
    String firstName;
    String surname;
    String telNumber;
    String emailAddress;
    String physicalAddress;

    /**
     * Constructor Method
     * @param assignment {String} - Client/Contractor/Architect
     * @param firstName {String} - Persons first name
     * @param surname {String} - Persons Surname
     * @param telNumber {String} - Person Telephone number
     * @param emailAddress {String} - Persons Email Address
     * @param physicalAddress {String} - Persons Physical Address
     */
    public Person(String assignment, String firstName, String surname, String telNumber, String emailAddress,
                  String physicalAddress)
    {
        this.type = assignment;
        this.firstName = firstName;
        this.surname = surname;
        this.telNumber = telNumber;
        this.emailAddress = emailAddress;
        this.physicalAddress = physicalAddress;
    }

    /* Getters */

    /**
     * Get Full Name
     * @return (firstName + surname)
     */
    public String getName()
    {
        return firstName + " " + surname;
    }

    /**
     * Get the person object type (contractor / client / architect)
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * A string representation of all object info
     * @return type + firstName + surname + telNumber + emailAddress + physicalAddress
     */
    public String toString(){
        String output = "Assignment Category       :" + type + "\n";
        output += "First Name                :" + firstName + "\n";
        output += "Surname                   :" + surname + "\n";
        output += "Telephone Number          :" + telNumber + "\n";
        output += "Email Address             :" + emailAddress + "\n";
        output += "Physical Address          :" + physicalAddress +"\n";
        return output;
    }
}
