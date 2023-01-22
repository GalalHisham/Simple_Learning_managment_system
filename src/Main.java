import org.json.simple.JSONObject;

import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) throws Exception {
        String StudentcsvFile = "src/CSV_Files/StudentDatafinal.csv";
        Xml2Csv.csvconverter("src/coursedata.xml","src/output.csv");
        Vector<Student> students = Student.get_students(StudentcsvFile);
        String CoursesCsvfile = "src/CSV_Files/output.csv";
        Vector<Course> courses = Course.get_courses(CoursesCsvfile);
        students.get(0).enroll_course(courses.get(0).courseid);
        students.get(0).enroll_course(courses.get(1).courseid);
        students.get(0).enroll_course(courses.get(2).courseid);
        students.get(0).enroll_course(courses.get(3).courseid);
        students.get(1).enroll_course(courses.get(1).courseid);
        students.get(1).enroll_course(courses.get(3).courseid);
        students.get(1).enroll_course(courses.get(5).courseid);
        students.get(2).enroll_course(courses.get(0).courseid);
        students.get(2).enroll_course(courses.get(2).courseid);
        students.get(2).enroll_course(courses.get(4).courseid);
        CreatingJSONDocument Jsonfile = new CreatingJSONDocument();
        JSONObject jsonObject = Jsonfile.jsonfile(students);

        // UI
        System.out.println("Welcome to LMS");
        System.out.println("created by Galal Hisham_jan.2023");
        System.out.println("====================================================================================");
        System.out.println("Home page");
        System.out.println("====================================================================================");
        System.out.println("Student List:");
        Datadisplay.csvdisplayer("src/CSV_Files/StudentDatafinal.csv");
        System.out.println("------------------------------------------------------------------------------------");

        String student_id;
        Student current_student;
        while (true){
            System.out.print("please select the required student: ");
            Scanner scanner = new Scanner(System.in);
            student_id = scanner.next();
            current_student = Student.get_student_by_id(students, student_id);
            if (current_student == null) {
                System.out.println("Invalid Student ID");
            }
            else{
                break;
            }
        }

        while (true){
            System.out.println("Home page");
            System.out.println("====================================================================================");
            System.out.println("Student details page");
            System.out.println("====================================================================================");
            System.out.print(current_student.name + "   ,   " + current_student.grade + "    ,    " + current_student.email);
            System.out.println();
            CreatingJSONDocument.Search_Json(student_id, students, courses);

            System.out.println("------------------------------------------------------------------------------------");
            System.out.println("Please choose from the following: ");
            System.out.println("enroll in a course : a");
            System.out.println("unenrollfrom an existing course: d");
            System.out.println("Replace an existing course: r");
            System.out.println("please select the required action:");
            Scanner sc = new Scanner(System.in);
            String Action1 = sc.next();
            switch (Action1) {
                case "a": {
                    System.out.println("Enrollment page");
                    System.out.println("====================================================================================================");
                    CreatingJSONDocument.Search_Json(student_id, students, courses);
                    System.out.println("Please make one of the following:\n");
                    Scanner scanner2 = new Scanner(System.in);
                    String Course_id;
                    boolean condition = true;
                    while (condition) {
                        System.out.println("Enter the course id that you want to enroll the student to\n" +
                                "Enter b to go back to the home page\n");
                        Course_id = scanner2.next();
                        if (Course_id.equals("b")) {
                            condition = false;
                        } else {
                            CreatingJSONDocument.enroll_in_course(student_id, Course_id, students, courses, jsonObject);
                        }
                    }
                    break;
                }
                case "d": {
                    System.out.println("enroll page");
                    System.out.println("====================================================================================================");
                    CreatingJSONDocument.Search_Json(student_id, students, courses);
                    System.out.println("Please make one of the following:\n");
                    Scanner scanner2 = new Scanner(System.in);
                    String Course_id;
                    boolean condition = true;
                    while (condition) {
                        System.out.println("Enter the course id that you want to unenroll the student to\n" +
                                "Enter b to go back to the home page\n");
                        Course_id = scanner2.next();
                        if (Course_id.equals("b")) {
                            condition = false;
                        } else {
                            CreatingJSONDocument.unenroll_in_course(student_id, Course_id, students, jsonObject);
                        }
                    }
                    break;
                }
                case "r": {
                    System.out.println("replacement page");
                    System.out.println("====================================================================================================");
                    CreatingJSONDocument.Search_Json(student_id, students, courses);
                    System.out.println("Please make one of the following:\n");
                    Scanner scanner2 = new Scanner(System.in);
                    String Course_id1;
                    String Course_id2;
                    boolean condition = true;
                    while (true) {
                        System.out.println("Enter the course id that you want to be replaced");
                        System.out.println("or Enter b to go back to the home page\n");
                        Course_id1 = scanner2.next();
                        while (true) {
                            if (Course_id1.equals("b")) {
                                condition = false;
                                break;
                            }
                            else if (!Student.get_student_by_id(students, student_id).courses.contains(Course_id1)) {
                                System.out.println("this student is not enrolled in this course");
                                System.out.println("Enter the course id that you want to be replaced");
                                System.out.println("or Enter b to go back to the home page\n");
                                Course_id1 = scanner2.next();
                            }
                            else {
                                //success
                                break;
                            }
                        }
                        if (!condition) {
                            break;
                        }
                        System.out.println("Available Courses");
                        Course.get_course_by_id(courses, Course_id1).display();
                        System.out.println("======================================");

                        System.out.println("Please enter the required course id to replace:");
                        System.out.println("or Enter b to go back to the home page\n");
                        Course_id2 = scanner2.next();
                        while (true) {
                            if (Course_id2.equals("b")) {
                                condition = false;
                                break;
                            } else if (Course.get_course_by_id(courses, Course_id2) == null) {
                                System.out.println("this course is not exist");
                                System.out.println("Enter the course id that you want to be replaced");
                                System.out.println("or Enter b to go back to the home page\n");
                                Course_id2 = scanner2.next();
                            } else if (Student.get_student_by_id(students,student_id).courses.contains(Course_id2)) {

                                System.out.println("Available Courses");
                                Course.get_course_by_id(courses, Course_id1).display();
                                System.out.println("======================================");

                                System.out.println("the student is already enrolled in this course");
                                System.out.println("Please enter the required course id to replace:");
                                System.out.println("or Enter b to go back to the home page\n");
                                Course_id2 = scanner2.next();

                            } else {
                                break;
                            }
                        }
                        if (!condition) {
                            break;
                        }
                        System.out.println("Available Courses");
                        Course.get_course_by_id(courses, Course_id2).display();
                        System.out.println("======================================");

                        CreatingJSONDocument.replace_course(student_id, Course_id1, Course_id2, students, jsonObject);
                        break;
                    }
                }
            }
        }
    }
}