package com.bloducspauter.chat.enums;

import lombok.Getter;
import lombok.Setter;

public enum ReceptionType {

    COMMENT("comment"),

    REPLY("reply"),

    Unknown("unknown"),

    INIT("init");


    public final String type;

    ReceptionType(String type) {
        this.type = type;
    }

    public static ReceptionType getReceptionType(String atype) {
        for (ReceptionType receptionType : values()) {
            if (receptionType.type.equalsIgnoreCase(atype)) {
                return receptionType;
            }
        }
        return Unknown;
    }
}
    
