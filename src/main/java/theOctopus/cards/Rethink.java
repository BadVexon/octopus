package theOctopus.cards;

import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theOctopus.OctoMod;
import theOctopus.actions.DrawPileOrderAction;
import theOctopus.characters.TheOctopus;
import theOctopus.powers.IndecisivePower;

import java.util.ArrayList;

import static theOctopus.OctoMod.makeCardPath;

public class Rethink extends AbstractChoiceCard {

    public static final String ID = OctoMod.makeID("Rethink");
    public static final String IMG = makeCardPath("Rethink.png");
    public static final AbstractCard.CardColor COLOR = TheOctopus.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 1;
    private static final int CHOICES = 1;

    public Rethink() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHOICES;
        exhaust = true;
    }

    @Override
    public ArrayList<OctoChoiceCard> choiceList(AbstractMonster m) {
        ArrayList<OctoChoiceCard> list = new ArrayList<>();
        list.add(new OctoChoiceCard("DrawPile", "Draw Pile", makeCardPath("Rethink.png"), "Put a card from your draw pile on top of your draw pile.", AbstractCard.CardType.SKILL));
        list.add(new OctoChoiceCard("DiscardPile", "Discard Pile", makeCardPath("Rethink.png"), "Put a card from your discard pile on top of your draw pile.", AbstractCard.CardType.SKILL));
        return list;
    }

    @Override
    public void doChoiceStuff(AbstractMonster monster, CardGroup group, OctoChoiceCard cardChoice) {
        if (cardChoice.cardID.equals("DrawPile") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            addToTop(new DrawPileOrderAction());
        }
        if (cardChoice.cardID.equals("DiscardPile") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            addToTop(new DiscardPileToTopOfDeckAction(AbstractDungeon.player));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}
