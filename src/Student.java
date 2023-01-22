import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class Student {
    public static final String delimiter = (",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

    public Student(String id, String name, String grade, String email, String address, String region, String country) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.email = email;
        this.address = address;
        this.region = region;
        this.country = country;
    }

    public  String id ;
    public   String name ;
    public  String grade ;
    public   String email;
    public  String address ;
    public  String region ;
    public   String country ;
    public  Vector<String> courses = new Vector<String>();


    public void display(){
        System.out.println(id+" "+name+" "+grade+" "+email+" "+address+" "+region+" "+country);
    }

    public static Student get_student_by_id(Vector<Student> students, String userid){
        for (int i = 0; i< students.size();i++){
            if(students.get(i).id.equals(userid)){
                return students.get(i);
            }
        }
        return null;
    }
    public static Vector<Student> get_students(String csvFile) {
        Vector<Student> students =new Vector<Student>();

        try {
            int counter = 0;
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;
            while ((line = br.readLine()) != null) {
                tempArr = line.split(delimiter);
                if (tempArr[0].equals("id")) {
                    continue;
                }
                Student student= new Student(tempArr[0],tempArr[1],tempArr[2],tempArr[3],tempArr[4],tempArr[5],tempArr[6]);
                students.add(student);
                counter++;
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return students;
    }
//    public static void load_student_courses(String path, Vector<Student> students){
//        JSONParser jsonParser = new JSONParser();
//        try (FileReader reader = new FileReader(path)) {
//            //Read JSON file
//            JSONObject jsonObject = new JSONObject();
//            JSONObject obj = (JSONObject) jsonParser.parse(reader);
//            Set keys = obj.keySet();
//            Iterator<String> it = keys.iterator();
//            System.out.println(obj.values());
//            while(it.hasNext()) {
//                System.out.println(it.next());
//            }
////            for (int i=0;i< keys.size();i++){
////                System.out.println(keys.);
////                //System.out.println(obj.get(obj.keySet()[0]));
////                //Student.get_student_by_id(students, id)
////            }
////            JSONArray student_courses = (JSONArray) obj.get(Student_id);
////            System.out.println(student_courses);
//
//        }
//        catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Integer enroll_course(String enrolled_course){
        // 1 --> size > 6
        // 2 --> exist
        // 3 --> success
        if (courses.size() < 6 && !courses.contains(enrolled_course)) {
            courses.add(enrolled_course);
            return 3;
        }
        else if(courses.size() < 6)
           return 1;
        else {
            return 2;
        }
    }
    public Integer unenroll_course(String enrolled_course){
        // 2 --> not exist
        // 3 --> success
        if (courses.contains(enrolled_course)) {
            courses.remove(enrolled_course);
            return 3;
        }
        else {
            return 2;
        }
    }
    public Integer replace_course(String to_remove,String to_add){
        // 2 --> not exist
        // 3 --> success
        if (courses.contains(to_remove)&& !courses.contains(to_add)) {
            courses.remove(to_remove);
            courses.add(to_add);
            return 3;
        }
      else {
            return 2;
        }

    }
    public static void display_students(Vector<Student> students){
        for(int i = 0; i< students.size(); i++){
            students.get(i).display();
        }
    }

}

