package theOctopus.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import theOctopus.OctoMod;
import theOctopus.actions.BossDamageAction;
import theOctopus.characters.TheOctopus;

import static theOctopus.OctoMod.makeCardPath;

public class CannonShot extends AbstractOctoCard {

    public static final String ID = OctoMod.makeID("CannonShot");
    public static final String IMG = makeCardPath("CannonShot.png");
    public static final AbstractCard.CardColor COLOR = TheOctopus.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;

    public CannonShot() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = 8;
        baseSecondDamage = secondDamage = 4;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
            addToBot(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(p, secondDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        } else {
            AbstractDungeon.actionManager.addToNextCombat(new BossDamageAction(secondDamage));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeSecondDamage(1);
            initializeDescription();
        }
    }
}
