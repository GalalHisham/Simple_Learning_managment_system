import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Course {
    public static final String delimiter = (",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
    public  String courseid;
    public  String coursename;
    public String instructor;
    public  String courseduration;
    public  String coursetime;
    public  String location;

    public Course(String courseid, String coursename, String instructor, String courseduration, String coursetime, String location) {
        this.courseid = courseid;
        this.coursename = coursename;
        this.instructor = instructor;
        this.courseduration = courseduration;
        this.coursetime = coursetime;
        this.location = location;
    }
//     public String[] enrolledcourses1 =[
//    public enrolledcourses1[]
    public void display(){
        System.out.println(courseid+" "+coursename+" "+instructor+" "+courseduration+" "+coursetime+" "+location);
    }

    public static Vector<Course> get_courses(String csvFile) {
        Vector<Course> courses = new Vector<Course>();
        try {int counter =0;
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;
            while((line = br.readLine()) != null) {
                tempArr = line.split(delimiter);
                if(tempArr[0].equals("id")){
                    continue;
                }
                Course course = new Course(tempArr[0],tempArr[1],tempArr[2],tempArr[3],tempArr[4],tempArr[5]);
                courses.add(course);

                counter++;
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return courses;
    }
    public static void display_courses(Vector<Course> courses) {
        for (int i = 0; i<courses.size();i++){
            courses.get(i).display();
        }

    }
    public static Course get_course_by_id(Vector<Course> courses, String course_id){
        for (int i = 0; i< courses.size();i++){
            if(courses.get(i).courseid.equals(course_id)){
                return courses.get(i);
            }
        }
        return null;
    }
}
