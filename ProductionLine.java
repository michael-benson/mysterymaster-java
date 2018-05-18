package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class ProductionLine extends Puzzle {
	private final NounType times, weights;
	
	ProductionLine() {
		// Properties.
		myName = "ProductionLine";
		myTitle = "Production Line";

		// Nouns.
		NounType mothers = addNounType("Mother");
		Noun baker = mothers.addNoun("Baker");
		Noun brewer = mothers.addNoun("Brewer");
		Noun butcher = mothers.addNoun("Butcher");
		Noun carter = mothers.addNoun("Carter");
		Noun porter = mothers.addNoun("Porter");

		NounType babies = addNounType("Baby");
		Noun bernadette = babies.addNoun("Bernadette");
		Noun emma = babies.addNoun("Emma");
		Noun lucy = babies.addNoun("Lucy");
		Noun mark = babies.addNoun("Mark");
		Noun stephen = babies.addNoun("Stephen");

		times = addNounType("Time");
		Noun time0320 = times.addNoun("03:20");
		Noun time0505 = times.addNoun("05:05");
		Noun time1440 = times.addNoun("14:40");
		Noun time1615 = times.addNoun("16:15");
		Noun time2335 = times.addNoun("23:35");

		weights = addNounType("Weight");
		Noun weight6Lbs10Oz = weights.addNoun("6 lbs 10 oz");
		Noun weight7Lbs2Oz = weights.addNoun("7 lbs  2 oz");
		Noun weight7Lbs10Oz = weights.addNoun("7 lbs 10 oz");
		Noun weight8Lbs10Oz = weights.addNoun("8 lbs 10 oz");
		Noun weight9Lbs0Oz = weights.addNoun("9 lbs  0 oz");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link bornBefore = addLink("born before", times);
		bornBefore.f = SmartLink.getIsLessThan(0);

		Link within3HoursAfter = addLink("within 3 hours after", times);
		within3HoursAfter.f = (noun1, noun2) -> (noun1.num == 2 && noun2.num == 1) || (noun1.num == 4 && noun2.num == 3) ? Is : IsNot;

		Link onePoundLighterThan = addLink("one pound lighter than", weights);
		onePoundLighterThan.f = (noun1, noun2) -> (noun1.num == 1 && noun2.num == 3) || (noun1.num == 3 && noun2.num == 4) ? Is : IsNot;

		Link eightOzHeavierThan = addLink("8 ounces heavier than", weights);
		eightOzHeavierThan.f = (noun1, noun2) -> (noun1.num == 2 && noun2.num == 1) || (noun1.num == 3 && noun2.num == 2) ? Is : IsNot;

		Link justBefore = addLink("immediately before", times);
		justBefore.f = SmartLink.getIsLessBy(1);

		// Facts.
		List<Noun> guys = getList(mark, stephen);
		List<Noun> gals = getList(bernadette, emma, lucy);

		addFact("1", lucy, IsNot, With, getList(time0320, time0505, time2335, weight6Lbs10Oz));
		addFact("1", weight6Lbs10Oz, Is, bornBefore, guys);
		addFact("1", weight6Lbs10Oz, IsNot, With, time0320);

		addFact("2", butcher, IsNot, With, guys);
		addFact("2", butcher, Is, within3HoursAfter, emma);
		addFact("2", butcher, Is, onePoundLighterThan, time2335);

		addFact("3", weight9Lbs0Oz, IsNot, With, gals);
		addFact("3", weight9Lbs0Oz, IsNot, With, brewer);
		addFact("3", brewer, IsNot, With, gals);

		addFact("4", carter, Is, eightOzHeavierThan, bernadette);
		addFact("4", carter, IsNot, With, guys);
		addFact("4", porter, IsNot, With, stephen);
		addFactsInSequence("4", getList(weight6Lbs10Oz, carter, porter), Is, justBefore);

		// Solution.
		answer = new int[][]{{1, 4, 0, 2, 3}, {0, 4, 1, 2, 3}, {3, 2, 0, 1, 4}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Mother, 2=Baby, 3=Time, 4=Weight
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1:
						if (link.nounType == times)
							msg = "Mrs. " + noun1.name + "'s baby " + verb.name + " born " + link.name + " Mrs. " + noun2.name + "'s baby";
						break;
					case 2:
						if (link == With)
							msg = "Mrs. " + noun1.name + "s baby " + verb.name + " " + noun2.name;
						else if (link.nounType == times)
							msg = "Mrs. " + noun1.name + "'s baby " + verb.name + " born " + link.name + " " + noun2.name;
						else if (link.nounType == weights)
							msg = "Mrs. " + noun1.name + "'s baby " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3:
						if (link.nounType == weights)
							msg = "Mrs. " + noun1.name + "'s baby " + verb.name + " " + link.name + " the baby born at " + noun2.name;
						break;
					case 4: break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + verb.name + " born at " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + "'s weight " + verb.name + " " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The baby weighing " + noun1.name + " " + verb.name + " Mrs. " + noun2.name + "'s baby";
						else if (link.nounType == times)
							msg = "The baby weighing " + noun1.name + " " + verb.name + " " + link.name + " Mrs. " + noun2.name + "'s baby.";
						break;
					case 2:
						if (link == With)
							msg = "The baby weighing " + noun1.name + " " + verb.name + " " + noun2.name;
						if (link.nounType == times)
							msg = "The baby weighing " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = "The baby weighing " + noun1.name + " " + verb.name + " born at " + noun2.name;
						break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
