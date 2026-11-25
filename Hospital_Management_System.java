
import java.util.*;

class Patient {
    int id, age, severity, bed = -1;
    String name, disease;

    Patient(int id, String name, int age, String disease, int severity) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.severity = severity;
    }

    void display() {
        System.out.println("ID: " + id + ", Name: " + name +
            ", Age: " + age + ", Disease: " + disease +
            ", Severity: " + severity + ", Bed: " + bed);
    }
}

public class dsa_project {

    Scanner sc = new Scanner(System.in);

    Map<Integer, Patient> patients = new HashMap<>();
    Queue<Patient> opd = new LinkedList<>();

    PriorityQueue<Patient> emergency = new PriorityQueue<>(
        (a, b) -> Integer.compare(b.severity, a.severity)
    );

    PriorityQueue<Integer> beds = new PriorityQueue<>();

    // Register Patient
    void register() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Disease: ");
        String disease = sc.nextLine();

        System.out.print("Enter Severity (1-5): ");
        int severity = sc.nextInt();

        if (patients.containsKey(id) || age <= 0 ||
            severity < 1 || severity > 5) {
            System.out.println("Invalid details or ID exists!");
            return;
        }

        Patient p = new Patient(id, name, age, disease, severity);
        patients.put(id, p);

        if (severity >= 4)
            emergency.add(p);
        else
            opd.add(p);

        System.out.println("Patient Registered!");
    }

    // Call Next Patient
    void callNext() {
        Patient p = emergency.isEmpty()
                    ? opd.poll()
                    : emergency.poll();

        if (p == null)
            System.out.println("No patients waiting!");
        else
            p.display();
    }

    // Admit Patient
    void admit() {
        System.out.print("Enter Patient ID: ");
        Patient p = patients.get(sc.nextInt());

        if (p == null || p.bed != -1 || beds.isEmpty()) {
            System.out.println("Invalid patient or no bed available!");
            return;
        }

        p.bed = beds.poll();
        System.out.println("Admitted to Bed: " + p.bed);
    }

    // Discharge Patient
    void discharge() {
        System.out.print("Enter Patient ID: ");
        Patient p = patients.get(sc.nextInt());

        if (p == null || p.bed == -1) {
            System.out.println("Patient not admitted!");
            return;
        }

        beds.add(p.bed);
        p.bed = -1;

        System.out.println("Patient Discharged!");
    }

    // Search Patient
    void search() {
        System.out.print("Enter Patient ID: ");
        Patient p = patients.get(sc.nextInt());

        if (p == null)
            System.out.println("Patient Not Found!");
        else
            p.display();
    }

    // Add Hospital Bed
    void addBed() {
        System.out.print("Enter Bed Number: ");
        int bed = sc.nextInt();

        if (bed <= 0 || beds.contains(bed)) {
            System.out.println("Invalid or duplicate bed!");
            return;
        }

        for (Patient p : patients.values()) {
            if (p.bed == bed) {
                System.out.println("Bed already occupied!");
                return;
            }
        }

        beds.add(bed);
        System.out.println("Bed Added Successfully!");
    }

    public static void main(String[] args) {
        dsa_project h = new dsa_project();
        int ch;

        do {
            System.out.println("\n===== HOSPITAL MANAGEMENT SYSTEM =====");
            System.out.println("1. Register Patient");
            System.out.println("2. Call Next Patient");
            System.out.println("3. Add Hospital Bed");
            System.out.println("4. Admit Patient");
            System.out.println("5. Discharge Patient");
            System.out.println("6. Search Patient");
            System.out.println("7. Exit");

            System.out.print("Enter Choice: ");
            ch = h.sc.nextInt();

            switch (ch) {
                case 1:
                    h.register();
                    break;
                case 2:
                    h.callNext();
                    break;
                case 3:
                    h.addBed();
                    break;
                case 4:
                    h.admit();
                    break;
                case 5:
                    h.discharge();
                    break;
                case 6:
                    h.search();
                    break;
                case 7:
                    System.out.println("Thank You!");
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }

        } while (ch != 7);
    }
}

