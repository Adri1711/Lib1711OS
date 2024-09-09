package com.adri1711.auxiliar1_13_R1;


public enum ChatPosition {

    CHAT((byte) 0),
    SYSTEM((byte) 1),
    ACTION((byte) 2);

    private final byte id;

    ChatPosition(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }
}
