package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class PlayingCards extends Puzzle {
	PlayingCards() {
		// Properties.
		myName = "PlayingCards";
		myTitle = "Playing Cards";

		// Nouns.
		NounType players = addNounType("Player");
		Noun bart = players.addNoun("Bart");
		Noun chet = players.addNoun("Chet");
		Noun kitty = players.addNoun("Kitty");
		Noun paladin = players.addNoun("Paladin");

		NounType ranks = addNounType("Rank");
		Noun two = ranks.addNoun("two");
		Noun three = ranks.addNoun("three");
		Noun four = ranks.addNoun("four");
		Noun five = ranks.addNoun("five");

		NounType suits = addNounType("Suit");
		Noun club = suits.addNoun("club");
		Noun diamond = suits.addNoun("diamond");
		Noun heart = suits.addNoun("heart");
		Noun spade = suits.addNoun("spade");

		// Links.
		Link higherThan = addLink("higher than", ranks);
		higherThan.f = SmartLink.getIsMoreThan(0);

		Link oneHigherThan = addLink("one higher than", ranks);
		oneHigherThan.f = SmartLink.getIsMoreBy(1);

		// Facts.
		addFact("1", chet, IsNot, With, getList(club, spade, two));
		addFact("1", kitty, Is, oneHigherThan, chet);
		addFact("3", four, IsNot, With, getList(diamond, heart));

		// Rules.
		Rule rule1 = addRule("1", "Chet holds the highest red card.", getList(chet));
		rule1.f = (mark) -> {
			int rs = 0;
			String msg = "";

			// Violation if the other red card is higher than Chet's card.
			int a = Mark.getPairNounNum(chet, ranks);
			int b = Mark.getPairNounNum(chet, suits);
			if (a > 0 && b > 0) {
				// Get the rank of the other red card.
				int c = (b == 2 ? Mark.getPairNounNum(heart, ranks) : Mark.getPairNounNum(diamond, ranks));
				if (c > a) return -1;
			}

			// If the current mark has Chet with the three, then the two is not black, and the four and five are not red.
			if (mark.noun1 == chet && mark.verb == Is && mark.noun2 == three) {
				msg = "the two is not a black card.";
				rs = solver.addMarkByRule(mark, rule1, 'a', two, IsNot, club, msg);
				if (rs != 0) return rs;
				rs = solver.addMarkByRule(mark, rule1, 'a', two, IsNot, spade, msg);
				if (rs != 0) return rs;

				msg = "the four is not a red card.";
				rs = solver.addMarkByRule(mark, rule1, 'b', four, IsNot, diamond, msg);
				if (rs != 0) return rs;
				rs = solver.addMarkByRule(mark, rule1, 'b', four, IsNot, heart, msg);
				if (rs != 0) return rs;

				msg = "the five is not a red card.";
				rs = solver.addMarkByRule(mark, rule1, 'c', five, IsNot, diamond, msg);
				if (rs != 0) return rs;
				rs = solver.addMarkByRule(mark, rule1, 'c', five, IsNot, heart, msg);
				if (rs != 0) return rs;
			}
			return rs;
		};

		Rule rule2 = addRule("2", "Bart and Kitty hold cards of the same color.");
		List<List<Noun>> array2D = new ArrayList<List<Noun>>() {{
			add(Arrays.asList(club, spade));
			add(Arrays.asList(diamond, heart));
		}};
		rule2.f = smartRule.getMatchOneList(rule2, getList(bart, kitty), array2D);
		
		Rule rule3 = addRule("4", "The sum of the spade and diamond is higher than the sum of the club and heart.", getList(club, diamond, heart, spade));
		rule3.f = (mark) -> {
			int rs = 0;

			// Get the rank for each card.
			Noun clubRank = Mark.getPairNoun(club, ranks);
			Noun diamondRank = Mark.getPairNoun(diamond, ranks);
			Noun heartRank = Mark.getPairNoun(heart, ranks);
			Noun spadeRank = Mark.getPairNoun(spade, ranks);

			// Violation if the sum of the spade and diamond is not higher than the sum of the club and heart.
			if (clubRank != null && diamondRank != null && heartRank != null && spadeRank != null) {
				if (spadeRank.num + diamondRank.num <= clubRank.num + heartRank.num) rs = -1;
			}

			// Violation if the diamond or the spade is the two.
			if (diamondRank == two || spadeRank == two) return -1;

			// Violation if the club or the heart is the five.
			if (clubRank == five || heartRank == five) return -1;

			// Trigger. Either the diamond or the spade must be the five.
			if (mark.noun1 == five && mark.verb == IsNot) {
				if (mark.noun2 == diamond) {
					String msg = "The spade must be the five.";
					rs = solver.addMarkByRule(mark, rule3, 'a', spade, Is, five, msg);
					if (rs != 0) return rs;
				}
				else if (mark.noun2 == spade) {
					String msg = "The diamond must be the five.";
					rs = solver.addMarkByRule(mark, rule3, 'b', diamond, Is, five, msg);
					if (rs != 0) return rs;
				}
			}

			// Trigger. Either the club or the heart must be the two.
			if (mark.noun1 == two && mark.verb == IsNot) {
				if (mark.noun2 == club) {
					String msg = "The heart must be the two.";
					rs = solver.addMarkByRule(mark, rule3, 'c', heart, Is, two, msg);
					if (rs != 0) return rs;
				}
				else if (mark.noun2 == heart) {
					String msg = "The club must be the two.";
					rs = solver.addMarkByRule(mark, rule3, 'd', club, Is, two, msg);
					if (rs != 0) return rs;
				}
			}

			return rs;
		};

		// Solution.
		answer = new int[][]{{3, 1, 2, 0}, {3, 1, 0, 2}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Player, 2=Rank, 3=Suit
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1:
						msg = noun1.name + "'s card " + verb.name + " " + link.name + " " + noun2.name + "'s card";
						break;
					case 2:
						if (link == With)
							msg = noun1.name + "'s card " + verb.name + " the " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = noun1.name + "'s card " + verb.name + " a " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " a " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
				}
				break;
		}

		return msg + ".";
	}
}
