
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static FileManager FileManager;

    public static void main(String[] args) {

        ArrayList<AidRequest> requests = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("  Smart Humanitarian Aid Tracker — غزة  ");
        System.out.println("===========================================");

        // Demo data
        Family f1 = new Family("أسرة أبو علي", "شمال غزة", "0599000001", 7, true, false);
        Family f2 = new Family("أسرة الشريف", "خان يونس", "0599000002", 3, false, true);
        Family f3 = new Family("أسرة حمدان", "رفح", "0599000003", 10, true, true);

        String[] types1 = {"غذاء", "ماء", "دواء"};
        int[] qty1 = {7, 7, 3};
        AidRequest r1 = new AidRequest(f1, types1, qty1);

        String[] types2 = {"غذاء", "مأوى"};
        int[] qty2 = {3, 1};
        UrgentRequest r2 = new UrgentRequest(f2, types2, qty2, "إصابة بالغة", 9);

        String[] types3 = {"غذاء", "ماء", "ملابس", "وقود"};
        int[] qty3 = {10, 10, 10, 5};
        AidRequest r3 = new AidRequest(f3, types3, qty3);

        requests.add(r1);
        requests.add(r2);
        requests.add(r3);

        // Display using loop
        System.out.println("\nقائمة الطلبات المسجلة:");
        System.out.println("-------------------------------------------");
        for (AidRequest req : requests) {
            System.out.println(req.getSummary());
            System.out.println("  نقاط الأولوية: " + req.getPriorityScore());
        }

        // Sort by priority using Arrays (simple bubble sort)
        AidRequest[] arr = requests.toArray(new AidRequest[0]);
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j].getPriorityScore() < arr[j+1].getPriorityScore()) {
                    AidRequest temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }

        System.out.println("\nترتيب الطلبات حسب الأولوية:");
        for (AidRequest req : arr) {
            System.out.println(req.getFamily().getName() + " → " + req.getPriorityScore());
        }

        // Save to file
        FileManager.saveRequests(requests);
        FileManager.exportReport(requests, "report.txt");

        System.out.println("\nلتشغيل الواجهة الرسومية: شغّل MainGUI.java");

        // Launch GUI
        // gui.MainGUI.main(args);

        scanner.close();
    }
}