


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

public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement addCourse;
    private static ResultSet resultSet;
    private static String description;
    
    
    public static ArrayList<CourseEntry> getAllCourses(String currentSemester)
    {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<>();
        try 
        {
            PreparedStatement getCourses = connection.prepareStatement("select * from app.course where semester = ?");
            getCourses.setString(1, currentSemester);
            resultSet = getCourses.executeQuery();

            while(resultSet.next())
            {           
                courses.add((CourseEntry) resultSet.getObject(1));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
    
       
    }


    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse= connection.prepareStatement("insert into app.course (semester, coursecode, description, seats) values (?,?,?,?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

    }

    
    public static ArrayList<String> getAllCourseCodes(String currentSemester)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> coursecode = new ArrayList<>();
           try 
           {
               PreparedStatement getCourseCodes = connection.prepareStatement("select coursecode from app.course where semester = ?");
               getCourseCodes.setString(1, currentSemester);
               resultSet = getCourseCodes.executeQuery();
               while(resultSet.next())
               {
                   coursecode.add(resultSet.getString(1));
               }

           }
           catch(SQLException sqlException)
           {
               sqlException.printStackTrace();
           }
           return coursecode;
    }
    
    public static int getCourseSeats(String currentSemester, String courseCode)
    {
        connection = DBConnection.getConnection();
        int seats = 0;
        
        try
        {
            PreparedStatement courseSeats = connection.prepareStatement("select seats from app.course where semester = ? and coursecode = ?");
            courseSeats.setString(1, currentSemester);
            courseSeats.setString(2, courseCode);
   
            resultSet = courseSeats.executeQuery();
            while (resultSet.next())
            {
                seats = resultSet.getInt(1);
           
            }
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        
        return seats;
    }
    
    public static String getCourseDescription(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        
        try
        {
            PreparedStatement getCourseDescription = connection.prepareStatement("select description from app.course where semester = ? and coursecode = ?");
            getCourseDescription.setString(1, semester);
            getCourseDescription.setString(2, courseCode);
            
            resultSet = getCourseDescription.executeQuery();
            while (resultSet.next())
            {
                description = resultSet.getString(1);
            }
            
        } 
        
        catch(SQLException sqlException){
             sqlException.printStackTrace();
        }
        
        return description;
    
    }

    public static void dropCourse(String semester, String courseCode) 
    {
        connection = DBConnection.getConnection();
        try
        {
            PreparedStatement dropCourse = connection.prepareStatement("delete from app.course where semester = ? and coursecode = ?");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            dropCourse.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

 
}
