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

        // Toggle aktywności
        addDrawableChild(ButtonWidget.builder(
                getToggleText(),
                btn -> {
                    AutoAttackMod.attackEnabled = !AutoAttackMod.attackEnabled;
                    btn.setMessage(getToggleText());
                })
                .dimensions(centerX - btnW / 2, startY, btnW, 20)
                .build());

        // Toggle atakowania graczy
        addDrawableChild(ButtonWidget.builder(
                getPlayersText(),
                btn -> {
                    AutoAttackMod.attackPlayers = !AutoAttackMod.attackPlayers;
                    btn.setMessage(getPlayersText());
                })
                .dimensions(centerX - btnW / 2, startY + 28, btnW, 20)
                .build());

        // Toggle hitboxów
        addDrawableChild(ButtonWidget.builder(
                getHitboxText(),
                btn -> {
                    AutoAttackMod.hitboxEnabled = !AutoAttackMod.hitboxEnabled;
                    MinecraftClient.getInstance().getEntityRenderDispatcher().setRenderHitboxes(AutoAttackMod.hitboxEnabled);
                    btn.setMessage(getHitboxText());
                })
                .dimensions(centerX - btnW / 2, startY + 56, btnW, 20)
                .build());

        // Slider min delay
        addDrawableChild(new DelaySlider(centerX - btnW / 2, startY + 90, btnW, 20,
                "Min delay", AutoAttackMod.minDelay, 2, 40, true));

        // Slider max delay
        addDrawableChild(new DelaySlider(centerX - btnW / 2, startY + 118, btnW, 20,
                "Max delay", AutoAttackMod.maxDelay, 2, 40, false));

        // Powrót
        addDrawableChild(ButtonWidget.builder(
                Text.literal("Zamknij"),
                btn -> MinecraftClient.getInstance().setScreen(parent))
                .dimensions(centerX - btnW / 2, startY + 158, btnW, 20)
                .build());
    }

    @Override
    public void render(net.minecraft.client.gui.DrawContext context, int mouseX, int
