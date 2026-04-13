package com.autoattack.mod;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.List;

public class AutoAttackSettingsButton {

    public static void injectIntoScreen(Screen screen, MinecraftClient client) {
        List<ClickableWidget> buttons = Screens.getButtons(screen);

        int baseY = screen.height / 4 + 8;
        int btnX = screen.width / 2 - 102;

        ButtonWidget settingsBtn = ButtonWidget.builder(
                Text.literal("§6⚔ AutoAttack"),
                btn -> client.setScreen(new AutoAttackSettingsScreen(screen))
        ).dimensions(btnX + 208, baseY, 96, 20).build();

        buttons.add(settingsBtn);
    }
}
