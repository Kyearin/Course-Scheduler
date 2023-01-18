


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kzy51
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentQueries {
    
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getFirstName;
    private static PreparedStatement getLastName;
    private static PreparedStatement getStudent;
    private static ResultSet resultSet;
    

    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studenTid, firstname, lastname) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
                  
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> studentEntry = new ArrayList<>();
        try
        {
            PreparedStatement getStudents = connection.prepareStatement("select * from app.student");
            resultSet = getStudents.executeQuery();
            
            while(resultSet.next())
            {
                studentEntry.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return studentEntry;
        
    }  
   
   public static String getFirstName(String studentID){
        connection = DBConnection.getConnection();
        String firstName = null;
        try{
            getFirstName = connection.prepareStatement("select firstname from app.student where studentid = ?");
            getFirstName.setString(1, studentID);
            resultSet = getFirstName.executeQuery();
            while(resultSet.next()){
                firstName = resultSet.getString(1);
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return firstName;
    }
    
    public static String getLastName(String studentID){
        connection = DBConnection.getConnection();
        String lastName = null;
        try{
            getLastName = connection.prepareStatement("select lastname from app.student where studentid = ?");
            getLastName.setString(1, studentID);
            resultSet = getLastName.executeQuery();
            while(resultSet.next()){
                lastName = resultSet.getString(1);
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return lastName;
    }
    
   public static ArrayList<String> getAllStudentID()
   {
        connection = DBConnection.getConnection();
        ArrayList<String> studentID = new ArrayList<>();
        try
        {
            PreparedStatement getStudentID = connection.prepareStatement("select studentID from app.student");
            resultSet = getStudentID.executeQuery();
 
            
            while(resultSet.next())
           {
               studentID.add(resultSet.getString(1));
           }

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return studentID;
   }
   
   public static StudentEntry getStudent(String studentID)
   {
        connection = DBConnection.getConnection();
        StudentEntry student = null;
        try 
        {
            getStudent = connection.prepareStatement("select * from app.student where studentid = ?");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            
            while(resultSet.next())
            {
                student = new StudentEntry (resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
       
   }
   
   
   public static void dropStudent(String studentID)
   {
        connection = DBConnection.getConnection();
        try
        {
            PreparedStatement dropStudent = connection.prepareStatement("delete from app.student where studentid = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
   }


}
