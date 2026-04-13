package com.autoattack.mod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class AutoAttackSettingsScreen extends Screen {

    private final Screen parent;

    public AutoAttackSettingsScreen(Screen parent) {
        super(Text.literal("§6AutoAttack — Ustawienia"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 2 - 90;
        int btnW = 220;

        addDrawableChild(ButtonWidget.builder(
                getToggleText(),
                btn -> {
                    AutoAttackMod.attackEnabled = !AutoAttackMod.attackEnabled;
                    btn.setMessage(getToggleText());
                })
                .dimensions(centerX - btnW / 2, startY, btnW, 20)
                .build());

        addDrawableChild(ButtonWidget.builder(
                getPlayersText(),
                btn -> {
                    AutoAttackMod.attackPlayers = !AutoAttackMod.attackPlayers;
                    btn.setMessage(getPlayersText());
                })
                .dimensions(centerX - btnW / 2, startY + 28, btnW, 20)
                .build());

        addDrawableChild(ButtonWidget.builder(
                getHitboxText(),
                btn -> {
                    AutoAttackMod.hitboxEnabled = !AutoAttackMod.hitboxEnabled;
                    MinecraftClient.getInstance().getEntityRenderDispatcher().setRenderHitboxes(AutoAttackMod.hitboxEnabled);
                    btn.setMessage(getHitboxText());
                })
                .dimensions(centerX - btnW / 2, startY + 56, btnW, 20)
                .build());

        addDrawableChild(new DelaySlider(centerX - btnW / 2, startY + 90, btnW, 20,
                "Min delay", AutoAttackMod.minDelay, 2, 40, true));

        addDrawableChild(new DelaySlider(centerX - btnW / 2, startY + 118, btnW, 20,
                "Max delay", AutoAttackMod.maxDelay, 2, 40, false));

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Zamknij"),
                btn -> MinecraftClient.getInstance().setScreen(parent))
                .dimensions(centerX - btnW / 2, startY + 158, btnW, 20)
                .build());
    }

    @Override
    public void render(net.minecraft.client.gui.DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, height / 2 - 115, 0xFFFFFF);

        int cx = width / 2;
        int sy = height / 2 - 90;
        context.drawTextWithShadow(textRenderer,
                Text.literal("§7Min delay: " + AutoAttackMod.minDelay + " ticków  |  Max delay: " + AutoAttackMod.maxDelay + " ticków"),
                cx - 110, sy + 146, 0xAAAAAA);
    }

    @Override
    public boolean shouldCloseOnEsc() { return true; }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(parent);
    }

    private Text getToggleText() {
        return Text.literal("AutoAttack: " + (AutoAttackMod.attackEnabled ? "§aWŁĄCZONY" : "§cWYŁĄCZONY"));
    }

    private Text getPlayersText() {
        return Text.literal("Atakuj graczy: " + (AutoAttackMod.attackPlayers ? "§aWŁĄCZONE" : "§cWYŁĄCZONE"));
    }

    private Text getHitboxText() {
        return Text.literal("Hitboxy przez ściany: " + (AutoAttackMod.hitboxEnabled ? "§aWŁĄCZONE" : "§cWYŁĄCZONE"));
    }

    private static class DelaySlider extends SliderWidget {
        private final String label;
        private final int minVal;
        private final int maxVal;
        private final boolean isMin;

        public DelaySlider(int x, int y, int width, int height, String label, int current, int minVal, int maxVal, boolean isMin) {
            super(x, y, width, height, Text.empty(), (double)(current - minVal) / (maxVal - minVal));
            this.label = label;
            this.minVal = minVal;
            this.maxVal = maxVal;
            this.isMin = isMin;
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            int val = minVal + (int)(value * (maxVal - minVal));
            setMessage(Text.literal(label + ": §e" + val + " §7ticków §8(~" + String.format("%.2f", val / 20.0) + "s)"));
        }

        @Override
        protected void applyValue() {
            int val = minVal + (int)(value * (maxVal - minVal));
            if (isMin) {
                AutoAttackMod.minDelay = val;
                if (AutoAttackMod.minDelay > AutoAttackMod.maxDelay)
                    AutoAttackMod.maxDelay = AutoAttackMod.minDelay;
            } else {
                AutoAttackMod.maxDelay = val;
                if (AutoAttackMod.maxDelay < AutoAttackMod.minDelay)
                    AutoAttackMod.minDelay = AutoAttackMod.maxDelay;
            }
        }
    }
}
