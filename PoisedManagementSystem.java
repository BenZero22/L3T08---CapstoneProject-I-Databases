import javax.swing.*;
import java.sql.*;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import static java.lang.System.out;

/**
 * This program is a simple project management system designed for Poise which will allow users to create projects, link
 * those projects to clients, architects or contractors. Users will also be able to update projects, get details on projects
 * as well as update people details
 */
public class PoisedManagementSystem {
    /* Constants suggested by SonarLint - Repetitive strings */
    public static final String FIRSTNAME_COLUMN = "firstname";
    public static final String SURNAME_COLUMN = "surname";
    public static final String ASSIGNMENT4 = "Structural Engineer";
    public static final String CONCAT_FIRST_NAME_SURNAME = "CONCAT(firstName,' ',surname) = ";
    public static final String ERROR = "ERROR!\n";
    public static final String MATCH_SEARCH_FROM_PEOPLE = "SELECT * FROM people WHERE CONCAT(firstName,' ',surname) = ";
    public static final String ENTER_NEW_TELEPHONE_NUMBER = "Enter new Telephone number";
    public static final String UPDATE_PEOPLE_SET_TELEPHONE = "UPDATE people SET telephone =";
    public static final String WHERE_CONCAT_FIRST_NAME_SURNAME = " WHERE CONCAT(firstName,' ',surname)=";
    public static final String TELEPHONE_NUMBER_UPDATED_FOR_CLIENT = "Telephone number updated for Client:\n";
    public static final String ENTER_NEW_EMAIL_ADDRESS = "Enter new email address";
    public static final String UPDATE_PEOPLE_SET_EMAIL = "UPDATE people SET email =";
    public static final String ENTER_NEW_PHYSICAL_ADDRESS = "Enter new physical address";
    public static final String UPDATE_PEOPLE_SET_PHYSICAL_ADDRESS = "UPDATE people SET physicalAddress =";
    public static final String DELETE_FROM_PEOPLE_WHERE_CONCAT_FIRST_NAME_SURNAME = "DELETE FROM people WHERE " +
            CONCAT_FIRST_NAME_SURNAME;
    public static final String AND_TYPE_ARCHITECT = " AND type = 'Architect'";
    public static final String EMAIL_ADDRESS_UPDATED = "Email Address updated";
    public static final String PHYSICAL_ADDRESS_UPDATED = "Physical address updated";
    public static final String AND_TYPE_STRUCTURAL_ENGINEER = " AND type = 'Structural Engineer'";
    public static final String FOR_STRUCTURAL_ENGINEER = " for Structural Engineer:\n";
    public static final String FOR_PROJECT_MANAGER = " for Project Manager:\n";
    public static final String AND_ID = "AND id =";
    public static final String AND_TYPE_PROJECT_MANAGER = " AND type = 'Project Manager'";
    public static final String FIRST_NAME_SURNAME = "SELECT * FROM people WHERE CONCAT(firstName,' ',surname) = ";
    public static final String AND_TYPE_CLIENT = " AND type = 'Client'";
    public static final String NO_RECORD_FOUND = "No record found";
    public static final String BACK_TO_MAIN_MENU = "Going back to Main Menu";
    public static final String PROJECT_MANAGER_AND = "SELECT * FROM people WHERE type = 'Project Manager' AND ";
    public static final String TYPE_ARCHITECT_AND = "SELECT * FROM people WHERE type = 'Architect' AND ";
    private static final String ASSIGNMENT = "Client";
    private static final String ASSIGNMENT1 = "Project Manager";
    private static final String ASSIGNMENT2 = "Architect";
    private static final String ASSIGNMENT3 = "'Structural Engineer'";
    private static final String TEL_MESSAGE = "Telephone Number / Cellphone Number";
    private static final String EMAIL_ADDRESS = "Email Address";
    private static final String HAS = " has ";
    private static final String DISPLAYED_IN_IDE = "Details displayed in IDE";
    private static final String NAME = "1) Search using name\n";
    private static final String DISPLAY_IN_IDE = "Results will display in IDE";
    private static final String INDEX_NAME = "Index      Name";
    private static final String SUCCESSFULLY = "\nsuccessfully";
    public static final String TELEPHONE = "telephone";
    public static final String EMAIL = "email";
    public static final String NOT_COMPLETE = "Not Complete";

    /**
     * This is the Primary source class for the Poised Project Management System which holds main and sub methods
     * @param args {String}
     */
    public static void main ( String [] args) {
        // Read and create objects from file
        // Main Menu
        // Initialise response with a value other than that linked to switch
        // Resource Credit : https://www.w3schools.com/java/java_switch.asp
        // Simple form of using multiple if-statements - applied Switch/Case method
        int response = 999;
        while (response != 0)
        {
            try{
            response = mainMenu(response);}
            catch (NumberFormatException e){
                out.println("Please ensure your entry is a number");
                response = mainMenu(response);
            }
        }
}

    /**
     * Application main menu
     * @param response {int}
     */
    private static int mainMenu(int response) {
        try {
            response = Integer.parseInt(JOptionPane.showInputDialog("""
                        
                    Welcome to Poised project Management System
                                    
                    What would you would like to do:
                                    
                      1. New Project
                      2. Find Project
                      3. Active
                      4. Overdue
                      5. People
                      
                      0. Quit"""));
            switch (response) {
                case 0 -> // Exits response loop and closes program
                        JOptionPane.showMessageDialog(null,"Goodbye!");
                case 1 -> // Add New project Objects and Person Objects
                        createNewProject();
                case 2 -> // Edit an existing project Objects parameters
                        searchProject();
                case 3 -> // Present user with a summary of active projects and get People Object details
                        showActiveProjects();
                case 4 -> // Present user with a summary of overdue projects
                        showOverdueProjects();
                case 5 -> // Allow user to update a Person Objects details
                        updatePersonObjectMenu();
                default ->
                        JOptionPane.showMessageDialog(null, "Please make a valid selection");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error!\nPlease enter only numbers");
        }
        return response;
    }

    /**
     * Method that connects to the source Projects database
     * @return connection {Connection}
     */
    private static Connection poisedPmsDatabase(){
        Connection connection = null;
        try{
            // Create connection for this session
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/PoisedPMS?allowPublicKeyRetrieval=true&useSSL=false",
                    "otheruser",
                    "swordfish"
            );
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"ERROR - " + e);
        }
        return connection;
    }

    /**
     * ___________________Main Menu Option 5
     * Allows the user to get an existing person object to update it, or to create a new person object
     */
    private static void updatePersonObjectMenu () {
        try {
            // Present the user with menu
            int updatePersonOption = Integer.parseInt(JOptionPane.showInputDialog(null,
                    """
                                   ________Update Personal Details________
                            1) New Person
                            2) Update an existing Project Manager's details
                            3) Update an existing Architect's details
                            4) Update an existing Client's details
                            5) Update an existing Structural Engineer's details
                            
                            0) Main Menu"""));
            switch (updatePersonOption) {
                case 0 -> {// Returns to main menu
                }
                // Create a new person Object
                case 1 -> createNewPersonMenu();
                // Present a summary list of person Objects where the assignment attribute = Project Manager
                case 2 -> showProjectManagerPersonObjects();
                // Present a summary list of person Objects where the assignment attribute = Architect
                case 3 -> showArchitectPersonObjects();
                // Present a summary list of person Object where the assignment attribute = Client
                case 4 -> showClientPersonObject();
                // Present a summary list of person Object where the assignment attribute = Structural Engineer
                case 5 -> showStructuralEngineerPersonObjects();
                default -> JOptionPane.showMessageDialog(null, "Invalid Option");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error!\nEnter a number");
            updatePersonObjectMenu();
        }
    }

    /**
     * Allows user to search for a client or to get a list all contractors
     */
    private static void showClientPersonObject () {
        // Present user with search preference option
        int clientDetails = Integer.parseInt(JOptionPane.showInputDialog(getNAME() +
                "2) List all clients"));
        // Search person using name
        if (clientDetails == 1){
            searchClientName();
        }
        if (clientDetails == 2){
            JOptionPane.showMessageDialog(null, getDisplayInIde());
            listClients();
        }
    }

    /**
     * Determines output based on whether there are any client objects to display or not
     */
    private static void listClients() {
        // Create index loop and add 1 for each people assignment attribute = Client
        int peopleFound = 0;
        out.println(getIndexName());
        try(Connection connection = poisedPmsDatabase()){
            ResultSet results;
            Statement statement = connection.createStatement();
            results = statement.executeQuery("SELECT * FROM people WHERE type = 'Client'");
            while(results.next()){
                peopleFound ++;
                out.println(results.getString(2) + ' ' + results.getString(3));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, ERROR +e);
        }
        out.println();
        // Allow user to search index as long as there was a match
        if (peopleFound != 0) {
            int match = 0;
            String listedClientName = JOptionPane.showInputDialog("Enter Full Name of Client's details you would" +
                    " like to edit");
            listedClientName = "'"+listedClientName+"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(FIRST_NAME_SURNAME +listedClientName+ AND_TYPE_CLIENT);
                while (results.next()){
                    Person buildClientObject = new Person(results.getString(1),results.getString(2),
                            results.getString(3),results.getString(4),results.getString(5),
                            results.getString(6));
                    out.println(buildClientObject);
                    match ++;
                }
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            if(match!=0){
                editProjManMenu(listedClientName);
            }
            else{
                JOptionPane.showMessageDialog(null, NO_RECORD_FOUND);
            }
        }
        // Where there are no people objects that meet the initial search criteria, make user aware
        else {
            out.println("___No Clients to list___");
            JOptionPane.showMessageDialog(null, "There are currently no clients on listed.");
        }
    }

    /**
     * Menu that allows the user to update values on a client - type person Object
     * @param clientName {String}
     */
    private static void editClientMenu(String clientName) {
        // Allow user to update person Object Attributes
        int updateClient = Integer.parseInt(JOptionPane.showInputDialog("""
                              Edit Client's Details
                1) Update Telephone Number
                2) Update Email Address
                3) Update Physical Address
                4) Delete Contractor
                
                0) Cancel\040"""));

        // Update person Object telephone number attribute
        if (updateClient == 1) {
            String telNumber = JOptionPane.showInputDialog(ENTER_NEW_TELEPHONE_NUMBER);
            telNumber = "'"+telNumber+"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(UPDATE_PEOPLE_SET_TELEPHONE+
                        telNumber+" WHERE CONCAT(firstName,' ',surname) =" + clientName);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, TELEPHONE_NUMBER_UPDATED_FOR_CLIENT +
                    clientName + getSUCCESSFULLY());
        }
        // Update person Object email address attribute
        if (updateClient == 2) {
            String email = JOptionPane.showInputDialog(ENTER_NEW_EMAIL_ADDRESS);
            email = "'"+email+"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(UPDATE_PEOPLE_SET_EMAIL +email+ WHERE_CONCAT_FIRST_NAME_SURNAME+
                        clientName);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, "Email Address updated for Client:\n" +
                    clientName + getSUCCESSFULLY());
        }
        // Update person Object physical address attributes
        if (updateClient == 3) {
            String address = JOptionPane.showInputDialog(ENTER_NEW_PHYSICAL_ADDRESS);
            address = "'"+ address +"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(UPDATE_PEOPLE_SET_PHYSICAL_ADDRESS +address+WHERE_CONCAT_FIRST_NAME_SURNAME+
                        clientName);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, "Physical address updated for Client:\n" +clientName + getSUCCESSFULLY());
        }
        // Remove person Object from personsArray
        if (updateClient == 4) {
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(DELETE_FROM_PEOPLE_WHERE_CONCAT_FIRST_NAME_SURNAME + clientName);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, "Record deleted for Client:\n" +
                    clientName + getSUCCESSFULLY());
        }
        // Return to main menu
        if (updateClient == 0){
            JOptionPane.showMessageDialog(null, BACK_TO_MAIN_MENU);
        }
    }

    /**
     * Allows the user to search for a clients full name to get toString()
     */
    private static void searchClientName () {
        String clientName = JOptionPane.showInputDialog("Enter clients first name and surname");
        clientName = "'"+clientName+"'";
        int clientsFound = 0;
        // Iterate through people db to identify client with input name
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(MATCH_SEARCH_FROM_PEOPLE +clientName+ AND_TYPE_CLIENT);
            while (results.next()){
                Person buildClientObject = new Person(results.getString(1),results.getString(2),
                        results.getString(3),results.getString(4),results.getString(5),
                        results.getString(6));
                clientsFound ++;
                out.println(buildClientObject);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, ERROR +e);
        }
        if(clientsFound!=0){
            editClientMenu(clientName);
        }
        else{
            JOptionPane.showMessageDialog(null,"No Client Found.");
        }
    }

    /**
     * Allows user to search for a Project Manager using name and provides user with toString() for object
     */
    private static void searchProjectManagerName () {
        String projectManagerName = JOptionPane.showInputDialog("Enter Project Manager name and surname");
        projectManagerName = "'"+projectManagerName+"'";
        int projManFound = 0;
        // Iterate through database to identify project manager with input name
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(PROJECT_MANAGER_AND +
                    "CONCAT(firstname,' ',surname) = "+projectManagerName);
            while(results.next()){
                Person buildProjectManagerObject = new Person(results.getString(1),results.getString(2),
                        results.getString(3),results.getString(4),results.getString(5),
                        results.getString(6));
                projManFound++;
                out.println(buildProjectManagerObject);
            }
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, ERROR +e);
        }
        if(projManFound != 0){
            editProjManMenu(projectManagerName);
        }
        else{
            JOptionPane.showMessageDialog(null,"No Project Manager Found");
        }
    }

    /**
     * Allows user to get an architects toString() by searching full name
     */
    private static void searchArchitectName () {
        String architectName = JOptionPane.showInputDialog("Enter architect's name and surname");
        architectName = "'"+architectName+"'";
        int architectsFound = 0;
        // Iterate through people db to identify Architect with input name
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(TYPE_ARCHITECT_AND + "CONCAT(firstname,' ',surname) = "
                    +architectName);
            while (results.next()){
                Person buildClientObject = new Person(results.getString(1),results.getString(2),
                        results.getString(3),results.getString(4),results.getString(5),
                        results.getString(6));
                architectsFound ++;
                out.println(buildClientObject);}
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, ERROR +e);
        }
        if(architectsFound !=0){
            editArchitectMenu(architectName);
        }
        else{
            JOptionPane.showMessageDialog(null,"No Architect Found.");
        }
    }

    /**
     * Allows user to get an Structural Engineer's toString() by searching full name
     */
    private static void searchStructuralEngineerName () {
        String structuralEngineerName = JOptionPane.showInputDialog("Enter Structural Engineer's name and surname");
        structuralEngineerName = "'"+structuralEngineerName+"'";
        int strucEngFound = 0;
        // Iterate through people db to identify Architect with input name
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM people WHERE CONCAT(firstName,' '," +
                    "surname) = "+structuralEngineerName+ AND_TYPE_STRUCTURAL_ENGINEER);
            while (results.next()){
                Person buildClientObject = new Person(results.getString(1),results.getString(2),
                        results.getString(3),results.getString(4),results.getString(5),
                        results.getString(6));
                strucEngFound++;
                out.println(buildClientObject);}
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, ERROR +e);
        }
        if(strucEngFound>0){
            editStrucEngMenu(structuralEngineerName);
        }
        else{
            JOptionPane.showMessageDialog(null, "No Structural Engineer Found.");
        }
    }

    /**
     * A side option that allows the user to either list architects or search by name without listing
     */
    private static void showArchitectPersonObjects () {
        // Allow user to indicate search method (search by name, or by code)
        int architectDetails = Integer.parseInt(JOptionPane.showInputDialog(getNAME() +
                "2) List all architects"));
        // Search person using name
        if (architectDetails == 1){
            searchArchitectName();}
        if (architectDetails == 2){
            JOptionPane.showMessageDialog(null, getDisplayInIde());
            listArchitects();}
    }

    /**
     * A side option that allows the user to either list structural engineers or search by name without listing
     */
    private static void showStructuralEngineerPersonObjects () {
        // Allow user to indicate search method (search by name, or by code)
        int structuralEngineerDetails = Integer.parseInt(JOptionPane.showInputDialog(getNAME() +
                "2) List all Structural Engineers"));
        // Search person using name
        if (structuralEngineerDetails == 1){
            searchStructuralEngineerName();}
        if (structuralEngineerDetails == 2){
            JOptionPane.showMessageDialog(null, getDisplayInIde());
            listStructuralEngineers();}
    }

    /**
     * Allows user to get an architects toString() by entering
     */
    private static void listArchitects() {
        int peopleFound = 0;
        out.println(getIndexName());
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM people WHERE type = 'Architect'");
            while(results.next()){
                peopleFound ++;
                out.println(results.getString(2) + ' ' + results.getString(3));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, ERROR +e);
        }
        out.println();
        // Allow the user to search an Architect name as long as there was a match in type Architect in people table
        if (peopleFound != 0) {
            int match = 0;
            String listedArchitectName = JOptionPane.showInputDialog("Enter Full Name of the Architect for details you would" +
                    " like to edit");
            listedArchitectName = "'"+listedArchitectName+"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(FIRST_NAME_SURNAME
                        +listedArchitectName+AND_TYPE_ARCHITECT);
                while (results.next()){
                    Person buildClientObject = new Person(results.getString(1),results.getString(2),
                            results.getString(3),results.getString(4),results.getString(5),
                            results.getString(6));
                    out.println(buildClientObject);
                    match ++;
                }
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            if(match!=0){
                editProjManMenu(listedArchitectName);
            }
            else{
                JOptionPane.showMessageDialog(null, NO_RECORD_FOUND);
            }
        }
        // Where there are no people objects that meet the initial search criteria, make user aware
        else {
            out.println("___No Architects to list___");
            JOptionPane.showMessageDialog(null, "There are currently no architects listed");
        }
    }

    /**
     * Method that allows users to update architect person type object details and writes it back to table in db
     * @param architectName {String}
     */
    private static void editArchitectMenu(String architectName) {
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(MATCH_SEARCH_FROM_PEOPLE +
                    architectName+ AND_TYPE_ARCHITECT);
            while (results.next()){
                Person buildClientObject = new Person(results.getString(1),results.getString(2),
                        results.getString(3),results.getString(4),results.getString(5),
                        results.getString(6));
                out.println(buildClientObject);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, ERROR +e);
        }
        // Allow user to update person Object Attributes
        int updateClient = Integer.parseInt(JOptionPane.showInputDialog("""
                              Edit Architect's Details
                1) Update Telephone Number
                2) Update Email Address
                3) Update Physical Address
                4) Delete Architect Details
                
                0) Cancel"""));
        // Update person Object telephone number attribute
        if (updateClient == 1) {
            String telNumber = JOptionPane.showInputDialog(ENTER_NEW_TELEPHONE_NUMBER);
            telNumber = "'"+telNumber+"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(UPDATE_PEOPLE_SET_TELEPHONE +telNumber+WHERE_CONCAT_FIRST_NAME_SURNAME+
                        architectName + AND_TYPE_ARCHITECT);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, "Telephone number updated for Architect:\n" +
                    architectName + getSUCCESSFULLY());
        }
        // Update person Object email address attribute
        if (updateClient == 2) {
            String email = JOptionPane.showInputDialog(ENTER_NEW_EMAIL_ADDRESS);
            email = "'"+email+"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(UPDATE_PEOPLE_SET_EMAIL +email+WHERE_CONCAT_FIRST_NAME_SURNAME+architectName);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, EMAIL_ADDRESS_UPDATED +
                    " for Architect:\n" +architectName + getSUCCESSFULLY());
        }
        // Update person Object physical address attributes
        if (updateClient == 3) {
            String address = JOptionPane.showInputDialog(ENTER_NEW_PHYSICAL_ADDRESS);
            address = "'"+ address +"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(UPDATE_PEOPLE_SET_PHYSICAL_ADDRESS + address +
                        WHERE_CONCAT_FIRST_NAME_SURNAME+architectName);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, PHYSICAL_ADDRESS_UPDATED +
                    " for Architect:\n" +architectName + getSUCCESSFULLY());
        }
        // Remove person Object from personsArray
        if (updateClient == 4) {
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(DELETE_FROM_PEOPLE_WHERE_CONCAT_FIRST_NAME_SURNAME +
                        architectName);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, "Record deleted for Architect:\n" +
                    architectName + getSUCCESSFULLY());
        }
        // Cancel menu
        if (updateClient == 0){
            JOptionPane.showMessageDialog(null, BACK_TO_MAIN_MENU);
        }
    }

    /**
     * Method that reads people table and  lists structural engineer names
     */
    private static void listStructuralEngineers() {
        int peopleFound = 0;
        out.println(getIndexName());
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM people WHERE type = 'Structural Engineer'");
            while(results.next()){
                peopleFound ++;
                out.println(results.getString(2) + ' ' + results.getString(3));
            }}catch(SQLException e){
            JOptionPane.showMessageDialog(null, ERROR +e);
        }
        out.println();
        // Allow user to search index as long as there are people Objects in peoplesArray
        if (peopleFound != 0) {
            int match = 0;
            String listedClientName = JOptionPane.showInputDialog("Enter Full Name of the Structural Engineer for " +
                    "details you would like to edit");
            listedClientName = "'"+listedClientName+"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(FIRST_NAME_SURNAME
                        +listedClientName+ AND_TYPE_STRUCTURAL_ENGINEER);
                while (results.next()){
                    Person buildClientObject = new Person(results.getString(1),results.getString(2),
                            results.getString(3),results.getString(4),results.getString(5),
                            results.getString(6));
                    out.println(buildClientObject);
                    match ++;
                }
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            if(match!=0){
                editProjManMenu(listedClientName);
            }
            else{
                JOptionPane.showMessageDialog(null, NO_RECORD_FOUND);
            }
        }
        // Where there are no people objects that meet the initial search criteria, make user aware
        else {
            out.println("___No Structural Engineers to list___");
            JOptionPane.showMessageDialog(null, "There are currently no Structural Engineers listed");
        }
    }

    /**
     * Method that allows users to update Structural engineer type person object's details and writes it
     * back to table in db
     * @param strucEngName {String}
     */
    private static void editStrucEngMenu(String strucEngName) {
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(MATCH_SEARCH_FROM_PEOPLE +
                    strucEngName+ AND_TYPE_STRUCTURAL_ENGINEER);
            while (results.next()){
                Person buildClientObject = new Person(results.getString(1),results.getString(2),
                        results.getString(3),results.getString(4),results.getString(5),
                        results.getString(6));
                out.println(buildClientObject);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, ERROR +e);
        }
        // Allow user to update person Object Attributes
        int updateClient = Integer.parseInt(JOptionPane.showInputDialog("""
                              Edit Structural Engineer's Details
                1) Update Telephone Number
                2) Update Email Address
                3) Update Physical Address
                4) Delete Architect Details
                
                0) Cancel"""));

        // Update person Object telephone number attribute
        if (updateClient == 1) {
            String telNumber = JOptionPane.showInputDialog(ENTER_NEW_TELEPHONE_NUMBER);
            telNumber = "'"+telNumber+"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(UPDATE_PEOPLE_SET_TELEPHONE +telNumber+WHERE_CONCAT_FIRST_NAME_SURNAME+
                        strucEngName + AND_TYPE_STRUCTURAL_ENGINEER);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, "Telephone number updated" +
                    FOR_STRUCTURAL_ENGINEER +strucEngName + getSUCCESSFULLY());
        }
        // Update person Object email address attribute
        if (updateClient == 2) {
            String email = JOptionPane.showInputDialog(ENTER_NEW_EMAIL_ADDRESS);
            email = "'"+email+"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(UPDATE_PEOPLE_SET_EMAIL +email+WHERE_CONCAT_FIRST_NAME_SURNAME+
                        strucEngName);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, EMAIL_ADDRESS_UPDATED +
                    FOR_STRUCTURAL_ENGINEER +strucEngName + getSUCCESSFULLY());
        }
        // Update person Object physical address attributes
        if (updateClient == 3) {
            String address = JOptionPane.showInputDialog(ENTER_NEW_PHYSICAL_ADDRESS);
            address = "'"+ address +"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(UPDATE_PEOPLE_SET_PHYSICAL_ADDRESS + address +
                        WHERE_CONCAT_FIRST_NAME_SURNAME+strucEngName);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, PHYSICAL_ADDRESS_UPDATED +
                    FOR_STRUCTURAL_ENGINEER +strucEngName + getSUCCESSFULLY());
        }
        // Remove person Object from personsArray
        if (updateClient == 4) {
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(DELETE_FROM_PEOPLE_WHERE_CONCAT_FIRST_NAME_SURNAME +
                        strucEngName);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, "Record deleted for Structural Engineer:\n" +
                    strucEngName + getSUCCESSFULLY());
        }
        // Go back to main menu
        if (updateClient == 0){
            JOptionPane.showMessageDialog(null, BACK_TO_MAIN_MENU);
        }
    }

    /**
     * Allows user to search for a Project Manager or list all contractors
     */
    private static void showProjectManagerPersonObjects() {
        // Allow user to indicate search method (search by name, or by code)
        int projectManager = Integer.parseInt(JOptionPane.showInputDialog(getNAME() +
                "2) List all Project Managers"));
        // Search person using name
        if (projectManager == 1){
            searchProjectManagerName();
        }
        // List all projects
        if (projectManager == 2){
            JOptionPane.showMessageDialog(null, getDisplayInIde());
            listProjectManagers();
        }
    }

    /**
     * Determines output based on whether there are any contractor objects to display or not
     */
    private static void listProjectManagers() {
        int peopleFound = 0;
        out.println(getIndexName());
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM people WHERE type = 'Project Manager'");
            while(results.next()){
                peopleFound ++;
                out.println(results.getString(2) + ' ' + results.getString(3));
            }
            statement.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, ERROR +e);
        }
        out.println();
        // Allow user enter Project Manager name
        if (peopleFound != 0) {
            int match = 0;
            String listedClientName = JOptionPane.showInputDialog("Enter Full Name of the Project Manager for " +
                    "details you would like to edit");
            listedClientName = "'"+listedClientName+"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(FIRST_NAME_SURNAME
                        +listedClientName+ AND_TYPE_PROJECT_MANAGER);
                while (results.next()){
                    Person buildClientObject = new Person(results.getString(1),results.getString(2),
                            results.getString(3),results.getString(4),results.getString(5),
                            results.getString(6));
                    out.println(buildClientObject);
                    match ++;
                }
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            if(match!=0){
                editProjManMenu(listedClientName);
            }
            else{
                JOptionPane.showMessageDialog(null, NO_RECORD_FOUND);
            }
        }
        // Where there are no people objects that meet the initial search criteria, make user aware
        else {
            out.println("___No Project Managers to list___");
            JOptionPane.showMessageDialog(null, "There are currently no Project Managers on Record");
        }
    }

    /**
     * Method that allows users to update project manager object details and writes it back to table in db
     * @param listedClientName {String}
     */
    private static void editProjManMenu(String listedClientName) {
        // Allow user to update person Object Attributes
        int updateClient = Integer.parseInt(JOptionPane.showInputDialog("""
                              Edit Project Manager's Details
                1) Update Telephone Number
                2) Update Email Address
                3) Update Physical Address
                4) Delete Project Manager Details
                
                0) Cancel"""));
        // Update person Object telephone number attribute
        if (updateClient == 1) {
            String telNumber = JOptionPane.showInputDialog(ENTER_NEW_TELEPHONE_NUMBER);
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(UPDATE_PEOPLE_SET_TELEPHONE +telNumber+WHERE_CONCAT_FIRST_NAME_SURNAME+
                        listedClientName + AND_TYPE_PROJECT_MANAGER);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, "Telephone number updated" +
                    FOR_PROJECT_MANAGER + listedClientName + getSUCCESSFULLY());
        }
        // Update person Object email address attribute
        if (updateClient == 2) {
            String email = JOptionPane.showInputDialog(ENTER_NEW_EMAIL_ADDRESS);
            email = "'"+email+"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(UPDATE_PEOPLE_SET_EMAIL +email+ WHERE_CONCAT_FIRST_NAME_SURNAME+
                        listedClientName+ "AND type = 'Project Manager'");
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, EMAIL_ADDRESS_UPDATED +
                    FOR_PROJECT_MANAGER + listedClientName + getSUCCESSFULLY());
        }
        // Update person Object physical address attributes
        if (updateClient == 3) {
            String address = JOptionPane.showInputDialog(ENTER_NEW_PHYSICAL_ADDRESS);
            address = "'"+ address +"'";
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(UPDATE_PEOPLE_SET_PHYSICAL_ADDRESS +address+ WHERE_CONCAT_FIRST_NAME_SURNAME +
                        listedClientName + AND_TYPE_PROJECT_MANAGER);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, PHYSICAL_ADDRESS_UPDATED +
                    FOR_PROJECT_MANAGER + listedClientName + getSUCCESSFULLY());
        }
        // Remove person Object from db table
        if (updateClient == 4) {
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate(DELETE_FROM_PEOPLE_WHERE_CONCAT_FIRST_NAME_SURNAME +
                        listedClientName + AND_TYPE_PROJECT_MANAGER);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR +e);
            }
            JOptionPane.showMessageDialog(null, "Record deleted for Project Manager:\n" +
                    listedClientName + getSUCCESSFULLY());
        }
        // Return to Main Menu
        if (updateClient == 0){
            JOptionPane.showMessageDialog(null, BACK_TO_MAIN_MENU);
        }
    }

    /**
     * Allows a user to create a new Person object - without having to create a new project first
     */
    private static void createNewPersonMenu () {
        // Determine what category person Object will be created
        int newPersonAssignment = Integer.parseInt(JOptionPane.showInputDialog("""
                                    Assignment
                1) New Client
                2) New Project Manager
                3) New Architect
                4) Structural Engineer"""));
        // Variable initialised outside of loops in order to catch result after conditions are processed
        String newPersonAssignmentStr;
        newPersonAssignmentStr = assignPersonType(newPersonAssignment, "");
        // Obtain remaining person Object attributes from user
        String newName = JOptionPane.showInputDialog("Name");
        String newSurname = JOptionPane.showInputDialog("Surname");
        String newTelNumber = JOptionPane.showInputDialog("Telephone Number");
        String newEmailAddress = JOptionPane.showInputDialog(getEmailAddress());
        String newPhysicalAddress = JOptionPane.showInputDialog("Physical Address");
        // Create person object
        Person newPerson = new Person(newPersonAssignmentStr, newName, newSurname, newTelNumber,
                newEmailAddress, newPhysicalAddress);
        // Allow user to review new person entity before adding it to 'database'
        out.println();
        out.println(newPerson);
        try {
            // Get confirmation to proceed from user, otherwise cancel creation of person Object
            int continueCreation = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "New " + newPersonAssignmentStr + " details displayed in IDE\n" +
                            "1) Create\n2) Cancel"));
            if (continueCreation == 1) {
                personToDb(newPersonAssignmentStr, newPerson);
            } else {
                JOptionPane.showMessageDialog(null, "Creation aborted");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error!\nEnter a number response");
        }
    }

    /**
     * Method to write new person data to people table in database
     * @param newPersonAssignmentStr {String} - dictates the type-column value
     * @param newPerson {Person}
     */
    private static void personToDb(String newPersonAssignmentStr, Person newPerson) {
        try (Connection connection = poisedPmsDatabase()){
            String insert = "INSERT INTO people(type, firstName, surname, telephone, email, physicalAddress) " +
                    "VALUES (?,?,?,?,?,?)";
            PreparedStatement columnIndex = connection.prepareStatement(insert);
            columnIndex.setString(1,String.valueOf(newPersonAssignmentStr));
            columnIndex.setString(2, newPerson.firstName);
            columnIndex.setString(3, newPerson.surname);
            columnIndex.setString(4, newPerson.telNumber);
            columnIndex.setString(5, newPerson.emailAddress);
            columnIndex.setString(6, newPerson.physicalAddress);
            columnIndex.executeUpdate();
            columnIndex.close();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,ERROR +
                    "Description:\n" +
                    ""+e);
        }
        JOptionPane.showMessageDialog(null, "Person created successfully!");
    }

    /**
     * This method is used when creating new people objects to allocate the correct 'type'
     * @param newPersonAssignment {int}
     * @param newPersonAssignmentStr {String}
     * @return newPersonAssignmentStr
     */
    private static String assignPersonType ( int newPersonAssignment, String newPersonAssignmentStr){
        // Automatic assignment takes place based on creation option prior
        if (newPersonAssignment == 1) {
            newPersonAssignmentStr = getASSIGNMENT();
        }
        if (newPersonAssignment == 2) {
            newPersonAssignmentStr = getASSIGNMENT1();
        }
        if (newPersonAssignment == 3) {
            newPersonAssignmentStr = getASSIGNMENT2();
        }
        if (newPersonAssignment == 4) {
            newPersonAssignmentStr = ASSIGNMENT4;
        }
        return newPersonAssignmentStr;
    }

    /**
     * ___________________Main Menu Option 2
     * Allows user to search using either project code or project name, input is referenced against database query to
     * present user with project.getString()
     */
    private static void searchProject() {
        getProjects();
        // User to choose search type preference
        String findProject = JOptionPane.showInputDialog(null, """
                ______________Search Projects______________
                             
                Enter the projects code OR the project's name
                                
                               Enter '0' to Cancel           """);
        findProject = "'" + findProject + "'";
        // Counter used in else block to inform user when there are no projects
        int projectsFound = 0;
        try (Connection connection = poisedPmsDatabase()) {
            Statement statement = connection.createStatement();
            // Query table for match
            ResultSet results = statement.executeQuery("SELECT * FROM projects WHERE id =" + findProject +
                    " OR projectName = " + findProject);
            while (results.next()) {
                JOptionPane.showMessageDialog(null, DISPLAYED_IN_IDE);
                // Build project object from columns in projects table
                Project buildProject = new Project(Integer.parseInt(results.getString(1)),
                        results.getString(2), results.getString(3),
                        results.getString(4), Integer.parseInt(results.getString(5)),
                        Integer.parseInt(results.getString(6)),
                        Integer.parseInt(results.getString(7)), results.getString(8),
                        results.getString(9), results.getString(10),
                        results.getString(12), results.getString(13),
                        results.getString(11));
                projectsFound++;
                // Update project object to reflect correct final status
                if(!buildProject.finalDate.equalsIgnoreCase(NOT_COMPLETE)){
                    buildProject.markAsFinal();
                }
                out.println(buildProject);
                updateProject(buildProject);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, ERROR + e);
        }
        // Allow user to go back to main menu
        if ((Objects.equals(findProject, "'0'"))) {
            // Breaks flow and returns user to main menu
            JOptionPane.showMessageDialog(null, "Search Cancelled");
        }
        // Inform user if their search was not found and avoid 'No project found' when a user enters '0'
        // If entry by user is unrecognised and to prevent premature crash - recursive call
        else {
            if (projectsFound == 0) {
                JOptionPane.showMessageDialog(null, "No project found.");
                searchProject();
            }
        }
    }

    /**
     * ___________________Main Menu Option 4
     * Displays a summary of overdue projects by checking current and due date
     */
    private static void showOverdueProjects () {
        JOptionPane.showMessageDialog(null, "Overdue Projects displayed in IDE");
        int overdueFound = 0;
        try(Connection connection = poisedPmsDatabase()){
            out.println("\n______Overdue Projects_____");
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM projects WHERE dueDate > now()");
            while (results.next()){
                out.println(results.getString(2) + " for " +results.getString(13) + " was due " +
                        "on "+results.getString(8));
                overdueFound ++;
            }
            if(overdueFound==0){
                out.println("\nThere are currently no overdue projects!");
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, ERROR +e);
        }
    }

    /**
     * ___________________Main Menu Option 3
     * Filters through projects table to identify active projects - builds the project object and allows user to get
     * more info in people linked to project
     */
    private static void showActiveProjects(){
        // Present a list of active projects
        int activeProjNumber = getOpenProjects();
            if(activeProjNumber!=0){
            // Allow user to obtain more info on project
            String getProjectDetails = JOptionPane.showInputDialog("Enter project name or code for more info,\nor '0' " +
                    "to go back");
            getProjectDetails = "'"+getProjectDetails+"'";
            // If a user requests for more info
            if (!getProjectDetails.equals("0")) {
                try(Connection connection = poisedPmsDatabase()){
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery("SELECT * FROM projects WHERE projectName ="+
                            getProjectDetails+" OR id ="+getProjectDetails);
                    while(results.next()){
                        Project buildProject = new Project(Integer.parseInt(results.getString(1)),
                                results.getString(2), results.getString(3),
                                results.getString(4), Integer.parseInt(results.getString(5)),
                                Integer.parseInt(results.getString(6)),
                                Integer.parseInt(results.getString(7)),
                                results.getString(8), results.getString(9),
                                results.getString(10), results.getString(12),
                                results.getString(13), results.getString(11));
                        JOptionPane.showMessageDialog(null, "Details for " +
                                buildProject.getProjectName() + " displayed in IDE");
                        out.println(buildProject);
                        peopleObjectsMenu(buildProject);
                    }
                }catch (SQLException e){
                    JOptionPane.showMessageDialog(null, ERROR +e);
                }
            }
        }
    }

    /**
     * ___________________Update Project Option 4
     * Takes in a project and allows the user to select which person linked to the project they would like to get more
     * info on
     * @param project {Project}
     */
    private static void peopleObjectsMenu (Project project){
        while(true){
            int moreOptions = Integer.parseInt(JOptionPane.showInputDialog("""
                            _____Get project allocation details_____
                            
                    1) Get this project's Architect Details
                    2) Get this project's Client Details
                    3) Get this project's Project Manager Details
                    4) Get this project's Structural Engineer Details
                    
                    0) Back to Main Menu
                    """));
            String type;
            // Assign type as architect - get architect's details and displays to user
            if(moreOptions == 1){
                type = ASSIGNMENT2;
                showPersonObject(project,type);
            }
            // Assign type as client - get client's details and displays to user
            if(moreOptions == 2){
                type = ASSIGNMENT;
                showPersonObject(project,type);
            }
            // Assign type as project manager - get project manager's details and displays to user
            if(moreOptions == 3){
                type = ASSIGNMENT1;
                showPersonObject(project,type);
            }
            // Assign type as structural engineer - get structural engineer's details and displays to user
            if(moreOptions == 4){
                type = ASSIGNMENT4;
                showPersonObject(project,type);
            }
            if(moreOptions == 0){
                break;
            }
        }
    }

    /**
     * Reads project object type to prepare string information for database query
     * @param project {Project}
     */
    private static void showPersonObject(Project project, String type){
        JOptionPane.showMessageDialog(null, type + " " + getDisplayedInIde());
        type = "'"+type+"'";
        String personName = "";
        // Prepare the string for SQL query
        if(type.equalsIgnoreCase("'Client'")){
            // Assign the project's client name and format for SQL syntax
            personName = project.getClient();
            personName = "'"+personName+"'";
            // Match client name to db and assign column values to build person object
        }
        if(type.equalsIgnoreCase("'Architect'")){
            personName = project.getArchitect();
            personName = "'"+personName+"'";
        }
        if(type.equalsIgnoreCase(ASSIGNMENT3)){
            personName = project.getStrucEng();
            personName = "'"+personName+"'";
        }
        if(type.equalsIgnoreCase("'Project Manager'")){
            personName = project.getProjManager();
            personName = "'"+personName+"'";
        }
        getPersonData(type, personName);
    }

    /**
     * Attempts to access people table in DB to build person object and present info to user
     * @param type {String}
     * @param personName {String}
     */
    private static void getPersonData(String type, String personName) {
        int peopleFound = 0;
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM people WHERE type = "+type+" AND "+
                    CONCAT_FIRST_NAME_SURNAME + personName);
            out.println("\n     ___"+ type +" Details___ \n");
            while(results.next()){
                Person buildPerson = new Person(results.getString(1),results.getString(2),
                        results.getString(3),results.getString(4),results.getString(5),
                        results.getString(6));
                peopleFound++;
                // Present info to user
                out.println(buildPerson);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, ERROR + e);
        }
        if (peopleFound == 0){
            JOptionPane.showMessageDialog(null,"Person not found");
            out.println("... It appears that this record is missing from people table in database ...");
        }
    }

    /**
     * Displays a summary of active projects to user
     */
    private static int getOpenProjects () {
        JOptionPane.showMessageDialog(null, "Active Projects\nDisplayed in IDE");
        int counter = 0;
        out.println("\n______Active Projects______");
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM projects WHERE status = 'Not complete'");
            while(results.next()){
                out.println(results.getString(1) + " " + results.getString(2));
                counter ++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, ERROR + e);
        }
        out.println();
        out.println("There are " + counter + " active projects");
        return counter;
    }

    /**
     * Project menu that allows the user to make adjustments to an existing project, or get more info on people linked
     * to the project
     * @param project {Project}
     */
    private static void updateProject(Project project) {
        int updateProjectMenu = 99;
        while(updateProjectMenu != 0) {
                updateProjectMenu = Integer.parseInt(JOptionPane.showInputDialog("""
                        What would you like to do?
                                                        
                        1. Add payment to project
                        2. Update due date
                        3. Get outstanding balance on project
                        4. Assignment Info
                                                        
                        99. Finalise project
                                                        
                        0. Back to main menu"""));
                switch (updateProjectMenu) {
                    // Add amount - to amount paid to date on a project
                    case 1 -> updateProjectPayment(project);
                    // Update a due date on an existing project
                    case 2 -> updateProjectDate(project);
                    // Get the outstanding balance of the project
                    case 3 -> displayProjectBalance(project);
                    // Get people assignment info on project
                    case 4 -> peopleObjectsMenu(project);
                    // Finalise a project as complete
                    case 99 -> finaliseProject(project);
                    // Return to main menu
                    case 0 -> {
                        // break out of loop
                    }
                    // Otherwise Invalid Option
                    default -> JOptionPane.showMessageDialog(null, "Invalid option");
                }
            }
        }

    /**
     * ___________________Update Project Option 5
     * Allows the user to complete or finalise a project
     * @param project {Project}
     * */
    private static void finaliseProject (Project project){
        if(!project.finalDate.equalsIgnoreCase(NOT_COMPLETE)){
            Date currentDate = new Date();
            String currentDate1 = String.valueOf(currentDate);
            project.markAsFinal();
            project.finalDate = currentDate1;
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                statement.executeUpdate("UPDATE projects SET status = 'Final : " +
                        project.finalDate+"' WHERE projectName ='" + project.getProjectName()+"' " +
                        AND_ID + project.getProjectNumber());
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, ERROR + e);
            }
            JOptionPane.showMessageDialog(null, "Project has been marked as Final");
            int outstandingBalance;
            outstandingBalance = project.getOutstandingBalance();
            if(outstandingBalance>0){
                JOptionPane.showMessageDialog(null,
                        """
                                Generating Invoice
                                """);
                // build Client Person Object to get details
                String clientName = String.valueOf(project.clientDetails);
                clientName = "'"+clientName+"'";
                try(Connection connection = poisedPmsDatabase()){
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery("SELECT * FROM people WHERE type = 'Client' " +
                            "AND CONCAT(firstname,' ',surname) = "+clientName);
                    while(results.next()){
                    Person clientObject = new Person(results.getString(1),results.getString(2),
                            results.getString(3),results.getString(4),
                            results.getString(5),results.getString(6));
                    JOptionPane.showMessageDialog(null, "           _________Invoice_________\n\n"+
                            "Client Name             :"+clientObject.getName() +"\n" +
                            "Telephone Number   :"+clientObject.telNumber+"\n" +
                            "Email Address           :"+clientObject.emailAddress+"\n\n" +
                            "Finalisation Date  : "+project.finalDate+"\n" +
                            "**Amount Owed    : R"+project.getOutstandingBalance()+"\n\n" +
                            "Project Total      : R"+project.totalFee);
                    }
                }catch (SQLException e){
                    JOptionPane.showMessageDialog(null,ERROR + e);
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null,project.getProjectName() +
                    " has already been finalised!");
        }
    }

    /**
     * ___________________Update Project Option 3
     * Displays any outstanding balance on a project
     * @param project {Project}
     * */
    private static void displayProjectBalance (Project project){
        JOptionPane.showMessageDialog(null, "The current " +
                "outstanding balance on '" + project.getProjectName() + "' is R" +
                project.getOutstandingBalance());
    }

    /**
     * ___________________Update Project Option 2
     * Allows the user to update the due date on an active project
     * @param project {Project}
     * */
    private static void updateProjectDate (Project project){
        // Prevent the user from adding a date that has already passed

        // Replace date on project with date provided by user
        project.dueDate = JOptionPane.showInputDialog("Enter the new project due date " +
                "in format dd/mm/yyyy.");
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE projects SET dueDate =" +
                    project.paidToDate+" WHERE projectName ='" + project.getProjectName()+"' " +
                    AND_ID + project.getProjectNumber());
            JOptionPane.showMessageDialog(null,"Project's due date has been " +
                    "updated successfully!");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, ERROR + e);
        }
        JOptionPane.showMessageDialog(null, "Updated project " +
                "displayed in IDE");
        out.println(project);
        out.println();
    }

    /**
     * ___________________Update Project Option 1
     * Allows the user to update a payment amount towards an active project
     * @param project {Project}
     * */
    private static void updateProjectPayment (Project project){
        int addAmount = Integer.parseInt(JOptionPane.showInputDialog("Enter payment " +
                "amount (R)"));
        project.paidToDate = project.paidToDate + addAmount;
        // Update the project in db
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE projects SET projectPaid =" +
                    project.paidToDate+" WHERE projectName ='" + project.getProjectName()+"' " +
                    AND_ID + project.getProjectNumber());
            JOptionPane.showMessageDialog(null,"Project payments updated successfully!");
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,ERROR + e);
        }
        JOptionPane.showMessageDialog(null, "Updated project" +
                " displayed in IDE");
        // Present the user with the updated project
        out.println(project);
        out.println();
    }

    /**
     * Displays a list of projects
     */
    private static void getProjects () {
        int counter = 0;
        JOptionPane.showMessageDialog(null, "Displaying existing a list of projects in IDE");
        out.println(" CODE - PROJECT NAME\n" +
                "---------------------");
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM projects");
            while(results.next()){
                out.print(results.getString(1) + " - ");
                out.print(results.getString(2) + "\n");
                counter ++;
            }
            out.println();
        }
        catch(SQLException e){
        JOptionPane.showMessageDialog(null, "ERROR! \n " + e);
    }
        if(counter == 0){
            out.println("\n____There are no projects on record____");
            JOptionPane.showMessageDialog(null,"No projects on record");
        }
    }

    /**
     * ___________________Main Menu Option 1
     * The main method for obtaining details required to create a new project, and save it to database
     */
    private static void createNewProject (){
        String projectName = null;
        // Declare start for project number generator
        int projectNumber = generateUniqueProjectNumber();
        // Declare Object variables that will be used to create new People Objects
        Person newClient;
        Person newProjectManager;
        Person newArchitect;
        Person newStructuralEngineer;
        // Provide user with an option to personalise project name parameter in object
        int personaliseProject = JOptionPane.showConfirmDialog(null,
                """
                                _____ Create a new project_____

                        Would you like to personalise the project Name?""");
        // If user selects to personalise, they will input, otherwise generic name will be created
        if (personaliseProject == 0) {
            projectName = JOptionPane.showInputDialog("Enter the project name");
            // Capitalise the first letter of project name
            projectName = projectName.substring(0,1).toUpperCase() + projectName.substring(1);
        }
        // Obtain other parameter info in order to successfully initialise a new object
        String buildingType = JOptionPane.showInputDialog("Building type (eg. Flat, House, Apartment)");
        buildingType = buildingType.substring(0,1).toUpperCase() + buildingType.substring(1);
        String physicalAddress = JOptionPane.showInputDialog("Physical Address of Project.");
        int erfNumber = Integer.parseInt(JOptionPane.showInputDialog("ERF Number of property"));
        int totalFee = Integer.parseInt(JOptionPane.showInputDialog("Project cost to Client\n(R)"));
        int paidToDate = Integer.parseInt(JOptionPane.showInputDialog("Amounts paid by client (to date) " +
                "(R)"));
        String dueDate = JOptionPane.showInputDialog("""
                Expected Completion Date
                                format
                             dd/mm/yyyy
                            ** include '/'""");
        // Create a new client or link an existing client to the project
        newClient = getClientDetails(physicalAddress);
        while (newClient.firstName.equalsIgnoreCase("")){
            // To prevent blank objects
            newClient = getClientDetails(physicalAddress);
        }
        // Create a new project manager or link an existing project manager to the project
        newProjectManager = getProjectManagerDetails();
        while (newProjectManager.firstName.equalsIgnoreCase("")){
            // To prevent blank objects
            newProjectManager = getProjectManagerDetails();
        }
        // Create a new architect or link an existing architect to the project
        newArchitect = getArchitectDetails();
        while (newArchitect.firstName.equalsIgnoreCase("")){
            // To prevent blank objects, restart process
            newArchitect = getArchitectDetails();
        }
        // Create a new structural engineer or link an existing structural engineer to the project
        newStructuralEngineer = getStructuralEngineerDetails();
        while (newStructuralEngineer.firstName.equalsIgnoreCase("")){
            // To prevent blank objects, restart process
            newStructuralEngineer = getArchitectDetails();
        }
        // If project name was not personalised, compile a generic project name.
        if (personaliseProject == 1) {
            projectName = buildingType+ " " + newClient.surname;
            // Formality for Database Style
            projectName = projectName.substring(0,1).toUpperCase() + projectName.substring(1);
        }
        // Add project details to 'projects' table in database
        try (Connection connection = poisedPmsDatabase()){
            // Set the values for a new entry
            String insert = "INSERT INTO projects(id, projectName, buildingType, projectAddress, erf, projectCost," +
                    " projectPaid, dueDate, status, structuralEngineer, projectManager, architect, client) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement columnIndex = connection.prepareStatement(insert);
            columnIndex.setString(1,String.valueOf(projectNumber));
            columnIndex.setString(2,projectName);
            columnIndex.setString(3,buildingType);
            columnIndex.setString(4,physicalAddress);
            columnIndex.setString(5,String.valueOf(erfNumber));
            columnIndex.setString(6,String.valueOf(totalFee));
            columnIndex.setString(7,String.valueOf(paidToDate));
            columnIndex.setString(8,dueDate);
            columnIndex.setString(9, NOT_COMPLETE);
            columnIndex.setString(10,newStructuralEngineer.getName());
            columnIndex.setString(11,newProjectManager.getName());
            columnIndex.setString(12,newArchitect.getName());
            columnIndex.setString(13,newClient.getName());
            columnIndex.executeUpdate();
            connection.close();
            JOptionPane.showMessageDialog(null, "Project Created Successfully!");
            } catch (SQLException e){
            JOptionPane.showMessageDialog(null,ERROR +
                    "Description:\n" +
                    ""+e);
        }
        // Create a project Object and display details to user
        Project newProject = new Project(projectNumber, projectName, buildingType, physicalAddress,
                erfNumber, totalFee, paidToDate, dueDate, "Not complete",newStructuralEngineer.getName(),
                newArchitect.getName(), newClient.getName(), newProjectManager.getName());
        out.println(newProject);
        JOptionPane.showMessageDialog(null, "New project details displayed " +
                "in IDE");
    }

    /**
     * A method to build client-person object and presents objects getName()
     *
     * @param displayList {String}
     * @param clientType  {String}
     */
    private static int getPeopleList (String displayList, String clientType){
        String type = "";
        if(clientType.equalsIgnoreCase("  ___List of Clients___")){
            type = "'Client'";
        }
        if (clientType.equalsIgnoreCase("  ___List of Architects___")){
            type = "'Architect'";
        }
        if (clientType.equalsIgnoreCase("  ___List of Project Managers___")){
            type = "'Project Manager'";
        }
        if (clientType.equalsIgnoreCase("  ___List of Structural Engineers___")){
            type = ASSIGNMENT3;
        }
        JOptionPane.showMessageDialog(null, displayList);
        out.println();
        out.println(clientType);
        int clientsFound = 0;
        // Get all details from projects table where column type = 'Client'
        try(Connection connection = poisedPmsDatabase()){
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM people WHERE type = "+type);
            while(results.next()){
                // Create Object
                Person buildPerson = new Person(results.getString(1),results.getString(2),
                        results.getString(3), results.getString(4),results.getString(5),
                        results.getString(6));
                out.println(buildPerson.getName());
                clientsFound ++;
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, ERROR +e);
        }
        return clientsFound;
    }

    /**
     * Browses existing client-type people rows in SQL DB and pre-fills Object parameter in createNewProject
     * or allows the user to create a new 'Client' type record
     * @param physicalAddress {String}
     */
    private static Person getClientDetails (String physicalAddress){
        // Declare Variables required to build a client type Object
        String clientAssignment = "";
        String clientFirstName = "";
        String clientSurname = "";
        String clientTelNo = "";
        String clientEmailAd = "";
        int createNewClient = JOptionPane.showConfirmDialog(null, "Create a new client profile?");
        // User clicks yes to create a new client object
        if (createNewClient == 0) {
            // Prevent crash if user accidentally enters a string where numbers are expected
            try {
                clientAssignment = getASSIGNMENT();
                clientFirstName = String.valueOf(JOptionPane.showInputDialog("Client first name"));
                clientFirstName = clientFirstName.substring(0,1).toUpperCase() + clientFirstName.substring(1);
                clientSurname = String.valueOf(JOptionPane.showInputDialog("Client surname"));
                clientSurname = clientSurname.substring(0,1).toUpperCase() + clientSurname.substring(1);
                clientTelNo = String.valueOf(JOptionPane.showInputDialog(getTelMessage()));
                clientEmailAd = String.valueOf(JOptionPane.showInputDialog(getEmailAddress()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error!\nPlease enter numbers only");
                getClientDetails(physicalAddress);
            }
        }
        // User clicks No
        else {
            // Get a list of people from people Table in DB where type = client
            int clientsFound = getPeopleList("Client list in IDE", "  ___List of Clients___");
            if(clientsFound != 0){
                // Allow client to select a client
                String searchClient = JOptionPane.showInputDialog("Enter client name and surname");
                searchClient = "'"+searchClient+"'";
                // counter to determine when the user needs to be informed that their entry did not match entry in DB
                int matchCounter = 0;
                // Connect to DB to match search input
                try(Connection connection = poisedPmsDatabase()){
                    Statement statement = connection.createStatement();
                    ResultSet results = statement.executeQuery("SELECT * FROM people WHERE CONCAT(firstname,' ',surname) ="+
                            searchClient+ AND_TYPE_CLIENT);
                    while(results.next()){
                        // Assign the found column values to variables
                        clientAssignment = results.getString("type");
                        clientFirstName = results.getString(FIRSTNAME_COLUMN);
                        clientSurname = results.getString(SURNAME_COLUMN);
                        clientTelNo = results.getString(TELEPHONE);
                        clientEmailAd = results.getString(EMAIL);
                        matchCounter ++;
                        // Make user aware that process was successful
                        JOptionPane.showMessageDialog(null, clientFirstName + ' ' + clientSurname +
                                getHAS() + "been linked to this project as the Client");
                    }
                } catch (SQLException e){JOptionPane.showMessageDialog(null,ERROR + e);
                }
                // Inform user that there was no match
                if (matchCounter == 0) {
                    JOptionPane.showMessageDialog(null, "Client not found");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "No client details in database\n" +
                        "Please create a new client");
            }
        }
        return new Person(clientAssignment, clientFirstName, clientSurname, clientTelNo,
                clientEmailAd, physicalAddress);
    }

    /**
     * Browses existing project manager-type Person Object and pre-fills person Object parameter in createNewProject
     */
    private static Person getProjectManagerDetails() {
        // Declare Variables required to build a contractor type Object
        String assignment = "";
        String firstName = "";
        String surname = "";
        String telNo = "";
        String emailAd = "";
        String physicalAddress = "";
        int createNewProjectManager = JOptionPane.showConfirmDialog(null, "Create a new " +
                "Project Manager profile?");
        // User clicks Yes
        if (createNewProjectManager == 0) {
            assignment = getASSIGNMENT1();
            firstName = String.valueOf(JOptionPane.showInputDialog("Project Manager's first name"));
            firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1);
            surname = String.valueOf(JOptionPane.showInputDialog("Project Manager's surname"));
            surname = surname.substring(0,1).toUpperCase() + surname.substring(1);
            telNo = String.valueOf(JOptionPane.showInputDialog("Project Manager's " + getTelMessage()));
            emailAd = String.valueOf(JOptionPane.showInputDialog("Project Manager's " + getEmailAddress()));
            physicalAddress = String.valueOf(JOptionPane.showInputDialog("" +
                    "Project Manager's Address"));
        }
        // User clicks no or cancel
        else {
            getPeopleList("Project Manager list in IDE", "  ___List of Project Managers___"
            );
            // Get Project Manager List
            String searchProjectManager = JOptionPane.showInputDialog("Enter Project Manager's name and surname that you would " +
                    "like to link");
            searchProjectManager = "'" + searchProjectManager + "'";
            int duplicateCounter = 0;
            // Get and assign details based on search match
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(PROJECT_MANAGER_AND +
                        "CONCAT(firstname,' ',surname) =" + searchProjectManager);
                while(results.next()){
                    assignment = results.getString("type");
                    firstName = results.getString(FIRSTNAME_COLUMN);
                    surname = results.getString(SURNAME_COLUMN);
                    telNo = results.getString(TELEPHONE);
                    emailAd = results.getString(EMAIL);
                    duplicateCounter ++;
                    JOptionPane.showMessageDialog(null, firstName + ' ' + surname +
                            getHAS() + "been linked to this project as the Project Manager");
                }
            } catch (SQLException e){JOptionPane.showMessageDialog(null,ERROR + e);
            }
            if (duplicateCounter == 0) {
                JOptionPane.showMessageDialog(null, "Project Manager not found");
            }
        }
        return new Person(assignment, firstName, surname,
                telNo, emailAd, physicalAddress);
    }

    /**
     * Browses existing architect-type Person Object and pre-fills Architect Object parameter in createNewProject()
     */
    private static Person getArchitectDetails () {
        // Declare Variables required to build an architect type Object
        String assignment = "";
        String firstName = "";
        String surname = "";
        String telNo = "";
        String emailAd = "";
        String physicalAddress = "";
        int createNewArchitect = JOptionPane.showConfirmDialog(null, "Create a " +
                "new Architect profile?");
        if (createNewArchitect == 0) {
            assignment = getASSIGNMENT2();
            firstName = String.valueOf(JOptionPane.showInputDialog("Architect first name"));
            firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1);
            surname = String.valueOf(JOptionPane.showInputDialog("Architect surname"));
            surname = surname.substring(0,1).toUpperCase() + surname.substring(1);
            telNo = String.valueOf(JOptionPane.showInputDialog(getTelMessage()));
            emailAd = String.valueOf(JOptionPane.showInputDialog(getEmailAddress()));
            physicalAddress = String.valueOf(JOptionPane.showInputDialog("" +
                    "Architect Address"));
        }
        else {
            getPeopleList("Architect list in IDE", "  ___List of Architects___");
            String searchArchitect = JOptionPane.showInputDialog("Enter Architect name and surname you" +
                    " would like to link");
            searchArchitect = "'"+searchArchitect+"'";
            int duplicateCounter = 0;
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(TYPE_ARCHITECT_AND +
                        "CONCAT(firstname,' ',surname) =" + searchArchitect);
                while(results.next()){
                    assignment = results.getString("type");
                    firstName = results.getString(FIRSTNAME_COLUMN);
                    surname = results.getString(SURNAME_COLUMN);
                    telNo = results.getString(TELEPHONE);
                    emailAd = results.getString(EMAIL);
                    duplicateCounter ++;
                    JOptionPane.showMessageDialog(null, firstName + ' ' + surname +
                            getHAS() + "been linked to this project as the Project Manager");
                }
            } catch (SQLException e){JOptionPane.showMessageDialog(null,"Error!\n" + e);
            }
            if (duplicateCounter == 0) {
                JOptionPane.showMessageDialog(null, "No architect found");
            }
        }
        return new Person(assignment, firstName, surname,
                telNo, emailAd, physicalAddress);
    }

    /**
     * Browses existing Strucutal Engineer-type Person Object and pre-fills Architect Object parameter in createNewProject()
     */
    private static Person getStructuralEngineerDetails() {
        // Declare Variables required to build an architect type Object
        String assignment = "";
        String firstName = "";
        String surname = "";
        String telNo = "";
        String emailAd = "";
        String physicalAddress = "";
        int createNewStructuralEngineer = JOptionPane.showConfirmDialog(null, "Create a " +
                "new Structural Engineer profile?");
        if (createNewStructuralEngineer == 0) {
            assignment = getASSIGNMENT3();
            firstName = String.valueOf(JOptionPane.showInputDialog(getASSIGNMENT3() + "'s first name"));
            firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1);
            surname = String.valueOf(JOptionPane.showInputDialog(getASSIGNMENT3() + "'s surname"));
            surname = surname.substring(0,1).toUpperCase()+surname.substring(1);
            telNo = String.valueOf(JOptionPane.showInputDialog(getASSIGNMENT3() + "'s " +getTelMessage()));
            emailAd = String.valueOf(JOptionPane.showInputDialog(getASSIGNMENT3() + "'s " +getEmailAddress()));
            physicalAddress = String.valueOf(JOptionPane.showInputDialog("" +
                    getASSIGNMENT3() + "'s Address"));
        }
        else {
            getPeopleList("Structural Engineer list in IDE", "  ___List of Structural Engineer___"
            );
            String searchStructEng = JOptionPane.showInputDialog("Enter Structural Engineer name and surname you" +
                    " would like to link");
            searchStructEng = "'"+searchStructEng+"'";
            int matchCounter = 0;
            try(Connection connection = poisedPmsDatabase()){
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("SELECT * FROM people WHERE type = 'Structural Engineer' AND " +
                        " CONCAT(firstname,' ',surname) =" + searchStructEng);
                while(results.next()){
                    assignment = results.getString("type");
                    firstName = results.getString(FIRSTNAME_COLUMN);
                    surname = results.getString(SURNAME_COLUMN);
                    telNo = results.getString(TELEPHONE);
                    emailAd = results.getString(EMAIL);
                    matchCounter ++;
                    JOptionPane.showMessageDialog(null, firstName + ' ' + surname +
                            getHAS() + "been linked to this project as the Structural Engineer");
                }
            } catch (SQLException e){JOptionPane.showMessageDialog(null,"Error!\n" + e);
            }
            if (matchCounter == 0) {
                JOptionPane.showMessageDialog(null, "No architect found");
            }
        }
        return new Person(assignment, firstName, surname,
                telNo, emailAd, physicalAddress);
    }

    /**
     * Generates a unique project number
     * @return random.nextInt(99999) - any random number of up to 5 digits
     */
    private static int generateUniqueProjectNumber () {
        Random random = new Random();
        return random.nextInt(99999);
    }

    /* Getters */
    /**
     * Constant for string "Client"
     * @return Client type-assignment
     * */
    public static String getASSIGNMENT () {
        return ASSIGNMENT;
    }

    /**
     * Constant for string "Project Manager"
     * @return Contractor type-assignment
     * */
    public static String getASSIGNMENT1 () {
        return ASSIGNMENT1;
    }

    /**
     * Constant for string "Architect"
     * @return Architect type-assignment
     * */
    public static String getASSIGNMENT2 () {
        return ASSIGNMENT2;
    }
    /**
     * Constant for string "Structural Engineer"
     * @return Architect type-assignment
     * */
    public static String getASSIGNMENT3(){
        return ASSIGNMENT3;
    }

    /**
     * Constant for string "Telephone Number / Cellphone Number"
     * @return TEL_MESSAGE {String}
     */
    public static String getTelMessage () {
        return TEL_MESSAGE;
    }

    /**
     * Constant for string "Email Address"
     * @return EMAIL_ADDRESS {String}
     */
    public static String getEmailAddress () {
        return EMAIL_ADDRESS;
    }

    /**
     * Constant for string "has"
     * @return HAS {String}
     */
    public static String getHAS () {
        return HAS;
    }

    /**
     * Constant for string "Details displayed in IDE"
     * @return DISPLAYED_IN_IDE {String}
     */
    public static String getDisplayedInIde () {
        return DISPLAYED_IN_IDE;
    }
    /**
     * Constant for string "1) Search using name"
     * @return NAME {String}
     */
    public static String getNAME () {
        return NAME;
    }

    /**
     * Constant for string "Results will display in IDE"
     * @return DISPLAY_IN_IDE {String}
     */
    public static String getDisplayInIde () {
        return DISPLAY_IN_IDE;
    }

    /**
     * Constant for string "Index      Name"
     * @return INDEX_NAME {String}
     */
    public static String getIndexName () {
        return INDEX_NAME;
    }

    /**
     * Constant for string "successfully"
     * @return SUCCESSFULLY {String}
     */
    public static String getSUCCESSFULLY () {
        return SUCCESSFULLY;
    }
}
