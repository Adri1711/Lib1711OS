package com.adri1711.auxiliar1_12_R1;

public enum HoverAction {

    SHOW_TEXT(net.minecraft.server.v1_12_R1.ChatHoverable.EnumHoverAction.SHOW_TEXT),
    SHOW_ITEM(net.minecraft.server.v1_12_R1.ChatHoverable.EnumHoverAction.SHOW_ITEM),
    SHOW_ENTITY(net.minecraft.server.v1_12_R1.ChatHoverable.EnumHoverAction.SHOW_ENTITY);

    private net.minecraft.server.v1_12_R1.ChatHoverable.EnumHoverAction hoverAction;

    HoverAction(net.minecraft.server.v1_12_R1.ChatHoverable.EnumHoverAction hoverAction) {
        this.hoverAction = hoverAction;
    }

    public net.minecraft.server.v1_12_R1.ChatHoverable.EnumHoverAction getNMS() {
        return hoverAction;
    }
}