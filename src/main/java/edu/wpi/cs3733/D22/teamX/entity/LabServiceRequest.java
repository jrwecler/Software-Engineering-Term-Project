package edu.wpi.cs3733.D22.teamX.entity;

/** Represents a lab work service request */
public class LabServiceRequest extends ServiceRequest {
  @Override
  public String makeRequestID() {
    return "sample";
  }
}