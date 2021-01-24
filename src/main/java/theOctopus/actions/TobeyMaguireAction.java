package theOctopus.actions;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theOctopus.characters.TheOctopus;
import theOctopus.util.CenterGridCardSelectScreen;

import static theOctopus.OctoMod.makeCardPath;

public class TobeyMaguireAction extends AbstractGameAction {
    private boolean pickCard = false;
    private CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public TobeyMaguireAction() {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;
            group.addToTop(new ShiftingChoiceCard("Attack", "Attack", makeCardPath("AttackTobeyMaguire.png"), "Deal !D! damage to ALL enemies.", AbstractCard.CardType.ATTACK, 4, -1));
            group.addToTop(new ShiftingChoiceCard("Block", "Block", makeCardPath("SkillTobeyMaguire.png"), "Gain !B! Block.", AbstractCard.CardType.SKILL, -1, 2));

            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, 1, "Choose an Action", false);
        } else if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;
            AbstractCard cardChoice = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            CenterGridCardSelectScreen.centerGridSelect = false;

            if (cardChoice.cardID.contains("Attack")) {
                AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(group.findCardById("Attack").baseDamage), DamageInfo.DamageType.THORNS, AttackEffect.BLUNT_HEAVY));
            }
            if (cardChoice.cardID.contains("Block")) {
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, group.findCardById("Block").baseBlock));
            }

            isDone = true;
        }
        tickDuration();
    }

    private static class ShiftingChoiceCard extends CustomCard {
        private static final int COST = -2;
        private String baseID;

        ShiftingChoiceCard(String id, String name, String IMG, String description, CardType type, int damageAmt, int blockAmt) {
            super(id, name, IMG, COST, description, type, TheOctopus.Enums.COLOR_GRAY, CardRarity.SPECIAL, CardTarget.NONE);

            baseID = id;

            baseDamage = damageAmt;
            baseBlock = blockAmt;

        }

        @Override
        public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

        }

        @Override
        public void upgrade() {

        }
    }
}