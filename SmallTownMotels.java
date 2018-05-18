package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class SmallTownMotels extends Puzzle {
		SmallTownMotels() {
		// Properties.
		myName = "SmallTownMotels";
		myTitle = "Small Town Motels";

		// Nouns.
		NounType years = addNounType("Founded");
		Noun year1860 = years.addNoun("1860");
		Noun year1870 = years.addNoun("1870");
		Noun year1888 = years.addNoun("1888");
		Noun year1889 = years.addNoun("1889");
		Noun year1901 = years.addNoun("1901");
		Noun year1902 = years.addNoun("1902");
		Noun year1923 = years.addNoun("1923");
		Noun year1924 = years.addNoun("1924");
		Noun year1925 = years.addNoun("1925");

		NounType streets = addNounType("Street");
		Noun willow = streets.addNoun("Willow");
		Noun maple = streets.addNoun("Maple");
		Noun upper = streets.addNoun("Upper");
		Noun ferry = streets.addNoun("Ferry");
		Noun pleasant = streets.addNoun("Pleasant");
		Noun first = streets.addNoun("First");
		Noun main = streets.addNoun("Main");
		Noun spring = streets.addNoun("Spring");
		Noun water = streets.addNoun("Water");

		NounType towns = addNounType("Town");
		Noun greenfield = towns.addNoun("Greenfield");
		Noun london = towns.addNoun("London");
		Noun port = towns.addNoun("Port");
		Noun hunter = towns.addNoun("Hunter");
		Noun cleanwater = towns.addNoun("Cleanwater");
		Noun greenup = towns.addNoun("Greenup");
		Noun summit = towns.addNoun("Summit");
		Noun newtown = towns.addNoun("Newtown");
		Noun springdale = towns.addNoun("Springdale");

		NounType states = addNounType("State");
		Noun delaware = states.addNoun("Delaware");
		Noun colorado = states.addNoun("Colorado");
		Noun kentucky = states.addNoun("Kentucky");
		Noun missouri = states.addNoun("Missouri");
		Noun wisconsin = states.addNoun("Wisconsin");
		Noun alabama = states.addNoun("Alabama");
		Noun iowa = states.addNoun("Iowa");
		Noun tennessee = states.addNoun("Tennessee");
		Noun ohio = states.addNoun("Ohio");

		NounType johnSlots = addNounType("John");
		Noun john1 = johnSlots.addNoun("1J");
		Noun john2 = johnSlots.addNoun("2J");
		Noun john3 = johnSlots.addNoun("3J");
		Noun john4 = johnSlots.addNoun("4J");
		Noun john5 = johnSlots.addNoun("5J");
		Noun john6 = johnSlots.addNoun("6J");
		Noun john7 = johnSlots.addNoun("7J");
		Noun john8 = johnSlots.addNoun("8J");
		Noun john9 = johnSlots.addNoun("9J");

		NounType aliceSlots = addNounType("Alice");
		Noun alice1 = aliceSlots.addNoun("1A");
		Noun alice2 = aliceSlots.addNoun("2A");
		Noun alice3 = aliceSlots.addNoun("3A");
		Noun alice4 = aliceSlots.addNoun("4A");
		Noun alice5 = aliceSlots.addNoun("5A");
		Noun alice6 = aliceSlots.addNoun("6A");
		Noun alice7 = aliceSlots.addNoun("7A");
		Noun alice8 = aliceSlots.addNoun("8A");
		Noun alice9 = aliceSlots.addNoun("9A");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link foundedAfter = addLink("founded after", years);
		foundedAfter.f = SmartLink.getIsMoreThan(0);

		// Years: 1860, 1870, 1888, 1889, 1901, 1902, 1923, 1924, 1925.
		Link founded24YearsAfter = addLink("founded 24 years after", years);
		founded24YearsAfter.f = (noun1, noun2) -> noun1.num == 9 && noun2.num == 5 ? Is : IsNot;

		Link johnVisited3WeeksAfter = addLink("visited by John three weeks after", johnSlots);
		johnVisited3WeeksAfter.f = SmartLink.getIsMoreBy(3);

		Link aliceVisitedAfter = addLink("visited by Alice after", aliceSlots);
		aliceVisitedAfter.f = SmartLink.getIsMoreThan(0);

		Link aliceVisited3WeeksAfter = addLink("visited by Alice three weeks after", aliceSlots);
		aliceVisited3WeeksAfter.f = SmartLink.getIsMoreBy(3);

		Link aliceTwiceVisitNum = addLink("twice Alice's visit number to", aliceSlots);
		aliceTwiceVisitNum.f = SmartLink.getHasRatio(1, 2);

		// Facts.
		// Clue 1. Alice's first stop was to London.
		addFact("1", alice1, Is, With, london);

		// Clue 2. John watched a different episode of a TV series on Maple Street, in Port, and in the motel that Alice visited fifth.
		addFact("2", getList(maple, port, alice5), IsNot, With);

		// Clue 3. John visited the motel in Hunter, Delaware fourth.
		addFact("3", getList(john4, hunter, delaware), Is, With);

		// Clue 4. Alice rented a different car for her visits to Cleanwater, the motel on Upper Street, and the town founded in 1924.
		addFact("4", getList(cleanwater, upper, year1924), IsNot, With);

		// Clue 5. The three motels on Ferry Street, the town founded in 1889, and in Greenup are, in some order, the ones in Delaware, the one John visited second, and the one Alice visited sixth.
		// Note: The lists are also for rule 1.
		List<Noun> list5a = getList(ferry, year1889, greenup);
		List<Noun> list5b = getList(delaware, john2, alice6);
		addFact("5", list5a, IsNot, With);
		addFact("5", list5b, IsNot, With);

		// Clue 6. The town in Colorado was founded after 1902, when some other town was founded.
		addFact("6", colorado, Is, foundedAfter, year1902);

		// Clue 7. The town in Kentucky was founded 24 years before the town with Pleasant Street.
		addFact("7", pleasant, Is, founded24YearsAfter, kentucky);

		// Clue 8. John ate at a different restaurant the weeks he was in Greenup, when he visited the motel that Alice visited first, and when he was in the town founded in 1860.
		addFact("8", getList(greenup, alice1, year1860), IsNot, With);

		// Clue 9. John's fifth visit was to Summit, Missouri.
		addFact("9", getList(john5, summit, missouri), Is, With);

		// Clue 10. The motel in Wisconsin and the one in a town founded in 1902 are on streets with different names.
		addFact("10", wisconsin, IsNot, With, year1902);

		// Clue 11. Alice visited the motel in Delaware three weeks after she visited the town founded in 1902.
		addFact("11", delaware, Is, aliceVisited3WeeksAfter, year1902);

		// Clue 12. Alice's second stop was to Missouri, where she enjoyed visiting the museum displaying the founding of the town in 1870.
		addFact("12", getList(alice2, missouri, year1870), Is, With);

		// Clue 13. John visited Wisconsin three weeks before he went to Pleasant Street.
		addFact("13", pleasant, Is, johnVisited3WeeksAfter, wisconsin);

		// Clue 14. Alice visited Newtown before she visited the motel on First Street.
		addFact("14", first, Is, aliceVisitedAfter, newtown);

		// Clue 15. The three towns with Upper Street, the one founded in 1923, and the one in Alabama are, in some order, the one John visited eighth, the one Alice visited third, and Cleanwater.
		// The lists are also for rule 2.
		List<Noun> list15a = getList(upper, year1923, alabama);
		List<Noun> list15b = getList(john8, alice3, cleanwater);
		addFact("15", list15a, IsNot, With);
		addFact("15", list15b, IsNot, With);

		// Clue 16. John visited the motel on historic Main Street three weeks after he visited Iowa.
		addFact("16", main, Is, johnVisited3WeeksAfter, iowa);

		// Clue 17. Six of the motels are the one in Greenup, the one on Spring Street, the one in a town founded in 1924, the one in Tennessee, the one John visited fourth, and the one Alice visited fifth.
		addFact("17", getList(greenup, spring, year1924, tennessee, john4, alice5), IsNot, With);

		// Clue 18. John's eighth stop was to Maple Street in a town founded in 1860.
		addFact("18", getList(john8, maple, year1860), Is, With);

		// Clue 19. The sequence number of Alice's stop in Iowa was twice the sequence number of her stop on Water Street.
		addFact("19", water, IsNot, With, iowa);

		// Alice's stop in Iowa cannot be an odd number.
		addFact("19", iowa, IsNot, With, getList(alice1, alice3, alice5, alice7, alice9));

		// Alice's stop to Water Street cannot be greater than 4.
		addFact("19", water, IsNot, With, getList(alice5, alice6, alice7, alice8, alice9));

		// Clue 20. John visited Delaware three weeks before he visited the town founded in 1888.
		addFact("20", year1888, Is, johnVisited3WeeksAfter, delaware);

		// Clue 21. John's last stop was to Springdale, a town founded in 1901.
		addFact("21", getList(john9, springdale, year1901), Is, With);

		// Clue 22. Alice counted a different number of churches within walking distance of her motels in the town founded in 1888, in Tennessee, and in the state John visited sixth.
		addFact("22", getList(year1888, tennessee, john6), IsNot, With);

		// Clue 23. John found different defects with the management of the motel on First Street, in the one in Ohio, and in the one that Alice visited fifth.
		addFact("23", getList(first, ohio, alice5), IsNot, With);

		// Analysis Facts.
		addFact("A1", alice4, IsNot, With, getList(maple, upper, main, alabama), "Alice's 4th stop is not Maple, Upper, Main, or Alabama.", false);

		// Rules.
		Rule rule1 = addRule("5", "The three motels on Ferry Street, the town founded in 1889, and in Greenup are, in some order, the ones in Delaware, the one John visited second, and the one Alice visited sixth.");
		rule1.f = smartRule.getMatchOneToOne(rule1, list5a, list5b);

		Rule rule2 = addRule("15", "The three towns with Upper Street, the one founded in 1923, and the one in Alabama are, in some order, the one John visited eighth, the one Alice visited third, and Cleanwater.");
		rule2.f = smartRule.getMatchOneToOne(rule2, list15a, list15b);

		Rule rule3 = addRule("19", "The sequence number of Alice's stop in Iowa was twice the sequence number of her stop on Water Street.");
		rule3.f = (mark) -> {
			int rs = 0;
			int a = Mark.getPairNounNum(iowa, aliceSlots);  // Get Alice's visit to Iowa.
			int b = Mark.getPairNounNum(water, aliceSlots); // Get Alice's visit to Water Street.
			if (a > 0 && b > 0 && a != 2*b) return -1;

			// Check if the mark is a 'X' for noun1 and noun2 where noun2 is for Alice's visits.
			if (mark.verb != IsNot || mark.noun2.type != aliceSlots) return rs;
			int n2 = mark.noun2.num;

			// If Alice cannot visit Iowa (visit n2), then Alice cannot visit Water Street for visit n2/2.
			if (mark.noun1 == iowa) {
				if (n2 % 2 == 0) {
					Noun noun = aliceSlots.nouns.get(n2 / 2 - 1);
					String msg = water.name + " cannot be Alice's " + noun.name + " visit.";
					rs = solver.addMarkByRule(mark, rule3, 'a', water, IsNot, noun, msg);
					if (rs != 0) return rs;
				}
			}

			// If Alice cannot visit Water Street (visit n2), then Alice cannot visit Iowa for visit 2*n2.
			if (mark.noun1 == water) {
				if (n2 < 5) {
					Noun noun = aliceSlots.nouns.get(2 * n2 - 1);
					String msg = iowa.name + " cannot be Alice's " + noun.name + " visit.";
					rs = solver.addMarkByRule(mark, rule3, 'b', iowa, IsNot, noun, msg);
					if (rs != 0) return rs;
				}
			}

			return rs;
		};

		// Solution.
		answer = new int[][]{{1, 6, 7, 5, 2, 8, 0, 3, 4}, {0, 6, 1, 3, 8, 7, 4, 2, 5}, {5, 3, 8, 0, 2, 7, 4, 6, 1}, {7, 4, 6, 3, 8, 0, 2, 1, 5}, {8, 1, 0, 6, 2, 3, 4, 7, 5}};
	}

		@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Founded, 2=Street, 3=Town, 4=State, 5=John, 6=Alice
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg = "The town founded in " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = "The town founded in " + noun1.name + " " + verb.name + " in " + noun2.name;
						else
							msg = "The town founded in " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = "The town founded in " + noun1.name + " " + verb.name + " John's " + noun2.name + " visit";
						break;
					case 6:
						if (link == With)
							msg = "The town founded in " + noun1.name + " " + verb.name + " Alice's " + noun2.name + " visit";
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " street " + verb.name + " in the town founded in " + noun2.name;
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + " street " + verb.name + " in " + noun2.name;
						else
							msg = noun1.name + " street " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + " street " + verb.name + " in " + noun2.name;
						else
							msg = noun1.name + " street " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = noun1.name + " street " + verb.name + " John's " + noun2.name + " visit";
						break;
					case 6:
						if (link == With)
							msg = noun1.name + " street " + verb.name + " Alice's " + noun2.name + " visit";
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + verb.name + " founded in " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the town with " + noun2.name + " street";
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + verb.name + " in " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = noun1.name + " " + verb.name + " John's " + noun2.name + " visit";
						break;
					case 6:
						if (link == With)
							msg = noun1.name + " " + verb.name + " Alice's " + noun2.name + " visit";
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The town in " + noun1.name + " " + verb.name + " founded in " + noun2.name;
						else
							msg = "The town in " + noun1.name + " " + verb.name + " " + link.name + " the town founded in " + noun2.name;
						break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5:
						if (link == With)
							msg = noun1.name + " " + verb.name + " John's " + noun2.name + " visit";
						break;
					case 6:
						if (link == With)
							msg = noun1.name + " " + verb.name + " Alice's " + noun2.name + " visit";
						break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "John's " + noun1.name + " visit " + verb.name + " to the town founded in " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = "John's " + noun1.name + " visit " + verb.name + " to " + noun2.name + " street";
						break;
					case 3:
						if (link == With)
							msg = "John's " + noun1.name + " visit " + verb.name + " to " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = "John's " + noun1.name + " visit " + verb.name + " to " + noun2.name;
						break;
					case 5:
						break;
					case 6:
						if (link == With)
							msg = "John's " + noun1.name + " visit " + verb.name + " to the motel Alice visited " + noun2.name;
						break;
				}
				break;
			case 6:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "Alices's " + noun1.name + " visit " + verb.name + " to the town founded in " + noun2.name;
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = "Alice's " + noun1.name + " visit " + verb.name + " to " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = "Alice's " + noun1.name + " visit " + verb.name + " to " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = "Alice's " + noun1.name + " visit " + verb.name + " to the motel John visited " + noun2.name;
						break;
					case 6: break;
				}
				break;
		}

		return msg + ".";
	}
}
