package testingRegion;

import java.io.FileNotFoundException;

import voxspell.BashCommand;
import voxspell.FileHandler;

public class Test {
	public static void main(String[] args){
		BashCommand.sayFestival("hello there");
		System.out.println(BashCommand.bashReturnCommand("pwd"));
	}

}
