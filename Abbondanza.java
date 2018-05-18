package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class Abbondanza extends Puzzle {
	Abbondanza() {
		// Properties.
		myName = "Abbondanza";
		myTitle = "Abbondanza!";

		// Nouns.
		NounType firstNames = addNounType("First Name");
		Noun andrea  = firstNames.addNoun("Andrea");
		Noun beth    = firstNames.addNoun("Beth");
		Noun candice = firstNames.addNoun("Candice");
		Noun doug    = firstNames.addNoun("Doug");
		Noun earl    = firstNames.addNoun("Earl");
		Noun frank   = firstNames.addNoun("Frank");

		NounType lastNames = addNounType("Last Name");
		Noun gronski   = lastNames.addNoun("Gronski");
		Noun moulin    = lastNames.addNoun("Moulin");
		Noun tobin     = lastNames.addNoun("Tobin");
		Noun vasquez   = lastNames.addNoun("Vasquez");
		Noun wasserman = lastNames.addNoun("Wasserman");
		Noun zapple    = lastNames.addNoun("Zapple");

		NounType pastas = addNounType("Pasta");
		Noun fettuccine = pastas.addNoun("Fettuccine");
		Noun linguine   = pastas.addNoun("Linguine");
		Noun rigatoni   = pastas.addNoun("Rigatoni");
		Noun vermicelli = pastas.addNoun("Vermicelli");
		Noun linguine2  = pastas.addNoun("Linguine2");
		Noun pasta6     = pastas.addNoun("Pasta6");

		NounType sauces = addNounType("Sauce");
		Noun alfredo   = sauces.addNoun("Alfredo");
		Noun carbonara = sauces.addNoun("Carbonara");
		Noun marinara  = sauces.addNoun("Marinara");
		Noun pesto     = sauces.addNoun("Pesto");
		Noun sauce5    = sauces.addNoun("Sauce5");
		Noun sauce6    = sauces.addNoun("Sauce6");

		// Clues for facts and rules.
		List<Noun> gals = getList(andrea, beth, candice);
		List<Noun> guys = getList(doug, earl, frank);

		int[] genders = new int[]{0, 0, 0, 1, 1, 1}; // Gender of first names where 0=female, 1=male.
		// Clue 01. Each kind of pasta and each kind of sauce was ordered by at least one of the six diners at the table.
		// Based on clues 1, 4, and 6, force Frank and Earl to be with either linguine2 or pasta6.
		for (Noun noun : firstNames.nouns) {
			if (noun.name.equals("Frank") || noun.name.equals("Earl")) continue;
			addFact("1", noun, IsNot, With, linguine2, noun.name + " did not order " + linguine2.name + ".");
			addFact("1", noun, IsNot, With, pasta6, noun.name + " did not order " + pasta6.name + ".");
		}

		// Clue 02. Doug isn't Gronski or Zapple.
		addFact("2", doug, IsNot, With, gronski, "Doug is not Gronski.");
		addFact("2", doug, IsNot, With, zapple, "Doug is not Zapple.");

		// Clue 03. No one ordered linquine pesto. Rule 1 must check pasta6, sauce5, and sauce6
		addFact("3", linguine, IsNot, With, pesto, "No one ordered linquine pesto.");
		addFact("3", linguine2, IsNot, With, pesto, "No one ordered linquine pesto.");

		// Clue 04. Frank's pasta was the same as at least one other person's, but not the same as Vasquez's.
		addFact("4", frank, IsNot, With, vasquez, "Frank is not Vasquez.");

		// Clue 05. Tobin wasn't the one who ordered fettuccine alfredo.
		addFact("5", tobin, IsNot, With, fettuccine, "Tobin did not order fettuccine.");
		addFact("5", fettuccine, Is, With, alfredo, "Fettuccine was ordered with alfredo.");

		// Clue 06. Earl ordered the same pasta as at least one woman.

		// Clue 07. Beth isn't Gronski.
		addFact("7", beth, IsNot, With, gronski, "Beth is not Gronski.");

		// Clue 08. The only two people whose sauce orders were unique at the table are of the same sex.
		// This means one woman and one man ordered the same sauce.

		// The following assumes that the men did not have unique sauce orders.
		for (Noun noun : firstNames.nouns) {
			if (genders[noun.num - 1] > 0) continue;
			addFact("8", noun, IsNot, With, sauce5, noun.name + " did not order " + sauce5.name + ".");
			addFact("8", noun, IsNot, With, sauce6, noun.name + " did not order " + sauce6.name + ".");
		}

		// Clue 09. There were exactly as many orders of vermicelli as there were of alfredo sauce.

		// Clue 10. Earl and Frank ordered (not necessarily respectively) marinara and pesto.
		addFact("10", earl, IsNot, With, alfredo, "Earl did not order alfredo.");
		addFact("10", earl, IsNot, With, carbonara, "Earl did not order carbonara.");
		addFact("10", frank, IsNot, With, alfredo, "Frank did not order alfredo.");
		addFact("10", frank, IsNot, With, carbonara, "Frank did not order carbonara.");

		// Clue 11. Vasquez ordered either rigatoni or carbonara, or both.

		// Clue 12. Andrea (who isn't Tobin) and Moulin both ordered linguine.
		addFact("12", andrea, IsNot, With, tobin, "Andrea is not Tobin.");
		addFact("12", andrea, IsNot, With, moulin, "Andrea is not Moulin.");
		addFact("12", andrea, Is, With, linguine, "Andrea ordered linguine.");
		addFact("12", moulin, Is, With, linguine2, "Moulin ordered linguine.");

		// Clue 13. Wasserman and Zapple (who are of the same sex) ordered the same kind of sauce, one with rigatoni and the other with vermacelli (not necessarily respectively).
		// sauce5 is reserved for Zapple, and sauce6 is for the 3rd person of the sex with two unique sauces.
		addFact("13", wasserman, IsNot, With, fettuccine, "Wasserman did not order fettuccine.");
		addFact("13", wasserman, IsNot, With, linguine, "Wasserman did not order linguine.");
		addFact("13", wasserman, IsNot, With, linguine2, "Wasserman did not order linguine2.");
		addFact("13", wasserman, IsNot, With, pasta6, "Wasserman did not order pasta6.");
		addFact("13", zapple, IsNot, With, fettuccine, "Zapple did not order fettuccine.");
		addFact("13", zapple, IsNot, With, linguine, "Zapple did not order linguine.");
		addFact("13", zapple, IsNot, With, linguine2, "Zapple did not order linguine2.");

		// Zapple's sauce is sauce5, and Waserman's sauce is not sauce6.
		addFact("13", zapple, Is, With, sauce5, "Zapple did not order sauce5.");
		addFact("13", wasserman, IsNot, With, sauce6, "Wasserman did not order sauce6.");

		// ----------------------------------------------------------------------------------------------

		// Clue 04. Frank's pasta was the same as at least one other person's, but not the same as Vasquez's.
		Rule rule01 = addRule("4", "Frank's pasta was the same as at least one other person's, but not the same as Vasquez's.");
		rule01.f = (mark) -> {
			int rs = 0;
			Rule rule = rule01;

			// Trigger. If Vasquez had linguine, then Frank's pasta is not linguine.
			Noun pasta = Mark.getPairNoun(vasquez, pastas);
			if (pasta == linguine) {
				String msg = "The pasta for Frank and Vasquez are different.";
				rs = solver.addMarkByRule(mark, rule, 'a', frank, IsNot, linguine2, msg);
				if (rs != 0) return rs;
			}
			if (pasta == linguine2) {
				String msg = "The pasta for Frank and Vasquez are different.";
				rs = solver.addMarkByRule(mark, rule, 'a', frank, IsNot, linguine, msg);
				if (rs != 0) return rs;
			}

		  return rs;
		};


		// Clue 03. No one ordered linguine pesto.
		// Clue 10. Earl and Frank ordered (not necessarily respectively) marinara and pesto.
		// 
		Rule rule02 = addRule("3,10", "If Earl or Frank have linguine with sauce5/sauce6, sauce must be marinara and other person has pesto.");
		rule02.f = (mark) -> {
			int rs = 0;
			Rule rule = rule02;

			List<Noun> fnames = getList(earl, frank);
			for (Noun fname1 : fnames) {
				Noun pasta1 = Mark.getPairNoun(fname1, pastas);
				if (pasta1 != linguine && pasta1 != linguine2) continue;
				Noun sauce1 = Mark.getPairNoun(fname1, sauces);
				if (sauce1 != sauce5 && sauce1 != sauce6) continue;
				if (!sauce1.name.equals(marinara.name)) {
					mark.addPlacer(sauce1, marinara.name);
				}

				// The other person must have pesto.
				Noun fname2 = fname1 == earl ? frank : earl;
				Noun sauce2 = Mark.getPairNoun(fname2, sauces);
				if (sauce2 != sauce5 && sauce2 != sauce6) continue;
				if (!sauce2.name.equals(pesto.name)) {
					mark.addPlacer(sauce2, pesto.name);
				}
			}
			return rs;
		};

		// Clue 11. Vasquez ordered either rigatoni or carbonara, or both.
		Rule rule03 = addRule("11", "Vasquez ordered either rigatoni or carbonara, or both.");
		rule03.f = (mark) -> {
			int rs = 0;
			Rule rule = rule03;
			if (mark.noun1 != vasquez) return rs;

			// If Vasquez's pasta is not rigatoni, then Vasquez's sauce must be carbonara.
			if ((mark.verb == IsNot && mark.noun2 == rigatoni) || (mark.verb == Is && mark.noun2.type == pastas && mark.noun2 != rigatoni)) {
				String msg = "The sauce for Vasquez must be carbonara.";
				rs = solver.addMarkByRule(mark, rule, 'a', vasquez, Is, carbonara, msg);
				if (rs != 0) return rs;
			}

			// If Vasquez's sauce is not carbonara, then Vasquez's pasta must be rigatoni.
			if ((mark.verb == IsNot && mark.noun2 == carbonara) || (mark.verb == Is && mark.noun2.type == sauces && mark.noun2 != carbonara)) {
				String msg = "The pasta for Vasquez must be rigatoni.";
				rs = solver.addMarkByRule(mark, rule, 'b', vasquez, Is, rigatoni, msg);
				if (rs != 0) return rs;
			}
			return rs;
		};

		// Clue 13. Wasserman and Zapple (who are of the same sex) ordered the same kind of sauce, one with rigatoni and the other with vermacelli (not necessarily respectively).
		Rule rule04 = addRule("13", "Wasserman and Zapple are of the same sex.");
		List<List<Noun>> array2D = new ArrayList<List<Noun>>() {{
			add(gals);
			add(guys);
		}};
		rule04.f = smartRule.getMatchOneList(rule04, getList(wasserman, zapple), array2D);

		// Clue 13. Wasserman and Zapple (who are of the same sex) ordered the same kind of sauce, one with rigatoni and the other with vermacelli (not necessarily respectively).
		Rule rule05 = addRule("13", "If the value of Zapple's sauce5 is known, then assign Wasserman's sauce.");
		rule05.f = (mark) -> {
			int rs = 0;
			Rule rule = rule05;

			if (!sauce5.name.equals("Sauce5")) {
				for (Noun sauce : sauces.nouns) {
					// Only look at the first 4 sauces.
					if (sauce == sauce5) break;
					if (!sauce.name.equals(sauce5.name)) continue;
					String msg = "Wasserman and Zapple ordered the " + sauce.name + " sauce.";
					rs = solver.addMarkByRule(mark, rule, 'a', wasserman, Is, sauce, msg);
					if (rs != 0) return rs;
				}
			}

			return rs;
		};

		// Clue 08. The only two people whose sauce orders were unique at the table are of the same sex.
		// Clue 13. Wasserman and Zapple (who are of the same sex) ordered the same kind of sauce.
		// If Wasserman and Zapple are men, then (1) the third man and a woman ordered the same sauce,
		// and (2) the sauce orders for the other two woman are unique.
		Rule rule06 = addRule("8,13", "Reconcile sauce orders.");
		rule06.f = (mark) -> {
			int rs = 0;

			Noun fnameA = Mark.getPairNoun(wasserman, firstNames); // Doug
			Noun fnameB = Mark.getPairNoun(zapple, firstNames);    // Frank
			if (fnameA != null && fnameB != null) {
				int genderA = genders[fnameA.num - 1];
				int genderB = genders[fnameB.num - 1];
				if (genderA == genderB) {
					List<Noun> list = genderA > 0 ? guys : gals;
					Noun fnameC = null;
					for (Noun item : list) {
						if (item != fnameA && item != fnameB) {
							fnameC = item;
							break;
						}
					}
					//print("fnameC=" + fnameC);

					// Find a person of the opposite sex that this person could share a sauce with.
					Noun fnameD = null;
					list = genderA > 0 ? gals: guys;
					for (Noun fnameX : list) {
						Noun sauceX = solver.getCommonNoun(fnameC, fnameX, sauces);
						if (sauceX == null) continue;
						if (fnameD != null) continue;
						fnameD = fnameX;
						//print(fnameC + " shares the " + sauceX + " sauce with " + fnameD);
					}
				}
			}

			return rs;
		};


		// Clue 09. There were exactly as many orders of vermicelli as there were of Alfredo sauce.
		// Clue 13. Wasserman and Zapple (who are of the same sex) ordered the same kind of sauce, one with rigatoni and the other with vermicelli (not necessarily respectively).
		// If the values for both sauce5 and sauce6 are known, count the number Of Alfredo sauces.
		// If there is only one, there is only one vermicelli, so pasta6 cannot be vermicelli.
		// Note: Either Earl or Frank has pasta6.
		// If Wasserman or Zapple have pasta6, then pasta6 must be rigatoni, and the pasta for the other person has vermicelli.
		Rule rule07 = addRule("9,13", "Determine the pasta dishes for Wasserman and Zapple.");
		rule07.f = (mark) -> {
			int rs = 0;
			Rule rule = rule07;

			if (!sauce5.name.equals("Sauce5") && !sauce6.name.equals("Sauce6")) {
				int count = 1;
				if (sauce5.name.equals(alfredo.name)) ++count;
				if (sauce6.name.equals(alfredo.name)) ++count;
				if (count == 1) {
					Noun lnameA = Mark.getPairNoun(pasta6, lastNames);
					if (lnameA == wasserman || lnameA == zapple) {
						mark.addPlacer(pasta6, rigatoni.name);
						Noun lnameB = lnameA == wasserman ? zapple : wasserman;
						String msg = "If Wasserman or Zapple has pasta6, then the other person had vermicelli.";
						rs = solver.addMarkByRule(mark, rule, 'a', lnameB, Is, vermicelli, msg);
						if (rs != 0) return rs;
					}
				}
			}

			return rs;
		};


		// Clue 10. Earl and Frank ordered (not necessarily respectively) marinara and pesto.
		// Clue 13. Wasserman and Zapple (who are of the same sex) ordered the same kind of sauce, one with rigatoni and the other with vermacelli (not necessarily respectively).
		Rule rule08 = addRule("10,13", "Earl and Frank cannot both be Wasserman and Zapple (in some order).");
		rule08.f = (mark) -> {
			int rs = 0;
			Rule rule = rule08;

			List<Noun> nounsA = getList(earl, frank);
			List<Noun> nounsB = getList(wasserman, zapple);

			NounType typeA = earl.type;
			NounType typeB = wasserman.type;

			// If nounA can only be with a noun in nounsB, then the other nouns in nounsA cannot be with the nouns in nounsB
			for (Noun nounA : nounsA) {
				boolean ok = false;
				for (Noun nounB : typeB.nouns) {
					if (!nounsB.contains(nounB) && solver.canBeWith(nounA, nounB)) {
						ok = true;
						break;
					}
				}
				if (ok) continue;
				for (Noun nounX : nounsA) {
					if (nounX == nounA) continue;
					for (Noun nounB : nounsB) {
						String msg = "Earl and Frank cannot both be Wasserman and Zapple";
						rs = solver.addMarkByRule(mark, rule, ' ', nounX, IsNot, nounB, msg);
						if (rs != 0) return rs;
					}
				}
			}

			// If nounB can only be with a noun in nounsA, then the other nouns in nounsB cannot be with the nouns in nounsA
			for (Noun nounB : nounsB) {
				boolean ok = false;
				for (Noun nounA : typeA.nouns) {
					if (!nounsA.contains(nounA) && solver.canBeWith(nounB, nounA)) {
						ok = true;
						break;
					}
				}
				if (ok) continue;
				for (Noun nounX : nounsB) {
					if (nounX == nounB) continue;
					for (Noun nounA : nounsA) {
						String msg = "Earl and Frank cannot both be Wasserman and Zapple";
						rs = solver.addMarkByRule(mark, rule, ' ', nounX, IsNot, nounA, msg);
						if (rs != 0) return rs;
					}
				}
			}

			return rs;
		};

		// Solution.
		// Andrea  Vasquez   linguine   carbonara
		// Beth    Tobin     rigatoni   marinara
		// Candice Gronski   fettuccine Alfredo
		// Doug    Wasserman vermicelli pesto
		// Earl    Moulin    linguine   marinara
		// Frank   Zapple    rigatoni   pesto
		answer = null;
	}
}
