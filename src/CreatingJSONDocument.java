import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
public class CreatingJSONDocument {
    public static JSONObject jsonfile(Vector<Student> students) {
        //Creating a JSONObject object
        JSONObject jsonObject = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonObject.put(students.get(0).id, students.get(0).courses);
        jsonObject.put(students.get(1).id, students.get(1).courses);
        jsonObject.put(students.get(2).id, students.get(2).courses);
        try {
            FileWriter file = new FileWriter("E:/output.json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static void Search_Json(String userid, Vector<Student> students, Vector<Course> courses) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
//        System.out.print("please enter student id: ");
        try {
            int ID = Integer.parseInt(userid);
            Student current_student = Student.get_student_by_id(students, userid);
            if (current_student == null) {
                System.out.println("Invalid Student ID");
            } else {
                System.out.println("------------------------------------------------------------------------------------");
                System.out.println("Student enrolled courses");
                System.out.println("====================================================================================");
                try (FileReader reader = new FileReader("E:/output.json")) {
                    //Read JSON file
                    JSONObject obj = (JSONObject) jsonParser.parse(reader);
                    JSONArray student_courses = (JSONArray) obj.get(userid);
                    if (student_courses != null) {
                        System.out.println();

                        for (int i = 0; i < (courses.size()); i++) {
                            if (i == student_courses.size()) {
                                break;
                            }
                            //System.out.println("Student enrolled courses");
                            //System.out.println((i + 1) + "-");
                            System.out.print(courses.get(Integer.parseInt(student_courses.get(i).toString()) - 1).courseid + " ,");
                            System.out.print(courses.get(Integer.parseInt(student_courses.get(i).toString()) - 1).coursename + " ,");
                            System.out.print(courses.get(Integer.parseInt(student_courses.get(i).toString()) - 1).instructor + ", ");
                            System.out.print(courses.get(Integer.parseInt(student_courses.get(i).toString()) - 1).courseduration + ", ");
                            System.out.print(courses.get(Integer.parseInt(student_courses.get(i).toString()) - 1).coursetime + " ,");
                            System.out.println(courses.get(Integer.parseInt(student_courses.get(i).toString()) - 1).location);
                        }
                    } else {
                        System.out.println("This student hasn't enrolled in any courses");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid UserID");
        }

    }
    public static void enroll_in_course(String Student_id, String Course_id, Vector<Student> students, Vector<Course> courses, JSONObject jsonObject){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("E:/output.json")) {
            //Read JSON file
//            JSONObject jsonObject = new JSONObject();
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray student_courses = (JSONArray) obj.get(Student_id);
//            System.out.println(student_courses);

           if(student_courses == null){
               Student current =Student.get_student_by_id(students,Student_id);
               current.enroll_course(Course_id);
               jsonObject.put(students.get(Integer.parseInt(Student_id)-1).id,current.courses);
               try {
                   FileWriter file = new FileWriter("E:/output.json");
                   file.write(jsonObject.toJSONString());
                   file.close();
               }
               catch (IOException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }
           }

            else if (Course.get_course_by_id(courses, Course_id) == null){
                System.out.println("this course doesn't exist");
            }
            else if(!student_courses.contains(Course_id)){
                if(student_courses.size() >= 6){
                    System.out.println("the student has reached the maximum number of courses");
                }
                else {
                    Student current =Student.get_student_by_id(students,Student_id);
                    current.enroll_course(Course_id);
                    jsonObject.put(students.get(Integer.parseInt(Student_id)-1).id,current.courses);
//                    jsonObject.put(students.get(Integer.parseInt(Student_id)-1).id,"9");
                    try {
                        FileWriter file = new FileWriter("E:/output.json");
                        file.write(jsonObject.toJSONString());
                        file.close();
                    }
                    catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            else  {
                System.out.println("the student has already enrolled in this course");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static void unenroll_in_course(String Student_id, String Course_id, Vector<Student> students, JSONObject jsonObject){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("E:/output.json")) {
            //Read JSON file
//            JSONObject jsonObject = new JSONObject();
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray student_courses = (JSONArray) obj.get(Student_id);
//            System.out.println(student_courses);

//            if(student_courses == null){
//                System.out.println("this student isn't enrolled in any courses yet");
//                //                Student current =Student.get_student_by_id(students,Student_id);
////                current.enroll_course(Course_id);
////                jsonObject.put(students.get(Integer.parseInt(Student_id)-1).id,current.courses);
////                try {
////                    FileWriter file = new FileWriter("E:/output.json");
////                    file.write(jsonObject.toJSONString());
////                    file.close();
////                }
////                catch (IOException e) {
////                    // TODO Auto-generated catch block
////                    e.printStackTrace();
////                }
//            }
             if (student_courses == null){
                System.out.println("this student isn't enrolled in any courses yet");
            }
            else if(student_courses.contains(Course_id)){
                Student current = Student.get_student_by_id(students,Student_id);
                current.unenroll_course(Course_id);
                jsonObject.put(students.get(Integer.parseInt(Student_id)-1).id,current.courses);
//                    jsonObject.put(students.get(Integer.parseInt(Student_id)-1).id,"9");
                try {
                    FileWriter file = new FileWriter("E:/output.json");
                    file.write(jsonObject.toJSONString());
                    file.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else  {
                System.out.println("the student is not enrolled in this course");
            }
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void replace_course(String Student_id, String Course_id1, String Course_id2, Vector<Student> students, JSONObject jsonObject){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("E:/output.json")) {
            //Read JSON file
//            JSONObject jsonObject = new JSONObject();
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray student_courses = (JSONArray) obj.get(Student_id);
//            System.out.println(student_courses);
            if (student_courses.size() == 0){
                System.out.println("this student isn't enrolled in any courses yet");
            }
            if(student_courses.contains(Course_id1)){
                Student current = Student.get_student_by_id(students,Student_id);
                current.replace_course(Course_id1,Course_id2);
                jsonObject.put(students.get(Integer.parseInt(Student_id)-1).id,current.courses);
//                    jsonObject.put(students.get(Integer.parseInt(Student_id)-1).id,"9");
                try {
                    FileWriter file = new FileWriter("E:/output.json");
                    file.write(jsonObject.toJSONString());
                    file.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else  {
                System.out.println("the student is not enrolled in this course");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

