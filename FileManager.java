


import java.io.*;
import java.util.ArrayList;

public class FileManager {
    private static final String FILE_PATH = "aid_requests.txt";

    // Save all requests to file
    public static void saveRequests(ArrayList<AidRequest> requests) {
        try (FileWriter fw = new FileWriter(FILE_PATH);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write("=== Smart Humanitarian Aid Tracker ===");
            bw.newLine();
            bw.write("إجمالي الطلبات: " + requests.size());
            bw.newLine();
            bw.write("---");
            bw.newLine();

            for (AidRequest req : requests) {
                bw.write(req.getRequestId() + "|"
                        + req.getFamily().getName() + "|"
                        + req.getFamily().getLocation() + "|"
                        + req.getStatus() + "|"
                        + req.getPriorityScore());
                bw.newLine();
            }

            System.out.println("✓ تم حفظ " + requests.size() + " طلب في الملف.");

        } catch (IOException e) {
            System.err.println("خطأ في حفظ الملف: " + e.getMessage());
        }
    }

    // Load request IDs and statuses from file
    public static void loadAndPrintRequests() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("لا يوجد ملف محفوظ.");
            return;
        }

        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println("خطأ في قراءة الملف: " + e.getMessage());
        }
    }

    // Export summary report
    public static void exportReport(ArrayList<AidRequest> requests, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("تقرير توزيع المساعدات - Smart Aid Tracker");
            pw.println("==========================================");

            int pending = 0, approved = 0, delivered = 0;
            for (AidRequest r : requests) {
                switch (r.getStatus()) {
                    case "قيد الانتظار": pending++; break;
                    case "موافق عليه":   approved++; break;
                    case "تم التسليم":   delivered++; break;
                }
                pw.println(r.getSummary());
            }

            pw.println("------------------------------------------");
            pw.println("قيد الانتظار: " + pending);
            pw.println("موافق عليه:   " + approved);
            pw.println("تم التسليم:   " + delivered);

        } catch (IOException e) {
            System.err.println("خطأ في التصدير: " + e.getMessage());
        }
    }


}
