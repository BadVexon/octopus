package theOctopus.util;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theOctopus.cards.AbstractOctoCard;

public class OctoMagic extends DynamicVariable {

    @Override
    public String key() {
        return "octoMagic";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractOctoCard) card).isOctoMagicModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractOctoCard) card).octoMagic;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractOctoCard) card).baseOctoMagic;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractOctoCard) card).upgradedOctoMagic;
    }
}