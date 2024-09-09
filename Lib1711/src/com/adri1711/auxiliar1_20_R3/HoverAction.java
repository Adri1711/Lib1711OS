package com.adri1711.auxiliar1_20_R3;

public enum HoverAction {

    SHOW_TEXT(net.minecraft.network.chat.ChatHoverable.EnumHoverAction.a),
    SHOW_ITEM(net.minecraft.network.chat.ChatHoverable.EnumHoverAction.b),
    SHOW_ENTITY(net.minecraft.network.chat.ChatHoverable.EnumHoverAction.c);

    private net.minecraft.network.chat.ChatHoverable.EnumHoverAction hoverAction;

    HoverAction(net.minecraft.network.chat.ChatHoverable.EnumHoverAction hoverAction) {
        this.hoverAction = hoverAction;
    }

    public net.minecraft.network.chat.ChatHoverable.EnumHoverAction getNMS() {
        return hoverAction;
    }
}