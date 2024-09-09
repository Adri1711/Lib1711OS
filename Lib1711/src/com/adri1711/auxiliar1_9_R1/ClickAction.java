package com.adri1711.auxiliar1_9_R1;

public enum ClickAction {

    OPEN_URL(net.minecraft.server.v1_9_R1.ChatClickable.EnumClickAction.OPEN_URL),
    OPEN_FILE(net.minecraft.server.v1_9_R1.ChatClickable.EnumClickAction.OPEN_FILE),
    RUN_COMMAND(net.minecraft.server.v1_9_R1.ChatClickable.EnumClickAction.RUN_COMMAND),
    SUGGEST_COMMAND(net.minecraft.server.v1_9_R1.ChatClickable.EnumClickAction.SUGGEST_COMMAND),
    CHANGE_PAGE(net.minecraft.server.v1_9_R1.ChatClickable.EnumClickAction.CHANGE_PAGE),;

    private net.minecraft.server.v1_9_R1.ChatClickable.EnumClickAction clickAction;

    ClickAction(net.minecraft.server.v1_9_R1.ChatClickable.EnumClickAction action) {
        this.clickAction = action;
    }

    public net.minecraft.server.v1_9_R1.ChatClickable.EnumClickAction getNMS() {
        return this.clickAction;
    }
}
