package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class CircusAnniversaries extends Puzzle {
	CircusAnniversaries() {
		// Properties.
		myName = "CircusAnniversaries";
		myTitle = "Circus Anniversaries";

		// Nouns.
		NounType slots = addNounType("Years");
		Noun slot09 = slots.addNoun("9");
		Noun slot10 = slots.addNoun("10");
		Noun slot11 = slots.addNoun("11");
		Noun slot12 = slots.addNoun("12");
		Noun slot13 = slots.addNoun("13");

		NounType firstNames = addNounType("First Name");
		Noun brad = firstNames.addNoun("Brad");
		Noun cindy = firstNames.addNoun("Cindy");
		Noun dean = firstNames.addNoun("Dean");
		Noun kristen = firstNames.addNoun("Kristen");
		Noun lisa = firstNames.addNoun("Lisa");

		NounType lastNames = addNounType("Last Name");
		Noun enright = lastNames.addNoun("Enright");
		Noun gleason = lastNames.addNoun("Gleason");
		Noun quinlan = lastNames.addNoun("Quinlan");
		Noun raasch = lastNames.addNoun("Raasch");
		Noun ziegler = lastNames.addNoun("Ziegler");

		NounType acts = addNounType("Act");
		Noun aerialist = acts.addNoun("aerialist");
		Noun clown = acts.addNoun("clown");
		Noun juggler = acts.addNoun("juggler");
		Noun lionTamer = acts.addNoun("lion tamer");
		Noun tightrope = acts.addNoun("tightrope walker", "Tightrope");

		// Links.
		Link lessThan = addLink("less than", slots);
		lessThan.f = SmartLink.getIsLessThan(0);

		Link oneLess = addLink("one year less than", slots);
		oneLess.f = SmartLink.getIsLessBy(1);

		Link twoLess = addLink("two years less than", slots);
		twoLess.f = SmartLink.getIsLessBy(2);

		// Facts.
		List<Noun> guys = getList(brad, dean);
		List<Noun>  gals = getList(cindy, kristen, lisa);

		addFact("1", clown, IsNot, With, enright);
		addFact("1", clown, Is, twoLess, lisa);
		addFact("1", lisa, Is, oneLess, raasch);

		List<Noun>  list2 = getList(juggler, ziegler, slot10);
		addFact("2", list2, IsNot, With);
		addFact("2", guys, IsNot, With, list2);

		addFact("3", quinlan, Is, lessThan, gleason);

		addFactsInSequence("4", getList(enright, dean, tightrope), Is, oneLess);
		addFact("4", tightrope, IsNot, With, brad);

		addFact("5", kristen, IsNot, With, getList(ziegler, lionTamer));

		// Solution.
		answer = new int[][]{{0, 3, 4, 2, 1}, {2, 1, 0, 3, 4}, {1, 0, 2, 3, 4}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Years, 2=First Name, 3=Last Name, 4=Act.
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
				}
				break;
			case 2:
				msg = noun1.name + " ";
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += (verb == Is ? "has" : "has not") + " been with the circus " + noun2.name + " years";
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg += verb.name + " " + noun2.name;
						else
							msg += (verb == Is ? "has" : "has not") + " been with the circus " + link.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg += verb.name + " the " + noun2.name;
						else
							msg += (verb == Is ? "has" : "has not") + " been with the circus " + link.name + " the " + noun2.name;
						break;
				}
				break;
			case 3:
				msg = noun1.name + " ";
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += (verb == Is ? "has" : "has not") + " been with the circus " + noun2.name + " years";
						break;
					case 2:
						if (link != With)
							msg += (verb == Is ? "has" : "has not") + " been with the circus " + link.name + " " + noun2.name;
						break;
					case 3:
						msg += (verb == Is ? "has" : "has not") + " been with the circus " + link.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg += verb.name + " the " + noun2.name;
						break;
				}
				break;
			case 4:
				msg = "The " + noun1.name + " ";
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += (verb == Is ? "has" : "has not") + " been with the circus " + noun2.name + " years";
						break;
					case 2:
						if (link == With)
							msg += verb.name + " " + noun2.name;
						else
							msg += (verb == Is ? "has" : "has not") + " been with the circus " + link.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg += verb.name + " " + noun2.name;
						break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
