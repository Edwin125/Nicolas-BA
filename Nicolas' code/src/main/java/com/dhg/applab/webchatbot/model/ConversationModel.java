package com.dhg.applab.webchatbot.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ConversationModel {
    private Integer id;
    private Timestamp dateAdded;
    private Timestamp dateDeactivated;
}
