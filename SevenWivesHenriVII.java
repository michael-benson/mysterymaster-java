package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class SevenWivesHenriVII extends Puzzle {
	SevenWivesHenriVII() {
	// Properties.
	myName = "SevenWivesHenriVII";
	myTitle = "The Seven Wives of Henri VII";

	// Nouns.
	NounType slots = addNounType("Order");
	Noun slot1 = slots.addNoun("1st");
	Noun slot2 = slots.addNoun("2nd");
	Noun slot3 = slots.addNoun("3rd");
	Noun slot4 = slots.addNoun("4th");
	Noun slot5 = slots.addNoun("5th");
	Noun slot6 = slots.addNoun("6th");
	Noun slot7 = slots.addNoun("7th");

	NounType names = addNounType("Name");
	Noun angelica = names.addNoun("Angelica");
	Noun carolina = names.addNoun("Carolina");
	Noun dominica = names.addNoun("Dominica");
	Noun isabela = names.addNoun("Isabela");
	Noun maria = names.addNoun("Maria");
	Noun rosanna = names.addNoun("Rosanna");
	Noun sophia = names.addNoun("Sophia");

	NounType countries = addNounType("Country");
	Noun austria = countries.addNoun("Austria");
	Noun france = countries.addNoun("France");
	Noun greece = countries.addNoun("Greece");
	Noun italy = countries.addNoun("Italy");
	Noun portugal = countries.addNoun("Portugal");
	Noun russia = countries.addNoun("Russia");
	Noun spain = countries.addNoun("Spain");

	// Verbs.
	IsNot.name = "was not";
	Is.name = "was";

	// Links.
	Link oneBefore = addLink("immediately before", slots);
	oneBefore.f = SmartLink.getIsLessBy(1);

	Link moreThanOneAway = addLink("more than one away from", slots);
	moreThanOneAway.f = SmartLink.getIsOutsideOf(1);

	// Facts.
	// Clue 1. The one from France was not Rosanna.
	addFact("1", france, IsNot, With, rosanna);

	// Clue 2. The Russian queen immediately preceded Isabela, who was not from Portugal and who did not survive Henri VII.
	addFact("2", russia, Is, oneBefore, isabela);
	addFact("2", isabela, IsNot, With, getList(portugal, slot7));

	// Clue 3. Dominica was queen immediately before the one from France.
	addFact("3", dominica, Is, oneBefore, france);

	// Clue 4. Both Angelica and the bride from Spain were exiled when Henri decided to take new brides.
	addFact("4", getList(angelica, spain, slot7), IsNot, With);

	// Clue 5. Maria was queen immediately following the lady from Portugal.
	addFact("5", portugal, Is, oneBefore, maria);

	// Clue 6. Sophia immediately preceded the princess from Italy as queen.
	addFact("6", sophia, Is, oneBefore, italy);

	// Clue 7. The bride from Austria immediately preceded Rosanna as queen.
	addFact("7", austria, Is, oneBefore, rosanna);

	// Clue 8. These three queens died in Plutonia while still married to Henri VII: Maria and the queens from France and Russia; no two of them were queens in consecutive order.
	List<Noun> list8 = getList(maria, france, russia);
	addFact("8", list8, IsNot, With);
	addFact("8", list8, IsNot, With, slot7);
	addFact("8", list8, Is, moreThanOneAway);

	addFact("4,8", angelica, IsNot, With, france, "Angelica was exiled, but the French one died as queen.");
	addFact("4,8", angelica, IsNot, With, russia, "Angelica was exiled, but the Russian one died as queen.");
	addFact("4,8", spain, IsNot, With, maria, "The Spanish one was exiled as queen, but Maria died.");

	// Solution.
	answer = new int[][]{{6, 3, 2, 1, 0, 4, 5}, {5, 3, 6, 1, 4, 0, 2}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Order, 2=Name, 3=Country
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name + " queen.";
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + verb.name + " from " + noun2.name;
						else
							msg = noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name + " queen.";
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The queen from " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = "The queen from " + noun1.name + " " + verb.name + " " + noun2.name;
						else
							msg = "The queen from " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3:
						msg = "The queen from " + noun1.name + " " + verb.name + " " + link.name + " the queen from " + noun2.name;
						break;
				}
				break;
		}

		return msg + ".";
	}
}
