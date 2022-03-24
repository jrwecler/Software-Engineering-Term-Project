package edu.wpi.teamname;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserProgram {
  private static List<Location> locationsFromCSV;

  /**
   * Starts the program that the user will interact with. This method runs until the user cannot
   * login or they enter "6" to exit the program
   *
   * @param username used to login
   * @param password used to login
   */
  public static void executeProgram(String username, String password) {
    if (username.equals("admin") && password.equals("admin")) {
      System.out.println("Successfully logged in!");
    } else {
      System.out.println("Incorrect username or password");
      return;
    }

    loadCSV();

    System.out.println(
        "1 – Location Information\n"
            + "2 – Change Floor and Type\n"
            + "3 – Enter Location\n"
            + "4 – Delete Location\n"
            + "5 – Save Locations to CSV file\n"
            + "6 – Exit Program");

    // This loop will end when the user selects option 6
    while (true) {
      Scanner input = new Scanner(System.in);
      String option = input.nextLine();
      switch (option) {
        case ("1"):
          System.out.println("option 1 placeholder");
          break;
        case ("2"):
          System.out.println("option 2 placeholder");
          break;
        case ("3"):
          System.out.println("option 3 placeholder");
          break;
        case ("4"):
          System.out.println("option 4 placeholder");
          break;
        case ("5"):
          System.out.println("option 5 placeholder");
          break;
        case ("6"):
          return;
        default:
          System.out.println("Your input should be one number between 1 and 6, try again");
          break;
      }
    }
  }

  private static void loadCSV() {
    locationsFromCSV = new ArrayList<Location>();
    try {
      Scanner sc =
          new Scanner(
              new File("empty-project/src/main/resources/edu/wpi/teamname/TowerLocations.csv"));
      sc.nextLine();
      while (sc.hasNextLine()) {
        String[] currLine = sc.nextLine().replaceAll("\r\n", "").split(",");
        if (currLine.length == 8) {
          Location lnode =
              new Location(
                  currLine[0],
                  Integer.parseInt(currLine[1]),
                  Integer.parseInt(currLine[2]),
                  currLine[3],
                  currLine[4],
                  currLine[5],
                  currLine[6],
                  currLine[7]);
          locationsFromCSV.add(lnode);
        } else {
          System.out.println("CSV file formatted improperly");
          System.exit(1);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("File not found!");
    }
  }
}
