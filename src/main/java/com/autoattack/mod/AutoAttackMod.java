package com.autoattack.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class AutoAttackMod implements ClientModInitializer {

    public static final String MOD_ID = "autoattack";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static KeyBinding toggleKey;

    public static boolean isEnabled = false;
    public static boolean attackPlayers = false;
    public static int minDelay = 8;
    public static int maxDelay = 14;

    private static int attackCooldown = 0;
    private static final Random RANDOM = new Random();

    @Override
    public void onInitializeClient() {
        LOGGER.info("[AutoAttack] Mod załadowany!");

        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.autoattack.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.autoattack"
        ));

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof GameMenuScreen) {
                AutoAttackSettingsButton.injectIntoScreen(screen, client);
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            while (toggleKey.wasPressed()) {
                isEnabled = !isEnabled;
                String status = isEnabled ? "§aWŁĄCZONY" : "§cWYŁĄCZONY";
                client.player.sendMessage(Text.literal("§6[AutoAttack] §r" + status), true);
            }

            if (!isEnabled) return;

            if (attackCooldown > 0) {
                attackCooldown--;
                return;
            }

            HitResult hitResult = client.crosshairTarget;
            if (hitResult == null || hitResult.getType() != HitResult.Type.ENTITY) return;

            EntityHitResult entityHit = (EntityHitResult) hitResult;
            Entity target = entityHit.getEntity();

            if (!isValidTarget(target, client)) return;

            client.interactionManager.attackEntity(client.player, target);
            client.player.swingHand(Hand.MAIN_HAND);

            attackCooldown = minDelay + RANDOM.nextInt(Math.max(1, maxDelay - minDelay + 1));
        });
    }

    private boolean isValidTarget(Entity entity, MinecraftClient client) {
        if (!(entity instanceof LivingEntity living)) return false;
        if (living.isDead() || living.getHealth() <= 0) return false;
        if (entity == client.player) return false;
        if (entity instanceof PlayerEntity && !attackPlayers) return false;
        return true;
    }
}
