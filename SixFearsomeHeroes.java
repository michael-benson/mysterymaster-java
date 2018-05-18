package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class SixFearsomeHeroes extends Puzzle {
	private final NounType members, fizzbinRanks, chessRanks, fears, heroes;

	SixFearsomeHeroes() {
		// Properties.
		myName = "SixFearsomeHeroes";
		myTitle = "Six Fearsome Heroes";

		// Nouns.
		members = addNounType("Member");
		Noun data = members.addNoun("Data");
		Noun geordi = members.addNoun("Geordi");
		Noun picard = members.addNoun("Picard");
		Noun riker = members.addNoun("Riker");
		Noun troi = members.addNoun("Troi");
		Noun worf = members.addNoun("Worf");

		fizzbinRanks = addNounType("Fizzbin");
		Noun fizzbin1 = fizzbinRanks.addNoun("1F");
		Noun fizzbin2 = fizzbinRanks.addNoun("2F");
		Noun fizzbin3 = fizzbinRanks.addNoun("3F");
		Noun fizzbin4 = fizzbinRanks.addNoun("4F");
		Noun fizzbin5 = fizzbinRanks.addNoun("5F");
		Noun fizzbin6 = fizzbinRanks.addNoun("6F");

		chessRanks = addNounType("3D Chess");
		Noun chess1 = chessRanks.addNoun("1C");
		Noun chess2 = chessRanks.addNoun("2C");
		Noun chess3 = chessRanks.addNoun("3C");
		Noun chess4 = chessRanks.addNoun("4C");
		Noun chess5 = chessRanks.addNoun("5C");
		Noun chess6 = chessRanks.addNoun("6C");

		fears = addNounType("Fear");
		Noun dataF = fears.addNoun("Data-F");
		Noun geordiF = fears.addNoun("Geordi-F");
		Noun picardF = fears.addNoun("Picard-F");
		Noun rikerF = fears.addNoun("Riker-F");
		Noun troiF = fears.addNoun("Troi-F");
		Noun worfF = fears.addNoun("Worf-F");

		heroes = addNounType("Hero");
		Noun dataH = heroes.addNoun("Data-H");
		Noun geordiH = heroes.addNoun("Geordi-H");
		Noun picardH = heroes.addNoun("Picard-H");
		Noun rikerH = heroes.addNoun("Riker-H");
		Noun troiH = heroes.addNoun("Troi-H");
		Noun worfH = heroes.addNoun("Worf-H");

		// Links. Important: 1 is the lowest rank and 6 is the highest rank.
		Link twoHigherF = addLink("two positions higher than", fizzbinRanks);
		twoHigherF.f = SmartLink.getIsMoreBy(2);

		Link fourHigherF = addLink("four positions higher than", fizzbinRanks);
		fourHigherF.f = SmartLink.getIsMoreBy(4);

		Link threeXHigherC = addLink("three times higher than", chessRanks);
		threeXHigherC.f = SmartLink.getHasRatio(1, 3);

		Link twoHigherC = addLink("two positions higher than", chessRanks);
		twoHigherC.f = SmartLink.getIsMoreBy(2);

		Link higherC = addLink("higher than", chessRanks);
		higherC.f = SmartLink.getIsMoreThan(0);

		// Facts.
		// Introduction. Nobody fears him/herself.
		// Introduction. Nobody counts him/herself as their hero.
		// Introduction. Nobody fears his/her own hero.
		addFactsOneToOne("0", members, IsNot, With, fears);
		addFactsOneToOne("0", members, IsNot, With, heroes);
		addFactsOneToOne("0", fears, IsNot, With, heroes);

		// Clue 1. Geordi ranks 2nd at three-dimensional chess.
		addFact("1", geordi, Is, With, chess2);

		// Clue 2. Picard ranks two positions behind Troi at Fizzbin.
		addFact("2", troi, Is, twoHigherF, picard);

		// Clue 3. Troi is feared by the person Geordi fears.
		addFact("3", geordi, IsNot, With, troiF);

		// Clue 4. Worf's hero ranks 3 times lower at three-dimensional chess than the crew member who is best at Fizzbin.
		// Therefore, The best at Fizzbin (6F) is either 6C and Worf's hero is 3C, or 3C and Worf's hero is 1C.
		addFact("4", fizzbin6, IsNot, With, getList(chess1, chess2, chess4, chess5));

		// Clue 5. Picard's hero fears Geordi.
		addFact("5", picard, IsNot, With, getList(geordiH, geordiF));

		// Clue 6. Data's hero is not Geordi.
		addFact("6", data, IsNot, With, geordiH);

		// Clue 7. Data is the hero of Riker's hero.
		addFact("7", riker, IsNot, With, dataH);

		// Clue 8. The person who is worst at Fizzbin is better than Troi at three-dimensional chess.
		addFact("8", fizzbin1, Is, higherC, troi);

		// Clue 9. The person ranked 3rd at three-dimensional chess is ranked 4 positions higher than Data at Fizzbin.
		addFact("9", chess3, Is, fourHigherF, data);

		// Clue 10. Riker is feared by the person Picard fears and is the hero of Worf's hero.
		addFact("10", picard, IsNot, With, rikerF);
		addFact("10", worf, IsNot, With, rikerH);

		// Clue 11. Riker is ranked 2 positions lower at three-dimensional chess than the crew member ranked 2nd at Fizzbin.
		addFact("11", fizzbin2, Is, twoHigherC, riker);

		// Rules.
		Rule rule1 = addRule("3", "Troi is feared by the person Geordi fears.");
		Rule rule2 = addRule("4", "The one who is best at Fizzbin ranks 3 times higher at 3D chess than Worf's hero.");
		Rule rule3 = addRule("5", "Picard's hero fears Geordi.");
		Rule rule4 = addRule("7", "Data is the hero of Riker's hero.");
		Rule rule5 = addRule("10", "Riker is feared by the person Picard fears and is the hero of Worf's hero.");
		
		// Clue 3. Troi is feared by the person Geordi fears.
		// (a) Member X fears Troi, so member X is not Troi.
		// (b) Geordi fears member X, so member X is not Geordi.
		// Facts: Geordi does not fear Troi.
		rule1.f = (mark) -> {
			int rs = 0;

			// Geordi fears member A, where A cannot be Troi or Geordi.
			Noun memberX = getFearOf(geordi);
			if (memberX == troi || memberX == geordi) return -1;

			if (memberX != null) {
				// Member A fears member B, where B must be Troi.
				Noun memberY = getFearOf(memberX);
				if (memberY != null) {
					// Violation if B is not Troi.
					if (memberY != troi) return -1;
				}
				else {
					// Trigger: (a) Member A fears Troi.
					String msg = "If Geordi fears " + memberX + ", then " + memberX + " fears Troi.";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule1, 'a', memberX, Is, troiF, msg);
					if (rs != 0) return rs;
				}
			}
			else {
				// Member A fears Troi.
				memberX = Mark.getPairNoun(troiF, members);
				if (memberX == troi || memberX == geordi) return -1;
				if (memberX != null) {
					// Trigger: (b) Geordi fears member A.
					String msg = "If " + memberX + " fears Troi, then Geordi fears " + memberX + ".";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule1, 'b', geordi, Is, getNounAsFear(memberX), msg);
					if (rs != 0) return rs;
				}
			}

			// Trigger: (c) If member X does not fear Troi, then Geordi cannot fear member X.
			if (mark.noun1.type == members && mark.verb == IsNot && mark.noun2 == troiF) {
				memberX = mark.noun1;
				String msg = "If " + memberX + " does not fear Troi, then Geordi cannot fear " + memberX + ".";
				//print(msg);
				rs = solver.addMarkByRule(mark, rule1, 'c', geordi, IsNot, getNounAsFear(mark.noun1), msg);
				if (rs != 0) return rs;
			}

			// Trigger: (d) If Geordi does not fear member X, then member X cannot fear Troi.
			if (mark.noun1 == geordi && mark.verb == IsNot && mark.noun2.type == fears) {
				memberX = getNounAsMember(mark.noun2);
				String msg = "If Geordi does not fear " + memberX + ", then " + memberX + " cannot fear Troi.";
				//print(msg);
				rs = solver.addMarkByRule(mark, rule1, 'd', memberX, IsNot, troiF, msg);
				if (rs != 0) return rs;
			}

			return rs;
		};


		// Clue 4. The one who is best at Fizzbin ranks 3 times higher at 3D chess than Worf's hero.
		// Member A is the hero of Worf, but Worf cannot be his own hero.
		// Member B is the best at Fizzbin (6F). This can be Worf.
		// The facts ensure 6F is either 3C or 6C. Worf's hero must be 1C or 2C.
		rule2.f = (mark) -> {
			int rs = 0;
			String msg;

			Noun memberA = getHeroOf(worf);
			if (memberA == worf) return -1;

			Noun rankA = memberA != null ? Mark.getPairNoun(memberA, chessRanks) : null;
			Noun rankB = Mark.getPairNoun(fizzbin6, chessRanks);

			// Violation: if best at Fizzbin (6F) is not ranked 3 times higher at 3D chess than Worf's hero.
			if (rankA != null && rankB != null) {
				if (threeXHigherC.f.apply(rankB, rankA) == IsNot) return -1;
			}

			if (memberA != null) {
				// Trigger: Worf's hero is not the best at Fizzin.
				msg = "If Worf's hero is " + memberA + ", then " + memberA + " is not the best at Fizzbin.";
				//print(msg);
				rs = solver.addMarkByRule(mark, rule2, 'a', memberA, IsNot, fizzbin6, msg);
				if (rs != 0) return rs;

				if (rankA != null) {
					// Trigger: The best at Fizzbin (6F) is ranked 3 times higher at 3D chess than Worf's hero.
					Noun noun = null;
					if (rankA == chess1) noun = chess3;
					if (rankA == chess2) noun = chess6;
					if (noun != null) {
						msg = "The best at Fizzbin (6F) is ranked 3 times higher at 3D chess than Worf's hero.";
						//print("rule2: 6F chess rank is " + noun);
						rs = solver.addMarkByRule(mark, rule2, 'b', fizzbin6, Is, noun, msg);
						if (rs != 0) return rs;
					}
					else
						return -1;
				}
				else {
					// Trigger: Worf's hero can only be ranked 1 or 2 at 3D chess.
					msg = "If Worf's hero is " + memberA + ", then " + memberA + " is only 1 or 2 at 3D chess.";
					//print(msg);
					for (int i = 2; i < chessRanks.nouns.size(); i++) {
						Noun noun = chessRanks.nouns.get(i);
						rs = solver.addMarkByRule(mark, rule2, 'b', memberA, IsNot, noun, msg);
						if (rs != 0) return rs;
					}
				}
			}

			// Trigger: (c) If the best at Fizzbin (6F) has chess ranking, then member with 1/3 rank is Worf's hero.
			// There is a violation if Worf's hero is Worf.
			Noun rank = Mark.getPairNoun(fizzbin6, chessRanks);
			memberA = null;
			if (rank == chess3) memberA = Mark.getPairNoun(chess1, members);
			if (rank == chess6) memberA = Mark.getPairNoun(chess2, members);
			if (memberA == worf) return -1;

			if (memberA != null) {
				msg = "If " + memberA + " ranks 3X lower at 3D chess than best at Fizzbin, then Worf's hero is " + memberA + ".";
				//print(msg);
				rs = solver.addMarkByRule(mark, rule2, 'c', worf, Is, getNounAsHero(memberA), msg);
				if (rs != 0) return rs;
			}

			// Trigger: (d) If memberX cannot be 1C or 2C, then Worf's hero is not memberX.
			for (Noun heroX : heroes.nouns) {
				if (solver.getGridVerb(worf, heroX) == IsNot) continue;
				Noun memberX = getNounAsMember(heroX);
				if (solver.getGridVerb(memberX, chess1) == IsNot && solver.getGridVerb(memberX, chess2) == IsNot) {
					msg = "If " + memberX + " is not ranked 1 or 2 at 3D Chess, then Worf's hero is not " + memberX + ".";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule2, 'd', worf, IsNot, heroX, msg);
					if (rs != 0) return rs;
				}
			}

			return rs;
		};


		// Clue 5. Picard's hero fears Geordi.
		// Member A fears Geordi, and is the hero of Picard, so A cannot be Geordi or Picard.
		rule3.f = (mark) -> {
			int rs = 0;

			// Member A is the hero of Picard.
			// Member B is feared by A. B cannot be A.

			// Violation if Picard's hero is Geordi or Picard.
			Noun memberA = getHeroOf(picard);
			if (memberA == geordi || memberA == picard) return -1;

			if (memberA != null) {
				Noun memberB = getFearOf(memberA);
				if (memberB == memberA) return -1;

				if (memberB != null) {
					// Violation if member B is not Geordi.
					if (memberB != geordi) return -1;
				}
				else {
					// Trigger: (a) member A fears Geordi.
					String msg = "If Picard's hero is " + memberA + ", then " + memberA + " fears Geordi.";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule3, 'a', memberA, Is, geordiF, msg);
					if (rs != 0) return rs;
				}
			}
			else {
				// Member A fears Geordi.
				memberA = Mark.getPairNoun(geordiF, members);
				if (memberA == geordi || memberA == picard) return -1;

				if (memberA != null) {
					// Trigger: (b) Picard's hero is A.
					String msg = "If " + memberA + " fears Geordi, then Picard's hero is " + memberA + ".";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule3, 'b', picard, Is, getNounAsHero(memberA), msg);
					if (rs != 0) return rs;
				}
			}

			// Trigger: (c) If Picard's hero is not member X, then member X cannot fear Geordi.
			if (mark.noun1 == picard && mark.verb == IsNot && mark.noun2.type == heroes) {
				Noun memberX = getNounAsMember(mark.noun2);
				String msg = "If Picard's hero is not " + memberX + ", then " + memberX + " cannot fear Geordi.";
				//print(msg);
				rs = solver.addMarkByRule(mark, rule3, 'c', memberX, IsNot, geordiF, msg);
				if (rs != 0) return rs;
			}

			// Trigger: (d) If member X does not fear Geordi, then Picard's hero is not member X.
			if (mark.noun1.type == members && mark.verb == IsNot && mark.noun2 == geordiF) {
				Noun memberX = mark.noun1;
				String msg = "If " + memberX + " does not fear Geordi, then Picard's hero is not " + memberX + ".";
				//print(msg);
				rs = solver.addMarkByRule(mark, rule3, 'd', picard, IsNot, heroes.nouns.get(mark.noun1.num - 1), msg);
				if (rs != 0) return rs;
			}

			return rs;
		};


		// Clue 7. Data is the hero of Riker's hero.
		// Member A's hero is Data, and is the hero of Riker, so A cannot be Data or Riker.
		rule4.f = (mark) -> {
			int rs = 0;

			// Member A is the hero of Riker.
			// Member B is the hero of A. B cannot be A.
			Noun memberA = getHeroOf(riker);
			if (memberA == data || memberA == riker) return -1;

			if (memberA != null) {
				Noun memberB = getHeroOf(memberA);
				if (memberB == memberA) return -1;

				if (memberB != null) {
					// Violation if member B is not Data.
					if (memberB != data) return -1;
				}
				else {
					// Trigger: (a) member A's hero is Data.
					String msg = "If Riker's hero is " + memberA + ", then " + memberA + "'s hero is Data.";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule4, 'a', memberA, Is, dataH, msg);
					if (rs != 0) return rs;
				}
			}
			else {
				// Member A's hero is Data.
				memberA = Mark.getPairNoun(dataH, members);
				if (memberA == data || memberA == riker) return -1;

				if (memberA != null) {
					// Trigger: (b) Riker's hero is A.
					String msg = "If " + memberA + "'s hero is Data, then Riker's hero is " + memberA + ".";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule4, 'b', riker, Is, getNounAsHero(memberA), msg);
					if (rs != 0) return rs;
				}
			}

			// Trigger: (c) If member X's hero is not Data, then Riker's hero is not member X.
			if (mark.noun1.type == members && mark.verb == IsNot && mark.noun2 == dataH) {
				Noun memberX = mark.noun1;
				String msg = "If " + memberX + "'s hero is not Data, then Riker's hero cannot be " + memberX + ".";
				//print(msg);
				rs = solver.addMarkByRule(mark, rule4, 'c', riker, IsNot, getNounAsHero(memberX), msg);
				if (rs != 0) return rs;
			}

			// Trigger: (d) If Riker's hero is not member X, then member X's hero is not Data.
			if (mark.noun1 == riker && mark.verb == IsNot && mark.noun2.type == heroes) {
				Noun memberX = getNounAsMember(mark.noun2);
				String msg = "If Riker's hero is not " + memberX + ", then " + memberX + "'s hero is not Data.";
				//print(msg);
				rs = solver.addMarkByRule(mark, rule4, 'd', memberX, IsNot, dataH, msg);
				if (rs != 0) return rs;
			}

			return rs;
		};


		// Clue 10. Riker is feared by the person Picard fears and is the hero of Worf's hero.
		rule5.f = (mark) -> {
			int rs = 0;
			Noun memberA, memberB;

			// 1) Riker is feared by the person Picard fears.
			// Member A fears Riker, and is feared by Picard, so A cannot be Picard or Riker.
			// Member B is feared by A. B cannot be A.

			// Member A is feared by Picard.
			memberA = getFearOf(picard);
			if (memberA == picard || memberA == riker) return -1;

			if (memberA != null) {
				memberB = getFearOf(memberA);
				if (memberB == memberA) return -1;

				if (memberB != null) {
					// Violation if member B is not Riker
					if (memberB != riker) return -1;
				}
				else {
					// Trigger: (a) member A fears Riker
					String msg = "If Picard fears " + memberA + ", then " + memberA + " fears Riker.";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule5, 'a', memberA, Is, rikerF, msg);
					if (rs != 0) return rs;
				}
			}
			else {
				// Member A fears Riker.
				memberA = Mark.getPairNoun(rikerF, members);
				if (memberA == picard || memberA == riker) return -1;

				if (memberA != null) {
					// Trigger: (b) Picard fears member A.
					String msg = "If " + memberA + " fears Riker, then Picard fears " + memberA + ".";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule5, 'b', picard, Is, getNounAsFear(memberA), msg);
					if (rs != 0) return rs;
				}
			}

			// 2) Riker is the hero of Worf's hero.
			// Member A's hero is Riker, and is the hero of Worf, so A cannot be Riker or Worf.
			// Member B is the hero of A. B cannot be A.

			// Member A is the hero of Worf.
			memberA = getHeroOf(worf);
			if (memberA == riker || memberA == worf) return -1;

			if (memberA != null) {
				memberB = getHeroOf(memberA);
				if (memberB == memberA) return -1;

				if (memberB != null) {
					// Violation if member B is not Riker.
					if (memberB != riker) return -1;
				}
				else {
					// Trigger: (c) member A's hero is Riker.
					String msg = "If Worf's hero is " + memberA + ", then " + memberA + "'s hero is Riker.";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule5, 'c', memberA, Is, rikerH, msg);
					if (rs != 0) return rs;
				}
			}
			else {
				// Member A's hero is Riker.
				memberA = Mark.getPairNoun(rikerH, members);

				// Violation if member A is Riker or Worf.
				if (memberA == riker || memberA == worf) return -1;

				// Trigger: (d) Worf's hero is member A.
				if (memberA != null) {
					String msg = "If " + memberA + "'s hero is Riker, then Worf's hero is " + memberA + ".";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule5, 'd', worf, Is, getNounAsHero(memberA), msg);
					if (rs != 0) return rs;
				}
			}

			// Trigger: (e) If member X does not fear Riker, then Picard cannot fear member X.
			if (mark.noun1.type == members && mark.verb == IsNot && mark.noun2 == rikerF) {
				Noun memberX = mark.noun1;
				String msg = "If member " + memberX + " does not fear Riker, then Picard cannot fear " + memberX + ".";
				//print(msg);
				rs = solver.addMarkByRule(mark, rule5, 'e', picard, IsNot, fears.nouns.get(mark.noun1.num - 1), msg);
				if (rs != 0) return rs;
			}

			// Trigger: (f) If Picard does not fear member X, then member X cannot fear Riker.
			if (mark.noun1 == picard && mark.verb == IsNot && mark.noun2.type == fears) {
				Noun memberX = getNounAsMember(mark.noun2);
				String msg = "If Picard does not fear " + memberX + ", then " + memberX + " cannot fear Riker.";
				//print(msg);
				rs = solver.addMarkByRule(mark, rule5, 'f', memberX, IsNot, rikerF, msg);
				if (rs != 0) return rs;
			}

			// Trigger: (g) If member X's hero is not Riker, then Worf's hero is not member X.
			if (mark.noun1.type == members && mark.verb == IsNot && mark.noun2 == rikerH) {
				Noun memberX = mark.noun1;
				String msg = "If " + memberX + "'s hero is not Riker, then Worf's hero is not " + memberX + ".";
				//print(msg);
				rs = solver.addMarkByRule(mark, rule5, 'g', worf, IsNot, heroes.nouns.get(mark.noun1.num - 1), msg);
				if (rs != 0) return rs;
			}

			// Trigger: (h) If Worf's hero is not member X, then member X's hero is not Riker.
			if (mark.noun1 == worf && mark.verb == IsNot && mark.noun2.type == heroes) {
				Noun memberX = getNounAsMember(mark.noun2);
				String msg = "If Worf's hero is not " + memberX + ", then " + memberX + "'s hero is not Riker.";
				//print(msg);
				rs = solver.addMarkByRule(mark, rule5, 'h', memberX, IsNot, rikerH, msg);
				if (rs != 0) return rs;
			}

			return rs;
		};

		// Solution.
		answer = new int[][]{{0, 2, 1, 4, 3, 5}, {3, 1, 4, 2, 0, 5}, {1, 2, 4, 5, 3, 0}, {4, 3, 0, 2, 5, 1}};
	}

	private Noun getNounAsMember(Noun noun) { return members.nouns.get(noun.num - 1); }
	private Noun getNounAsFear(Noun noun) { return fears.nouns.get(noun.num - 1); }
	private Noun getNounAsHero(Noun noun) { return heroes.nouns.get(noun.num - 1); }
	
	private Noun getHeroOf(Noun noun) {
		int i = Mark.getPairNounNum(noun, heroes) - 1;
		return i < 0 ? null : members.nouns.get(i);
	}

	private Noun getFearOf(Noun noun) {
		int i = Mark.getPairNounNum(noun, fears) - 1;
		return i < 0 ? null : members.nouns.get(i);
	}

	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Member, 2=Fizzbin, 3=3D Chess, 4=Fear, 5=Hero
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1:
						if (link.nounType == fizzbinRanks)
							msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name + " at Fizzbin";
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + verb.name + " ranked " + noun2.name + " at 3D chess";
						break;
					case 4:
						if (link == With) {
							if (noun1.num == noun2.num)
								msg = noun1.name + " " + (verb == Is ? "fears" : "does not fear") + " him/herself";
							else
								msg = noun1.name + " " + (verb == Is ? "fears" : "does not fear") + " " + members.nouns.get(noun2.num - 1).name;
						}
						break;
					case 5:
						if (link == With) {
							if (noun1.num == noun2.num)
								msg = noun1.name + "'s hero " + verb.name + " him/herself";
							else
								msg = noun1.name + "'s hero " + verb.name + " " + members.nouns.get(noun2.num - 1).name;
						}
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						if (link.nounType == chessRanks)
							msg = "The person ranked " + noun1.name + " at Fizzbin " + verb.name + " " + link.name + " " + noun2.name + " at 3D chess";
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg = "The person ranked " + noun1.name + " at Fizzbin " + verb.name + " ranked " + noun2.name + " at 3D chess";
						break;
					case 4: break;
					case 5: break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link.nounType == fizzbinRanks)
							msg = "The person ranked " + noun1.name + " at 3D chess " + verb.name + " " + link.name + " " + noun2.name + " at Fizzbin";
						break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5: break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5:
						if (link == With) {
							if (noun1.num == noun2.num)
								msg = members.nouns.get(noun1.num - 1).name + (verb == Is ? "fears" : " does not fear") + " his/her own hero" ;
							else
								msg = "The one " + members.nouns.get(noun1.num - 1).name + " fears " + verb.name + " " + members.nouns.get(noun2.num - 1).name + "'s hero";
						}
						break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5: break;
				}
				break;
		}

		return msg + ".";
	}
}
