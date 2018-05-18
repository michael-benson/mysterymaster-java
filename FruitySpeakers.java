package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class FruitySpeakers extends Puzzle {
	FruitySpeakers() {
		// Properties.
		myName = "FruitySpeakers";
		myTitle = "Fruity Speakers";

		// Nouns.
		NounType days = addNounType("Day");
		Noun monday = days.addNoun("Monday");
		Noun tuesday = days.addNoun("Tuesday");
		Noun wednesday = days.addNoun("Wednesday");
		Noun thursday = days.addNoun("Thursday");
		Noun friday = days.addNoun("Friday");

		NounType growers = addNounType("Grower");
		Noun alvinPipps = growers.addNoun("Alvin Pipps");
		Noun cyTruss = growers.addNoun("Cy Truss");
		Noun elmerPeel = growers.addNoun("Elmer Peel");
		Noun jakeSkinn = growers.addNoun("Jake Skinn");
		Noun zachZesty = growers.addNoun("Zach Zesty");

		NounType fruits = addNounType("Fruit");
		Noun grapefruits = fruits.addNoun("grapefruits");
		Noun lemons = fruits.addNoun("lemons");
		Noun limes = fruits.addNoun("limes");
		Noun oranges = fruits.addNoun("oranges");
		Noun pineapples = fruits.addNoun("pineapples");

		NounType states = addNounType("State");
		Noun alabama = states.addNoun("Alabama");
		Noun california = states.addNoun("California");
		Noun florida = states.addNoun("Florida");
		Noun georgia = states.addNoun("Georgia");
		Noun hawaii = states.addNoun("Hawaii");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link dayAfter = addLink("the day after", days);
		dayAfter.f = SmartLink.getIsMoreBy(1);

		Link twoDaysAfter = addLink("two days after", days);
		twoDaysAfter.f = SmartLink.getIsMoreBy(2);

		// Facts.
		addFact("1", alvinPipps, Is, With, lemons);
		addFact("1", alvinPipps, Is, twoDaysAfter, florida);
		addFact("2", jakeSkinn, Is, dayAfter, oranges);
		addFact("3", zachZesty, Is, With, alabama);
		addFact("4", pineapples, Is, With, hawaii);
		addFact("5", limes, Is, With, thursday);
		addFact("6", monday, Is, With, california);
		addFact("7", elmerPeel, IsNot, With, grapefruits);

		// Solution.
		answer = new int[][]{{2, 3, 1, 4, 0}, {3, 4, 0, 2, 1}, {1, 4, 2, 0, 3}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Day, 2=Grower, 3=Fruit, 4=State
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4:
						if (link == With)
							msg = "The " + noun1.name + " address " + verb.name + " by the grower from " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "grows" : "does not grow") + " " + noun2.name;
						else
							msg = noun1.name + " " + (verb == Is ? "spoke" : "did not speak") + " " + link.name + " the talk on " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + verb.name + " from " + noun2.name;
						else
							msg = noun1.name + " " + (verb == Is ? "spoke" : "did not speak") + " " + link.name + " the grower from " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The talk on " + noun1.name + " " + verb.name + " on " + noun2.name;
						break;
					case 2: break;
					case 3: break;
					case 4:
						if (link == With)
							msg = "The grower of " + noun1.name + " " + verb.name + " from " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
