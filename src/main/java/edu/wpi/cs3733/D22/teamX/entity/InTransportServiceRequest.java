package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a transport service request */
public class InTransportServiceRequest extends ServiceRequest {
  private String patientName;
  private String transportFrom; // this is destination, this is unecessary

  public InTransportServiceRequest() {
    super();
    this.patientName = "";
    this.transportFrom = "";
  }

  public InTransportServiceRequest(
      String requestID,
      Location destination,
      String status,
      Employee assignee,
      String patientName,
      String transportFrom,
      String addAccommodation) {
    super(requestID, destination, status, assignee);
    this.patientName = patientName;
    this.transportFrom = transportFrom;
  }

  public void setTransportFrom(String transportFrom) {
    this.transportFrom = transportFrom;
  }

  public String getPatientName() {
    return patientName;
  }

  public String getTransportFrom() {
    return transportFrom;
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }
}
