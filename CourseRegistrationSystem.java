
package main78;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 1. Student Sınıfı
class Student {
    private String studentID;
    private String name;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    // Öğrenci bilgilerini yazdıran metot[cite: 2]
    public void printStudentInfo() {
        System.out.println("Öğrenci ID: " + studentID + ", İsim: " + name);
    }
}

// 2. Course Sınıfı
class Course {
    private String courseName;
    private int capacity;
    private List<Student> enrolledStudents;

    public Course(String courseName, int capacity) {
        this.courseName = courseName;
        this.capacity = capacity;
        this.enrolledStudents = new ArrayList<>(); // Boş kayıt listesi ile başlatılır[cite: 1]
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    // Ders bilgilerini ve kayıtlı öğrencileri gösteren metot[cite: 1, 3]
    public void displayCourseInfo() {
        System.out.println("Ders Adı: " + courseName);
        System.out.println("Kapasite: " + capacity);
        System.out.println("Kayıtlı Öğrenci Sayısı: " + enrolledStudents.size());
        System.out.println("Kayıtlı Öğrenciler:");
        if (enrolledStudents.isEmpty()) {
            System.out.println("  Henüz kayıtlı öğrenci yok.");
        } else {
            for (Student s : enrolledStudents) {
                System.out.print("  - ");
                s.printStudentInfo();
            }
        }
        System.out.println("-----------------------------------");
    }
}

public class CourseRegistrationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Course> courses = new ArrayList<>();

        // 3. Course Setup (Ders Kurulumu)
        System.out.print("Kaç ders oluşturmak istiyorsunuz? (En az 3): ");
        int courseCount = scanner.nextInt();
        while (courseCount < 3) {
            System.out.print("Lütfen en az 3 giriniz: ");
            courseCount = scanner.nextInt();
        }
        scanner.nextLine(); // Satır sonu karakterini temizle

        for (int i = 0; i < courseCount; i++) {
            System.out.println("\n--- Ders " + (i + 1) + " ---");
            System.out.print("Ders adını girin: ");
            String cName = scanner.nextLine();
            System.out.print("Sınıf kapasitesini girin: ");
            int cCapacity = scanner.nextInt();
            scanner.nextLine(); // Satır sonu karakterini temizle

            Course course = new Course(cName, cCapacity);
            courses.add(course); // Ders listesine ekleme[cite: 1]
        }

        // Oluşturulan derslerin listesini yazdırma[cite: 1]
        System.out.println("\n=== Kayıt İçin Mevcut Dersler ===");
        for (Course c : courses) {
            System.out.println("- " + c.getCourseName() + " (Kapasite: " + c.getCapacity() + ")");
        }

        // 4 & 5. Öğrenci Kaydı ve Dersleri Görüntüleme Menüsü
        boolean running = true;
        while (running) {
            System.out.println("\nİşlem Seçiniz:");
            System.out.println("1. Öğrenci Kaydı Yap");
            System.out.println("2. Tüm Dersleri ve Kayıtları Görüntüle");
            System.out.println("3. Çıkış");
            System.out.print("Seçiminiz: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Satır sonu temizliği

            if (choice == 1) {
                System.out.print("Öğrenci ID girin: ");
                String sID = scanner.nextLine();
                System.out.print("Öğrenci adı girin: ");
                String sName = scanner.nextLine();
                System.out.print("Kayıt olmak istenen dersin tam adını girin: ");
                String targetCourseName = scanner.nextLine();

                // Dersi arama
                Course foundCourse = null;
                for (Course c : courses) {
                    if (c.getCourseName().equalsIgnoreCase(targetCourseName)) {
                        foundCourse = c;
                        break;
                    }
                }

                // Ders bulunamadıysa[cite: 4]
                if (foundCourse == null) {
                    System.out.println("No course found with that name.");
                } else {
                    // Kapasite kontrolü[cite: 4]
                    if (foundCourse.getEnrolledStudents().size() >= foundCourse.getCapacity()) {
                        System.out.println("This course is full. Registration failed.");
                    } else {
                        // Aynı ID ile çift kayıt (duplicate) kontrolü[cite: 4]
                        boolean alreadyRegistered = false;
                        for (Student s : foundCourse.getEnrolledStudents()) {
                            if (s.getStudentID().equals(sID)) {
                                alreadyRegistered = true;
                                break;
                            }
                        }

                        if (alreadyRegistered) {
                            System.out.println("This student is already registered for this course.");
                        } else {
                            // Başarılı kayıt[cite: 4]
                            Student newStudent = new Student(sID, sName);
                            foundCourse.getEnrolledStudents().add(newStudent);
                            System.out.println("Registration successful.");
                        }
                    }
                }
            } else if (choice == 2) {
                // Tüm derslerin ve kayıtlı öğrencilerin görüntülenmesi[cite: 3]
                System.out.println("\n=== Tüm Ders Bilgileri ===");
                for (Course c : courses) {
                    c.displayCourseInfo();
                }
            } else if (choice == 3) {
                running = false;
                System.out.println("Programdan çıkılıyor.");
            } else {
                System.out.println("Geçersiz seçim, lütfen tekrar deneyin.");
            }
        }
        scanner.close();
    }
}