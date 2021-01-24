package theOctopus.cards;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theOctopus.actions.OctoChoiceAction;
import theOctopus.powers.BoostNextChoicePower;
import theOctopus.powers.BoostNextChoiceThisTurnPower;
import theOctopus.powers.BoostNextChoicesPower;
import theOctopus.powers.KrakenPower;

import java.util.ArrayList;

public abstract class AbstractChoiceCard extends AbstractOctoCard {

    public AbstractChoiceCard(final String id, final String name, final String img, final int cost, final String rawDescription, final CardType type, final CardColor color, final CardRarity rarity, final CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public void applyPowers() {
        super.applyPowers();
        int base = this.baseMagicNumber;
        if (AbstractDungeon.player.hasPower(BoostNextChoicePower.POWER_ID)) {
            base += AbstractDungeon.player.getPower(BoostNextChoicePower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(BoostNextChoicesPower.POWER_ID)) {
            base += 1;
        }
        if (AbstractDungeon.player.hasPower(BoostNextChoiceThisTurnPower.POWER_ID)) {
            base += AbstractDungeon.player.getPower(BoostNextChoiceThisTurnPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(KrakenPower.POWER_ID)) {
            base += AbstractDungeon.player.getPower(KrakenPower.POWER_ID).amount;
        }
        this.magicNumber = base;
        this.isMagicNumberModified = (this.magicNumber != this.baseMagicNumber);
    }

    public abstract ArrayList<OctoChoiceCard> choiceList(AbstractMonster m);

    public void doChoiceStuff(AbstractMonster monster, CardGroup group, OctoChoiceCard cardChoice) {

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (magicNumber > 0) {
            for (int i = 0; i < magicNumber; i++) {
                addToBot(new OctoChoiceAction(m, this, magicNumber - i));
            }
        }
    }
}