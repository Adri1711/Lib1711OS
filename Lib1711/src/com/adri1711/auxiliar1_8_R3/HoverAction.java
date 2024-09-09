package com.adri1711.auxiliar1_8_R3;

public enum HoverAction {

    SHOW_TEXT(net.minecraft.server.v1_8_R3.ChatHoverable.EnumHoverAction.SHOW_TEXT),
    SHOW_ACHIEVEMENT(net.minecraft.server.v1_8_R3.ChatHoverable.EnumHoverAction.SHOW_ACHIEVEMENT),
    SHOW_ITEM(net.minecraft.server.v1_8_R3.ChatHoverable.EnumHoverAction.SHOW_ITEM),
    SHOW_ENTITY(net.minecraft.server.v1_8_R3.ChatHoverable.EnumHoverAction.SHOW_ENTITY);

    private net.minecraft.server.v1_8_R3.ChatHoverable.EnumHoverAction hoverAction;

    HoverAction(net.minecraft.server.v1_8_R3.ChatHoverable.EnumHoverAction hoverAction) {
        this.hoverAction = hoverAction;
    }

    public net.minecraft.server.v1_8_R3.ChatHoverable.EnumHoverAction getNMS() {
        return hoverAction;
    }
}