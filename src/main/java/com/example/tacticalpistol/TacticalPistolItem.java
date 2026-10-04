package com.example.tacticalpistol;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class TacticalPistolItem extends Item {
    private static final String AMMO_TAG = "AmmoLoaded";
    private static final int CAPACITY = 17;

    public TacticalPistolItem(Properties properties) { super(properties); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack gun = player.getItemInHand(hand);
        if (level.isClientSide) return InteractionResultHolder.sidedSuccess(gun, true);

        int loaded = gun.getOrCreateTag().getInt(AMMO_TAG);
        if (loaded <= 0) {
            if (player instanceof ServerPlayer sp)
                sp.displayClientMessage(Component.literal("Пусто — используйте патроны 9×19 и нажмите ПКМ").withStyle(ChatFormatting.GRAY), true);
            return InteractionResultHolder.fail(gun);
        }

        gun.getOrCreateTag().putInt(AMMO_TAG, loaded - 1);
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getLookAngle().scale(40.0));
        HitResult hit = level.clip(new net.minecraft.world.level.ClipContext(start, end,
            net.minecraft.world.level.ClipContext.Block.OUTLINE,
            net.minecraft.world.level.ClipContext.Fluid.NONE, player));
        if (hit.getType() == HitResult.Type.BLOCK) {
            Vec3 pos = hit.getLocation();
            level.addParticle(net.minecraft.core.particles.ParticleTypes.SMOKE,
                pos.x, pos.y, pos.z, 0, 0.01, 0);
        } else {
            LivingEntity target = level.getEntitiesOfClass(LivingEntity.class,
                new net.minecraft.world.phys.AABB(start, end).inflate(1.0),
                e -> e != player && e.isAlive()).stream()
                .min(java.util.Comparator.comparingDouble(e -> e.distanceToSqr(player)))
                .orElse(null);
            if (target != null && player.hasLineOfSight(target)) target.hurt(level.damageSources().playerAttack(player), 6.0F);
        }
        player.getCooldowns().addCooldown(this, 8);
        return InteractionResultHolder.success(gun);
    }

    @Override
    public boolean isFoil(ItemStack stack) { return false; }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack));
    }
}
