package theOctopus.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theOctopus.OctoMod;
import theOctopus.characters.TheOctopus;
import theOctopus.powers.IndecisivePower;

import java.util.ArrayList;

import static theOctopus.OctoMod.makeCardPath;


public class Flexability extends AbstractChoiceCard {

    public static final String ID = OctoMod.makeID("Flexability");
    public static final String IMG = makeCardPath("Flexability.png");
    public static final AbstractCard.CardColor COLOR = TheOctopus.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 0;
    private static final int CHOICES = 2;

    public Flexability() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHOICES;
    }

    @Override
    public ArrayList<OctoChoiceCard> choiceList(AbstractMonster m) {
        ArrayList<OctoChoiceCard> list = new ArrayList<>();
        list.add(new OctoChoiceCard("StrengthThisTurn", "Strength This Turn", makeCardPath("Flexability.png"), "Gain 1 Strength. At the end of your turn, lose 1 Strength.", CardType.SKILL));
        list.add(new OctoChoiceCard("DexterityThisTurn", "Dexterity This Turn", makeCardPath("Flexability.png"), "Gain 1 Dexterity. At the end of your turn, lose 1 Dexterity.", AbstractCard.CardType.SKILL));
        return list;
    }

    @Override
    public void doChoiceStuff(AbstractMonster monster, CardGroup group, OctoChoiceCard cardChoice) {
        if (cardChoice.cardID.equals("StrengthThisTurn") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, 1), 1));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
        }
        if (cardChoice.cardID.equals("DexterityThisTurn") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseDexterityPower(AbstractDungeon.player, 1), 1));
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
