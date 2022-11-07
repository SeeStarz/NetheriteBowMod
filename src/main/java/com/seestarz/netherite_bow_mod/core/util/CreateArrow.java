package com.seestarz.netherite_bow_mod.core.util;

import com.seestarz.netherite_bow_mod.common.entity.custom.ArrowEntity;
import com.seestarz.netherite_bow_mod.common.entity.custom.SpectralArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class CreateArrow {
        public static AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
            if (stack.getItem() == Items.SPECTRAL_ARROW) {
                SpectralArrowEntity arrowentity = new SpectralArrowEntity(worldIn, shooter);
                return arrowentity;
            }
            else {
                ArrowEntity arrowentity = new ArrowEntity(worldIn, shooter);
                arrowentity.setPotionEffect(stack);
                return arrowentity;
            }
        }
}
