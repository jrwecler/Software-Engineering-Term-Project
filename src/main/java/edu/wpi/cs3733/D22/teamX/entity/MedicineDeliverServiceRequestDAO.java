package edu.wpi.cs3733.D22.teamX.entity;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MedicineDeliverServiceRequestDAO implements DAO<MedicineServiceRequest> {
  private static List<MedicineServiceRequest> medicineServiceRequests =
      new ArrayList<MedicineServiceRequest>();
  private static String csv = "MedicineDeliveryRequests.csv";

  private MedicineDeliverServiceRequestDAO() {}

  private static class SingletonHelper {
    private static final MedicineDeliverServiceRequestDAO medicineDeliveryServiceRequestDAO =
        new MedicineDeliverServiceRequestDAO();
  }

  public static MedicineDeliverServiceRequestDAO getDAO() {
    return SingletonHelper.medicineDeliveryServiceRequestDAO;
  }

  @Override
  public List<MedicineServiceRequest> getAllRecords() {
    return medicineServiceRequests;
  }

  @Override
  public MedicineServiceRequest getRecord(String recordID) {
    // iterate through list to find element with matching requestID
    for (MedicineServiceRequest esr : medicineServiceRequests) {
      // if matching IDs
      if (esr.getRequestID().equals(recordID)) {
        return esr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  @Override
  public void deleteRecord(MedicineServiceRequest recordObject) {
    // add to destination's list of service requests
    recordObject.getDestination().removeRequest(recordObject);
    // remove from list
    int index = 0; // create index variable for while loop
    int initialSize = medicineServiceRequests.size();
    while (index < medicineServiceRequests.size()) {
      if (medicineServiceRequests.get(index).equals(recordObject)) {
        medicineServiceRequests.remove(index); // removes object from list
        index--;
        break; // exit
      }
      index++; // increment if not found yet
    }
    if (index == initialSize) {
      throw new NoSuchElementException("request does not exist");
    }
    // remove from database
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // remove location from DB table
      statement.executeUpdate(
          "DELETE FROM MedicineDeliveryServiceRequest WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateRecord(MedicineServiceRequest recordObject) {
    // remove mesr from old location and add to new one if location changes in the update
    if (!recordObject
        .getDestination()
        .equals(getRecord(recordObject.getRequestID()).getDestination())) {
      getRecord(recordObject.getRequestID()).getDestination().removeRequest(recordObject);
      recordObject.getDestination().addRequest(recordObject);
    }
    int index = 0; // create indexer varible for while loop
    while (index < medicineServiceRequests.size()) {
      if (medicineServiceRequests.get(index).equals(recordObject)) {
        medicineServiceRequests.set(index, recordObject);
        break; // exit
      }
      index++; // increment if not found yet
    }
    // if medical equipment service request not found
    if (index == medicineServiceRequests.size()) {
      throw new NoSuchElementException("request does not exist");
    }

    // update DB table
    try {
      // create the statement
      Statement statement = connection.createStatement();
      // update item in DB
      statement.executeUpdate(
          "UPDATE MedicineDeliveryServiceRequest SET"
              + " destination = '"
              + recordObject.getDestination().getNodeID()
              + "', status = '"
              + recordObject.getStatus()
              + "', assignee = '"
              + recordObject.getAssignee()
              + "', rxNum = '"
              + recordObject.getRxNum()
              + "', patientFor = '"
              + recordObject.getPatientFor()
              + "' WHERE requestID = '"
              + recordObject.getRequestID()
              + "'");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addRecord(MedicineServiceRequest recordObject) {
    medicineServiceRequests.add(recordObject);

    try {
      Statement initialization = connection.createStatement();
      StringBuilder medicineDeliveryServiceRequest = new StringBuilder();
      medicineDeliveryServiceRequest.append("INSERT INTO MedicineDeliveryServiceRequest VALUES(");
      medicineDeliveryServiceRequest.append("'" + recordObject.getRequestID() + "'" + ", ");
      medicineDeliveryServiceRequest.append(
          "'" + recordObject.getDestination().getNodeID() + "'" + ", ");
      medicineDeliveryServiceRequest.append("'" + recordObject.getStatus() + "'" + ", ");
      medicineDeliveryServiceRequest.append("'" + recordObject.getAssignee() + "'" + ", ");
      medicineDeliveryServiceRequest.append("'" + recordObject.getRxNum() + "'" + ", ");
      medicineDeliveryServiceRequest.append("'" + recordObject.getPatientFor() + "'");
      medicineDeliveryServiceRequest.append(")");
      initialization.execute(medicineDeliveryServiceRequest.toString());
    } catch (SQLException e) {
      System.out.println("Database could not be updated");
      return;
    }
    recordObject
        .getDestination()
        .addRequest(recordObject); // add mesr to destination's list of service requests
  }

  @Override
  public void createTable() {
    try {
      Statement initialization = connection.createStatement();
      initialization.execute(
          "CREATE TABLE MedicineDeliveryServiceRequest(requestID CHAR(8) PRIMARY KEY NOT NULL, "
              + "destination CHAR(10),"
              + "status CHAR(4),"
              + "assignee CHAR(8),"
              + "rxNum CHAR(8),"
              + "patientFor CHAR(8),"
              + "CONSTRAINT MDSR_dest_fk "
              + "FOREIGN KEY (destination) REFERENCES Location(nodeID)"
              + "ON DELETE SET NULL, "
              + "CONSTRAINT MESR_equipmentType_fk ");
    } catch (SQLException e) {
      System.out.println(
          "MedicineDeliveryServiceRequest table creation failed. Check output console.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void dropTable() {
    try {
      Statement dropMedicalEquipmentServiceRequest = connection.createStatement();
      dropMedicalEquipmentServiceRequest.execute("DROP TABLE MedicalEquipmentServiceRequest");
    } catch (SQLException e) {
      System.out.println("MedicineDeliveryServiceRequest not dropped");
      e.printStackTrace();
    }
  }

  @Override
  public boolean loadCSV() {
    return false;
  }

  @Override
  public boolean saveCSV(String dirPath) {
    return false;
  }

  @Override
  public String makeID() {
    return null;
  }
}
