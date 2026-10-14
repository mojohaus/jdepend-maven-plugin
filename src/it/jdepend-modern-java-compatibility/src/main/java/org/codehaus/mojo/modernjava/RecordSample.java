package org.codehaus.mojo.modernjava;

public record RecordSample(String id, String status) {
    public String getFormattedStatus() {
        return "ID: " + id + " has status: " + status;
    }
}
