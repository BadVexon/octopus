package theOctopus.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractOctoCard extends CustomCard {

    public int octoMagic;
    public int baseOctoMagic;
    public boolean upgradedOctoMagic;
    public boolean isOctoMagicModified;

    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;

    public AbstractOctoCard(final String id, final String name, final String img, final int cost, final String rawDescription, final CardType type, final CardColor color, final CardRarity rarity, final CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isOctoMagicModified = false;
        isSecondDamageModified = false;
    }

    @Override
    public void applyPowers() {
        secondDamage = baseSecondDamage;

        int tmp = baseDamage;
        baseDamage = baseSecondDamage;

        super.applyPowers();

        secondDamage = damage;
        baseDamage = tmp;

        super.applyPowers();

        isSecondDamageModified = (secondDamage != baseSecondDamage);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        secondDamage = baseSecondDamage;

        int tmp = baseDamage;
        baseDamage = baseSecondDamage;

        super.calculateCardDamage(mo);

        secondDamage = damage;
        baseDamage = tmp;

        super.calculateCardDamage(mo);

        isSecondDamageModified = (secondDamage != baseSecondDamage);
    }

    @Override
    public void onMoveToDiscard() {
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedOctoMagic) {
            octoMagic = baseOctoMagic;
            isOctoMagicModified = true;
        }
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
    }

    void upgradeOctoMagic(int amount) {
        baseOctoMagic += amount;
        octoMagic = baseOctoMagic;
        upgradedOctoMagic = true;
    }

    void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }
}