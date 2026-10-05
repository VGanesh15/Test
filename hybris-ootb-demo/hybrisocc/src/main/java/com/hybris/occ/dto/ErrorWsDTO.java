package com.hybris.occ.dto;

/**
 * Hybris OCC: ErrorWsDTO. Mirrors SAP Commerce OCC error response format.
 */
public class ErrorWsDTO {
    private String type;
    private String reason;
    private String message;
    private String subject;
    private String subjectType;
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getSubjectType() { return subjectType; }
    public void setSubjectType(String subjectType) { this.subjectType = subjectType; }
}
