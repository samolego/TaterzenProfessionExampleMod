package com.example.profession_example_mod;

import com.example.profession_example_mod.command.MerchantCommand;
import com.example.profession_example_mod.profession.MerchantProfession;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.samo_lego.taterzens.api.TaterzensAPI;

public class ProfessionExampleMod implements ModInitializer {

    /**
     * Mod id, should be same as in fabric.mod.json
     */
    public static final String MOD_ID = "profession_example_mod";

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        System.out.println("Loading professions from : " + MOD_ID);

        // Registering our profession(s)
        // This will also make it automatically appear in command suggestions
        // for adding profession (`/npc edit professions add <PROFESSION_ID>`)
        TaterzensAPI.registerProfession(MerchantProfession.PROFESSION_ID, new MerchantProfession());

        // Registering command for editing merchant profession
        CommandRegistrationCallback.EVENT.register(MerchantCommand::register);
    }
}
