package com.seestarz.netherite_bow_mod.common.entity.custom;

import com.seestarz.netherite_bow_mod.common.entity.ModEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpectralArrowEntity extends AbstractArrowEntity implements IEntityAdditionalSpawnData {
    private int duration = 200;

    public SpectralArrowEntity(EntityType<? extends SpectralArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public SpectralArrowEntity(World worldIn, LivingEntity shooter) {
        super(ModEntityType.SPECTRAL_ARROW.get(), shooter, worldIn);
    }

    public SpectralArrowEntity(World worldIn, double x, double y, double z) {
        super(ModEntityType.SPECTRAL_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        Vector3d vector = super.getMotion();
        double speedX = vector.x;
        double speedY = vector.y;
        double speedZ = vector.z;

        buffer.writeInt((int)(speedX*8000));
        buffer.writeInt((int)(speedY*8000));
        buffer.writeInt((int)(speedZ*8000));
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        double speedX = additionalData.readInt() / 8000D;
        double speedY = additionalData.readInt() / 8000D;
        double speedZ = additionalData.readInt() / 8000D;

        super.setMotion(speedX,speedY,speedZ);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (this.world.isRemote && !this.inGround) {
            this.world.addParticle(ParticleTypes.INSTANT_EFFECT, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        }

    }

    protected ItemStack getArrowStack() {
        return new ItemStack(Items.SPECTRAL_ARROW);
    }

    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);
        EffectInstance effectinstance = new EffectInstance(Effects.GLOWING, this.duration, 0);
        living.addPotionEffect(effectinstance);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Duration")) {
            this.duration = compound.getInt("Duration");
        }

    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Duration", this.duration);
    }
}
