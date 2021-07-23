package StudentManager;

import java.sql.*;
import java.util.Arrays;

public class StudentManager
{
    private Connection connection;
    private Statement st;

    public StudentManager() throws SQLException
    {
        connection = DriverManager.getConnection("jdbc:h2:./dataBase", "sa", "qwe");
        st = connection.createStatement();
    }

    public void addStudent(String firstName, String lastName) throws SQLException
    {
        st.executeUpdate("insert into STUDENTS (FIRSTNAME, LASTNAME) VALUES ('" + firstName + "', '" + lastName + "' )");
    }

    public void removeStudent(String firstName, String lastName) throws SQLException
    {
        st.executeUpdate("delete from STUDENTS where FIRSTNAME = '" + firstName +"' and LASTNAME = '" + lastName + "'");
    }

    public void addCourse(String studentFirstName, String studentLastName, String courseTitle, String grade) throws SQLException
    {
        st.executeUpdate("insert into STUDENTCOURSES (ID, COURSETITLE, GRADE)\n" +
                "values (\n" +
                "        (select id from STUDENTS where FIRSTNAME = '" + studentFirstName + "' and LASTNAME = '" + studentLastName + "'),\n" +
                "        '" + courseTitle + "',\n" +
                "        '" + grade + "'\n" +
                "       )");
    }

    public int getStudentID(String firstName, String lastName) throws SQLException
    {
        ResultSet rs = st.executeQuery("select id from STUDENTS where FIRSTNAME = '" + firstName + "' and LASTNAME = '" + lastName + "'");
        rs.next();
        return rs.getInt("id");
    }

    public String getGrade(String studentFirstName, String studentLastName, String courseTitle) throws SQLException
    {
        int id = getStudentID(studentFirstName, studentLastName);
        ResultSet rs = st.executeQuery("select GRADE from STUDENTCOURSES where COURSETITLE = '" + courseTitle + "' and id = " + id);
        rs.next();
        return rs.getString("grade");
    }

    public void close() throws SQLException
    {
        connection.close();
    }
}

