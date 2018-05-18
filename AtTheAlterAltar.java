package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class AtTheAlterAltar extends Puzzle {
	AtTheAlterAltar() {
		// Properties.
		myName = "AtTheAlterAltar";
		myTitle = "At The Alter Altar";

		// Nouns.
		NounType groomFirsts = addNounType("Groom First");
		Noun benny = groomFirsts.addNoun("Benny");
		Noun clyde = groomFirsts.addNoun("Clyde");
		Noun dale = groomFirsts.addNoun("Dale");
		Noun joel = groomFirsts.addNoun("Joel");
		Noun reuben = groomFirsts.addNoun("Reuben");

		NounType groomLasts = addNounType("Groom Last");
		Noun barley = groomLasts.addNoun("Barley");
		Noun darley = groomLasts.addNoun("Darley");
		Noun farley = groomLasts.addNoun("Farley");
		Noun harley = groomLasts.addNoun("Harley");
		Noun marley = groomLasts.addNoun("Marley");

		NounType brideFirsts = addNounType("Bride First");
		Noun clara = brideFirsts.addNoun("Clara");
		Noun dorinda = brideFirsts.addNoun("Dorinda");
		Noun marilyn = brideFirsts.addNoun("Marilyn");
		Noun primrose = brideFirsts.addNoun("Primrose");
		Noun sandra = brideFirsts.addNoun("Sandra");

		NounType brideLasts = addNounType("Bride Last");
		Noun dicks = brideLasts.addNoun("Dicks");
		Noun hicks = brideLasts.addNoun("Hicks");
		Noun nix = brideLasts.addNoun("Nix");
		Noun rix = brideLasts.addNoun("Rix");
		Noun wicks = brideLasts.addNoun("Wicks");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Facts.
		addFact("1", getList(marilyn, primrose, sandra), IsNot, With, harley);
		addFact("2", dale, IsNot, With, getList(barley, harley, marley));
		addFact("2", primrose, IsNot, With, getList(hicks, nix, rix));
		addFact("2", reuben, Is, With, marley);
		addFact("3", sandra, IsNot, With, getList(dicks, darley));
		addFact("3", dicks, IsNot, With, darley);
		addFact("4", marilyn, IsNot, With, getList(dicks, hicks, nix));
		addFact("4", marilyn, IsNot, With, getList(joel, darley));
		addFact("4", joel, IsNot, With, darley);
		addFact("5", getList(benny, clyde), IsNot, With, barley);
		addFact("5", barley, IsNot, With, getList(clara, dorinda, sandra));
		addFact("6", benny, IsNot, With, getList(nix, rix, wicks));
		addFact("7", nix, IsNot, With, getList(clyde, dale));
		addFact("8", dorinda, IsNot, With, getList(dale, clyde));
		addFact("8", dorinda, IsNot, With, getList(hicks, nix, wicks));
		
		// Rules.
		Rule rule1 = addRule("6", "Clara is either Miss Rix or Dale's bride.");
		rule1.f = smartRule.getMatchAtLeastOne(rule1, clara, getList(rix, dale));

		// Solution.
		answer = new int[][]{{3, 2, 1, 0, 4}, {1, 2, 0, 3, 4}, {0, 3, 1, 4, 2}};
	}
	
	@Override
	public String 	sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Groom First, 2=Groom Last, 3=Bride First, 4=Bride Last;
		switch (noun1.type.num) {
			case 1:
				msg = noun1.name + " ";
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg += verb.name + " Mr. " + noun2.name;
						break;
					case 3: break;
					case 4:
						msg += (verb == Is ? "did" : "did not") + " marry Miss " + noun2.name;
						break;
				}
				break;
			case 2:
				msg = "Mr. " + noun1.name + " ";
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						msg += (verb == Is ? "did" : "did not") + " marry " + noun2.name;
						break;
					case 4: break;
				}
				break;
			case 3:
				msg = noun1.name + " ";
				switch (noun2.type.num) {
					case 1:
						msg += (verb == Is ? "did" : "did not") + " marry " + noun2.name;
						break;
					case 2:
						msg += verb.name + " Mrs. " + noun2.name;
						break;
					case 3: break;
					case 4:
						msg += verb.name + " Miss " + noun2.name;
						break;
				}
				break;
			case 4:
				msg = "Miss " + noun1.name + " ";
				switch (noun2.type.num) {
					case 1:
						msg += (verb == Is ? "did" : "did not") + " marry " + noun2.name;
						break;
					case 2:
						msg += verb.name + " Mrs. " + noun2.name;
						break;
					case 3: break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
