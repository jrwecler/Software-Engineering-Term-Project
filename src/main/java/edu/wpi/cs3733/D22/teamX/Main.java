package edu.wpi.cs3733.D22.teamX;

public class Main {

  public static void main(String[] args) {
    System.setProperty(
        "prism.allowhidpi",
        "false"); // Disables windows DPI scaling so the application fits all 1920x1080 screens.
    App.launch(App.class, args);
  }
}