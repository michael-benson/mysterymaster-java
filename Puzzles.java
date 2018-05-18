package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.Base;
import com.mysterymaster.puzzle.Puzzle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Puzzles class for the Mystery Master projects.<br>
 * This class contains useful methods.<br>
 * @version 2018-05-07
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class Puzzles extends Base {
	@Override
	public String toString() { return "Puzzles"; }
	
	@Override
	public String asString() { return this.toString(); }

	/** Puzzle titles. */
	public final static List<String> titles = Arrays.asList(
		"Please select a logic puzzle"
			  
		, "Abbondanza!"
		, "A Day At The Zoo"
		, "Air Turbulence"
		, "All Tired Out"
		, "Angling For Success"
		, "Astrophysics Conference"
		, "At The Alter Altar"
		, "Big 5 Game Rangers"
		, "Bumper Crop"
		, "Circus Anniversaries"
		, "Coins Of The Realm"
		, "Credit Card Shuffle"
		, "Dandy Salespeople"
		, "The Designated Driver"
		, "Dog Duty"
		, "Eating Out"
		, "Ellis Island Idylls"
		, "Extra Keys"
		, "Fancy Footwear"
		, "Five Houses"
		, "The Floppies"
		, "Floral Deliveries"
		, "Fruity Speakers"
		, "Gifts Of The Heart"
		, "Hello Dolly!"
		, "Household Pets"
		, "House Members"
		, "Iron Filings"
		, "Island Hopping"
		, "Life Is A Picnic"
		, "Lucky Streets"
		, "Mixed-Up Mythology"
		, "Modern Novels"
		, "Moving Day"
		, "Nested Dolls"
		, "New Self-Improvement Committee"
		, "Oh Christmas Tree"
		, "On The Beach"
		, "Overdue"
		, "Playing Cards"
		, "Polynesian Postcards"
		, "Production Line"
		, "Psychic Phone Friends"
		, "Sally's Flower Girls"
		, "The Seven Wives of Henri VII"
		, "Shoo! Out Of The Garden!"
		, "Six Fearsome Heroes"
		, "Small Town Motels"
		, "The Smith-Jones-Robinson Classic"
		, "Soapy Situations"
		, "Spy Girls"
		, "Star-Crossed"
		, "Student Logic"
		, "Super Surfers"
		, "Surprise Visits"
		, "Under The Big Top"
		, "Video Rentals"
		, "Weekend Trekkies"
		, "Wicked Women"
		, "Winter Breaks"
		, "Witnesses"
	);
	
	/**
	 * Returns the Puzzle object given its one-based index.
	 * @param index One-based index.
	 * @return Puzzle.
	 */
	public static Puzzle getPuzzleByNum(int index) {
		Puzzle puzzle = null;
		
		// This is created programmatically.
		switch(index) {
			case 1: puzzle = new Abbondanza(); break;
			case 2: puzzle = new ADayAtTheZoo(); break;
			case 3: puzzle = new AirTurbulence(); break;
			case 4: puzzle = new AllTiredOut(); break;
			case 5: puzzle = new AnglingForSuccess(); break;
			case 6: puzzle = new AstrophysicsConference(); break;
			case 7: puzzle = new AtTheAlterAltar(); break;
			case 8: puzzle = new Big5GameRangers(); break;
			case 9: puzzle = new BumperCrop(); break;
			case 10: puzzle = new CircusAnniversaries(); break;
			case 11: puzzle = new CoinsOfTheRealm(); break;
			case 12: puzzle = new CreditCardShuffle(); break;
			case 13: puzzle = new DandySalespeople(); break;
			case 14: puzzle = new DesignatedDriver(); break;
			case 15: puzzle = new DogDuty(); break;
			case 16: puzzle = new EatingOut(); break;
			case 17: puzzle = new EllisIslandIdylls(); break;
			case 18: puzzle = new ExtraKeys(); break;
			case 19: puzzle = new FancyFootwear(); break;
			case 20: puzzle = new FiveHouses(); break;
			case 21: puzzle = new Floppies(); break;
			case 22: puzzle = new FloralDeliveries(); break;
			case 23: puzzle = new FruitySpeakers(); break;
			case 24: puzzle = new GiftsOfTheHeart(); break;
			case 25: puzzle = new HelloDolly(); break;
			case 26: puzzle = new HouseholdPets(); break;
			case 27: puzzle = new HouseMembers(); break;
			case 28: puzzle = new IronFilings(); break;
			case 29: puzzle = new IslandHopping(); break;
			case 30: puzzle = new LifeIsAPicnic(); break;
			case 31: puzzle = new LuckyStreets(); break;
			case 32: puzzle = new MixedUpMythology(); break;
			case 33: puzzle = new ModernNovels(); break;
			case 34: puzzle = new MovingDay(); break;
			case 35: puzzle = new NestedDolls(); break;
			case 36: puzzle = new NewSelfImprovement(); break;
			case 37: puzzle = new OhChristmasTree(); break;
			case 38: puzzle = new OnTheBeach(); break;
			case 39: puzzle = new Overdue(); break;
			case 40: puzzle = new PlayingCards(); break;
			case 41: puzzle = new PolynesianPostcards(); break;
			case 42: puzzle = new ProductionLine(); break;
			case 43: puzzle = new PsychicPhoneFriends(); break;
			case 44: puzzle = new SallysFlowerGirls(); break;
			case 45: puzzle = new SevenWivesHenriVII(); break;
			case 46: puzzle = new ShooOutOfTheGarden(); break;
			case 47: puzzle = new SixFearsomeHeroes(); break;
			case 48: puzzle = new SmallTownMotels(); break;
			case 49: puzzle = new SmithJonesRobinson(); break;
			case 50: puzzle = new SoapySituations(); break;
			case 51: puzzle = new SpyGirls(); break;
			case 52: puzzle = new StarCrossed(); break;
			case 53: puzzle = new StudentLogic(); break;
			case 54: puzzle = new SuperSurfers(); break;
			case 55: puzzle = new SurpriseVisits(); break;
			case 56: puzzle = new UnderTheBigTop(); break;
			case 57: puzzle = new VideoRentals(); break;
			case 58: puzzle = new WeekendTrekkies(); break;
			case 59: puzzle = new WickedWomen(); break;
			case 60: puzzle = new WinterBreaks(); break;
			case 61: puzzle = new Witnesses(); break;
		}
		
		return puzzle;
	}
	
	/**
	 * Returns the Puzzle object given its one-based index.
	 * @param name Name of the puzzle class.
	 * @return Puzzle.
	 */
	public static Puzzle getPuzzleByName(String name) {
		Puzzle puzzle = null;
		
		switch(name) {
			case "Abbondanza": puzzle = new Abbondanza(); break;
			case "ADayAtTheZoo": puzzle = new ADayAtTheZoo(); break;
			case "AirTurbulence": puzzle = new AirTurbulence(); break;
			case "AllTiredOut": puzzle = new AllTiredOut(); break;
			case "AnglingForSuccess": puzzle = new AnglingForSuccess(); break;
			case "AstrophysicsConference": puzzle = new AstrophysicsConference(); break;
			case "AtTheAlterAltar": puzzle = new AtTheAlterAltar(); break;
			case "Big5GameRangers": puzzle = new Big5GameRangers(); break;
			case "BumperCrop": puzzle = new BumperCrop(); break;
			case "CircusAnniversaries": puzzle = new CircusAnniversaries(); break;
			case "CoinsOfTheRealm": puzzle = new CoinsOfTheRealm(); break;
			case "CreditCardShuffle": puzzle = new CreditCardShuffle(); break;
			case "DandySalespeople": puzzle = new DandySalespeople(); break;
			case "DesignatedDriver": puzzle = new DesignatedDriver(); break;
			case "DogDuty": puzzle = new DogDuty(); break;
			case "EatingOut": puzzle = new EatingOut(); break;
			case "EllisIslandIdylls": puzzle = new EllisIslandIdylls(); break;
			case "ExtraKeys": puzzle = new ExtraKeys(); break;
			case "FancyFootwear": puzzle = new FancyFootwear(); break;
			case "FiveHouses": puzzle = new FiveHouses(); break;
			case "Floppies": puzzle = new Floppies(); break;
			case "FloralDeliveries": puzzle = new FloralDeliveries(); break;
			case "FruitySpeakers": puzzle = new FruitySpeakers(); break;
			case "GiftsOfTheHeart": puzzle = new GiftsOfTheHeart(); break;
			case "HelloDolly": puzzle = new HelloDolly(); break;
			case "HouseholdPets": puzzle = new HouseholdPets(); break;
			case "HouseMembers": puzzle = new HouseMembers(); break;
			case "IronFilings": puzzle = new IronFilings(); break;
			case "IslandHopping": puzzle = new IslandHopping(); break;
			case "LifeIsAPicnic": puzzle = new LifeIsAPicnic(); break;
			case "LuckyStreets": puzzle = new LuckyStreets(); break;
			case "MixedUpMythology": puzzle = new MixedUpMythology(); break;
			case "ModernNovels": puzzle = new ModernNovels(); break;
			case "MovingDay": puzzle = new MovingDay(); break;
			case "NestedDolls": puzzle = new NestedDolls(); break;
			case "NewSelfImprovement": puzzle = new NewSelfImprovement(); break;
			case "OhChristmasTree": puzzle = new OhChristmasTree(); break;
			case "OnTheBeach": puzzle = new OnTheBeach(); break;
			case "Overdue": puzzle = new Overdue(); break;
			case "PlayingCards": puzzle = new PlayingCards(); break;
			case "PolynesianPostcards": puzzle = new PolynesianPostcards(); break;
			case "ProductionLine": puzzle = new ProductionLine(); break;
			case "PsychicPhoneFriends": puzzle = new PsychicPhoneFriends(); break;
			case "SallysFlowerGirls": puzzle = new SallysFlowerGirls(); break;
			case "SevenWivesHenriVII": puzzle = new SevenWivesHenriVII(); break;
			case "ShooOutOfTheGarden": puzzle = new ShooOutOfTheGarden(); break;
			case "SixFearsomeHeroes": puzzle = new SixFearsomeHeroes(); break;
			case "SmallTownMotels": puzzle = new SmallTownMotels(); break;
			case "SmithJonesRobinson": puzzle = new SmithJonesRobinson(); break;
			case "SoapySituations": puzzle = new SoapySituations(); break;
			case "SpyGirls": puzzle = new SpyGirls(); break;
			case "StarCrossed": puzzle = new StarCrossed(); break;
			case "StudentLogic": puzzle = new StudentLogic(); break;
			case "SuperSurfers": puzzle = new SuperSurfers(); break;
			case "SurpriseVisits": puzzle = new SurpriseVisits(); break;
			case "UnderTheBigTop": puzzle = new UnderTheBigTop(); break;
			case "VideoRentals": puzzle = new VideoRentals(); break;
			case "WeekendTrekkies": puzzle = new WeekendTrekkies(); break;
			case "WickedWomen": puzzle = new WickedWomen(); break;
			case "WinterBreaks": puzzle = new WinterBreaks(); break;
			case "Witnesses": puzzle = new Witnesses(); break;
		}
		
		return puzzle;
	}
	
	/**
	 * Returns a list of puzzles where optionally the first item is null.
	 * @param nullFlag True for first item is null.
	 * @return List of puzzles.
	 */
	public static List<Puzzle> getPuzzlesAsList(boolean nullFlag) {
		List<Puzzle> list = new ArrayList<>();
		
		// The "dumb" way to populate this list.
		if (nullFlag) {
			Puzzle puzzle = new Puzzle();
			puzzle.myName = "Please select a logic puzzle";
			puzzle.myTitle = "Please select a logic puzzle";
			list.add(puzzle);
		}
		
		for (int i = 0; i < 61; i++) {
			list.add(getPuzzleByNum(i + 1));
		}

		return list;
	}
}
