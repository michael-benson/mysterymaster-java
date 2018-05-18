package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class LuckyStreets extends Puzzle {
	private final NounType days, coins;
	
	LuckyStreets() {
		// Properties.
		myName = "LuckyStreets";
		myTitle = "Lucky Streets";

		// Nouns.
		days = addNounType("Day");
		Noun monday = days.addNoun("Monday");
		Noun tuesday = days.addNoun("Tuesday");
		Noun wednesday = days.addNoun("Wednesday");
		Noun thursday = days.addNoun("Thursday");
		Noun friday = days.addNoun("Friday");
		Noun saturday = days.addNoun("Saturday");

		coins = addNounType("Coin");
		Noun penny = coins.addNoun("penny");
		Noun nickel = coins.addNoun("nickel");
		Noun dime = coins.addNoun("dime");
		Noun quarter = coins.addNoun("quarter");
		Noun halfDollar = coins.addNoun("half dollar");
		Noun dollar = coins.addNoun("dollar");

		NounType streets = addNounType("Street");
		Noun alder = streets.addNoun("Alder");
		Noun first = streets.addNoun("First");
		Noun betts = streets.addNoun("Betts");
		Noun elm = streets.addNoun("Elm");
		Noun cherry = streets.addNoun("Cherry");
		Noun delta = streets.addNoun("Delta");

		NounType buildings = addNounType("Building");
		Noun veranda = buildings.addNoun("Veranda Restaurant", "Veranda");
		Noun harris = buildings.addNoun("Harris Hotel");
		Noun cityHall = buildings.addNoun("City Hall");
		Noun state = buildings.addNoun("State Bank");
		Noun updike = buildings.addNoun("Updike Building", "Updike");
		Noun walpole = buildings.addNoun("Walpole Building", "Walpole");

		NounType wishes = addNounType("Wish");
		Noun reunion = wishes.addNoun("Monkees reunion", "Reunion");
		Noun lottery = wishes.addNoun("lottery win", "Lottery");
		Noun rome = wishes.addNoun("Rome visit");
		Noun sunshine = wishes.addNoun("sunshine");
		Noun cubs = wishes.addNoun("Cubs win");
		Noun prosperity = wishes.addNoun("prosperity");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link earlierThan = addLink("earlier in the week than", days);
		earlierThan.f = SmartLink.getIsLessThan(0);

		Link twoTimesValue = addLink("twice the value of", coins);
		twoTimesValue.f = (noun1, noun2) ->
		(noun1 == dime && noun2 == nickel) ||
		(noun1 == halfDollar && noun2 == quarter) ||
		(noun1 == dollar && noun2 == halfDollar) ? Is : IsNot;

		Link fiveTimesValue = addLink("five times the value of", coins);
		fiveTimesValue.f = (noun1, noun2) ->
		(noun1 == nickel && noun2 == penny) ||
		(noun1 == quarter && noun2 == nickel) ||
		(noun1 == halfDollar && noun2 == dime) ? Is : IsNot;

		// Facts.
		addFact("1", alder, Is, twoTimesValue, harris);
		addFact("1", wednesday, Is, fiveTimesValue, first);

		addFactsOneToOne("2", getList(saturday, lottery), Is, fiveTimesValue, getList(state, thursday));

		addFactsOneToOne("3", getList(rome, cityHall), IsNot, With, getList(penny, nickel));
		addFactsInSequence("3", getList(state, dollar, betts), Is, earlierThan);

		addFact("4", sunshine, Is, With, updike);
		addFactsInSequence("4", getList(updike, elm, cubs), Is, earlierThan);

		List<Noun> list5 = getList(quarter, cherry, lottery);
		addFact("5", list5, IsNot, With);
		addFact("5", list5, Is, earlierThan, friday);
		addFact("5", halfDollar, IsNot, With, saturday);

		addFact("6", getList(delta, updike, walpole, prosperity, nickel, tuesday), IsNot, With);

		// Solution.
		answer = new int[][]{{3, 0, 4, 2, 5, 1}, {5, 4, 0, 1, 3, 2}, {1, 3, 5, 4, 2, 0}, {2, 0, 1, 3, 5, 4}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Day, 2=Coin, 3=Street, 4=Building, 5=Wish
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link.nounType == coins)
							msg = "The coin on " + noun1.name + " " + verb.name + " " + link.name + " the coin on " + noun2.name + " street";
						break;
					case 4:
						if (link.nounType == coins)
							msg = "The coin on " + noun1.name + " " + verb.name + " " + link.name + " the coin at the " + noun2.name;
						break;
					case 5: break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " found on " + noun2.name;
						else if (link.nounType == days)
							msg = "The " + noun1.name + " " + verb.name + " found " + link.name + " " + noun2.name;
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " found on " + noun2.name + " street";
						else if (link.nounType == days)
							msg = "The " + noun1.name + " " + verb.name + " found " + link.name + " the coin on " + noun2.name + " street";
						break;
					case 4: break;
					case 5:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " the coin she wished for the " + noun2.name;
						break;
				}
				break;
			case 3:
				msg = "The coin on " + noun1.name + " street " + verb.name + " ";
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += "found on " + noun2.name;
						else if (link.nounType == days)
							msg += "found " + link.name + " " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg += "the " + noun2.name;
						break;
					case 3:break;
					case 4:
						if (link == With)
							msg += "the coin at the " + noun2.name;
						else
							msg += link.name + " the one at the " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg += "the one she wished for the " + noun2.name;
						if (link.nounType == days)
							msg += "found " + link.name + " the wish for the " + noun2.name;
						break;
				}
				break;
			case 4:
				msg = "The coin at " + noun1.name + " " + verb.name + " ";
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += "found on " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg += "the " + noun2.name;
						else if (link.nounType == days)
							msg += "found " + link.name + " the " + noun2.name;
						else if (link.nounType == coins)
							msg += link.name + " the " + noun2.name;
						break;
					case 3:
						if (link != With)
							msg += link.name + " the coin on " + noun2.name + " street";
						break;
					case 4: break;
					case 5:
						if (link == With)
							msg += "the coin she wished for the " + noun2.name;
						break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The coin she wished for the " + noun1.name + " " + verb.name + " the " + noun2.name;
						else if (link.nounType == days)
							msg = "The wish for the " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						else if (link.nounType == coins)
							msg = "The coin she wished for the " + noun1.name + " " + verb.name + " " + link.name + " the coin on " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = "The coin she wished for the " + noun1.name + " " + verb.name + " the " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg = "The wish for the " + noun1.name + " " + verb.name + " at the " + noun2.name;
						break;
					case 5: break;
				}
				break;
		}

		return msg + ".";
	}
}
