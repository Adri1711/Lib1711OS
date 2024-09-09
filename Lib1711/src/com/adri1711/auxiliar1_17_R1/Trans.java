package com.adri1711.auxiliar1_17_R1;


import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import net.minecraft.network.chat.ChatClickable;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.chat.IChatBaseComponent;

public class Trans extends ChatMessage {

    public Trans() {
        super("");
    }

    public Trans(String string, Object... objects) {
        super(string, objects);
    }

    public static Trans fromItemStack(ItemStack stack) {
        return new Trans().append(Util.fromItemStack(stack));
    }

    public Trans append(String text) {
        return (Trans) c(text);
    }

    public Trans append(IChatBaseComponent node) {
        return (Trans) addSibling(node);
    }

    public Trans append(IChatBaseComponent... nodes) {
        for (IChatBaseComponent node : nodes) {
            addSibling(node);
        }

        return this;
    }

    public Trans appendItem(ItemStack stack) {
        return append(Util.fromItemStack(stack));
    }

    public boolean isBold() {
        return getChatModifier().isBold();
    }

    public Trans setBold(boolean bold) {
        getChatModifier().setBold(bold);
        return this;
    }

    public boolean isItalic() {
        return getChatModifier().isItalic();
    }

    public Trans setItalic(boolean italic) {
        getChatModifier().setItalic(italic);
        return this;
    }

    public boolean isUnderlined() {
        return getChatModifier().isUnderlined();
    }

    public Trans setUnderline(boolean underline) {
        getChatModifier().setUnderline(underline);
        return this;
    }

    public boolean isRandom() {
        return getChatModifier().isRandom();
    }

    public Trans setRandom(boolean random) {
        getChatModifier().setRandom(random);
        return this;
    }

    public boolean isStrikethrough() {
        return getChatModifier().isStrikethrough();
    }

    public Trans setStrikethrough(boolean strikethrough) {
        getChatModifier().setStrikethrough(strikethrough);
        return this;
    }

    public ChatColor getColor() {
        return ChatColor.valueOf(getChatModifier().getColor().e);
    }

    public ChatClickable getChatClickable() {
        return getChatModifier().getClickEvent();
    }

    public Trans setClick(ClickAction action, String value) {
        getChatModifier().setChatClickable(new ChatClickable(action.getNMS(), value));
        return this;
    }

    public String getShiftClickText() {
        return getChatModifier().getInsertion();
    }

    public Trans setShiftClickText(String text) {
        getChatModifier().setInsertion(text);
        return this;
    }

    public ChatHoverable getChatHoverable() {
        return getChatModifier().getHoverEvent();
    }

    public Trans setHover(HoverAction action, IChatBaseComponent value) {
        getChatModifier().setChatHoverable(new ChatHoverable(action.getNMS(), value));
        return this;
    }

    public Trans setHoverText(String text) {
        return setHover(HoverAction.SHOW_TEXT, new Text(text));
    }

 

    public void send(CommandSender sender) {
        send(sender, ChatPosition.CHAT);
    }

    public void send(CommandSender sender, ChatPosition position) {
        Util.send(sender, this, position);
    }
}