package com.adri1711.auxiliar1_8_R3;


import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class Trans extends net.minecraft.server.v1_8_R3.ChatMessage {

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
        return (Trans) a(text);
    }

    public Trans append(net.minecraft.server.v1_8_R3.IChatBaseComponent node) {
        return (Trans) addSibling(node);
    }

    public Trans append(net.minecraft.server.v1_8_R3.IChatBaseComponent... nodes) {
        for (net.minecraft.server.v1_8_R3.IChatBaseComponent node : nodes) {
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
        return ChatColor.valueOf(getChatModifier().getColor().name());
    }

    public net.minecraft.server.v1_8_R3.ChatClickable getChatClickable() {
        return getChatModifier().h();
    }

    public Trans setClick(ClickAction action, String value) {
        getChatModifier().setChatClickable(new net.minecraft.server.v1_8_R3.ChatClickable(action.getNMS(), value));
        return this;
    }

    public String getShiftClickText() {
        return getChatModifier().j();
    }

    public Trans setShiftClickText(String text) {
        getChatModifier().setInsertion(text);
        return this;
    }

    public net.minecraft.server.v1_8_R3.ChatHoverable getChatHoverable() {
        return getChatModifier().i();
    }

    public Trans setHover(HoverAction action, net.minecraft.server.v1_8_R3.IChatBaseComponent value) {
        getChatModifier().setChatHoverable(new net.minecraft.server.v1_8_R3.ChatHoverable(action.getNMS(), value));
        return this;
    }

    public Trans setHoverText(String text) {
        return setHover(HoverAction.SHOW_TEXT, new Text(text));
    }

    @Override
    public net.minecraft.server.v1_8_R3.IChatBaseComponent f() {
        return h();
    }

    public void send(CommandSender sender) {
        send(sender, ChatPosition.CHAT);
    }

    public void send(CommandSender sender, ChatPosition position) {
        Util.send(sender, this, position);
    }
}
