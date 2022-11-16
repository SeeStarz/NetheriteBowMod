package com.seestarz.netherite_bow_mod.common.item.custom;

import com.seestarz.netherite_bow_mod.core.config.NetheriteBowClientConfig;
import com.seestarz.netherite_bow_mod.core.config.NetheriteBowCommonConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class NetheriteBowItem extends BowItem {
    public static float getChargeTime() {
        return NetheriteBowCommonConfig.chargeTime.get().floatValue();
    }
    public static float getVelocityMultiplier() {
        return NetheriteBowCommonConfig.velocityMultiplier.get().floatValue();
    }
    public static float getMaxZoom() {
        return NetheriteBowClientConfig.maxZoom.get().floatValue();
    }

    public NetheriteBowItem(Properties builder) {

        super(builder);

        ItemModelsProperties.registerProperty(this, new ResourceLocation("pull"), (stack, world, player) -> {
            if (player == null) {
                return 0.0F;
            } else {
                return player.getActiveItemStack() != stack ? 0.0F :
                        ((float)(stack.getUseDuration() - player.getItemInUseCount()) )/ NetheriteBowItem.getChargeTime();
            }
        });
        ItemModelsProperties.registerProperty(this, new ResourceLocation("pulling"), (stack, world, player) -> {
            return player != null && player.isHandActive() && player.getActiveItemStack() == stack ? 1.0F : 0.0F;
        });
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack bowStack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity) entityLiving;

            boolean isCreative = playerentity.abilities.isCreativeMode;
            boolean infinity = isCreative || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bowStack) > 0;
            ItemStack ammoStack = playerentity.findAmmo(bowStack);

            int charge = this.getUseDuration(bowStack) - timeLeft;
            charge = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(bowStack, worldIn, playerentity, charge, !ammoStack.isEmpty() || infinity);
            if (charge < 0) return;

            if (!ammoStack.isEmpty() || infinity) {
                if (ammoStack.isEmpty()) {
                    ammoStack = new ItemStack(Items.ARROW);
                }

                // New velocity calculation
                float velocityMultiplier = NetheriteBowItem.getVelocityMultiplier();
                float velocity = getVelocity(charge) * velocityMultiplier;

                if (!((double) velocity  < 0.1D * velocityMultiplier)) {
                    boolean unpickable =
                            isCreative || (ammoStack.getItem() instanceof ArrowItem && ((ArrowItem) ammoStack.getItem()).isInfinite(ammoStack, bowStack, playerentity));

                    if (!worldIn.isRemote) {
                        ArrowItem arrowitem = (ArrowItem) (ammoStack.getItem() instanceof ArrowItem ? ammoStack.getItem() : Items.ARROW);
                        AbstractArrowEntity arrowEntity = arrowitem.createArrow(worldIn, ammoStack, playerentity);
                        arrowEntity = customArrow(arrowEntity);
                        arrowEntity.setDirectionAndMovement(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, velocity * 3.0F, 0.3F);

                        if (velocity == 1.0F * velocityMultiplier) {
                            arrowEntity.setIsCritical(true);
                        }

                        double baseDamage = arrowEntity.getDamage();
                        int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bowStack);
                        if (power > 0) {
                            arrowEntity.setDamage(baseDamage + (double) power * 0.5f + 0.5f);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bowStack);
                        arrowEntity.setKnockbackStrength(k + 1);

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bowStack) > 0) {
                            arrowEntity.setFire(100);
                        }

                        bowStack.damageItem(1, playerentity,
                                (player) -> player.sendBreakAnimation(playerentity.getActiveHand()));

                        if (unpickable) {
                            arrowEntity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }

                        worldIn.addEntity(arrowEntity);
                    }

                    worldIn.playSound(null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);
                    if (!unpickable) {
                        ammoStack.shrink(1);
                        if (ammoStack.isEmpty()) {
                            playerentity.inventory.deleteStack(ammoStack);
                        }
                    }

                    playerentity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    /**
     * Gets the velocity of the arrow entity from the bow's charge
     */
    protected float getVelocity(int charge) {
        float f = (float)charge / NetheriteBowItem.getChargeTime();
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {
        return (int)(72000 * NetheriteBowItem.getChargeTime() / 20);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

}