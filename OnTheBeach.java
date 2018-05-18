package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class OnTheBeach extends Puzzle {
	OnTheBeach() {
		// Properties.
		myName = "OnTheBeach";
		myTitle = "On The Beach";

		// Nouns.
		NounType firstNames = addNounType("First Name");
		Noun angela = firstNames.addNoun("Angela");
		Noun darren = firstNames.addNoun("Darren");
		Noun kevin = firstNames.addNoun("Kevin");
		Noun susan = firstNames.addNoun("Susan");
		Noun wayne = firstNames.addNoun("Wayne");

		NounType lastNames = addNounType("Surname");
		Noun dale = lastNames.addNoun("Dale");
		Noun hill = lastNames.addNoun("Hill");
		Noun robinson = lastNames.addNoun("Robinson");
		Noun smith1 = lastNames.addNoun("Smith1");
		Noun smith2 = lastNames.addNoun("Smith2");

		NounType ages = addNounType("Age");
		Noun six = ages.addNoun("6");
		Noun seven = ages.addNoun("7");
		Noun eight1 = ages.addNoun("8a");
		Noun eight2 = ages.addNoun("8b");
		Noun nine = ages.addNoun("9");

		NounType acts = addNounType("Activity");
		Noun sandCastle = acts.addNoun("build sand castle", "Sand Castle");
		Noun buriedDad = acts.addNoun("bury dad");
		Noun caughtCrabs = acts.addNoun("catch crabs");
		Noun gotShells = acts.addNoun("collect shells", "Shells");
		Noun paddled = acts.addNoun("paddle in sea", "Paddle");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link twoYearsOlderThan = addLink("two years older than", ages);
		twoYearsOlderThan.f = (noun1, noun2) ->
		(noun1.num == 3 && noun2.num == 1) || (noun1.num == 4 && noun2.num == 1) ||
		(noun1.num == 5 && noun2.num == 2) ? Is : IsNot;

		// Facts.
		List<Noun> guys = getList(darren, kevin, wayne);
		List<Noun> gals = getList(angela, susan);

		addFact("1", smith1, Is, twoYearsOlderThan, smith2, "The Smiths' daughter (Smith 1) is two years older than her brother (Smith 2).");
		addFact("1", smith2, IsNot, With, sandCastle, "Her brother (Smith 2) did not build the sand-castle.");
		addFact("1", guys, IsNot, With, smith1);
		addFact("1", gals, IsNot, With, smith2);
		addFact("2", kevin, IsNot, With, getList(seven, hill));
		addFact("3", nine, Is, With, paddled);
		addFact("4", wayne, Is, With, eight1);
		addFact("4", angela, IsNot, With, eight2);
		addFact("5", robinson, Is, With, caughtCrabs);
		addFact("5", robinson, IsNot, With, six);
		addFact("6", susan, Is, With, dale);
		addFact("6", getList(angela, susan, darren), IsNot, With, buriedDad);

		// Solution.
		answer = new int[][]{{3, 4, 2, 0, 1}, {4, 1, 3, 0, 2}, {4, 3, 2, 0, 1}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=First Name, 2=Surname, 3=Age, 4=Activity
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name + "-year-old";
						break;
					case 4:
						if (link == With)
							msg = noun1.name + "'s activity " + verb.name + " to " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg = "The " + noun1.name + " child " + verb.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = "The " + noun1.name + " child's activity " + verb.name + " to " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4:
						if (link == With)
							msg = "The " + noun1.name + "-year-old's activity " + verb.name + " to " + noun2.name;
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
