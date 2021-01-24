package theOctopus.cards;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theOctopus.OctoMod;
import theOctopus.actions.ChangeGoldAction;
import theOctopus.characters.TheOctopus;
import theOctopus.powers.IndecisivePower;

import java.util.ArrayList;

import static theOctopus.OctoMod.makeCardPath;


public class TreasureChest extends AbstractChoiceCard {

    public static final String ID = OctoMod.makeID("TreasureChest");
    public static final String IMG = makeCardPath("Expansion.png");
    public static final AbstractCard.CardColor COLOR = TheOctopus.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;
    private static final int CHOICES = 1;

    public TreasureChest() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.isEthereal = true;
        this.exhaust = true;
        baseMagicNumber = magicNumber = CHOICES;
        this.tags.add(CardTags.HEALING);
    }



    @Override
    public ArrayList<OctoChoiceCard> choiceList(AbstractMonster m) {
        ArrayList<OctoChoiceCard> list = new ArrayList<>();
        list.add(new OctoChoiceCard("Heal", "Heal", makeCardPath("Expansion.png"), "Heal 5 HP.", AbstractCard.CardType.SKILL));
        list.add(new OctoChoiceCard("Gold", "Gold", makeCardPath("Expansion.png"), "Gain 10 Gold.", AbstractCard.CardType.SKILL));
        list.add(new OctoChoiceCard("Potion", "Potion", makeCardPath("Expansion.png"), "Obtain a random potion.", AbstractCard.CardType.SKILL));
        return list;
    }

    @Override
    public void doChoiceStuff(AbstractMonster monster, CardGroup group, OctoChoiceCard cardChoice) {
        if (cardChoice.cardID.equals("Heal") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 5));
        }
        if (cardChoice.cardID.equals("Gold") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ChangeGoldAction(10));
        }
        if (cardChoice.cardID.equals("Potion") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
