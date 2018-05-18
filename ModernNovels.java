package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;
import java.util.function.Function;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class ModernNovels extends Puzzle {
	ModernNovels() {
		// Properties.
		myName = "ModernNovels";
		myTitle = "Modern Novels";

		// Nouns.
		NounType students = addNounType("Student");
		Noun student1 = students.addNoun("1st");
		Noun student2 = students.addNoun("2nd");
		Noun student3 = students.addNoun("3rd");
		Noun student4 = students.addNoun("4th");
		Noun student5 = students.addNoun("5th");

		NounType firstNames = addNounType("First Name");
		Noun oscar = firstNames.addNoun("Oscar");
		Noun peter = firstNames.addNoun("Peter");
		Noun ruth1 = firstNames.addNoun("Ruth1");
		Noun ruth2 = firstNames.addNoun("Ruth2");
		Noun shirley = firstNames.addNoun("Shirley");

		NounType lastNames = addNounType("Last Name");
		Noun underwood = lastNames.addNoun("Underwood");
		Noun valdez = lastNames.addNoun("Valdez");
		Noun yorke = lastNames.addNoun("Yorke");
		Noun zaniewski = lastNames.addNoun("Zaniewski");
		Noun sameLast = lastNames.addNoun("SameLast");

		NounType novels = addNounType("Novel");
		Noun absalom = novels.addNoun("Absalom! Absalom!", "Absalom!");
		Noun lightAugust = novels.addNoun("Light in August");
		Noun shipOfFools = novels.addNoun("Ship of Fools");
		Noun sunRises = novels.addNoun("The Sun Also Rises", "Sun Also Rises");
		Noun lighthouse = novels.addNoun("To The Lighthouse", "Lighthouse");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link before = addLink("before", students);
		before.f = SmartLink.getIsLessThan(0);

		// Facts.

		// * From clue 3, the person who chose "The Sun Also Rises" has a sibling, so that person may or may not have SameLast.
		// * Students with no sibling are: Shirley, Valdez, Yorke, so they cannot be "SameLast" and/or chose "The Sun Also Rises".

		// Intro. Two women are named Ruth.
		addFact("0", ruth1, Is, before, ruth2, "Ruth1 is before Ruth2 to avoid duplicate solutions.");

		// Clue 1. Shirley is an only child.
		addFact("1", shirley, IsNot, With, sameLast);

		// Clue 2. The two men are unrelated; exactly one of them chose a Faulkner novel.

		// Clue 3. The student (whose first name isn't Ruth) who chose Ernest Hemingway's "The Sun Also Rises" has a sibling in class.
		addFact("3", getList(ruth1, ruth2), IsNot, With, sunRises);

		addFact("3", sunRises, IsNot, With, sameLast, "The student who chose The Sun Also Rises has a sibling (SameLast) in class.");
		addFact("1,3", shirley, IsNot, With, sunRises);

		// Clue 4. The book chosen by the only student surnamed Yorke didn't have "light" anywhere in its alias.
		addFact("4", yorke, IsNot, With, getList(lightAugust, lighthouse));
		addFact("3,4", yorke, IsNot, With, sunRises);

		// Clue 5. Four of the five students are Oscar, Ms. Underwood, the only student surnamed Valdez, and the one who chose Virginia Woolf's To the Lighthouse;
		// the fifth student shares either a first name, last name, or choice of author with the person who chose William Faulkner's Absalom! Absalom!
		// Because there is a sibling, care must be used when restricting where the last names go. This clue means:
		// 1. Oscar
		// 2. A woman with the last name Underwood or SameLast. Note that Underwood may have a male sibling!
		// 3. Valdez (an only student)
		// 4. "To The Lighthouse"
		// 5. Either (a) the William Faulkner book "Light in August", or (b) person who chose "Absalom! Absalom!" is Ruth1 and first name is Ruth2 or
		// (c) one is SameLast and the other is "Ship of Fools".
		addFact("5", student1, Is, With, oscar);
		addFact("5", student2, IsNot, With, getList(peter, yorke, zaniewski));
		addFact("5", student3, Is, With, valdez);
		addFact("3,5", valdez, IsNot, With, sunRises);
		addFact("5", student4, Is, With, lighthouse);
		addFact("5", underwood, IsNot, With, lighthouse);
		addFact("5", student5, IsNot, With, absalom);

		// Rules.
		List<Noun> gals = getList(ruth1, ruth2, shirley);

		Rule rule1 = addRule("2", "Oscar and Peter are not siblings.", getList(sameLast, sunRises));
		rule1.f = (mark) -> {
			int rs = 0;
			// Violation: If one man has SameLast and the other man chose "The Sun Also Rises".
			Noun firstNameA = Mark.getPairNoun(sameLast, firstNames); // Get first name of SameLast.
			Noun firstNameB = Mark.getPairNoun(sunRises, firstNames); // Get first name with "The Sun Also Rises".
			if ((firstNameA == oscar && firstNameB == peter) || (firstNameA == peter && firstNameB == oscar)) return -1;

			// Trigger: If no woman can have "The Sun Also Rises", then the two men cannot be SameLast.
			// They also cannot have "Ship of Fools" and "To The Lighthouse" because one has "The Sun Also Rises" and the other has a Faulkner novel.
			if (mark.verb == IsNot && mark.noun1.type == firstNames && mark.noun2.type == lastNames) {
				if (solver.cannotBeWith(gals, sunRises)) {
					String msg = "A man cannot be SameLast.";
					//print(msg);
					rs = solver.addMarkByRule(mark, rule1, 'a', oscar, IsNot, sameLast, msg); if (rs != 0) return rs;
					rs = solver.addMarkByRule(mark, rule1, 'a', peter, IsNot, sameLast, msg); if (rs != 0) return rs;

					msg = "A man cannot have Ship of Fools.";
					rs = solver.addMarkByRule(mark, rule1, 'b', oscar, IsNot, shipOfFools, msg); if (rs != 0) return rs;
					rs = solver.addMarkByRule(mark, rule1, 'b', peter, IsNot, shipOfFools, msg); if (rs != 0) return rs;

					msg = "A man cannot have To the Lighthouse";
					rs = solver.addMarkByRule(mark, rule1, 'b', oscar, IsNot, lighthouse, msg); if (rs != 0) return rs;
					rs = solver.addMarkByRule(mark, rule1, 'b', peter, IsNot, lighthouse, msg); if (rs != 0) return rs;
				}
			}

			return rs;
		};


		Rule rule2 = addRule("2", "Exactly one of the two men chose a Faulkner novel.");
		rule2.f = smartRule.getMatchOneToExactlyOne(rule2, getList(oscar, peter), getList(absalom, lightAugust));

		// Clue 5. The fifth student shares either (a) first name, (b) last name (sibling), or (c) author with the student who chose Absolom! Absalom!
		// If the fifth student's first name, last name, and novel are known, then:
		// (a) The novel is "Light In August", or
		// If the first and last names of the student who chose "Absalom! Absalom!" are known
		// (b) The first names are the same (Ruth2 and Ruth1), and the last names are not (because they cannot be siblings), or
		// (c) The last names are the same - one of them is SameLast, the other has "The Sun Also Rises", and they can be siblings.
		Rule rule3 = addRule("5", "The fifth student shares either a first name, last name, or author with the person who chose Absolom! Absalom!");
		Function<Mark, Integer> f3 = smartRule.getMatchAtLeastOne(rule3, student5, getList(ruth2, sameLast, lightAugust));
		rule3.f = (mark) -> {
			int rs = 0;

			// Violation: if the fifth student's first name is not Ruth2, last name is not SameLast, and novel is not "Light in August".
			rs = f3.apply(mark);
			if (rs != 0) return -1;

			Noun A = Mark.getPairNoun(student5, firstNames); if (A == null) return rs;
			Noun B = Mark.getPairNoun(student5, lastNames); if (B == null) return rs;
			Noun C = Mark.getPairNoun(student5, novels); if (C == null) return rs;
			Noun D = Mark.getPairNoun(ruth1, novels);

			// Violation: if the fifth student's first name is Ruth2 and last name is not SameLast
			// and novel is not "Light in August" and Ruth1's novel is not "Absalom! Absalom".
			if (A == ruth2 && B != sameLast && C != lightAugust && D != null && D != absalom) return -1;

			// Trigger: If the fifth student's first name is Ruth2, last name is not SameLast, and novel is not
			// "Light in August", then Ruth1's novel must be "Absalom! Absalom!".
			if (A == ruth2 && B != sameLast && C != lightAugust && D == null) {
				String msg = "If fifth student's first name is Ruth2, last name not SameLast, novel not Light in August, then Ruth1's novel must be Absalom! Absalom! ";
				rs = solver.addMarkByRule(mark, rule3, 'a', ruth1, Is, absalom, msg);
				if (rs != 0) return rs;
			}

			return rs;
		};

		// Clues 1,2,3,5. Either Ruth1 or Ruth2 is a sibling of Oscar or Peter.
		// So if the men cannot be SameLast, then a Ruth must be SameLast (determined).
		// Trigger: If SameLast is 2nd, then Ruth1 is 2nd and one of the men is Underwood.
		Rule rule4 = addRule("1,2,3,5", "The second student's last name is Underwood.", getList(student2, sameLast));
		rule4.f = (mark) -> {
			int rs = 0;

			if (mark.verb == Is && mark.noun1 == student2 && mark.noun2 == sameLast) {
				String msg = "Ruth1 must be 2nd";
				rs = solver.addMarkByRule(mark, rule4, 'a', ruth1, Is, student2, msg);
				if (rs != 0) return rs;

				msg = "One of the men must be Underwood.";
				for (Noun gal : gals) {
					rs = solver.addMarkByRule(mark, rule4, 'b', gal, IsNot, underwood, msg);
					if (rs != 0) return rs;
				}

				msg = "Ms. Underwood must chose The Sun Also Rises";
				rs = solver.addMarkByRule(mark, rule4, 'a', underwood, Is, sunRises, msg);
				if (rs != 0) return rs;
			}

			return rs;
		};

		// Solution. Needs 1/3 assumptions.
		answer = new int[][]{{0, 2, 1, 4, 3}, {0, 4, 1, 3, 2}, {3, 0, 1, 4, 2}};
	}

	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Student, 2=First Name, 3=Last Name, 4=Novel
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg = "The " + noun1.name + " student " + verb.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = "The " + noun1.name + " student " + verb.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = "The " + noun1.name + " student " + verb.name + " the one who chose " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + "'s novel " + verb.name + " " + noun2.name;
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
							msg = noun1.name + "'s novel " + verb.name + " " + noun2.name;
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
