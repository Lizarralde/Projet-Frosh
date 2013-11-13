package states.junit;
import states.*;
import subjects.*;
import static org.junit.Assert.*;

import org.junit.Test;
import config.Config;
import diseases.Disease;

public class TestState {
	
	
	@Test
	public void changeState() {
		Subject s = new Subject(Type.HUMAN);
		State h = new Healthy();
		State si = new Sick();
		State c = new Contagious();
		
		assertEquals(s.getState().toString(),"H");	
		s.setDisease(Disease.H1N1);
		h.changeState(s);
		assertEquals(s.getState().toString(),"S");		
		si.changeState(s);
		assertEquals(s.getState().toString(),"S");
		for(int i =0;i<Integer.parseInt(Config.getProperty("Human.Incubation.Sick"));i++)
		{
			s.incubate();
		}
		si.changeState(s);
		
		assertEquals(s.getState().toString(),"C");
		for(int i =0;i<Integer.parseInt(Config.getProperty("Human.Incubation.Contagious"));i++)
		{
			s.incubate();
		}
		c.changeState(s);
		
		//Si on a mis 100 dans le fichier de config alors c'est certain qu'il est mort
		// J'ai préféré faire un if que mettre une fonction qui modifie le fichier properties car si quelqu'un s'amuse a changer les valeurs au cours du programme...
		if (Config.getProperty("H1N1.Mortality.Human")=="100")
		{
		assertEquals(s.getState().toString(),"D");
		}
		else if (Config.getProperty("H1N1.Mortality.Human")=="0")
		{
		assertEquals(s.getState().toString(),"R");
		}
	}
	
}
