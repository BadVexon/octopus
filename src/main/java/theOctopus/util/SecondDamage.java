package theOctopus.util;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theOctopus.cards.AbstractOctoCard;

public class SecondDamage extends DynamicVariable {

    @Override
    public String key() {
        return "secondDamage";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractOctoCard) card).isSecondDamageModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractOctoCard) card).secondDamage;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractOctoCard) card).baseSecondDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractOctoCard) card).upgradedSecondDamage;
    }
}