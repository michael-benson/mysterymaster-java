package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class EllisIslandIdylls extends Puzzle {
	EllisIslandIdylls() {
		// Properties.
		myName = "EllisIslandIdylls";
		myTitle = "Ellis Island Idylls";

		// Nouns.
		NounType firstNames = addNounType("First Name");
		Noun mike = firstNames.addNoun("Mike");
		Noun pete = firstNames.addNoun("Pete");
		Noun ray = firstNames.addNoun("Ray");
		Noun steve = firstNames.addNoun("Steve");
		Noun tom = firstNames.addNoun("Tom");

		NounType lastNames = addNounType("Last Name");
		Noun adams = lastNames.addNoun("Adams");
		Noun block = lastNames.addNoun("Block");
		Noun curtis = lastNames.addNoun("Curtis");
		Noun decker = lastNames.addNoun("Decker");
		Noun evans = lastNames.addNoun("Evans");

		NounType auditRoles = addNounType("Auditioned");
		Noun giorgioA = auditRoles.addNoun("Giorgio-A");
		Noun ivanA = auditRoles.addNoun("Ivan-A");
		Noun johannA = auditRoles.addNoun("Johann-A");
		Noun olafA = auditRoles.addNoun("Olaf-A");
		Noun padraicA = auditRoles.addNoun("Padraic-A");

		NounType playsRoles = addNounType("Played");
		Noun giorgioP = playsRoles.addNoun("Giorgio-P");
		Noun ivanP = playsRoles.addNoun("Ivan-P");
		Noun johannP = playsRoles.addNoun("Johann-P");
		Noun olafP = playsRoles.addNoun("Olaf-P");
		Noun padraicP = playsRoles.addNoun("Padraic-P");

		// Facts.
		// Intro. No one is playing the role for which he auditioned.
		addFactsOneToOne("0", auditRoles, IsNot, With, playsRoles);

		// Clue 2. Tom is not the one playing Ivan.
		addFact("2", tom, IsNot, With, ivanP);

		// Clue 3. Four of the actors - Ray, Evans, the one who tried out for Padraic, and the one portraying Ivan - had been in the Junior Class play.
		addFact("3", getList(ray, evans, padraicA, ivanP), IsNot, With);

		// Clue 4. The boy who auditioned for the role of Johann - who is not Pete Adams - is not playing Olaf in the production.
		addFact("4", pete, Is, With, adams);
		addFact("4", pete, IsNot, With, johannA);
		addFact("4", johannA, IsNot, With, olafP);

		// Clue 5. In one scene, Decker and the one who auditioned as Giorgio woo the same girl.
		addFact("5", decker, IsNot, With, giorgioA);

		// Clue 6. Ray did not try out as Ivan and did not win the Padraic role.
		addFact("6", ray, IsNot, With, getList(ivanA, padraicP));

		// Clue 7. The boy who auditioned as Padraic - who is not Tom - is not the one playing Johann.
		addFact("7", padraicA, IsNot, With, getList(tom, johannP));

		// Clue 8. Steve and the Decker boy are also on the stage construction crew.
		addFact("8", steve, IsNot, With, decker);

		// Clue 9. The actor playing Padraic auditioned as Olaf; he is not Tom.
		addFact("9", padraicP, Is, With, olafA);
		addFact("9", padraicP, IsNot, With, tom);

		// Rules.
		Rule rule1 = addRule("1", "No two boys auditioned for and play the same two roles.");
		Rule rule2 = addRule("10", "The Block youth got the part for which Pete auditioned.", getList(block, pete));

		// Clue 1. No two boys auditioned for and play the same two roles.
		rule1.f = (mark) -> {
			int rs = 0;
			int n = auditRoles.nouns.size();

			// Violation if A = D and B = C (all one-based numbers) where
			// Boy Audit Plays
			//  1    A     C
			//  2    B     D
			for (int a = 1; a <= n - 1; a++) {
				Noun audit1 = auditRoles.nouns.get(a - 1);
				for (int b = a + 1; b <= n; b++) {
					Noun audit2 = auditRoles.nouns.get(b - 1);
					int c = Mark.getPairNounNum(audit1, playsRoles);
					int d = Mark.getPairNounNum(audit2, playsRoles);
					if (a == d && b == c) return -1;
				}
			}

			// Trigger: If mark is positive for auditioned and played roles, then the opposite is false.
			if (mark.verb == Is && mark.noun1.type == auditRoles && mark.noun2.type == playsRoles) {
				String msg = rule1.name;
				Noun audit = auditRoles.nouns.get(mark.noun2.num - 1);
				Noun plays = playsRoles.nouns.get(mark.noun1.num - 1);
				rs = solver.addMarkByRule(mark, rule1, 'a', audit, IsNot, plays, msg);
				if (rs != 0) return rs;
			}

			return rs;
		};

		// Clue 10. The Block youth got the part for which Pete auditioned.
		rule2.f = (mark) -> {
			int rs = 0;

			int a = Mark.getPairNounNum(block, playsRoles);
			int b = Mark.getPairNounNum(pete, auditRoles);

			// Violation if role Block plays is not the role Pete auditioned for.
			if (a > 0 && b > 0 && a != b) return -1;

			// Trigger: If mark is negative for Pete and auditioned role, then Block did not play the role.
			if (mark.verb == IsNot && mark.noun1 == pete && mark.noun2.type == auditRoles) {
				String msg = rule2.name;
				Noun plays = playsRoles.nouns.get(mark.noun2.num - 1);
				rs = solver.addMarkByRule(mark, rule1, 'a', block, IsNot, plays, msg);
				if (rs != 0) return rs;
			}

			// Trigger: If mark is negative for Block and played role, then Pete did not audition the role.
			if (mark.verb == IsNot && mark.noun1 == block && mark.noun2.type == playsRoles) {
				String msg = rule2.name;
				Noun audit = auditRoles.nouns.get(mark.noun2.num - 1);
				rs = solver.addMarkByRule(mark, rule1, 'b', pete, IsNot, audit, msg);
				if (rs != 0) return rs;
			}

			return rs;
		};

		// Solution.
		answer = new int[][]{{3, 0, 2, 1, 4}, {2, 4, 0, 3, 1}, {1, 0, 2, 4, 3}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=First Name, 2=Last Name, 3=Auditioned, 4=Played
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						break;
					case 3:
						msg = noun1.name + " " + (verb == Is ? "did" : "did not") + " audition for " + noun2.name;
						break;
					case 4:
						msg = noun1.name + " " + verb.name + " playing " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						msg = noun1.name + " " + (verb == Is ? "did" : "did not") + " audition for " + noun2.name;
						break;
					case 4:
						msg = noun1.name + " " + verb.name + " playing " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						msg = "The boy who auditioned for " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 2: break;
					case 3: break;
					case 4:
						msg = "The boy who auditioned for " + noun1.name + " " + verb.name +  " playing " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						msg = "The boy playing " + noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 2: break;
					case 3:
						msg = "The boy playing " + noun1.name + " " + (verb == Is ? "did" : "did not") + " audition for " + noun2.name;
						break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
