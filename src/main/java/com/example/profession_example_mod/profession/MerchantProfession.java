package com.example.profession_example_mod.profession;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Vec3d;
import org.samo_lego.taterzens.api.professions.TaterzenProfession;
import org.samo_lego.taterzens.npc.TaterzenNPC;

import static com.example.profession_example_mod.ProfessionExampleMod.MOD_ID;

/**
 * Profession class.
 * @see org.samo_lego.taterzens.api.professions.TaterzenProfession
 * @see <a href="https://samolego.github.io/Taterzens/dokka/common/common/org.samo_lego.taterzens.api.professions/-taterzen-profession/index.html">Dokkadocs</a>
 */
public class MerchantProfession implements TaterzenProfession {
    public static final Identifier PROFESSION_ID = new Identifier(MOD_ID, "merchant");
    private TaterzenNPC npc;

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d pos, Hand hand) {
        System.out.println("Player " + player.getName().getString() + " has interacted with taterzen " + this.npc.getName().getString());
        player.sendMessage(new LiteralText("I will pay for any item you click on with!"), false);

        ItemStack stack =player.getStackInHand(hand);
        if(!stack.isEmpty()) {
            Rarity rarity = stack.getItem().getRarity(stack);
            ItemStack paymentStack;
            switch(rarity) {
                default:
                case COMMON:
                    paymentStack = new ItemStack(Items.OAK_LOG);
                    paymentStack.setCount(1);
                    break;
                case UNCOMMON:
                    paymentStack = new ItemStack(Items.IRON_INGOT);
                    paymentStack.setCount(1);
                    break;
                case RARE:
                    paymentStack = new ItemStack(Items.GOLD_INGOT);
                    paymentStack.setCount(1);
                    break;
                case EPIC:
                    paymentStack = new ItemStack(Items.DIAMOND);
                    paymentStack.setCount(1);
                    break;
            }
            // Decrease player's stack count
            stack.setCount(stack.getCount() - 1);

            // Create item to be tossed
            ItemEntity itemEntity = new ItemEntity(this.npc.getEntityWorld(), this.npc.getX(), this.npc.getBodyY(0.5D), this.npc.getZ(), paymentStack);
            itemEntity.setVelocity(this.npc.getRotationVector().multiply(0.1D));


            this.npc.getEntityWorld().spawnEntity(itemEntity);
        }

        return ActionResult.PASS;
    }

    @Override
    public boolean handleAttack(Entity attacker) {
        attacker.sendSystemMessage(new LiteralText("Ouch!"), this.npc.getUuid());
        if(attacker instanceof PlayerEntity)
            ((PlayerEntity) attacker).inventory.clear(); // Haha, there we go, they shouldn't have messed with us!

        return false; // false as "don't cancel the attack"
    }

    @Override
    public MerchantProfession create(TaterzenNPC taterzenNPC) {
        MerchantProfession profession = new MerchantProfession();

        // Save the Taterzen so you can use it later
        profession.npc = taterzenNPC;
        profession.npc.setCanPickUpLoot(true);

        return profession;
    }
}
