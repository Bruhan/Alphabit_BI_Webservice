package com.owner.process.usecases.peppol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class peppolOutboudStatusPojo {
    private String event;
    private String docId;
    private String status;
    private String transmissionTime;
    private String message;

    @Override
    public String toString() {
        return "peppolOutboudStatusPojo{" +
                "event='" + event + '\'' +
                ", docId='" + docId + '\'' +
                ", status='" + status + '\'' +
                ", transmissionTime='" + transmissionTime + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
