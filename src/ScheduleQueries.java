


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kzy51
 */


public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentsByCourse;
    private static PreparedStatement getWaitlistedStudentsByCourse;
    private static PreparedStatement getWaitlistedStudent;
    private static PreparedStatement updateStudent;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement courseCodeByStudent;
    private static PreparedStatement semesterByStudent;
    private static PreparedStatement dropStudentAllSchedule;
    private static PreparedStatement updateScheduleEntry;
    private static PreparedStatement dropCourseStudentList;
    private static PreparedStatement getStatus;
    private static PreparedStatement getCourseCode;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry) 
    {
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, studentid, coursecode, status, timestamp) values (?,?,?,?,?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getStudent_ID());
            addScheduleEntry.setString(3, entry.getCourse_Code());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String student_ID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleEntry = new ArrayList<>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select semester, studentID, coursecode, status, timestamp from app.schedule where semester = ? and studentid = ?");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, student_ID);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
                scheduleEntry.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduleEntry;
        
    }
    
    public static ArrayList<String> getCourseCode(String semester, String student_ID)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<>();
        try
        {
            getCourseCode = connection.prepareStatement("select coursecode from app.schedule where semester = ? and studentID = ?");
            getCourseCode.setString(1, semester);
            getCourseCode.setString(2, student_ID);
            resultSet = getCourseCode.executeQuery();
            
            while(resultSet.next())
            {
                courseCodes.add(resultSet.getString(1));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
    }
    
    public static String getStatus(String semester, String student_ID, String coursecode)
    {
        connection = DBConnection.getConnection();
        String status = "";
        try{
            getStatus = connection.prepareStatement("select status from app.schedule where semester = ? and studentid = ? and coursecode = ?");
            getStatus.setString(1, semester);
            getStatus.setString(2, student_ID);
            getStatus.setString(3, coursecode);
            resultSet = getStatus.executeQuery();
            
            while (resultSet.next()){
                status = resultSet.getString(1);
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
        return status;
        
    }
    
    public static int getScheduledStudentCount(String semester, String coursecode) 
    {   

        connection = DBConnection.getConnection();
        int count = 0;
        try
        {
            PreparedStatement scheduledStudentCount = connection.prepareStatement("select studentID from app.schedule where semester = ? and coursecode = ?");
            scheduledStudentCount.setString(1, semester);
            scheduledStudentCount.setString(2, coursecode);
            resultSet = scheduledStudentCount.executeQuery();
            
            while (resultSet.next())
            {
                count = count + 1;
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return count;
    }
    
    
    public static ArrayList<StudentEntry> getScheduledStudentsByCourse(String semester, String coursecode){
        
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> ScheduledStudentsByCourse = new ArrayList<>();
        try
        {
            getScheduledStudentsByCourse = connection.prepareStatement("select firstname, lastname, studentID from app.schedule where semester = ? and coursecode = ? and status = ?");
            getScheduledStudentsByCourse.setString(1, semester);
            getScheduledStudentsByCourse.setString(2, coursecode);
            getScheduledStudentsByCourse.setString(3, "s");
            resultSet = getScheduledStudentsByCourse.executeQuery();
            
            while(resultSet.next())
            {
                ScheduledStudentsByCourse.add((StudentEntry) resultSet.getObject(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return ScheduledStudentsByCourse;
        
    }
    
    public static ArrayList<String> getScheduledStudentsByCourse_String(String semester, String coursecode){
        
        connection = DBConnection.getConnection();
        ArrayList<String> ScheduledStudentsByCourse = new ArrayList<>();
        try
        {
            getScheduledStudentsByCourse = connection.prepareStatement("select studentID from app.schedule where semester = ? and coursecode = ? and status = ?");
            getScheduledStudentsByCourse.setString(1, semester);
            getScheduledStudentsByCourse.setString(2, coursecode);
            getScheduledStudentsByCourse.setString(3, "s");
            resultSet = getScheduledStudentsByCourse.executeQuery();
            
            while(resultSet.next())
            {
                ScheduledStudentsByCourse.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return ScheduledStudentsByCourse;   
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String coursecode){
        
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> WaitlistedStudentsByCourse = new ArrayList<>();
        try
        {
            getWaitlistedStudentsByCourse = connection.prepareStatement("select semester, coursecode, studentID, status, timestamp from app.schedule where semester = ? and coursecode = ? and status = ?");
            getWaitlistedStudentsByCourse.setString(1, semester);
            getWaitlistedStudentsByCourse.setString(2, coursecode);
            getWaitlistedStudentsByCourse.setString(3, "w");
            resultSet = getWaitlistedStudentsByCourse.executeQuery();
            
            while(resultSet.next())
            {
                WaitlistedStudentsByCourse.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(3), resultSet.getString(2), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return WaitlistedStudentsByCourse;
        
    }
    
    public static ArrayList<String> getWaitlistedStudentsByCourse_String(String semester, String coursecode){
        
        connection = DBConnection.getConnection();
        ArrayList<String> WaitlistedStudentsByCourse = new ArrayList<>();
        try
        {
            getWaitlistedStudentsByCourse = connection.prepareStatement("select studentID from app.schedule where semester = ? and coursecode = ? and status = ?");
            getWaitlistedStudentsByCourse.setString(1, semester);
            getWaitlistedStudentsByCourse.setString(2, coursecode);
            getWaitlistedStudentsByCourse.setString(3, "w");
            resultSet = getWaitlistedStudentsByCourse.executeQuery();
            
            while(resultSet.next())
            {
                WaitlistedStudentsByCourse.add((String) resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return WaitlistedStudentsByCourse;
        
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String coursecode){
        connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and studentID = ? and coursecode = ?");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, coursecode);
            dropStudentScheduleByCourse.executeUpdate();
        }  
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }   
    
    
    //dropping the student across all schedules
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        try
        {
            dropStudentAllSchedule = connection.prepareStatement("delete from app.schedule where studentID = ?");
            dropStudentAllSchedule.setString(1, studentID);
            dropStudentAllSchedule.executeUpdate();
        }  
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }      
    
    
    public static void dropScheduleByCourse(String semester, String coursecode){
        connection = DBConnection.getConnection();
        try
        {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and coursecode = ?");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, coursecode);
            dropScheduleByCourse.executeUpdate();
        }  
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static void updateStudent(String semester, String coursecode, String studentID){
        connection = DBConnection.getConnection();
        try
        {
            updateStudent = connection.prepareStatement("update app.schedule set status = ? where semester = ? and coursecode = ? and studentid = ?");
            updateStudent.setString(1, "s");
            updateStudent.setString(2, semester);
            updateStudent.setString(3, coursecode);
            updateStudent.setString(4, studentID);
            updateStudent.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    
    public static ArrayList<String> semesterByStudent(String studentID){
        connection = DBConnection.getConnection();
        
        ArrayList<String> semester = new ArrayList<>();
        try
        {
            semesterByStudent = connection.prepareStatement("Select semester from app.schedule where studentid = ?");
            semesterByStudent.setString(1, studentID);
            resultSet = semesterByStudent.executeQuery();
            while(resultSet.next())
            {
                semester.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return semester;
    }
    
    public static ArrayList<String> courseCodeByStudent(String studentID){
        connection = DBConnection.getConnection();
        
        ArrayList<String> courses = new ArrayList<>();
        try
        {
            courseCodeByStudent = connection.prepareStatement("Select coursecode from app.schedule where studentid = ?");
            courseCodeByStudent.setString(1, studentID);
            resultSet = courseCodeByStudent.executeQuery();
            
            while(resultSet.next())
            {
                courses.add(resultSet.getString(1));
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static String getWaitlistedStudent(String semester, String coursecode){
        connection = DBConnection.getConnection();
        String student = null;
        try
        {
            getWaitlistedStudent = connection.prepareStatement("select studentid from app.schedule where semester = ? and coursecode = ? and status = ? order by timestamp");
            getWaitlistedStudent.setString(1, semester);
            getWaitlistedStudent.setString(2, coursecode);
            getWaitlistedStudent.setString(3, "w");
            resultSet = getWaitlistedStudent.executeQuery();
            if(resultSet.next())
            {
                student = resultSet.getString(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
    }
    
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try
        {
            updateScheduleEntry = connection.prepareStatement("update app.schedule set status = 's' where semester = ? and studentid = ? and coursecode = ?");
            updateScheduleEntry.setString(1, entry.getSemester());
            updateScheduleEntry.setString(3, entry.getCourse_Code());
            updateScheduleEntry.setString(2, entry.getStudent_ID());
            updateScheduleEntry.executeUpdate();
        }  
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<String> dropCourseStudentList(String semester, String coursecode){
        
        connection = DBConnection.getConnection();
        ArrayList<String> listStudents = new ArrayList<>();
        
        try{
            dropCourseStudentList = connection.prepareStatement("select studentID from app.schedule where semester = ? and coursecode = ?");
            dropCourseStudentList.setString(1, semester);
            dropCourseStudentList.setString(2, coursecode);
            resultSet = dropCourseStudentList.executeQuery();
            
            while(resultSet.next())
            {
                listStudents.add(resultSet.getString(1));
            }
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return listStudents;
    }
    
}
