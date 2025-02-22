import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<HausWesteros> hausWesterosList = new ArrayList<>();
        try (BufferedReader bw = new BufferedReader(new FileReader("evenimente.tsv"))) {
            String line;
            bw.readLine();
            while ((line = bw.readLine()) != null) {
                String[] fields = line.split("\t");
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                String house = fields[2];
                String ereignis = fields[3];
                String date = fields[4];
                hausWesterosList.add(new HausWesteros(id, name, house, ereignis, date));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n --- Westeros Ereignisse ---");
            System.out.println("1. Display Mitglider nach initial");
            System.out.println("2. Display Stark Mitglider (sortiert)");
            System.out.println("3. Save house Ereignisse to file");
            System.out.println("4. Exit");
            System.out.println("Choose an option: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid option. Please try again.");
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    System.out.println("Enter initial: ");
                    char initial = scanner.next().charAt(0);
                    System.out.println("\n --- Mitglider ---");
                    for (HausWesteros hw : hausWesterosList) {
                        if (hw.getName().startsWith(String.valueOf(initial))) {
                            System.out.println(hw.getName());
                        }
                    }
                    break;
                case 2:
                    System.out.println("\n --- Stark Mitglider ---");
                    hausWesterosList.stream().filter(hw -> hw.getHouse().equals("Stark"))
                            .map(HausWesteros::getDate).sorted().forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("Enter file name: ");
                    String fileName = scanner.nextLine();
                    Map<String, Integer> housePoints = new HashMap<>();
                    for (HausWesteros hw : hausWesterosList) {
                        housePoints.put(hw.getHouse(), housePoints.getOrDefault(hw.getHouse(), 0) + 1);
                    }
                    List<Map.Entry<String, Integer>> sortedScores = housePoints.entrySet()
                            .stream()
                            .sorted(Map.Entry.<String, Integer> comparingByValue().reversed())
                            .toList();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                        for (Map.Entry<String, Integer> entry : sortedScores) {
                            writer.write(entry.getKey() + "#" + entry.getValue());
                            writer.newLine();
                        }
                        System.out.println("Anzahl Ereignisse save successfully.");
                    } catch (IOException e) {
                        System.out.println("Error writing to file: " + e.getMessage());

                    }
                    break;
                case 4:
                    System.out.println("Exit");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");

            }
        }
    }
}

class HausWesteros {
    int id;
    String name;
    String house;
    String ereignis;
    String date;

    public HausWesteros(int id, String name, String house, String ereignis, String date) {
        this.id = id;
        this.name = name;
        this.house = house;
        this.ereignis = ereignis;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getEreignis() {
        return ereignis;
    }

    public void setEreignis(String ereignis) {
        this.ereignis = ereignis;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}