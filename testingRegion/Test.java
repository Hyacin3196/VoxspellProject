package testingRegion;

import java.io.FileNotFoundException;

import voxspell.BashCommand;
import voxspell.FileHandler;

public class Test {
	public static void main(String[] args){
		System.out.println(BashCommand.bashReturnCommand("ls \"spelling lists\""));
		System.out.println(BashCommand.bashReturnCommand("pwd"));
		System.out.println(FileHandler.getWordList("spelling_lists/easy.txt"));
	}

}
