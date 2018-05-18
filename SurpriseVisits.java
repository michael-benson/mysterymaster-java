package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 *
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class SurpriseVisits extends Puzzle {

	SurpriseVisits() {
		// Properties.
		myName = "SurpriseVisits";
		myTitle = "Surprise Visits";

		// Nouns.
		NounType firstNames = addNounType("First Name");
		Noun paul = firstNames.addNoun("Paul");
		Noun peter = firstNames.addNoun("Peter");
		Noun reuben = firstNames.addNoun("Reuben");

		NounType lastNames = addNounType("Last Name");
		Noun abbott = lastNames.addNoun("Abbott");
		Noun mcNeill = lastNames.addNoun("McNeill");
		Noun whistler = lastNames.addNoun("Whistler");

		NounType homes = addNounType("Home City");
		Noun losAngeles = homes.addNoun("Los Angeles", "LA");
		Noun phoenix = homes.addNoun("Phoenix");
		Noun reno = homes.addNoun("Reno");

		NounType visits = addNounType("Visited City");
		Noun losAngelesV = visits.addNoun("Los Angeles-V", "LA-V");
		Noun phoenixV = visits.addNoun("Phoenix-V");
		Noun renoV = visits.addNoun("Reno-V");

		// Facts.
		// Clue 1. No two visited the same city.
		addFactsOneToOne("1", homes, IsNot, With, visits);

		// Clue 2. Mr. Abbott tried to visit Mr. McNeill, who tried to visit Peter.
		addFact("2", getList(abbott, mcNeill, peter), IsNot, With);

		// Clue 3. Mr. Whistler traveled to Phoenix.
		addFact("3", whistler, Is, With, phoenixV);

		// Clue 4. The man who lives in Reno tried to visit Reuben.
		addFact("4", reuben, IsNot, With, getList(reno, renoV));

		// Rules.
		Rule rule1 = addRule("2", "Mr. Abbott tried to visit Mr. McNeill.");
		rule1.f = (mark) -> didVisit(mark, rule1, abbott, mcNeill, visits, homes);

		Rule rule2 = addRule("2", "Mr. McNeill tried to visit Peter.");
		rule2.f = (mark) -> didVisit(mark, rule2, mcNeill, peter, visits, homes);

		Rule rule3 = addRule("4", "The man in Reno tried to visit Reuben.");
		rule3.f = (mark) -> didVisit(mark, rule3, reno, reuben, visits, homes);

		// Solution.
		answer = new int[][]{{1, 2, 0}, {0, 2, 1}, {2, 1, 0}};
	}

	public int didVisit(Mark mark, Rule rule, Noun nounA, Noun nounB, NounType vcities, NounType hcities) {
		int rs = 0;

		Noun vcity = Mark.getPairNoun(nounA, vcities);
		Noun hcity = Mark.getPairNoun(nounB, hcities);
		if (vcity == null && hcity == null) {
			return rs;
		}

		// Violation if the city nounA visited is not the home city of nounB.
		if (vcity != null && hcity != null) {
			return (vcity.num != hcity.num) ? -1 : 0;
		}

		// Triggers.
		// The city nounA visited is the home city of nounB.
		if (vcity != null) {
			Noun home = hcities.nouns.get(vcity.num - 1);
			String msg = "If " + nounA.name + " visited " + vcity.name + " then " + nounB + "'s home is " + home.name + ".";
			rs = solver.addMarkByRule(mark, rule, 'a', nounB, Is, home, msg);
			if (rs != 0) {
				return rs;
			}
		}

		// The home city of nounB is the city nounA visited.
		if (hcity != null) {
			Noun visit = vcities.nouns.get(hcity.num - 1);
			String msg = "If " + nounB.name + "'s home is " + hcity.name + " then " + nounA + " visited " + visit.name + ".";
			rs = solver.addMarkByRule(mark, rule, 'b', nounA, Is, visit, msg);
			if (rs != 0) {
				return rs;
			}
		}

		return rs;
	}

	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=First Name, 2=Last Name, 3=Home City, 4=Visited City
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1:
						break;
					case 2:
						break;
					case 3:
						msg = noun1.name + " " + (verb == Is ? "lives" : "does not live") + " in " + noun2.name;
						break;
					case 4:
						msg = noun1.name + " " + (verb == Is ? "visited" : "did not visit") + " " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						msg = "Mr. " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						msg = "Mr. " + noun1.name + " " + (verb == Is ? "visited" : "did not visit") + " " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						msg = "The man in " + noun1.name + " " + (verb == Is ? "visited" : "did not visit") + " " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
				}
				break;
		}

		return msg + ".";
	}
}
