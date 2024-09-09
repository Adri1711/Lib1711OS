package com.adri1711.auxiliar1_19_R2;


public enum ClickAction {

    OPEN_URL(net.minecraft.network.chat.ChatClickable.EnumClickAction.a),
    OPEN_FILE(net.minecraft.network.chat.ChatClickable.EnumClickAction.b),
    RUN_COMMAND(net.minecraft.network.chat.ChatClickable.EnumClickAction.c),
    SUGGEST_COMMAND(net.minecraft.network.chat.ChatClickable.EnumClickAction.d),
    CHANGE_PAGE(net.minecraft.network.chat.ChatClickable.EnumClickAction.e);

    private net.minecraft.network.chat.ChatClickable.EnumClickAction clickAction;

    ClickAction(net.minecraft.network.chat.ChatClickable.EnumClickAction action) {
        this.clickAction = action;
    }

    public net.minecraft.network.chat.ChatClickable.EnumClickAction getNMS() {
        return this.clickAction;
    }
}

