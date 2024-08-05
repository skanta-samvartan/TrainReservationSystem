package Box;

public class Boxing {
    public static void displayTicketBox(String content) {
        int width = content.length() + 4;
        System.out.println("+" + "-".repeat(width) + "+");
        System.out.println("| " + content + " |");
        System.out.println("+" + "-".repeat(width) + "+");
    }

    // Display content in a box
    public static void displayBox(String content) {
        int width = content.length() + 4;
        System.out.println("+" + "-".repeat(width) + "+");
        System.out.println("| " + content + " |");
        System.out.println("+" + "-".repeat(width) + "+");
    }

    // Pad a string to the right with spaces
    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }


    // Display data in a table format
    public  static void displayTable(String[] headers, String[][] data) {
        int[] maxLengths = new int[headers.length];

        // Calculate maximum lengths for each column
        for (int i = 0; i < headers.length; i++) {
            maxLengths[i] = headers[i].length();
            for (String[] row : data) {
                if (row[i].length() > maxLengths[i]) {
                    maxLengths[i] = row[i].length();
                }
            }
        }

        // Print headers
        for (int i = 0; i < headers.length; i++) {
            System.out.print(padRight(headers[i], maxLengths[i] + 2) + "|");
        }
        System.out.println();
        // Print separator line
        for (int i = 0; i < headers.length; i++) {
            System.out.print("-".repeat(maxLengths[i] + 2) + "+");
        }
        System.out.println();

        // Print data rows
        for (String[] row : data) {
            for (int i = 0; i < headers.length; i++) {
                System.out.print(padRight(row[i], maxLengths[i] + 2) + "|");
            }
            System.out.println();  // Move this line to print a new line after each row
        }
    }
}
