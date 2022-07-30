/**
 *  This class defines a Project Object for 'Poised' based on data stored per project
 */
public class Project {
    // Attributes containing data held by Poised for each project
    int projectNumber;
    String projectName;
    String buildingType;
    String projAddress;
    int erfNumber;
    int totalFee;
    int paidToDate;
    String dueDate;
    String finalDate;
    String structuralEngineerDetails;
    String projectManagerDetails;
    String architectDetails;
    String clientDetails;
    boolean projectFinal = false;

    /**
     * Constructor Method
     * @param projectNumber {int}
     * @param projectName {String}
     * @param buildingType {String}
     * @param projAddress {String}
     * @param erfNumber {int}
     * @param totalFee {int}
     * @param paidToDate {int}
     * @param dueDate {String}
     * @param finalDate {String}
     * @param architectDetails {Object}
     * @param clientDetails {Object}
     * @param projectManagerDetails {Object}
     */
    public Project(int projectNumber, String projectName, String buildingType, String projAddress, int erfNumber,
                   int totalFee, int paidToDate, String dueDate, String finalDate,String structuralEngineerDetails,
                   String architectDetails, String clientDetails , String projectManagerDetails){
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.buildingType = buildingType;
        this.projAddress = projAddress;
        this.erfNumber = erfNumber;
        this.totalFee = totalFee;
        this.paidToDate = paidToDate;
        this.dueDate = dueDate;
        this.finalDate = finalDate;
        this.structuralEngineerDetails = structuralEngineerDetails;
        this.clientDetails = clientDetails;
        this.architectDetails = architectDetails;
        this.projectManagerDetails = projectManagerDetails;
    }

    /**
     * Gets a string Representation of the project
     * @return output {String}
     */
    public String toString(){
        String output = "\n\t\t___Project Details___\n";
        output += "project #            : " + projectNumber + " (" + projectName+")\n";
        output += buildingType + " - " + projAddress +"\n" + "ERF Number           : " +erfNumber+ "\n";
        output += "Total Fee            : R " + totalFee +"\n";
        output += "Paid to Date         : R " + paidToDate + "\n";
        output += "project Due Date     : " + dueDate +"\n";
        if (!projectFinal){
            output += "project Status       : In progress\n";
        }
        else
        {
            output += "project Status       : Final\n";
        }
        output += "Completed Date       : " + finalDate + "\n";
        output += "Architect            : " + architectDetails + "\n";
        output += "Client               : " + clientDetails +"\n";
        output += "Project Manager      : " + projectManagerDetails + "\n";
        output += "Structural Engineer  : " + structuralEngineerDetails +"\n";
        output += "";
        return output;
    }

    /**
     * Updates Project Boolean projectFinal to true (complete)
     */
    public void markAsFinal()
    {
        projectFinal = true;
    }

    /* Getters */
    /**
     * Gets the projects name
     * @return (projectName)
     */
    public String getProjectName(){ return projectName;}

    /**
     * Gets the project number
     * @return (projectNumber)
     */
    public int getProjectNumber(){return projectNumber;}

    /**
     * Gets the outstanding balance of a project
     * @return (totalFee - paidToDate)
     */
    public int getOutstandingBalance(){return (totalFee - paidToDate);}

    /** Gets the full names of each assigned person*/
    /**
     * Gets Structural Engineers name
     * @return structuralEngineerDetails {String}
     */
    public String getStrucEng(){return structuralEngineerDetails;}

    /**
     * Gets Architect name
     * @return architectDetails {String}
     */
    public String getArchitect(){return architectDetails;}

    /**
     * Gets Clients name
     * @return clientDetails {String}
     */
    public String getClient(){return clientDetails;}

    /**
     * Gets Project Manager's same
     * @return projectManagerDetails {String}
     */
    public String getProjManager(){return projectManagerDetails;}
}
