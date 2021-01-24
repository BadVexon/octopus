package theOctopus.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import theOctopus.OctoMod;
import theOctopus.characters.TheOctopus;
import theOctopus.powers.IndecisivePower;
import theOctopus.powers.FakeConstrictedPower;

import java.util.ArrayList;

import static theOctopus.OctoMod.makeCardPath;


public class Choke extends AbstractChoiceCard {

    public static final String ID = OctoMod.makeID("Choke");
    public static final String IMG = makeCardPath("Choke.png");
    public static final AbstractCard.CardColor COLOR = TheOctopus.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private static final int CHOICES = 1;

    public Choke() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHOICES;
    }

    @Override
    public ArrayList<OctoChoiceCard> choiceList(AbstractMonster m) {
        ArrayList<OctoChoiceCard> list = new ArrayList<>();
        list.add(new OctoChoiceCard("Constricted", "Constricted", makeCardPath("Choke.png"), "Apply 3 Constricted.", AbstractCard.CardType.SKILL));
        list.add(new OctoChoiceCard("Choke", "Choke", makeCardPath("Choke.png"), "Activate the enemy's Constricted.", AbstractCard.CardType.SKILL));
        return list;
    }

    @Override
    public void doChoiceStuff(AbstractMonster monster, CardGroup group, OctoChoiceCard cardChoice) {
        if (cardChoice.cardID.equals("Constricted") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster, AbstractDungeon.player, new FakeConstrictedPower(monster, AbstractDungeon.player, 3), 3));
        }
        if (cardChoice.cardID.equals("Choke") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            if (monster.hasPower(FakeConstrictedPower.POWER_ID)) {
                monster.getPower(FakeConstrictedPower.POWER_ID).flashWithoutSound();// 44
                monster.getPower(FakeConstrictedPower.POWER_ID).playApplyPowerSfx();// 45
                AbstractDungeon.actionManager.addToTop(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, monster.getPower(FakeConstrictedPower.POWER_ID).amount, DamageInfo.DamageType.THORNS)));
            }
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
